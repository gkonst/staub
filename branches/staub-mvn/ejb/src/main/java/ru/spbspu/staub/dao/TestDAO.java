package ru.spbspu.staub.dao;

import ru.spbspu.staub.entity.Test;

import javax.ejb.Local;

/**
 * Local Interface for <code>TestDAOBean</code> Stateless EJB.
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface TestDAO extends GenericDAO<Test, Integer> {
}
