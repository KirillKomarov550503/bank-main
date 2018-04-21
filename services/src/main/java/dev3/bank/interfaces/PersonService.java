package dev3.bank.interfaces;

import dev3.bank.entity.Person;

import java.util.Collection;

public interface PersonService {
    Collection<Person> getAllPeople();
}
