package com.herohuapp.parkhyeoncheolboot2.domain.posts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostsRepositoryTests {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PostsRepository postsRepository;
	
	
	
	@AfterEach
	void tearDown() throws Exception {
		postsRepository.deleteAll();
	}

	@Test
	void test() {
		postsRepository.save(
				Posts.builder()
				.title("게시물제목")
				.content("게시글내용")
				.author("user")
				.build()
				);
		List<Posts> postsList = postsRepository.findAll();
		Posts posts = postsList.get(0);
		posts.toString();
		logger.debug("등록된 레코드 수" + postsRepository.count());
		logger.info("디버그" + posts.toString());
	}

}
