package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Topic;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import java.util.Date;

/**
 * The <code>GroupService</code> is a stateless EJB service to manipulate <code>Group</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface GroupService extends GenericService<Group, Integer> {
    /**
     * Checks whether a name is not assigned to any group.
     *
     * @param name the name
     *
     * @return <code>true</code> if the name is unique; <code>false</code> otherwise
     */
    boolean isNameUnique(String name);

    /**
     * Returns statistical data for a group matching specified criteria.
     *
     * @param formProperties the form properties
     * @param group          the group
     * @param discipline     the discipline
     * @param category       the category
     * @param topic          the topic
     * @param begin          the begin
     * @param end            the end
     *
     * @return the table of results
     */
    FormTable getStatistics(FormProperties formProperties, Group group, Discipline discipline, Category category,
                            Topic topic, Date begin, Date end);
}