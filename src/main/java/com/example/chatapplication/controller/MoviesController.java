package com.example.chatapplication.controller;

import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.model.query.MoviesFilterQuery;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.service.read.MoviesQueryService;
import com.example.chatapplication.service.write.MovieCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @IsAdmin
    public ResponseEntity<?> createMovie(){
        System.out.println("DETAIL MOVIES");
        return ResponseEntity.ok("");
    }
    private final MovieCommandService movieCommandService;
    @PutMapping("{id}")
    @IsAdmin
    public ResponseEntity<?> activeMovie(@PathVariable Long id,@RequestBody Map<String,Integer> active){
        CommonRes<?> commonRes =  movieCommandService.activeMovie(id,active.get("active"));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }


    @IsAdmin
    @DeleteMapping("")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") Long id){
        System.out.println("DETAIL MOVIES");
        return ResponseEntity.ok(moviesQueryService.findDetailMovie(id));
    }

    @GetMapping("admin/all")
    @IsAdmin
    public ResponseEntity<?> getAllMovie(){
        return ResponseEntity.ok(Utils.createSuccessResponse(moviesQueryService.getAllMovie()));
    }
}
