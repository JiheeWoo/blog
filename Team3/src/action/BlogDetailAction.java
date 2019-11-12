package action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BlogDetailService;
import vo.ActionForward;
import vo.BlogBean;

public class BlogDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BlogDetailAction");
		int blog_num = Integer.parseInt(request.getParameter("blog_num"));
		String nowPage = request.getParameter("page");
		
		System.out.println(blog_num + ", " + nowPage);
		
		BlogDetailService blogDetailService = new BlogDetailService();
		BlogBean blogBean = blogDetailService.getArticle(blog_num);
		
		request.setAttribute("page", nowPage);
		request.setAttribute("article", blogBean);
//		Cookie todayImageCookie = new Cookie("today"+blog_num, blogBean.getBlog_file());
//		todayImageCookie.setMaxAge(60*60*24);
//		response.addCookie(todayImageCookie);
		ActionForward forward = null;
		forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./blog-single.jsp");
		
		return forward;
	}
	
}
