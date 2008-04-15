package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.EJB;
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
    @EJB
    private  AssignmentService assignmentService;

    public FormTable findAllToPassForUser(FormProperties formProperties, User user) {
        String query = "select t from Test t, Assignment a where t.id = a.fkTest and a.fkUser = :userId and a.testBegin <= :currentDate and a.testEnd > :currentDate";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userId", user.getId());
        parameters.put("currentDate", new Date());
        return findAll(query, formProperties, parameters);
    }

    public Test addQuestionToTest(Test test, Question question, User user) {
        test.setModifiedBy(user.getUsername());
        test.setModified(new Date());

        createTestQuestion(test, question.getId());

        // TODO: Consider NOT NULL constraint instead.
        Integer count = test.getQuestionsCount();
        if (count == null) {
            count = 0;
        }
        test.setQuestionsCount(count + 1);

        return makePersistent(test);
    }

    public Test addQuestionsToTest(Test test, List<Integer> questionsIds, User user) {
        test.setModifiedBy(user.getUsername());
        test.setModified(new Date());

        for (Integer questionId : questionsIds) {
            createTestQuestion(test, questionId);
        }

        test.setQuestionsCount(test.getQuestionsCount() + questionsIds.size());

        return makePersistent(test);
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

    public void assignTest(Integer testId, List<Integer> usersIds, Date begin, Date end) {
        for (Integer userId : usersIds) {
            Assignment assignment = new Assignment();
            assignment.setFkTest(testId);
            assignment.setFkUser(userId);
            assignment.setTestBegin(begin);
            assignment.setTestEnd(end);

            assignmentService.saveAssignment(assignment);
        }
    }

    private void createTestQuestion(Test test, Integer questionId) {
        TestQuestion tq = new TestQuestion();
        tq.setFkTest(test.getId());
        tq.setFkQuestion(questionId);
        getEntityManager().persist(tq);
    }
}
