package com.app.dao.impl;

import com.app.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public class DaoImpl<T> implements Dao<T> {
    private final transient Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final Class<T> domainClass;
    @PersistenceContext
    protected EntityManager entityManager;

    public DaoImpl(Class<T> domainClass) {
        this.domainClass = domainClass;
    }
    public Class<T> getDomainClass() {
        return domainClass;
    }
    public T find(Long id) {
        T entity = null;
        try {
            entity = entityManager.find(domainClass, id);
        } catch (NoResultException e) {
            entity = null;
        }
        return entity;
    }
    public void add(T entity) {
        entityManager.persist(entity);
    }
    public T update(T entity) {
        return entityManager.merge(entity);
    }
    public void remove(T entity) {
        entityManager.remove(entity);
    }
    public long count() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(domainClass)));
        return entityManager.createQuery(query).getSingleResult();
    }
    public List<T> list() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(domainClass);
        Root<T> root = criteria.from(domainClass);
        criteria.select(root).orderBy(builder.asc(root.get("id")));
        List<T> resultList = entityManager.createQuery(criteria).getResultList();
        return resultList;
    }
    public List<T> list(String orderBy) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(domainClass);
        Root<T> root = criteria.from(domainClass);
        criteria.select(root).orderBy(builder.asc(root.get(orderBy)));
        List<T> resultList = entityManager.createQuery(criteria).getResultList();
        return resultList;
    }
    public List<T> list(int start, int limit, String orderBy, String sortOrder) {
        return list(start, limit, orderBy, sortOrder, new HashMap<String, Object>());
    }
    public List<T> list(int start, int limit, String orderBy, Map<String, Object> where) {
        return list(start, limit, orderBy, "asc", where);
    }
    public List<T> list(int start, int limit, String orderBy, String sortOrder, Map<String, Object> where) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(domainClass);
        Root<T> root = criteria.from(domainClass);

        ArrayList<Predicate> predicates = new ArrayList<Predicate>();
        Set<String> keys = where.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = where.get(key);
            javax.persistence.criteria.Path path = root.get(key);
            Predicate predicate = builder.equal(path, value);
            predicates.add(predicate);
        }
        Predicate[] predicatesArray = predicates.toArray(new Predicate[]{});
        Predicate predicate = builder.and(predicatesArray);
        criteria = criteria.select(root);
        criteria = criteria.where(predicate);
        if ("asc".equals(sortOrder)) {
            criteria = criteria.orderBy(builder.asc(root.get(orderBy)));
        } else {
            criteria = criteria.orderBy(builder.desc(root.get(orderBy)));
        }

        return entityManager.createQuery(criteria)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }
}
