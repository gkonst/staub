package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import java.util.List;

/**
 * The <code>StudentService</code> is a stateless EJB service to manipulate <code>Student</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface StudentService extends GenericService<Student, Integer> {
    /**
     * Searches a student with the name and code values specified.
     *
     * @param name the name
     * @param code the code
     *
     * @return the User entity if found; <code>null</code> otherwise
     */
    Student findByNameAndCode(String name, String code);

    /**
     * Searches a students from the specified group.
     *
     * @param group the group
     *
     * @return the search results
     */
    List<Student> findStudents(Group group);

    /**
     * Searches a students from the specified group.
     *
     * @param formProperties the form properties
     * @param group          the group
     *
     * @return the table of results
     */
    FormTable findStudents(FormProperties formProperties, Group group);

    /**
     * Counts students from a specified group.
     *
     * @param group the group
     *
     * @return the number of sudents
     */
    long countStudents(Group group);
}
