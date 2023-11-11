package com.example.chatapplication.controller;

import com.example.chatapplication.model.command.CreateCollectionCommand;
import com.example.chatapplication.model.command.DeleteCollectionCommand;
import com.example.chatapplication.model.query.CollectionQuery;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.springsecurity.UserSercurity;
import com.example.chatapplication.service.read.CollectionQueryService;
import com.example.chatapplication.service.write.CollectionCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/collection")
public class CollectionController {
    private final CollectionQueryService collectionQueryService;
    private final CollectionCommandService collectionCommandService;

    @GetMapping("/mine")
    public ResponseEntity<?> getMyCollection(CollectionQuery query, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CommonRes<?> res = collectionQueryService.findMyCollection(userDetails.getUsername(), query);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @PostMapping("/mine")
    public ResponseEntity<?> addMyCollection(@RequestBody CreateCollectionCommand command, Authentication authentication) {
        UserSercurity userDetails = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> res = collectionCommandService.addMovieToCollection(userDetails.getUserId(), command);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }

    @DeleteMapping("/mine")
    public ResponseEntity<?> deleteMyCollection(@RequestBody  DeleteCollectionCommand command, Authentication authentication) {
        UserSercurity userDetails = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> res = collectionCommandService.removeMovieCollection(userDetails.getUserId(), command);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }
}
