package kr.spring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		return "main";
	}
	
	@RequestMapping("/boardList.do")
	public @ResponseBody List<Board> boardLists(){
		List<Board> list = mapper.getLists();
		return list;
	}
	
	@RequestMapping("boardInsert.do")
	public @ResponseBody void boardInsert(Board board) {
		mapper.boardInsert(board);
	}
	
	@RequestMapping("boardDelete.do")
	public @ResponseBody void boardDelete(@RequestParam("idx") int idx) {
		System.out.println("게시글 삭제 기능 수행");
		mapper.boardDelete(idx);
	}
}

