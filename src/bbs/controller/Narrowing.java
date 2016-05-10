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

@WebServlet(urlPatterns = {"/narrowing"})
public class Narrowing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String category = request.getParameter("category");
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		User user = (User) request.getSession().getAttribute("loginUser");


	 // 開始日時が終了日時よりも後ろにあった場合、開始日時と終了日時を入れ替え

		if(((!dateStart.isEmpty()) && (!dateEnd.isEmpty()) && dateStart.compareTo(dateEnd) > 0)){
			String tmp = dateStart;
			dateStart = dateEnd;
			dateEnd = tmp;
		}

		String[] selectedDates = new String[2];
		selectedDates[0] = dateStart;
		selectedDates[1] = dateEnd;

		request.setAttribute("dates", selectedDates);
		List<String> categories = new MessageService().getCategories();
		request.setAttribute("categories", categories);

		request.setAttribute("selectedCategory", category);
		List<Message> messages;

		if(!category.equals("カテゴリを選択してください") && ((!dateStart.isEmpty()) || (!dateEnd.isEmpty()))){
			messages =  new MessageService().getMessage(category, selectedDates);
		} else if(category.equals("カテゴリを選択してください") && dateStart.isEmpty() && dateEnd.isEmpty()){
			messages =  new MessageService().getMessage();
		} else if(dateStart.isEmpty() && dateEnd.isEmpty()){
			messages =  new MessageService().getMessage(category);
		} else{
			messages =  new MessageService().getMessage(selectedDates);
		}
		request.setAttribute("messages", messages);
		List<Comment> comments = new MessageService().getComment();
		request.setAttribute("comments", comments);
		int[] userPostCount = new MessageService().getUserPostCount(user.getId());
		int[] branchPostCount = new MessageService().getBranchPostCount(user.getId());
		request.setAttribute("userPostCount", userPostCount);
		request.setAttribute("branchPostCount", branchPostCount);
		request.getRequestDispatcher("/top.jsp").forward(request, response);

	}

}
