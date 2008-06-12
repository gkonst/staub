package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

/**
 * The <code>QuestionStatisticsServiceBean</code> is a stateless EJB service to manipulate
 * <code>QuestionStatistics</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Name("questionStatisticsService")
@AutoCreate
@Stateless
public class QuestionStatisticsServiceBean extends GenericServiceBean<QuestionStatistics, Integer>
        implements QuestionStatisticsService {
    private static final String DELETE_STATISTICS = "delete from question_statistics";

    public static final String INSERT_STATISTICS = "insert into question_statistics " +
            "(fk_question, total_answers, correct_answers, correct_answers_pc, n1, n2, n3, n4, " +
            "k1, k2, k3, k4, last_update) " +
            "(select s.fk_question, " +
            "s.total_answers, " +
            "s.correct_answers, " +
            "100 * s.correct_answers / s.total_answers as correct_answers_pc, " +
            "s.n1, " +
            "s.n2, " +
            "s.n3, " +
            "s.n4, " +
            "(s.n1 + s.n3) / s.total_answers as k1, " +
            "case when (s.n2 + s.n4) = 0 then null else s.n2 / (s.n2 + s.n4) end as k2, " +
            "case when (s.n1 + s.n3) = 0 then null else s.n3 / (s.n1 + s.n3) end as k3, " +
            "(s.n1 + s.n4) / s.total_answers as k4, " +
            "s.last_update " +
            "from " +
            "(select qt.fk_question as fk_question, " +
            "count(*) as total_answers, " +
            "sum(cast(qt.correct as integer)) as correct_answers, " +
            "sum(cast((tt.test_passed and qt.correct) as integer)) as n1, " +
            "sum(cast((tt.test_passed and not qt.correct) as integer)) as n2, " +
            "sum(cast((not tt.test_passed and qt.correct) as integer)) as n3, " +
            "sum(cast((not tt.test_passed and not qt.correct) as integer)) as n4, " +
            "current_timestamp as last_update " +
            "from question_trace qt " +
            "join test_trace tt " +
            "on qt.fk_test_trace = tt.id " +
            "join question q " +
            "on qt.fk_question = q.id " +
            "where q.active = true " +
            "and qt.correct is not null " +
            "group by qt.fk_question) as s)";

    public QuestionStatistics find(Question question) {
        logger.debug("> find(Question=#0)", question);

        Query q = getEntityManager().createQuery("select q from QuestionStatistics q where q.question = :question");
        q.setParameter("question", question);

        QuestionStatistics questionStatistics = null;
        try {
            questionStatistics = (QuestionStatistics) q.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        logger.debug("< find(Question)");

        return questionStatistics;
    }

    public FormTable find(FormProperties formProperties, Discipline discipline, Category category, Topic topic,
                          Difficulty difficulty, Integer questionId) {
        logger.debug("> find(FormProperties=#0, Discipline=#1, Category=#2, Topic=#3, Difficulty=#4, Integer=#5)",
                formProperties, discipline, category, topic, difficulty, questionId);

        StringBuilder query = new StringBuilder();
        query.append("select q from QuestionStatistics q");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (questionId != null) {
            query.append(" where cast(q.question.id as string) like :questionId and");
            parameters.put("questionId", questionId.toString() + '%');
        } else if (topic != null) {
            query.append(" where q.question.topic = :topic and");
            parameters.put("topic", topic);
        } else if (category != null) {
            query.append(", Category c join c.topics t where q.question.topic = t and c = :category and");
            parameters.put("category", category);
        } else if (discipline != null) {
            query.append(", Discipline d join d.categories c join c.topics t where q.question.topic = t and d = :discipline and");
            parameters.put("discipline", discipline);
        } else {
            query.append(" where");
        }

        query.append(" q.question.active = true");

        if (difficulty != null) {
            query.append(" and q.question.difficulty = :difficulty");
            parameters.put("difficulty", difficulty);
        }

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< find(FormProperties, Discipline, Category, Topic, Difficulty, Integer)");

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