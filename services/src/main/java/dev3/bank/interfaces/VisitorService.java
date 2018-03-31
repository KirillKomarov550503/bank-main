package dev3.bank.interfaces;

import dev3.bank.entity.Client;
import dev3.bank.entity.GeneralNews;
import dev3.bank.entity.Person;

import java.util.Collection;

public interface VisitorService {
    Collection<GeneralNews> getAllNews();

    Person registration(Client client);

    Person signIn(Person person);
}
