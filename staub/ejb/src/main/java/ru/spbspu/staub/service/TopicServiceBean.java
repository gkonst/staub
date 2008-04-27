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

    @Override
    public void remove(Topic topic) {
        logger.debug("> remove(topic=#0)", topic);

        Topic t = getEntityManager().merge(topic);

        long count = getQuestionsCount(t) + getTestsCount(t);
        if (count == 0) {
            logger.debug("*  No relations found.");
        } else {
            logger.debug("*  Relations exist.");
            throw new IllegalArgumentException("Could not remove a topic.");
        }

        getEntityManager().remove(t);

        logger.debug("< remove(topic=#0)", topic);
    }

    private long getQuestionsCount(Topic topic) {
        Query q = getEntityManager().createQuery("select count(q) from Question q where q.topic = :topic");
        q.setParameter("topic", topic);
        return (Long) q.getSingleResult();
    }

    private long getTestsCount(Topic topic) {
        Query q = getEntityManager().createQuery("select count(t) from Test t join t.topics tt where tt = :topic");
        q.setParameter("topic", topic);
        return (Long) q.getSingleResult();
    }
}
