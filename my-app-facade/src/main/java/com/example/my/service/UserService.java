package com.example.my.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List getUserList(String name);

    @Async
    void doASync();
}
