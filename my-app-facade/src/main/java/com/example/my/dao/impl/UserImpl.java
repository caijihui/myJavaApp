package com.example.my.dao.impl;

import com.example.my.dao.UserDao;
import com.example.my.dao.mapper.UserMapper;
import com.example.my.dao.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserImpl implements UserDao {

    @Autowired
    UserMapper userMapper;

    public List<User> getUserList() {

        List<User> userList = userMapper.selectList(null);


//        ArrayList<Integer> ids = new ArrayList<>();
//        ids.add(3);
//        ids.add(5);
//        List<User>  userList1  = userMapper.selectBatchIds(ids);

        return userList;
    }

    public User getUserById(Integer id) {
        User user = userMapper.selectById(id);
        return user;
    }
}
