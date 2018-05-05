package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "news")
public class News extends BaseEntity{

    @Column(name = "date", length = 20)
    private String date;

    @Column(name = "title")
    private String title;

    @Column(name = "text", columnDefinition = "Text")
    private String text;

    @ManyToMany
    @JoinTable(name = "news_client", joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private Set<Client> clients = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column(name = "news_status")
    @Enumerated(EnumType.STRING)
    private NewsStatus newsStatus;

    public News() {
    }

    public News(String date, String title, String text, Set<Client> clients, Admin admin, NewsStatus newsStatus) {
        this.date = date;
        this.title = title;
        this.text = text;
        this.clients = clients;
        this.admin = admin;
        this.newsStatus = newsStatus;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        News news = (News) o;
        return Objects.equals(date, news.date) &&
                Objects.equals(title, news.title) &&
                Objects.equals(text, news.text) &&
                Objects.equals(admin, news.admin) &&
                newsStatus == news.newsStatus;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), date, title, text, admin, newsStatus);
    }

    @Override
    public String toString() {
        return "News{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", admin=" + admin +
                ", newsStatus=" + newsStatus +
                ", id=" + id +
                '}';
    }
}
