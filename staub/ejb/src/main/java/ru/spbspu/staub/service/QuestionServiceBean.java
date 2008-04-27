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
        StringBuilder queryString = new StringBuilder()
                .append("select o from ")
                .append(Question.class.getName())
                .append(" o where o.active = true");
        List<Question> result = getEntityManager().createQuery(queryString.toString()).getResultList();
        logger.debug("<<< Finding all...Ok(#0 found)", result.size());
        return result;
    }

    @Override
    public FormTable findAll(FormProperties formProperties) {
        StringBuilder queryString = new StringBuilder()
                .append("select o from ")
                .append(Question.class.getName())
                .append(" o where o.active = true");
        return findAll(queryString.toString(), formProperties, new HashMap<String, Object>(0));
    }

    @Override
    public void remove(Question question) {
        logger.debug("> remove(question=#0)", question);

        Question q = getEntityManager().merge(question);
        if (questionTraceService.countQuestionTraces(q) == 0) {
            logger.debug("*  No related QuestionTrace entities found.");
            getEntityManager().remove(q);
            logger.debug("*  Question removed from a database.");
        } else {
            logger.debug("*  Related QuestionTrace entities exist.");
            q.setActive(false);
            q = getEntityManager().merge(q);
            logger.debug("*  Question marked inactive.");
        }

        logger.debug("< remove(question=#0)", q);
    }
}
