package com.example.chatapplication.service.write;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.common.Constant;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.dto.request.ChangePassword;
import com.example.chatapplication.dto.request.UpdateUser;
import com.example.chatapplication.dto.response.ResponseMessage;
import com.example.chatapplication.dto.view.UserView;
import com.example.chatapplication.exception.GeneralException;
import com.example.chatapplication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class UserCommandService {

    private final UserRepository userRepository;


    public UserView updateUser(String username,UpdateUser userInfo){
            User user=userRepository.findByUsername(username);
             if(user==null)
            throw new GeneralException(Constant.BAD_REQUEST, Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"User is not exist");
             user.setFullname(userInfo.getName());
             user.setDob(userInfo.getDob());
             user.setEmail(userInfo.getEmail());
             user.setAddress(userInfo.getAddress());
             user.setPhonenumber(userInfo.getPhonenumber());
             return this.convertToView(userRepository.save(user));
    }

    public ResponseMessage changePassword(String username,ChangePassword changePassword){
        User user=userRepository.findByUsername(username);
        if(user==null)
            throw new GeneralException(Constant.BAD_REQUEST, Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"User is not exist");
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if(!encoder.matches(changePassword.getOldPass(),user.getPassword()))
            throw new GeneralException(Constant.BAD_REQUEST,Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"Password is not correct");
        user.setPassword(Utils.hashPassword(changePassword.getNewPass()));
        userRepository.save(user);
        return ResponseMessage.builder().message("SUCCESS").build();
    }
    private UserView convertToView(User domain){
        return UserView
                .builder()
                .id(domain.getId())
                .dob(domain.getDob())
                .address(domain.getAddress())
                .email(domain.getEmail())
                .fullname(domain.getFullname())
                .username(domain.getUsername())
                .phonnumber(domain.getPhonenumber())
                .role(domain.getRole())
                .gender(domain.getGender())
                .isNotifi(domain.getIsNotifi())
                .build();
    }
}
