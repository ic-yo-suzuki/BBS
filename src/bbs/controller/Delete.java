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

@WebServlet(urlPatterns = {"/delete"})
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{


		System.out.println(request.getParameter("id"));
		new MessageService().delete(Integer.parseInt(request.getParameter("id")));

		if(!(request.getParameter("permission").equals("1"))){
			request.setAttribute("errorMessages", "この操作の権限がありません");
			request.getRequestDispatcher("/top.jsp").forward(request, response);
		}

		List<String> categories = new MessageService().getCategories();
		request.setAttribute("categories", categories);
		List<UserMessage> messages =  new MessageService().getMessage();
		request.setAttribute("messages", messages);
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}
