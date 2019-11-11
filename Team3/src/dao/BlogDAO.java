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
	// ------------ 싱글톤 디자인 패턴을 활용한 BlogDAO 인스턴스 작업 ---------------
	// 1. 인스턴스 생성 불가능하도록 생성자 private 선언
	// 2. 직접 인스턴스를 생성하여 저장(3번에서 인스턴스가 없을 때 직접 생성해도 됨)
	// 3. Getter 사용하여 생성한 인스턴스를 외부로 리턴
	// 4. 3번 메서드를 인스턴스 생성없이 호출해야하므로 static 으로 선언
	//    => 이 때, 2번의 변수도 static 으로 선언해야함
	// 5. 2번의 변수도 외부에서 접근이 불가능하도록 private 선언
	private BlogDAO() {}
	
	private static BlogDAO instance;

	public static BlogDAO getInstance() {
		// 기존의 인스턴스가 instance 변수에 저장되어 있지 않을 경우(null 일 경우) 생성하여 리턴
		if(instance == null) {
			instance = new BlogDAO();
		}
		
		return instance;
	}
	// -------------------------------------------------------------------------------

	Connection con;
	
	public void setConnection(Connection con) {
		// Service 클래스로부터 Connection 객체를 전달받아 멤버변수에 저장
		this.con = con;
	}

	public int insertArticle(BlogBean blogBean) {
		// Service 클래스로부터 BlogBean 객체를 전달받아 DB 에 INSERT 작업을 수행한 후 결과(int타입) 리턴
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		int num = 0;
		int insertCount = 0;
		
		try {
			// 현재 게시물의 최대 번호를 조회하여 새로운 글번호를 결정(+1)
			String sql = "SELECT MAX(blog_num) FROM blog"; // 가장 큰 번호 조회
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 등록된 게시물이 하나라도 존재할 경우
				num = rs.getInt(1) + 1; // 새 글 번호 = 현재 게시물 가장 큰 번호 + 1
			} else { // 등록된 게시물이 하나도 없을 경우
				num = 1; // 새 글 번호 = 1
			}
			
			// 전달받은 데이터를 사용하여 INSERT 작업 수행
			// => 마지막 필드인 blog_date(게시물 작성일) 는 데이터베이스 now() 함수 사용하여 현재 시각 사용
			sql = "INSERT INTO blog VALUES(?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
//			pstmt.setString(2, BlogBean.getBlog_name());
			pstmt.setString(2, blogBean.getBlog_pass());
			pstmt.setString(3, blogBean.getBlog_subject());
			pstmt.setString(4, blogBean.getBlog_content());
			pstmt.setString(5, blogBean.getBlog_file());
//			pstmt.setInt(7, num);
//			pstmt.setInt(8, BlogBean.getblog_re_lev());
//			pstmt.setInt(9, BlogBean.getblog_re_seq());
			pstmt.setInt(6, blogBean.getReadcount());
			
			insertCount = pstmt.executeUpdate(); // INSERT 실행 결과 값을 int형 변수로 저장
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("insertArticle() 오류 - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
//			close(con); // 주의!! DAO 내에서 Connection 객체를 닫지 않도록 주의할 것!!!!
			// => Service 클래스에서 commit, rollback 여부를 결정한 후 Connection 에 접근해야하기 때문에
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
			System.out.println("updateArticle()오류" + e.getMessage());
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}
//	public BlogBean getContent(int blog_num) {		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		BlogBean blogBean = null;
//		try {
//			con = getConnection();
//			
//			String sql = "select * from blog where blog_num=?";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1,blog_num);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				blogBean = new BlogBean();
////				blogBean.setNum(rs.getInt("num"));
////				blogBean.setName(rs.getString("name"));
////				blogBean.setPass(rs.getString("pass"));
//				blogBean.setBlog_subject(rs.getString("blog_subject"));
//				blogBean.setBlog_content(rs.getString("blog_content"));
//				blogBean.setBlog_date(rs.getDate("blog_date"));
//				blogBean.setBlog_file(rs.getString("blog_file"));
//				blogBean.setReadcount(rs.getInt("readcount"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(rs!=null) try {rs.close();} catch(SQLException ex) {}
//			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
//			if(con!=null) try {con.close();}  catch(SQLException ex) {}
//		}
//		return blogBean;
//	}
	// 게시물 패스워드 일치 여부 확인
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
//				blogBean.setNum(rs.getInt("num"));
//				blogBean.setName(rs.getString("name"));
//				blogBean.setPass(rs.getString("pass"));
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
		//  board 테이블의 총 게시물 수 조회하여 리턴
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from blog";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우(rs.next() 가 true 일 경우) 
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생 : " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		return listCount;
			
	}
	public ArrayList<BlogBean> selectArticleList(int page, int limit){
		// 지정된 갯수 만큼의 게시물 조회하여 ArrayList 객체에 저장한 뒤 리턴
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<BlogBean> articleList = new ArrayList<BlogBean>();
		
		
		// 조회 시작 게시물 번호(행 번호) 계산
		int startRow = (page-1)*10;
		try {
		String sql = "SELECT * FROM blog order by blog_num desc LIMIT ?,?";
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, startRow);
		pstmt.setInt(2,limit);
		rs = pstmt.executeQuery();
		// 읽어올 게시물이 존재할 경우
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
		System.out.println("selectArticleList() 오류 : " + e.getMessage());
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
//				// 한 사람의 정보 저장(while문 안에 MemberBean 객체 생성)
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
//				// 배열 한칸에 한사람의 정보저장
//				boardList.add(bb);	//add 하면 순서대로 저장!!
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			//마무리 작업
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
//				// 한 사람의 정보 저장(while문 안에 MemberBean 객체 생성)
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
//				// 배열 한칸에 한사람의 정보저장
//				boardList.add(bb);	//add 하면 순서대로 저장!!
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			//마무리 작업
//			if(rs!=null) try {rs.close();} catch(SQLException ex) {}
//			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
//			if(con!=null) try {con.close();}  catch(SQLException ex) {}
//		}
//		return boardList;
//	}
	public int updateReadCount(int blog_num) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select readcount from blog where blog_num=?";
		int readcount = 0;
	try {
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			readcount = rs.getInt("readcount");
		}
		sql = "update blog set readcount=? where blog_num=?";
		// 4단계 실행
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1,readcount+1);
		pstmt.setInt(2, blog_num);
		pstmt.executeUpdate();
		
		
	} catch (Exception e) {
		System.out.println("updateReadCount 에서 오류" + e.getMessage());
		e.printStackTrace();
	} finally {
		if(pstmt!=null) try{pstmt.close();}catch(SQLException ex) {}
	}
	return updateCount;
	}
	
}
