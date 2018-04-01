package dev3.bank.entity;

public class News extends BaseEntity {
    private String date;
    private String title;
    private String text;
    private Admin admin;
    private NewsStatus newsStatus;

    @Override
    public String toString() {
        return "News{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", admin=" + admin +
                ", newsStatus=" + newsStatus +
                ", id=" + id +
                '}';
    }

    public NewsStatus getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(NewsStatus newsStatus) {
        this.newsStatus = newsStatus;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
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
