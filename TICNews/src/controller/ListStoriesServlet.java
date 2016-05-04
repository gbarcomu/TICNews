package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCNewsDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.NewsDAO;
import dao.UserDAO;
import helper.MyLogger;
import model.News;
import model.User;

import javax.servlet.RequestDispatcher;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet("/Index")
public class ListStoriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ListStoriesServlet() {
		super();
	}

	protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		NewsDAO newsDAO = createNewsDAO();
		UserDAO userDAO = createUserDAO();

		Map<News, User> newsMap = createNewsUserMap(newsDAO, userDAO);

		_request.setAttribute("newsMap", newsMap);
		
		MyLogger.logMessage("Showing Stories");

		RequestDispatcher view = _request.getRequestDispatcher("/WEB-INF/index.jsp");
		view.forward(_request, _response);
	}

	private NewsDAO createNewsDAO() {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		return newsDao;
	}

	private UserDAO createUserDAO() {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		return userDao;
	}

	private Map<News, User> createNewsUserMap(NewsDAO newsDAO, UserDAO userDAO) {

		List<News> newsList = newsDAO.getAll();
		Map<News, User> newsMap = new HashMap<News, User>();

		Iterator<News> it = newsList.iterator();

		while (it.hasNext()) {
			News news = (News) it.next();
			User user = userDAO.get(news.getOwner());
			newsMap.put(news, user);

		}

		return newsMap;
	}
}
