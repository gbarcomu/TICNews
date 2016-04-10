package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCNewsDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.NewsDAO;
import dao.UserDAO;
import helper.MyLogger;
import model.Comment;
import model.News;
import model.User;

@WebServlet("/Story")
public class ShowStoryWithCommentsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ShowStoryWithCommentsServlet() {

		super();
	}

	protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		request = _request;
		response = _response;

		NewsDAO newsDao = createNewsDAO();
		UserDAO userDao = createUserDAO();
		CommentDAO commentDao = createCommentDAO();

		GenerateStory(newsDao, userDao);

		Map<Comment, User> commentsWithUser = GenerateComments(commentDao, userDao);
		
		request.setAttribute("commentsWithUser", commentsWithUser);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/story.jsp");
		view.forward(request, response);
	}

	private void GenerateStory(NewsDAO newsDao, UserDAO userDao) {

		News news = newsDao.get(Long.parseLong(request.getParameter("id")));
		request.setAttribute("news", news);
		User owner = userDao.get(news.getOwner());
		request.setAttribute("user", owner);
		
		MyLogger.logMessage("Showing Story detail, ID: " + news.getId());
	}

	private Map<Comment, User> GenerateComments(CommentDAO commentDao, UserDAO userDao) {

		List<Comment> comments = commentDao.getAllByNews(Long.parseLong(request.getParameter("id")));
		Map<Comment, User> commentsWithUser = new HashMap<Comment, User>();
		
		Iterator<Comment> it = comments.iterator();
		while (it.hasNext()) {
			Comment comment = (Comment) it.next();
			User user = userDao.get(comment.getOwner());
			commentsWithUser.put(comment, user);
		}
		
		return commentsWithUser;
	}

	private UserDAO createUserDAO() {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		return userDao;
	}

	private NewsDAO createNewsDAO() {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		return newsDao;
	}

	private CommentDAO createCommentDAO() {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);

		return commentDao;
	}
}
