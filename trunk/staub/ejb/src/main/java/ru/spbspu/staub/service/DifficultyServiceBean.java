package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.data.question.DifficultyDataType;
import ru.spbspu.staub.entity.Difficulty;
import ru.spbspu.staub.exception.RemoveException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
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
    @EJB
    private QuestionService questionService;

    @EJB
    private TestService testService;

    public boolean isCodeUnique(Integer code) {
        logger.debug("> isCodeUnique(Integer=#0)", code);

        Query q = getEntityManager().createQuery("select count(d) from Difficulty d where d.code = :code");
        q.setParameter("code", code);

        boolean unique = ((Long) q.getSingleResult() == 0);
        if (unique) {
            logger.debug("*  Code is unique.");
        } else {
            logger.debug("*  Code is not unique.");
        }

        logger.debug("< isCodeUnique(Integer)");

        return unique;
    }

    public Difficulty importDifficulty(DifficultyDataType difficultyData) {
        logger.debug("> importDifficulty(DifficultyDataType=#0)", difficultyData);

        Integer code = difficultyData.getCode();
        Difficulty result = findByCode(code);
        if (result == null) {
            logger.debug("*  Difficulty with code=#0 was not found.", code);

            Difficulty difficulty = new Difficulty();
            difficulty.setCode(code);
            difficulty.setName(difficultyData.getName());

            result = makePersistent(difficulty);
        }

        logger.debug("*  Result: #0", result);
        logger.debug("< importDifficulty(DifficultyDataType)");

        return result;
    }

    public DifficultyDataType exportDifficulty(Integer id) {
        logger.debug("> exportDifficulty(Integer=#0)", id);

        Difficulty difficulty = null;
        try {
            difficulty = getEntityManager().find(Difficulty.class, id);
        } catch (NoResultException e) {
            // do nothing
        }

        DifficultyDataType result = null;
        if (difficulty != null) {
            result = new DifficultyDataType();
            result.setCode(difficulty.getCode());
            result.setName(difficulty.getName());
        }

        logger.debug("*  Result: #0", result);
        logger.debug("< exportDifficulty(Integer)");

        return result;
    }

    @Override
    public void remove(Difficulty difficulty) throws RemoveException {
        logger.debug("> remove(Difficulty=#0)", difficulty);

        Difficulty d = getEntityManager().merge(difficulty);
        if ((questionService.count(d) + testService.count(d)) == 0) {
            logger.debug("*  No related entities found.");
            getEntityManager().remove(d);
            logger.debug("*  Difficulty removed from a database.");
        } else {
            logger.debug("*  Related entities exist.");
            throw new RemoveException("Could not remove a difficulty.");
        }

        logger.debug("< remove(Difficulty=#0)", difficulty);
    }

    private Difficulty findByCode(Integer code) {
        Difficulty difficulty = null;

        Query q = getEntityManager().createQuery("select d from Difficulty d where d.code = :code");
        q.setParameter("code", code);

        try {
            difficulty = (Difficulty) q.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        return difficulty;
    }
}