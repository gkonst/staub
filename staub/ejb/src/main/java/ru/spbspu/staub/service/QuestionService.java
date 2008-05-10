package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;

/**
 * Local Interface for <code>QuestionServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Local
public interface QuestionService extends GenericService<Question, Integer> {
    /**
     * Searches questions matching specified criteria.
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
    public FormTable findQuestions(FormProperties formProperties, Discipline discipline, Category category, Topic topic,
                                   Difficulty difficulty, Integer questionId);

    /**
     * Counts questions of a specified category.
     *
     * @param category the category
     *
     * @return the number of questions
     */
    long countQuestions(Category category);

    /**
     * Counts questions of a specified difficulty.
     *
     * @param difficulty the difficulty
     *
     * @return the number of questions
     */
    long countQuestions(Difficulty difficulty);

    /**
     * Counts questions of a specified discipline.
     *
     * @param discipline the discipline
     *
     * @return the number of questions
     */
    long countQuestions(Discipline discipline);

    /**
     * Counts questions of a specified topic.
     *
     * @param topic the topic
     *
     * @return the number of questions
     */
    long countQuestions(Topic topic);

    /**
     * Saves question (updates or inserts).
     * Also updates neede audit fields(created, createdBy, etc.)
     *
     * @param question question to save
     * @param user     current user, author of this modifications
     *
     * @return updated question
     */
    Question saveQuestion(Question question, User user);
}
