package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Stateless EJB Service for manipulations with <code>Category</code> entity.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Name("categoryService")
@AutoCreate
@Stateless
public class CategoryServiceBean extends GenericServiceBean<Category, Integer> implements CategoryService {
    @EJB
    private QuestionService questionService;

    @EJB
    private TestService testService;

    @SuppressWarnings("unchecked")
    public List<Category> findCategories(Discipline discipline) {
        Query q = getEntityManager().createQuery("select c from Category c where c.discipline = :discipline");
        q.setParameter("discipline", discipline);
        return q.getResultList();
    }

    public FormTable findCategories(FormProperties formProperties, Discipline discipline) {
        logger.debug("> findCategories(FormProperties=#0, Discipline=#1)", formProperties, discipline);

        StringBuilder query = new StringBuilder();
        query.append("select c from Category c");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (discipline != null) {
            query.append(" where c.discipline = :discipline");
            parameters.put("discipline", discipline);
        }

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< findCategories(FormProperties, Discipline)");

        return formTable;
    }

    @Override
    public void remove(Category category) throws RemoveException {
        logger.debug("> remove(Category=#0)", category);

        Category c = getEntityManager().merge(category);
        if ((questionService.countQuestions(c) + testService.countTests(c)) == 0) {
            logger.debug("*  No related entities found.");
            getEntityManager().remove(c);
            logger.debug("*  Category removed from a database.");
        } else {
            logger.debug("*  Related entities exist.");
            throw new RemoveException("Could not remove a category.");
        }

        logger.debug("< remove(Category)");
    }
}
