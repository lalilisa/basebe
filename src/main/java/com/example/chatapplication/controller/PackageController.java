package com.example.chatapplication.controller;

import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.model.command.PackageCommand;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.springsecurity.UserSercurity;
import com.example.chatapplication.service.read.PackageQueryService;
import com.example.chatapplication.service.write.PackageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/package")
public class PackageController {
    private final PackageQueryService packageQueryService;
    private final PackageCommandService packageCommandService;

    @GetMapping("")
    public ResponseEntity<?> getAllPackage() {
        CommonRes<?> commonRes = Utils.createSuccessResponse(packageQueryService.getAllPackage());
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @GetMapping("my-package")
    public ResponseEntity<?> getMyPackage(Authentication authentication) {
        UserSercurity userSercurity = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> commonRes = Utils.createSuccessResponse(packageQueryService.getMyAllPackage(userSercurity.getUserId()));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getDetailPackage(@PathVariable Long id) {
        CommonRes<?> commonRes = Utils.createSuccessResponse(packageQueryService.getDetailPackage(id));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @IsAdmin
    @PostMapping("")
    public ResponseEntity<?> createPackage(@RequestBody PackageCommand packageCommand) {
        return ResponseEntity.ok(packageCommandService.createPackage(packageCommand));
    }

    @IsAdmin
    @PutMapping("{id}")
    public ResponseEntity<?> updatePackage(@PathVariable("id") Long id, @RequestBody PackageCommand packageCommand) {

        return ResponseEntity.ok(packageCommandService.updatePackage(id, packageCommand));
    }

    @IsAdmin
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletPackage(@PathVariable("id") Long id) {
        return ResponseEntity.ok(packageCommandService.deletePackage(id));
    }
}
