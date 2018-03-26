package dev3.bank.dao.interfaces;

import dev3.bank.entity.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public interface CrudDAO<T extends BaseEntity> {
    Map<Class<? extends BaseEntity>, Map<Long, ? extends BaseEntity>> memoryMap = new HashMap<>();
    T getById(long id);

    T add(T entity);

    T update(T entity);

    void delete(long id);

}
