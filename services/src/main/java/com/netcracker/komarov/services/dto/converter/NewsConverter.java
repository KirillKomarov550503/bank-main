package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import org.springframework.stereotype.Component;

@Component
public class NewsConverter implements Converter<NewsDTO, News> {
    @Override
    public NewsDTO convertToDTO(News news) {
        NewsDTO newsDTO = null;
        if (news != null) {
            newsDTO = new NewsDTO();
            newsDTO.setId(news.getId());
            newsDTO.setDate(news.getDate());
            newsDTO.setText(news.getText());
            newsDTO.setDate(news.getDate());
        }
        return newsDTO;
    }

    @Override
    public News convertToEntity(NewsDTO dto) {
        News news = new News();
        news.setTitle(dto.getTitle());
        news.setText(dto.getText());
        news.setDate(dto.getDate());
        String status = dto.getStatus().toLowerCase();
        if (status.equals("client")) {
            news.setNewsStatus(NewsStatus.CLIENT);
        } else if (status.equals("general")) {
            news.setNewsStatus(NewsStatus.GENERAL);
        }
        return news;
    }
}
