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

		if(isValid(request, messages, message)){
			try {
				new MessageService().register(message);
			} catch (Exception e) {

			}

		}else{
			messages.add("投稿に失敗しました");
			session.setAttribute("errorMessages", messages);
			request.setAttribute("inputValues", message);
			List<String> categories = new MessageService().getCategories();
			request.setAttribute("categories", categories);
			List<Comment> comments = new MessageService().getComment();
			request.setAttribute("comments", comments);
			request.getRequestDispatcher("/newposts.jsp").forward(request, response);
			return;
		}

		request.setAttribute("loginUser", user);
		response.sendRedirect("./top");

	}

	private boolean isValid(HttpServletRequest request, List<String> messages, Message message) {


		if(StringUtils.isBlank(message.getText())){
			messages.add("本文を入力してください");
		}
		if(message.getText().length() > 1000){
			messages.add("本文は1000文字以下で入力してください");
		}
		if(message.getTitle().length() > 50){
			messages.add("タイトルは50文字以下で入力してください");
		}
		if(StringUtils.isBlank(message.getTitle())){
			messages.add("タイトルを入力してください");
		}
		if(message.getCategory().length() > 10){
			messages.add("カテゴリ名は10文字以内で入力してください");
			message.setCategory("");
		}

		if(isExistNgWord(message.getText())){
			messages.add("使うことの出来ないキーワードが含まれています");
		}
		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}

	private boolean isExistNgWord(String text){
		List<String> ngWord = new MessageService().getNgWord();
		boolean flg = false;
		for(String s : ngWord){
			if(text.indexOf(s) != -1){
				flg = true;
			}
		}
		return flg;
	}
}
