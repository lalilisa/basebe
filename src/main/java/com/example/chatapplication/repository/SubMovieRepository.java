package com.example.chatapplication.repository;

import com.example.chatapplication.domain.SubMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubMovieRepository extends JpaRepository<SubMovie,Long> {

    List<SubMovie> findByMovieIdOrderByEpisode(Long movieId);
}
