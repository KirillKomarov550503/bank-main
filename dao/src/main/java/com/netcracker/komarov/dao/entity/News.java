package com.netcracker.komarov.dao.entity;

import java.util.Objects;

public class News extends BaseEntity {
    private String date;
    private String title;
    private String text;
    private long adminId;
    private NewsStatus newsStatus;

    public News() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        News news = (News) o;
        return adminId == news.adminId &&
                Objects.equals(date, news.date) &&
                Objects.equals(title, news.title) &&
                Objects.equals(text, news.text) &&
                newsStatus == news.newsStatus;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), date, title, text, adminId, newsStatus);
    }

    public News(long id, String date, String title, String text, long adminId, NewsStatus newsStatus) {

        super(id);
        this.date = date;
        this.title = title;
        this.text = text;
        this.adminId = adminId;
        this.newsStatus = newsStatus;
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

    @Override
    public String toString() {
        return "News{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", adminId=" + adminId +
                ", newsStatus=" + newsStatus +
                ", id=" + id +
                '}';
    }

}
