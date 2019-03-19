package org.prime.internship.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BaseRepository <T>{

    T getOne (Integer id);
    List<T> getAll ();
    T insert (T t);
    T update (T t);
    void delete (Integer id);

}
