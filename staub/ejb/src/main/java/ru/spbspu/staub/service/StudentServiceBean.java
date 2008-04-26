package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
}
