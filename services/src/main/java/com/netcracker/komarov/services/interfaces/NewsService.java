package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.services.dto.entity.NewsDTO;

import java.util.Collection;

public interface NewsService {
    Collection<NewsDTO> getAllClientNewsById(long clientId);

    NewsDTO getPersonalNews(long newsId);

    Collection<NewsDTO> getAllNews();

    Collection<NewsDTO> getAllGeneralNews();

    Collection<NewsDTO> getAllNewsByStatus(NewsStatus newsStatus);

    NewsDTO addNews(NewsDTO newsDTO, long adminId, String status);

    NewsDTO addClientNews(Collection<Long> clientIds, long newsId);
}
