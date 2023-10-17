package com.example.chatapplication.repo;

import com.example.chatapplication.domain.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications,Long> {


    List<Notifications> findByUserIdOrderByCreatedAtDesc(Long userId);
}
