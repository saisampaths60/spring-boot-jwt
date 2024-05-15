package com.sample.springbootjwt.dto;

import lombok.Data;

@Data
public class LoginUserDto {
	private String email;
	private String password;
}
