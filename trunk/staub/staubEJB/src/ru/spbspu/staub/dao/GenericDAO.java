package ru.spbspu.staub.dao;

import java.io.Serializable;

/**
 * Base interface for dao interfaces,
 * defines basic operations with entity.
 * @author Konstantin Grigoriev
 */
public interface GenericDAO<T, ID extends Serializable> {
    T findById(ID id);
}
