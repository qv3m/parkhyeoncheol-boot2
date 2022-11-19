package com.herohuapp.parkhyeoncheolboot2.config.auth.dto;

import java.util.Map;

import com.herohuapp.parkhyeoncheolboot2.config.auth.Role;
import com.herohuapp.parkhyeoncheolboot2.domain.users.Users;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email,
			String picture) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
	}

	// 여러 외부 로그인 인증을 위한 분기용 of()메소드 생성
	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
			Map<String, Object> attributes) {
		if ("naver".equals(registrationId)) {
			// 아래 줄은 스프링부트OAuth2에서 생성된다.(구글실제 id값은 "sub")
			userNameAttributeName = "id";// 네이버API는 수동으로 입력.
			return ofNaver(userNameAttributeName, attributes);
		}
		return null;
	}

	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");
		return OAuthAttributes.builder().name((String) response.get("name")).email((String) response.get("email"))
				.picture((String) response.get("profile_image")).attributes(response)
				.nameAttributeKey(userNameAttributeName).build();
	}

	// Users 엔티티(DB)에 저장
	public Users toEntity() {
		return Users.builder().name(name).email(email).picture(picture).role(Role.USER)// 네이버 API 로그인한 회원은 무조건
																						// ROLE_USER권한으로 등록한다.
				.build();
	}
}
