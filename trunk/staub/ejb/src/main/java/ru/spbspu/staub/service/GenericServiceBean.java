package ru.spbspu.staub.service;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import ru.spbspu.staub.exception.RemoveException;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.model.list.SortItem;

import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements the generic CRUD data access operations using Java Persistence APIs.
 * Uses traditional 1:1 appraoch for Entity:DAO design.
 *
 * @author Konstantin Grigoriev
 */
public abstract class GenericServiceBean<T extends Serializable, ID extends Serializable> implements GenericService<T, ID> {

    private static final Pattern FROM_PATTERN = Pattern.compile("(^|\\s)(from)\\s", Pattern.CASE_INSENSITIVE);
    private static final Pattern ORDER_PATTERN = Pattern.compile("\\s(order)(\\s)+by\\s", Pattern.CASE_INSENSITIVE);
//    private static final Pattern WHERE_PATTERN = Pattern.compile("\\s(where)\\s", Pattern.CASE_INSENSITIVE);
    private static final Pattern GROUP_PATTERN = Pattern.compile("\\s(group)(\\s)+by\\s", Pattern.CASE_INSENSITIVE);
//    private static final Pattern ORDER_CLAUSE_PATTERN = Pattern.compile("^[\\w\\.,\\s]*$");

    private Class<T> entityClass;

    @PersistenceContext
    private EntityManager em;

    @Logger
    protected Log logger;

    @SuppressWarnings("unchecked")
    public GenericServiceBean() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * {@inheritDoc}
     */
    public T findById(ID id) {
        logger.debug(">>> Find by id (entity=#0, id=#1)...", entityClass.getName(), id);
        T entity = getEntityManager().find(getEntityClass(), id);
        logger.debug("<<< Find by id...Ok(#0 found)", entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        logger.debug(">>> Finding all(entity=#0)...", entityClass.getName());
        StringBuilder queryString = new StringBuilder().append("from ").append(getEntityClass().getName());
        List<T> result = getEntityManager().createQuery(queryString.toString()).getResultList();
        logger.debug("<<< Finding all...Ok(#0 found)", result.size());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public FormTable findAll(FormProperties formProperties) {
        StringBuilder queryString = new StringBuilder("select o from ");
        queryString.append(getEntityClass().getName());
        queryString.append(" o");
        return findAll(queryString.toString(), formProperties, new HashMap<String, Object>(0));
    }

    /**
     * Fetches all rows for specific queries, using form properties and query
     * parameters. Encapsulates paging, sort and search features.
     *
     * @param queryString     specific query string
     * @param formProperties  form properties for fetching
     * @param queryParameters query parameters
     * @return result fetch
     */
    protected FormTable findAll(String queryString, FormProperties formProperties, Map<String, Object> queryParameters) {
        logger.debug(">>> Finding all with paging(entity=#0)...", entityClass.getName());
        logger.debug(" query string : #0", queryString);
        logger.debug(" query parameters : #0", queryParameters);
        FormTable table = new FormTable();
        table.setFullCount(countQuery(queryString, queryParameters));
        if (table.getFullCount() != 0) {
            Query q;
            SortItem sortItem = formProperties.getSort();
            if (sortItem != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(queryString);
                sb.append(" order by ");
                sb.append(sortItem.getField());
                if (sortItem.isDesc()) {
                    sb.append(" desc");
                }
                String modifiedQueryString = sb.toString();
                logger.debug(" modified query string : #0", modifiedQueryString);
                q = getEntityManager().createQuery(modifiedQueryString);
            } else {
                q = getEntityManager().createQuery(queryString);
            }

            for (Map.Entry<String, Object> param : queryParameters.entrySet()) {
                q.setParameter(param.getKey(), param.getValue());
            }
            // TODO implement search features
            wrapQueryForPaging(q, formProperties.getCurrentPage(), formProperties.getRowsOnPage());
            table.setRows(q.getResultList());
        } else {
            logger.debug(" count query returns 0 -> skipping query");
            table.setRows(Collections.EMPTY_LIST);
        }
        logger.debug("<<< Finding all with paging...Ok(#0 found)", table.getFullCount());
        return table;
    }

    /**
     * Counts total rows in table for current query.
     *
     * @param queryString     current query string
     * @param queryParameters some parameters (search values for example)
     * @return total rows in table
     */
    @SuppressWarnings("unchecked")
    private long countQuery(String queryString, Map<String, Object> queryParameters) {
        logger.debug("  count query...");

        Matcher fromMatcher = FROM_PATTERN.matcher(queryString);
        if (!fromMatcher.find()) {
            throw new IllegalArgumentException("no from clause found in query");
        }
        int fromLoc = fromMatcher.start(2);

        Matcher orderMatcher = ORDER_PATTERN.matcher(queryString);
        int orderLoc = orderMatcher.find() ? orderMatcher.start(1) : queryString.length();

        String countQueryString = "select count(*) " + queryString.substring(fromLoc, orderLoc);

        logger.debug(" countQueryString : #0", countQueryString);

        Query countQuery = getEntityManager().createQuery(countQueryString);
        for (Map.Entry<String, Object> param : queryParameters.entrySet()) {
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        Matcher groupMatcher = GROUP_PATTERN.matcher(countQueryString);
        boolean groupFound = groupMatcher.find();

        long fullCount;
        if (!groupFound) {
            fullCount = (Long) countQuery.getSingleResult();
        } else {
            // Subqueries are not supported. No better solution exists.
            List<Long> rows = countQuery.getResultList();
            fullCount = rows.size();
        }

        logger.debug("  count query...#0 rows...OK", fullCount);
        return fullCount;
    }

    /**
     * Sets firstResult number and maxResult number
     * for current query instance.
     *
     * @param query     current query instance
     * @param curPage   page to show
     * @param recOnPage rows on page to show
     */
    private void wrapQueryForPaging(Query query, int curPage, int recOnPage) {
        int startAtRow;
        int howManyRows;
        startAtRow = recOnPage * (curPage - 1);
        if (startAtRow < 0) {
            startAtRow = 0;
        }
        howManyRows = recOnPage;
        logger.debug("   startAtRow : #0 howManyRows : #1", startAtRow, howManyRows);
        query.setFirstResult(startAtRow);
        query.setMaxResults(howManyRows);
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
        if (em == null) {
            throw new IllegalStateException("EntityManager has not been set on Service before usage");
        }
        return em;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Remove
    public void destroy() {
    }

    public void remove(T entity) throws RemoveException {
        logger.debug(">>> remove(entity=#0)...", entityClass.getName());
        getEntityManager().remove(entity);
        logger.debug("<<< remove...Ok");
    }
}
