package bbs.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.User;
//import bbs.beans.UserMessage;
//import bbs.service.MessageService;

@WebServlet(urlPatterns = {"/top"})
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		User user = (User) request.getSession().getAttribute("loginUser");
		boolean isShowMessageForm = false;
		if(user != null){
			isShowMessageForm = true;
		}else{
			request.setAttribute("errorMessages", "ログインしてください");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	//	List<UserMessage> messages =  new MessageService().getMessage();
//		request.setAttribute("messages", messages);
		request.setAttribute("isShowMessageForm", isShowMessageForm);
		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}
}