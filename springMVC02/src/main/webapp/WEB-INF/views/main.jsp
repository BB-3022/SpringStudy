<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- JSTL 사용  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	
	<div class="container">
	 <h2>Spring MVC02</h2>
		 <div class="panel panel-default">
		   <div class="panel-heading">Board</div>
		   
		   <!-- 게시글 테이블 -->
		   <div class="panel-body" >
		   	<table id="boardList" class="table table-bordered table-hover">
		   		<tr class="active">
		   			<td>번호</td>
		   			<td>제목</td>
		   			<td>내용</td>
		   			<td>작성자</td>
		   			<td>작성일</td>
		   			<td>조회수</td>
		   		</tr>
		   		
		   		<tbody id="view">
		   		
		   		<!-- 비동기 방식으로 가져온 게시글 나오게 할 부분 -->
		   		</tbody>
		   		
		   		<tr>
	               <td colspan= "5">
	                  <buttton onclick="goForm()" class ="btn btn-primary btn-sm">글쓰기</buttton>
	               </td>
	            </tr>
		   	</table>
		   </div>
		   
		   <!-- 글쓰기 폼 -->
		   <div class ="panel-body" id = "wform" style="display : none;">
	         <form id ="frm">
	          <table class="table">
	             <tr>
	                <td>제목</td>
	                <td><input type="text" name="title" class="form-control"></td>
	             </tr>
	             <tr>   
	                <td>내용</td>
	                <td><textarea class="form-control" name="content" rows="7" cols="" style ="resize: none"></textarea></td>
	             </tr>
	            <tr>   
	                <td>작성자</td>
	                <td><input type="text" name="writer" class="form-control"></td>             
	             </tr>
	             <tr>
	                <td colspan="2" align="center">
	                  <button class="btn btn-success btn-sm" type="button" onclick ="goInsert()">등록</button>                
	                  <button class="btn btn-warning btn-sm" type="reset" id = "fclear">취소</button>                
	                  <button onclick="goList()" class="btn btn-info btn-sm">목록</a>                
	                </td>
	             </tr>
	          </table>
	          </form>
	      </div>
		   
		   <div class="panel-footer">Spring 게시판 - BBB</div>
		 </div>
	</div>
	
	<script type="text/javascript">
	
	$(document).ready(function(){
		// HTML 이 다 로딩되고 나서 아래 코드 실행
		loadList();
	});
	
	// 게시글 리스트 가져오기 loadList()
	function loadList(){
	      // 비동기방식으로 게시글 리스트 가져오기 기능
	      // 제이쿼리, ajax 
	      // ajax 안에는 - 요청 url, 어떻게 받을지, 요청방식 등... -> 객체(JSON) 형태로
	      $.ajax({
	         url : "boardList.do", // url
	         type : "get", // 요청방식 - get, post
	         dataType : "json", //서버로 부터 받아올 datatype
	         success: makeView, // ajax를 요청했을 때 makeView 함수 호출 하겠다.
	         error: function(){alert("error");}
	      });
	   }
	
	// 비동기 통신 성공 하면 작동하는 함수 makeView() : 게시글의 모든 정보를 받아온다.
	function makeView(data){
		var listHtml = "";
		$.each(data, function(index, obj){
			listHtml += "<tr>";
	         listHtml += "<td>" +(index+1) +"</td>";
	         listHtml += "<td id ='t" +obj.idx+"'>"
	         listHtml += "<a href ='javascript:goContent("+ obj.idx +")'>";
	         listHtml += obj.title;                
	         listHtml += "</a>";            
	         listHtml += "</td>";
	         listHtml +="<td id ='w"+obj.idx+"'>" +obj.content +"</td>";
	         listHtml +="<td>" +obj.writer +"</td>";
	         listHtml +="<td>" +obj.indate +"</td>";
	         listHtml +="<td>" +obj.count +"</td>";
	         listHtml +="</tr>"; 	
		
		     // 상세보기 화면
	         listHtml += "<tr id ='c" + obj.idx +"' style = 'display : none'>";
	         listHtml += "<td>내용</td>";
	         listHtml += "<td colspan ='4'>";
	         listHtml += "<textarea id='ta"+ obj.idx+"' readonly style = 'resize:none' rows='7' class ='form-control'>";   
	         listHtml += obj.content;   
	         listHtml += "</textarea>";       
	         
	         //수정 삭제 화면
	         listHtml += "<br>";
	         listHtml += "<span id ='ub"+obj.idx+"'>";
	         listHtml +="<button onclick='goUpdateForm("+obj.idx+")' class = 'btn btn-primary btn-sm'>수정화면</button></span> &nbsp"
	         listHtml +="<button onclick='goDelete(" + obj.idx +" )' class = 'btn btn-warning btn-sm'>삭제</button> &nbsp"   
	         listHtml += "</td>";        
	         listHtml += "</tr>";
		});     
		
		// view를 찾아서, listHtml 을 보여준다.
		$("#view").html(listHtml);
		
		goList();
	}
	
	// goForm 함수를 만들어서 boardList는 감추고 wform 은 보이기 하시오.
	function goForm(){
		$("#boardList").css("display","none");
		$("#wform").css("display","block");
	}
	
	function goList(){
		$("#boardList").css("display","block");
		$("#wform").css("display","none");
	}
	
	// input 태그 안의 제목, 내용, 작성자 데이터를 가져오기 위해서, form 태그에 id=frm 을 준다. 
	function goInsert(){
		// 직렬화 형태로 가져온다. -> serialize()
		// title="제목"&content="내용"$writer="작성자"
		var fData = $("#frm").serialize();
		
		$.ajax({
			url : "boardInsert.do",
			type :"post",
			data : fData,
			success : loadList(),
			error : function(){alert("error")}
		});	
		
		// trigger() : 강제로 이벤트 실행
		// 게시글 작성 후, 게시글 입력데이터 지우기
		$("#fclear").trigger("click";)
	}
	
	function goContent(idx){
	    // display 값을 가져와서 none과 비교
	    // none 보이지 않는 상태
		if($("#c"+ idx).css("display") == "none"){
	        
	        $.ajax({
	           url : "board/" + idx,   //패스베리어블방식
	           type : "get",
	           dataType : "json",
	           success : function(data) {
	            $("#ta" + idx).val(data.content);
	           },
	           error: function(){alert("error");}
	        });
	        // 보이지 않는 상태라면, 보이도록 설정
	        $("#c"+idx).css("display", "table-row");   
	                
	     }else{
	    	// 보이는 상태라면, 보이지 않게 설정
	        $("#c"+idx).css("display", "none");   
	        
	       $.ajax({
	           url : "board/count/"+idx,
	           type: "put",
	           success:  loadList,
	           error: function(){alert("error")}
	        }); 
	     }
	   }
   
	// 삭제 기능 goDelete()
	function goDelete(idx){
	      $.ajax({
	         url : "board/" + idx,
	         type: "delete",
	         data: {"idx" : idx},
	         success:  loadList,
	         error: function(){alert("error")}

	      });
	      
	   }
	
	</script>
	
</body>
</html>