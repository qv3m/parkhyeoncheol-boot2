package com.herohuapp.parkhyeoncheolboot2.service.posts;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herohuapp.parkhyeoncheolboot2.domain.posts.File;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.FileRepository;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.ManyFile;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.ManyFileRepository;
import com.herohuapp.parkhyeoncheolboot2.web.dto.FileDto;
import com.herohuapp.parkhyeoncheolboot2.web.dto.ManyFileDto;

@Service
public class ManyFileService {
	private ManyFileRepository fileRepository;

	public ManyFileService(ManyFileRepository fileRepository) {
		//super();
		this.fileRepository = fileRepository;
	}
	
	@Transactional
	public void deleteFile(Long id) {//파일테이블의 고유키값인 id를 매개변수로 받는다.
		fileRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 파일이 없습니다."));
		fileRepository.deleteById(id);//삭제는 반환값이 없음=void
	}
	@Transactional
	public Long saveFile(ManyFileDto fileDto) {
		return fileRepository.save(fileDto.toEntity()).getId();//저장 후 id고유값을 반환
	}
	@Transactional
	public ManyFileDto getFile(Long id) {
		System.out.println("여기까지1" + id);
		ManyFile file = fileRepository.findById(id).get();//DB의 파일 테이블에서 가져온 객체를 get()메소드로 반환한다.
		return ManyFileDto.builder()
				.id(id)
				.origFilename(file.getOrigFilename())
				.filename(file.getFilename())
				.filePath(file.getFilePath())
				.posts(file.getPosts())
				.build();
	}
	
	@Transactional
	public List<ManyFile> getManyFile(Long posts_id) {
		return fileRepository.fileAllDesc(posts_id);
	}
}
