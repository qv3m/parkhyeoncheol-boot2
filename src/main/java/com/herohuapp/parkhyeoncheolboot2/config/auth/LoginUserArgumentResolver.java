package com.herohuapp.parkhyeoncheolboot2.config.auth;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
	private final HttpSession httpSession;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
		return isUserClass;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//인증객체생성
		String userName = authentication.getName();//admin, user, guest 중 1개가 들어간다.
		Role userAuthor = null;
		if(httpSession.getAttribute("user")==null && !"anonymousUser".equals(userName)) {//초기인증값이 없을 때 anonymousUser 값을 갖는다
			Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();//권한객체생성
			
			if(roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				userAuthor = Role.ADMIN;
			} else if(roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
				userAuthor = Role.USER;
			} else {
				userAuthor = Role.GUEST;
			}
			// 세션 값 저장 핵심(아래)
			httpSession.setAttribute("user", new SessionUser(userName,userAuthor.getKey()));
		}
		//세션값을 가져와서 return한다.
		return httpSession.getAttribute("user");
	}


}
