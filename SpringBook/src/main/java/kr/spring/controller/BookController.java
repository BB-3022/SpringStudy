package kr.spring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.entity.Book;
import kr.spring.mapper.BookMapper;

//현재클래스를 핸들러맵핑이 찾기위해 컨트롤럴로 등록하는 부분- 핸들러 매핑이 컨트롤러를 찾기 위해 스프링에 컨트롤러를 등록한다.
@Controller
public class BookController {
	
	@Autowired
	private BookMapper mapper; // Mybatis 한테 JDBC를 실행하게 요청하는 객체
	
	@RequestMapping("/") //요청 url로 들어왔을 때 아래 기능을 수행하겠다.
	//컨트롤러가 View Name 을 돌려준다.
	public String home() {
		System.out.println("홈 기능 수행");
		// "/"로 url 요청이 들어오면, view name을 돌려주는 것이 아니라
		// bookList.do라는 url을 돌려준다.  --> redirect
		return "main";
	}
	
	@RequestMapping("/bookList.do")
	public @ResponseBody List<Book> bookLists(){
		List<Book> list = mapper.getLists();
		return list;
	}
	
	@PostMapping("/new")
	public String bookInsert(Book book) {
	    // book 객체를 데이터베이스에 저장하는 코드
	    mapper.bookInsert(book);
	    // 저장 후 도서 목록 페이지로 리다이렉트
	    return "redirect:/bookList.do";
	}
	
	
	
	
}

