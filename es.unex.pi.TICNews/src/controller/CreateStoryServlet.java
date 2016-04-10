package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import helper.MyLogger;
import model.News;
import model.User;

@WebServlet("/private/NewStory")
public class CreateStoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	public CreateStoryServlet() {

		super();
	}

	protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		RequestDispatcher view = _request.getRequestDispatcher("/WEB-INF/sendStory.jsp");
		view.forward(_request, _response);
	}

	protected void doPost(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {
		
		request = _request;
		response = _response;
		session = request.getSession();

		NewsDAO newsDao = createNewsDAO();
		News news = createNews();
		newsDao.add(news);
		
		MyLogger.logMessage("Story created, ID: " + news.getId());

		response.sendRedirect(request.getContextPath()+"/Index");
	}
	
	private NewsDAO createNewsDAO() {
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		
		return newsDao;
	}
	
	private News createNews() {
		
		News news = new News();
		
		String title = request.getParameter("title");
		String url = request.getParameter("url");
		String text = request.getParameter("text");
		User user = (User) session.getAttribute("registredUser");
		
		news.setTitle(title);
		news.setUrl(url);
		news.setText(text);
		news.setLikes(0);
		news.setOwner(user.getId());
		
		return news;
	}
}
