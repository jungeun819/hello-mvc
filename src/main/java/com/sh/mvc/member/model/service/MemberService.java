package com.sh.mvc.member.model.service;

import static com.sh.mvc.common.JdbcTemplate.*;

import java.sql.Connection;

import com.sh.mvc.member.model.dao.MemberDao;
import com.sh.mvc.member.model.dto.Member;

public class MemberService {

	private MemberDao memberDao = new MemberDao();

	public Member selectOneMember(String memberId) {
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. dao요청 (Connection 전달)
		Member member = memberDao.selectOneMember(conn, memberId);
		// 3. 반환
		close(conn);
		return member;
	}

	public int insertMember(Member member) {
		int result = 0;
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. dao 요청
		try {
			result = memberDao.insertMember(conn, member);
			// 3. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			// 4. 자원반납
			close(conn);
		}
		return result;
	}

	public int updateMember(Member member) {
		int result = 0;
		// 1. Connection 생성
		Connection conn = getConnection();
		// 2. dao 요청
		try {
			result = memberDao.updateMember(conn, member);
			// 3. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			// 4. 자원반납
			close(conn);
		}
		return result;
	}

	public int updatePassword(String memberId, String newPwd) {
		int result = 0;
		// 1. Connection 생성
		Connection conn = getConnection();
		try {
			// 2. dao요청
			result = memberDao.updatePassword(conn, memberId, newPwd);
			// 3. 트랜잭션 처리
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
			// 4. 자원반납
		}
		return result;
	}
}
