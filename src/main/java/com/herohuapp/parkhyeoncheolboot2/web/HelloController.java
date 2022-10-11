package com.herohuapp.parkhyeoncheolboot2.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.herohuapp.parkhyeoncheolboot2.web.dto.HelloDto;

@RestController
public class HelloController {
	
	@GetMapping("/hello/dto")
	public HelloDto helloDto(@RequestParam("name")String name, @RequestParam("amount")int amount) {
		return new HelloDto(name, amount);
		
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello";
		
	}
}
