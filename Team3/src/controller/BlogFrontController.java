package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BlogDeleteProAction;
import action.BlogDetailAction;
import action.BlogListAction;
import action.BlogModifyFormAction;
import action.BlogModifyProAction;
import action.BlogSearchProAction;
import action.BlogWriteProAction;
import vo.ActionForward;


@WebServlet("*.bl")
public class BlogFrontController extends HttpServlet {
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String command = request.getServletPath(); 
		Action action = null;
		ActionForward forward = null;
		if(command.equals("/BlogWriteForm.bl")) {
			forward = new ActionForward();
			forward.setPath("/blog_write.jsp");
		} else if(command.equals("/BlogWritePro.bl")) {
			action = new BlogWriteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BlogList.bl")) {
			action = new BlogListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(command.equals("/BlogDetail.bl")) {
			action = new BlogDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BlogModifyForm.bl")) {
			action = new BlogModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BlogModifyPro.bl")) {
			action = new BlogModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(command.equals("/BlogDeleteForm.bl")) {
			forward = new ActionForward();
			forward.setPath("./blog_delete_write.jsp");
		} else if(command.equals("/BlogDeletePro.bl")) {
			action = new BlogDeleteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	
	if(forward!=null) {
		if(forward.isRedirect()) {	
			response.sendRedirect(forward.getPath());
			System.out.println("redirect");
		} else {	
			System.out.println("dispatch");
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
			dispatcher.forward(request, response);
		}
	}
}
	
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
}
