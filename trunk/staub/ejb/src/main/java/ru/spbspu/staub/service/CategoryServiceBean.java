package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

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
    public List<Category> getCategories(Discipline discipline) {
        Query q = getEntityManager().createQuery("select c from Category c where c.discipline = :discipline");
        q.setParameter("discipline", discipline);
        return q.getResultList();
    }

    @Override
    public void remove(Category category) {
        logger.debug("> remove(category=#0)", category);

        Category c = getEntityManager().merge(category);
        if ((questionService.countQuestions(c) + testService.countTests(c)) == 0) {
            logger.debug("*  No related entities found.");
            getEntityManager().remove(c);
            logger.debug("*  Category removed from a database.");
        } else {
            logger.debug("*  Related entities exist.");
            throw new IllegalArgumentException("Could not remove a category.");
        }

        logger.debug("< remove(category=#0)", category);
    }
}
