package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.User;
import bbs.service.MessageService;
import bbs.service.UserService;

@WebServlet(urlPatterns = {"/deletengword"})

public class DeleteNgWordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		int id = Integer.parseInt(request.getParameter("id"));
		new MessageService().deleteNgWord(id);

		HttpSession session = request.getSession();
		List<User> userList =  new UserService().getUserList();
		session.setAttribute("userList", userList);
		response.sendRedirect("./ngwordmanager");
	}
}
