package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.netcracker.komarov.services.json.NewsJson;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "title", "text", "status", "date"})
public class NewsDTO implements Serializable {
    @ApiModelProperty(position = 1)
    private long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private String text;

    @ApiModelProperty(position = 5)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String status;

    public NewsDTO() {
    }

    public NewsDTO(NewsJson json) {
        id = json.getId();
        date = json.getDate();
        title = json.getTitle();
        text = json.getText();
        status = json.getStatus();
    }

    public NewsDTO(long id, String date, String title, String text, String status) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.text = text;
        this.status = status;
    }

    @ApiModelProperty(readOnly = true, hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(long id) {
        this.id = id;
    }

    @ApiModelProperty(readOnly = true, hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getDate() {
        return date;
    }

    @JsonIgnore
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
        NewsDTO newsDTO = (NewsDTO) o;
        return id == newsDTO.id &&
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

