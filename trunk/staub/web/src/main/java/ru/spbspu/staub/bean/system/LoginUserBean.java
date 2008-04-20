package ru.spbspu.staub.bean.system;

import org.jboss.seam.ScopeType;
import org.jboss.seam.core.Expressions;
import static org.jboss.seam.ScopeType.EVENT;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;
import ru.spbspu.staub.bean.GenericBean;
import ru.spbspu.staub.entity.*;
import ru.spbspu.staub.service.UserService;
import ru.spbspu.staub.service.GroupService;
import ru.spbspu.staub.service.StudentService;

import javax.faces.model.SelectItem;
import java.util.List;
import java.util.ArrayList;

/**
 * Webbean for logging into the system as <code>User</code>.
 *
 * @author Konstantin Grigoriev
 */
@Name("loginUserBean")
@Scope(ScopeType.EVENT)
public class LoginUserBean extends GenericBean {

    private String username;
    private String password;

    @In
    private Identity identity;

    @In
    private UserService userService;

    @Out(scope = ScopeType.SESSION, required = false)
    private User user;

    public boolean authenticate() {
        logger.debug(">>> Authentinicating user(username=#{identity.username}), password=#{identity.password}");
        user = userService.findUserByUserNameAndPassword(identity.getUsername(), identity.getPassword());
        logger.debug(" User Role : #0", user.getRole());
        identity.addRole(String.valueOf(user.getRole()));
        logger.debug("<<< Authentinicating ok(result=#0)", user != null);
        return user != null;
    }

    public String login() {
        identity.setUsername(username);
        identity.setPassword(password);
        identity.setAuthenticateMethod(Expressions.instance().createMethodExpression("#{loginUserBean.authenticate}"));
        return identity.login();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
