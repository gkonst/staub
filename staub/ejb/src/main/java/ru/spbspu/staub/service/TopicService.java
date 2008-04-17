package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Topic;

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
     * Returns a list of topics associated with a category.
     *
     * @param category the category
     *
     * @return the list of topics
     */
    List<Topic> getTopics(Category category);
}
