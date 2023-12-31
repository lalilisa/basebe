package com.example.chatapplication.service.read;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.repository.NotificationsRepository;
import com.example.chatapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationQueryService {



    private final UserRepository userRepository;

    private final NotificationsRepository notificationsRepository;



    public CommonRes<?> myNotifi(String username){
        User user=userRepository.findByUsername(username);
        if(user==null)
//            throw new GeneralException(Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"User is not exist");
            return Utils.createErrorResponse(400,"User is not exist");
       return Utils.createSuccessResponse(notificationsRepository.findByUserIdOrderByCreatedAtDesc(user.getId()));
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
