package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.exception.RemoveException;

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
    public FormTable find(FormProperties formProperties, Test test) {
        String query = "select a from Assignment a where a.test = :test";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("test", test);
        return findAll(query, formProperties, parameters);
    }

    public FormTable find(FormProperties formProperties, Student student) {
        String query = "select a from Assignment a where a.student = :student and a.testBegin <= :currentDate and a.testEnd > :currentDate";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("student", student);
        parameters.put("currentDate", new Date());
        return findAll(query, formProperties, parameters);
    }

    @SuppressWarnings("unchecked")
    public List<Assignment> find(Student student) {
        Query q = getEntityManager().createQuery("select a from Assignment a where a.student = :student and a.testBegin <= :currentDate and a.testEnd > :currentDate");
        q.setParameter("student", student);
        q.setParameter("currentDate", new Date());
        return q.getResultList();
    }

    public long count(Student student) {
        Query q = getEntityManager().createQuery("select count(a) from Assignment a where a.student = :student");
        q.setParameter("student", student);
        return (Long) q.getSingleResult();
    }

    public long count(Test test) {
        Query q = getEntityManager().createQuery("select count(a) from Assignment a where a.test = :test");
        q.setParameter("test", test);
        return (Long) q.getSingleResult();
    }

    public void assignTest(Integer testId, List<Integer> studentIds, Date testBegin, Date testEnd) {
        Test test = getEntityManager().find(Test.class, testId);
        for (Integer studentId : studentIds) {
            Student student = getEntityManager().find(Student.class, studentId);

            Assignment assignment = new Assignment();
            assignment.setTest(test);
            assignment.setStudent(student);
            assignment.setTestBegin(testBegin);
            assignment.setTestEnd(testEnd);

            makePersistent(assignment);
        }
    }

    @SuppressWarnings("unchecked")
    public void processExpiredAssignments() {
        Query q = getEntityManager().createQuery("select a from Assignment a where a.testEnd <= :currentDate and a.testTrace is null");
        q.setParameter("currentDate", new Date());

        for (Assignment assignment : (List<Assignment>) q.getResultList()) {
            createTestTrace(assignment);

            try {
                remove(assignment);
            } catch (RemoveException e) {
                // should not happen
            }
        }
    }

    @Override
    public void remove(Assignment assignment) throws RemoveException {
        logger.debug("> remove(Assignment=#0)", assignment);

        Assignment a = getEntityManager().merge(assignment);
        getEntityManager().remove(a);
        logger.debug("*  Assignment removed from a database.");

        logger.debug("< remove(Assignment)");
    }

    private void createTestTrace(Assignment assignment) {
        TestTrace testTrace = new TestTrace();
        testTrace.setTest(assignment.getTest());
        testTrace.setStudent(assignment.getStudent());
        testTrace.setScore(0);
        testTrace.setTestPassed(false);
        testTrace.setStarted(assignment.getTestEnd());
        testTrace.setFinished(new Date(0));

        getEntityManager().persist(testTrace);
    }
}
