package com.netcracker.komarov.dao.interfaces;

import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;

import java.sql.SQLException;
import java.util.Collection;

public interface NewsDAO extends CrudDAO<News> {
    Collection<News> getNewsByAdmin(long adminId) throws SQLException;

    Collection<News> getNewsByStatus(NewsStatus newsStatus) throws SQLException;
}