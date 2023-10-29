package kr.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Board;
import kr.spring.service.BoardService;

@Controller
@RequestMapping("/board/*")// /board/* / 뒤에 오는 요청에 따라 여기로 오겠다.
public class BoardController {

	@Autowired
	private BoardService service;
	// BoardService 는 인터페이스이다.
	// 구현한것은 BoardServiceImpl
	// BoardService로 업캐스팅 된다.
	// BoardServiceImpl 외 다른 Impl 이 생성되었을 때 부모타입으로 받는것이 효율적이다.
	
	@PostMapping("/reply")
	public String reply(Board vo) { // 부모글 번호, 작성 ID, 제목, 답글, 작성자 이름
		service.reply(vo);
		return "redirect:/board/list";
		
	}
	
	@GetMapping("/reply")
	public String reply(@RequestParam("idx") int idx, Model model) {
		Board vo = service.get(idx);
		model.addAttribute("vo",vo);
		return "board/reply";
	}

	@GetMapping("/remove")
	public String remove(@RequestParam("idx") int idx) {
		service.remove(idx);
		return "redirect:/board/list";
	}
	
	@PostMapping("/modify")
	public String modify(Board vo) {
		service.modify(vo);
		return "redirect:/board/list";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam("idx") int idx, Model model) {
		Board vo = service.get(idx);
		model.addAttribute("vo",vo);
		return "board/modify";
	}
	
	@GetMapping
	public String get(@RequestParam("idx") int idx, Model model) {
		Board vo = service.get(idx);
		model.addAttribute("vo", vo);
		return "board/get";
	}
	
	@GetMapping("register")
	public String register() {
		return "board/register";
	}
	
	@PostMapping("register")
	public String register(Board vo, RedirectAttributes rttr) {
		//System.out.println(vo.toString());
		service.register(vo);
		//System.out.println(vo.toString());
		rttr.addFlashAttribute("result", vo.getIdx());
		return "redirect:/board/list";
	}
	
	
	@GetMapping("/list")
	public String boardList(Model model){
		
		List<Board> list = service.getList();
		model.addAttribute("list",list);
		return "board/list";
	}

}
