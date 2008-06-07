package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

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
     * Searches categories of a specified discipline.
     *
     * @param discipline the discipline
     *
     * @return the list of categories
     */
    List<Category> find(Discipline discipline);

    /**
     * Searches categories of a specified discipline.
     *
     * @param formProperties the form properties
     * @param discipline     the discipline
     *
     * @return the table of reults
     */
    FormTable find(FormProperties formProperties, Discipline discipline);

    /**
     * Checks whether a code is not assigned to any category.
     *
     * @param code the code
     *
     * @return <code>true</code> if the code is unique; <code>false</code> otherwise
     */
    boolean isCodeUnique(String code);
}
