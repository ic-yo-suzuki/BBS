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

import bbs.beans.User;
import bbs.service.LoginService;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		LoginService loginService = new LoginService();
		User user = null;
		int id = 0;
		try {
			id = loginService.login(loginId, password);
		} catch (Exception e) {
			e.printStackTrace();

		}
		if (id != 0) {
			new UserService().updateLastLoginDate(id);
			user = new UserService().getUser(id);
			session.setAttribute("loginUser", user);
			response.sendRedirect("./top");
		} else {
			messages.add("ログインに失敗しました");
			session.setAttribute("errorMessages", messages);
			request.setAttribute("inputValue", request.getParameter("loginId"));
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
