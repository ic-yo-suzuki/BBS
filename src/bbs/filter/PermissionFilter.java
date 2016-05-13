package bbs.filter;
import java.io.IOException;
import java.util.List;

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

import bbs.beans.Comment;
import bbs.beans.Message;
import bbs.beans.User;
import bbs.service.MessageService;

@WebFilter(urlPatterns = {"/signup", "/usermanager", "/edit"})
public class PermissionFilter implements Filter{

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User user = (User)((HttpServletRequest)request).getSession().getAttribute("loginUser");
		try{
			if(user.getDepartmentId() != 1){
				HttpSession session = ((HttpServletRequest)request).getSession();
				session.setAttribute("errorMessages", "この操作に対する権限がありません");
				List<String> categories = new MessageService().getCategories();
				session.setAttribute("categories", categories);
				List<Message> messages =  new MessageService().getMessage();
				session.setAttribute("loginUser", user);
				session.setAttribute("messages", messages);
				session.setAttribute("categories", categories);
				List<Comment> comments = new MessageService().getComment();
				session.setAttribute("comments", comments);
				session.setAttribute("postCount", messages.size());

				((HttpServletResponse)response).sendRedirect("./top");
				session.removeAttribute("categories");
				session.removeAttribute("messages");
				session.removeAttribute("comments");
				session.removeAttribute("postCount");
				return;
			}
			chain.doFilter(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}


	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自動生成されたメソッド・スタブ

	}

}
