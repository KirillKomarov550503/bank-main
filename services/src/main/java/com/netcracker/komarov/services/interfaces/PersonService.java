package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.Person;

import java.util.Collection;

public interface PersonService {
    Collection<Person> getAllPeople();
}
