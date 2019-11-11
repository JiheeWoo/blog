package svc;

import java.sql.Connection;

import dao.BlogDAO;

import static db.JdbcUtil.*;
import vo.BlogBean;

public class BlogDetailService {

	public BlogBean getArticle(int blog_num) {
		System.out.println("BlogDetailService");
		
		Connection con = getConnection();
		BlogDAO blogDAO = BlogDAO.getInstance();
		blogDAO.setConnection(con);
		
		BlogBean article = blogDAO.selectArticle(blog_num);
		System.out.println(article.getBlog_subject());
		
		int updateCount = blogDAO.updateReadCount(blog_num);
		
		if(updateCount > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		close(con);
		
		return article;
	}

}
