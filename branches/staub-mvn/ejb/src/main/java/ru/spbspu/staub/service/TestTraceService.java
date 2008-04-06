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
     * @param test      specific <code>Test</code> instance
     * @param user      current user
     * @param sessionId <code>HttpSession</code> identifier
     *
     * @return created or found <code>TestTrace</code>
     */
    TestTrace getTestTrace(Test test, User user, String sessionId);

    /**
     * Starts test (fills started field).
     *
     * @param testTrace <code>TestTrace</code> to start
     *
     * @return started <code>TestTrace</code>
     */
    TestTrace startTest(TestTrace testTrace);

    /**
     * Ends test (fills finished field).
     * Forms <code>UserHistory</code>, etc.
     *
     * @param testTrace ended test trace
     *
     * @return updated instance
     */
    TestTrace endTest(TestTrace testTrace);

    /**
     * Validates questions from a given part.
     *
     * @param testTrace the <code>TestTrace</code> to process
     * @param part      the identification number
     *
     * @return <code>true</code> if check succeeded; <code>false</code> otherwise
     */
    boolean checkPart(TestTrace testTrace, Integer part);

    /**
     * Validates the whole TestTrace. No actual validation of QuestionTrace elements is performed. Field
     * QuestionTrace.correct is analyzed, null value is treated as false.
     * <p>
     * This method is called by {@link #endTest(TestTrace)}.
     *
     * @param testTrace the <code>TestTrace</code> to process
     * @return the updated <code>TestTrace</code> entity
     */
    TestTrace checkTestTrace(TestTrace testTrace);
}
