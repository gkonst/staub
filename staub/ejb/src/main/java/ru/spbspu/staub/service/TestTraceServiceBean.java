package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.*;

/**
 * Stateless EJB Service for manipulations with <code>TestTrace</code> entity.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Name("testTraceService")
@AutoCreate
@Stateless
public class TestTraceServiceBean extends GenericServiceBean<TestTrace, Integer> implements TestTraceService {
    private static final long EXPIRATION_THRESHOLD = 5 * 60 * 1000; // 5 minutes

    @EJB
    private AssignmentService assignmentService;

    @EJB
    private QuestionTraceService questionTraceService;

    public FormTable find(FormProperties formProperties, Group group, Student student, Discipline discipline,
                                    Date begin, Date end) {
        logger.debug("> find(FormProperties=#0, Group=#1, Student=#2, Discipline=#3, Date=#4, Date=#5)",
                formProperties, group, student, discipline, begin, end);

        StringBuilder query = new StringBuilder();
        query.append("select t from TestTrace t");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (discipline != null) {
            query.append(", Discipline d join d.categories c where t.test.category = c and d = :discipline and");
            parameters.put("discipline", discipline);
        } else {
            query.append(" where");
        }

        if (student != null) {
            query.append(" t.student = :student and");
            parameters.put("student", student);
        } else if (group != null) {
            query.append(" t.student.group = :group and");
            parameters.put("group", group);
        }

        if (begin != null) {
            query.append(" t.started >= :begin and");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(begin);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            parameters.put("begin", calendar.getTime());
        }

        if (end != null) {
            query.append(" t.started <= :end and");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);

            parameters.put("end", calendar.getTime());
        }

        query.append(" t.finished is not null");

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< find(FormProperties, Group, Student, Discipline, Date, Date)");

        return formTable;
    }

    public long count(Student student) {
        Query q = getEntityManager().createQuery("select count(t) from TestTrace t where t.student = :student");
        q.setParameter("student", student);
        return (Long) q.getSingleResult();
    }

    public long count(Test test) {
        Query q = getEntityManager().createQuery("select count(t) from TestTrace t where t.test = :test");
        q.setParameter("test", test);
        return (Long) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public TestTrace getTestTrace(Assignment assignment) {
        Query q = getEntityManager().createQuery("select t from TestTrace t where t.assignment = :assignment");
        q.setParameter("assignment", assignment);

        TestTrace testTrace = null;
        try {
            testTrace = (TestTrace) q.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        if (testTrace == null) {
            testTrace = createTestTrace(assignment);
        }

        return testTrace;
    }

    public TestTrace startTest(TestTrace testTrace) {
        if (testTrace.getStarted() == null) {
            testTrace.setStarted(new Date());
        }
        return makePersistent(testTrace);
    }

    public TestTrace endTest(TestTrace testTrace, boolean passed) {
        long questionsCount = getCount(testTrace);
        long correctCount = getCorrectCount(testTrace);
        int score = (int) (correctCount * 100 / questionsCount);
        testTrace.setScore(score);

        testTrace.setTestPassed(passed);

        testTrace.setFinished(new Date());

        Assignment assignment = testTrace.getAssignment();

        testTrace.setAssignment(null);

        assignment.setTestTrace(null);

        makePersistent(testTrace);

        try {
            assignmentService.remove(assignmentService.makePersistent(assignment));
        } catch (RemoveException e) {
            // should not happen
        }

        return testTrace;
    }

    @SuppressWarnings("unchecked")
    public boolean checkPart(TestTrace testTrace, Integer part) {
        Query q = getEntityManager().createQuery("select q.id from QuestionTrace q where q.testTrace = :testTrace and q.part = :part");
        q.setParameter("testTrace", testTrace);
        q.setParameter("part", part);
        List<Integer> questionTraceIds = q.getResultList();

        int passScore = getPassScore(testTrace, part);

        int questionsCount = questionTraceIds.size();
        int correctCount = 0;
        for (Integer questionTraceId : questionTraceIds) {
            QuestionTrace qt = questionTraceService.findById(questionTraceId);
            qt = questionTraceService.checkQuestion(qt);
            if (qt.getCorrect()) {
                correctCount++;
            }
        }

        return ((correctCount * 100 / questionsCount) >= passScore);
    }

    public long getCorrectCount(TestTrace testTrace) {
        Query q = getEntityManager().createQuery("select count(q) from QuestionTrace q where q.correct = true and q.testTrace = :testTrace");
        q.setParameter("testTrace", testTrace);
        return (Long) q.getSingleResult();
    }

    public long getCount(TestTrace testTrace) {
        Query q = getEntityManager().createQuery("select count(q) from QuestionTrace q where q.testTrace = :testTrace");
        q.setParameter("testTrace", testTrace);
        return (Long) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public void processExpiredTestTraces() {
        logger.debug("> processExpiredTestTraces()");

        Query q = getEntityManager().createQuery("select t from TestTrace t where t.finished is null and t.assignment.testEnd <= :currentDate");
        Date date = new Date();
        q.setParameter("currentDate", date);

        for (TestTrace testTrace : (List<TestTrace>) q.getResultList()) {
            if ((date.getTime() - testTrace.getStarted().getTime()) >
                    (1000 * testTrace.getTest().getTimeLimit() + EXPIRATION_THRESHOLD)) {
                endTest(testTrace, false);
            }
        }

        logger.debug("< processExpiredTestTraces()");
    }

    private TestTrace createTestTrace(Assignment assignment) {
        TestTrace testTrace = new TestTrace();
        testTrace.setAssignment(assignment);
        testTrace.setTest(assignment.getTest());
        testTrace.setStudent(assignment.getStudent());
        testTrace = makePersistent(testTrace);

        createTestTraceElements(testTrace);

        return testTrace;
    }

    @SuppressWarnings("unchecked")
    private void createTestTraceElements(TestTrace testTrace) {
        Test test = testTrace.getTest();
//        Query q = getEntityManager().createQuery("select d from Test t join t.difficultyLevels d where t = :test");
        Query q = getEntityManager().createQuery("select d from Test t join t.difficultyLevels d where t = :test order by d.difficulty.code");
        q.setParameter("test", test);
        List<TestDifficulty> difficultyLevels = q.getResultList();
//        Collections.sort(difficultyLevels);
        for (TestDifficulty testDifficulty : difficultyLevels) {
            createTestTraceElements(testTrace, testDifficulty);
        }
    }

    private int getPassScore(TestTrace testTrace, Integer part) {
        int passScore = 0;
        for (TestDifficulty testDifficulty : testTrace.getTest().getDifficultyLevels()) {
            if (testDifficulty.getDifficulty().getId().equals(part)) {
                passScore = testDifficulty.getPassScore();
                break;
            }
        }
        return passScore;
    }

    @SuppressWarnings("unchecked")
    private void createTestTraceElements(TestTrace testTrace, TestDifficulty testDifficulty) {
        Test test = testTrace.getTest();
        Query q;
        Set<Topic> topics = test.getTopics();
        if ((topics != null) && (!topics.isEmpty())) {
            q = getEntityManager().createQuery("select q.id from Question q, Test tt join tt.topics t where q.topic = t and tt = :test and q.difficulty = :difficulty and q.active = true");
            q.setParameter("test", test);
            q.setParameter("difficulty", testDifficulty.getDifficulty());
        } else {
            q = getEntityManager().createQuery("select q.id from Question q where q.topic.category = :category and q.difficulty = :difficulty and q.active = true");
            q.setParameter("category", test.getCategory());
            q.setParameter("difficulty", testDifficulty.getDifficulty());
        }
        List<Integer> questionIds = q.getResultList();
        Collections.shuffle(questionIds);
        int questionsCount = testDifficulty.getQuestionsCount();
        if (questionsCount < questionIds.size()) {
            questionIds = questionIds.subList(0, questionsCount);
        }
        createTestTraceElements(testTrace, questionIds, testDifficulty.getDifficulty().getId());
    }

    private void createTestTraceElements(TestTrace testTrace, List<Integer> questionIds, Integer part) {
        for (Integer questionId : questionIds) {
            Question q = getEntityManager().find(Question.class, questionId);
            QuestionTrace qt = new QuestionTrace();
            qt.setTestTrace(testTrace);
            qt.setQuestion(q);
            qt.setPart(part);
            getEntityManager().persist(qt);
        }
    }
}
