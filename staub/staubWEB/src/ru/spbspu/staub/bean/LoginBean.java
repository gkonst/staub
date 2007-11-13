package ru.spbspu.staub.bean;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import ru.spbspu.staub.dao.UserDAO;
import ru.spbspu.staub.entity.User;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("loginBean")
public class LoginBean {
    @Logger
    private Log logger;

    @Out(required = false, scope = ScopeType.SESSION)
    private User user;

    @In
    private UserDAO userDAO;

    public boolean authenticate() {
        logger.debug("--> Authentinicating user(username=#{identity.username}), password=#{identity.password}");
        user = userDAO.findUserByUserNameAndPassword(Identity.instance().getUsername(), Identity.instance().getPassword());
        logger.debug("<-- Authentinicating ok(result=" + (user != null) + ").");
        return user != null;
    }
}
