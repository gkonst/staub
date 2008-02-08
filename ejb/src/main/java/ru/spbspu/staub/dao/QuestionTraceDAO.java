package ru.spbspu.staub.dao;

import ru.spbspu.staub.entity.QuestionTrace;

import javax.ejb.Local;

/**
 * Local Interface for <code>QuestionTraceDAOBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface QuestionTraceDAO extends GenericDAO<QuestionTrace, Integer> {
}
