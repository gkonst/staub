package ru.spbspu.staub.bean;

import static org.jboss.seam.ScopeType.EVENT;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.security.Identity;
import org.jboss.seam.ScopeType;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.service.UserService;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("loginBean")
@Scope(EVENT)
public class LoginBean extends GenericBean {

    @In
    private UserService userService;

    @Out(scope = ScopeType.SESSION)
    private User user;

    public boolean authenticate() {
        logger.debug(">>> Authentinicating user(username=#{identity.username}), password=#{identity.password}");
        user = userService.findUserByUserNameAndPassword(Identity.instance().getUsername(), Identity.instance().getPassword());
        logger.debug("<<< Authentinicating ok(result=" + (user != null) + ").");
        return user != null;
    }
}
