package dev3.bank.dao.interfaces;

import dev3.bank.entity.BaseEntity;

import java.util.Collection;

public interface CrudDAO<T extends BaseEntity> {
    T getById(long id);

    T update(T entity);

    void delete(long id);

    T add(T entity);

    Collection<T> getAll();
}
