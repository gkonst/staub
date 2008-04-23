package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Topic;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

/**
 * The <code>TopicServiceBean</code> is a stateless EJB service to manipulate <code>Topic</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Name("topicService")
@AutoCreate
@Stateless
public class TopicServiceBean extends GenericServiceBean<Topic, Integer> implements TopicService {
    @SuppressWarnings("unchecked")
    public List<Topic> getTopics(Category category) {
        Query q = getEntityManager().createQuery("select t from Topic t where t.category = :category");
        q.setParameter("category", category);
        return q.getResultList();
    }
}
