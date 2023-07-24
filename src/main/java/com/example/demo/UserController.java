package com.example.demo;

import com.example.demo.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@PostMapping("/users")
	public String newUser(@RequestBody User newUser) {
		System.out.printf("New User: %s\n", newUser);
		return "OK";
	}
}