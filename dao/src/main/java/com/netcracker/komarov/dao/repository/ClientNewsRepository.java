package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.ClientNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientNewsRepository extends JpaRepository<ClientNews, Long> {
}
