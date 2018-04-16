package dev3.bank.entity;

import java.util.Objects;

public class ClientNews extends BaseEntity{
    private long clientId;
    private long newsId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientNews that = (ClientNews) o;
        return clientId == that.clientId &&
                newsId == that.newsId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientId, newsId);
    }
}
