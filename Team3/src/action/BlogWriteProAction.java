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
		
		
		// �Խ��� �۾��� ��ư�� ���� �ۼ��� �Խù� ����
//		(board_name, board_pass, board_subject, board_content, board_file ��������)
		// import
		BlogBean blogBean = null;
		String realFolder = "";	// ���ε� �� ������ ����Ǵ� ���� ���
		String saveFolder = "/blogUpload";	// ���ε� �� ������ ����Ǵ� ���� ��� => ��Ŭ�������� ���̴� ���� ����
		int fileSize = 10 * 1024 * 1024;	// 10M ũ�� ����
//		MultipartRequest multi = 
//				new MultipartRequest(request,saveFolder,fileSize,"utf-8",new DefaultFileRenamePolicy());	
		
		
		ServletContext context = request.getServletContext();
		realFolder = context.getRealPath(saveFolder); // ������Ʈ ���� ���������� �������� ���� ��� �˾Ƴ���
//		System.out.println(realFolder);
		
		// ���� ���ε� ó���� ���� MultiPartRequest ��ü ���� => cos.jar �ʿ�
		MultipartRequest multi = new MultipartRequest(
				request,	// request ��ü
				realFolder,	// ���� ���ε� ���� ���
				fileSize,	// ���� ũ��
				"UTF-8",	// �ѱ� ���ϸ� ���� ���ڵ� ��� ����
				new DefaultFileRenamePolicy()	// ������ �̸��� ���Ͽ� ���� ó��
		);
		
		// DB �۾��� ���� Service Ŭ���� �ν��Ͻ� ����
		
		// ���޹��� �����͸� ������ BoardBean ��ü ����
		blogBean = new BlogBean();
		blogBean.setBlog_pass(multi.getParameter("blog_pass"));
		blogBean.setBlog_subject(multi.getParameter("blog_subject"));
		blogBean.setBlog_content(multi.getParameter("blog_content"));
		blogBean.setBlog_file(multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
		// => ����! ���� ���� �� multi.getParameter("BOARD_FILE"); �� �ƴ�
		
		BoardWriteProService boardWriteProService = new BoardWriteProService();
		
		// BoardWriteProService �ν��Ͻ��� registArticle() �޼��带 ȣ���Ͽ� �Խù� ��� �۾� ����
		// => �Ķ���ͷ� BoardBean ��ü ����
		// => boolean Ÿ������ ���� ��� ����
		boolean isWriteSuccess = boardWriteProService.registArticle(blogBean);
		
		
		// �ڹٽ�ũ��Ʈ�� ���� "�Խù� ��� ����!" �޼��� ���
		if(!isWriteSuccess) {	// �۾� ���� ���(isWriteSuccess)�� false �� ���
			
			response.setContentType("text/html; charset=UTF-8");
			// �ڹ� �ڵ带 ����Ͽ� �±׳� ��ũ��Ʈ ���� �����Ϸ��� PrintWriter ��ü �ʿ�(��½�Ʈ�� ���)
			
			
			PrintWriter out = response.getWriter();	// response ��ü�κ��� PrintWriter ��ü ������
			// PrintWriter ��ü�� println() �޼��带 ����Ͽ� �ڹٽ�ũ��Ʈ �ۼ� => ���ڿ� ���·� �ۼ�
			out.println("<script>");
			out.println("alert('�Խù� ��� ����')");
			out.println("history.back();");
			out.println("</script>");
		} else {	// �۾� ���� ����� true�� ���
			// ������ ���� ������ ���� ActionForward ��ü�� �����Ͽ�
			// �������� ��θ� BoardList.bo�� �����ϰ�, ������ ���(�����̷�Ʈ ���)�� Redirect ������� ����
			forward = new ActionForward();
			forward.setPath("BlogList.bl");
			forward.setRedirect(true);	// Redirect ��� = true, Dispatch ��� = false ����
			
		}
		
		// FrontController ���� ActionForward ��ü ����

		
		return forward;
	}

}
