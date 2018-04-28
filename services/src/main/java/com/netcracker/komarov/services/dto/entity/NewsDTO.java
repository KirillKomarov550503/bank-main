package com.netcracker.komarov.services.dto.entity;

import com.netcracker.komarov.dao.entity.NewsStatus;

import java.util.Objects;

public class NewsDTO extends AbstractDTO {
    private String date;
    private String title;
    private String text;
    private long adminId;
    private NewsStatus newsStatus;

    public NewsDTO() {
    }

    public NewsDTO(String date, String title, String text, long adminId, NewsStatus newsStatus) {
        this.date = date;
        this.title = title;
        this.text = text;
        this.adminId = adminId;
        this.newsStatus = newsStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public NewsStatus getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(NewsStatus newsStatus) {
        this.newsStatus = newsStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsDTO newsDTO = (NewsDTO) o;
        return adminId == newsDTO.adminId &&
                Objects.equals(date, newsDTO.date) &&
                Objects.equals(title, newsDTO.title) &&
                Objects.equals(text, newsDTO.text) &&
                newsStatus == newsDTO.newsStatus;
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, title, text, adminId, newsStatus);
    }

    @Override
    public String toString() {
        return "NewsDTO{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", adminId=" + adminId +
                ", newsStatus=" + newsStatus +
                '}';
    }
}

