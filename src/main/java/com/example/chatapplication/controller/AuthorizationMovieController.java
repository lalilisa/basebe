package com.example.chatapplication.controller;


import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.springsecurity.UserSercurity;
import com.example.chatapplication.service.read.AuthorizationMovieQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/authhorizemovie/")
@Slf4j
@RequiredArgsConstructor
public class AuthorizationMovieController {
    private final AuthorizationMovieQueryService authorizationMovieQueryService;
    @PostMapping("{movieId}")
    public ResponseEntity<?> checkAuthorizeMovie(@PathVariable("movieId") Long movieId, Authentication authentication){
        UserSercurity userDetails = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> commonRes = authorizationMovieQueryService.checkAuthorizedMovie(userDetails.getUserId(),movieId);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }
}
