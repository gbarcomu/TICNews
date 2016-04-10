package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

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

@WebServlet("/private/MyStories")
public class ListUserStoriesServlet extends HttpServlet {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private static final long serialVersionUID = 1L;

	public ListUserStoriesServlet() {
		super();
	}

	protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		request = _request;
		response = _response;
		session = request.getSession();
		
		NewsDAO newsDAO = createNewsDAO();

		List<News> newsList = createNewsList(newsDAO);

		request.setAttribute("newsList", newsList);

		MyLogger.logMessage("Showing user Stories");
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/myStories.jsp");
		view.forward(request, response);
	}
	
	private NewsDAO createNewsDAO() {
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		
		return newsDao;
	}
	
	private List<News> createNewsList(NewsDAO newsDAO) {
		
		User user = (User) session.getAttribute("registredUser");
		List<News> newsList = newsDAO.getAllByOwner(user.getId());
		request.setAttribute("newsList", newsList);
		
		return newsList;
	}
}
