package com.example.chatapplication.controller;


import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.service.read.NotificationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/notification")
public class NotificationController {
    private final NotificationQueryService notificationQueryService;
//    @PostMapping("fcm")
//    public DeviceToken deviceToken(@RequestBody FCMToken fcmToken, Authentication authentication){
//        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
//        return notificationQueryService.tokenNotifi(userDetails.getUsername(),fcmToken.getToken());
//    }
    @GetMapping("me")
    public ResponseEntity<?> myNotification(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        CommonRes<?> commonRes = notificationQueryService.myNotifi(userDetails.getUsername());
        return ResponseEntity.status(commonRes.getStatusCode()).body((commonRes));
    }
}
