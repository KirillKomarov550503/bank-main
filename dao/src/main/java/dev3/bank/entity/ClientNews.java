package dev3.bank.entity;

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
}
