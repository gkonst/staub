package ru.spbspu.staub.dao;

import ru.spbspu.staub.entity.User;

import javax.ejb.Local;

/**
 * Local Interface for <code>UserDAOBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface UserDAO extends GenericDAO<User, String> {
    /**
     * Fetches specific user by username and password.
     * @param username specific user name
     * @param password specific user password
     * @return fetched user
     */
    User findUserByUserNameAndPassword(String username, String password);
}
