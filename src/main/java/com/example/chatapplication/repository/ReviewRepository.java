package com.example.chatapplication.repository;

import com.example.chatapplication.domain.ReviewMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReviewRepository extends JpaRepository<ReviewMovie,Long> {


    @Query(value = "select rv from ReviewMovie rv where rv.movieId = :movieId and rv.parrentId is null")
    List<ReviewMovie> findReviewInMoview(Long movieId);

    List<ReviewMovie> findReviewMovieByParrentId(Long parrentId);

    Optional<ReviewMovie> findFirstByUserIdAndId(Long userid,Long reviewId);
}
