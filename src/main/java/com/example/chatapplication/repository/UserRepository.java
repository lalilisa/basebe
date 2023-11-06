package com.example.chatapplication.repository;

import com.example.chatapplication.common.Category;
import com.example.chatapplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    User findByEmail(String email);

    List<User> findByEmailOrUsernameOrPhonenumber(String email,String username,String phoneNumber);

    User findByPhonenumber(String phonenumber);

    List<User> findByRoleAndIdNotIn(Category.Role role,List<Long> ids);


    List<User> findByIdNotIn(List<Long> ids);
}
