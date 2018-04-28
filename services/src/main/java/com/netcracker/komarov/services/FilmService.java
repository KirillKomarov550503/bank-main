package com.netcracker.komarov.services;

import com.netcracker.komarov.dao.Film;
import com.netcracker.komarov.dao.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmService {
    private FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film addFilm(Film film) {
        System.out.println("FilmService.addFilm()");
        return filmRepository.save(film);
//        return null;
    }
}
