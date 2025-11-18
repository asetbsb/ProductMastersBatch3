package org.example;

import java.util.List;

public interface MovieService {

    List<Movie> listMovies();

    // MEDIUM
    List<Movie> listMoviesByDirector(String director);

    // HARD
    Movie addMovie(Movie movie);
}
