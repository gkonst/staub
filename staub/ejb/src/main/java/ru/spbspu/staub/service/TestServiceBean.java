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
import java.util.Iterator;

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
            test.setActive(true);
            test.setCreatedBy(user.getUsername());
            test.setCreated(new Date());
        } else {
            Iterator<TestDifficulty> iter = test.getDifficultyLevels().iterator();
            while (iter.hasNext()) {
                TestDifficulty testDifficulty = iter.next();
                iter.remove();
                getEntityManager().remove(testDifficulty);
            }

            test.setModifiedBy(user.getUsername());
            test.setModified(new Date());
        }

        Test result = makePersistent(test);

        for (DifficultyWrapper wrapper : difficulties) {
            TestDifficulty testDifficulty = new TestDifficulty(test, wrapper.getDifficulty(),
                    wrapper.getQuestionsCount(), wrapper.getPassScore());
            getEntityManager().persist(testDifficulty);
        }

        return result;
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
