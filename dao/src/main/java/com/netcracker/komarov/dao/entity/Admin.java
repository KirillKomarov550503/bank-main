package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "admin")
public class Admin extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "admin")
    private Set<News> news = new HashSet<>();

    public Admin() {
    }

    public Admin(Person person) {
        this.person = person;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(person, admin.person);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), person);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", person=" + person +
                '}';
    }

}
