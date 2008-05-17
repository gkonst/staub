package ru.spbspu.staub.bean.admin;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import ru.spbspu.staub.bean.BeanMode;
import ru.spbspu.staub.bean.GenericDetailBean;
import ru.spbspu.staub.entity.RoleEnum;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.service.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * Webbean for manipulating detail data of <code>User</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("userDetailBean")
@Scope(SESSION)
public class UserDetailBean extends GenericDetailBean<User> {
    private static final long serialVersionUID = 6034450093340030424L;

    @In
    private UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillModel(Integer modelId) {
        if (isCreateMode()) {
            setModel(new User());
            RoleEnum role = (RoleEnum) Contexts.getConversationContext().get(RoleEnum.class.getName());
            if (role != null) {
                getModel().setRole(role);
            }
        } else {
            setModel(userService.findById(modelId));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave() {
        logger.debug("Saving user...");
        setModel(userService.saveUser(getModel(), getModel().getPassword()));
        logger.debug("  Changing bean mode -> " + BeanMode.VIEW_MODE);
        setBeanMode(BeanMode.VIEW_MODE);
        addFacesMessageFromResourceBundle("common.messages.saveSuccess");
        logger.debug("Saving... OK");
    }

    public void validateUsername(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if(!userService.isUsernameUnique(String.valueOf(value))) {
            throw new ValidatorException(new FacesMessage("User name must be unique"));
        }
    }

    public RoleEnum[] getRoleArray() {
        return RoleEnum.values();
    }
}
