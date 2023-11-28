package com.example.chatapplication.service.write;


import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Movies;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MovieCommandService {
    private final MoviesRepository moviesRepository;



    public CommonRes<?> activeMovie(Long movieId,Integer active){
        Optional<Movies> movies = moviesRepository.findById(movieId);
        if(movies.isPresent()){
            Movies movies1 = movies.get();
            movies1.setActive(active);
            return Utils.createSuccessResponse(moviesRepository.save(movies1));
        }
        return Utils.createErrorResponse(400,"Movie is not exists");
    }
}
