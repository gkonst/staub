package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.entity.User;

import javax.ejb.Local;

/**
 * Local Interface for <code>TestTraceServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface TestTraceService extends GenericService<TestTrace, Integer> {
    /**
     * Checks is <code>TestTrace</code> already presents for
     * this user and this sessionId, if not generates it with <code>QuestionTrace</code> entites.
     *
     * @param test specific <code>Test</code> instance
     * @param sessionId <code>HttpSession</code> identifier
     * @param user current user
     * @return created or found <code>TestTrace</code>
     */
    TestTrace getTestTrace(Test test, String sessionId, User user);

    /**
     * Starts test (fill started field).
     *
     * @param testTrace <code>TestTrace</code> to start
     * @return started <code>TestTrace</code>
     */
    TestTrace startTest(TestTrace testTrace);

    TestTrace saveAndCheckTest(TestTrace testTrace);
}
