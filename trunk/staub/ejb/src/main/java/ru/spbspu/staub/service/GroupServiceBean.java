package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Topic;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

/**
 * The <code>GroupServiceBean</code> is a stateless EJB service to manipulate <code>Group</code> entities.
 *
 * @author Alexander V. Elagin
 */
@Name("groupService")
@AutoCreate
@Stateless
public class GroupServiceBean extends GenericServiceBean<Group, Integer> implements GroupService {
    @EJB
    private StudentService studentService;

    public boolean isNameUnique(String name) {
        logger.debug("> isNameUnique(String=#0)", name);

        Query q = getEntityManager().createQuery("select count(g) from Group g where g.name = :name");
        q.setParameter("name", name);

        boolean unique = ((Long) q.getSingleResult() == 0);
        if (unique) {
            logger.debug("*  Name is unique.");
        } else {
            logger.debug("*  Name is not unique.");
        }

        logger.debug("< isNameUnique(String)");

        return unique;
    }

    public FormTable getStatistics(FormProperties formProperties, Group group, Discipline discipline, Category category,
                                   Topic topic, Date begin, Date end) {
        logger.debug("> getStatistics(FormProperties=#0, Group=#1, Discipline=#2, Category=#3, Topic=#4, Date=#5, Date=#6)",
                formProperties, group, discipline, category, topic, begin, end);

        StringBuilder query = new StringBuilder();
        query.append("select NEW ru.spbspu.staub.statistics.GroupStatistics(g, t, count(tt), sum(cast(tt.testPassed as integer))) from TestTrace tt join tt.student s join s.group g join tt.test t");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (topic != null) {
            query.append(" join t.test.topics tc");
        } else if (discipline != null) {
            query.append(", Discipline d join d.categories c");
        }

        query.append(" where");

        if (group != null) {
            query.append(" g = :group and");
            parameters.put("group", group);
        }

        if (topic != null) {
            query.append(" tc = :topic and");
            parameters.put("topic", topic);
        } else if (category != null) {
            query.append(" t.category = :category and");
            parameters.put("category", category);
        } else if (discipline != null) {
            query.append(" t.category = c and d = :discipline and");
            parameters.put("discipline", discipline);
        }

        if (begin != null) {
            query.append(" tt.started >= :begin and");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(begin);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            parameters.put("begin", calendar.getTime());
        }

        if (end != null) {
            query.append(" tt.started <= :end and");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);

            parameters.put("end", calendar.getTime());
        }

        query.append(" tt.finished is not null and t.active = true group by g, t");


        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< getStatistics(FormProperties, Group, Discipline, Category, Topic, Date, Date)");

        return formTable;
    }

    @Override
    public void remove(Group group) throws RemoveException {
        logger.debug("> remove(Group=#0)", group);

        Group g = getEntityManager().merge(group);
        if ((studentService.count(g)) == 0) {
            logger.debug("*  No related Student entities found.");
            getEntityManager().remove(g);
            logger.debug("*  Group removed from a database.");
        } else {
            logger.debug("*  Related Student entities exist.");
            throw new RemoveException("Could not remove a group.");
        }

        logger.debug("< remove(Group=#0)", group);
    }
}