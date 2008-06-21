package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

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
    public List<Student> find(Group group) {
        Query q = getEntityManager().createQuery("select s from Student s where s.group = :group and s.active = true");
        q.setParameter("group", group);
        return q.getResultList();
    }

    public FormTable find(FormProperties formProperties, Group group) {
        logger.debug("> find(FormProperties=#0, Group=#1)", formProperties, group);

        StringBuilder query = new StringBuilder();
        query.append("select s from Student s");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (group != null) {
            query.append(" where s.group = :group and");
            parameters.put("group", group);
        } else {
            query.append(" where");
        }

        query.append(" s.active = true");

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< find(FormProperties, Group)");

        return formTable;
    }

    public long count(Group group) {
        Query q = getEntityManager().createQuery("select count(s) from Student s where s.group = :group");
        q.setParameter("group", group);
        return (Long) q.getSingleResult();
    }

    public Student save(Student student) {
        logger.debug("> save(Student=#0)", student);

        student.setActive(true);
        Student result = getEntityManager().merge(student);

        logger.debug("< save(Student)");

        return result;
    }

    public boolean isCodeUnique(String code) {
        logger.debug("> isCodeUnique(String=#0)", code);

        Query q = getEntityManager().createQuery("select count(s) from Student s where s.code = :code");
        q.setParameter("code", code);

        boolean unique = ((Long) q.getSingleResult() == 0);
        if (unique) {
            logger.debug("*  Code is unique.");
        } else {
            logger.debug("*  Code is not unique.");
        }

        logger.debug("< isCodeUnique(String)");

        return unique;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Student> findAll() {
        logger.debug(">>> Finding all(entity=#0)...", Student.class.getName());
        StringBuilder queryString = new StringBuilder().append("select s from Student s where s.active = true");
        List<Student> result = getEntityManager().createQuery(queryString.toString()).getResultList();
        logger.debug("<<< Finding all...Ok(#0 found)", result.size());
        return result;
    }

    @Override
    public FormTable findAll(FormProperties formProperties) {
        StringBuilder queryString = new StringBuilder().append("select s from Student s where s.active = true");
        return findAll(queryString.toString(), formProperties, new HashMap<String, Object>(0));
    }

    @Override
    public void remove(Student student) throws RemoveException {
        logger.debug("> remove(Student=#0)", student);

        Student s = getEntityManager().merge(student);
        if (countAssignments(s) == 0) {
            logger.debug("*  No related Assignment entities found.");
            if (countStudents(s) == 0) {
                logger.debug("*  No related TestTrace entities found.");
                getEntityManager().remove(s);
                logger.debug("*  Student removed from a database.");
            } else {
                logger.debug("*  Related TestTrace entities exist.");
                s.setActive(false);
                getEntityManager().merge(s);
                logger.debug("*  Student marked inactive.");
            }
        } else {
            logger.debug("*  Related Assignment entities exist.");
            throw new RemoveException("Could not remove a Student.");
        }

        logger.debug("< remove(Student)");
    }

    private long countAssignments(Student student) {
        Query q = getEntityManager().createQuery("select count(a) from Assignment a where a.student = :student");
        q.setParameter("student", student);
        return (Long) q.getSingleResult();
    }

    private long countStudents(Student student) {
        Query q = getEntityManager().createQuery("select count(t) from TestTrace t where t.student = :student");
        q.setParameter("student", student);
        return (Long) q.getSingleResult();
    }
}
