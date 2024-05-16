package com.sample.springbootjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springbootjwt.entity.User;
import com.sample.springbootjwt.service.UserService;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/currentLoggedInUser")
	public ResponseEntity<User> currentLoggedInUser(Authentication authentication) {
		String username = authentication.getName();
		User currentUser = userService.findByEmail(username);
		return ResponseEntity.ok(currentUser);
	}

	@GetMapping("/")
	public ResponseEntity<List<User>> allUsers() {
		List<User> users = userService.allUsers();
		return ResponseEntity.ok(users);
	}
}