package com.example.chatapplication.repository;


import com.example.chatapplication.domain.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Packages,Long> {

    Optional<Packages> findByCodeAndActive(String code,Boolean active);

    @Query(value = "select p from Packages p inner join MoviePackage mp on mp.id.packageId = p.id " +
            "inner join Movies m on mp.id.movieId = m.id where m.id = :movieId")
    List<Packages> findPackagesByMovieIn(Long movieId);
}
