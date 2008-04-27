package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Difficulty;
import ru.spbspu.staub.exception.RemoveException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

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

    @Override
    public void remove(Difficulty difficulty) throws RemoveException {
        logger.debug("> remove(difficulty=#0)", difficulty);

        Difficulty d = getEntityManager().merge(difficulty);
        if ((questionService.countQuestions(d) + testService.countTests(d)) == 0) {
            logger.debug("*  No related entities found.");
            getEntityManager().remove(d);
            logger.debug("*  Difficulty removed from a database.");
        } else {
            logger.debug("*  Related entities exist.");
            throw new RemoveException("Could not remove a difficulty.");
        }

        logger.debug("< remove(difficulty=#0)", difficulty);
    }
}