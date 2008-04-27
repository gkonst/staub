package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.DifficultyWrapper;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.*;

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
    private TestTraceService testTraceService;

    public long countTests(Category category) {
        Query q = getEntityManager().createQuery("select count(t) from Test t where t.category = :category");
        q.setParameter("category", category);
        return (Long) q.getSingleResult();
    }

    public long countTests(Difficulty difficulty) {
        Query q = getEntityManager().createQuery("select count(t) from Test t join t.difficultyLevels d where d.difficulty = :difficulty");
        q.setParameter("difficulty", difficulty);
        return (Long) q.getSingleResult();
    }

    public long countTests(Discipline discipline) {
        Query q = getEntityManager().createQuery("select count(t) from Discipline d join d.categories c, Test t where c = t.category and d = :discipline");
        q.setParameter("discipline", discipline);
        return (Long) q.getSingleResult();
    }

    public long countTests(Topic topic) {
        Query q = getEntityManager().createQuery("select count(t) from Test t join t.topics tt where tt = :topic");
        q.setParameter("topic", topic);
        return (Long) q.getSingleResult();
    }

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
            if (wrapper.getSelected()) {
                TestDifficulty testDifficulty = new TestDifficulty(test, wrapper.getDifficulty(),
                        wrapper.getQuestionsCount(), wrapper.getPassScore());
                getEntityManager().persist(testDifficulty);
            }
        }

        return result;
    }

    public void assignTest(Integer testId, List<Integer> studentIds) {
        Test test = findById(testId);
        for (Integer studentId : studentIds) {
            Student student = getEntityManager().find(Student.class, studentId);

            Assignment assignment = new Assignment();
            assignment.setTest(test);
            assignment.setStudent(student);

            assignmentService.saveAssignment(assignment);
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

    @Override
    @SuppressWarnings("unchecked")
    public List<Test> findAll() {
        logger.debug(">>> Finding all(entity=#0)...", Test.class.getName());
        StringBuilder queryString = new StringBuilder()
                .append("select o from ")
                .append(Test.class.getName())
                .append(" o where o.active = true");
        List<Test> result = getEntityManager().createQuery(queryString.toString()).getResultList();
        logger.debug("<<< Finding all...Ok(#0 found)", result.size());
        return result;
    }

    @Override
    public FormTable findAll(FormProperties formProperties) {
        StringBuilder queryString = new StringBuilder()
                .append("select o from ")
                .append(Test.class.getName())
                .append(" o where o.active = true");
        return findAll(queryString.toString(), formProperties, new HashMap<String, Object>(0));
    }

    @Override
    public void remove(Test test) throws RemoveException {
        logger.debug("> remove(test=#0)", test);

        Test t = getEntityManager().merge(test);
        if (assignmentService.countAssignments(t) == 0) {
            logger.debug("*  No related Assignment entities found.");
            if (testTraceService.countTestTraces(t) == 0) {
                logger.debug("*  No related TestTrace entities found.");
                getEntityManager().remove(t);
                logger.debug("*  Test removed from a database.");
            } else {
                logger.debug("*  Related TestTrace entities exist.");
                t.setActive(false);
                t = getEntityManager().merge(t);
                logger.debug("*  Test marked inactive.");
            }
        } else {
            logger.debug("*  Related Assignment entities exist.");
            throw new RemoveException("Could not remove a Test.");
        }

        logger.debug("< remove(difficulty=#0)", t);
    }

    private void validateTest(Test test) {
        Integer timeLimit = test.getTimeLimit();
        if ((timeLimit != null) && (timeLimit <= 0)) {
            String message = MessageFormat.format("Could not save Test with timeLimit={0}.", timeLimit);
            throw new IllegalArgumentException(message);
        }
    }
}
