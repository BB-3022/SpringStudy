package kr.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.entity.Board;
import kr.spring.mapper.BoardMapper;

//현재클래스를 핸들러맵핑이 찾기위해 컨트롤럴로 등록하는 부분- 핸들러 매핑이 컨트롤러를 찾기 위해 스프링에 컨트롤러를 등록한다.
@Controller
public class BoardController {
	
	@Autowired
	private BoardMapper mapper; // Mybatis 한테 JDBC를 실행하게 요청하는 객체
	
	@RequestMapping("/") //요청 url로 들어왔을 때 아래 기능을 수행하겠다.
	//컨트롤러가 View Name 을 돌려준다.
	public String home() {
		System.out.println("홈 기능 수행");
		// "/"로 url 요청이 들어오면, view name을 돌려주는 것이 아니라
		// boardList.do라는 url을 돌려준다.  --> redirect
		return "redirect:/boardList.do";
	}
	
	@RequestMapping("/boardList.do")
	public String boardList(Model model) {
		System.out.println("게시판목록보기 기능 수행");
		
		// 게시글 정보 가져오기
		// 한개의 게시글은 - 번호, 제목, 내용, 작성자, 작성일, 조회수
		
		// 전체 게시글 조회기능
		List<Board> list = mapper.getLists();
		
		// list의 값을 boardList로 이동시켜야 한다.
		// model 영역에 저장한다.
		// model은 request 영역안에 있다.
		// 객체바인딩 - 동적바인딩
		model.addAttribute("list",list);
		
		// boardList.jsp View name을 돌려준다.
		return "boardList"; // WEB-INF/views/boardList.jsp -> forward방식
	}
	
	@RequestMapping("/boardForm.do")
	public String boardForm() {
		System.out.println("글쓰기페이지 이동");
		return "boardForm";
	}
	
	@RequestMapping("/boardInsert.do")
	public String boardInsert(Board board) {
		System.out.println("게시글 등록 기능수행");
		
		mapper.boardInsert(board);
		
		return "redirect:/boardList.do";
	}
	
	@RequestMapping("/boardContent.do/{idx}")
	public String boardContent(@PathVariable("idx") int idx, Model model) {
		System.out.println("게시글 상세보기 기능수행");
		// 게시글 조회수 증가
		mapper.boardCount(idx);
		
		Board vo = mapper.boardContent(idx);
		
		model.addAttribute("vo", vo);
		
		return "boardContent";
	}
	
	@RequestMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable("idx") int idx) {
		System.out.println("게시글 삭제 기능 수행");
		mapper.boardDelete(idx);	
		return "redirect:/boardList.do";
	}
	
	@RequestMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable("idx") int idx, Model model) {
		System.out.println("게시글 수정화면이동 기능수행");
		
		Board vo = mapper.boardContent(idx);
		
		model.addAttribute("vo", vo);
		
		return "boardUpdateForm";
	}
	
	@RequestMapping("/boardUpdate.do")
	public String boardUpdate(Board vo) {
		
		mapper.boardUpdate(vo);
		
		return "redirect:/boardList.do";
	}
	
}
