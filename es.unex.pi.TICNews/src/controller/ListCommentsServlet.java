package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
import model.Comment;
import model.News;
import model.User;

@WebServlet("/ListCommentsServlet")
public class ListCommentsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	
	public ListCommentsServlet() {
		
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("Atendiendo GET");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);
		
		News news = newsDAO.get(Long.parseLong(request.getParameter("id")));
		request.setAttribute("news", news);
		
		User owner = userDAO.get(news.getOwner());
		request.setAttribute("user", owner);
		
		List<Comment> comments = commentDAO.getAllByNews(Long.parseLong(request.getParameter("id")));
		
		Map<Comment, User> commentsWithUser = new HashMap<Comment, User>();

		Iterator<Comment> it = comments.iterator();
		while(it.hasNext()) {
			Comment comment = (Comment) it.next();
			User user = userDAO.get(comment.getOwner());
			commentsWithUser.put(comment, user);
		}
		
		request.setAttribute("commentsWithUser", commentsWithUser);
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/noticia.jsp");
		view.forward(request,response);
	}
}
