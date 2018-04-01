package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.GeneralNewsDAO;
import dev3.bank.entity.News;

import java.util.Collection;
import java.util.stream.Collectors;

public class GeneralNewsDAOImpl extends CrudDAOImpl<News> implements GeneralNewsDAO {
    public GeneralNewsDAOImpl() {
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
    public Collection<News> getNewsByDate(String date) {
        return getEntityMapValues()
                .stream()
                .filter(news -> news.getDate().equals(date))
                .collect(Collectors.toList());
    }
}
