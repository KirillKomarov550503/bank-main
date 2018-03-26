package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.CrudDAO;
import dev3.bank.entity.BaseEntity;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CrudDAOImpl<T extends BaseEntity> implements CrudDAO<T> {

    private Class<T> classType;

    public CrudDAOImpl(Class<T> classType) {
        this.classType = classType;
        memoryMap.computeIfAbsent(classType, k -> new HashMap<Long, T>());
    }

    public Collection<T> getEntityMapValues(){
        return getEntityMap().values();
    }

    private Map getEntityMap() {
        return CrudDAO.memoryMap.get(classType);
    }

    @Override
    public T getById(long id) {
        Map<Long, T> entityMap = getEntityMap();
        return entityMap.get(id);
    }

    @Override
    public T add(T entity) {
        Map<Long, T> entityMap = getEntityMap();
        if (entityMap.isEmpty()) {
            entity.setId(1);
            entityMap.put(1L, entity);
        } else {
            long lastId = getLastId(entityMap);
            entity.setId(lastId + 1L);
            entityMap.put(lastId + 1L, entity);
        }
        return null;
    }

    public static void writeEntitiesMap(String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path))) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(memoryMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readEntitiesMap(String path) {
        try (FileInputStream inputStream = new FileInputStream(new File(path))) {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            memoryMap.putAll((Map) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private long getLastId(Map<Long, T> entityMap) {
        return entityMap.keySet()
                .stream()
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public T update(T entity) {
        Map<Long, T> entityMap = getEntityMap();
        entityMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void delete(long id) {
        Map<Long, T> entityMap = getEntityMap();
        entityMap.remove(id);
    }
}
