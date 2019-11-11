package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcUtil {
	// DB 관련 연결 및 자원 반환 코드
	// 커넥션 풀(DBCP) 로부터 Connection 객체를 가져와서 리턴
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			Context initCtx = new InitialContext(); // 톰캣으로부터 컨텍스트 객체 가져오기
			Context envCtx = (Context) initCtx.lookup("java:comp/env"); // context.xml 파일의 Resource 정의 컨텍스트 가져오기
			DataSource ds = (DataSource) envCtx.lookup("jdbc/MySQL"); // DataSource 객체 가져오기
			con = ds.getConnection(); // DataSource 객체로부터 저장되어 있는 Connection 객체 가져오기
			con.setAutoCommit(false); // 트랜잭션에 대한 자동 커밋(적용) 기능 해제(기본값은 true)
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return con;
	}
	
	// DB 자원 반환(해제)을 위한 close() 메서드 오버로딩
	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) { // PreparedStatement 도 포함됨
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Auto Commit 기능을 해제했으므로 별도로 Commit, Rollback 작업을 수행할 메서드 정의(Connection 객체 사용)
	public static void commit(Connection con) {
		try {
			con.commit();
			System.out.println("Commit Success!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection con) {
		try {
			con.rollback();
			System.out.println("Rollback Success!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}






















