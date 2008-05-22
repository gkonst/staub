package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * The <code>AssignmentService</code> is a stateless EJB service to manipulate <code>Assignment</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface AssignmentService extends GenericService<Assignment, Integer> {
    /**
     * Searches an assignments to a specified test.
     *
     * @param formProperties the form properties
     * @param test           the test
     *
     * @return the table of results
     */
    FormTable find(FormProperties formProperties, Test test);

    /**
     * Searches an active assignments of a student.
     *
     * @param formProperties the form properties
     * @param student        the student
     *
     * @return the table of results
     */
    FormTable find(FormProperties formProperties, Student student);

    /**
     * Searches an active assignments of a student.
     *
     * @param student the studemt
     *
     * @return the list of assignments
     */
    List<Assignment> find(Student student);

    /**
     * Counts assignments to a specific test.
     *
     * @param test the test
     *
     * @return the number of assignments
     */
    long countAssignments(Test test);

    /**
     * Counts assignments of a student.
     *
     * @param student the student
     *
     * @return the number of assignments
     */
    long countAssignments(Student student);

    /**
     * Assigns students for specific test.
     *
     * @param testId     specific test identifier
     * @param studentIds list or students identifiers
     * @param testBegin  the test begin
     * @param testEnd    the test end
     */
    void assignTest(Integer testId, List<Integer> studentIds, Date testBegin, Date testEnd);

    /**
     * Removes an expired assignments. Creates test trace records as necessary.
     */
    void processExpiredAssignments();
}
