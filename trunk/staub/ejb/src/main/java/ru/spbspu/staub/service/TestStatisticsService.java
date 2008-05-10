package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;

/**
 * The <code>TestStatisticsService</code> is a stateless EJB service to manipulate <code>TestStatistics</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Local
public interface TestStatisticsService extends GenericService<TestStatistics, Integer> {
    /**
     * Searches statistical data for a specified test.
     *
     * @param test the test
     *
     * @return the search result; <code>null</code> if not found
     */
    TestStatistics find(Test test);

    /**
     * Searches statistical data for a test matching specified criteria.
     *
     * @param formProperties the form properties
     * @param discipline     the discipline
     * @param category       the category
     * @param topic          the topic
     *
     * @return the table of results
     */
    public FormTable findTests(FormProperties formProperties, Discipline discipline, Category category, Topic topic);

    /**
     * Refreshes statistical data for test.
     */
    void calculateStatistics();
}