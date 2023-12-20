package com.example.chatapplication.controller;

import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.model.command.CreateMovieCommand;
import com.example.chatapplication.model.command.SubMovieCommand;
import com.example.chatapplication.model.query.MoviesFilterQuery;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.springsecurity.UserSercurity;
import com.example.chatapplication.service.fileservice.FilesStorageService;
import com.example.chatapplication.service.read.MoviesQueryService;
import com.example.chatapplication.service.write.MovieCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/movies")
public class MoviesController {

    private final MoviesQueryService moviesQueryService;

    private final FilesStorageService storageService;
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


    @IsAdmin
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllMovie(){
        return ResponseEntity.ok(Utils.createSuccessResponse(moviesQueryService.getAllMovie()));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getDetailMovies(@PathVariable("id") Long id, Authentication authentication){
        Optional<UserSercurity> userSercurity = Optional.ofNullable(authentication)
                .map(e -> (UserSercurity) authentication.getPrincipal())
                .stream().findFirst();
        System.out.println("DETAIL MOVIES");
        System.out.println(userSercurity.orElse(null));
        return ResponseEntity.ok(moviesQueryService.findDetailMovie(id, userSercurity.map(UserSercurity::getUserId).orElse(null)));
    }


    @PostMapping(value = "", consumes = {"multipart/form-data"})
//    @IsAdmin
    public ResponseEntity<?> createMovie(@ModelAttribute CreateMovieCommand createMovieCommand){
        CommonRes<?> commonRes = movieCommandService.createMovie(createMovieCommand);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @GetMapping(value = "submovie/{id}")
//    @IsAdmin
    public ResponseEntity<?> getListSubMovie(@PathVariable Long id){
        CommonRes<?> commonRes = Utils.createSuccessResponse(moviesQueryService.getSubMovie(id));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @GetMapping(value = "submovie/detail/{id}")
//    @IsAdmin
    public ResponseEntity<?> getDetailSubMovie(@PathVariable Long id){
        CommonRes<?> commonRes = Utils.createSuccessResponse(moviesQueryService.getDetailMovie(id));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }
    @PostMapping(value = "submovie", consumes = {"multipart/form-data"})
//    @IsAdmin
    public ResponseEntity<?> createSubMovie(@ModelAttribute SubMovieCommand command){
        CommonRes<?> commonRes = movieCommandService.createSubMovie(command);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @PutMapping(value = "submovie/{id}", consumes = {"multipart/form-data"})
//    @IsAdmin
    public ResponseEntity<?> eidtSubMovie(@PathVariable Long id,@ModelAttribute SubMovieCommand command){
        System.out.println(id);
        CommonRes<?> commonRes = movieCommandService.updateSubMovie(id,command);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @PutMapping(value = "{id}", consumes = {"multipart/form-data"})
//    @IsAdmin
    public ResponseEntity<?> eidtMovie(@PathVariable Long id,@ModelAttribute CreateMovieCommand command){
        CommonRes<?> commonRes = movieCommandService.updateMovie(id,command);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }
    @DeleteMapping(value = "submovie/{id}")
//    @IsAdmin
    public ResponseEntity<?> deleteSubMovie(@PathVariable Long id){
        CommonRes<?> commonRes = movieCommandService.deleteSubMovie(id);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @PutMapping("active/{id}")
    @IsAdmin
    public ResponseEntity<?> activeSubMovie(@PathVariable Long id,@RequestBody Map<String,Integer> active){
        CommonRes<?> commonRes =  movieCommandService.activeMovie(id,active.get("active"));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }
    private final MovieCommandService movieCommandService;
    @PutMapping("/submovie/active/{id}")
    @IsAdmin
    public ResponseEntity<?> activeMovie(@PathVariable Long id,@RequestBody Map<String,Integer> active){
        CommonRes<?> commonRes =  movieCommandService.activeSubMovie(id,active.get("active"));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }


    @IsAdmin
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") Long id){
        System.out.println("DETAIL MOVIES");
        return ResponseEntity.ok(moviesQueryService.deleteMovie(id));
    }


}
