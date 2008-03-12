package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.QuestionTrace;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Stateless EJB Service for manipulations with <code>QuestionTrace</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionTraceService")
@AutoCreate
@Stateless
public class QuestionTraceServiceBean extends GenericServiceBean<QuestionTrace, Integer> implements QuestionTraceService {
    @SuppressWarnings("unchecked")
    public List<Integer> findIdsByTestTraceId(Integer testTraceId) {
        return getEntityManager().createQuery("select qt.id from QuestionTrace qt where qt.testTrace.id = :testTraceId").setParameter("testTraceId", testTraceId).getResultList();
    }

    public void saveAnswer(QuestionTrace questionTrace) {
        // TODO implement method

    }

    public boolean checkGroup(QuestionTrace questionTrace) {
        // TODO implement method
        return false;
    }
}
