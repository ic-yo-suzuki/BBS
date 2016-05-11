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
import bbs.service.UserService;

@WebServlet(urlPatterns = {"/changeStatus"})
public class ChangeStatusServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		new UserService().changeStatis(id);
		List<User> userList =  new UserService().getUserList();
		HttpSession session = request.getSession();

		session.setAttribute("userList", userList);
		response.sendRedirect("./usermanager");
	}

}
