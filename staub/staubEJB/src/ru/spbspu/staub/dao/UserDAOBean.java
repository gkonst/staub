package ru.spbspu.staub.dao;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Name("userDAO")
@AutoCreate
@Stateless
public class UserDAOBean extends GenericDAOBean<User, String> implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    public User findUserByUserNameAndPassword(String username, String password) {
        User user = null;
        try {
            user = (User)em.createQuery("select u from User u where u.username=:username and u.password=:password")
                    .setParameter("username", username).setParameter("password", password).getSingleResult();
        } catch (NoResultException ex) {
            return user;
        }
        return user;
    }
}
