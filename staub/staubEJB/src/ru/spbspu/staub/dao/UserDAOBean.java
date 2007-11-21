package ru.spbspu.staub.dao;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.User;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * Stateless EJB DAO for manipulations with <code>User</code> entity. 
 *
 * @author Konstantin Grigoriev
 */
@Name("userDAO")
@AutoCreate
@Stateless
public class UserDAOBean extends GenericDAOBean<User, String> implements UserDAO {

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
}
