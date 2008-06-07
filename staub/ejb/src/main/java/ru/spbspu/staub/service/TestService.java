package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.DifficultyWrapper;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

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
     * Searches test matching specified criteria.
     *
     * @param formProperties the form properties
     * @param discipline     the discipline
     * @param category       the category
     * @param topic          the topic
     *
     * @return the table of results
     */
    public FormTable find(FormProperties formProperties, Discipline discipline, Category category, Topic topic);

    /**
     * Counts tests of a specified category.
     *
     * @param category the category
     *
     * @return the number of tests
     */
    long count(Category category);

    /**
     * Counts tests of a specified difficulty.
     *
     * @param difficulty the difficulty
     *
     * @return the number of tests
     */
    long count(Difficulty difficulty);

    /**
     * Counts tests of a specified discipline.
     *
     * @param discipline the discipline
     *
     * @return the number of tests
     */
    long count(Discipline discipline);

    /**
     * Counts tests of a specified topic.
     *
     * @param topic the topic
     *
     * @return the number of tests
     */
    long count(Topic topic);

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
    Test save(Test test, List<DifficultyWrapper> difficulties, User user);

    /**
     * Returns a questions count for a test.
     *
     * @param test the test
     *
     * @return the questions count
     */
    long getExpectedNumberOfQuestions(Test test);
}
