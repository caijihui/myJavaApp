package com.my.facade.controller;

import com.my.facade.dao.domain.User;
import com.my.common.api.CommonResult;
import com.my.facade.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;


    @GetMapping(value = "login")
    @ApiOperation(value = "jwt")
    public CommonResult login(){

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String id =  request.getParameter("id");

        User user = new User();
        user.setId(1L);
        Map<String, Object> map = userService.login(user);

        return  CommonResult.success(map);
    }

    @GetMapping(value = "me")
    @ApiOperation(value = "me")
    public CommonResult me(){

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String id =  request.getParameter("id");
        String token =  request.getParameter("token");

        User user = userService.getUserByToken(token);

        return  CommonResult.success(user);
    }

}
