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
import bbs.beans.Message;
import bbs.service.MessageService;

@WebServlet(urlPatterns = {"/deleteComment"})
public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{


		int id = Integer.parseInt(request.getParameter("id"));

		HttpSession session = request.getSession();



		new MessageService().deleteComment(id);


		List<String> categories = new MessageService().getCategories();
		session.setAttribute("categories", categories);
		List<Message> messages =  new MessageService().getMessage();
		session.setAttribute("messages", messages);
		session.setAttribute("categories", categories);
		List<Comment> comments = new MessageService().getComment();
		session.setAttribute("comments", comments);
		response.sendRedirect("./top");
	}

}
