package com.example.chatapplication.repository;

import com.example.chatapplication.domain.OrderPackage;
import com.example.chatapplication.domain.Packages;
import com.example.chatapplication.domain.compositekey.OrderPackageKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderPackageRepository extends JpaRepository<OrderPackage, OrderPackageKey> {


    @Query(value = "select op from OrderPackage op inner join Packages  p on op.id.packageId = p.id where  op.id.userId = :userId and p.active = 1 and  op.status = 1")
    List<OrderPackage> findOrderPackageByUserId(Long userId);

    Optional<OrderPackage> findByTransaction(String transction);


}
