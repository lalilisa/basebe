package com.example.chatapplication.service.read;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.Actor;
import com.example.chatapplication.model.query.MovieQuery;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.view.ActorView;
import com.example.chatapplication.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ActorQueryService {

    private final ActorRepository actorRepository;

    public CommonRes<?> findOneActorById(Long id){
        Optional<Actor> actor = actorRepository.findById(id);
        if(actor.isEmpty()){
            return Utils.createErrorResponse(400,"Actor is not exist");
        }
        return Utils.createSuccessResponse(convertToView(actor.get()));
    }


    public CommonRes<?> findRelatedActorMovie(MovieQuery query){
       Page<Actor> actors = actorRepository.findRelatedActor(query.getMovieId(),query.pageable());
       Page<ActorView> actorViews = actors.map(e-> convertToView(e));
       return Utils.createSuccessResponse(actors);
    }

    private ActorView convertToView(Actor domain){
        return  ActorView.builder()
                .id(domain.getId())
                .dob(domain.getDob())
                .avatar(domain.getAvatar())
                .nickName(domain.getNickName())
                .profile(domain.getProfile())
                .fullName(domain.getFullName())
                .build();
    }
}
