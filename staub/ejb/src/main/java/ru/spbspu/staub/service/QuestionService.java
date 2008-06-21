package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
     * @param inputStream the data to import
     * @param user        the user
     *
     * @return the target entity
     *
     * @throws JAXBException if an exception occurs during an XML document processing.
     */
    Question importQuestion(InputStream inputStream, User user) throws JAXBException;

    /**
     * Exports a question.
     *
     * @param id           the identification number
     * @param outputStream the output stream
     *
     * @throws JAXBException if an exception occurs during an XML document processing.
     * @throws IOException   if an exception occurs during an IO operation.
     */
    void exportQuestion(Integer id, OutputStream outputStream) throws JAXBException, IOException;
}
