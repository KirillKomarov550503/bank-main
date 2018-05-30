package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Collection<Person> findPeopleByRole(Role role);

    Person findPersonByUsername(String username);

    @Modifying
    void deletePersonById(long personId);
}

