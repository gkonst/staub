package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.User;

import javax.ejb.Local;

/**
 * Local Interface for <code>QuestionServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface QuestionService extends GenericService<Question, Integer> {
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
