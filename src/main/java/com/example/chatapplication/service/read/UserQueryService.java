package com.example.chatapplication.service.read;


import com.example.chatapplication.common.Category;
import com.example.chatapplication.common.Constant;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.dto.view.UserView;
import com.example.chatapplication.exception.GeneralException;
import com.example.chatapplication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserQueryService{

    private final UserRepository userRepository;




    public UserView createUser(String username,String password,String email){
        User existedUser=userRepository.findByEmailOrUsername(email,username);
        if(existedUser!=null)
            throw new GeneralException(Constant.BAD_REQUEST,Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"Username and email is not exist");
        String hashPassword= Utils.hashPassword(password);
        User user=User.builder()
                .username(username)
                .password(hashPassword)
                .email(email)
                .gender(0)
                .isNotifi(0)
                .role(Category.Role.USER)
                .build();
        User newUser=userRepository.save(user);
        initCart(newUser);
        return this.convertToView(newUser);
    }
    public UserView getUserInfo(String username){
        User user=userRepository.findByUsername(username);
        if(user==null)
            throw new GeneralException(Constant.BAD_REQUEST,Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"User is not exist");
        return this.convertToView(user);
    }
    public UserView login(String username,String password){

        User user=userRepository.findByUsername(username);
        if(user==null)
            throw new GeneralException(Constant.BAD_REQUEST,Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"User is not exist");
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if(!encoder.matches(password,user.getPassword()))
            throw new GeneralException(Constant.BAD_REQUEST,Category.ErrorCodeEnum.INVALID_PARAMETER.name(),"Password is not correct");
        return this.convertToView(user);
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

    private void initCart(User user){

    }

    public List<UserView> getUser(String username){
        User user=userRepository.findByUsername(username);
        List<Long> ids=new ArrayList<>(){{add(user.getId());}};
        if(user.getRole().name().equals("ADMIN"))
              return  userRepository.findByIdNotIn(ids).stream().map(this::convertToView).collect(Collectors.toList());
        return userRepository.findByRoleAndIdNotIn(Category.Role.ADMIN,ids).stream().map(user1 -> convertToView(user1)).collect(Collectors.toList());
    }
}
