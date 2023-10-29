<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- JSTL 사용  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="cpath" value="${pageContext.request.contextPath}" />

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
		<h2>Spring MVC07</h2>
		<div class="panel panel-default">
			<div class="panel-heading">Board</div>			
			<div class="panel-body">
				<form action="${cpath}/board/modify" method="post">
	             <table class="table table-bordered table-hover">
	                <tr>
	                   <td>번호</td>
	                   <td><input class="form-control" type="text" name="idx" value="${vo.idx}" readonly="readonly"></td>
	                </tr>
	                <tr>
	                   <td>제목</td>
	                   <td><input class="form-control" type="text" name="title" value="<c:out value='${vo.title}'/>"></td>
	                </tr>
	                <tr>
	                   <td>내용</td>
	                   <td>
	                      <textarea class="form-control" rows="10" cols="" name="content"><c:out value="${vo.content}"/></textarea>
	                   </td>
	                </tr>
	                <tr>
	                   <td>작성자</td>
	                   <td><input class="form-control" type="text" name="writer" value="${vo.writer}" readonly="readonly"></td>
	                </tr>
	                <tr>
	                   <td colspan="2" style="text-align: center;">
	                      
	                      <c:if test="${not empty mvo && mvo.memID eq vo.memID}">
		                      <button type="submit" class="btn btn-sm btn-primary">수정</button>
		                      <button data-btn="remove" type="button" class="btn btn-sm btn-success">삭제</button>
	                      </c:if>
	                      
	                      <c:if test="${empty mvo || mvo.memID ne vo.memID}">
		                      <button disabled="disabled" type="submit" class="btn btn-sm btn-primary">수정</button>
		                      <button data-btn="remove" disabled="disabled" type="button" class="btn btn-sm btn-success">삭제</button>
	                      </c:if>
	                      
	                      <button data-btn="list" type="button" class="btn btn-sm btn-warning">목록</button>
	                   </td>
	                </tr>
	             </table>        
	          </form>
	          
			<form id="frm" method="get" action="">
				<input id="idx" type="hidden" name="idx" value="${vo.idx}">
			</form>	
				
			</div>
			<div class="panel-footer">Spring 게시판 - BBB</div>
		</div>
	</div>
	
	<script type="text/javascript">
		// 링크처리
		$(document).ready(function(){
			// 클릭을 감지하는 이벤트 달아주기
			//click을 했을 때 함수를 작동시키겠다.
			// e : 클릭했을 때 요소를 감지하겠다.
			$("button").on("click",function(e){
				var formData = $("#frm");
				var btn = $(this).data("btn"); // this : 버튼을 클릭한 요소를 말한다. $(this).data("btn") : data-btn 값을 가져온다.
	
				if(btn == "remove"){
					formData.attr("action","${cpath}/board/remove"); //수정 할 때는 idx를 가지고 가야 한다.
				}else if(btn == "list"){
					formData.attr("action","${cpath}/board/list") // 리스트는 idx 가 필요 없다.
					formData.find("#idx").remove(); // idx 값을 찾아서 제거해 준다.
				}
				
				formData.submit(); // form 태그에 submit 을 작동시킨다.
			});
		});
		
	</script>

</body>
</html>