package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface NewsService {
    Collection<NewsDTO> getAllClientNewsById(long clientId) throws NotFoundException;

    NewsDTO findById(long newsId) throws NotFoundException;

    Collection<NewsDTO> getAllNews();

    Collection<NewsDTO> getAllNewsByStatus(NewsStatus newsStatus);

    NewsDTO addNews(NewsDTO newsDTO, long adminId) throws NotFoundException;

    NewsDTO addClientNews(Collection<Long> clientIds, long newsId) throws NotFoundException;

    NewsDTO update(NewsDTO newsDTO) throws NotFoundException;
}
