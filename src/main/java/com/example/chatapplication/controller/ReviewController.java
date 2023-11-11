package com.example.chatapplication.controller;

import com.example.chatapplication.model.command.CreateReviewCommand;
import com.example.chatapplication.model.command.DeleteReviewCommand;
import com.example.chatapplication.model.command.UpdateReviewCommand;
import com.example.chatapplication.model.command.VoteMovieCommand;
import com.example.chatapplication.model.query.CommentQuery;
import com.example.chatapplication.model.query.MovieQuery;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.springsecurity.UserSercurity;
import com.example.chatapplication.service.read.ReviewQueryService;
import com.example.chatapplication.service.write.ReviewCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/review")
@Slf4j
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @GetMapping("")
    public ResponseEntity<?> getAllCommentInMovie(MovieQuery query, Authentication authentication) {
        Optional<UserSercurity> userSercurity = Optional.ofNullable(authentication)
                .map(e -> (UserSercurity) authentication.getPrincipal())
                .stream().findFirst();
        log.warn("API GET COMMENT");
        CommonRes<?> res = reviewQueryService.findReviewMovies(query, userSercurity.isEmpty() ? null : userSercurity.get().getUserId());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @GetMapping("reply")
    public ResponseEntity<?> getAllCommentInMovie(CommentQuery query, Authentication authentication) {
        Optional<UserSercurity> userSercurity = Optional.ofNullable(authentication)
                .map(e -> (UserSercurity) authentication.getPrincipal())
                .stream().findFirst();
        log.warn("API GET COMMENT");
        CommonRes<?> res = reviewQueryService.findReplyReviewMovies(query, userSercurity.isEmpty() ? null : userSercurity.get().getUserId());
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
    @PostMapping("")
    public ResponseEntity<?> reviewInMovie(Authentication authentication,@RequestBody CreateReviewCommand command) {
        UserSercurity userSercurity = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> res = reviewCommandService.reviewMovie(userSercurity.getUserId(), command);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @PutMapping("")
    public ResponseEntity<?> editcomment(Authentication authentication,@RequestBody UpdateReviewCommand command) {
        UserSercurity userSercurity = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> res = reviewCommandService.updateReviewMovie(userSercurity.getUserId(), command);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @PostMapping("reply")
    public ResponseEntity<?> replyReview(Authentication authentication,@RequestBody CreateReviewCommand command) {
        UserSercurity userSercurity = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> res = reviewCommandService.reply(userSercurity.getUserId(), command);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteReview(Authentication authentication,@RequestBody DeleteReviewCommand command) {
        UserSercurity userSercurity = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> res = reviewCommandService.deleteReviewMovie(userSercurity.getUserId(), command);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @PostMapping("vote")
    public ResponseEntity<?> voteMovies(Authentication authentication,@RequestBody VoteMovieCommand command) {
        UserSercurity userSercurity = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> res = reviewCommandService.voteMovie(userSercurity.getUserId(), command);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
