package svc;

import java.sql.Connection;

import dao.BlogDAO;

import static db.JdbcUtil.*;
public class BlogDeleteProService {

	public boolean isArticleWriter(int blog_num, String blog_pass) {
		System.out.println("BlogDeleteProService - isArticleWriter()");
		Connection con = getConnection();
		BlogDAO blogDAO = BlogDAO.getInstance();
		blogDAO.setConnection(con);
		
		boolean isArticleWriter = blogDAO.isBlogArticleWriter(blog_num, blog_pass);
		
		close(con);
		
		return isArticleWriter;
	}

	public boolean removeArticle(int blog_num) {
		System.out.println("BlogDeleteProService - removeArticle()");
		Connection con = getConnection();
		BlogDAO blogDAO = BlogDAO.getInstance();
		blogDAO.setConnection(con);
		boolean isDeleteSuccess = false;
		
		int deleteCount = blogDAO.deleteArticle(blog_num);
		if(deleteCount>0) {
			commit(con);
			isDeleteSuccess = true;
		} else {
			rollback(con);
		}
		close(con);
		
		
		close(con);
		return isDeleteSuccess;
	}

}
