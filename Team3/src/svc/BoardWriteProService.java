package svc;


// JdbcUtil Ŭ������ static �޼��带 Ŭ������ ���� ȣ���ϱ� ���� static import
import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BlogDAO;
import vo.BlogBean;

// Action Ŭ�����κ��� ���ø� �޾� DAO Ŭ������ ��ȣ�ۿ��� ���� ���� DB �۾��� �����ϴ� Ŭ���� = Service Ŭ����
public class BoardWriteProService {
	
	// Action Ŭ�����κ��� �����͸� ���޹޾� BoardDAO �� insertArticle() �޼��� ȣ���Ͽ� �Խù� ��� ����
	public boolean registArticle(BlogBean blogBean) {
//		System.out.println("BoardWriteProService");
		// Action Ŭ������ ������ ����� ������ ����
		boolean isWriteSuccess = false; 
		
		// DB ������ ���� Connection ��ü�� ������ ����
		// => db.JdbcUtil Ŭ������ getConnection() �޼��� ȣ���Ͽ� Connection ��ü ���Ϲ���
//		Connection con = JdbcUtil.getConnection();
		// JdbcUtil Ŭ������ static �޼��带 Ŭ������ ���� ȣ���ϱ� ���ؼ� static import ���
		// => import static db.JdbcUtil.*; ���� �ʿ�
		Connection con = getConnection();
		
		// �̱��� ������ �������� �̸� ������ BoardDAO �ν��Ͻ� ��������
		BlogDAO blogDAO = BlogDAO.getInstance();
		
		// BoardDAO �ν��Ͻ��� Connection ��ü ����
		blogDAO.setConnection(con);
		
		// BoardDAO �� insertArticle() �޼��带 ȣ���Ͽ� �Խù� ��� �۾� ����
		// => �Ķ���ͷ� BoardBean ��ü ����
		// => ���ϰ����� executeUpdate() �޼��带 ���� ���� ���� ����� ���� ������ ���޹���
		int insertCount = blogDAO.insertArticle(blogBean);
		
		// �� ���� ���� ���� �Ǻ�(������� 0���� ũ�� ����, �ƴϸ� ����)
		if(insertCount > 0) {
			// commit �۾� ���� => JdbcUtil Ŭ������ commit() �޼��� ȣ�� => Connection ��ü ����
			commit(con);
			// ���� ���θ� �����ϴ� ���� isWriteSuccess �� true �� ����
			isWriteSuccess = true;
		} else {
			// rollback �۾� ����
			rollback(con);
		}
		
		// Connection �ڿ� ��ȯ�� ���� JdbcUtil Ŭ������ close() �޼��� ȣ��
		close(con);
		
		
		return isWriteSuccess;
	}
	
}

















