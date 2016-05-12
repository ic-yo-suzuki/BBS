package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.Comment;
import bbs.beans.Message;
import bbs.beans.User;
import bbs.service.MessageService;

@WebServlet(urlPatterns = {"/top"})

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{


		User user = (User) request.getSession().getAttribute("loginUser");
		List<String> categories = new MessageService().getCategories();
		request.setAttribute("categories", categories);
		List<Message> messages =  new MessageService().getMessage();


		int[] userPostCount = new MessageService().getUserPostCount(user.getId());
		int[] branchPostCount = new MessageService().getBranchPostCount(user.getId());
		request.setAttribute("userPostCount", userPostCount);
		request.setAttribute("branchPostCount", branchPostCount);

		request.setAttribute("loginUser", user);

		request.setAttribute("messages", messages);
		request.setAttribute("postCount", messages.size());
		request.setAttribute("categories", categories);

		List<Comment> comments = new MessageService().getComment();
		request.setAttribute("comments", comments);
		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}



}