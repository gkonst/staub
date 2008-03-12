package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.QuestionTrace;

import javax.ejb.Local;
import java.util.List;

/**
 * Local Interface for <code>QuestionTraceServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface QuestionTraceService extends GenericService<QuestionTrace, Integer> {
    List<Integer> findIdsByTestTraceId(Integer testTraceId);
}
