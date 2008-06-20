package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.data.question.CategoryDataType;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Category> find(Discipline discipline) {
        Query q = getEntityManager().createQuery("select c from Category c where c.discipline = :discipline");
        q.setParameter("discipline", discipline);
        return q.getResultList();
    }

    public FormTable find(FormProperties formProperties, Discipline discipline) {
        logger.debug("> find(FormProperties=#0, Discipline=#1)", formProperties, discipline);

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

        logger.debug("< find(FormProperties, Discipline)");

        return formTable;
    }

    public boolean isCodeUnique(String code) {
        logger.debug("> isCodeUnique(String=#0)", code);

        Query q = getEntityManager().createQuery("select count(c) from Category c where c.code = :code");
        q.setParameter("code", code);

        boolean unique = ((Long) q.getSingleResult() == 0);
        if (unique) {
            logger.debug("*  Code is unique.");
        } else {
            logger.debug("*  Code is not unique.");
        }

        logger.debug("< isCodeUnique(String)");

        return unique;
    }

    public Category importCategory(CategoryDataType categoryData, Discipline discipline) {
        logger.debug("> importCategory(CategoryDataType=#0, Discipline=#1)", categoryData, discipline);

        String code = categoryData.getCode();
        Category result = findByDisciplineAndCode(discipline, code);
        if (result == null) {
            logger.debug("*  Category with the given discipline and code values was not found.");

            Category category = new Category();
            category.setCode(code);
            category.setName(categoryData.getName());
            category.setDiscipline(discipline);

            result = makePersistent(category);
        }

        logger.debug("*  Result: #0", result);
        logger.debug("< importCategory(CategoryDataType, Discipline)");

        return result;
    }

    public CategoryDataType exportCategory(Integer id) {
        logger.debug("> exportCategory(Integer=#0)", id);

        Category category = null;
        try {
            category = getEntityManager().find(Category.class, id);
        } catch (NoResultException e) {
            // do nothing
        }

        CategoryDataType result = null;
        if (category != null) {
            result = new CategoryDataType();
            result.setCode(category.getCode());
            result.setName(category.getName());
        }

        logger.debug("*  Result: #0", result);
        logger.debug("< exportCategory(Integer)");

        return result;
    }

    @Override
    public void remove(Category category) throws RemoveException {
        logger.debug("> remove(Category=#0)", category);

        Category c = getEntityManager().merge(category);
        if ((questionService.count(c) + testService.count(c)) == 0) {
            logger.debug("*  No related entities found.");
            getEntityManager().remove(c);
            logger.debug("*  Category removed from a database.");
        } else {
            logger.debug("*  Related entities exist.");
            throw new RemoveException("Could not remove a category.");
        }

        logger.debug("< remove(Category)");
    }

    private Category findByDisciplineAndCode(Discipline discipline, String code) {
        Category category = null;

        Query q = getEntityManager().createQuery("select c from Category c where c.discipline = :discipline and c.code = :code");
        q.setParameter("discipline", discipline);
        q.setParameter("code", code);

        try {
            category = (Category) q.getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        return category;
    }
}
