package bbs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.beans.NgWord;
import bbs.service.MessageService;

@WebServlet(urlPatterns = { "/ngwordmanager" })
public class ManageNgWordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<NgWord> ngWordList = new MessageService().getNgWord();

		request.setAttribute("ngWordList", ngWordList);
		request.getRequestDispatcher("ngwordmanager.jsp").forward(request, response);
	}
}
