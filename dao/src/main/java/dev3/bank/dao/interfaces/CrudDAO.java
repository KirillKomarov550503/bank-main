package dev3.bank.dao.interfaces;

import dev3.bank.entity.BaseEntity;

import java.sql.SQLException;
import java.util.Collection;

public interface CrudDAO<T extends BaseEntity> {
    T getById(long id) throws SQLException;

    T update(T entity) throws SQLException;

    void delete(long id) throws SQLException;

    T add(T entity) throws SQLException;

    Collection<T> getAll() throws SQLException;
}
