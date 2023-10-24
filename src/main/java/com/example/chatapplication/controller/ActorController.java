package com.example.chatapplication.controller;


import com.example.chatapplication.model.query.MovieQuery;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.service.read.ActorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/actor")
public class ActorController {

    private final ActorQueryService actorQueryService;

    @GetMapping("{id}")
    public ResponseEntity<?> findActorById(@PathVariable Long id) {
        CommonRes<?> res = actorQueryService.findOneActorById(id);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @GetMapping("/relateInMovies")
    public ResponseEntity<?> findActorRelateInMovie(MovieQuery query) {
        CommonRes<?> res = actorQueryService.findRelatedActorMovie(query);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
