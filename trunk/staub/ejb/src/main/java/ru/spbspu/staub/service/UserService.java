package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.RoleEnum;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;

/**
 * Local Interface for <code>UserServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface UserService extends GenericService<User, Integer> {
    /**
     * Searches users of a specified group.
     *
     * @param formProperties the form properties
     * @param role           the role
     *
     * @return the table of results
     */
    FormTable find(FormProperties formProperties, RoleEnum role);

    /**
     * Fetches specific user by username and password.
     *
     * @param username specific user name
     * @param password specific user password
     *
     * @return fetched user
     */
    User find(String username, String password);

    /**
     * Saves or updates a user.
     *
     * @param user     the user
     * @param password the unencrypted password if change required
     *
     * @return the updated entity
     */
    User save(User user, String password);

    /**
     * Checks whether a username is not registered already.
     *
     * @param username the username
     *
     * @return <code>true</code> if the username is unique; <code>false</code> otherwise
     */
    boolean isUsernameUnique(String username);
}
