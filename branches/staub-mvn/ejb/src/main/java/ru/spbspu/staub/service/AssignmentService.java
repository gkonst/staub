package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Assignment;

import javax.ejb.Local;

/**
 * The <code>AssignmentService</code> is a stateless EJB service to manipulate <code>Assignment</code> entity.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface AssignmentService extends GenericService<Assignment, Integer> {
    /**
     * Saves or updates an assignment.
     *
     * @param assignment the assignment to process
     *
     * @return the updated instance
     */
    Assignment saveAssignment(Assignment assignment);
}
