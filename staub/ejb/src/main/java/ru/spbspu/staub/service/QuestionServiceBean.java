package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Stateless EJB Service for manipulations with <code>Question</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionService")
@AutoCreate
@Stateless
public class QuestionServiceBean extends GenericServiceBean<Question, Integer> implements QuestionService {
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

        long count = getQuestionTracesCount(q);
        if (count == 0) {
            logger.debug("*  No relations found.");
            getEntityManager().remove(q);
        } else {
            logger.debug("*  Relations exist.");
            q.setActive(false);
            q = getEntityManager().merge(q);
        }

        logger.debug("< remove(question=#0)", q);
    }

    private long getQuestionTracesCount(Question question) {
        Query q = getEntityManager().createQuery("select count(q) from QuestionTrace q where q.question = :question");
        q.setParameter("question", question);
        return (Long) q.getSingleResult();
    }
}
