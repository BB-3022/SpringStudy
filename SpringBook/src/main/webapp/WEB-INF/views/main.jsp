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
	 <h2>Spring Book</h2>
		 <div class="panel panel-default">
		   <div class="panel-heading">Book</div>
		   <div class="panel-body" >
		   	<table id="bookList" class="table table-bordered table-hover">
		   		<tr class="active">
		   			<td>번호</td>
		   			<td>제목</td>
		   			<td>작가</td>
		   			<td>출판</td>
		   			<td>ISBN</td>
		   			<td>보유도서 수</td>
		   			<td>삭제</td>
		   			<td>수정</td>
		   		</tr>
		   		<tbody id="view">
		   		<!-- 비동기 방식으로 가져온 게시글 나오게 할 부분 -->
		   		</tbody>
		   		
		   		<tr>
	               <td colspan= "6">
	                  <buttton onclick="goForm()" class ="btn btn-primary btn-sm" >도서등록</buttton>
	               </td>
	            </tr>
		   	</table>
		   </div>
		   
		   <!-- 글쓰기 폼 -->
		   <div class ="panel-body" id = "wform" style="display : none">
	         <form id ="frm">
	          <table class="table">
				<tr>   
	                <td>제목</td>
	                <td><input type="text" name="title" class="form-control"></textarea></td>
	            </tr>
				<tr>   
	                <td>작가</td>
	                <td><input type="text" name="author" class="form-control"></textarea></td>
	            </tr>
				<tr>   
	                <td>출판사</td>
	                <td><input type="text" name="company" class="form-control"></textarea></td>
	            </tr>
	            <tr>   
	                <td>ISBN</td>
	                <td><input type="text" name="isbn" class="form-control"></textarea></td>
	            </tr>
	            <tr>   
	                <td>보유도서수</td>
	                <td><input type="text" name="count" class="form-control"></textarea></td>
	            </tr>

	             <tr>
	                <td colspan="2" align="center">
	                  <button class="btn btn-success btn-sm" type="button" onclick ="goInsert()">등록</button>                
	                  <button class="btn btn-warning btn-sm" type="reset" id = "fclear">취소</button>                
	                  <button onclick="goList()" class="btn btn-info btn-sm">리스트바로가기</a>                
	                </td>
	             </tr>
	          </table>
	          </form>
	      </div>

		   <div class="panel-footer">스프링도서관-박보배</div>
		 </div>
	</div>
	
	
	<script type="text/javascript">
	
	$(document).ready(function(){
		// HTML 이 다 로딩되고 나서 아래 코드 실행
		loadList();
	});
	
	function loadList(){
	      // 비동기방식으로 게시글 리스트 가져오기
	      // 제이쿼리, ajax 
	      // ajax 안에는 - 요청 url, 어떻게 받을지, 요청방식 등... -> 객체(JSON) 형태로
	      $.ajax({
	         url : "bookList.do", // url
	         type : "get", // get, post
	         dataType : "json", //서버로 부터 받아올 datatype
	         success: makeView, // ajax를 요청했을 때 makeView 함수 호출 하겠다.
	         error: function(){alert("error");}
	      });
	   }
	
	
	function makeView(data) {
	    var listHtml = "";
	    $.each(data, function (num, obj) {
	        listHtml += "<tr>";
	        listHtml += "<td>" + (num + 1) + "</td>";
	        listHtml += "<td>" + obj.title + "</td>";
	        listHtml += "<td>" + obj.author + "</td>";
	        listHtml += "<td>" + obj.company + "</td>";
	        listHtml += "<td>" + obj.isbn + "</td>";
	        listHtml += "<td>" + obj.count + "</td>";
	        listHtml += "<td><button onclick='deleteBook(" + obj.num + ")' class='btn btn-primary btn-sm'>삭제</button></td>";
	        listHtml += "<td><button onclick='goForm(" + obj.num + ")' class='btn btn-warning btn-sm'>수정</button></td>";
	        listHtml += "</tr>";
	    });
	 
	    $("#view").html(listHtml);trewyuiop
	}
	
	function goInsert() {
		// 게시글 등록기능 - 비동기
		var fData = $("#frm").serialize();

		$.ajax({
			url : "book/new",
			type : "post",
			data : fData,
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success : loadList,
			error : function() {
				alert("error")
			}
		});

		$("#fclear").trigger("click");
	}
	
	// 삭제 함수
   function deleteBook(num){
	      $.ajax({
	         url : "board/" + num,
	         type: "delete",
	         data: {"num" : num},
	         success:  loadList,
	         error: function(){alert("error")}
	      });
	}
	      
	// 수정 함수
	function editBook(bookId) {
	    // 수정 동작 구현
	    // bookId를 사용하여 해당 도서 정보를 수정하는 동작을 수행할 수 있습니다.
	}
	
	// goForm 함수를 만들어서 bookList는 감추고 wform 은 보이기 하시오.
	function goForm(){
		$("#bookList").css("display","none");
		$("#wform").css("display","block");
	}
	
	</script>
	
	
</body>
</html>