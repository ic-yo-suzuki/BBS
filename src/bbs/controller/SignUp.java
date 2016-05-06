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

@WebServlet(urlPatterns = {"/signup"})
public class SignUp extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{

		request.setAttribute("branches", BranchDao.getBranches());
		request.setAttribute("departments", DepartmentDao.getDepartments());
		request.getRequestDispatcher("signup.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
//		request.setCharacterEncoding("UTF-8");
		User user = new User();
		user.setName(request.getParameter("name"));
		user.setLoginId(request.getParameter("loginId"));
		user.setPassword(request.getParameter("password"));
		user.setBranchId(BranchDao.getBranchId(request.getParameter("branch")));
		user.setDepartmentId(DepartmentDao.getDepartmentId(request.getParameter("department")));

		if(isValid(request, messages)){
			new UserService().register(user);
			System.out.println("ユーザ登録完了");
			response.sendRedirect("./usermanager");
		}else{
			session.setAttribute("errorMessages", messages);
			request.setAttribute("inputValues", user);

			request.setAttribute("branches", BranchDao.getBranches());
			request.setAttribute("selectedBranch", request.getParameter("branch"));
			System.out.println(request.getParameter("branch"));

			request.setAttribute("departments", DepartmentDao.getDepartments());
			request.setAttribute("selectedDepartment", request.getParameter("department"));
			System.out.println(request.getAttribute("selectedDepartment"));

			request.getRequestDispatcher("signup.jsp").forward(request, response);
			session.removeAttribute("editUser");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		if(StringUtils.isEmpty(request.getParameter("name"))){
			messages.add("名前を入力してください");
		}
		if(request.getParameter("name").length() > 11){
			messages.add("名前は10文字以内で入力してください");
		}

		if(StringUtils.isEmpty(request.getParameter("loginId"))){
			System.out.println("ログインID空欄");
			messages.add("ログインIDを入力してください");

		} else if((!request.getParameter("loginId").matches("^[0-9a-zA-Z]{6,20}"))){

			System.out.println("ログインIDフォーマット不正");
			messages.add("ログインIDは半角英数字6文字以上20文字以下で入力してください");

		} else if(UserDao.isExist(request.getParameter("loginId"))){
			System.out.println("アカウント存在");
			messages.add("ログインIDが既に使われています");

		}
		if(StringUtils.isEmpty(request.getParameter("password"))
				|| StringUtils.isEmpty(request.getParameter("password_verify"))){
			System.out.println("パスワード空欄");
			messages.add("パスワードを入力してください");
		}
		if(request.getParameter("password").getBytes().length > request.getParameter("password").length()
				|| request.getParameter("password_verify").getBytes().length > request.getParameter("password_verify").length()
				|| (request.getParameter("password").matches("{6,255}"))
				|| (request.getParameter("password_verify").matches("{6,255}"))){
			System.out.println(request.getParameter("password").length() + ", " + request.getParameter("password_verify").length() + "：パスワードフォーマット不正");
			messages.add("パスワードは半角文字6文字以上255文字以下で入力してください");
		} else if(!(request.getParameter("password").equals(request.getParameter("password_verify")))){
			System.out.println("パスワード不一致");
			messages.add("入力されたパスワードが一致しません");
		}

		if(messages.size() == 0){
			return true;
		}else{
			return false;
		}
	}



}