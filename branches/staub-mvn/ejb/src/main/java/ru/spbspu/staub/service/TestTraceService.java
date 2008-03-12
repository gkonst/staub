package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.TestTrace;

/**
 * Local Interface for <code>TestTraceServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
public interface TestTraceService extends GenericService<TestTrace, Integer> {
    /**
     * Checks is <code>TestTrace</code> already presents for
     * this user and this sessionId, if not generates it with <code>QuestionTrace</code> entites.
     *
     * @param testId    <code>Test</code> identifier
     * @param sessionId <code>HttpSession</code> identifier
     * @param username  current username
     * @return created or found <code>TestTrace</code>
     */
    TestTrace findTestTrace(Integer testId, String sessionId, String username);

    /**
     * Starts test (fill started field).
     *
     * @param testTrace <code>TestTrace</code> to start
     * @return started <code>TestTrace</code>
     */
    TestTrace startTest(TestTrace testTrace);
}
