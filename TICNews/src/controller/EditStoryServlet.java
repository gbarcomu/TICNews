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

@WebServlet("/private/EditStory")
public class EditStoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	public EditStoryServlet() {

		super();
	}

	protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		request = _request;
		response = _response;
		session = request.getSession();

		NewsDAO newsDao = createNewsDAO();

		Long newsID = Long.parseLong(request.getParameter("id"));
		News news = newsDao.get(newsID);

		if (storyBelongsToUser(news.getOwner())) {

			request.setAttribute("news", news);

			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/editStory.jsp");
			view.forward(request, response);

			MyLogger.logMessage("Editing Story, ID: " + news.getId());
		}

		else {

			response.sendRedirect(request.getContextPath() + "/Index");
			MyLogger.logMessage("Editing Story forbidden, ID: " + news.getId());
		}
	}

	protected void doPost(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		request = _request;
		response = _response;
		session = request.getSession();

		NewsDAO newsDao = createNewsDAO();

		Long newsID = Long.parseLong(request.getParameter("newsID"));
		News news = newsDao.get(newsID);

		if (storyBelongsToUser(news.getOwner())) {

			news = editNews(news);
			newsDao.delete(newsID);
			newsDao.add(news);

			response.sendRedirect(request.getContextPath() + "/Index");
			MyLogger.logMessage("Edited Story, ID: " + news.getId());
		}

		else {

			response.sendRedirect(request.getContextPath() + "/Index");
			MyLogger.logMessage("Edited Story forbidden, ID: " + news.getId());
		}
	}

	private NewsDAO createNewsDAO() {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		return newsDao;
	}

	private News editNews(News oldNews) {

		String title = request.getParameter("title");
		String url = request.getParameter("url");
		String text = request.getParameter("text");
		User user = (User) session.getAttribute("registredUser");

		oldNews.setTitle(title);
		oldNews.setUrl(url);
		oldNews.setText(text);
		oldNews.setOwner(user.getId());

		return oldNews;
	}

	private boolean storyBelongsToUser(Long newsOwnerID) {

		User user = (User) session.getAttribute("registredUser");
		return user.getId() == newsOwnerID;
	}
}
