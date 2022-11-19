package com.herohuapp.parkhyeoncheolboot2.web.dto;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.herohuapp.parkhyeoncheolboot2.domain.simple_users.SimpleUsers;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimpleUsersDto {
	private Long id;
	private String username;
	private String password;
	private String role;
	private Boolean enabled;
	private LocalDateTime modifiedDate;
	
	public SimpleUsersDto(SimpleUsers entity) {
		this.id = entity.getId();
		this.username = entity.getUsername();
		this.password = entity.getPassword();
		this.role = entity.getRole();
		this.enabled = entity.getEnabled();
		this.modifiedDate = entity.getModifieDate();
		
	}
	@Builder
	public SimpleUsersDto(String username, String password, String role, Boolean enabled) {
		
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}
	
	public SimpleUsers toEntity() {
		String encPassword = null;
	
		if(!password.isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			encPassword = passwordEncoder.encode(password);
			this.password = encPassword;
		}
		
		return SimpleUsers.builder()
				.username(username)
				.password(encPassword)
				.role(role)
				.enabled(enabled)
				.build();
				
	}
	
}
