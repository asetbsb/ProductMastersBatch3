package org.example;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    // Динамический список, в который можно добавлять новые фильмы
    private final List<Movie> movies = new ArrayList<>(
            List.of(
                    new Movie("Inception", "Christopher Nolan", 2010),
                    new Movie("Interstellar", "Christopher Nolan", 2014),
                    new Movie("The Dark Knight", "Christopher Nolan", 2008),
                    new Movie("Pulp Fiction", "Quentin Tarantino", 1994)
            )
    );

    @Override
    public List<Movie> getAllMovies() {
        return movies;
    }

    @Override
    public List<Movie> findByDirector(String director) {
        return movies.stream()
                .filter(m -> m.getDirector().equalsIgnoreCase(director))
                .collect(Collectors.toList());
    }

    @Override
    public Movie addMovie(Movie movie) {
        movies.add(movie);
        return movie;
    }
}
