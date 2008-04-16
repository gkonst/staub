package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;

import javax.ejb.Local;

/**
 * The <code>AssignmentService</code> is a stateless EJB service to manipulate <code>Assignment</code> entity.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface AssignmentService extends GenericService<Assignment, Integer> {
    /**
     * Searches an assignment.
     *
     * @param test the user
     * @param user the test
     *
     * @return the assignment if found; <code>null</code> otherwise
     */
    Assignment findAssignment(User user, Test test);

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
     * @param user the user
     * @param test the test
     */
    void removeAssignment(User user, Test test);
}
