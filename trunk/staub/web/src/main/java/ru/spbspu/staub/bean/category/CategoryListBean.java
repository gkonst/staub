package ru.spbspu.staub.bean.category;

import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.service.CategoryService;
import ru.spbspu.staub.service.DisciplineService;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.In;
import static org.jboss.seam.ScopeType.SESSION;

import java.util.List;

/**
 * Webbean for manipulating list data of <code>Category</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("categoryListBean")
@Scope(SESSION)
public class CategoryListBean extends GenericListBean<Category> {
    @In
    private CategoryService categoryService;
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return categoryService.findAll(formProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        categoryService.remove(getSelected());
        doRefresh();
    }
}
