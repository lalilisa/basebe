package com.example.chatapplication.controller;


import com.example.chatapplication.dto.request.ChangePassword;
import com.example.chatapplication.dto.request.UpdateUser;
import com.example.chatapplication.dto.response.ResponseMessage;
import com.example.chatapplication.dto.view.UserView;
import com.example.chatapplication.service.read.UserQueryService;
import com.example.chatapplication.service.write.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    @GetMapping("user-info")
    public UserView getUserInfo(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return userQueryService.getUserInfo(userDetails.getUsername());
    }

    @PutMapping("user-info")
    public UserView updateUserInfo(Authentication authentication,@RequestBody UpdateUser user){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return userCommandService.updateUser(userDetails.getUsername(),user);
    }
    @PutMapping("change-password")
    public ResponseMessage changePassword(Authentication authentication, @RequestBody ChangePassword changePassword){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return userCommandService.changePassword(userDetails.getUsername(),changePassword);
    }

    @GetMapping("list-user")
    public ResponseEntity<?> getUser(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userQueryService.getUser(userDetails.getUsername()));
    }
}
