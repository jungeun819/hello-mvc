<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.sh.mvc.member.model.dto.Gender"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section id=enroll-container>
	<h2>회원 정보</h2>
	<form name="memberUpdateFrm" method="post" action="<%= request.getContextPath() %>/member/memberUpdate">
		<table>
			<tr>
				<th>아이디<sup>*</sup></th>
				<td>
					<input type="text" name="memberId" id="memberId" value="<%= loginMember.getMemberId() %>" readonly>
				</td>
			</tr>
			<tr>
				<th>이름<sup>*</sup></th>
				<td>	
				<input type="text"  name="memberName" id="memberName" value="<%= loginMember.getMemberName() %>"  required><br>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
				<input type="date" name="birthday" id="birthday" value="<%= loginMember.getBirthday() %>"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%= loginMember.getEmail() != null ? loginMember.getEmail() : "" %>"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰<sup>*</sup></th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%= loginMember.getPhone() %>" required><br>
				</td>
			</tr>
			<tr>
				<th>포인트</th>
				<td>	
					<input type="text" placeholder="" name="point" id="point" value="<%= loginMember.getPoint() %>" readonly><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
			       		 <input type="radio" name="gender" id="gender0" value="M" <%= loginMember.getGender() == Gender.M ? "checked" : "" %>>
						 <label for="gender0">남</label>
						 <input type="radio" name="gender" id="gender1" value="F" <%= loginMember.getGender() == Gender.F ? "checked" : "" %>>
						 <label for="gender1">여</label>
				</td>
			</tr>
<%
	String hobby = loginMember.getHobby(); // nullable
	List<String> hobbyList = null;
	if(hobby != null){
		hobbyList = Arrays.asList(hobby.split(","));		
	}
%>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동" <%= hobbyList != null && hobbyList.contains("운동") ? "checked" : "" %>><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산" <%= hobbyList != null && hobbyList.contains("등산") ? "checked" : "" %>><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서" <%= hobbyList != null && hobbyList.contains("독서") ? "checked" : "" %>><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임" <%= hobbyList != null && hobbyList.contains("게임") ? "checked" : "" %>><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행" <%= hobbyList != null && hobbyList.contains("여행") ? "checked" : "" %>><label for="hobby4">여행</label><br />


				</td>
			</tr>
		</table>
        <input type="submit" value="정보수정"/>
        <input type="button" value="비밀번호변경" onclick="updatePassword();"/>
        <input type="button" onclick="deleteMember();" value="탈퇴"/>
	</form>
</section>
<script>
/**
 * 기존비밀번호입력
 * 새비밀번호/비밀번호 확인
 * 
 * 기존비밀번호가 일치하면, 새비밀번호로 업데이트
 * 기존비밀번호가 일치하지 않으면, 새비밀번호 업데이트 취소
 * 
 * Get /mvc/member/updatePassword 비밀번호 폼 요청
 * Post /mvc/member/updatePassword db비밀번호 변경 요청
 */
const updatePassword = () => {
	location.href = "<%= request.getContextPath() %>/member/updatePassword";
};

document.memberUpdateFrm.onsubmit = (e) => {
	const memberName = document.querySelector("#memberName");
	const phone = document.querySelector("#phone");

	// 이름 - 한글 2글자이상
	if(!/^[가-힣]{2,}$/.test(memberName.value)){
		alert("이름은 한글 2글자 이상이어야 합니다.");
		memberName.select();
		return false;
	}
	
	// 전화번호는 숫자 01012345678 형식
	if(!/^010[0-9]{8}$/.test(phone.value)){
		alert("전화번호가 유효하지 않습니다.");
		phone.select();
		return false;
	}
	
};

</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
