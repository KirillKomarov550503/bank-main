package dev3.bank.entity;

public class ClientNews extends BaseEntity {
    private Client client;
    private News news;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
