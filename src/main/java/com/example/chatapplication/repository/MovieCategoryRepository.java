package com.example.chatapplication.repository;

import com.example.chatapplication.domain.MovieCategory;
import com.example.chatapplication.domain.compositekey.MovieCategoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCategoryRepository extends JpaRepository<MovieCategory, MovieCategoryKey> {
}
