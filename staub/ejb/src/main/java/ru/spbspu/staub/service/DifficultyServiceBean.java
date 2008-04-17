package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Difficulty;
import ru.spbspu.staub.entity.Test;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

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
    @SuppressWarnings("unchecked")
    public List<Difficulty> getDifficultyLevels(Test test) {
        Query q = getEntityManager().createQuery("select d from Difficulty d, TestDifficulty t where d.id = t.fkDifficulty and t.fkTest = :testId");
        q.setParameter("testId", test.getId());
        return q.getResultList();
    }
}