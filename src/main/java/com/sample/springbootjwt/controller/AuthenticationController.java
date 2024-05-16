package com.sample.springbootjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springbootjwt.config.JwtTokenUtil;
import com.sample.springbootjwt.dto.LoginUserDto;
import com.sample.springbootjwt.dto.RegisterUserDto;
import com.sample.springbootjwt.entity.User;
import com.sample.springbootjwt.response.LoginResponse;
import com.sample.springbootjwt.service.JwtUserDetailsService;
import com.sample.springbootjwt.service.UserService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
		User registeredUser = userService.signup(registerUserDto);
		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));

		if (authentication.isAuthenticated()) {
			final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginUserDto.getEmail());
			String jwtToken = jwtTokenUtil.generateToken(userDetails);
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setToken(jwtToken);
			loginResponse.setExpiresIn(jwtTokenUtil.getExpirationTime());

			return ResponseEntity.ok(loginResponse);
		} else {
			throw new BadCredentialsException("invalid login details");
		}

	}
}