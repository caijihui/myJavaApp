package com.my.facade.cd.test.impl;

import com.my.facade.cd.test.People;
import org.springframework.stereotype.Service;

@Service
public class Bob implements People {

    @Override
    public void fun() {
        System.out.println("get");
    }
}
