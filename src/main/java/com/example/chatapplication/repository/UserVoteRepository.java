package com.example.chatapplication.repository;

import com.example.chatapplication.domain.UserVote;
import com.example.chatapplication.domain.compositekey.UserMoviesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVote, UserMoviesKey> {

    @Query(value = "select sum(uv.vote) from UserVote uv where uv.id.movieId = :movieId")
    Integer getSumStartVote(Long movieId);
    @Query(value = "select count (uv.vote) from UserVote uv where uv.id.movieId = :movieId")
    Integer countByMovieId(Long movieId);
}
