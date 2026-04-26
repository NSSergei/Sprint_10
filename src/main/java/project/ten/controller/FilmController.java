package project.ten.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import project.ten.exception.ValidationException;
import project.ten.model.Film;
import org.springframework.web.bind.annotation.*;
import project.ten.service.FilmService;
import project.ten.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film){
        return filmService.addFilm(film);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable long id) {
        filmService.deleteFilm(id);
    }

    @PutMapping
    public Film changeInfo(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping
    public Collection<Film> getAllFilms(){
        return filmService.getFilms();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id,
                           @PathVariable long userId) {
        filmService.deleteLike(id, userId);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable long id,
                              @PathVariable long userId ) {
        filmService.addLikeToFilm(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getTopFilmsList(@RequestParam long count) {
        return filmService.getTopFilmsList(count);
    }
}
