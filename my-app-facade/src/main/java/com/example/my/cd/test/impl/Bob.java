package com.example.my.cd.test.impl;

import com.example.my.cd.test.People;
import org.springframework.stereotype.Service;

@Service
public class Bob implements People {

    @Override
    public void fun() {
        System.out.println("get");
    }
}
