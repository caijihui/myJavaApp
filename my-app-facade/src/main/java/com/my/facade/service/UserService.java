package com.my.facade.service;

import com.my.facade.dao.domain.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    List getUserList(String name);

    @Async
    void doASync();

    Map<String, Object> login(User user);

    User getUserByToken(String token);
}
