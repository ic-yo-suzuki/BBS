package bbs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.User;
import bbs.service.UserService;

@WebFilter(urlPatterns = {"/deleteUser", "/changeStatus"})
public class ModifyUserFilter implements Filter {

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO 自動生成されたメソッド・スタブ
		int modifyUserId = Integer.parseInt(((HttpServletRequest)request).getParameter("id"));
		User loginUser = (User)((HttpServletRequest)request).getSession().getAttribute("loginUser");
		int loginUserId = new UserService().getUser(loginUser.getId()).getId();

		if(modifyUserId == loginUserId){
			HttpSession session = ((HttpServletRequest)request).getSession();

			session.setAttribute("errorMessages", "このユーザに対して変更を適用できません");
			session.setAttribute("userList", new UserService().getUserList());
			((HttpServletResponse)response).sendRedirect("./usermanager");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自動生成されたメソッド・スタブ

	}
}
