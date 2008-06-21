package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.data.question.DisciplineDataType;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.exception.RemoveException;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
    public boolean isCodeUnique(String code) {
        logger.debug("> isCodeUnique(String=#0)", code);

        Query q = getEntityManager().createQuery("select count(d) from Discipline d where d.code = :code");
        q.setParameter("code", code);

        boolean unique = ((Long) q.getSingleResult() == 0);
        if (unique) {
            logger.debug("*  Code is unique.");
        } else {
            logger.debug("*  Code is not unique.");
        }

        logger.debug("< isCodeUnique(String)");

        return unique;
    }

    public Discipline importDiscipline(DisciplineDataType disciplineData) {
        logger.debug("> importDiscipline(DisciplineDataType=#0)", disciplineData);

        String code = disciplineData.getCode();
        Discipline result = findByCode(code);
        if (result == null) {
            logger.debug("*  Discipline with code=#0 was not found.", code);

            Discipline discipline = new Discipline();
            discipline.setCode(code);
            discipline.setName(disciplineData.getName());

            result = makePersistent(discipline);
        }

        logger.debug("*  Result: #0", result);
        logger.debug("< importDiscipline(DisciplineDataType)");

        return result;
    }

    public DisciplineDataType exportDiscipline(Integer id) {
        logger.debug("> exportDiscipline(Integer=#0)", id);

        Discipline discipline = null;
        try {
            discipline = getEntityManager().find(Discipline.class, id);
        } catch (NoResultException e) {
            // do nothing
        }

        DisciplineDataType result = null;
        if (discipline != null) {
            result = new DisciplineDataType();
            result.setCode(discipline.getCode());
            result.setName(discipline.getName());
        }

        logger.debug("*  Result: #0", result);
        logger.debug("< exportDiscipline(Integer)");

        return result;
    }

    @Override
    public void remove(Discipline discipline) throws RemoveException {
        logger.debug("> remove(Discipline=#0)", discipline);

        Discipline d = getEntityManager().merge(discipline);
        if ((countQuestions(d) + countTests(d)) == 0) {
            logger.debug("*  No related entities found.");
            getEntityManager().remove(d);
            logger.debug("*  Discipline removed from a database.");
        } else {
            logger.debug("*  Related entities exist.");
            throw new RemoveException("Could not remove a discipline.");
        }

        logger.debug("< remove(Discipline)");
    }

    private Discipline findByCode(String code) {
        Discipline discipline = null;

        Query q = getEntityManager().createQuery("select d from Discipline d where d.code = :code");
        q.setParameter("code", code);

        try {
            discipline = (Discipline) q.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        return discipline;
    }

    private long countQuestions(Discipline discipline) {
        Query q = getEntityManager().createQuery("select count(q) from Discipline d join d.categories c join c.topics t, Question q where q.topic = t and d = :discipline");
        q.setParameter("discipline", discipline);
        return (Long) q.getSingleResult();
    }

    private long countTests(Discipline discipline) {
        Query q = getEntityManager().createQuery("select count(t) from Discipline d join d.categories c, Test t where c = t.category and d = :discipline");
        q.setParameter("discipline", discipline);
        return (Long) q.getSingleResult();
    }
}
