package org.kaidzen.webscrap.document.dao;

import java.util.List;

public interface GeneralDao<T> {
    List<T> getAll();

    T getById(Integer Id);

    void add(T item);

    int addAll(List<T> items);

    void update(T item);

    void delete(Integer Id);

    boolean isWithIdExists(Integer Id);

    int addAllNonDuplicated(List<T> items);
}
