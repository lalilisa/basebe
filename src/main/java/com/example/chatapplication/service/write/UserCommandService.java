package com.example.chatapplication.service.write;

import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.model.command.ChangePassword;
import com.example.chatapplication.model.command.UpdateUser;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.response.ResponseMessage;
import com.example.chatapplication.model.view.UserView;
import com.example.chatapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class UserCommandService {

    private final UserRepository userRepository;


    public CommonRes<?> updateUser(String username, UpdateUser userInfo) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            // throw new GeneralException(Constant.BAD_REQUEST, Category.ErrorCodeEnum.INVALID_PARAMETER.name(), "User is not exist");
            return Utils.createErrorResponse(400, "User is not exist");
        user.setFullname(userInfo.getName() != null ? userInfo.getName() : user.getFullname());
        user.setDob(userInfo.getDob() != null ? userInfo.getDob() : user.getDob());
        user.setEmail(userInfo.getEmail() != null ? userInfo.getEmail() : user.getEmail());
        user.setAddress(userInfo.getAddress() != null ? userInfo.getAddress() : user.getAddress());
        user.setPhonenumber(userInfo.getPhonenumber() != null ? userInfo.getPhonenumber() : user.getPhonenumber());
        return Utils.createSuccessResponse(this.convertToView(userRepository.save(user)));
    }

    public CommonRes<?> changePassword(String username, ChangePassword changePassword) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            //throw new GeneralException(Constant.BAD_REQUEST, Category.ErrorCodeEnum.INVALID_PARAMETER.name(), "User is not exist");
            return Utils.createErrorResponse(400, "User is not exist");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(changePassword.getOldPass(), user.getPassword()))
            return Utils.createErrorResponse(400, "Password is not correct");
        // throw new GeneralException(Constant.BAD_REQUEST, Category.ErrorCodeEnum.INVALID_PARAMETER.name(), "Password is not correct");
        user.setPassword(Utils.hashPassword(changePassword.getNewPass()));
        userRepository.save(user);
        return Utils.createSuccessResponse(ResponseMessage.builder().message("SUCCESS").build());
    }

    private UserView convertToView(User domain) {
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
