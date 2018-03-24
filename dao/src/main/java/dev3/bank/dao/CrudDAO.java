package dev3.bank.dao;

import dev3.bank.entity.BaseEntity;
public interface CrudDAO<T extends BaseEntity> {
    T getById(long id);

    T add(T entity);

    T update(T entity);

    void delete(long id);

}
