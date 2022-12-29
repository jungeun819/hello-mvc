package com.sh.mvc.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sh.mvc.common.HelloMvcUtils;
import com.sh.mvc.member.model.dto.Member;
import com.sh.mvc.member.model.service.MemberService;

/**
 * Servlet implementation class MemberUpdatePasswordServlet
 */
@WebServlet("/member/updatePassword")
public class MemberUpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 단순 폼요청
		request.getRequestDispatcher("/WEB-INF/views/member/updatePassword.jsp")
			.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 가져오기
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		String memberId = loginMember.getMemberId();
		String oldPwd = HelloMvcUtils.getEncryptedPassword(request.getParameter("oldPassword"), memberId);
		String newPwd = HelloMvcUtils.getEncryptedPassword(request.getParameter("newPassword"), memberId);
		
		// 2. 기존 비밀번호 일치여부 검사
		// db에 있는 비밀번호와 비교 / session의 비밀번호와 비교
		Member member = memberService.selectOneMember(memberId);
		boolean passed = oldPwd.equals(member.getPassword());
		
		// 3. 업무로직
		if(passed) {
			// 신규비밀번호 업데이트 : 쿼리작성(update member set password = ? where member_id = ?)
			int result = memberService.updatePassword(memberId, newPwd);
			
			// 비밀번호변경 성공 메세지 & 리다이렉트 /member/memberView
			if(result > 0) {
				session.setAttribute("msg", "비밀번호 변경 성공");
				response.sendRedirect(request.getContextPath() + "/member/memberView");
			}
			else {
				session.setAttribute("msg", "비밀번호 변경 실패");
				response.sendRedirect(request.getContextPath() + "/member/updatePassword");
			}
		}
		else {
			// 기존비밀번호 틀림 메세지 & 리다이렉트 /member/updatePassword
			session.setAttribute("msg", "기존 비밀번호를 확인해주세요.");
			response.sendRedirect(request.getContextPath() + "/member/updatePassword");
		}
		
		// 4. 세션의 정보는 db의 정보 일치하지 않기 때문에 바꿔줌
		session.setAttribute("loginMember", memberService.selectOneMember(memberId));
	}

}
