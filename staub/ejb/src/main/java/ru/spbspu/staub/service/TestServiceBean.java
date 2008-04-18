package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Assignment;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Stateless EJB Service for manipulations with <code>Test</code> entity.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Name("testService")
@AutoCreate
@Stateless
public class TestServiceBean extends GenericServiceBean<Test, Integer> implements TestService {
    @EJB
    private AssignmentService assignmentService;

    @EJB
    private TestService testService;

    @EJB
    private StudentService studentService;

    public Test saveTest(Test test, User user) {
        validateTest(test);

        if (test.getId() == null) {
            test.setCreatedBy(user.getUsername());
            test.setCreated(new Date());
        } else {
            test.setModifiedBy(user.getUsername());
            test.setModified(new Date());
        }

        return makePersistent(test);
    }

    public void assignTest(Integer testId, List<Integer> studentIds) {
        Test test = testService.findById(testId);
        for (Integer studentId : studentIds) {
            Student student = studentService.findById(studentId);

            Assignment assignment = new Assignment();
            assignment.setTest(test);
            assignment.setStudent(student);

            assignmentService.saveAssignment(assignment);
        }
    }

    private void validateTest(Test test) {
        Integer timeLimit = test.getTimeLimit();
        if ((timeLimit != null) && (timeLimit <= 0)) {
            String message = MessageFormat.format("Could not save Test with timeLimit={0}.", timeLimit);
            throw new IllegalArgumentException(message);
        }

        Integer passScore = test.getPassScore();
        if ((passScore != null) && ((passScore <= 0) || (passScore > 100))) {
            String message = MessageFormat.format("Could not save Test with passScore={0}.", passScore);
            throw new IllegalArgumentException(message);
        }
    }

    public long getTotalNumberOfQuestions(Test test) {
        Query q = getEntityManager().createQuery("select sum(td.questionsCount) from TestDifficulty td where td.fkTest = :testId");
        q.setParameter("testId", test.getId());
        return (Long) q.getSingleResult();
    }
}
