package dev3.bank.interfaces;

import dev3.bank.entity.News;
import dev3.bank.entity.NewsStatus;

import java.util.Collection;

public interface NewsService {
    Collection<News> getAllPersonalNews(long clientId);

    News getPersonalNews(long newsId);

    Collection<News> getAllNews();

    Collection<News> getAllGeneralNews();

    Collection<News> getAllNewsByStatus(NewsStatus newsStatus);

    News addGeneralNews(News news, long adminId);
}
