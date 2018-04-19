package dev3.bank.dao.interfaces;

import dev3.bank.entity.Person;
import dev3.bank.entity.Role;

import java.sql.SQLException;
import java.util.Collection;

public interface PersonDAO extends CrudDAO<Person> {
    Collection<Person> getByRole(Role role) throws SQLException;
}
