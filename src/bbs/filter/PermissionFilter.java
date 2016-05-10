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

import bbs.beans.Comment;
import bbs.beans.User;
import bbs.beans.Message;
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
				request.setAttribute("errorMessages", "この操作に対する権限がありません");
				List<String> categories = new MessageService().getCategories();
				request.setAttribute("categories", categories);
				List<Message> messages =  new MessageService().getMessage();
				request.setAttribute("loginUser", user);
				request.setAttribute("messages", messages);
				request.setAttribute("categories", categories);
				List<Comment> comments = new MessageService().getComment();
				request.setAttribute("comments", comments);
				request.getRequestDispatcher("/top.jsp").forward(request, response);
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
