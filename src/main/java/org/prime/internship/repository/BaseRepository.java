package org.prime.internship.repository;

import java.util.List;

public interface BaseRepository <T>{

    T getOne (Integer id);
    T getOneByName (String name);
    List<T> getAll ();
    T insert (T t);
    T update (T t);
    void delete (Integer id);

}
