package kr.spring.mapper;


import org.apache.ibatis.annotations.Mapper;


import kr.spring.entity.Member03;

@Mapper // MyBatis가 interface를 찾기위해 달아주는 부분
public interface MemberMapper {

	public Member03 registerCheck(String memID);

	public int join(Member03 m);

	public Member03 login(Member03 m);

	public int update(Member03 m);

	public void profileUpdate(Member03 mvo);

	public Member03 getMember(String memID);
	
	
	

	
}
