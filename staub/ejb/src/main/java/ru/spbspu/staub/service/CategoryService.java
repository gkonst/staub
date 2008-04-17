package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;

import javax.ejb.Local;
import java.util.List;

/**
 * Local Interface for <code>CategoryServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 * @author Alexander V. Elagin
 */
@Local
public interface CategoryService extends GenericService<Category, Integer> {
    /**
     * Returns a list of categories associated with a discipline.
     *
     * @param discipline the discipline
     *
     * @return the list of categories
     */
    List<Category> getCategories(Discipline discipline);
}
