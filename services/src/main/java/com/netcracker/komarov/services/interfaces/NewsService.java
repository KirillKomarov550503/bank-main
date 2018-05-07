package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.services.dto.entity.NewsDTO;

import java.util.Collection;

public interface NewsService {
    Collection<NewsDTO> getAllClientNewsById(long clientId);

    NewsDTO getNewsById(long newsId);

    Collection<NewsDTO> getAllNews();

    Collection<NewsDTO> getAllNewsByStatus(NewsStatus newsStatus);

    NewsDTO addNews(NewsDTO newsDTO, long adminId);

    NewsDTO addClientNews(Collection<Long> clientIds, long newsId);

    NewsDTO update(NewsDTO newsDTO);

    void deleteById(long newsId);
}
