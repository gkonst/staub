package ru.spbspu.staub.service;

import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;

import java.io.Serializable;
import java.util.List;

/**
 * Base interface for service interfaces,
 * defines basic operations with entity.
 *
 * @author Konstantin Grigoriev
 */
public interface GenericService<T, ID extends Serializable> {
    /**
     * Fetches entity by specific id.
     *
     * @param id id to fetch
     *
     * @return fetched entity
     */
    T findById(ID id);

    /**
     * Fetches all entities.
     * May cause <code>java.lang.OutOfMemoryError</code> in case
     * big amount of entities.
     *
     * @return parametrized list of entities
     */
    List<T> findAll();

    /**
     * Fetches all rows for current entity, using form properties.
     * Encapsulates paging, sort and search features.
     *
     * @param formProperties form properties for fetching
     *
     * @return result fetch
     */
    FormTable findAll(FormProperties formProperties);

    /**
     * Makes entity persistent(i.e. inserts to table).
     * Uses <code>EntityManager.merge(Object entity)</code> method.
     *
     * @param entity entity to persist
     *
     * @return persistent entity(with id field filled)
     */
    T makePersistent(T entity);

    /**
     * Removes an entity from a database.
     *
     * @param entity the entity to remove
     *
     * @throws RemoveException if a remove operation could not be performed.
     */
    void remove(T entity) throws RemoveException;
}
