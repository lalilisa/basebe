package com.example.chatapplication.controller;


import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.model.command.CategoryCommand;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.service.read.CategoryQueryService;
import com.example.chatapplication.service.write.CategoryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category/")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {

    private final CategoryQueryService categoryQueryService;
    private final CategoryCommandService categoryCommandService;

    @GetMapping("all")
    public ResponseEntity<?> getCategory() {
        CommonRes<?> commonRes = Utils.createSuccessResponse(categoryQueryService.categories(false));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @IsAdmin
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin")
    public ResponseEntity<?> getCategoryAdmin() {
        CommonRes<?> commonRes = Utils.createSuccessResponse(categoryQueryService.categories(true));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @IsAdmin
    @GetMapping("{id}")
    public ResponseEntity<?> getDetailCategory(@PathVariable("id") Long id) {
        CommonRes<?> commonRes = Utils.createSuccessResponse(categoryQueryService.categories(id));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @IsAdmin
    @PostMapping()
    public ResponseEntity<?> crateCategory(@RequestBody CategoryCommand categoryCommand) {
        CommonRes<?> commonRes = categoryCommandService.createCategory(categoryCommand);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @IsAdmin
    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryCommand categoryCommand) {
        CommonRes<?> commonRes = categoryCommandService.updateCategory(id, categoryCommand);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @IsAdmin
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        CommonRes<?> commonRes = categoryCommandService.deleteCategory(id);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }
}
