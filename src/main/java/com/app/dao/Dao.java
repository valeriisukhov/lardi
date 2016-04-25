package com.app.dao;

import java.util.List;
import java.util.Map;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public interface Dao<T> {
    T find(Long id);
    void add(T entity);
    T update(T entity);
    void remove(T entity);
    long count();
    List<T> list();
    List<T> list(String orderBy);
    List<T> list(int start, int limit, String orderBy, String sortOrder);
    List<T> list(int start, int limit, String orderBy, Map<String, Object> where);
    List<T> list(int start, int limit, String orderBy, String sortOrder, Map<String, Object> where);
}
