package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import java.util.Date;

/**
 * Local Interface for <code>TestTraceServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Local
public interface TestTraceService extends GenericService<TestTrace, Integer> {
    /**
     * Searches test matching specified criteria.
     *
     * @param formProperties the form properties
     * @param group          the group
     * @param student        the student
     * @param discipline     the discipline
     * @param begin          the begin
     * @param end            the end
     *
     * @return the table of results
     */
    FormTable find(FormProperties formProperties, Group group, Student student, Discipline discipline,
                             Date begin, Date end);

    /**
     * Counts test traces of a specific student.
     *
     * @param student the student
     *
     * @return the number of test traces
     */
    long count(Student student);

    /**
     * Counts test traces of a specific test.
     *
     * @param test the test
     *
     * @return the number of test traces
     */
    long count(Test test);

    /**
     * Returns a <code>TestTrace</code> instance for a test. Creates if necessary.
     *
     * @param assignment the assignment
     *
     * @return the <code>TestTrace</code> instance
     */
    TestTrace getTestTrace(Assignment assignment);

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
     * @param passed    <true>true</true> if the test should be marked as passed; <code>false</code> if the test should
     *                  be marked as failed
     *
     * @return updated instance
     */
    TestTrace endTest(TestTrace testTrace, boolean passed);

    /**
     * Validates questions from a given part.
     *
     * @param testTrace the <code>TestTrace</code> to process
     * @param partId    the identification number
     *
     * @return <code>true</code> if check succeeded; <code>false</code> otherwise
     */
    boolean checkPart(TestTrace testTrace, Integer partId);

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

    /**
     * Closes an expired test trace records.
     */
    void processExpiredTestTraces();
}
