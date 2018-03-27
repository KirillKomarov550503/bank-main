package dev3.bank.entity;

import java.util.Collection;

public class Admin extends Person {
    public Collection<News> getNewsCollection() {
        return newsCollection;
    }

    public void setNewsCollection(Collection<News> newsCollection) {
        this.newsCollection = newsCollection;
    }

    private Collection<News> newsCollection;
}
