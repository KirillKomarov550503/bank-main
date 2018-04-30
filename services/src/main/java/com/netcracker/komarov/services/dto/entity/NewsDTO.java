package com.netcracker.komarov.services.dto.entity;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.NewsStatus;

import java.util.Objects;

public class NewsDTO extends AbstractDTO {
    private String date;
    private String title;
    private String text;


    public NewsDTO() {
    }

    public NewsDTO(String date, String title, String text) {
        this.date = date;
        this.title = title;
        this.text = text;
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
}

