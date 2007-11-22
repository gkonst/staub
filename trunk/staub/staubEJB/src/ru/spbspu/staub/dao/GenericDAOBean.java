package ru.spbspu.staub.dao;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;

import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Implements the generic CRUD data access operations using Java Persistence APIs.
 * Uses traditional 1:1 appraoch for Entity:DAO design.
 * @author Konstantin Grigoriev
 */
public abstract class GenericDAOBean<T, ID extends Serializable> implements GenericDAO<T, ID>{

    private Class<T> entityClass;

    @PersistenceContext
    private EntityManager em;

    @Logger
    protected Log logger;

    @SuppressWarnings("unchecked")
    public GenericDAOBean() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * {@inheritDoc}
     */
    public T findById(ID id, boolean lock) {
        logger.debug(">>> Find by id (entity=#0, id=#1)...", entityClass.getName(), id);
        T entity;
        if (lock) {
            entity = getEntityManager().find(getEntityClass(), id);
            em.lock(entity, javax.persistence.LockModeType.WRITE);
        } else {
            entity = getEntityManager().find(getEntityClass(), id);
        }
        logger.debug("<<< Find by id...Ok(#0 found)", entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        logger.debug(">>> Finding all(entity=#0)...", entityClass.getName());
        List<T> result = getEntityManager().createQuery("from " + getEntityClass().getName() ).getResultList();
        logger.debug("<<< Finding all...Ok(#0 found)", result.size());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public T makePersistent(T entity) {
        logger.debug(">>> Making persistent(entity=#0)...", entityClass.getName());
        entity = getEntityManager().merge(entity);
        logger.debug("<<< Making persistent...Ok");
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    public void makeTransient(T entity) {
        logger.debug(">>> Making transient(entity=#0)...", entityClass.getName());
        getEntityManager().remove(entity);
        logger.debug("<<< Making transient...Ok");
    }

    public void flush() {
        getEntityManager().flush();
    }

    public void clear() {
        getEntityManager().clear();
    }


    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    protected EntityManager getEntityManager() {
        if (em == null)
            throw new IllegalStateException("EntityManager has not been set on DAO before usage");
        return em;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Remove
    public void destroy() {}
}
