package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.Test;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * The <code>AssignmentServiceBean</code> is a stateless EJB service to manipulate <code>Assignment</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Name("assignmentService")
@AutoCreate
@Stateless
public class AssignmentServiceBean extends GenericServiceBean<Assignment, Integer> implements AssignmentService {
    public Assignment findAssignment(Student student, Test test) {
        Query q = getEntityManager()
                .createQuery("select a from Assignment a where a.student = :student and a.test = :test");
        q.setParameter("student", student);
        q.setParameter("test", test);
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

    public void removeAssignment(Student student, Test test) {
        Assignment assignment = findAssignment(student, test);
        if (assignment != null) {
            getEntityManager().remove(assignment);
        }
    }
}
