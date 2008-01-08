package ru.spbspu.staub.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Base interface for dao interfaces,
 * defines basic operations with entity.
 * @author Konstantin Grigoriev
 */
public interface GenericDAO<T, ID extends Serializable> {
    /**
     * Fetches entity by specific id.
     * @param id id to fetch
     * @param lock need to get lock or not
     * @return fetched entity
     */
    T findById(ID id, boolean lock);

    /**
     * Fetches all entities.
     * May cause <code>java.lang.OutOfMemoryError</code> in case
     * big amount of entities.
     * @return parametrized list of entities
     */
    List<T> findAll();

    /**
     * Makes entity persistent(i.e. inserts to table).
     * Uses <code>EntityManager.merge(Object entity)</code> method.
     * @param entity entity to persist
     * @return persistent entity(with id field filled)
     */
    T makePersistent(T entity);

    /**
     * Makes entity transient(i.e. deletes from table).
     * @param entity entity to remove
     */
    void makeTransient(T entity);
}
