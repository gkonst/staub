package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.*;

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
     * Counts a questions of a specified category.
     *
     * @param category the category
     *
     * @return the number of questions
     */
    long countQuestions(Category category);

    /**
     * Counts a questions of a specified difficulty.
     *
     * @param difficulty the difficulty
     *
     * @return the number of questions
     */
    long countQuestions(Difficulty difficulty);

    /**
     * Counts a questions of a specified discipline.
     *
     * @param discipline the discipline
     *
     * @return the number of questions
     */
    long countQuestions(Discipline discipline);

    /**
     * Counts a questions of a specified topic.
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
