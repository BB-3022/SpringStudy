package kr.spring.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.spring.entity.Member;
import kr.spring.mapper.MemberMapper;

@Controller
public class MemberController {

	@Autowired
	private MemberMapper mapper;
	
	@RequestMapping
	public String joinForm() {
		// View Resolver가 WEB-INF/views 의 경로를 생성해 준다.??
		return "member/joinForm";
	}
	
	// 비동기 방식, 일반 컨트롤러
	// @ResponseBody
	// 일치하는 아이디가 있으면 해당정보를 모두 받아온다.(0, 1 반환 기능 없음)
	@RequestMapping("/registerCheck.do")
	public @ResponseBody int registerCheck(@RequestParam("memID") String memID) {
		Member m =  mapper.registerCheck(memID);
		// m == null -> 아이디 사용가능
		// m != null -> 아이디 사용 불가능
		if(m != null || memID.equals("")) {
			return 0;
		}else {
			return 1;
		}
	}
	@RequestMapping("/join.do")
	public String join(Member m, RedirectAttributes rttr, HttpSession session) {
		System.out.println("회원가입 기능요청");
		
		//프론트 & 백앤드 유효성 검사 필요
		if(m.getMemID() == null || m.getMemID().equals("") || 
				m.getMemPassword() == null || m.getMemPassword().equals("") ||
				m.getMemName() == null || m.getMemName().equals("") ||
				m.getMemAge() == 0 ||
				m.getMemEmail() == null || m.getMemEmail().equals("")
				) {
			// 회원가입을 할 수 없다. 하나라도 누락되어 있기 때문에
			
			// 실패시 joinForm.do로 msgType 과 msg 내용을 보내야 한다.
			// msgType : 실패메세지, msg:모든 내용을 입력하세요
			// RedirectAttributes - 리다이렉트 방식으로 이동할때 보낼 데이터를 저장하는 객체
			rttr.addFlashAttribute("msgType","실패메세지");
			rttr.addFlashAttribute("msg","모든 내용을 입력하세요.");
			
			// 다시 회원가입 페이지로 이동
			return "redirect:/joinForm.do";
			
		}else {
			// 회원가입을 시도할 수 있는 부분
			
			m.setMemProfile("");
			
			int cnt = mapper.join(m);
			
			if(cnt==1) {
				System.out.println("회원가입 성공!");
				rttr.addFlashAttribute("msgType","성공메세지");
				rttr.addFlashAttribute("msg","회원가입에 성공했습니다.");
				// 회원가입 성공 시 로그인 처리까지 시키기
				session.setAttribute("mvo", m);
				
				return "redirect:/";
			}else {
				System.out.println("회원가입 실패..");
				rttr.addFlashAttribute("msgType","실패메세지");
				rttr.addFlashAttribute("msg","회원가입에 실패했습니다.");
				return "rederect:/joinForm.do";
			}
		}
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/loginForm.do")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@RequestMapping("/login.do")
	public String login(Member m, HttpSession session, RedirectAttributes rttr) {
		// mapper에 login 이라는 메소드 이름으로 로그인 기능을 수행하시오.
		// 로그인 성공 시, index/jsp 로 이동 후 로그인에 성공했습니다. modal창 띄우기
		// 로그인 실패 시, login/jps 로 이동 후 로그인에 실패했습니다. modal창 띄우기
		Member mvo = mapper.login(m);
		
		if(mvo != null) {
			System.out.println("로그인 성공");
			
			rttr.addFlashAttribute("msgType","성공메세지");
			rttr.addFlashAttribute("msg","로그인에 성공했습니다.");
			// 헤더 jsp 에서 mvo로 정보를 가져오고 있으므로
			// mvo 라고 명칭해야 한다.
			session.setAttribute("mvo", mvo);
			
			return "redirect:/";
			
		}else {
			
			System.out.println("로그인 실패");
			
			rttr.addFlashAttribute("msgType","실패메세지");
			rttr.addFlashAttribute("msg","로그인에 실패했습니다.");
			
			return "redirect:/loginForm.do";
		}
	}
	
	@RequestMapping("/updateForm.do")
	public String updateForm() {
		return "member/updateForm";
	}
	
	@RequestMapping("/update.do")
	public String update(Member m, RedirectAttributes rttr, HttpSession session) {
				
		// 누락된 부분이 있을 경우, updateFrom.jsp 로 이동
		if(m.getMemID() == null || m.getMemID().equals("") || 
				m.getMemPassword() == null || m.getMemPassword().equals("") ||
				m.getMemName() == null || m.getMemName().equals("") ||
				m.getMemAge() == 0 ||
				m.getMemEmail() == null || m.getMemEmail().equals("")
				) {
			
			rttr.addFlashAttribute("msgType","실패메세지");
			rttr.addFlashAttribute("msg","모든 내용을 입력하세요.");
			
			return "redirect:/updateForm.do";
			
		}else {
			
			// 회원정보수정을 시도할 수 있는 부분
			// 업데이트를 실행 하면 세션에 있는 기존 프로필 정보를 가져온다.
			Member mvo = (Member)session.getAttribute("mvo");
			m.setMemProfile(mvo.getMemProfile());
			
			int cnt = mapper.update(m);
			
			if(cnt == 1) {
				
				rttr.addFlashAttribute("msgType","성공메세지");
				rttr.addFlashAttribute("msg","회원수정에 성공했습니다.");
				
				session.setAttribute("mvo", m);
				
				return "redirect:/";
			}else {
				rttr.addFlashAttribute("msgType","실패메세지");
				rttr.addFlashAttribute("msg","회원수정에 실패했습니다.");
				return "rederect:/updateForm.do";
			}
		}
	}
	
	@RequestMapping("/imageForm.do")
	public String imageForm() {
		
		return "member/imageForm";
	}
	
	@RequestMapping("/imageUpdate.do")
	public String imageUpdate(HttpServletRequest request, HttpSession session, RedirectAttributes rttr) {
		// 파일업로드를 할 수 있게 도와주는 객체가 필요하다.(cos.jar)
		// 파일업로드를 할 수 있게 도와주는 MultipartRequest 객체를 생성하기 위해서는
		// 5개의 정보가 필요하다.
		// 요청데이터, 저장경로, 최대크기, 인코딩방식, 파일명 중복제거 여부
		MultipartRequest multi = null;
		
		// 저장경로
		String savePath = request.getRealPath("resources/upload");
		
		//이미지 최대크기
		int fileMaxSize = 10 * 1024 * 1024 * 10 * 10;
		
		// 기존 해당 프로필 이미지 삭제
		// - 로그인 한 사람의 프로필 값을 가져와야 한다.
		
		//member 타입으로 다운캐스팅
		
		String memID = ((Member)session.getAttribute("mvo")).getMemID();
		
		// getMmeber 메소드는 memID와 일치하는 회원의 정보 (Member)를 가져온다.
		String oldImg = mapper.getMember(memID).getMemProfile();
		
		// 기존의 프로필 사진 삭제
		File oldFile = new File(savePath+"/"+oldImg);
		// oldFile 이 있을때만 삭제 하겠다.
		if(oldFile.exists()) {
			oldFile.delete();
		}
		
	
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//내가 업로드한 파일 가져오기
		File file = multi.getFile("memProfile");
		
		if(file != null) {// 업로드가 된 상태
			//System.out.println(file.getName());
			// 파일이름에 마지막 '.' 을 찾아서 확장자를 구분한다.
			
			String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
	
			// 소문자를 대문자로 변환하여 저장
			ext = ext.toUpperCase();
			
			// png, gif, jpg 파일이 아니라면
			if(!(ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG"))) {
				if(file.exists()) {
					file.delete();
					
					rttr.addFlashAttribute("msgType","실패메세지");
					rttr.addFlashAttribute("msg","이미지 파일만 가능합니다.(PNG, JPG, GIF)");
					return "redirect:/imageForm.do";
				}
			}
		}
		
		// DB에는 이미지 파일의 이름만 저장한다.
		// 이미지에 대한 모든 정보는 multi가 가지고 있다.
		// 업로드한 파일의 이름을 가져오는 코드
		String newProfile = multi.getFilesystemName("memProfile");
		
		Member mvo = new Member();
		mvo.setMemID(memID);
		mvo.setMemProfile(newProfile);
		
		// 사진 업데이트 후 수정된 회원정보를 다시 가져와서 세션에 담기
		Member m = mapper.getMember(memID);
		
		session.setAttribute("mvo", m);
		
		mapper.profileUpdate(mvo);
		rttr.addFlashAttribute("msgType","성공메세지");
		rttr.addFlashAttribute("msg","이미지 변경에 성공했습니다.");
				
		return "redirect:/";

	}
	
}
