package com.herohuapp.parkhyeoncheolboot2.service.posts;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herohuapp.parkhyeoncheolboot2.domain.posts.File;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.FileRepository;
import com.herohuapp.parkhyeoncheolboot2.web.dto.FileDto;

@Service
public class FileService {
	private FileRepository fileRepository;

	public FileService(FileRepository fileRepository) {
		//super();
		this.fileRepository = fileRepository;
	}
	
	@Transactional
	public void deleteFile(Long id) {//파일테이블의 고유키값인 id를 매개변수로 받는다.
		fileRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 파일이 없습니다."));
		fileRepository.deleteById(id);//삭제는 반환값이 없음=void
	}
	@Transactional
	public Long saveFile(FileDto fileDto) {
		return fileRepository.save(fileDto.toEntity()).getId();//저장 후 id고유값을 반환
	}
	@Transactional
	public FileDto getFile(Long id) {
		File file = fileRepository.findById(id).get();//DB의 파일 테이블에서 가져온 객체를 get()메소드로 반환한다.
		return FileDto.builder()
				.id(id)
				.origFilename(file.getOrigFilename())
				.filename(file.getFilename())
				.filePath(file.getFilePath())
				.build();
	}
}
