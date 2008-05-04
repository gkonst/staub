package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import java.util.List;

/**
 * The <code>AssignmentService</code> is a stateless EJB service to manipulate <code>Assignment</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface AssignmentService extends GenericService<Assignment, Integer> {
    /**
     * Searches an active assignments of a student.
     *
     * @param formProperties the form properties
     * @param student        the student
     *
     * @return the table of results
     */
    FormTable findAssignments(FormProperties formProperties, Student student);

    /**
     * Searches an active assignments of a student.
     *
     * @param student the studemt
     *
     * @return the list of assignments
     */
    List<Assignment> findAssignments(Student student);

    /**
     * Counts assignments for a specific test.
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
     * Creates or updates an assignment.
     *
     * @param assignment the assignment to process
     *
     * @return the updated instance
     */
    Assignment saveAssignment(Assignment assignment);

    /**
     * Assigns students for specific test.
     *
     * @param testId     specific test identifier
     * @param studentIds list or students identifiers
     */
    void assignTest(Integer testId, List<Integer> studentIds);
}
