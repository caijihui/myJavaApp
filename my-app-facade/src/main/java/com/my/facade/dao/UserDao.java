package com.my.facade.dao;

import com.my.facade.dao.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    public List<User> getUserList();

    public User getUserById(Long id);


}
