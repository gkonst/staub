package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * Stateless EJB Service for manipulations with <code>User</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("userService")
@AutoCreate
@Stateless
public class UserServiceBean extends GenericServiceBean<User, String> implements UserService {

    public User findUserByUserNameAndPassword(String username, String password) {
        User user = null;
        try {
            user = (User)getEntityManager().createQuery("select u from User u where u.username=:username and u.password=:password")
                    .setParameter("username", username).setParameter("password", password).getSingleResult();
        } catch (NoResultException ex) {
            return user;
        }
        return user;
    }

    public FormTable findUsersForAssign(FormProperties formProperties) {
        // TODO implement method
        return findAll(formProperties);
    }
}
