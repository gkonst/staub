package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.DifficultyWrapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    public Test saveTest(Test test, List<DifficultyWrapper> difficulties, User user) {
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
    }

    public long getExpectedNumberOfQuestions(Test test) {
        long result = 0;
        Set<TestDifficulty> levels = test.getDifficultyLevels();
        if (levels != null) {
            for (TestDifficulty testDifficulty : levels) {
                result += testDifficulty.getQuestionsCount();
            }
        }
        return result;
    }
}
