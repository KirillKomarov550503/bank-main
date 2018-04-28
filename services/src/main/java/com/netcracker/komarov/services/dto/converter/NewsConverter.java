package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.NewsDTO;

public class NewsConverter implements Converter<NewsDTO, News> {

    @Override
    public NewsDTO convertToDTO(News entity) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle(entity.getTitle());
        newsDTO.setText(entity.getText());
        newsDTO.setDate(entity.getDate());
        return newsDTO;
    }

    @Override
    public News convertToEntity(NewsDTO dto) {
        News news = new News();
        news.setText(dto.getText());
        news.setNewsStatus(dto.getNewsStatus());
        news.setTitle(dto.getTitle());
        news.getAdmin().setId(dto.getAdminId());
        news.setDate(dto.getDate());
        return news;
    }
}

