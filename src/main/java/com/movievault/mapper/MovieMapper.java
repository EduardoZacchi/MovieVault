package com.movievault.mapper;

import com.movievault.controller.request.MovieRequest;
import com.movievault.controller.response.MovieResponse;
import com.movievault.entity.Category;
import com.movievault.entity.Movie;
import com.movievault.entity.Streaming;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MovieMapper {

    public static Movie toMovie(MovieRequest request){
        List<Category> categories = request.categories()
                .stream()
                .map(categoryId -> Category.builder().id(categoryId).build())
                .toList();

        List<Streaming> streamings = request.streamings()
                .stream()
                .map(streamingId -> Streaming.builder().id(streamingId).build())
                .toList();

        return Movie
                .builder()
                .title(request.title())
                .description(request.description())
                .releaseDate(request.releaseDate())
                .rating(request.rating())
                .categories(categories)
                .streamings(streamings)
                .build();

    }

    public static MovieResponse toMovieResponse(Movie movie){
        return MovieResponse
                .builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .categories(movie.getCategories()
                        .stream()
                        .map(CategoryMapper::toCategoryResponse)
                        .toList()
                )
                .streamings(movie.getStreamings()
                        .stream()
                        .map(StreamingMapper::toStreamingResponse)
                        .toList()
                )
                .build();
    }
}
