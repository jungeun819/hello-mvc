package com.sh.mvc.jdbc.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JdbcTestServlet
 */
@WebServlet("/jdbc/test")
public class JdbcTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		response.getWriter().append("jdbc 연결 테스트 중입니다...");
		
		try {
			testJdbcConnection();
			out.append("<h2>성공</h2>");
		} catch (ClassNotFoundException | SQLException e) {
			out.append("<h2>실패</h2>");
			e.printStackTrace();
		}
	}

	private void testJdbcConnection() throws ClassNotFoundException, SQLException {
		String driverClass = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "web";
		String password = "web";
		
		String sql = "select * from member";
		
		//1. DriverClass 등록
		Class.forName(driverClass);
		System.out.println("[드라이버클래스 등록완료!]");
		
		//2. Connection 객체 생성
		Connection conn = DriverManager.getConnection(url, user, password);
		conn.setAutoCommit(false);
		System.out.println("[Connection객체 생성 완료!]");
		
		//3. PreparedStatement 객체 생성 및 미완성쿼리 값대입
		PreparedStatement pstmt = conn.prepareStatement(sql);
		System.out.println("[PreparedStatement객체 생성 및 실행 완료!]");
		
		//4. 실행 (DML pstmt.excuteUpdate():int, DQL pstmt.excuteQuery():ResultSet)
		ResultSet rset = pstmt.executeQuery();
		
		//5. 이후처리 (DML 트랜잭션처리, DQL ResultSet을 자바변수에 옮겨담기)
		while(rset.next()) {
			String memberId = rset.getString("member_id");
			String memberName = rset.getString("member_name");
			
			System.out.printf("%s\t%s\n", memberId, memberName);
		}
		
		//6. 자원반남
		rset.close();
		pstmt.close();
		conn.close();
		
		System.out.println("[자원 반납 완료!]");
	}

}
