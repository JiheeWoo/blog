package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BlogListService;
import vo.ActionForward;
import vo.BlogBean;
import vo.PageInfo;

public class BlogListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BlogListAction");
		
		int page = 1;
		int limit = 10;
		
		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		BlogListService blogListService = new BlogListService();
		
		int listCount = blogListService.getListCount();
		System.out.println("ÃÑ °Ô½Ã¹° ¼ö : " + listCount);
		
		ArrayList<BlogBean> articleList = new ArrayList<BlogBean>();
		articleList = blogListService.getArticleList(page,limit);
		
		int maxPage = (int)((double)listCount / limit + 0.95);
		int startPage = ((int)((double)page / 10 + 0.9)-1)*10+1;
		int endPage = startPage + 10 - 1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./beer_blog.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
