package svc;


import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BlogDAO;
import vo.BlogBean;

public class BoardWriteProService {
	
	public boolean registArticle(BlogBean blogBean) {
//		System.out.println("BoardWriteProService");
		boolean isWriteSuccess = false; 
		
		Connection con = getConnection();
		
		BlogDAO blogDAO = BlogDAO.getInstance();
		
		blogDAO.setConnection(con);
		
		int insertCount = blogDAO.insertArticle(blogBean);
		
		if(insertCount > 0) {
			commit(con);
			isWriteSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		
		return isWriteSuccess;
	}
	
}

















