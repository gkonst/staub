package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Topic;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The <code>TopicServiceBean</code> is a stateless EJB service to manipulate <code>Topic</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Name("topicService")
@AutoCreate
@Stateless
public class TopicServiceBean extends GenericServiceBean<Topic, Integer> implements TopicService {
    @EJB
    private QuestionService questionService;

    @EJB
    private TestService testService;

    @SuppressWarnings("unchecked")
    public List<Topic> findTopics(Category category) {
        Query q = getEntityManager().createQuery("select t from Topic t where t.category = :category");
        q.setParameter("category", category);
        return q.getResultList();
    }

    public FormTable findTopics(FormProperties formProperties, Category category) {
        logger.debug("> findTopics(FormProperties=#0, Category=#1)", formProperties, category);

        StringBuilder query = new StringBuilder();
        query.append("select t from Topic t");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (category != null) {
            query.append(" where t.category = :category");
            parameters.put("category", category);
        }

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< findTopics(FormProperties, Category)");

        return formTable;
    }

    @Override
    public void remove(Topic topic) throws RemoveException {
        logger.debug("> remove(Topic=#0)", topic);

        Topic t = getEntityManager().merge(topic);
        if ((questionService.countQuestions(t) + testService.countTests(t)) == 0) {
            logger.debug("*  No related entities found.");
            getEntityManager().remove(t);
            logger.debug("*  Topic removed from a database.");
        } else {
            logger.debug("*  Related entities exist.");
            throw new RemoveException("Could not remove a topic.");
        }

        logger.debug("< remove(Topic)");
    }
}
