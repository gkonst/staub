package ru.spbspu.staub.dao;

import ru.spbspu.staub.entity.User;

import javax.ejb.Local;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface UserDAO {
    User findUserByUserNameAndPassword(String username, String password);
}
