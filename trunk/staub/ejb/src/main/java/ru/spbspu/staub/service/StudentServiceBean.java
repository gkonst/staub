package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The <code>StudentServiceBean</code> is a stateless EJB service to manipulate <code>Student</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Name("studentService")
@AutoCreate
@Stateless
public class StudentServiceBean extends GenericServiceBean<Student, Integer> implements StudentService {
    @EJB
    private AssignmentService assignmentService;

    @EJB
    private TestTraceService testTraceService;

    public Student findByNameAndCode(String name, String code) {
        Student student = null;
        try {
            Query q = getEntityManager().createQuery("select s from Student s where s.name = :name and s.code = :code and s.active = true");
            q.setParameter("name", name);
            q.setParameter("code", code);

            student = (Student) q.getSingleResult();
        } catch (NoResultException ex) {
            // do nothing
        }
        return student;
    }

    @SuppressWarnings("unchecked")
    public List<Student> findStudentsByGroup(Group group) {
        Query q = getEntityManager().createQuery("select s from Student s where s.group = :group and s.active = true");
        q.setParameter("group", group);
        return q.getResultList();
    }

    public FormTable findStudentsToAssign(FormProperties formProperties, Group group) {
        String query = "select s from Student s where s.group = :group and s.active = true";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("group", group);
        return findAll(query, formProperties, parameters);
    }

    public long countStudents(Group group) {
        Query q = getEntityManager().createQuery("select count(s) from Student s where s.group = :group");
        q.setParameter("group", group);
        return (Long) q.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Student> findAll() {
        logger.debug(">>> Finding all(entity=#0)...", Student.class.getName());
        StringBuilder queryString = new StringBuilder()
                .append("select o from ")
                .append(Student.class.getName())
                .append(" o where o.active = true");
        List<Student> result = getEntityManager().createQuery(queryString.toString()).getResultList();
        logger.debug("<<< Finding all...Ok(#0 found)", result.size());
        return result;
    }

    @Override
    public FormTable findAll(FormProperties formProperties) {
        StringBuilder queryString = new StringBuilder()
                .append("select o from ")
                .append(Student.class.getName())
                .append(" o where o.active = true");
        return findAll(queryString.toString(), formProperties, new HashMap<String, Object>(0));
    }

    @Override
    public void remove(Student student) throws RemoveException {
        logger.debug("> remove(student=#0)", student);

        Student s = getEntityManager().merge(student);
        if (assignmentService.countAssignments(s) == 0) {
            logger.debug("*  No related Assignment entities found.");
            if (testTraceService.countTestTraces(s) == 0) {
                logger.debug("*  No related TestTrace entities found.");
                getEntityManager().remove(s);
                logger.debug("*  Student removed from a database.");
            } else {
                logger.debug("*  Related TestTrace entities exist.");
                s.setActive(false);
                s = getEntityManager().merge(s);
                logger.debug("*  Student marked inactive.");
            }
        } else {
            logger.debug("*  Related Assignment entities exist.");
            throw new RemoveException("Could not remove a Student.");
        }

        logger.debug("< remove(student=#0)", s);
    }
}
