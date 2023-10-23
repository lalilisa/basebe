package com.example.chatapplication.repository;

import com.example.chatapplication.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {


    List<Category> findByActiveOrderByName(Integer satatus);

    @Query(value = "select c from Category c inner join MovieCategory mc on c.id = mc.id.categoryId where mc.id.movieId in (:ids) group by c.id")
    List<Category> findByMovieIds(List<Long> ids);


    @Query(value = "select c from Category c inner join MovieCategory mc on c.id = mc.id.categoryId where mc.id.movieId = :id group by c.id")
    List<Category> findByMovieId(Long id);
}
