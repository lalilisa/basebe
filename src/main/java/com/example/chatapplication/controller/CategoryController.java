package com.example.chatapplication.controller;


import com.example.chatapplication.service.read.CategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/category/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryQueryService categoryQueryService;
    @GetMapping("all")
    public ResponseEntity<?> getCAtegory(){
        return ResponseEntity.ok(categoryQueryService.categories());
    }
}
