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

@WebFilter(urlPatterns = { "/top", "/newpost", "/signup", "/usermanager", "/edit", "/ngwordmanager" })
public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User loginUser = (User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");
		User user = null;
		if (loginUser != null) {
			user = new UserService().getUser(loginUser.getId());
		}
		if (user == null || user.getStatus() == false || new UserService().isExistUser(user.getId()) == false) {
			HttpSession session = ((HttpServletRequest) request).getSession();
			session.setAttribute("errorMessages", "ログインしてください");
			((HttpServletResponse) response).sendRedirect("./login");

			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
