package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.NewsDAO;
import dev3.bank.entity.News;
import dev3.bank.entity.NewsStatus;

import java.util.Collection;
import java.util.stream.Collectors;

public class NewsDAOImpl extends CrudDAOImpl<News> implements NewsDAO {
    public NewsDAOImpl() {
        super(News.class);
    }

    @Override
    public Collection<News> getNewsByAdmin(long adminId) {
        return getEntityMapValues()
                .stream()
                .filter(news -> news.getAdmin().getId() == adminId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<News> getNewsByStatus(NewsStatus newsStatus) {
        return getEntityMapValues()
                .stream()
                .filter(news -> news.getNewsStatus().equals(newsStatus))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<News> getNewsByDate(String date) {
        return getEntityMapValues()
                .stream()
                .filter(news -> news.getDate().equals(date))
                .collect(Collectors.toList());
    }
}
