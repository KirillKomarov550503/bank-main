package com.netcracker.komarov.dao.interfaces;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;

import java.sql.SQLException;
import java.util.Collection;

public interface PersonDAO extends CrudDAO<Person> {
    Collection<Person> getByRole(Role role) throws SQLException;
}
