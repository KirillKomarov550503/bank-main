package dev3.bank.entity;

public class ClientNews {
    private Client client;
    private boolean viewed;
    private News news;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
