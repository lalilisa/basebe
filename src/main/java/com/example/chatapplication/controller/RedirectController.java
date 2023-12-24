package com.example.chatapplication.controller;


import com.example.chatapplication.domain.OrderPackage;
import com.example.chatapplication.repository.OrderPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RedirectController {

    private final OrderPackageRepository orderPackageRepository;
    @GetMapping("/callback-order")
    public String callBackOrder(@RequestParam  Map<String,Object> params){
        String transactionId = (String) params.get("vnp_TxnRef");
        System.out.println(params);
        Optional<OrderPackage> orderPackageOptional = orderPackageRepository.findByTransaction(transactionId);
        if(orderPackageOptional.isPresent()){
           OrderPackage orderPackage = orderPackageOptional.get();
           orderPackage.setStatus(1);
           orderPackageRepository.save(orderPackage);
        }
        return "successorder";
    }
}
