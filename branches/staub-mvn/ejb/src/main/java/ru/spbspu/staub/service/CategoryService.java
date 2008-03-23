package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Category;

import javax.ejb.Local;

/**
 * Local Interface for <code>CategoryServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface CategoryService extends GenericService<Category, Integer> {
}
