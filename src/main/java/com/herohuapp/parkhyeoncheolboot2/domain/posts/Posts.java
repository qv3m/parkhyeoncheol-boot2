package com.herohuapp.parkhyeoncheolboot2.domain.posts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.herohuapp.parkhyeoncheolboot2.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter//엔티티 출력이 가능하게 구현된다
@NoArgsConstructor//엔티티 생성자를 자동으로 추가한다
@Entity//엔티티와 매핑되는 저장소를 만든다
public class Posts extends BaseTimeEntity{
	@Id//주키 Primary Key 로 만든다
	@GeneratedValue(strategy=GenerationType.IDENTITY)//자동증가값으로 구현한다 
	private long id;
	
	@Column(length=500, nullable=false)//기본길이 255글자
	private String title;//글 제목
	
	@Column(columnDefinition="TEXT", nullable=false)
    private String content;//글 내용
	
    private String author;//글 작성자 아이디
    
    private Long fileId;//첨부파일 번호
    
    @Builder//조립가능한 형식으로 메소드 사용가능하게 만든다.
	public Posts(String title, String content, String author, Long fileId) {
		//super();//BaseTimeEntity 생성자 메소드가 없는 추상클래스 이기때문에 주석처리
		this.title = title;
		this.content = content;
		this.author = author;
		this.fileId = fileId;
	}
    public void update(String title, String content, Long fileId) {
		this.title = title;
		this.content = content;
		this.fileId = fileId;
	}
    //테스트용 출력 오버라이드 메소드 추가(아래)
	@Override
	public String toString() {
		return "Posts [id=" + id + ", title=" + title + ", content=" + content + ", author=" + author + ", fileId="
				+ fileId + "]";
	}
}