package com.example.chatapplication.service.write;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.MovieCollections;
import com.example.chatapplication.domain.compositekey.UserMoviesKey;
import com.example.chatapplication.model.command.CreateCollectionCommand;
import com.example.chatapplication.model.command.DeleteCollectionCommand;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.response.ResponseMessage;
import com.example.chatapplication.repository.CollectionRepository;
import com.example.chatapplication.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CollectionCommandService {

    private final CollectionRepository collectionRepository;

    public CommonRes<?> addMovieToCollection(Long userId, CreateCollectionCommand command){
        MovieCollections movieCollections = new MovieCollections();
        UserMoviesKey userMoviesKey = new UserMoviesKey();
        userMoviesKey.setMovieId(command.getMovieId());
        userMoviesKey.setUserId(userId);
        movieCollections.setId(userMoviesKey);
        collectionRepository.save(movieCollections);
        return Utils.createSuccessResponse(ResponseMessage.builder().message("SUCCESS").build());
    }

    public CommonRes<?> removeMovieCollection(Long userId, DeleteCollectionCommand command){
        List<UserMoviesKey> userMoviesKeys = command.getMovieIds().stream().map(e-> UserMoviesKey.builder().movieId(e).userId(userId).build()).collect(Collectors.toList());
        collectionRepository.deleteAllById(userMoviesKeys);
        return Utils.createSuccessResponse(ResponseMessage.builder().message("SUCCESS").build());
    }
}
