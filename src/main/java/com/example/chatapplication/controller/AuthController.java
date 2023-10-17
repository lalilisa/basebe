package com.example.chatapplication.controller;


import com.example.chatapplication.common.Constant;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.dto.request.LoginRequest;
import com.example.chatapplication.dto.request.OtpVerifi;
import com.example.chatapplication.dto.request.PhonenumberRequest;
import com.example.chatapplication.dto.request.RegisterRequest;
import com.example.chatapplication.dto.response.LoginResponse;
import com.example.chatapplication.dto.response.OtpResponse;
import com.example.chatapplication.dto.response.QrLogin;
import com.example.chatapplication.dto.response.ResponseMessage;
import com.example.chatapplication.dto.view.UserView;
import com.example.chatapplication.repo.UserRepository;
import com.example.chatapplication.service.read.UserQueryService;
import com.example.chatapplication.service.write.OtpCommandService;
import com.example.chatapplication.util.JwtTokenUtil;
import com.google.zxing.WriterException;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    @Value("${HOST_PHONE}")
    private String hostPhone;

    private final UserQueryService userQueryService;

    private final JwtTokenUtil jwtTokenUtil;

    private final OtpCommandService otpCommandService;
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        UserView user=userQueryService.login(loginRequest.getUsername(),loginRequest.getPassword());
        Date expireAccess=new Date(System.currentTimeMillis() + Constant.JWT_TOKEN_VALIDITY * 1000);
        Date expireRefresh=new Date(System.currentTimeMillis() + Constant.JWT_TOKEN_VALIDITY * 10000);
        String accessToken=jwtTokenUtil.generateAccountToken(user,expireAccess);
        String refreshToken=jwtTokenUtil.generateAccountToken(user,expireRefresh);
        return ResponseEntity.ok(LoginResponse.builder()
                    .userId(user.getId())
                    .role(user.getRole())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expireAccressToken(expireAccess)
                 .isNotifi(user.getIsNotifi())
                    .build());
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        UserView user=userQueryService.createUser(registerRequest.getUsername(),registerRequest.getPassword(),registerRequest.getEmail());
        return ResponseEntity.ok(user);
    }

    @PostMapping("send-otp")
    public ResponseEntity<?> sendotp(@RequestBody PhonenumberRequest request){
        String phone=request.getPhonenumber();
        return ResponseEntity.ok(otpCommandService.sendOtp(phone));
    }


    @PostMapping("verifi-otp")
    public ResponseEntity<?> verifi(@RequestBody OtpVerifi request){
        return ResponseEntity.ok(otpCommandService.verifiOtp(request.getTransactionId(),request.getOtp()));
    }
    private final static String symbol="%";
    @GetMapping(value = "get-qr")
    public ResponseEntity<?> genQrLogin(Authentication authentication, HttpServletResponse response) throws IOException, WriterException {
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        long expireTime=new Date().getTime()+(long)60000;
        String socketKey= UUID.randomUUID().toString();
        String raw=userDetails.getUsername().concat(symbol).concat(Long.toString(expireTime)).concat(symbol).concat(socketKey);
        return ResponseEntity.ok(QrLogin.builder().img(Utils.genQrCode(raw)).socketKey(socketKey).build());
    }
    public static String autogenPassword(){
        return new Random().ints(10, 33, 122).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    @PostMapping("forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam("email") String email) throws IOException, WriterException, MessagingException {
        User user= userRepository.findByEmail(email);
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String newPass=autogenPassword();
        user.setPassword(hashPassword("trimai"));
        userRepository.save(user);
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText(String.format("Mật khẩu mới của bạn là %s . Tuyệt đối không được cung cấp cho người khác", newPass));
        javaMailSender.send(mimeMessage);
        return ResponseEntity.ok(ResponseMessage.builder().message("Vui lòng check Email của bạn").build());
    }
    public static String hashPassword(String plainText){
        int salt = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(salt, new SecureRandom());
        return bCryptPasswordEncoder.encode(plainText);
    }
}
