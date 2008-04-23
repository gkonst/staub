package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @EJB
    AssignmentService assignmentService;

    @EJB
    QuestionTraceService questionTraceService;

    @EJB
    TestService testService;

    @SuppressWarnings("unchecked")
    public TestTrace getTestTrace(Test test, Student student) {
        Query q = getEntityManager().createQuery("select t from TestTrace t where t.student = :student and t.test = :test and t.finished is null");
        q.setParameter("student", student);
        q.setParameter("test", test);

        TestTrace testTrace = null;
        try {
            testTrace = (TestTrace) q.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        if (testTrace == null) {
            testTrace = createTestTrace(test, student);
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
        
        makePersistent(testTrace);

        assignmentService.removeAssignment(testTrace.getStudent(), testTrace.getTest());

        return testTrace;
    }

    @SuppressWarnings("unchecked")
    public boolean checkPart(TestTrace testTrace, Integer part, int passScore) {
        Query q = getEntityManager().createQuery("select q.id from QuestionTrace q where q.testTrace = :testTrace and q.part = :part");
        q.setParameter("testTrace", testTrace);
        q.setParameter("part", part);
        List<Integer> questionTraceIds = q.getResultList();

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

    private TestTrace createTestTrace(Test test, Student student) {
        TestTrace testTrace = new TestTrace();
        testTrace.setTest(test);
        testTrace.setStudent(student);
        testTrace = makePersistent(testTrace);

        createTestTraceElements(testTrace);

        return testTrace;
    }

    @SuppressWarnings("unchecked")
    private void createTestTraceElements(TestTrace testTrace) {
        Test test = testTrace.getTest();
        Query q = getEntityManager().createQuery("select d from Test t join t.difficultyLevels d where t = :test");
        q.setParameter("test", test);
        List<TestDifficulty> difficultyLevels = q.getResultList();
        Collections.sort(difficultyLevels);
        for (TestDifficulty testDifficulty : difficultyLevels) {
            createTestTraceElements(testTrace, testDifficulty);
        }
    }

    @SuppressWarnings("unchecked")
    private void createTestTraceElements(TestTrace testTrace, TestDifficulty testDifficulty) {
        Test test = testTrace.getTest();
        Query q;
        Set<Topic> topics = test.getTopics();
        if ((topics != null) && (!topics.isEmpty())) {
            q = getEntityManager().createQuery("select q.id from Question q, Test tt join tt.topics t where q.topic = t and tt = :test and q.difficulty = :difficulty");
            q.setParameter("test", test);
            q.setParameter("difficulty", testDifficulty.getDifficulty());
        } else {
            q = getEntityManager().createQuery("select q.id from Question q where q.topic.category = :category and q.difficulty = :difficulty");
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
