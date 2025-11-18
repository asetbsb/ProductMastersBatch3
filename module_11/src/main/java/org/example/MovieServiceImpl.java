package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<Movie> listMovies() {
        return movieRepository.getAllMovies();
    }

    @Override
    public List<Movie> listMoviesByDirector(String director) {
        return movieRepository.findByDirector(director);
    }

    @Override
    public Movie addMovie(Movie movie) {
        // Дополнительная защита (на случай, если Bean Validation отключен)
        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title must not be empty");
        }
        if (movie.getDirector() == null || movie.getDirector().isBlank()) {
            throw new IllegalArgumentException("Director must not be empty");
        }
        if (movie.getYear() < 1900) {
            throw new IllegalArgumentException("Year must be >= 1900");
        }

        return movieRepository.addMovie(movie);
    }
}
