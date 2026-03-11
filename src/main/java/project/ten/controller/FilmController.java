package project.ten.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import project.ten.exception.ValidationException;
import project.ten.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long,Film> filmMap = new HashMap<>();

    //добавление фильма;
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film){
        log.info("Request to add film: {}", film.getName());
        //название не может быть пустым;
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Invalid name: {}", film.getName());
            throw new ValidationException("название не может быть пустым");
        }

        //максимальная длина описания — 200 символов;
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            log.warn("Invalid descriptions size: {}",film.getDescription().length());
            throw new ValidationException(" максимальная длина описания — 200 символов");
        }

        //дата релиза — не раньше 28 декабря 1895 года;
        if(film.getReleaseDate() != null && film.getReleaseDate().isBefore( LocalDate.of(1895,12,28))) {
            log.warn("Invalid ReleaseDate: {}", film.getReleaseDate());
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }

        //продолжительность фильма должна быть положительным числом.
        if (film.getDuration() <= 0) {
            log.warn("Invalid duration cant be negative : {}", film.getDuration());
            throw new ValidationException("продолжительность фильма должна быть положительным числом");
        }

        film.setId(nextIndex());
        filmMap.put(film.getId(),film);

        log.info("Film added with id {} and name {}", film.getId(), film.getName());
        return film;
    }

    public long nextIndex(){
        long index = filmMap.keySet().stream()
                .mapToLong(el -> el)
                .max()
                .orElse(0);
        return ++index;
    }

    //обновление фильма;
    @PutMapping
    public Film changeInfo(@Valid @RequestBody Film film) {
        if (film.getId() == null) {
            log.warn("Invalid id is missing: {}", film.getId());
            throw new ValidationException("id фильма не указан");
        }

        if (!filmMap.containsKey(film.getId())) {
            log.warn("Film with  id {} not found", film.getId());
            throw  new ValidationException("Фильм с заданным id отсутствует");
        }

        //название не может быть пустым;
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Invalid name: {}", film.getName());
            throw new ValidationException("название не может быть пустым");
        }

        //максимальная длина описания — 200 символов;
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            log.warn("Invalid descriptions size: {}",film.getDescription().length());
            throw new ValidationException(" максимальная длина описания — 200 символов");
        }

        //дата релиза — не раньше 28 декабря 1895 года;
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore( LocalDate.of(1895,12,28))) {
            log.warn("Invalid ReleaseDate: {}", film.getReleaseDate());
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");

        }

        //продолжительность фильма должна быть положительным числом.
        if (film.getDuration() <= 0) {
            log.warn("Invalid duration cant be negative : {}", film.getDuration());
            throw new ValidationException("продолжительность фильма должна быть положительным числом");
        }

        filmMap.put(film.getId(), film);
        log.info("Film updates: id={} and name={}", film.getId(), film.getName());
        return  film;
    }

    @GetMapping
    public Collection<Film> getAllFilms(){
        log.info("Request to get all films. Total films: {}", filmMap.size());
        return filmMap.values();
    }
}
