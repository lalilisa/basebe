package com.example.chatapplication.service.read;


import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.OrderPackage;
import com.example.chatapplication.domain.Packages;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.repository.OrderPackageRepository;
import com.example.chatapplication.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackageQueryService {

    private final PackageRepository packageRepository;

    private final OrderPackageRepository orderPackageRepository;
    public List<Packages> getAllPackage(){
        return packageRepository.findAll();
    }

    public Packages getDetailPackage(Long id){
        return packageRepository.findById(id).orElse(null);
    }

    public List<Packages> getMyAllPackage(Long userId){
        List<OrderPackage> orderPackages = orderPackageRepository.findOrderPackageByUserId(userId);
        List<Long>  packageIds =  orderPackages.stream().map(e->e.getId().getPackageId()).collect(Collectors.toList());
        return packageRepository.findAllById(packageIds);
    }
}
