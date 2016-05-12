package bbs.controller;

import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet(urlPatterns = {"/deletePost"})
public class DeletePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("./top");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		int id = Integer.parseInt(request.getParameter("id"));
		List<String> errorMessages = new ArrayList<String>();
		HttpSession session = request.getSession();

		if(isExistPost(id)){
			new MessageService().delete(id);
		}else{
			errorMessages.add("削除しようとした投稿は存在しません");
			session.setAttribute("errorMessages", errorMessages);
		}



		List<String> categories = new MessageService().getCategories();
		session.setAttribute("categories", categories);
		List<Message> messages =  new MessageService().getMessage();
		session.setAttribute("messages", messages);
		session.setAttribute("categories", categories);
		List<Comment> comments = new MessageService().getComment();
		session.setAttribute("comments", comments);
		request.setAttribute("postCount", messages.size());
		response.sendRedirect("./top");
	}

	private boolean isExistPost(int id) {
		return new MessageService().isExistPost(id) ;
	}

}
