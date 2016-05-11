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

import bbs.beans.User;

@WebFilter(urlPatterns = {"/top", "/newposts", "/signup", "/usermanager", "/edit", "/ngwordmanager"})
public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User user = (User)((HttpServletRequest)request).getSession().getAttribute("loginUser");

		if(user == null){
			request.setAttribute("errorMessages", "ログインしてください");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
