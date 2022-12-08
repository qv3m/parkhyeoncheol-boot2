package com.herohuapp.parkhyeoncheolboot2.web.dto;

import java.time.LocalDateTime;

import com.herohuapp.parkhyeoncheolboot2.domain.posts.File;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.ManyFile;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.Posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ManyFileDto {
	private Long id;
	private String origFilename;
	private String filename;
	private String filePath;
	private Posts posts;
	
	@Builder
	public ManyFileDto(Long id, String origFilename, String filename, String filePath, Posts posts) {
		//super();
		this.id = id;
		this.origFilename = origFilename;
		this.filename = filename;
		this.filePath = filePath;
		this.posts = posts;
	}
	
	public ManyFile toEntity() {
		return ManyFile.builder()
				.id(id)
				.origFilename(origFilename)
				.filename(filename)
				.filePath(filePath)
				.posts(posts)
				.build();

	}
	
}
