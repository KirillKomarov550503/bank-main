package com.netcracker.komarov.services.json;

import java.io.Serializable;
import java.util.Objects;

public class NewsJson implements Serializable {
    private long id;
    private String date;
    private String title;
    private String text;
    private String status;

    public NewsJson() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsJson json = (NewsJson) o;
        return id == json.id &&
                Objects.equals(date, json.date) &&
                Objects.equals(title, json.title) &&
                Objects.equals(text, json.text) &&
                Objects.equals(status, json.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, title, text, status);
    }
}
