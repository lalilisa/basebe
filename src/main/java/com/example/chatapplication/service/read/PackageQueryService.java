package com.example.chatapplication.service.read;


import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Packages;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackageQueryService {

    private final PackageRepository packageRepository;

    public List<Packages> getAllPackage(){
        return packageRepository.findAll();
    }

    public Packages getDetailPackage(Long id){
        return packageRepository.findById(id).orElse(null);
    }
}
