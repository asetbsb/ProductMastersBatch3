package org.example;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // EASY: получить все фильмы
    // GET /api/movies/all
    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieService.listMovies();
    }

    // MEDIUM: фильтрация по режиссёру
    // GET /api/movies/by-director?name=Christopher+Nolan
    @GetMapping("/by-director")
    public List<Movie> getMoviesByDirector(@RequestParam("name") String directorName) {
        return movieService.listMoviesByDirector(directorName);
    }

    // HARD: добавление фильма
    // POST /api/movies/add
    // Body:
    // {
    //   "title": "Interstellar",
    //   "director": "Christopher Nolan",
    //   "year": 2014
    // }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie addMovie(@Valid @RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }
}
