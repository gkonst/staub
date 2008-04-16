package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Stateless EJB Service for manipulations with <code>Question</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionService")
@AutoCreate
@Stateless
public class QuestionServiceBean extends GenericServiceBean<Question, Integer> implements QuestionService {
    public FormTable findAllForTest(FormProperties formProperties, Test test) {
        throw new IllegalStateException("Achtung, Partisanen!");
        /*String query = "select q from Question q, TestQuestion tq where q.id = tq.fkQuestion and tq.fkTest = :testId";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("testId", test.getId());
        return findAll(query, formProperties, parameters);*/
    }

    public Question saveQuestion(Question question, User user) {
        if (question.getId() == null) {
            question.setCreatedBy(user.getUsername());
            question.setCreated(new Date());
        } else {
            question.setModifiedBy(user.getUsername());
            question.setModified(new Date());
        }
        return makePersistent(question);
    }
}
