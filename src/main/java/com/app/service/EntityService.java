package com.app.service;

import java.util.List;

/**
 * @author Sukhov Valerii valerii.sukhov@gmail.com
 */
public interface EntityService<T> {
    T find(Long id);
    void add(T entity);
    T update(T entity);
    void remove(Long id);
    List<T> list();
}
