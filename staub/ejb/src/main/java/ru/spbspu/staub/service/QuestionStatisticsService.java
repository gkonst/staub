package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;

/**
 * The <code>QuestionStatisticsService</code> is a stateless EJB service to manipulate <code>QuestionStatisrics</code>
 * entities.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface QuestionStatisticsService extends GenericService<QuestionStatistics, Integer> {
    /**
     * Searches statistical data for a specified question.
     *
     * @param question the question
     *
     * @return the search result; <code>null</code> if not found
     */
    QuestionStatistics find(Question question);

    /**
     * Searches statistical data for questions matching specified criteria.
     *
     * @param formProperties the form properties
     * @param discipline     the discipline
     * @param category       the category
     * @param topic          the topic
     * @param difficulty     the difficulty
     * @param questionId     the question id
     *
     * @return the table of results
     */
    FormTable find(FormProperties formProperties, Discipline discipline, Category category, Topic topic,
                   Difficulty difficulty, Integer questionId);

    /**
     * Refreshes statistical data for questions.
     */
    void calculateStatistics();
}