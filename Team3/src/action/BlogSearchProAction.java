package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import svc.BlogSearchProService;
import vo.ActionForward;

public class BlogSearchProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BlogSearchProAction");	
		ActionForward forward = null;
		String search = request.getParameter("search");
//		BlogSearchProService blogSearchProService = new BlogSearchProService();
		
//		int count = blogSearchProService.getBlogCount(search);
		
		
//			if(count<0) {	// 작업 실행 결과(isWriteSuccess)가 false 일 경우
			
			response.setContentType("text/html; charset=UTF-8");
			// 자바 코드를 사용하여 태그나 스크립트 등을 전송하려면 PrintWriter 객체 필요(출력스트림 사용)
			
			
			PrintWriter out = response.getWriter();	// response 객체로부터 PrintWriter 객체 얻어오기
			// PrintWriter 객체의 println() 메서드를 사용하여 자바스크립트 작성 => 문자열 형태로 작성
			out.println("<script>");
			out.println("alert('찾으시는 정보가 없습니다')");
			out.println("history.back();");
			out.println("</script>");
//		}
//	else {
			forward = new ActionForward();
			forward.setPath("BlogMain.bl");
			forward.setRedirect(true);
//		}
			return forward;
			
	}
	
}
