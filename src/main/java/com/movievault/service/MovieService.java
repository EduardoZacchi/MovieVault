package com.movievault.service;

import com.movievault.controller.request.MovieRequest;
import com.movievault.entity.Category;
import com.movievault.entity.Movie;
import com.movievault.entity.Streaming;
import com.movievault.mapper.MovieMapper;
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

    public Optional<Movie> patch(Long id, Movie updateMovie){
        Optional<Movie> optMovie = movieRepository.findById(id);
        if(optMovie.isPresent()){
            Movie movie = optMovie.get();

            List<Category> categories = this.findCategories(updateMovie.getCategories());

            List<Streaming> streaming = this.findStreaming(updateMovie.getStreamings());

            if(updateMovie.getTitle() != null) movie.setTitle(updateMovie.getTitle());

            if(updateMovie.getDescription() != null) movie.setDescription(updateMovie.getDescription());

            if(updateMovie.getReleaseDate() != null) movie.setReleaseDate(updateMovie.getReleaseDate());

            if(updateMovie.getRating() != 0.0) movie.setRating(updateMovie.getRating());

            if(updateMovie.getCategories() != null){
                movie.getCategories().clear();
                movie.getCategories().addAll(categories);
            }

            if(updateMovie.getStreamings() != null){
                movie.getStreamings().clear();
                movie.getStreamings().addAll(streaming);
            }

            movieRepository.save(movie);
            return Optional.of(movie);
        }
        return Optional.empty();
    }
    //UPDATE METHOD WILL OVERRIDE ALL OBJECT FIELDS
    public Optional<Movie> update(Long id, Movie updateMovie){
        Optional<Movie> optMovie = movieRepository.findById(id);
        if(optMovie.isPresent()){
            Movie movie = optMovie.get();

            List<Category> categories = this.findCategories(updateMovie.getCategories());
            List<Streaming> streaming = this.findStreaming(updateMovie.getStreamings());

            movie.setTitle(updateMovie.getTitle());

            movie.setDescription(updateMovie.getDescription());

            movie.setReleaseDate(updateMovie.getReleaseDate());

            movie.setRating(updateMovie.getRating());

            movie.getCategories().clear();
            movie.getCategories().addAll(categories);

            movie.getStreamings().clear();
            movie.getStreamings().addAll(streaming);

            movieRepository.save(movie);
            return Optional.of(movie);
        }
        return Optional.empty();
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
