package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stateless EJB Service for manipulations with <code>Question</code> entity.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Name("questionService")
@AutoCreate
@Stateless
public class QuestionServiceBean extends GenericServiceBean<Question, Integer> implements QuestionService {
    @EJB
    QuestionTraceService questionTraceService;

    public FormTable findQuestions(FormProperties formProperties, Discipline discipline, Category category, Topic topic,
                                   Difficulty difficulty, Integer questionId) {
        logger.debug("> findQuestions(FormProperties=#0, Discipline=#1, Category=#2, Topic=#3, Difficulty=#4, Integer=#5)",
                formProperties, discipline, category, topic, difficulty, questionId);

        StringBuilder query = new StringBuilder();
        query.append("select q from Question q");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (questionId != null) {
            query.append(" where cast(q.id as string) like :questionId and");
            parameters.put("questionId", questionId.toString() + '%');
        } else if (topic != null) {
            query.append(" where q.topic = :topic and");
            parameters.put("topic", topic);
        } else if (category != null) {
            query.append(", Category c join c.topics t where q.topic = t and c = :category and");
            parameters.put("category", category);
        } else if (discipline != null) {
            query.append(", Discipline d join d.categories c join c.topics t where q.topic = t and d = :discipline and");
            parameters.put("discipline", discipline);
        } else {
            query.append(" where");
        }

        query.append(" q.active = true");

        if (difficulty != null) {
            query.append(" and q.difficulty = :difficulty");
            parameters.put("difficulty", difficulty);
        }

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< findQuestions(FormProperties, Discipline, Category, Topic, Difficulty, Integer)");

        return formTable;
    }

    public long countQuestions(Category category) {
        Query q = getEntityManager().createQuery("select count(q) from Category c join c.topics t, Question q where q.topic = t and c = :category");
        q.setParameter("category", category);
        return (Long) q.getSingleResult();
    }

    public long countQuestions(Difficulty difficulty) {
        Query q = getEntityManager().createQuery("select count(q) from Question q where q.difficulty = :difficulty");
        q.setParameter("difficulty", difficulty);
        return (Long) q.getSingleResult();
    }

    public long countQuestions(Discipline discipline) {
        Query q = getEntityManager().createQuery("select count(q) from Discipline d join d.categories c join c.topics t, Question q where q.topic = t and d = :discipline");
        q.setParameter("discipline", discipline);
        return (Long) q.getSingleResult();
    }

    public long countQuestions(Topic topic) {
        Query q = getEntityManager().createQuery("select count(q) from Question q where q.topic = :topic");
        q.setParameter("topic", topic);
        return (Long) q.getSingleResult();
    }

    public Question saveQuestion(Question question, User user) {
        if (question.getId() == null) {
            question.setActive(true);
            question.setCreatedBy(user.getUsername());
            question.setCreated(new Date());
        } else {
            question.setModifiedBy(user.getUsername());
            question.setModified(new Date());
        }
        return makePersistent(question);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> findAll() {
        logger.debug(">>> Finding all(entity=#0)...", Question.class.getName());
        StringBuilder queryString = new StringBuilder().append("select q from Question q where q.active = true");
        List<Question> result = getEntityManager().createQuery(queryString.toString()).getResultList();
        logger.debug("<<< Finding all...Ok(#0 found)", result.size());
        return result;
    }

    @Override
    public FormTable findAll(FormProperties formProperties) {
        StringBuilder queryString = new StringBuilder().append("select q from Question q where q.active = true");
        return findAll(queryString.toString(), formProperties, new HashMap<String, Object>(0));
    }

    @Override
    public void remove(Question question) {
        logger.debug("> remove(Question=#0)", question);

        Question q = getEntityManager().merge(question);
        if (questionTraceService.countQuestionTraces(q) == 0) {
            logger.debug("*  No related QuestionTrace entities found.");
            getEntityManager().remove(q);
            logger.debug("*  Question removed from a database.");
        } else {
            logger.debug("*  Related QuestionTrace entities exist.");
            q.setActive(false);
            getEntityManager().merge(q);
            logger.debug("*  Question marked inactive.");
        }

        logger.debug("< remove(Question)");
    }
}
