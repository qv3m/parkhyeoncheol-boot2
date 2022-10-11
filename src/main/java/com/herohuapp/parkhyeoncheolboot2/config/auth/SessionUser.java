package com.herohuapp.parkhyeoncheolboot2.config.auth;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	private String name;
	private String role;
	
	public SessionUser(String name, String role) {
		
		this.name = name;
		this.role = role;
	}
	
	
}
