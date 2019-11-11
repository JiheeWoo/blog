package svc;
import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BlogDAO;
import vo.BlogBean;
public class BlogModifyProService {

	public boolean isArticleWriter(int blog_num,String blog_pass) {
		System.out.println("BlogModifyProService");
		Connection con = getConnection();
		BlogDAO blogDAO = BlogDAO.getInstance();
		blogDAO.setConnection(con);
		
		boolean isArticleWriter = blogDAO.isBlogArticleWriter(blog_num,blog_pass);
		
		close(con);
		return isArticleWriter;
	}

	public boolean modifyArticle(BlogBean article) {
		Connection con = getConnection();
		BlogDAO blogDAO = BlogDAO.getInstance();
		blogDAO.setConnection(con);
		
		boolean isModifySuccess = false;
		
		int updateCount = blogDAO.updateArticle(article);
		
		if(updateCount>0) {
			commit(con);
			isModifySuccess = true;
		} else {
			rollback(con);
		}
		close(con);
		return isModifySuccess;
	}
	
	
	
}
