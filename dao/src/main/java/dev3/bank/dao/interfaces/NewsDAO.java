package dev3.bank.dao.interfaces;

import dev3.bank.entity.News;

import java.util.Collection;
import java.util.Date;

public interface NewsDAO extends CrudDAO<News> {
    Collection<News> getNewsByDate(String date);
    Collection<News> getNewsByAdmin(long adminId);
}