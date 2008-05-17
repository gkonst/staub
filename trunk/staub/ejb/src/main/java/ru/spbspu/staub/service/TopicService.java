package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Topic;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import java.util.List;

/**
 * The <code>TopicService</code> is a stateless EJB service to manipulate <code>Topic</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface TopicService extends GenericService<Topic, Integer> {
    /**
     * Searches topics of a specified category.
     *
     * @param category the category
     *
     * @return the list of topics
     */
    List<Topic> findTopics(Category category);

    /**
     * Searches topics of a specified category.
     *
     * @param formProperties the form properties
     * @param category       the category
     *
     * @return the table of results
     */
    FormTable findTopics(FormProperties formProperties, Category category);

    /**
     * Checks whether a code is not assigned to any topic.
     *
     * @param code the code
     *
     * @return <code>true</code> if the code is unique; <code>false</code> otherwise
     */
    boolean isCodeUnique(String code);
}
