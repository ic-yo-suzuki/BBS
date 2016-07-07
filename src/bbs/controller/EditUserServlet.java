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

import bbs.beans.User;
import bbs.dao.BranchDao;
import bbs.dao.DepartmentDao;
import bbs.dao.UserDao;
import bbs.service.UserService;

@WebServlet(urlPatterns = { "/edit" })

public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User editUser = null;
		try {
			if (isExistUser(Integer.parseInt(request.getParameter("id")))) {
				editUser = new UserService().getUser(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("editUser", editUser);
				request.setAttribute("branches", BranchDao.getBranches());
				request.setAttribute("departments", DepartmentDao.getDepartments());
				request.getRequestDispatcher("edit.jsp").forward(request, response);
				return;
			} else {
				response.sendRedirect("./usermanager");
				return;
			}
		} catch (NumberFormatException e) {
			response.sendRedirect("./usermanager");
			return;
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		User user = new User();
		boolean passwordModifyFlg = false;

		user.setName(request.getParameter("name"));
		user.setLoginId(request.getParameter("loginId"));
		if (isModifyPassword(request.getParameter("password"), request.getParameter("password_verify"))) {
			user.setPassword(request.getParameter("password"));
			passwordModifyFlg = true;
		}
		user.setBranchId(BranchDao.getBranchId(request.getParameter("branch")));
		user.setDepartmentId(DepartmentDao.getDepartmentId(request.getParameter("department")));
		user.setId(Integer.parseInt(request.getParameter("userId")));

		if (isValid(request, messages, passwordModifyFlg)) {
			new UserService().update(user, passwordModifyFlg);
			response.sendRedirect("./usermanager");
		} else {
			User editUser = new UserService().getUser(user.getId());
			session.setAttribute("errorMessages", messages);
			request.setAttribute("editUser", editUser);
			request.setAttribute("branches", BranchDao.getBranches());
			request.setAttribute("departments", DepartmentDao.getDepartments());
			request.getRequestDispatcher("edit.jsp").forward(request, response);
			session.removeAttribute("editUser");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages, boolean passwordModifyFlg) {

		if (StringUtils.isEmpty(request.getParameter("name"))) {
			messages.add("名前を入力してください");
		}
		if (request.getParameter("name").length() > 11) {
			messages.add("名前は10文字以内で入力してください");
		}

		if (StringUtils.isEmpty(request.getParameter("loginId"))) {
			messages.add("ログインIDを入力してください");
		} else if ((!request.getParameter("loginId").matches("^[0-9a-zA-Z]{6,20}"))) {
			messages.add("ログインIDは半角英数字6文字以上20文字以下で入力してください");
		} else if (!(new UserService().getLoginId(Integer.parseInt(request.getParameter("userId")))
				.equals(request.getParameter("loginId"))) && UserDao.isExist(request.getParameter("loginId"))) {
			messages.add("ログインIDが既に使われています");
		}

		if (!(request.getParameter("password").equals(request.getParameter("password_verify")))) {
			messages.add("入力されたパスワードが一致しません");
		}
		if (passwordModifyFlg) {
			if (request.getParameter("password").getBytes().length > request.getParameter("password").length()
					|| request.getParameter("password_verify").getBytes().length > request
							.getParameter("password_verify").length()
					|| (request.getParameter("password").matches("{6,255}"))
					|| (request.getParameter("password_verify").matches("{6,255}"))) {
				messages.add("パスワードは半角文字6文字以上255文字以下で入力してください");
			}
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isModifyPassword(String password, String password_verify) {
		if (StringUtils.isBlank(password) && StringUtils.isBlank(password_verify)) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isExistUser(int userId) {

		return new UserService().isExistUser(userId);
	}
}
