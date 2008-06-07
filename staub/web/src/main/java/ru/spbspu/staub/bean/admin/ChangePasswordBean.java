package ru.spbspu.staub.bean.admin;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.faces.FacesMessages;
import ru.spbspu.staub.bean.GenericBean;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.service.UserService;

/**
 * Webbean for changing <code>User</code> password.
 *
 * @author Konstantin Grigoriev
 */
@Name("changePasswordBean")
public class ChangePasswordBean extends GenericBean {
    private static final long serialVersionUID = -1597122442510107875L;

    @In
    @Out(scope = ScopeType.SESSION)
    private User user;

    @In
    private UserService userService;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public void doSave() {
        logger.debug("Changing password...");
        if (!checkOldPassword()) {
            addFacesMessageFromResourceBundle("system.changePassword.validation.oldPassword");
            logger.debug("Changing password... failed(oldPassword=#0,oldPasswordEntered=#1)", user.getPassword(), oldPassword);
        } else if (!newPassword.equals(confirmPassword)) {
            addFacesMessageFromResourceBundle("system.changePassword.validation.confirmPassword");
            logger.debug("Changing password... failed(newPassword=#0,confirmPassword=#1)", newPassword, confirmPassword);
        } else {
            user = userService.save(user, newPassword);
            logger.debug("Changing password... OK");
        }
    }

    public void doCancel() {
        FacesMessages.instance().clear();
    }

    private boolean checkOldPassword() {
        User search = userService.find(user.getUsername(), oldPassword);
        return search != null;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
