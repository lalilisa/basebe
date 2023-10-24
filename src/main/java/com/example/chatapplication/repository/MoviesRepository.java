package com.example.chatapplication.repository;

import com.example.chatapplication.domain.Movies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {

    @Query(value = "select  m from Movies m " +
            "inner join MovieCategory mc on m.id = mc.id.movieId " +
            "inner join Category c on c.id = mc.id.categoryId where c.code = :code and m.active = 1")
    Page<Movies> findMoviesByQueryRequest(Pageable pageable);

    @Query(value = "select  m from Movies m " +
                    "inner join MovieCollections mc on m.id = mc.id.movieId " +
                    "inner join User u on u.id = mc.id.userId " +
                    "where m.active = :active and u.username = :username")
    Page<Movies> findCollectionByUsername(String username, Integer active, Pageable pageable);
}

