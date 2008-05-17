package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.GenericListBean;
import ru.spbspu.staub.entity.RoleEnum;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.UserService;

/**
 * Webbean for manipulating list data of <code>User</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("userListBean")
@Scope(SESSION)
public class UserListBean extends GenericListBean<User> {
    private static final long serialVersionUID = 1382757734676731956L;

    @In
    private UserService userService;

    private RoleEnum role;

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return userService.find(formProperties, role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doDelete() {
        try {
            userService.remove(getSelected());
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
        Contexts.getConversationContext().set(RoleEnum.class.getName(), role);
        return super.doCreate();
    }

    public void setRole() {
        doRefresh();
    }

    public RoleEnum[] getRoleArray() {
        return RoleEnum.values();
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}