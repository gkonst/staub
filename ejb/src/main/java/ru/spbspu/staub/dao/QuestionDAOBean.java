package ru.spbspu.staub.dao;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Question;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Stateless EJB DAO for manipulations with <code>Question</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("questionDAO")
@AutoCreate
@Stateless
public class QuestionDAOBean extends GenericDAOBean<Question, Integer> implements QuestionDAO {
    public List<Integer> findIdsByTestId(Integer testId) {
        return getEntityManager().createQuery("select q.id from " + Question.class.getName() + " q where q.test.id = :testId").setParameter("testId", testId).getResultList();
    }
}
