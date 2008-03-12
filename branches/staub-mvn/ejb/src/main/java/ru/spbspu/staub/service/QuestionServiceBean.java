package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Question;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Stateless EJB Service for manipulations with <code>Question</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionService")
@AutoCreate
@Stateless
public class QuestionServiceBean extends GenericServiceBean<Question, Integer> implements QuestionService {
    @SuppressWarnings("unchecked")
    public List<Integer> findIdsByTestId(Integer testId) {
        return getEntityManager().createQuery("select q.id from Question q where q.test.id = :testId").setParameter("testId", testId).getResultList();
    }
}
