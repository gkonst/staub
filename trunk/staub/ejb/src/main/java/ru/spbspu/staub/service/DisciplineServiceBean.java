package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.exception.RemoveException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Stateless EJB Service for manipulations with <code>Discipline</code> entity.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Name("disciplineService")
@AutoCreate
@Stateless
public class DisciplineServiceBean extends GenericServiceBean<Discipline, Integer> implements DisciplineService {
    @EJB
    private QuestionService questionService;

    @EJB
    private TestService testService;

    @Override
    public void remove(Discipline discipline) throws RemoveException {
        logger.debug("> remove(Discipline=#0)", discipline);

        Discipline d = getEntityManager().merge(discipline);
        if ((questionService.countQuestions(d) + testService.countTests(d)) == 0) {
            logger.debug("*  No related entities found.");
            getEntityManager().remove(d);
            logger.debug("*  Discipline removed from a database.");
        } else {
            logger.debug("*  Related entities exist.");
            throw new RemoveException("Could not remove a discipline.");
        }

        logger.debug("< remove(Discipline)");
    }
}
