package ru.spbspu.staub.bean;

import static org.jboss.seam.ScopeType.EVENT;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;
import ru.spbspu.staub.dao.UserDAO;
import ru.spbspu.staub.entity.User;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("loginBean")
@Scope(EVENT)
public class LoginBean extends GenericBean {

    @In
    private UserDAO userDAO;

    public boolean authenticate() {
        logger.debug("--> Authentinicating user(username=#{identity.username}), password=#{identity.password}");
        User user = userDAO.findUserByUserNameAndPassword(Identity.instance().getUsername(), Identity.instance().getPassword());
        logger.debug("<-- Authentinicating ok(result=" + (user != null) + ").");
        return user != null;
    }
}
