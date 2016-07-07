package bbs.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import bbs.beans.NgWord;
import bbs.beans.User;
import bbs.service.MessageService;

@WebServlet(urlPatterns = { "/postComment" })

public class PostCommentServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<String> messages = new ArrayList<String>();
		User user = (User) request.getSession().getAttribute("loginUser");
		HttpSession session = request.getSession();

		if (isValid(request, messages)) {
			Comment comment = new Comment();
			comment.setText(request.getParameter("comment"));
			comment.setUserId(Integer.parseInt(request.getParameter("userId")));
			comment.setPostId(Integer.parseInt(request.getParameter("postId")));

			try {
				new MessageService().insertComment(comment);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			List<String> categories = new MessageService().getCategories();
			session.setAttribute("categories", categories);
			session.setAttribute("errorMessages", messages);

		}
		List<String> categories = new MessageService().getCategories();
		session.setAttribute("categories", categories);
		List<Message> userMessages = new MessageService().getMessage();
		List<Comment> comments = new MessageService().getComment();
		int[] userPostCount = new MessageService().getUserPostCount(user.getId());
		int[] branchPostCount = new MessageService().getBranchPostCount(user.getId());
		session.setAttribute("userPostCount", userPostCount);
		session.setAttribute("branchPostCount", branchPostCount);
		session.setAttribute("loginUser", user);
		session.setAttribute("messages", userMessages);
		session.setAttribute("comments", comments);
		session.setAttribute("categories", categories);
		response.sendRedirect("./top");
		session.removeAttribute("comments");
		session.removeAttribute("messages");
		session.removeAttribute("categories");
		session.removeAttribute("userPostCount");
		session.removeAttribute("branchPostCount");
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		if (StringUtils.isBlank(request.getParameter("comment"))) {
			messages.add("コメントを入力してください");
			System.out.println(request.getParameter("comment"));
		} else if (request.getParameter("comment").length() > 500) {
			messages.add("コメントは500文字以内で入力してください");
		}
		if (isExistNgWord(request.getParameter("comment"))) {
			messages.add("使うことの出来ないキーワードが含まれています");
		}
		if (!isExistPost(Integer.parseInt(request.getParameter("postId")))) {
			messages.add("コメントしようとした投稿は存在しません");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isExistPost(int postId) {
		return new MessageService().isExistPost(postId);
	}

	private boolean isExistNgWord(String text) {
		List<NgWord> ngWord = new MessageService().getNgWord();
		boolean flg = false;
		for (NgWord n : ngWord) {
			if (text.indexOf(n.getWord()) != -1) {
				flg = true;
			}
		}
		return flg;
	}
}
