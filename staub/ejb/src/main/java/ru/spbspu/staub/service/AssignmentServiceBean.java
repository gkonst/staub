package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * The <code>AssignmentServiceBean</code> is a stateless EJB service to manipulate <code>Assignment</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Name("assignmentService")
@AutoCreate
@Stateless
public class AssignmentServiceBean extends GenericServiceBean<Assignment, Integer> implements AssignmentService {
    public FormTable findAssigned(FormProperties formProperties, Student student) {
        String query = "select a from Assignment a where a.student = :student";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("student", student);
        return findAll(query, formProperties, parameters);
    }

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

    @SuppressWarnings("unchecked")
    public List<Assignment> findAssignment(Student student) {
        Query q = getEntityManager().createQuery("select a from Assignment a where a.student = :student");
        q.setParameter("student", student);
        return q.getResultList();     
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
