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
		
		
		BlogBean blogBean = null;
		String realFolder = "";	
		String saveFolder = "/blogUpload";	
		int fileSize = 10 * 1024 * 1024;	
//		MultipartRequest multi = 
//				new MultipartRequest(request,saveFolder,fileSize,"utf-8",new DefaultFileRenamePolicy());	
		
		
		ServletContext context = request.getServletContext();
		realFolder = context.getRealPath(saveFolder); 
//		System.out.println(realFolder);
		
		// cos.jar 
		MultipartRequest multi = new MultipartRequest(
				request,	
				realFolder,	
				fileSize,	
				"UTF-8",	
				new DefaultFileRenamePolicy()	
		);
		String blog_file = multi.getFilesystemName("blog_file");
		
		blogBean = new BlogBean();
		blogBean.setBlog_pass(multi.getParameter("blog_pass"));
		blogBean.setBlog_subject(multi.getParameter("blog_subject"));
		blogBean.setBlog_content(multi.getParameter("blog_content"));
		blogBean.setBlog_file(multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
		
		BoardWriteProService boardWriteProService = new BoardWriteProService();
		
		boolean isWriteSuccess = boardWriteProService.registArticle(blogBean);
		
		
		if(!isWriteSuccess) {	
			
			response.setContentType("text/html; charset=UTF-8");
			
			
			PrintWriter out = response.getWriter();	
			out.println("<script>");
			out.println("alert('게시물등록실패!')");
			out.println("history.back();");
			out.println("</script>");
		} else {	
			forward = new ActionForward();
			forward.setPath("BlogList.bl");
			forward.setRedirect(true);	
			
		}
		

		
		return forward;
	}

}
