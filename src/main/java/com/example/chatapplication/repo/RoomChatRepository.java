package com.example.chatapplication.repo;

import com.example.chatapplication.domain.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomChatRepository extends JpaRepository<RoomChat,Long> {


    @Query(value = "select r from RoomChat r where  r.adminId =:u1 or r.userId=:u1 ")
    List<RoomChat> findRoomChat(Long u1);
}
