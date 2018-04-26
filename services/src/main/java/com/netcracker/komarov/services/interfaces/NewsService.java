package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;

import java.util.Collection;

public interface NewsService {
    Collection<News> getAllPersonalNews(long clientId);

    News getPersonalNews(long newsId);

    Collection<News> getAllNews();

    Collection<News> getAllGeneralNews();

    Collection<News> getAllNewsByStatus(NewsStatus newsStatus);

    News addGeneralNews(News news, long adminId);
}
