package com.example.chatapplication.repo;

import com.example.chatapplication.domain.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenDeviceRepoditory extends JpaRepository<DeviceToken,Long> {

    DeviceToken findByUserId(Long userId);

    DeviceToken findByUserIdIn(List<Long> ids);
}
