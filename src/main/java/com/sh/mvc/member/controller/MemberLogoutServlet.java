package com.sh.mvc.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemberLogoutServlet
 */
@WebServlet("/member/logout")
public class MemberLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 로그아웃처리
		HttpSession session = request.getSession(false); // 세션이 존재하지 않는 경우, 생성하지 말고 null 반환
//		HttpSession session = request.getSession(true); // 세션이 존재하지 않는 경우, 새로 생성해서 반환
		System.out.println(session.getId()); // 쿠키를 대조해 찾음
		if(session != null)
			session.invalidate(); // 세션을 무효화 시킴
		
		// 2. 리다이렉트
		response.sendRedirect(request.getContextPath() + "/");
	}

}
