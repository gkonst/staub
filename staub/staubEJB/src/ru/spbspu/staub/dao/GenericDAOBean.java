package ru.spbspu.staub.dao;

import java.io.Serializable;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
public class GenericDAOBean<T, ID extends Serializable> implements GenericDAO<T, ID>{
    public T findById(ID id) {
        // TODO implement
        return null;
    }
}
