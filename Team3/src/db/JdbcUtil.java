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
	// DB ���� ���� �� �ڿ� ��ȯ �ڵ�
	// Ŀ�ؼ� Ǯ(DBCP) �κ��� Connection ��ü�� �����ͼ� ����
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			Context initCtx = new InitialContext(); // ��Ĺ���κ��� ���ؽ�Ʈ ��ü ��������
			Context envCtx = (Context) initCtx.lookup("java:comp/env"); // context.xml ������ Resource ���� ���ؽ�Ʈ ��������
			DataSource ds = (DataSource) envCtx.lookup("jdbc/MySQL"); // DataSource ��ü ��������
			con = ds.getConnection(); // DataSource ��ü�κ��� ����Ǿ� �ִ� Connection ��ü ��������
			con.setAutoCommit(false); // Ʈ����ǿ� ���� �ڵ� Ŀ��(����) ��� ����(�⺻���� true)
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return con;
	}
	
	// DB �ڿ� ��ȯ(����)�� ���� close() �޼��� �����ε�
	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) { // PreparedStatement �� ���Ե�
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
	
	// Auto Commit ����� ���������Ƿ� ������ Commit, Rollback �۾��� ������ �޼��� ����(Connection ��ü ���)
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






















