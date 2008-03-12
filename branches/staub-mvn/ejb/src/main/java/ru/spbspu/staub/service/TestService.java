package ru.spbspu.staub.service;

import ru.spbspu.staub.entity.Test;

import javax.ejb.Local;

/**
 * Local Interface for <code>TestServiceBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface TestService extends GenericService<Test, Integer> {
}
