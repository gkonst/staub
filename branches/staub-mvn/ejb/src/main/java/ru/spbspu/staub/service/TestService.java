package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Question;
import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;
import java.util.List;

/**
 * Local Interface for <code>TestServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface TestService extends GenericService<Test, Integer> {
    /**
     * Fetches all tests assigned for user.
     *
     * @param formProperties form properties
     * @param user           specific user
     * @return result fetch
     */
    FormTable findAllToPassForUser(FormProperties formProperties, User user);

    /**
     * Adds question for specific test.
     *
     * @param test     specific test instance
     * @param question specific question instance
     * @param user     specific user instance
     * @return updates test instance
     */
    Test addQuestionToTest(Test test, Question question, User user);

    /**
     * Adds questions for specific test.
     *
     * @param test         specific test instance
     * @param questionsIds list of question identifiers
     * @param user         specific user instance
     * @return updates test instance
     */
    Test addQuestionsToTest(Test test, List<Integer> questionsIds, User user);

    /**
     * Saves test (updates or inserts).
     * Also updates neede audit fields(created, createdBy, etc.)
     *
     * @param test test to save
     * @param user current user, author of this modifications
     * @return updated test
     */
    Test saveTest(Test test, User user);

    /**
     * Assigns users for specific test.
     *
     * @param testId   specific test identifier
     * @param usersIds list or users identifiers
     */
    void assignTest(Integer testId, List<Integer> usersIds);
}
