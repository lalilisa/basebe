package com.example.chatapplication.controller;

import com.example.chatapplication.config.CloudinaryService;
import com.example.chatapplication.model.command.MovieCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("test/file")
public class TestFileController {

    private final CloudinaryService cloudinaryService;
    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public ResponseEntity<?> testUpload(@ModelAttribute MovieCommand command) {
        System.out.println(command.getFile().getOriginalFilename());
        return ResponseEntity.ok(cloudinaryService.uploadURl(command.getFile()));
    }
}
