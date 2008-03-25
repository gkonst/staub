package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Stateless;

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
        // TODO implement method
        return findAll(formProperties);
    }
}
