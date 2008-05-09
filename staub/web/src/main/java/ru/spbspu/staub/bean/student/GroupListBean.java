package ru.spbspu.staub.bean.student;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.GroupService;

/**
 * Webbean for manipulating list data of <code>Group</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("groupListBean")
@Scope(SESSION)
public class GroupListBean extends GenericListBean<Group> {
    private static final long serialVersionUID = 554953481314388855L;

    @In
    private GroupService groupService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return groupService.findAll(formProperties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            groupService.remove(getSelected());
            doRefresh();
        } catch (RemoveException e) {
            addFacesMessageFromResourceBundle("common.messages.deleteFailed");
        }
    }
}
