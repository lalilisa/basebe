package com.example.chatapplication.repository;

import com.example.chatapplication.domain.Actor;
import com.example.chatapplication.domain.CastMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Long> {

    @Query(value = "select a from Actor a inner join CastMovie mc on a.id = mc.id.castId " +
            "inner join Movies m on m.id = mc.id.movieId " +
            "where mc.id.movieId = :movieId" )
    Page<Actor> findRelatedActor(Long movieId, Pageable pageable);
}
