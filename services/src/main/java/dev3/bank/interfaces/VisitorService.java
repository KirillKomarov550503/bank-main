package dev3.bank.interfaces;

import dev3.bank.entity.News;
import dev3.bank.entity.Person;

import java.util.Collection;

public interface VisitorService {
    Collection<News> getAllNews();

    Person registration(Person person);

    Person signIn(Person person);
}
