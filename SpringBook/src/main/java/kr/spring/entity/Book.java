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

public class Book {
	
	private int num; //번호
	private String title; //제목
	private String author; //작가
	private String company; //출판
	private String isbn; //ISBN
	private int count; //보유도서 수
	
}
