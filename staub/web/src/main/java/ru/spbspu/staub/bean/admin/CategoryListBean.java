package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Category;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.CategoryService;

/**
 * Webbean for manipulating list data of <code>Category</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("categoryListBean")
@Scope(SESSION)
public class CategoryListBean extends GenericListBean<Category> {
    private static final long serialVersionUID = 1729729174867773807L;

    @In
    private CategoryService categoryService;

    private Discipline discipline;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return categoryService.findCategories(formProperties, discipline);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepareBean() {
        Discipline discipline = (Discipline) Contexts.getConversationContext().get(Discipline.class.getName());
        if (discipline != null) {
            this.discipline = discipline;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            categoryService.remove(getSelected());
            doRefresh();
        } catch (RemoveException e) {
            addFacesMessageFromResourceBundle("common.messages.deleteFailed");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String doCreate() {
        Contexts.getConversationContext().set(Discipline.class.getName(), discipline);
        return super.doCreate();
    }

    public String showTopics() {
        Contexts.getConversationContext().set(Category.class.getName(), getSelected());
        return doView("topicList");
    }
}
