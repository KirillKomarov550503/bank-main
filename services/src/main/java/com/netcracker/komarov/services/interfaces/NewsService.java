package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;

import java.util.Collection;

public interface NewsService {
    Collection<News> getAllPersonalNews(long clientId);

    News findClientNewsById(long newsId);

    Collection<News> getAllNews();

    Collection<News> getAllNewsByStatus(NewsStatus newsStatus);

    News addGeneralNews(News news, long adminId);

    void addClientNews(Collection<Long> clientIds, long newsId);
}
