package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}
