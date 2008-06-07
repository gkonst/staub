package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.NoResultException;
import java.util.Map;
import java.util.HashMap;

/**
 * The <code>TestStatisticsServiceBean</code> is a stateless EJB service to manipulate <code>TestStatistics</code>
 * entities.
 *
 * @author Alexander V. Elagin
 */
@Name("testStatisticsService")
@AutoCreate
@Stateless
public class TestStatisticsServiceBean extends GenericServiceBean<TestStatistics, Integer>
        implements TestStatisticsService {
        private static final String DELETE_STATISTICS = "delete from test_statistics";

    private static final String INSERT_STATISTICS = "insert into test_statistics " +
            "(fk_test, total_answers, correct_answers, last_update) " +
            "(select tt.fk_test, count(*), sum(cast(tt.test_passed as integer)), current_timestamp " +
            "from test_trace tt join test t on tt.fk_test = t.id where tt.finished is not null and t.active = true " +
            "group by tt.fk_test)";

    public TestStatistics find(Test test) {
        logger.debug("> find(Test=#0)", test);

        Query q = getEntityManager().createQuery("select t from TestStatistics t where t.test = :test");
        q.setParameter("test", test);

        TestStatistics testStatistics = null;
        try {
            testStatistics = (TestStatistics) q.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        logger.debug("< find(Test)");

        return testStatistics;
    }

    public FormTable find(FormProperties formProperties, Discipline discipline, Category category, Topic topic) {
        logger.debug("> find(FormProperties=#0, Discipline=#1, Category=#2, Topic=#3)", formProperties, discipline,
                category, topic);

        StringBuilder query = new StringBuilder();
        query.append("select t from TestStatistics t");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (topic != null) {
            query.append(" join t.test.topics tt where tt = :topic and");
            parameters.put("topic", topic);
        } else if (category != null) {
            query.append(" where t.test.category = :category and");
            parameters.put("category", category);
        } else if (discipline != null) {
            query.append(", Discipline d join d.categories c where t.test.category = c and d = :discipline and");
            parameters.put("discipline", discipline);
        } else {
            query.append(" where");
        }

        query.append(" t.test.active = true");

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< find(FormProperties, Discipline, Category, Topic)");

        return formTable;
    }

    public void calculateStatistics() {
        logger.debug("> calculateStatistics()");

        Query deleteQuery = getEntityManager().createNativeQuery(DELETE_STATISTICS);
        int rowsDeleted = deleteQuery.executeUpdate();
        logger.debug("*  #0 rows deleted.", rowsDeleted);

        Query insertQuery = getEntityManager().createNativeQuery(INSERT_STATISTICS);
        int rowsInserted = insertQuery.executeUpdate();
        logger.debug("*  #0 rows inserted.", rowsInserted);

        // TODO: Find out how to deal with cached entities.

        logger.debug("< calculateStatistics()");
    }
}