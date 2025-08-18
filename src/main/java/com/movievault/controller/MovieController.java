package com.movievault.controller;

import com.movievault.controller.request.MovieRequest;
import com.movievault.controller.response.MovieResponse;
import com.movievault.entity.Category;
import com.movievault.entity.Movie;
import com.movievault.mapper.MovieMapper;
import com.movievault.service.MovieService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/MovieVault/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies()
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/searchByCategory")
    public ResponseEntity<List<MovieResponse>> getMovieByCategory(@RequestParam Long category){
        return ResponseEntity.ok(movieService.findMovieByCategories(category)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @PostMapping
    public ResponseEntity<MovieResponse> saveMovie(@RequestBody MovieRequest request){
        Movie movieEntity = MovieMapper.toMovie(request);
        Movie savedMovie = movieService.saveMovie(movieEntity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MovieMapper.toMovieResponse(savedMovie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        Optional<Movie> optMovie = movieService.getMovieById(id);
        if(optMovie.isPresent()){
            movieService.deleteMovie(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> patchUpdate(@PathVariable Long id, @RequestBody MovieRequest request){
        Movie updateMovie = MovieMapper.toMovie(request);
        return movieService.patch(id, updateMovie)
                .map(m -> ResponseEntity.ok(MovieMapper.toMovieResponse(m)))
                .orElse(ResponseEntity.notFound().build());
    }
    //fullUpdate will override all object fields
    @PutMapping("/update/{id}")
    public ResponseEntity<MovieResponse> fullUpdate(@PathVariable Long id, @RequestBody MovieRequest request){
        Movie updateMovie = MovieMapper.toMovie(request);
        return movieService.update(id, updateMovie)
                .map(m -> ResponseEntity.ok(MovieMapper.toMovieResponse(m)))
                .orElse(ResponseEntity.notFound().build());
    }
}
