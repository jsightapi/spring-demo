package com.example.demo;

import com.example.demo.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

@RestController
public class UserController {

	@PostMapping("/users")
	public ResponseEntity<String> newUser(@RequestBody User newUser) {
		System.out.printf("New User: %s\n", newUser);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Header", "x header value");
		return ResponseEntity.ok().headers(headers).body("\"OK\"");
	}
}