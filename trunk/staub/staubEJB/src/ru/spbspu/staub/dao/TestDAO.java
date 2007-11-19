package ru.spbspu.staub.dao;

import ru.spbspu.staub.entity.Test;

import javax.ejb.Local;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface TestDAO extends GenericDAO<Test, Integer> {
}
