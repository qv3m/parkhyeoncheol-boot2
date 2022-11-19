package com.herohuapp.parkhyeoncheolboot2.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import com.herohuapp.parkhyeoncheolboot2.config.auth.LoginUser;
import com.herohuapp.parkhyeoncheolboot2.config.auth.SessionUser;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.Posts;
import com.herohuapp.parkhyeoncheolboot2.service.posts.FileService;
import com.herohuapp.parkhyeoncheolboot2.service.posts.PostsService;
import com.herohuapp.parkhyeoncheolboot2.web.dto.FileDto;
import com.herohuapp.parkhyeoncheolboot2.web.dto.PostsDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	//로그 출력 객체생성
		private Logger logger = LoggerFactory.getLogger(getClass());
		private final PostsService postsService;//생성자로 주입
		private final FileService fileService;
		
		@GetMapping("/posts/update/{id}")
		public String postsUpdate(@PathVariable Long id, Model model) {
			PostsDto dto = postsService.postsOne(id);
			model.addAttribute("post", dto);
			if(dto.getFileId() != null) {
				FileDto fileDto = fileService.getFile(dto.getFileId());
				model.addAttribute("OrigFilename", fileDto.getOrigFilename());
			}
			return "posts/posts-update";
		}
		@GetMapping("/posts/read/{id}")
		public String postsRead(@PathVariable Long id, Model model) {
			PostsDto dto = postsService.postsOne(id);
			model.addAttribute("post", dto);
			if(dto.getFileId() != null) {
				FileDto fileDto = fileService.getFile(dto.getFileId());
				model.addAttribute("OrigFilename", fileDto.getOrigFilename());
			}
			return "posts/posts-read";
		}
		@GetMapping("/posts/save")
		public String postsSave() {
			return "posts/posts-save";
			
		}
		@GetMapping("/")//전체게시물 Read
		public String postList(@PageableDefault(size=5,sort="id",direction=Sort.Direction.DESC) 
		Pageable pageable, Model model,  @LoginUser SessionUser user) {
				if(user != null) {
					model.addAttribute("sessionUserName", user.getName());
					model.addAttribute("sessionRoleName", "ROLE_ADMIN".equals(user.getRole())?"admin":null);
						
				}
					
				
			Page<Posts> postsList = postsService.postsList(pageable);
			model.addAttribute("postsList", postsList);//게시글목록 5개
			model.addAttribute("currPage", postsList.getPageable().getPageNumber());//현재페이지번호
			model.addAttribute("pageIndex", postsList.getTotalPages());//전체페이지개수
			model.addAttribute("prevCheck", postsList.hasPrevious());//이전페이지 있는지 체크
			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());//이전페이지번호 사용
			model.addAttribute("nextCheck", postsList.hasNext());//다음페이지 있는지 체크
			model.addAttribute("next", pageable.next().getPageNumber());//다음페이지번호 사용
			return "index";//출력할 페이지명 posts폴더/post-list.mustache파일(html디자인템플릿)
		}
}
