package svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BlogDAO;
import vo.BlogBean;

import static db.JdbcUtil.*;


public class BlogListService {

	public int getListCount() {
		Connection con = getConnection();
		BlogDAO blogDAO = BlogDAO.getInstance();
		blogDAO.setConnection(con);
		
		int listCount = blogDAO.selectListCount();
		
		System.out.println("게시물 총 갯수 : " + listCount);
		close(con);
		
		return listCount;
	}

	public ArrayList<BlogBean> getArticleList(int page, int limit) {
		Connection con = getConnection();
		BlogDAO blogDAO = BlogDAO.getInstance();
		blogDAO.setConnection(con);
		
		ArrayList<BlogBean> articleList = null;
		articleList = blogDAO.selectArticleList(page,limit);
		
		close(con);
		return articleList;
	}
	
	
}	
