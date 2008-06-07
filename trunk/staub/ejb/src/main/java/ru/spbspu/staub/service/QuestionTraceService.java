package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.QuestionTrace;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import java.util.List;

/**
 * Local Interface for <code>QuestionTraceServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
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
     * Searches question traces of a specified test trace.
     *
     * @param formProperties the form table
     * @param testTrace      the test trace
     *
     * @return the table of results
     */
    FormTable find(FormProperties formProperties, TestTrace testTrace);

    /**
     * Counts a question traces of a specific question.
     *
     * @param question the question
     *
     * @return the number of question traces
     */
    long count(Question question);

    /**
     * Sets the "started" field of a trace element. If the field is not <code>null</code> it is keeped unchanged.
     *
     * @param questionTrace the trace element to start
     *
     * @return the updated trace element
     */
    QuestionTrace start(QuestionTrace questionTrace);

    /**
     * Saves anwered question.
     *
     * @param questionTrace question to save
     *
     * @return the updated trace element
     */
    QuestionTrace saveAnswer(QuestionTrace questionTrace);

    /**
     * Validates a single answer.
     *
     * @param questionTrace the trace element to process
     *
     * @return the updated trace element
     */
    QuestionTrace checkQuestion(QuestionTrace questionTrace);
}
