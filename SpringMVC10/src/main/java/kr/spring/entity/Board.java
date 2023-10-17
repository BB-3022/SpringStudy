package kr.spring.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Entity // Board VO가 Database Table 로 만들때 설정
@Data
@ToString
public class Board { // VO <--- ORM ---> TABLE
	
	@Id // PK 의미
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 와 같다. 1씩 증가하면서 값을 넣는 기능
	private Long idx; // 게시글 고유번호 (호환을 long 자료형으로 해준다.)
	
	private String title;
	
	@Column(length = 2000) //길이지정 -> 길이지정 따로 안하면 길이 255
	private String content;

	@Column(updatable = false) // 수정할 때 작성자는 변경하지 않겠다.
	private String writer;

	
	// indate 자동입력, 수정불가능 설정, default datetime 형식
	@Column(insertable = false, updatable = false, columnDefinition = "datetime default now()")
	private Date indate;

	
	@Column(insertable = false, updatable = false, columnDefinition = "int default 0")
	private Long count;

}
