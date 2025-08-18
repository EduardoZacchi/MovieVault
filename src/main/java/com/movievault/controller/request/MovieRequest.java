package com.movievault.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(String title,
                           String description,

                           @JsonFormat(pattern = "dd/MM/yyyy")
                           LocalDate releaseDate,

                           double rating,
                           List<Long> categories,
                           List<Long> streamings
) {
}
