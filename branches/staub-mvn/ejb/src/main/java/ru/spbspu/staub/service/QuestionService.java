package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Question;

import javax.ejb.Local;

/**
 * Local Interface for <code>QuestionServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface QuestionService extends GenericService<Question, Integer> {
}
