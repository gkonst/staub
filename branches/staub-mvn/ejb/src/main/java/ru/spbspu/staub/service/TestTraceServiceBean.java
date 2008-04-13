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

/**
 * Stateless EJB Service for manipulations with <code>TestTrace</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testTraceService")
@AutoCreate
@Stateless
public class TestTraceServiceBean extends GenericServiceBean<TestTrace, Integer> implements TestTraceService {
    @EJB
    QuestionTraceService questionTraceService;

    @EJB
    UserHistoryService userHistoryService;

    @EJB
    AssignmentService assignmentService;

    @SuppressWarnings("unchecked")
    public TestTrace getTestTrace(Test test, User user, String sessionId) {
        Query q = getEntityManager().createQuery("select t from TestTrace t where t.user = :user and t.sessionId = :sessionId and t.finished is null");
        q.setParameter("user", user);
        q.setParameter("sessionId", sessionId);

        TestTrace testTrace = null;
        try {
            testTrace = (TestTrace) q.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        if (testTrace == null) {
            testTrace = createTestTrace(test, user, sessionId);
        }

        return testTrace;
    }

    public TestTrace startTest(TestTrace testTrace) {
        // TODO: Should this code be here?
        Assignment assignment = assignmentService.findAssignment(testTrace.getUser(), testTrace.getTest());
        assignment.setTestStarted(true);
        assignmentService.saveAssignment(assignment);

        testTrace.setStarted(new Date());
        return makePersistent(testTrace);
    }

    public TestTrace endTest(TestTrace testTrace) {
        checkTestTrace(testTrace);

        testTrace.setFinished(new Date());
        makePersistent(testTrace);

        userHistoryService.populateUserHistory(testTrace);

        assignmentService.removeAssignment(testTrace.getUser(), testTrace.getTest());

        return testTrace;
    }

    public TestTrace checkTestTrace(TestTrace testTrace) {
        Query q = getEntityManager().createQuery("select count(q) from QuestionTrace q where q.correct = true and q.testTrace = :testTrace");
        q.setParameter("testTrace", testTrace);
        long correctCount = (Long) q.getSingleResult();

        Test test = testTrace.getTest();

        int questionsCount;
        if (test.getSelectorType() == SelectorEnum.ALL) {
            questionsCount = test.getQuestionsCount();
        } else {
            questionsCount = test.getSelectorCount();
        }        

        int score = (int) correctCount * 100 / questionsCount;
        testTrace.setScore(score);
        testTrace.setTestPassed(score >= test.getPassScore());

        makePersistent(testTrace);

        return testTrace;
    }

    @SuppressWarnings("unchecked")
    public boolean checkPart(TestTrace testTrace, Integer part) {
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

        Test test = testTrace.getTest();

        return ((correctCount * 100 / questionsCount) >= test.getPassScore());
    }

    private TestTrace createTestTrace(Test test, User user, String sessionId) {
        TestTrace testTrace = new TestTrace();
        testTrace.setTest(test);
        testTrace.setUser(user);
        testTrace.setSessionId(sessionId);
        testTrace = makePersistent(testTrace);

        createTestTraceElements(testTrace);

        return testTrace;
    }

    @SuppressWarnings("unchecked")
    private void createTestTraceElements(TestTrace testTrace) {
        Test test = testTrace.getTest();
        SelectorEnum selectorType = test.getSelectorType();
        switch (selectorType) {
            case ALL:
                createFromAll(testTrace);
                break;
            case COUNT:
                createFromCount(testTrace);
                break;
                // TODO: Implement the following selector types.
                /*case CATEGORY_COUNT:
                createFromCategoryCount(testTrace);
                break;
            case DIFFICULTY_COUNT:
                createFromDifficultyCount(testTrace);
                break;
            case DISCIPLINE_COUNT:
                createFromDisciplineCount(testTrace);*/
        }
    }

    @SuppressWarnings("unchecked")
    private void createFromAll(TestTrace testTrace) {
        Test test = testTrace.getTest();
        Query q = getEntityManager().createQuery("select q.id from Question q, TestQuestion tq, Test t where q.id = tq.fkQuestion and tq.fkTest = t.id and t = :test");
        q.setParameter("test", test);
        List<Integer> questionIds = q.getResultList();
        // TODO: Consider performance impact.
        Collections.shuffle(questionIds);
        createTestTraceElements(testTrace, questionIds, 1);
    }

    @SuppressWarnings("unchecked")
    private void createFromCount(TestTrace testTrace) {
        Test test = testTrace.getTest();
        Query q = getEntityManager().createQuery("select q.id from Question q, TestQuestion tq, Test t where q.id = tq.fkQuestion and tq.fkTest = t.id and t = :test");
        q.setParameter("test", test);
        List<Integer> questionIds = q.getResultList();
        // TODO: Consider performance impact.
        Collections.shuffle(questionIds);
        questionIds = questionIds.subList(0, test.getSelectorCount());
        createTestTraceElements(testTrace, questionIds, 0);
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
