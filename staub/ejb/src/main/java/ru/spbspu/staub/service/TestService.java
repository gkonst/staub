package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.DifficultyWrapper;

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
     * Counts a tests of a specified category.
     *
     * @param category the category
     *
     * @return the number of tests
     */
    long countTests(Category category);

    /**
     * Counts a tests of a specified difficulty.
     *
     * @param difficulty the difficulty
     *
     * @return the number of tests
     */
    long countTests(Difficulty difficulty);

    /**
     * Counts a tests of a specified discipline.
     *
     * @param discipline the discipline
     *
     * @return the number of tests
     */
    long countTests(Discipline discipline);

    /**
     * Counts a tests of a specified topic.
     *
     * @param topic the topic
     *
     * @return the number of tests
     */
    long countTests(Topic topic);

    /**
     * Saves test (updates or inserts).
     * Also updates neede audit fields(created, createdBy, etc.)
     *
     * @param test         test to save
     * @param difficulties list of wrapped difficlties
     * @param user         current user, author of this modifications
     *
     * @return updated test
     */
    Test saveTest(Test test, List<DifficultyWrapper> difficulties, User user);

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
     *
     * @return the questions count
     */
    long getExpectedNumberOfQuestions(Test test);
}
