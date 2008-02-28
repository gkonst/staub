package ru.spbspu.staub.dao;

import ru.spbspu.staub.entity.Question;

import javax.ejb.Local;
import java.util.List;

/**
 * Local Interface for <code>QuestionDAOBean</code> Stateless EJB. 
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface QuestionDAO extends GenericDAO<Question, Integer> {
    List<Integer> findIdsByTestId(Integer testId);
}