package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.QuestionTrace;

import javax.ejb.Local;

/**
 * Local Interface for <code>QuestionTraceServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface QuestionTraceService extends GenericService<QuestionTrace, Integer> {
}
