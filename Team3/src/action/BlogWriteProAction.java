package action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.BoardWriteProService;
import vo.ActionForward;
import vo.BlogBean;

public class BlogWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("BlogWriteProAction");
		ActionForward forward = null;
		
		
		// 게시판 글쓰기 버튼을 눌러 작성한 게시물 내용
//		(board_name, board_pass, board_subject, board_content, board_file 가져오기)
		// import
		BlogBean blogBean = null;
		String realFolder = "";	// 업로드 할 파일이 저장되는 실제 경로
		String saveFolder = "/blogUpload";	// 업로드 할 파일이 저장되는 가상 경로 => 이클립스에서 보이는 폴더 구조
		int fileSize = 10 * 1024 * 1024;	// 10M 크기 지정
//		MultipartRequest multi = 
//				new MultipartRequest(request,saveFolder,fileSize,"utf-8",new DefaultFileRenamePolicy());	
		
		
		ServletContext context = request.getServletContext();
		realFolder = context.getRealPath(saveFolder); // 프로젝트 상의 가상폴더를 기준으로 실제 경로 알아내기
//		System.out.println(realFolder);
		
		// 파일 업로드 처리를 위해 MultiPartRequest 객체 생성 => cos.jar 필요
		MultipartRequest multi = new MultipartRequest(
				request,	// request 객체
				realFolder,	// 실제 업로드 폴더 경로
				fileSize,	// 파일 크기
				"UTF-8",	// 한글 파일명에 대한 인코딩 방식 지정
				new DefaultFileRenamePolicy()	// 동일한 이름의 파일에 대한 처리
		);
		
		// DB 작업을 위한 Service 클래스 인스턴스 생성
		
		// 전달받은 데이터를 저장할 BoardBean 객체 생성
		blogBean = new BlogBean();
		blogBean.setBlog_pass(multi.getParameter("blog_pass"));
		blogBean.setBlog_subject(multi.getParameter("blog_subject"));
		blogBean.setBlog_content(multi.getParameter("blog_content"));
		blogBean.setBlog_file(multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
		// => 주의! 파일 지정 시 multi.getParameter("BOARD_FILE"); 이 아님
		
		BoardWriteProService boardWriteProService = new BoardWriteProService();
		
		// BoardWriteProService 인스턴스의 registArticle() 메서드를 호출하여 게시물 등록 작업 지시
		// => 파라미터로 BoardBean 객체 전달
		// => boolean 타입으로 실행 결과 리턴
		boolean isWriteSuccess = boardWriteProService.registArticle(blogBean);
		
		
		// 자바스크립트를 통해 "게시물 등록 실패!" 메세지 출력
		if(!isWriteSuccess) {	// 작업 실행 결과(isWriteSuccess)가 false 일 경우
			
			response.setContentType("text/html; charset=UTF-8");
			// 자바 코드를 사용하여 태그나 스크립트 등을 전송하려면 PrintWriter 객체 필요(출력스트림 사용)
			
			
			PrintWriter out = response.getWriter();	// response 객체로부터 PrintWriter 객체 얻어오기
			// PrintWriter 객체의 println() 메서드를 사용하여 자바스크립트 작성 => 문자열 형태로 작성
			out.println("<script>");
			out.println("alert('게시물 등록 실패')");
			out.println("history.back();");
			out.println("</script>");
		} else {	// 작업 실행 결과가 true일 경우
			// 포워딩 정보 저장을 위한 ActionForward 객체를 생성하여
			// 포워딩할 경로를 BoardList.bo로 지정하고, 포워딩 방식(리다이렉트 방식)을 Redirect 방식으로 지정
			forward = new ActionForward();
			forward.setPath("BlogList.bl");
			forward.setRedirect(true);	// Redirect 방식 = true, Dispatch 방식 = false 전달
			
		}
		
		// FrontController 에게 ActionForward 객체 리턴

		
		return forward;
	}

}
