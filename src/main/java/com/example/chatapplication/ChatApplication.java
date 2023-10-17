package com.example.chatapplication;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ChatApplication {
    @Value("${TWILIO_ACCOUNT_SID}")
    private String sid;
    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;


    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }


    @PostConstruct
    public void initPhoneOTP(){
        Twilio.init(sid,authToken);
    }

}
