package dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.BlogBean;

import static db.JdbcUtil.*;

public class BlogDAO {
	
	private BlogDAO() {}
	
	private static BlogDAO instance;

	public static BlogDAO getInstance() {
		if(instance == null) {
			instance = new BlogDAO();
		}
		
		return instance;
	}
	// -------------------------------------------------------------------------------

	Connection con;
	
	public void setConnection(Connection con) {
		this.con = con;
	}

	public int insertArticle(BlogBean blogBean) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		int num = 0;
		int insertCount = 0;
		
		try {
			String sql = "SELECT MAX(blog_num) FROM blog"; 
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { 
				num = rs.getInt(1) + 1; 
			} else {
				num = 1; 
			}
			
			sql = "INSERT INTO blog VALUES(?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, blogBean.getBlog_pass());
			pstmt.setString(3, blogBean.getBlog_subject());
			pstmt.setString(4, blogBean.getBlog_content());
			pstmt.setString(5, blogBean.getBlog_file());
			pstmt.setInt(6, blogBean.getReadcount());
			
			insertCount = pstmt.executeUpdate(); 
			
		} catch (SQLException e) {
			System.out.println("insertArticle() 에서 오류 - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}
	public int deleteArticle(int blog_num) {
		int deleteCount = 0;
		PreparedStatement pstmt = null;
		String sql = "delete from blog where blog_num=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, blog_num);
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return deleteCount;
	}
	
	public int updateArticle(BlogBean article) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = "update blog set blog_subject=?,blog_content=? where blog_num=?  ";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBlog_subject());
			pstmt.setString(2, article.getBlog_content());
			pstmt.setInt(3, article.getBlog_num());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateArticle()에서 오류" + e.getMessage());
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
	public boolean isBlogArticleWriter(int blog_num, String blog_pass) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			boolean isArticleWriter = false;
			
			String sql = "select blog_pass from blog where blog_num = ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, blog_num);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(blog_pass.equals(rs.getString("blog_pass"))) {
						isArticleWriter=true;
					} 
			}	
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
			}
			
			return isArticleWriter;
		}
	
	public BlogBean selectArticle(int blog_num) {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BlogBean article = null;
		try {
			con = getConnection();
			
			String sql = "select * from blog where blog_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,blog_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				article = new BlogBean();
				article.setBlog_num(rs.getInt("blog_num"));
				article.setBlog_subject(rs.getString("blog_subject"));
				article.setBlog_content(rs.getString("blog_content"));
				article.setBlog_date(rs.getDate("blog_date"));
				article.setBlog_file(rs.getString("blog_file"));
				article.setReadcount(rs.getInt("readcount"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try {rs.close();} catch(SQLException ex) {}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
		}
		return article;
	}
	
	public int selectListCount() {
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from blog";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문에서 오류 : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
			
	}
	public ArrayList<BlogBean> selectArticleList(int page, int limit){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<BlogBean> articleList = new ArrayList<BlogBean>();
		
		
		int startRow = (page-1)*10;
		try {
		String sql = "SELECT * FROM blog order by blog_num desc LIMIT ?,?";
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, startRow);
		pstmt.setInt(2,limit);
		rs = pstmt.executeQuery();

		while(rs.next()) {
			BlogBean blogBean = new BlogBean();
			blogBean.setBlog_num(rs.getInt("blog_num"));
			blogBean.setBlog_subject(rs.getString("blog_subject"));
			blogBean.setBlog_content(rs.getString("blog_content"));
			blogBean.setBlog_file(rs.getString("blog_file"));
			blogBean.setReadcount(rs.getInt("readcount"));
			blogBean.setBlog_date(rs.getDate("blog_date"));
			
			articleList.add(blogBean);
		}
		
		} catch(SQLException e) {
		System.out.println("selectArticleList() 에서 오류 : " + e.getMessage());
		} finally {
		close(rs);
		close(pstmt);
		}
		return articleList;
		
	
	}
//	public List getBoardList(int startRow, int pageSize) {
//		List boardList = new ArrayList();
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			con = getConnection();
//			
//			String sql = "select * from blog order by blog_num desc, re_seq asc limit ?,?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1, startRow-1);
//			pstmt.setInt(2, pageSize);
//			rs = pstmt.executeQuery();				
//			
//			while(rs.next()) {
//				// �� ����� ���� ����(while�� �ȿ� MemberBean ��ü ����)
//				BoardBean bb = new BoardBean();
//				bb.setNum(rs.getInt("num"));
//				bb.setSubject(rs.getString("subject"));
//				bb.setName(rs.getString("name"));
//				bb.setDate(rs.getDate("date"));
//				bb.setFile(rs.getString("file"));
//				bb.setReadcount(rs.getInt("readcount"));
//				bb.setRe_ref(rs.getInt("re_ref"));
//				bb.setRe_lev(rs.getInt("re_lev"));
//				bb.setRe_seq(rs.getInt("re_seq"));
//				// �迭 ��ĭ�� �ѻ���� ��������
//				boardList.add(bb);	//add �ϸ� ������� ����!!
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			//������ �۾�
//			if(rs!=null) try {rs.close();} catch(SQLException ex) {}
//			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
//			if(con!=null) try {con.close();}  catch(SQLException ex) {}
//		}
//		return boardList;
//	}
	
//	public List getBoardList(int startRow, int pageSize, String search) {
//		List boardList = new ArrayList();
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			con = getConnection();
//			
//			String sql = "select * from board where subject like ? order by num desc limit ?,?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, "%"+search+"%");
//			pstmt.setInt(2, startRow-1);
//			pstmt.setInt(3, pageSize);
//			rs = pstmt.executeQuery();				
//			
//			while(rs.next()) {
//				// �� ����� ���� ����(while�� �ȿ� MemberBean ��ü ����)
//				BoardBean bb = new BoardBean();
//				bb.setNum(rs.getInt("num"));
//				bb.setSubject(rs.getString("subject"));
//				bb.setName(rs.getString("name"));
//				bb.setDate(rs.getDate("date"));
//				bb.setFile(rs.getString("file"));
//				bb.setReadcount(rs.getInt("readcount"));
//				bb.setRe_ref(rs.getInt("re_ref"));
//				bb.setRe_lev(rs.getInt("re_lev"));
//				bb.setRe_seq(rs.getInt("re_seq"));
//				// �迭 ��ĭ�� �ѻ���� ��������
//				boardList.add(bb);	//add �ϸ� ������� ����!!
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(rs!=null) try {rs.close();} catch(SQLException ex) {}
//			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
//			if(con!=null) try {con.close();}  catch(SQLException ex) {}
//		}
//		return boardList;
//	}
	public int updateReadCount(int blog_num){
		int updateCount = 0;
		PreparedStatement pstmt = null;
	try {
		String sql = "update blog set readcount=readcount+1 where blog_num=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, blog_num);
		updateCount=pstmt.executeUpdate();
		
		
	} catch (Exception e) {
		System.out.println("updateReadCount 에서 오류" + e.getMessage());
		e.printStackTrace();
	} finally {
		if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
	}
	return updateCount;
	}
	
}
