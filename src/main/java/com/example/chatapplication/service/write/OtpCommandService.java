package com.example.chatapplication.service.write;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.common.Constant;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.domain.UserOtp;
import com.example.chatapplication.dto.response.LoginResponse;
import com.example.chatapplication.dto.view.SendOtpView;
import com.example.chatapplication.dto.view.UserView;
import com.example.chatapplication.exception.GeneralException;
import com.example.chatapplication.repo.OtpRepositpry;
import com.example.chatapplication.repo.UserOtpRepository;
import com.example.chatapplication.repo.UserRepository;
import com.example.chatapplication.util.JwtTokenUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpCommandService {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String sid;
    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;
    @Value("${HOST_PHONE}")
    private String hostPhone;
    private final UserRepository userRepository;
    private final OtpRepositpry otpRepositpry;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public SendOtpView sendOtp(String phonenumber){
        User user=userRepository.findByPhonenumber(phonenumber);
        if(user==null)
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_FORMAT.name(),"User is not exist");
        otpRepositpry.updateExpireAfterResend(user.getId());
        String otp="300801";
        String formatPhone=phonenumber.replaceFirst("0","+84");
        Message message=Message.creator(
                        new com.twilio.type.PhoneNumber(formatPhone),
                        new com.twilio.type.PhoneNumber(hostPhone),otp
                        )
                .create();
        if(message.getStatus().equals(Message.Status.FAILED))
            throw new GeneralException(Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"send otp fail");
        UUID uuid = UUID.randomUUID();
        otpRepositpry.save(UserOtp.builder().otp(otp).transactionId(uuid.toString()).userId(user.getId()).isExpire(0).build());
        return SendOtpView.builder().transactionId(uuid.toString()).otp(otp).build();
    }


    public LoginResponse verifiOtp(String transaction, String otp){
        Optional<UserOtp> userOtpOptional=otpRepositpry.findByTransactionIdAndIsExpire(transaction,0);
        if(userOtpOptional.isEmpty())
            throw new GeneralException("Otp Không hợp lệ");
        UserOtp userOtp=userOtpOptional.get();
        if(!otp.equals(userOtp.getOtp())){
            throw new GeneralException("Otp Không chính xác");
        }
        User user=userRepository.findById(userOtp.getUserId()).orElse(null);
        userOtp.setIsExpire(1);
        otpRepositpry.save(userOtp);
        Date expireAccess=new Date(System.currentTimeMillis() + Constant.JWT_TOKEN_VALIDITY * 1000);
        Date expireRefresh=new Date(System.currentTimeMillis() + Constant.JWT_TOKEN_VALIDITY * 10000);
        String accessToken=jwtTokenUtil.generateAccountToken(user,expireAccess);
        String refreshToken=jwtTokenUtil.generateAccountToken(user,expireRefresh);
        return  LoginResponse.builder()
                        .accessToken(accessToken)
                        .userId(user.getId())
                        .role(user.getRole())
                        .refreshToken(refreshToken)
                        .expireAccressToken(expireAccess)
                        .build();
    }

}
