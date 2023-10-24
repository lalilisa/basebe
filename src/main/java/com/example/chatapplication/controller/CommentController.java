package com.example.chatapplication.controller;

import com.example.chatapplication.model.query.MovieQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/comment")
public class CommentController {


    @GetMapping("")
    public ResponseEntity<?> getAllCommentInMovie(MovieQuery query){

        return ResponseEntity.ok("");
    }

    @PostMapping("")
    public ResponseEntity<?> commentInMovie(MovieQuery query){
        return ResponseEntity.ok("");
    }

    @PutMapping("")
    public ResponseEntity<?> eidtcomment(MovieQuery query){
        return ResponseEntity.ok("");
    }

    @PostMapping("reply")
    public ResponseEntity<?> replyComment(MovieQuery query){
        return ResponseEntity.ok("");
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<?> replyComment(@PathVariable Long commentId){
        return ResponseEntity.ok("");
    }
}
