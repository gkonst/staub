package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.interceptors.CallLogger;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.interceptor.Interceptors;

/**
 * The <code>AssignmentServiceBean</code> is a stateless EJB service to manipulate <code>Assignment</code> entity.
 *
 * @author Alexander V. Elagin
 */
@Name("assignmentService")
@AutoCreate
@Interceptors({CallLogger.class})
@Stateless
public class AssignmentServiceBean extends GenericServiceBean<Assignment, Integer> implements AssignmentService {
    public Assignment findAssignment(User user, Test test) {
        Query q = getEntityManager().createQuery("select a from Assignment a where a.fkUser = :userId and a.fkTest = :testId");
        q.setParameter("userId", user.getId());
        q.setParameter("testId", test.getId());
        Assignment assignment = null;
        try {
            assignment = (Assignment) q.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }
        return assignment;
    }

    public Assignment saveAssignment(Assignment assignment) {
        return makePersistent(assignment);
    }

    public void removeAssignment(User user, Test test) {
        Assignment assignment = findAssignment(user, test);
        if (assignment != null) {
            getEntityManager().remove(assignment);
        }
    }
}
