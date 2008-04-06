package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.TestQuestion;
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
    public FormTable findAllToPassForUser(FormProperties formProperties, User user) {
        String query = "select a.test from Assignment a where a.user = :user and a.testBegin <= :currentDate and a.testEnd > :currentDate";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("user", user);
        parameters.put("currentDate", new Date());
        return findAll(query, formProperties, parameters);
    }

    public Test addQuestionToTest(Test test, Question question, User user) {
        // TODO: Find out whether Test and Question should be persisted here.
        test.setModifiedBy(user.getUsername());
        test.setModified(new Date());
        makePersistent(test);

        createTestQuestion(test, question.getId());

        return test;
    }

    public Test addQuestionsToTest(Test test, List<Integer> questionsIds, User user) {
        // TODO: Find out whether Test should be persisted here.
        test.setModifiedBy(user.getUsername());
        test.setModified(new Date());
        makePersistent(test);

        for (Integer questionId : questionsIds) {
            createTestQuestion(test, questionId);
        }

        return test;
    }

    public Test saveTest(Test test, User user) {
        if (test.getId() == null) {
            test.setCreatedBy(user.getUsername());
            test.setCreated(new Date());
        } else {
            test.setModifiedBy(user.getUsername());
            test.setModified(new Date());
        }
        return makePersistent(test);
    }

    private void createTestQuestion(Test test, Integer questionId) {
        TestQuestion tq = new TestQuestion();
        tq.setFkTest(test.getId());
        tq.setFkQuestion(questionId);
        getEntityManager().persist(tq);
    }
}
