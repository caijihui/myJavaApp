package com.my.facade.dao.impl;

import com.my.facade.dao.UserDao;
import com.my.facade.dao.mapper.UserMapper;
import com.my.facade.dao.domain.User;
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

    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        return user;
    }
}
