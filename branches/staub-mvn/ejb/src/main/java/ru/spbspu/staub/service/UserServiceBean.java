package ru.spbspu.staub.service;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.entity.RoleEnum;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.interceptors.CallLogger;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.interceptor.Interceptors;
import java.util.Map;
import java.util.HashMap;

/**
 * Stateless EJB Service for manipulations with <code>User</code> entity.
 *
 * @author Konstantin Grigoriev
 */
@Name("userService")
@AutoCreate
@Interceptors({CallLogger.class})
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

    public FormTable findUsersToAssign(FormProperties formProperties) {
        String query = "select u from User u where u.role = :role";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("role", RoleEnum.USER);
        return findAll(query, formProperties, parameters);
    }
}
