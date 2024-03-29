package ru.spbspu.staub.service;

import ru.spbspu.staub.data.question.TopicDataType;
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
    List<Topic> find(Category category);

    /**
     * Searches topics of a specified category.
     *
     * @param formProperties the form properties
     * @param category       the category
     *
     * @return the table of results
     */
    FormTable find(FormProperties formProperties, Category category);

    /**
     * Checks whether a code is not assigned to any topic.
     *
     * @param code the code
     *
     * @return <code>true</code> if the code is unique; <code>false</code> otherwise
     */
    boolean isCodeUnique(String code);

    /**
     * Imports a topic.
     *
     * @param topicData the data to import
     * @param category  the parent entity
     *
     * @return the target entity
     */
    Topic importTopic(TopicDataType topicData, Category category);

    /**
     * Exports a topic.
     *
     * @param id the identification number
     *
     * @return the exported data
     */
    TopicDataType exportTopic(Integer id);
}
