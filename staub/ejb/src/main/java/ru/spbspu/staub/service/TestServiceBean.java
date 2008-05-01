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

    public FormTable findTests(FormProperties formProperties, Discipline discipline, Category category, Topic topic) {
        logger.debug("> findTests(FormProperties=#0, Discipline=#1, Category=#2, Topic=#3)", formProperties, discipline,
                category, topic);

        StringBuilder query = new StringBuilder();
        query.append("select t from Test t");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (topic != null) {
            query.append(" join t.topics tt where tt = :topic and");
            parameters.put("topic", topic);
        } else if (category != null) {
            query.append(" where t.category = :category and");
            parameters.put("category", category);
        } else if (discipline != null) {
            query.append(", Discipline d join d.categories c where t.category = c and d = :discipline and");
            parameters.put("discipline", discipline);
        } else {
            query.append(" where");
        }

        query.append(" t.active = true");

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< findTests(FormProperties, Discipline, Category, Topic)");

        return formTable;
    }

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
            removeDifficultyLevels(test);

            test.setModifiedBy(user.getUsername());
            test.setModified(new Date());
        }

        Test result = makePersistent(test);

        for (DifficultyWrapper wrapper : difficulties) {
            if (wrapper.getSelected()) {
                TestDifficulty testDifficulty = new TestDifficulty(result, wrapper.getDifficulty(),
                        wrapper.getQuestionsCount(), wrapper.getPassScore());
                getEntityManager().persist(testDifficulty);
            }
        }

        return result;
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
        StringBuilder queryString = new StringBuilder().append("select t from Test t where t.active = true");
        List<Test> result = getEntityManager().createQuery(queryString.toString()).getResultList();
        logger.debug("<<< Finding all...Ok(#0 found)", result.size());
        return result;
    }

    @Override
    public FormTable findAll(FormProperties formProperties) {
        StringBuilder queryString = new StringBuilder().append("select t from Test t where t.active = true");
        return findAll(queryString.toString(), formProperties, new HashMap<String, Object>(0));
    }

    @Override
    public void remove(Test test) throws RemoveException {
        logger.debug("> remove(Test=#0)", test);

        Test t = getEntityManager().merge(test);
        if (assignmentService.countAssignments(t) == 0) {
            logger.debug("*  No related Assignment entities found.");
            if (testTraceService.countTestTraces(t) == 0) {
                logger.debug("*  No related TestTrace entities found.");
                removeDifficultyLevels(t);
                getEntityManager().remove(t);
                logger.debug("*  Test removed from a database.");
            } else {
                logger.debug("*  Related TestTrace entities exist.");
                t.setActive(false);
                getEntityManager().merge(t);
                logger.debug("*  Test marked inactive.");
            }
        } else {
            logger.debug("*  Related Assignment entities exist.");
            throw new RemoveException("Could not remove a Test.");
        }

        logger.debug("< remove(Test)");
    }

    private void validateTest(Test test) {
        Integer timeLimit = test.getTimeLimit();
        if ((timeLimit != null) && (timeLimit <= 0)) {
            String message = MessageFormat.format("Could not save Test with timeLimit={0}.", timeLimit);
            throw new IllegalArgumentException(message);
        }
    }

    private void removeDifficultyLevels(Test test) {
        Iterator<TestDifficulty> iter = test.getDifficultyLevels().iterator();
        while (iter.hasNext()) {
            TestDifficulty testDifficulty = iter.next();
            iter.remove();
            getEntityManager().remove(getEntityManager().merge(testDifficulty));
        }
    }
}
