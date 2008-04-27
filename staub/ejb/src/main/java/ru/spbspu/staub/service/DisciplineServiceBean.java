package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Discipline;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Stateless EJB Service for manipulations with <code>Discipline</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("disciplineService")
@AutoCreate
@Stateless
public class DisciplineServiceBean extends GenericServiceBean<Discipline, Integer> implements DisciplineService {
    @Override
    public void remove(Discipline discipline) {
        logger.debug("> remove(discipline=#0)", discipline);

        Discipline d = getEntityManager().merge(discipline);

        long count = getQuestionsCount(d) + getTestsCount(d);
        if (count == 0) {
            logger.debug("*  No relations found.");
        } else {
            logger.debug("*  Relations exist.");
            throw new IllegalArgumentException("Could not remove a discipline.");
        }

        getEntityManager().remove(d);

        logger.debug("< remove(discipline=#0)", discipline);
    }

    private long getQuestionsCount(Discipline discipline) {
        Query q = getEntityManager().createQuery("select count(q) from Discipline d join d.categories c join c.topics t, Question q where q.topic = t and d = :discipline");
        q.setParameter("discipline", discipline);
        return (Long) q.getSingleResult();
    }

    private long getTestsCount(Discipline discipline) {
        Query q = getEntityManager().createQuery("select count(t) from Discipline d join d.categories c, Test t where c = t.category and d = :discipline");
        q.setParameter("discipline", discipline);
        return (Long) q.getSingleResult();
    }
}
