package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.PersonDAO;
import dev3.bank.entity.Person;
import dev3.bank.entity.Role;

import java.util.Collection;
import java.util.stream.Collectors;

public class PersonDAOImpl extends CrudDAOImpl<Person> implements PersonDAO {
    public PersonDAOImpl() {
        super(Person.class);
    }

    @Override
    public Collection<Person> getByRole(Role role) {
        return getEntityMapValues()
                .stream()
                .filter(person -> person.getRole().equals(role))
                .collect(Collectors.toList());
    }
}
