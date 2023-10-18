package com.example.chatapplication;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class Application {
    @Value("${TWILIO_ACCOUNT_SID}")
    private String sid;
    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @PostConstruct
    public void initPhoneOTP(){
        Twilio.init(sid,authToken);
    }

}
