package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Stateless;
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
    /**
     * {@inheritDoc}
     */
    public FormTable findAllForTest(FormProperties formProperties, Test test) {
        String query = "select q from Test t join t.questions q where t = :test";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("test", test);
        return findAll(query, formProperties, parameters);
    }

    /**
     * {@inheritDoc}
     */
    public Question saveQuestion(Question question, User user) {
        // TODO implement method
        return question;
    }
}
