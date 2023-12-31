package kr.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.entity.Book;

@Mapper // MyBatis 가 interface 를 찾기 위해 달아주는 부분
public interface BookMapper {
	
	public List<Book> getLists(); // 게시글 전체보기 기능을 수행한다.

	public void bookInsert(Book book);

	public Book bookContent(int num);

	public void bookDelete(int num);

	public void bookUpdate(Book vo);

	
}
