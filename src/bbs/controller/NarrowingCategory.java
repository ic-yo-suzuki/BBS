package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.UserMessage;
import bbs.service.MessageService;

@WebServlet(urlPatterns = {"/narrowingCategory"})
public class NarrowingCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String category = request.getParameter("category");


		List<String> categories = new MessageService().getCategories();
		request.setAttribute("categories", categories);
		request.setAttribute("selectedCategory", category);
		List<UserMessage> messages =  new MessageService().getMessage(category);
		request.setAttribute("messages", messages);
		request.getRequestDispatcher("/top.jsp").forward(request, response);


	}

}
