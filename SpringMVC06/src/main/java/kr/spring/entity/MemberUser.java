package kr.spring.entity;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class MemberUser extends User{
	// Spring Security에 Member 객체를 담을 수 있게 해주는 클래스
	// MemberUser 클래스 : 스프링 시큐리티에서 로그인 성공시 username에 로그인 성공한 사람의 아이디가 담긴다.
	// username을 바탕으로 login 기능을 수행한다.??
	
	private Member member;
	
	public MemberUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		// 컬렉션 배열형태의 권한을 받아오겠다?
		
		// MemberUser 객체 생성시 아이디, 비밀번호, 권한을 입력받는다.
		// 실제로 우리는 이 생성자를 사용하지 않는다.
		// 하지만 왜 구현했나? 추상메소드라서 어쩔수 없이 구현 해야 한다.
		// 아래 3개만 사용할 것이라면 이걸써도 나쁘지 않다.
		super(username, password, authorities);
	}
	
	// 실제로 사용할 생성자
	public MemberUser(Member mvo) {
		// User 클래스의 생성자를 사용해서 구현한다.
		// 생성자(아이디,비밀번호,권한을 넣어준다)
		super(mvo.getMemID(), mvo.getMemPassword(), 
				/* User 클래스의 생성자에 사용하는 권한정보는 Collection<SimpleGrantedAuthority>로 변경해서 넣어야 한다. */
				mvo.getAuthList().stream()/* 바이트로 변경 */
				/* List<Auth> -> Collection 안에 들어갈 수 있게 변경 */
				.map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
				/* 최종 콜렉션 리스트로 변경 */
				.collect(Collectors.toList())
				);
		
		this.member = mvo; 
	}
	
	
	
	
	
}
