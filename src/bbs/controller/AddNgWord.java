package bbs.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.beans.NgWord;
import bbs.service.MessageService;

@WebServlet(urlPatterns = {"/addngword"})
public class AddNgWord extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		String word = request.getParameter("word");
		HttpSession session = request.getSession();
		if(!isExistNgWord(word)){
			new MessageService().addNgWord(word);
		}else{
			session.setAttribute("errorMessages", "そのNGワードは既に登録されています");
		}

		List<NgWord> ngWordList =  new MessageService().getNgWord();
		session.setAttribute("ngWordList", ngWordList);
		response.sendRedirect("./ngwordmanager");

		session.removeAttribute("ngWordList");

	}

	private boolean isExistNgWord(String text){
		List<NgWord> ngWord = new MessageService().getNgWord();
		boolean flg = false;
		for(NgWord n : ngWord){
			if(text.toUpperCase().indexOf(n.getWord().toUpperCase()) != -1){
				flg = true;
			}
		}
		return flg;
	}
}
