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
		
		
//			if(count<0) {	
			
			response.setContentType("text/html; charset=UTF-8");
			
			
			PrintWriter out = response.getWriter();	
			out.println("<script>");
			out.println("alert('검색')");
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
