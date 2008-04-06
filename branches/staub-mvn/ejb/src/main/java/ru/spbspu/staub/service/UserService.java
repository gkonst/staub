package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.FormProperties;

import javax.ejb.Local;

/**
 * Local Interface for <code>UserServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface UserService extends GenericService<User, String> {
    /**
     * Fetches specific user by username and password.
     *
     * @param username specific user name
     * @param password specific user password
     * @return fetched user
     */
    User findUserByUserNameAndPassword(String username, String password);

    /**
     * Fetches users available for being assigned some test.
     *
     * @param formProperties form properties
     * @return result fetch
     */
    FormTable findUsersForAssign(FormProperties formProperties);
}
