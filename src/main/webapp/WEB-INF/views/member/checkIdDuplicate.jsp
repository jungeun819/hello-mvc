<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String memberId = request.getParameter("memberId");
	boolean available = (boolean)request.getAttribute("available"); // 아이디가 있으면 false 없으면 true
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디중복검사</title>
<style>
div#checkId-container{text-align:center; padding-top:50px;}
span#available {color:green; font-weight:bold;}
span#duplicated {color:red; font-weight:bold;}
</style>
</head>
<body>
	<div id="checkId-container">
	<!-- 입력한 아이디가 없을 경우 available = true -->
	<% if(available){ %>
		<p>
			[<span id="available"><%= memberId %></span>]는 사용가능합니다.
		</p>
		<p>
			<input type="button" value="닫기" onclick="closePopup();"/>
		</p>
	<!-- 입력한 아이디가 있을 경우 available = false -->
	<% } else { %>
		<p>
			[<span id="duplicated"><%= memberId %></span>]는 이미 사용중입니다.
		</p>
		<!-- 다시 중복검사 servlet에 보냄(jsp->servlet->jsp) 핑퐁 중 -->
		<form action="<%=request.getContextPath()%>/member/checkIdDuplicate" name="checkIdDuplicateFrm">
			<input type="text" name="memberId" id="memberId" placeholder="새로운 아이디를 입력하세요."/>
			<input type="submit" value="제출"/>
		</form>
	<% } %>
	</div>
	<script>
	/* 입력한 아이디가 존재할 경우 아이디 다시 입력받기 */
	<% if(!available){ %>
	document.checkIdDuplicateFrm?.onsubmit = (e) => {
		const memberId = document.querySelector("#memberId");
		if(!/^[A-Za-z0-9]{4,}$/.test(memberId.value)){
			alert("아이디는 영문자/숫자 4글자이상이어야합니다.");
			memberId.select();
			return;
		};
	};
	<% } %>
	
	/* 팝업으로 띄었기때문에 부모객체를 사용 할 수 있음 */
	const closePopup = () => {
		// 부모창의 window객체에 대한 참조
		const parentFrm = opener.document.memberEnrollFrm;
		parentFrm.memberId.value = "<%= memberId %>";
		parentFrm.idValid.value = "1";
		
		// 둘다 가넝
		// this.close(); // window
		self.close(); // window
	};
	</script>
</body>
</html>
