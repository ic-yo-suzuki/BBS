package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.Comment;
import bbs.beans.UserMessage;
import bbs.service.MessageService;

@WebServlet(urlPatterns = {"/delete"})
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String operation = null;
		System.out.println(request.getParameter("id"));
		HttpSession session = request.getSession();
		String mode = null;

		int permission = Integer.parseInt(request.getParameter("permission"));

		switch(permission){
		case 1:
			operation = "delete from posts ";
			mode = "post";
			break;

		case 2:
			operation = "delete from comments ";
			mode = "comment";
			break;

		default:
			request.setAttribute("errorMessages", "この操作の権限がありません");
			List<String> categories = new MessageService().getCategories();
			request.setAttribute("categories", categories);
			List<UserMessage> messages =  new MessageService().getMessage();
			request.setAttribute("messages", messages);
			request.setAttribute("categories", categories);
			List<Comment> comments = new MessageService().getComment();
			request.setAttribute("comments", comments);
			request.getRequestDispatcher("/top.jsp").forward(request, response);
		}


		new MessageService().delete(Integer.parseInt(request.getParameter("id")), operation, mode);


		List<String> categories = new MessageService().getCategories();
		session.setAttribute("categories", categories);
		List<UserMessage> messages =  new MessageService().getMessage();
		session.setAttribute("messages", messages);
		session.setAttribute("categories", categories);
		List<Comment> comments = new MessageService().getComment();
		session.setAttribute("comments", comments);
		response.sendRedirect("./top");
	}

}
