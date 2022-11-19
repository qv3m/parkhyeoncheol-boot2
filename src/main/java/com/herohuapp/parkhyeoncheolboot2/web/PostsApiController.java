package com.herohuapp.parkhyeoncheolboot2.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.herohuapp.parkhyeoncheolboot2.service.posts.PostsService;
import com.herohuapp.parkhyeoncheolboot2.web.dto.PostsDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final PostsService postsService;
	
	//포스트매핑은 페이지 폼에서만 접근가능(보안)
		@PostMapping("/api/posts/save")//저장:Create
		public Long save(@RequestBody PostsDto requestDto) {
			return postsService.save(requestDto);
		}
		//겟매핑은 페이지 URL에서만 접근가능(비보안)
		@GetMapping("/api/posts/{id}")//읽기:1개게시물 Read
		public PostsDto postOne(@PathVariable Long id) {
			return postsService.postsOne(id);
		}
		
		//전체게시물 읽기는 @RestController가 아닌 일반 @Controller에서 디자인 html 뷰파일과 함께 처리할 예정이다.
		
		//풋매핑은 페이지 폼에서만 접근가능(보안)
		@PutMapping("/api/posts/{id}")//수정:Update
		public Long update(@PathVariable Long id, @RequestBody PostsDto requestDto) {
			return postsService.update(id, requestDto);
		}
		@DeleteMapping("/api/posts/{id}")
		public Long delete(@PathVariable Long id) {
			postsService.delete(id);
			return id;
		}
	}
	
