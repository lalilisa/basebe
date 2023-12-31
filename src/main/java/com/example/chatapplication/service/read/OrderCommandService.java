package com.example.chatapplication.service.read;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.OrderPackage;
import com.example.chatapplication.domain.compositekey.OrderPackageKey;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.repository.OrderPackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderCommandService {

    private final OrderPackageRepository orderPackageRepository;

    public String createOrder(Long userId,Long packageId,String vnp_TxnRef){
        OrderPackageKey orderPackageKey =new OrderPackageKey(userId,packageId);
        OrderPackage orderPackage = OrderPackage.builder().id(orderPackageKey).status(0).transaction(vnp_TxnRef).build();

        return orderPackageRepository.save(orderPackage).getTransaction();
    }
}
