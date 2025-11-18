package org.example;

import java.util.List;

public interface MovieRepository {
    List<Movie> getAllMovies();

    // MEDIUM: поиск по режиссёру
    List<Movie> findByDirector(String director);

    // HARD: добавление фильма
    Movie addMovie(Movie movie);
}
