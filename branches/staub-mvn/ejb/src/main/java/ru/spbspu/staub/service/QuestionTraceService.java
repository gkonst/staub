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
    /**
     * Fetches <code>List</code> of <code>QuestionTrace</code> identifiers
     * for specific testTraceId.
     * @param testTraceId <code>TestTrace</code> identifier
     * @return fetched list
     */
    List<Integer> findIdsByTestTraceId(Integer testTraceId);

    /**
     * Saves anwered question.
     * @param questionTrace question to save
     */
    void saveAnswer(QuestionTrace questionTrace);

    /**
     * Chcecks group for propriety.
     * @param questionTrace ???
     * @return true or not
     */
    boolean checkGroup(QuestionTrace questionTrace);
}
