package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.Category;

import javax.ejb.Stateless;

/**
 * Stateless EJB Service for manipulations with <code>Category</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("categoryService")
@AutoCreate
@Stateless
public class CategoryServiceBean extends GenericServiceBean<Category, Integer> implements CategoryService {
}
