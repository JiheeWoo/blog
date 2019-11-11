package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BlogDetailService;
import vo.ActionForward;
import vo.BlogBean;

public class BlogModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BlogModifyFormAction");
		int blog_num = Integer.parseInt(request.getParameter("blog_num"));
//		String page = (String)request.getAttribute("page");
		BlogDetailService blogDetailService = new BlogDetailService();
		BlogBean article = blogDetailService.getArticle(blog_num);
		
		request.setAttribute("article", article);
//		request.setAttribute("page", page);
//		System.out.println("page");
		ActionForward forward = new ActionForward();
		forward.setPath("./blog_modify_write.jsp");
		
		return forward;
	}
	
}
