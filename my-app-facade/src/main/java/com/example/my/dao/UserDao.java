package com.example.my.dao;

import com.example.my.dao.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    public List<User> getUserList();

    public User getUserById(Integer id);

}
