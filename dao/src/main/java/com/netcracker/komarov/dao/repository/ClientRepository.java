package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Client c where c.person.id = :person_id")
    Client findClientByPersonId(@Param("person_id") long personId);
}

