package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.*;

import javax.ejb.Stateless;
import java.util.Date;

/**
 * Stateless EJB Service for manipulations with <code>TestTrace</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testTraceService")
@AutoCreate
@Stateless
public class TestTraceServiceBean extends GenericServiceBean<TestTrace, Integer> implements TestTraceService {
    /**
     * {@inheritDoc}
     */
    public TestTrace getTestTrace(Test test, String sessionId, User user) {
        // TODO implement
        // --> mock start
        TestTrace testTrace = new TestTrace();
        testTrace.setTest(test);
        testTrace.setUser(user);
        testTrace.setSessionId(sessionId);
        testTrace.setStarted(new Date());
        testTrace = makePersistent(testTrace);
        test = getEntityManager().find(Test.class, test.getId());
        for (Question question : test.getQuestions()) {
            QuestionTrace questionTrace = new QuestionTrace();
            questionTrace.setQuestion(question);
            questionTrace.setTestTrace(testTrace);
            getEntityManager().merge(questionTrace);
        }
        // <-- mock end
        return testTrace;
    }

    /**
     * {@inheritDoc}
     */
    public TestTrace startTest(TestTrace testTrace) {
        // TODO implement
        return testTrace;
    }

    /**
     * {@inheritDoc}
     */
    public TestTrace saveAndCheckTest(TestTrace testTrace) {
        // TODO implement
        // --> mock start
        testTrace.setFinished(new Date());
        testTrace = makePersistent(testTrace);
        // <-- mock end
        return testTrace;
    }
}
