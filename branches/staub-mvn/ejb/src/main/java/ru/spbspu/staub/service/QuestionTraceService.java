package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.entity.TestTrace;

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
     * for specific <code>TestTrace</code> instance.
     * Fetches only unfinished question traces with specific order.
     *
     * @param testTrace specific <code>TestTrace</code>
     *
     * @return fetched list
     */
    List<Integer> findIdsByTestTraceId(TestTrace testTrace);

    /**
     * Saves anwered question.
     *
     * @param questionTrace question to save
     */
    void saveAnswer(QuestionTrace questionTrace);

    /**
     * Validates a single answer.
     *
     * @param questionTrace the trace element to process
     *
     * @return the updated trace element
     */
    QuestionTrace checkQuestion(QuestionTrace questionTrace);
}
