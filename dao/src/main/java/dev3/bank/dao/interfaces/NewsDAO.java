package dev3.bank.dao.interfaces;

import dev3.bank.entity.News;
import dev3.bank.entity.NewsStatus;

import java.sql.SQLException;
import java.util.Collection;

public interface NewsDAO extends CrudDAO<News> {
    Collection<News> getNewsByAdmin(long adminId) throws SQLException;
    Collection<News> getNewsByStatus(NewsStatus newsStatus) throws SQLException;
}