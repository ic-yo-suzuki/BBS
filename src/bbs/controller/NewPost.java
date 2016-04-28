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

import org.apache.commons.lang.StringUtils;

import bbs.beans.Comment;
import bbs.beans.Message;
import bbs.beans.User;
import bbs.service.MessageService;

@WebServlet(urlPatterns = { "/newPost"})

public class NewPost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		User user = (User) request.getSession().getAttribute("loginUser");
		if(user == null){
			request.setAttribute("errorMessages", "ログインしてください");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		List<String> categories = new MessageService().getCategories();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("./newposts.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		Message message = new Message();

		User user = (User) session.getAttribute("loginUser");
		message.setText(request.getParameter("message"));
		message.setId(user.getId());
		message.setTitle(request.getParameter("title"));
		if(request.getParameter("newCategory").length() != 0){
			message.setCategory( request.getParameter("newCategory"));
		}else{
			message.setCategory(request.getParameter("category"));
		}

		if(isValid(request, messages)){
			try {
				new MessageService().register(message);
			} catch (Exception e) {

			}

		}else{
			messages.add("投稿に失敗しました");
			System.out.println(messages.get(0));
			session.setAttribute("errorMessages", messages);
			request.setAttribute("inputValues", message);
			List<String> categories = new MessageService().getCategories();
			request.setAttribute("categories", categories);
			List<Comment> comments = new MessageService().getComment();
			request.setAttribute("comments", comments);
			request.getRequestDispatcher("/newposts.jsp").forward(request, response);
		}
		request.setAttribute("loginUser", user);
		response.sendRedirect("./top");
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String message = request.getParameter("message");
		String title = request.getParameter("title");
		String category = request.getParameter("newCategory");

		if(StringUtils.isBlank(message)){
			messages.add("本文を入力してください");
		}
		if(message.length() > 1000){
			messages.add("本文は1000文字以下で入力してください");
		}
		if(title.length() > 50){
			messages.add("タイトルは50文字以下で入力してください");
		}
		if(StringUtils.isBlank(title)){
			messages.add("タイトルを入力してください");
		}
		if(category.length() > 10){
			messages.add("カテゴリ名は10文字以内で入力してください");
		}
		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}
}
