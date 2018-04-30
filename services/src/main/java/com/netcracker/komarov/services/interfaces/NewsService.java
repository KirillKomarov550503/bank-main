package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;

import java.util.Collection;

public interface NewsService {
    Collection<News> getAllClientNewsById(long clientId);

    News getPersonalNews(long newsId);

    Collection<News> getAllNews();

    Collection<News> getAllGeneralNews();

    Collection<News> getAllNewsByStatus(NewsStatus newsStatus);

    News addNews(News news, long adminId, String status);

    News addClientNews(Collection<Long> clientIds, long newsId);
}
