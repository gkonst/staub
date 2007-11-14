package ru.spbspu.staub.dao;

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

    @SuppressWarnings("unchecked")
    public GenericDAOBean() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * {@inheritDoc}
     */
    public T findById(ID id, boolean lock) {
        T entity;
        if (lock) {
            entity = getEntityManager().find(getEntityClass(), id);
            em.lock(entity, javax.persistence.LockModeType.WRITE);
        } else {
            entity = getEntityManager().find(getEntityClass(), id);
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return getEntityManager().createQuery("from " + getEntityClass().getName() ).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public T makePersistent(T entity) {
        return getEntityManager().merge(entity);
    }

    /**
     * {@inheritDoc}
     */
    public void makeTransient(T entity) {
        getEntityManager().remove(entity);
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
}
