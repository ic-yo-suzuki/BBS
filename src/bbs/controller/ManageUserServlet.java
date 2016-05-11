package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.User;
import bbs.service.UserService;

@WebServlet(urlPatterns = {"/usermanager"})
public class ManageUserServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		List<User> userList =  new UserService().getUserList();

		request.setAttribute("userList", userList);
		request.getRequestDispatcher("usermanager.jsp").forward(request, response);
	}
}
