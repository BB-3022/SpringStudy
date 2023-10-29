package kr.spring.service;

import java.util.List;

import kr.spring.entity.Board;
import kr.spring.entity.Member;

// Service 클래스에서 사용할 기능의 이름을 정의하는 인터페이스
public interface BoardService {
	
	// 게시글 전체목록 보기
	// 이전까지 컨트롤러는 뷰네임을 돌려줘야 하기 때문에 리턴타입이 String 이었다.
	// 지금부터는 서브스로부터 받아온 리스트를 돌려준다.
	public List<Board> getList();

	public Member login(Member vo);

	public void register(Board vo);

	public Board get(int idx);

	public void modify(Board vo);

	public void remove(int idx);

	public void reply(Board vo);
}
