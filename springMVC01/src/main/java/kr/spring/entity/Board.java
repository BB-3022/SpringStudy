package kr.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Getter, Setter
@Data
// 전체생성자
@AllArgsConstructor
// 생성자
@NoArgsConstructor
// ToString
@ToString

public class Board {
	
	private int idx; //번호
	private String title; //제목
	private String content; //내용
	private String writer; //작성자
	private String indate; //작성일
	private String count; //조회수
	
}
