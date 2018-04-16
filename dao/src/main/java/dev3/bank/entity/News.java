package dev3.bank.entity;

import java.util.Objects;

public class News extends BaseEntity {
    private String date;
    private String title;
    private String text;
    private long adminId;
    private NewsStatus newsStatus;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return adminId == news.adminId &&
                Objects.equals(date, news.date) &&
                Objects.equals(title, news.title) &&
                Objects.equals(text, news.text) &&
                newsStatus == news.newsStatus;
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, title, text, adminId, newsStatus);
    }
}
