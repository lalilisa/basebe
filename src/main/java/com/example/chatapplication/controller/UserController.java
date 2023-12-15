package com.example.chatapplication.controller;


import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.model.command.ChangePassword;
import com.example.chatapplication.model.command.UpdateUser;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.springsecurity.UserSercurity;
import com.example.chatapplication.service.read.UserQueryService;
import com.example.chatapplication.service.write.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    @GetMapping("user-info")
    public ResponseEntity<?> getUserInfo(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
         CommonRes<?> commonRes = userQueryService.getUserInfo(userDetails.getUsername());
         return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @PutMapping(value = "user-info",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateUserInfo(Authentication authentication,@ModelAttribute UpdateUser user,MultipartFile avatar){
        System.out.println(avatar);
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        CommonRes<?> commonRes = userCommandService.updateUser(userDetails.getUsername(),user);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }
    @PutMapping("change-password")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestBody ChangePassword changePassword){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        CommonRes<?> commonRes = userCommandService.changePassword(userDetails.getUsername(),changePassword);
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }


    @IsAdmin
    @GetMapping("list-user")
    public ResponseEntity<?> getUser(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userQueryService.getUser(userDetails.getUsername()));
    }


    @PostMapping(value = "upload-avatar", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadAvatar(Authentication authentication,@ModelAttribute MultipartFile multipartFile){
        UserSercurity userDetails=(UserSercurity) authentication.getPrincipal();
        return ResponseEntity.ok(userQueryService.uploadingAvatar(multipartFile,userDetails.getUserId()));
    }
}

