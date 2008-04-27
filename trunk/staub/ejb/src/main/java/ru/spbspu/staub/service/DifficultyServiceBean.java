package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Difficulty;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Stateless EJB Service for manipulations with <code>Difficulty</code> entity.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Name("difficultyService")
@AutoCreate
@Stateless
public class DifficultyServiceBean extends GenericServiceBean<Difficulty, Integer> implements DifficultyService {
    @Override
    public void remove(Difficulty difficulty) {
        logger.debug("> remove(difficulty=#0)", difficulty);

        Difficulty d = getEntityManager().merge(difficulty);

        long count = getQuestionsCount(d) + getTestsCount(d);
        if (count == 0) {
            logger.debug("*  No relations found.");
        } else {
            logger.debug("*  Relations exist.");
            throw new IllegalArgumentException("Could not remove a difficulty.");
        }

        getEntityManager().remove(d);

        logger.debug("< remove(difficulty=#0)", difficulty);
    }

    private long getQuestionsCount(Difficulty difficulty) {
        Query q = getEntityManager().createQuery("select count(q) from Question q where q.difficulty = :difficulty");
        q.setParameter("difficulty", difficulty);
        return (Long) q.getSingleResult();
    }

    private long getTestsCount(Difficulty difficulty) {
        Query q = getEntityManager().createQuery("select count(t) from Test t join t.difficultyLevels d where d.difficulty = :difficulty");
        q.setParameter("difficulty", difficulty);
        return (Long) q.getSingleResult();
    }
}