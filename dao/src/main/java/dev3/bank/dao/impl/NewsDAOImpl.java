package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.NewsDAO;
import dev3.bank.entity.News;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class NewsDAOImpl extends CrudDAOImpl<News> implements NewsDAO {
    public NewsDAOImpl() {
        super(News.class);
    }

    @Override
    public Collection<News> getNewsByDate(Date date) {
        return getEntityMapValues()
                .stream()
                .filter(news -> news.getDate().equals(date))
                .collect(Collectors.toList());
    }
}
