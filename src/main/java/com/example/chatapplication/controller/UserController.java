package com.example.chatapplication.controller;


import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.model.command.ChangePassword;
import com.example.chatapplication.model.command.UpdateUser;
import com.example.chatapplication.model.response.CommonRes;
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
    public ResponseEntity<?> getUserInfo(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
         CommonRes<?> commonRes = userQueryService.getUserInfo(userDetails.getUsername());
         return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }

    @PutMapping("user-info")
    public ResponseEntity<?> updateUserInfo(Authentication authentication,@RequestBody UpdateUser user){
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
}
