package ru.spbspu.staub.service;

import java.io.Serializable;
import java.util.List;

/**
 * Base interface for service interfaces,
 * defines basic operations with entity.
 * @author Konstantin Grigoriev
 */
public interface GenericService<T, ID extends Serializable> {
    /**
     * Fetches entity by specific id.
     * @param id id to fetch
     * @return fetched entity
     */
    T findById(ID id);

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
