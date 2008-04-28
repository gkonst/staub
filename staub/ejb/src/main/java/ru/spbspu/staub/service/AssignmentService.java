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
     * Searches tests assigned to a student.
     *
     * @param formProperties the form properties
     * @param student        the student
     *
     * @return the result table
     */
    FormTable findAssigned(FormProperties formProperties, Student student);

    /**
     * Searches an assignments.
     *
     * @param student the studemt
     *
     * @return the list of assignments
     */
    List<Assignment> findAssignment(Student student);

    /**
     * Searches an assignments.
     *
     * @param test the test
     *
     * @return the list of assignments
     */
    List<Assignment> findAssignment(Test test);

    /**
     * Searches an assignment.
     *
     * @param student the student
     * @param test    the test
     *
     * @return the assignment if found; <code>null</code> otherwise
     */
    Assignment findAssignment(Student student, Test test);

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
     * Removes all assignments of a specific student.
     *
     * @param student the student
     */
    void removeAssignments(Student student);

    /**
     * Removes all assignments to a specific test.
     *
     * @param test the test
     */
    void removeAssignments(Test test);

    /**
     * Deletes information about an assignment. Returns silently if no assignment exists.
     *
     * @param student the student
     * @param test    the test
     */
    void removeAssignment(Student student, Test test);

    /**
     * Assigns students for specific test.
     *
     * @param testId     specific test identifier
     * @param studentIds list or students identifiers
     */
    void assignTest(Integer testId, List<Integer> studentIds);
}
