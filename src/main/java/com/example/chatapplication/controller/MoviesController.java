package com.example.chatapplication.controller;

import com.example.chatapplication.model.query.MoviesFilterQuery;
import com.example.chatapplication.service.read.MoviesQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/movies")
public class MoviesController {

    private final MoviesQueryService moviesQueryService;
    @GetMapping()
    public ResponseEntity<?> getMovies(MoviesFilterQuery query){
        return ResponseEntity.ok(moviesQueryService.findMovies(query));
    }

    @GetMapping("/home")
    public ResponseEntity<?> getHomeMovies(){
        return ResponseEntity.ok(moviesQueryService.getMoviesHome());
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchMovie(MoviesFilterQuery query){
        return ResponseEntity.ok(moviesQueryService.searchMovie(query));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getDetailMovies(@PathVariable("id") Long id){
        System.out.println("DETAIL MOVIES");
        return ResponseEntity.ok(moviesQueryService.findDetailMovie(id));
    }


    @PostMapping("")
    public ResponseEntity<?> getDetailMoviess(){
        System.out.println("DETAIL MOVIES");
        return ResponseEntity.ok("");
    }

    @PutMapping("")
    public ResponseEntity<?> updateMovie(@PathVariable("id") Long id){
        System.out.println("DETAIL MOVIES");
        return ResponseEntity.ok(moviesQueryService.findDetailMovie(id));
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") Long id){
        System.out.println("DETAIL MOVIES");
        return ResponseEntity.ok(moviesQueryService.findDetailMovie(id));
    }
}
