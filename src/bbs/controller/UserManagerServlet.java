package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.Comment;
import bbs.beans.User;
import bbs.beans.UserMessage;
import bbs.service.MessageService;
import bbs.service.UserService;

@WebServlet(urlPatterns = {"/usermanager"})
public class UserManagerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		User user = (User) request.getSession().getAttribute("loginUser");


		if(user == null){
			request.setAttribute("errorMessages", "ログインしてください");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}

		if(user .getDepartmentId() != 1){
			request.setAttribute("errorMessages", "この操作に必要な権限がありません");
			List<String> categories = new MessageService().getCategories();
			request.setAttribute("categories", categories);
			List<UserMessage> messages =  new MessageService().getMessage();
			request.setAttribute("loginUser", user);
			request.setAttribute("messages", messages);
			request.setAttribute("categories", categories);
			List<Comment> comments = new MessageService().getComment();
			request.setAttribute("comments", comments);
			request.getRequestDispatcher("/top.jsp").forward(request, response);
		}

		List<User> userList =  new UserService().getUserList();

		request.setAttribute("userList", userList);
		request.getRequestDispatcher("usermanager.jsp").forward(request, response);
	}
}
