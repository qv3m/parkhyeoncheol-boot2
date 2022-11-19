package com.herohuapp.parkhyeoncheolboot2.config.auth;

import java.io.Serializable;

import com.herohuapp.parkhyeoncheolboot2.domain.users.Users;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	private String name;
	private String role;
	
	private String email;
	private String picture;
	
	
	public SessionUser(String name, String role) {
		
		this.name = name;
		this.role = role;
	}

	public SessionUser(Users user) {
		this.name = user.getName();
		this.role = user.getRoleKey();
		this.email = user.getEmail();
		this.picture = user.getPicture();
	}
	
	
}
