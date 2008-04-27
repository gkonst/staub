package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;

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

        long count = getQuestionsCount(c) + getTestsCount(c);
        if (count == 0) {
            logger.debug("*  No relations found.");
        } else {
            logger.debug("*  Relations exist.");
            throw new IllegalArgumentException("Could not remove a category.");
        }

        getEntityManager().remove(c);

        logger.debug("< remove(category=#0)", category);
    }

    private long getQuestionsCount(Category category) {
        Query q = getEntityManager().createQuery("select count(q) from Category c join c.topics t, Question q where q.topic = t and c = :category");
        q.setParameter("category", category);
        return (Long) q.getSingleResult();
    }

    private long getTestsCount(Category category) {
        Query q = getEntityManager().createQuery("select count(t) from Test t where t.category = :category");
        q.setParameter("category", category);
        return (Long) q.getSingleResult();
    }
}
