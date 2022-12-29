package com.sh.mvc.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter
 * - Filter인터페이스를 구현
 * - 특정 url별로 처리되도록 등록(web.xml) -> @WebFilter사용하면 자동등록
 * 
 * - servlet/jsp에 대해서 전처리/후처리 작업 가능
 * - 전처리 : 인증여부검사(로그인했나안했나), 권한검사, 인코딩처리, ...
 * - 후처리 : 파일압축. 
 * 
 * - 여러개의 필터를 등록하는 경우, FilterChain으로 관리되어 순차적으로 호출.
 * 
 * - doFilter 오버라이딩시 FilterChain#doFilter를 반드시 호출해야 다음 Filter 또는 Servlet으로 넘어간다.
 * 
 * - 필터처리순서 우선순위
 * 1. web.xml에 작성된 순서
 * 2. @WebFilter등록시 Filter이름순
 * 3. url-pattern 작성이 servlet 이름작성보다 우선순위 높음
 *
 */
public class LogFilter implements Filter{
	
	/**
	 * Servlet#doGet, Servlet#doPost의 매개변수는 HttpServletRequest, HttpServletResponse 타입
	 * Filter#doFilter의 매개변수는 ServletRequest, ServletResponse 타입
	 * 
	 * tomcat이 만든 request, response객체의 부모타입
	 * (톰갯이 request, response 객체를 미리 만들어놓음)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request; // 다향성 다운캐스팅..?
		// 전처리
		// 요청주소 - /mvc/member/memberView
		String uri = httpReq.getRequestURI();
		String method = httpReq.getMethod(); // get타입인지 post타입인지 확인 
		System.out.println("========================");
		System.out.println(method + " " + uri);
		System.out.println("------------------------");
		
		// 하는일 : FilterChain의 다음 Filter을 호출하던지 Servlet을 호출해줌
		chain.doFilter(request, response);
		
		// 후처리
		System.out.println("________________________"); // jsp에서 응답처리가 끝나고 찍힘
		System.out.println();
		System.out.println();
	}
	
}
