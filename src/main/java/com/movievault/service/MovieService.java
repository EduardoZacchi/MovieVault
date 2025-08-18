package com.movievault.service;

import com.movievault.entity.Category;
import com.movievault.entity.Movie;
import com.movievault.entity.Streaming;
import com.movievault.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id){
        return movieRepository.findById(id);
    }

    public Movie saveMovie(Movie movie){
        movie.setCategories(this.findCategories(movie.getCategories()));
        movie.setStreamings(this.findStreaming(movie.getStreamings()));
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }

    public List<Category> findCategories(List<Category> categories){
        List<Category> categoriesFound = new ArrayList<>();
        categories.forEach(c -> categoryService.getCategoryById(c.getId()).ifPresent(categoriesFound::add));
        return categoriesFound;
    }

    public List<Streaming> findStreaming(List<Streaming> streamings){
        List<Streaming> streamingsFound = new ArrayList<>();
        streamings.forEach(c -> streamingService.getStreamingById(c.getId()).ifPresent(streamingsFound::add));
        return streamingsFound;
    }
}
