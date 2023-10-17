package com.example.chatapplication.repo;

import com.example.chatapplication.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage,Long> {

    List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(Long roomId);

    @Query(value = "select cm from ChatMessage  cm inner join RoomChat rc on cm.roomId=rc.id " +
            "where cm.roomId = (select rc.id from RoomChat rc where (rc.adminId=:u1 and rc.userId=:u2) or  (rc.adminId=:u2 and rc.userId=:u1 )) ")
    List<ChatMessage> getMessageRoom(Long u1,Long u2);
}
