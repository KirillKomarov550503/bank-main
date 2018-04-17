package dev3.bank.interfaces;

import dev3.bank.entity.Person;

import java.util.Collection;

public interface PersonService extends BaseService {
    Collection<Person> getAllPeople();
}
