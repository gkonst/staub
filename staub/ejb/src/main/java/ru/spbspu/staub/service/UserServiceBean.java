package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.User;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
            Query q = getEntityManager()
                    .createQuery("select u from User u where u.username = :username and u.password = :password");
            q.setParameter("username", username);
            q.setParameter("password", password);

            user = (User) q.getSingleResult();
        } catch (NoResultException ex) {
            // do nothing
        }
        return user;
    }
}