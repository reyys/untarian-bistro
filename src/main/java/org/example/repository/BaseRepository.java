package org.example.repository;

import java.util.List;

public interface BaseRepository<T> {
    T save(T entity);

    List<T> findAll();

    T findById(int id);

    void update(T entity);

    void deleteById(int id);

    int getLastId();
}
