package com.example.chatapplication.service.read;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.domain.Notifications;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.exception.GeneralException;
import com.example.chatapplication.repo.NotificationsRepository;
import com.example.chatapplication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationQueryService {



    private final UserRepository userRepository;

    private final NotificationsRepository notificationsRepository;



    public List<Notifications> myNotifi(String username){
        User user=userRepository.findByUsername(username);
        if(user==null)
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"User is not exist");
       return notificationsRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    /*@Scheduled(cron = "")
    private void sendNewNotifi(){
        Map<String,String> map=new HashMap<>();
        map.put("type","ALL");
        Notice notice=Notice.builder()
                .subject("Chào ngày mới")
                .content("Hãy mua sắm để xả stress nào")
                .data(map)
                .image(null)
                .registrationTokens(tokenDeviceRepoditory.findAll().stream().map(DeviceToken::getFcmToken).collect(Collectors.toList()))
                .build();
    }*/
}
