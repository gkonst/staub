package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.entity.TestDifficulty;

import javax.ejb.Local;
import java.util.List;

/**
 * Local Interface for <code>TestServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Local
public interface TestService extends GenericService<Test, Integer> {
    /**
     * Saves test (updates or inserts).
     * Also updates neede audit fields(created, createdBy, etc.)
     *
     * @param test test to save
     * @param user current user, author of this modifications
     *
     * @return updated test
     */
    Test saveTest(Test test, User user);

    /**
     * Assigns students for specific test.
     *
     * @param testId     specific test identifier
     * @param studentIds list or students identifiers
     */
    void assignTest(Integer testId, List<Integer> studentIds);

    /**
     * Returns a questions count for a test.
     *
     * @param test the test
     * @return the questions count
     */
    long getExpectedNumberOfQuestions(Test test);

    /**
     * Removes a difficulty level from a test. The <code>TestDifficulty</code> entity is removed from a database as
     * well.
     *
     * @param testDifficulty the difficulty level to process
     */
    void removeDifficultyLevel(TestDifficulty testDifficulty);
}
