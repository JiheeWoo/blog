package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BlogModifyProService;
import vo.ActionForward;
import vo.BlogBean;

public class BlogModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BlogModifyProAction");
		int blog_num = Integer.parseInt(request.getParameter("blog_num"));
		String page = request.getParameter("page");
		String blog_pass = request.getParameter("blog_pass");
		System.out.println(page);
		ActionForward forward = null;
		BlogModifyProService blogModifyProService = new BlogModifyProService();
		boolean isRightUser = blogModifyProService.isArticleWriter(blog_num,blog_pass);
		if(!isRightUser) {
//			System.out.println("패스워드 틀림");
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정권한이 없습니다!')");
			out.println("history.back();");
			out.println("</script>");
		} else {
			BlogBean article = new BlogBean();
			article.setBlog_num(blog_num);
			article.setBlog_subject(request.getParameter("blog_subject"));
			article.setBlog_content(request.getParameter("blog_content"));
			boolean isModifySuccess = blogModifyProService.modifyArticle(article);
			if(!isModifySuccess) {
				response.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('글 수정 실패!')");
				out.println("history.back();");
				out.println("</script>");
			} else {
				forward = new ActionForward();
				forward.setRedirect(true);
				forward.setPath("BlogDetail.bl?blog_num="+blog_num+"&page="+page);
				System.out.println(page);
			}
			
			
		}
		request.setAttribute("page", page);
		forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("BlogDetail.bl?blog_num="+blog_num+"&page="+page);
		
		return forward;
	}
	
}
