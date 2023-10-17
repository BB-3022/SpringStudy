<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="cpath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
 
  <div class="card">
    <div class="card-header">
		<div class="jumbotron jumbotron-fluid">
			  <div class="container" >
			    <h1>Spring MVC10</h1>
			    <p>Java  HTML -> CSS -> JavaScript -> JSP&Servlet -> Spring F/W -> Spring Boot</p>
			  </div>
		</div>
    </div>
    <div class="card-body">
		<div class="row">
			<div class="col-lg-3">
				<div class="card" style="min-height: 500px; max-height: 1000 px;">
					<div class="card-body">
						<h4 class="card-title">GUEST</h4>
						<p class="card-text">회원님 Welcome!</p>
						<form action="">
							<div class="form-group">
								<label for="memID">아이디</label>
								<input type="text" class="form-control" id="memID" name="memID">
							</div>
							<div class="form-group">
								<label for="memPwd">비밀번호</label>
								<input type="password" class="form-control" id="memPwd" name="memPwd">
							</div>
							<button type="submit" class="form-control btn btn-sm btn-info">로그인</button>
						</form>
					</div>
				</div>
			</div>
			<div class="col-lg-5">
				<div class="card" style="min-height: 500px; max-height: 1000 px;">
					<div class="card-body">
						<table class="table table-bordered table-hover">
							<thead>
								<th>번호</th>
								<th>제목</th>
								<th>작성일</th>
								<th>조회수</th>
							</thead>
							<tbody>
								<c:forEach var="vo" items="${list}" varStatus="i">
									<tr>
										<td>${i.count}</td>
										<td><a href="${vo.idx}">${vo.title}</a></td>
										<td><fmt:formatDate value="${vo.indate}" pattern="yyyy-MM-dd"/></td>
										<td>${vo.count}</td>
									</tr>
								</c:forEach>
							</tbody>						
						</table>
					</div>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="card" style="min-height: 500px; max-height: 1000 px;">
					<div class="card-body">
						<form id="regForm" action="${cpath}/register" method="post">
							<div class="form-group">
								<label for="title">제목</label>
								<input type="text" class="form-control" id="title" name="title" placeholder="Enter Title">
							</div>
							<div class="form-group">
								<label for="content">내용</label>
								<textarea id="content" name="content" class="form-control" placeholder="Enter Content" rows="7" cols=""></textarea>
							</div>
							<div class="form-group">
								<label for="writer">작성자</label>
								<input type="text" class="form-control" id="writer" name="writer" placeholder="Enter Writer">
							</div>
							<button type="button" data-oper="register" class="btn btn-sm btn-warning">등록</button>
							<button type="button" data-oper="reset" class="btn btn-sm btn-danger">취소</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div> 
    <div class="card-footer">스프링 - BBB</div>
  </div>
  
  <script type="text/javascript">
  	$(document).ready(function(){
  		
  		var regForm = $("#regForm");
  		
  		$("button").on("click", function(){
  			
  			var oper = $(this).data("oper");
  			
  			if(oper == "register"){
  				regForm.submit();
  			}else if(oper == "reset"){
  				regForm[0].reset();
  			}
  		});
  		
  		$("a").on("click", function(e){ // 클릭했을 때 요소값을...?
  			
  			e.preventDefault();
  			var idx = $(this).attr("href"); // href 의 속성값을 가지고 온다.
  			
  			// 비동기 통신
  			$.ajax({
  				url : "${cpath}/get", // 해당 cpath를 get이라는 곳에 요청하겠다.
  				type : "get", // get 방식으로 
				data : {"idx" : idx},
				dataType : "json", // json 형태로 게시글을 받아온다.
				success : printBoard,
				error : function(){alert("error");}
  			});
  		});
  	});
  	
  	function printBoard(vo){
  
  		var regForm = $("#regForm");
  		regForm.find("#title").val(vo.title);
  		regForm.find("#content").val(vo.content);
  		regForm.find("#writer").val(vo.writer);
  		
  		regForm.find("input").attr("readonly",true);
  		regForm.find("textarea").attr("readonly",true);
  	}
  	
  	
  </script>

</body>
</html>
    