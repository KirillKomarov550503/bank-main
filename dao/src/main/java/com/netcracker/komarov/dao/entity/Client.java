package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToMany(mappedBy = "clients", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<News> newsSet = new HashSet<>();

    public Client() {
    }

    public Client(Person person) {
        this.person = person;
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

    public Set<News> getNewsSet() {
        return newsSet;
    }

    public void setNewsSet(Set<News> newsSet) {
        this.newsSet = newsSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(person, client.person);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, person);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", person=" + person +
                '}';
    }
}
