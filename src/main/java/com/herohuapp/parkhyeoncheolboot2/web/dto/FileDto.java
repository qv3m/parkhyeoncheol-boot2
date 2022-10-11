package com.herohuapp.parkhyeoncheolboot2.web.dto;

import java.time.LocalDateTime;

import com.herohuapp.parkhyeoncheolboot2.domain.posts.File;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileDto {
	private Long id;
	private String origFilename;
	private String filename;
	private String filePath;
	
	@Builder
	public FileDto(Long id, String origFilename, String filename, String filePath) {
		//super();
		this.id = id;
		this.origFilename = origFilename;
		this.filename = filename;
		this.filePath = filePath;
	}
	
	public File toEntity() {
		return File.builder()
				.id(id)
				.origFilename(origFilename)
				.filename(filename)
				.filePath(filePath)
				.build();
		
		
	}
	
}
