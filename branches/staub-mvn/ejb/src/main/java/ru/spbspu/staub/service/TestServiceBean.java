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
import java.util.List;
import java.util.Map;

/**
 * Stateless EJB Service for manipulations with <code>Test</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("testService")
@AutoCreate
@Stateless
public class TestServiceBean extends GenericServiceBean<Test, Integer> implements TestService {
    /**
     * {@inheritDoc}
     */
    public FormTable findAllToPassForUser(FormProperties formProperties, User user) {
        String query = "select a.test from Assignment a where a.user = :user and a.testBegin <= :currentDate and a.testEnd > :currentDate";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("user", user);
        parameters.put("currentDate", new Date());
        return findAll(query, formProperties, parameters);
    }

    /**
     * {@inheritDoc}
     */
    public Test addQuestionToTest(Test test, Question question, User user) {
        // TODO implement method
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Test addQuestionsToTest(Test test, List<Integer> questionsIds, User user) {
        // TODO implement method
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Test saveTest(Test test, User user) {
        // TODO implement method
        return test;
    }
}
