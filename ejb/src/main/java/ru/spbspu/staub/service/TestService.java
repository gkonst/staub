package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Test;
import ru.spbspu.staub.entity.User;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import javax.ejb.Local;

/**
 * Local Interface for <code>TestServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface TestService extends GenericService<Test, Integer> {
    /**
     * Fetches all tests assigned for user.
     *
     * @param formProperties form properties
     * @param user specific user
     * @return result fetch
     */
    FormTable findAllToPassForUser(FormProperties formProperties, User user);
}
