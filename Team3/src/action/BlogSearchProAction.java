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
		
		
//			if(count<0) {	// �۾� ���� ���(isWriteSuccess)�� false �� ���
			
			response.setContentType("text/html; charset=UTF-8");
			// �ڹ� �ڵ带 ����Ͽ� �±׳� ��ũ��Ʈ ���� �����Ϸ��� PrintWriter ��ü �ʿ�(��½�Ʈ�� ���)
			
			
			PrintWriter out = response.getWriter();	// response ��ü�κ��� PrintWriter ��ü ������
			// PrintWriter ��ü�� println() �޼��带 ����Ͽ� �ڹٽ�ũ��Ʈ �ۼ� => ���ڿ� ���·� �ۼ�
			out.println("<script>");
			out.println("alert('ã���ô� ������ �����ϴ�')");
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
