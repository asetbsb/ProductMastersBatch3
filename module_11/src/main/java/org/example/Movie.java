package org.example;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {

    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Director must not be empty")
    private String director;

    @Min(value = 1900, message = "Year must be >= 1900")
    private int year;
}
