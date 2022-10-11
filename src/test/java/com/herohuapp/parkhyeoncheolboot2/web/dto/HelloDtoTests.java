package com.herohuapp.parkhyeoncheolboot2.web.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class HelloDtoTests {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext ctx;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
				.addFilter(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build();
	}

	@Test
	void helloDto() throws Exception {
		MvcResult rt = mockMvc.perform(
				get("/hello/dto")
				.param("name", "오렌지")
				.param("amount", "100")
				)
				.andExpect(status().isOk())
				.andReturn();	
		String json = rt.getResponse().getContentAsString();
		HelloDto helloDto = new ObjectMapper().readValue(json, HelloDto.class);
		assertThat(helloDto.getName()).isEqualTo("오렌지");
		assertThat(helloDto.getAmount()).isEqualTo(100);
	}

}
