package com.example.chatapplication.repository;

import com.example.chatapplication.domain.MovieCollections;
import com.example.chatapplication.domain.compositekey.UserMoviesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<MovieCollections, UserMoviesKey> {

    List<MovieCollections> findByIdIn(List<UserMoviesKey> userMoviesKeys);
}
