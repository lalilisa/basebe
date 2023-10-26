package com.example.chatapplication.service.read;


import com.example.chatapplication.common.Category;
import com.example.chatapplication.common.Constant;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.model.command.RegisterRequest;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.view.UserView;
import com.example.chatapplication.exception.GeneralException;
import com.example.chatapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;


    public CommonRes<?> createUser(RegisterRequest registerRequest) {
        User existedUser = userRepository.findByEmailOrUsernameOrPhonenumber(registerRequest.getEmail(), registerRequest.getUsername(), registerRequest.getPhoneNumber());
        if (existedUser != null)
                return Utils.createErrorResponse(400,"E0001","Username and email is exist");
        String hashPassword = Utils.hashPassword(registerRequest.getPassword());
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(hashPassword)
                .email(registerRequest.getEmail())
                .phonenumber(registerRequest.getPhoneNumber())
                .fullname(registerRequest.getFullName())
                .gender(0)
                .isNotifi(0)
                .role(Category.Role.USER)
                .build();
        User newUser = userRepository.save(user);
        return Utils.createSuccessResponse(this.convertToView(newUser));
    }

    public CommonRes getUserInfo(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return new CommonRes<>(400, true, "User is not exist");
        }
        return new CommonRes<>(200, false, this.convertToView(user));

    }

    public UserView login(String username, String password, String fcmToken) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new GeneralException(Constant.BAD_REQUEST, Category.ErrorCodeEnum.INVALID_PARAMETER.name(), "User is not exist");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword()))
            throw new GeneralException(Constant.BAD_REQUEST, Category.ErrorCodeEnum.INVALID_PARAMETER.name(), "Password is not correct");
        user.setDeviceUUID(fcmToken);
        return this.convertToView(userRepository.save(user));
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


    public List<UserView> getUser(String username) {
        User user = userRepository.findByUsername(username);
        List<Long> ids = new ArrayList<>() {{
            add(user.getId());
        }};
        return userRepository.findByRoleAndIdNotIn(Category.Role.ADMIN, ids).stream().map(e -> convertToView(e)).collect(Collectors.toList());
    }
}
