package dev3.bank.dao.interfaces;

import dev3.bank.entity.GeneralNews;

import java.util.Collection;

public interface GeneralNewsDAO extends CrudDAO<GeneralNews> {
    Collection<GeneralNews> getNewsByDate(String date);
    Collection<GeneralNews> getNewsByAdmin(long adminId);
}