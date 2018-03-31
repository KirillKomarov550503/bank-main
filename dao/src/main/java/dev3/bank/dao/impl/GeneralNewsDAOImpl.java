package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.GeneralNewsDAO;
import dev3.bank.entity.GeneralNews;

import java.util.Collection;
import java.util.stream.Collectors;

public class GeneralNewsDAOImpl extends CrudDAOImpl<GeneralNews> implements GeneralNewsDAO {
    public GeneralNewsDAOImpl() {
        super(GeneralNews.class);
    }

    @Override
    public Collection<GeneralNews> getNewsByAdmin(long adminId) {
        return getEntityMapValues()
                .stream()
                .filter(news -> news.getAdmin().getId() == adminId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<GeneralNews> getNewsByDate(String date) {
        return getEntityMapValues()
                .stream()
                .filter(news -> news.getDate().equals(date))
                .collect(Collectors.toList());
    }
}
