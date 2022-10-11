package com.herohuapp.parkhyeoncheolboot2.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.herohuapp.parkhyeoncheolboot2.config.auth.LoginUserArgumentResolver;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final LoginUserArgumentResolver loginUserArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add((HandlerMethodArgumentResolver) loginUserArgumentResolver);
	}
	
	
	
	
}
