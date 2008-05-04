package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The <code>AssignmentServiceBean</code> is a stateless EJB service to manipulate <code>Assignment</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Name("assignmentService")
@AutoCreate
@Stateless
public class AssignmentServiceBean extends GenericServiceBean<Assignment, Integer> implements AssignmentService {
    public FormTable findAssignments(FormProperties formProperties, Student student) {
        String query = "select a from Assignment a where a.student = :student and a.testBegin <= :currentDate and a.testEnd > :currentDate";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("student", student);
        parameters.put("currentDate", new Date());
        return findAll(query, formProperties, parameters);
    }

    @SuppressWarnings("unchecked")
    public List<Assignment> findAssignments(Student student) {
        Query q = getEntityManager().createQuery("select a from Assignment a where a.student = :student and a.testBegin <= :currentDate and a.testEnd > :currentDate");
        q.setParameter("student", student);
        q.setParameter("currentDate", new Date());
        return q.getResultList();
    }

    public long countAssignments(Student student) {
        Query q = getEntityManager().createQuery("select count(a) from Assignment a where a.student = :student");
        q.setParameter("student", student);
        return (Long) q.getSingleResult();
    }

    public long countAssignments(Test test) {
        Query q = getEntityManager().createQuery("select count(a) from Assignment a where a.test = :test");
        q.setParameter("test", test);
        return (Long) q.getSingleResult();
    }

    public Assignment saveAssignment(Assignment assignment) {
        return makePersistent(assignment);
    }

    public void assignTest(Integer testId, List<Integer> studentIds) {
        Test test = getEntityManager().find(Test.class, testId);
        for (Integer studentId : studentIds) {
            Student student = getEntityManager().find(Student.class, studentId);

            Assignment assignment = new Assignment();
            assignment.setTest(test);
            assignment.setStudent(student);

            saveAssignment(assignment);
        }
    }
}
