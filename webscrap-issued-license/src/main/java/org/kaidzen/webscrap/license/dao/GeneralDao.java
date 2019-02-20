package org.kaidzen.webscrap.license.dao;

import java.util.List;

public interface GeneralDao<T> {
    List<T> getAll();

    T getById(Integer Id);

    void add(T item);

    int addAll(List<T> items);

    void update(T item);

    void delete(Integer Id);

    boolean isWithIdExists(Integer Id);

    void addAllNonDuplicated(List<T> items);
}
