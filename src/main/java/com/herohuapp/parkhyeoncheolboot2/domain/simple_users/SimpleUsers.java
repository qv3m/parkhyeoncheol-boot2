package com.herohuapp.parkhyeoncheolboot2.domain.simple_users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.herohuapp.parkhyeoncheolboot2.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(
		name="SimpleUsers",
		uniqueConstraints = {
				@UniqueConstraint(columnNames= {"username"})}
		)
public class SimpleUsers extends BaseTimeEntity {
	@Id//주키 Primary Key 로 만든다
	@GeneratedValue(strategy=GenerationType.IDENTITY)//자동증가값으로 구현한다 
	private Long id;
	@Column(nullable=false)
	private String username;
	@Column(nullable=false)
	private String password;
	@Column(nullable=false)
	private String role;
	@Column(nullable=false)
	private Boolean enabled;
	
	@Builder
	public SimpleUsers(String username, String password, String role, Boolean enabled) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}
	
	public void update (String username, String password, String role, Boolean enabled) {
		this.username = username;
		String encPassword =null;
		if(!password.isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			encPassword = passwordEncoder.encode(password);
			this.password = encPassword;	
		}
		this.role = role;
		this.enabled = enabled;
	}
}
