package com.example.chatapplication.service.write;


import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Category;
import com.example.chatapplication.domain.Packages;
import com.example.chatapplication.model.command.PackageCommand;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.response.ResponseMessage;
import com.example.chatapplication.repository.PackageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackageCommandService {

    private final PackageRepository packageRepository;


    public Packages createPackage(PackageCommand packageCommand){
        Packages packages = Packages.builder()
                .active(packageCommand.getActive())
                .code(packageCommand.getCode())
                .duration(packageCommand.getDuration())
                .name(packageCommand.getName())
                .price(packageCommand.getPrice())
                .img("")
                .build();
        return packageRepository.save(packages);
    }

    public CommonRes<?> updatePackage(Long id,PackageCommand packageCommand){
        Optional<Packages> packagesOptional = packageRepository.findById(id);
        if (packagesOptional.isEmpty()) {
            log.error("CATEGORY IS NOT FOUND");
            return Utils.createErrorResponse(400, "Dạnh mục không tồn tại");
        }
        Packages packagesUpdate = packagesOptional.get();
        Packages packages = Packages.builder()
                .id(packagesUpdate.getId())
                .active(packageCommand.getActive() != null  ? packageCommand.getActive()  : packagesUpdate.getActive())
                .code(packageCommand.getCode() != null  ?  packageCommand.getCode() : packagesUpdate.getCode())
                .duration(packageCommand.getDuration() != null  ? packageCommand.getDuration() : packagesUpdate.getDuration())
                .name(packageCommand.getName() != null  ? packageCommand.getName() : packagesUpdate.getName())
                .price(packageCommand.getPrice() != null  ? packageCommand.getPrice() : packagesUpdate.getPrice())
                .img("")
                .build();
        return Utils.createSuccessResponse(packageRepository.save(packages));
    }

    public ResponseMessage deletePackage(Long id){
        packageRepository.deleteById(id);
        return ResponseMessage.builder().message("SUCCESS").build();
    }
}
