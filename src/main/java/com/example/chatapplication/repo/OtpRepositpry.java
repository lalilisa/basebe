package com.example.chatapplication.repo;

import com.example.chatapplication.domain.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OtpRepositpry extends JpaRepository<UserOtp,Long> {
    Optional<UserOtp> findByTransactionIdAndIsExpire(String transaction,Integer status);


    @Modifying
    @Query(value = "update UserOtp uo set uo.isExpire = 1 where  uo.userId = :userId ")
    void updateExpireAfterResend(@Param("userId") Long userid);
}
