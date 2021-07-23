package com.my.facade;

import com.my.facade.cd.test.People;
import com.my.facade.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MyApplicationTests {

	@Autowired
	private People people;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	void inner() {

		people.fun();


		userService.getUserList("zd");

		people.fun();
		System.out.println("hello ");

		userService.getUserList("zd");

	}

}
