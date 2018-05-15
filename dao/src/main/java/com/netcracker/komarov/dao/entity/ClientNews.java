package com.netcracker.komarov.dao.entity;

import java.util.Objects;

public class ClientNews extends BaseEntity{
    private long clientId;
    private long newsId;

    public ClientNews(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClientNews that = (ClientNews) o;
        return clientId == that.clientId &&
                newsId == that.newsId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), clientId, newsId);
    }

    public ClientNews(long id, long clientId, long newsId) {

        super(id);
        this.clientId = clientId;
        this.newsId = newsId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    @Override
    public String toString() {
        return "ClientNews{" +
                "clientId=" + clientId +
                ", newsId=" + newsId +
                ", id=" + id +
                '}';
    }

}
