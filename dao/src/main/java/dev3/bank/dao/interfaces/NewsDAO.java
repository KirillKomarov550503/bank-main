package dev3.bank.dao.interfaces;

import dev3.bank.entity.News;
import dev3.bank.entity.NewsStatus;

import java.util.Collection;

public interface NewsDAO extends CrudDAO<News> {
    Collection<News> getNewsByDate(String date);
    Collection<News> getNewsByAdmin(long adminId);
    Collection<News> getNewsByStatus(NewsStatus newsStatus);
}