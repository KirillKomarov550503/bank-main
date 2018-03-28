package dev3.bank.interfaces;

import dev3.bank.entity.Client;
import dev3.bank.entity.News;
import dev3.bank.entity.Person;

import java.util.Collection;

public interface VisitorService {
    Collection<News> getAllNews();

    Person registration(Client client);

    Person signIn(Person person);
}
