package org.prime.internship.repository;

import java.util.Set;

public interface BaseRepository<T> {

    T getOne(Integer id);

    Set<T> getAll();

    T insert(T t);

    T update(T t);

    void delete(Integer id);
}
