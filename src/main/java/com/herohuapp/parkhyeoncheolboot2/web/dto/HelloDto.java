package com.herohuapp.parkhyeoncheolboot2.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloDto {
	
	private final String name;
	private final int amount;
	
	public HelloDto() {
		this.name = "";
		this.amount = 0;
		
	}
	
}
