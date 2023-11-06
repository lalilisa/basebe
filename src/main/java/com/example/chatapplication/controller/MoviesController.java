package com.example.chatapplication.controller;

import com.example.chatapplication.model.query.MoviesFilterQuery;
import com.example.chatapplication.service.read.MoviesQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("{id}")
    public ResponseEntity<?> getDetailMovies(@PathVariable("id") Long id){
        System.out.println("DETAIL MOVIES");
        return ResponseEntity.ok(moviesQueryService.findDetailMovie(id));
    }
}
