package ru.spbspu.staub.service;

import ru.spbspu.staub.data.question.QuestionDataType;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import javax.xml.bind.JAXBException;

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
    public FormTable find(FormProperties formProperties, Discipline discipline, Category category, Topic topic,
                          Difficulty difficulty, Integer questionId);

    /**
     * Counts questions of a specified category.
     *
     * @param category the category
     *
     * @return the number of questions
     */
    long count(Category category);

    /**
     * Counts questions of a specified difficulty.
     *
     * @param difficulty the difficulty
     *
     * @return the number of questions
     */
    long count(Difficulty difficulty);

    /**
     * Counts questions of a specified discipline.
     *
     * @param discipline the discipline
     *
     * @return the number of questions
     */
    long count(Discipline discipline);

    /**
     * Counts questions of a specified topic.
     *
     * @param topic the topic
     *
     * @return the number of questions
     */
    long count(Topic topic);

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

    /**
     * Imports a question.
     *
     * @param questionData the data to import
     * @param user         the user
     *
     * @return the target entity
     *
     * @throws JAXBException if an exception occurs during an XML document processing.
     */
    Question importQuestion(QuestionDataType questionData, User user) throws JAXBException;

    /**
     * Exports a question.
     *
     * @param id the identification number
     *
     * @return the exported data
     *
     * @throws JAXBException if an exception occurs during an XML document processing.
     */
    QuestionDataType exportQuestion(Integer id)  throws JAXBException;
}
