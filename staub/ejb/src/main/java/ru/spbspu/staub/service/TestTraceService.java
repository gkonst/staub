package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.TestTrace;

import javax.ejb.Local;

/**
 * Local Interface for <code>TestTraceServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Local
public interface TestTraceService extends GenericService<TestTrace, Integer> {
    /**
     * Returns a <code>TestTrace</code> for a test. Creates if necessary.
     *
     * @param test    specific <code>Test</code> instance
     * @param student the student
     *
     * @return created or found <code>TestTrace</code>
     */
    TestTrace getTestTrace(Test test, Student student);

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
     * @param partId    the identification number
     * @param passScore the minimal score to pass
     *
     * @return <code>true</code> if check succeeded; <code>false</code> otherwise
     */
    boolean checkPart(TestTrace testTrace, Integer partId, int passScore);

    /**
     * Validates the whole TestTrace. No actual validation of QuestionTrace elements is performed. Field
     * QuestionTrace.correct is analyzed, null value is treated as false.
     * <p/>
     * This method is called by {@link #endTest(TestTrace)}.
     *
     * @param testTrace the <code>TestTrace</code> to process
     *
     * @return the updated <code>TestTrace</code> entity
     */
    TestTrace checkTestTrace(TestTrace testTrace);

    /**
     * Returns a number of answers marked as correct.
     *
     * @param testTrace the test trace to analyze
     *
     * @return the answers count
     */
    long getCorrectCount(TestTrace testTrace);

    /**
     * Returns a number of trace element.
     *
     * @param testTrace the test trace to analyze
     *
     * @return the element count
     */
    long getCount(TestTrace testTrace);
}
