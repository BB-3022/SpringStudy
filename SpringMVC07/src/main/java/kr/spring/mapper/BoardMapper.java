package kr.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.entity.Board;
import kr.spring.entity.Member;

@Mapper
public interface BoardMapper {
	
	public List<Board> getList(); //getList 게시글 전체 보기 기능
	
	public void insert(Board vo);
	
	public void insertSelectKey(Board vo);

	public Member login(Member vo);

	public Board read(int idx);

	public void update(Board vo);

	public void delete(int idx);

	public void replySeqUpdate(Board parent);

	public void replyInsert(Board vo);
}
