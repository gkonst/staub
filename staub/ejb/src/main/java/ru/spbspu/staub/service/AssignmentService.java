package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.Test;

import javax.ejb.Local;

/**
 * The <code>AssignmentService</code> is a stateless EJB service to manipulate <code>Assignment</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface AssignmentService extends GenericService<Assignment, Integer> {
    /**
     * Searches an assignment.
     *
     * @param test    the student
     * @param student the test
     *
     * @return the assignment if found; <code>null</code> otherwise
     */
    Assignment findAssignment(Student student, Test test);

    /**
     * Creates or updates an assignment.
     *
     * @param assignment the assignment to process
     *
     * @return the updated instance
     */
    Assignment saveAssignment(Assignment assignment);

    /**
     * Deletes information about an assignment. Returns silently if no assignment exists.
     *
     * @param student the student
     * @param test    the test
     */
    void removeAssignment(Student student, Test test);
}
