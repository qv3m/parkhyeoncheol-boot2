package com.herohuapp.parkhyeoncheolboot2.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.herohuapp.parkhyeoncheolboot2.config.auth.Role;
import com.herohuapp.parkhyeoncheolboot2.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {
	@Id//주키 Primary Key 로 만든다
	@GeneratedValue(strategy=GenerationType.IDENTITY)//자동증가값으로 구현한다
	private Long id;
	@Column(nullable=false)
	private String name;//네이버에서 반환받는 name 변수값
	@Column(nullable=false)
	private String email;//네이버에서 반환받는 email 변수값
	@Column
	private String picture;//네이버에서 반환받는 profile_image 변수값
	@Enumerated(EnumType.STRING)//role에 들어갈 자료의 형태는 String이 아니고 나열형이다.
	@Column(nullable=false)
	private Role role;//스프링시큐리티 로직을 거쳐야 하기 때문에 필요
	
	@Builder//Dto클래스에서 사용, 입력 기능으로 다른 클래스에서 build() 메소드 형식으로 사용가능
	public Users(String name, String email, String picture, Role role) {
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.role = role;
	}
	//엔티티에서 업데이트쿼리대신 메소드로 처리
	public Users update(String name, String picture) {
		this.name = name;
		this.picture = picture;
		return Users.this;
	}
	public String getRoleKey() {//나열형 이라서 일반 GET메소드로는 부족하다.
		return this.role.getKey();//ADMIN, USER, ...반환
	}
}
