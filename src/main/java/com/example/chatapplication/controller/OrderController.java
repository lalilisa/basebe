package com.example.chatapplication.controller;


import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.model.command.CreateOrderCommand;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.springsecurity.UserSercurity;
import com.example.chatapplication.service.read.OrderCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderCommandService orderCommandService;
    @PostMapping()
    public ResponseEntity<?> createOrder(Authentication authentication, @RequestBody CreateOrderCommand command){
        UserSercurity userSercurity = (UserSercurity) authentication.getPrincipal();
        CommonRes<?> commonRes = Utils.createSuccessResponse(orderCommandService.createOrder(userSercurity.getUserId(),command.getPackageId()));
        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
    }
}
