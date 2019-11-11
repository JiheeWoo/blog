package svc;


// JdbcUtil 클래스의 static 메서드를 클래스명 없이 호출하기 위한 static import
import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BlogDAO;
import vo.BlogBean;

// Action 클래스로부터 지시를 받아 DAO 클래스와 상호작용을 통해 실제 DB 작업을 수행하는 클래스 = Service 클래스
public class BoardWriteProService {
	
	// Action 클래스로부터 데이터를 전달받아 BoardDAO 의 insertArticle() 메서드 호출하여 게시물 등록 수행
	public boolean registArticle(BlogBean blogBean) {
//		System.out.println("BoardWriteProService");
		// Action 클래스에 전달할 결과를 저장할 변수
		boolean isWriteSuccess = false; 
		
		// DB 연결을 위한 Connection 객체를 저장할 변수
		// => db.JdbcUtil 클래스의 getConnection() 메서드 호출하여 Connection 객체 리턴받음
//		Connection con = JdbcUtil.getConnection();
		// JdbcUtil 클래스의 static 메서드를 클래스명 없이 호출하기 위해서 static import 사용
		// => import static db.JdbcUtil.*; 문장 필요
		Connection con = getConnection();
		
		// 싱글톤 디자인 패턴으로 미리 생성된 BoardDAO 인스턴스 가져오기
		BlogDAO blogDAO = BlogDAO.getInstance();
		
		// BoardDAO 인스턴스에 Connection 객체 전달
		blogDAO.setConnection(con);
		
		// BoardDAO 의 insertArticle() 메서드를 호출하여 게시물 등록 작업 수행
		// => 파라미터로 BoardBean 객체 전달
		// => 리턴값으로 executeUpdate() 메서드를 통해 쿼리 실행 결과에 대한 정수값 전달받음
		int insertCount = blogDAO.insertArticle(blogBean);
		
		// 글 쓰기 성공 여부 판별(결과값이 0보다 크면 성공, 아니면 실패)
		if(insertCount > 0) {
			// commit 작업 수행 => JdbcUtil 클래스의 commit() 메서드 호출 => Connection 객체 전달
			commit(con);
			// 성공 여부를 저장하는 변수 isWriteSuccess 를 true 로 변경
			isWriteSuccess = true;
		} else {
			// rollback 작업 수행
			rollback(con);
		}
		
		// Connection 자원 반환을 위해 JdbcUtil 클래스의 close() 메서드 호출
		close(con);
		
		
		return isWriteSuccess;
	}
	
}

















