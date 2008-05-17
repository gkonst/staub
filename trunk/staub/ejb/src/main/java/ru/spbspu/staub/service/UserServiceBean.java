package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.RoleEnum;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Stateless EJB Service for manipulations with <code>User</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("userService")
@AutoCreate
@Stateless
public class UserServiceBean extends GenericServiceBean<User, String> implements UserService {
    public FormTable find(FormProperties formProperties, RoleEnum role) {
        logger.debug("> find(FormProperties=#0, RoleEnum=#1)", formProperties, role);

        StringBuilder query = new StringBuilder();
        query.append("select u from User u");

        Map<String, Object> parameters = new HashMap<String, Object>();

        if (role != null) {
            query.append(" where u.role = :role");
            parameters.put("role", role);
        }

        String queryString = query.toString();
        logger.debug("*  Query: #0", queryString);

        FormTable formTable = findAll(queryString, formProperties, parameters);

        logger.debug("< find(FormProperties, RoleEnum)");

        return formTable;
    }

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

    public User saveUser(User user, String password) {
        if (password != null) {
            user.setPassword(calculateHash(password));
        }
        return makePersistent(user);
    }

    private String calculateHash(String s) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) { // Should not happen.
            logger.error("Could not find a message digest algorithm.");
            return null;
        }

        messageDigest.update(s.getBytes());

        byte[] hash = messageDigest.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(b);
        }

        return sb.toString();
    }
}
