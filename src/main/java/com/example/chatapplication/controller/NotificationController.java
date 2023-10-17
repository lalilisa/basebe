package com.example.chatapplication.controller;


import com.example.chatapplication.domain.DeviceToken;
import com.example.chatapplication.domain.Notifications;
import com.example.chatapplication.dto.request.FCMToken;
import com.example.chatapplication.service.read.NotificationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/notification")
public class NotificationController {
    private final NotificationQueryService notificationQueryService;
    @PostMapping("fcm")
    public DeviceToken deviceToken(@RequestBody FCMToken fcmToken, Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return notificationQueryService.tokenNotifi(userDetails.getUsername(),fcmToken.getToken());
    }
    @GetMapping("me")
    public List<Notifications> myNotification(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return notificationQueryService.myNotifi(userDetails.getUsername());
    }
}
