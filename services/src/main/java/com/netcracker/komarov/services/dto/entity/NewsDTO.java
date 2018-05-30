package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDTO implements Serializable {
    @ApiModelProperty(position = 1, readOnly = true, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2, readOnly = true, hidden = true)
    private String date;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private String text;

    @ApiModelProperty(position = 5)
    private String status;

    public NewsDTO() {
    }

    public NewsDTO(long id, String date, String title, String text, String status) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.text = text;
        this.status = status;
    }

    public Long getId() {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsDTO newsDTO = (NewsDTO) o;
        return Objects.equals(id, newsDTO.id) &&
                Objects.equals(date, newsDTO.date) &&
                Objects.equals(title, newsDTO.title) &&
                Objects.equals(text, newsDTO.text) &&
                Objects.equals(status, newsDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, title, text, status);
    }

    @Override
    public String toString() {
        return "NewsDTO{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

