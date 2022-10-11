package com.herohuapp.parkhyeoncheolboot2.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.herohuapp.parkhyeoncheolboot2.domain.posts.Posts;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.PostsRepository;
import com.herohuapp.parkhyeoncheolboot2.web.dto.PostsDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final PostsRepository postsRepository;
	
	@Transactional
	public Long save(PostsDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
		
	}
	
	@Transactional
	public PostsDto postsOne(Long id) {
		Posts  entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException(id + "번호의 게시글이 없습니다."));
		return new PostsDto(entity);
	}
	
	@Transactional
	public Long update(Long id, PostsDto requestDto) {
		Posts  post = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException(id + "번호의 게시글이 없습니다."));
		post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getFileId());
		return id;
	}
	
	@Transactional
	public void delete(Long id) {
		Posts  entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException(id + "번호의 게시글이 없습니다."));
		postsRepository.delete(entity);	
	}

	public Page<Posts> postsList(Pageable pageable) {
		Page<Posts> postsList = postsRepository.findAll(pageable);
		return postsList;
	}
}
