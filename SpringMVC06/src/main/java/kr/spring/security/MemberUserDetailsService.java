package kr.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.spring.entity.Member;
import kr.spring.entity.MemberUser;
import kr.spring.mapper.MemberMapper;

public class MemberUserDetailsService implements UserDetailsService{
	//Spring Security 에서 Mapper 을 사용하기 위한 연결 클래스 -> Service
	
	@Autowired
	private MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 유저네임을 통해서 유저의 정보를 가져온다.
		// 스프링 시큐리티에서 Username = ID 를 의미한다.
		// id를 기준으로 로그인 정보를 가져오는 메소드
		// 내부적으로 보이지는 않지만 스프링 시큐리티가 해당 아이디를 가진 계정을 가져오고,
		// 암호화된 비밀번호와 입력 비밀번호를 비교하여 로그인을 체크하는 메소드
		
		// 로그인 폼에서 아이디 패스워드를 입력하면 MemberUserDetailsService 로 들어온다.
		
		// 로그인 처리하기
		// Spring Security가 로그인 기능을 다 끝마친 상태
		// 이제 개발자는 중간에 비밀번호를 알 수 있는 방법이 없다.
		Member mvo = mapper.login(username);
		
		// 로그인 성공시 이름을 반환, 실패시에는 아무것도 돌려주지 않는다.
		// Spring Security 내부 보안 규정상 우리가 직접만든 클래스 객체 (VO)
		// 바로 담을 수 없다.
		// 내가 원하는 VO를 담을 수 있게 변환해주는 User Class 가 필요하다.
		
		if(mvo != null) {
			// 해당 사용자 존재
			return new MemberUser(mvo);
			// Spring Security Context 안에 회원의 정보를 저장
		}else {
			// 해당 사용자 없음
			throw new UsernameNotFoundException("user with username" + username + "does not exist");
			
		}
	}
	
	
	
	
}
