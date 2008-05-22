package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.DisciplineService;

/**
 * Webbean for manipulating list data of <code>Discipline</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("disciplineListBean")
@Scope(SESSION)
public class DisciplineListBean extends GenericListBean<Discipline> {
    private static final long serialVersionUID = 554953481314388855L;

    @In
    private DisciplineService disciplineService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return disciplineService.findAll(formProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            disciplineService.remove(getSelected());
            doRefresh();
        } catch (RemoveException e) {
            addFacesMessageFromResourceBundle("common.messages.deleteFailed");
        }
    }

    public String showCategories() {
        Contexts.getConversationContext().set(Discipline.class.getName(), getSelected());
        return doView("categoryList");
    }
}
