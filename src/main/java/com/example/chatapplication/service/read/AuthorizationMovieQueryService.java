package com.example.chatapplication.service.read;


import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.OrderPackage;
import com.example.chatapplication.domain.Packages;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.view.AuthorizeMovieView;
import com.example.chatapplication.repository.MoviesRepository;
import com.example.chatapplication.repository.OrderPackageRepository;
import com.example.chatapplication.repository.PackageRepository;
import com.example.chatapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthorizationMovieQueryService {

    private final OrderPackageRepository orderPackageRepository;
    private final UserRepository userRepository;
    private final MoviesRepository moviesRepository;
    private final PackageRepository packageRepository;

    public CommonRes<?> checkAuthorizedMovie(Long userId,Long movieId){
        List<OrderPackage> myPackages = orderPackageRepository.findOrderPackageByUserId(userId);
        List<Packages> packagesMovieIn = packageRepository.findPackagesByMovieIn(movieId);
        List<Long> packageIds = packagesMovieIn.stream().map(e-> e.getId()).collect(Collectors.toList());
        List<OrderPackage> checkAuthorized = myPackages.stream().filter(e-> packageIds.contains(e.getId().getPackageId())).collect(Collectors.toList());
        if(checkAuthorized.isEmpty()){
            return Utils.createSuccessResponse(AuthorizeMovieView.builder().isAuthorized(0).build());
        }
        return Utils.createSuccessResponse(AuthorizeMovieView.builder().isAuthorized(1).build());
    }
}
