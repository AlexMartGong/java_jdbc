package org.ax.java.jdbc.repository;

import java.util.List;

public interface Repository<T> {
    List<T> listAll();
    T byId(Long id);
    void save(T t);
    void delete(Long id);
}
