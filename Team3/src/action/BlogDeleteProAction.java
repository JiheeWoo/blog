package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BlogDeleteProService;
import vo.ActionForward;

public class BlogDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BlogDeleteProAction");
		ActionForward forward = new ActionForward();
		
		int blog_num = (Integer.parseInt(request.getParameter("blog_num")));
		String nowPage = request.getParameter("page");
		
		BlogDeleteProService blogDeleteProService = new BlogDeleteProService();
		boolean isRightUser = blogDeleteProService.isArticleWriter(blog_num,request.getParameter("blog_pass"));
		if(!isRightUser) {
			System.out.println("패스워드틀림");
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제권한이 없습니다!')");
			out.println("history.back();");
			out.println("</script>");
		} else {
			boolean isDeleteSuccess = blogDeleteProService.removeArticle(blog_num);
			if(!isDeleteSuccess) {
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('삭제실패!')");
				out.println("history.back();");
				out.println("</script>");
			} else {
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("BlogList.bl?page="+nowPage);
			}
		}
		return forward;
	}
		
}
