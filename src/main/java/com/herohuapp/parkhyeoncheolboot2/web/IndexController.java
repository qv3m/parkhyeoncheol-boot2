package com.herohuapp.parkhyeoncheolboot2.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;

import com.herohuapp.parkhyeoncheolboot2.config.auth.LoginUser;
import com.herohuapp.parkhyeoncheolboot2.config.auth.SessionUser;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.ManyFile;
import com.herohuapp.parkhyeoncheolboot2.domain.posts.Posts;
import com.herohuapp.parkhyeoncheolboot2.service.posts.FileService;
import com.herohuapp.parkhyeoncheolboot2.service.posts.ManyFileService;
import com.herohuapp.parkhyeoncheolboot2.service.posts.PostsService;
import com.herohuapp.parkhyeoncheolboot2.service.simple_users.SimpleUsersService;
import com.herohuapp.parkhyeoncheolboot2.util.ScriptUtils;
import com.herohuapp.parkhyeoncheolboot2.web.dto.FileDto;
import com.herohuapp.parkhyeoncheolboot2.web.dto.PostsDto;
import com.herohuapp.parkhyeoncheolboot2.web.dto.SimpleUsersDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	//로그 출력 객체생성
		private Logger logger = LoggerFactory.getLogger(getClass());
		private final PostsService postsService;//생성자로 주입
		private final FileService fileService;
		private final ManyFileService manyFileService;
		private final SimpleUsersService simpleUsersService;
		
		@PostMapping("/mypage/delete")//회원삭제 API실행
		public String mypageDelete(HttpServletResponse response, SimpleUsersDto requestDto) throws IOException {
			simpleUsersService.delete(requestDto.getId());
			// "redirect:/simple_users/list";//삭제 후 절대경로로 페이지 이동
			ScriptUtils.alertAndMovePage(response, "회원탈퇴 되었습니다.", "/logout");
			return null;
		}
		
		@GetMapping("/mypage/update/{username}")//회원상세 디자인보기
		public String mypageUpdate(HttpServletResponse response, @PathVariable String username,Model model,@LoginUser SessionUser user) throws IOException {
			if(!username.equals(user.getName())) {
				ScriptUtils.alertAndBackPage(response, "본인 아이디만 수정가능합니다!");
				return null;
			}
			model.addAttribute("simple_user", simpleUsersService.findByName(username));
			return "mypage/update";
		}
		
		@PostMapping("/mypage/update")//회원수정 API실행
		public String mypageUpdatePost(HttpServletResponse response, SimpleUsersDto requestDto) throws IOException {
			simpleUsersService.update(requestDto.getId(), requestDto);
			//return "redirect:/simple_users/update/"+requestDto.getUsername();
			ScriptUtils.alertAndMovePage(response, "수정이 완료되었습니다.", "/mypage/update/"+requestDto.getUsername());
			return null;
		}
		
		@GetMapping("/signup")
		public String signupGet(Model model) {
			
			return "signup";
			
		}
		

		@PostMapping("/signup")//회원생성 API실행
		public String signupPost(HttpServletResponse response, SimpleUsersDto requestDto) throws IOException {
			//return "redirect:/simple_users/list";//저장 후 절대경로로 페이지이동
			SimpleUsersDto usersDto = null;
			try {
				usersDto = simpleUsersService.findByName(requestDto.getUsername());
				
			}catch(Exception e) {
				
			}
			if(usersDto == null) {
				requestDto.setRole("USER");
				simpleUsersService.save(requestDto);
				ScriptUtils.alertAndMovePage(response, "회원가입이 완료되었습니다. 로그인해주세요", "/");
				
			}else {
				ScriptUtils.alertAndBackPage(response, "중복아이디가 존재합니다. 아이디를 다시 입력해 주세요.");
			}
			return null;
		}
		
		@GetMapping("/kakaomap")
		public String kakaoMap(@RequestParam(value="keyword", defaultValue="천안시")String keyword, Model model) throws IOException {
				StringBuilder urlBuilder = new StringBuilder("http://openapi.kepco.co.kr/service/EvInfoServiceV2/getEvSearchList"); /*URL*/
		        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=PLJPmKeBFGOkoxgAoLJgT962Uh0QPWijxPNQ%2Bl%2B4o24r9R%2BqbclT0Fc9xSamDrGiMYAF4CrpJLaDOsKZ%2FDoN%2Bw%3D%3D"); /*Service Key*/
		        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
		        urlBuilder.append("&" + URLEncoder.encode("addr","UTF-8") + "=" + URLEncoder.encode(keyword,"UTF-8")); /*검색대상 충전소주소*/
		        URL url = new URL(urlBuilder.toString());
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("GET");
		        conn.setRequestProperty("Content-type", "application/json");
		        System.out.println("Response code: " + conn.getResponseCode());
		        BufferedReader rd;
		        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
		            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        } else {
		            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		        }
		        StringBuilder sb = new StringBuilder();
		        String line;
		        while ((line = rd.readLine()) != null) {
		            sb.append(line);
		        }
		        rd.close();
		        conn.disconnect();
		        
		        //System.out.println(sb.toString());
		        //logger.info("XML결과:\n" + sb.toString());
		        JSONObject jsonObject = XML.toJSONObject(sb.toString());
		        //System.out.println(jsonObject.toString());//xml to json 전체 데이터
		        /* 의령군 전기차 충전소 일때
		        JSONObject rfcOpenApi = (JSONObject) (jsonObject.get("rfcOpenApi"));
		        JSONObject header = (JSONObject) rfcOpenApi.get("header");
		        JSONObject body = (JSONObject) rfcOpenApi.get("body");
		        */
		        JSONObject response = (JSONObject) (jsonObject.get("response"));
		        JSONObject header = (JSONObject) response.get("header");
		        JSONObject body = (JSONObject) response.get("body");
		        //System.out.println(header.toString());//헤더 정보 확인
		        System.out.println(body.toString());//실제 데이터 정보 확인
			model.addAttribute("response", body);
			model.addAttribute("keyword", keyword);
			return "kakaomap";	
		}
		@GetMapping("/posts/update/{id}")
		public String postsUpdate(HttpServletResponse response, @PathVariable Long id, Model model, @LoginUser SessionUser user) throws IOException {
			if(user != null) {
				model.addAttribute("sessionUserName", user.getName());
				model.addAttribute("sessionRoleName", "ROLE_ADMIN".equals(user.getRole())?"admin":null);
			}
			PostsDto dto = postsService.postsOne(id);
			model.addAttribute("post", dto);
			if(dto.getFileId() != null) {
				FileDto fileDto = fileService.getFile(dto.getFileId());
				model.addAttribute("OrigFilename", fileDto.getOrigFilename());
				return null;
			}
			
			if(!user.getName().equals(dto.getAuthor()) && !"ROLE_ADMIN".equals(user.getRole())) {
				ScriptUtils.alertAndBackPage(response, "본인글만 수정가능합니다.");
			}
			
			//멀티파일 조회처리
			List<ManyFile> manyFileList = manyFileService.getManyFile(id);
			if(manyFileList.size()>0) {//객체의 레코드 개수를 구할 때 size() 메소드를 사용한다.
				model.addAttribute("manyFileList", manyFileList);
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
			//멀티파일 조회처리
			List<ManyFile> manyFileList = manyFileService.getManyFile(id);
			if(manyFileList.size()>0) {//객체의 레코드 개수를 구할 때 size() 메소드를 사용한다.
				model.addAttribute("manyFileList", manyFileList);
			}
			return "posts/posts-read";
		}
		@GetMapping("/posts/save")
		public String postsSave(Model model, @LoginUser SessionUser user) {
			model.addAttribute("sessionUserName", user.getName());
			return "posts/posts-save";
			
		}
		@GetMapping("/")//전체게시물 Read
		public String postList(@PageableDefault(size=5,sort="id",direction=Sort.Direction.DESC) 
		Pageable pageable, Model model,  @LoginUser SessionUser user) {
				if(user != null) {
					model.addAttribute("sessionUserName", user.getName());
					model.addAttribute("sessionRoleName", "ROLE_ADMIN".equals(user.getRole())?"admin":null);
					try {
						SimpleUsersDto usersDto = simpleUsersService.findByName(user.getName());	
						model.addAttribute("memberTrue", usersDto);
					}catch(Exception e) {
						model.addAttribute("memberTrue", null);
					}
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
