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
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		User user = null;
		try {
			user = loginService.login(loginId, password);
		} catch (Exception e) {

		}

		HttpSession session = request.getSession();
		if(user != null){
			System.out.println("ログイン成功");
			session.setAttribute("loginUser", user);
//			response.sendRedirect("./");
		} else{
			List<String> messages = new ArrayList<String>();
			messages.add("ログインに失敗しました");
			System.out.println(messages.get(0));
			session.setAttribute("errorMessages", messages);
			request.setAttribute("inputValue", request.getParameter("loginId"));
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
