package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import helper.MyLogger;
import model.Comment;
import model.User;

@WebServlet("/private/Comment")
public class CreateCommentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	
	public CreateCommentServlet() {

		super();
	}

	protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		_response.sendRedirect(_request.getContextPath()+"/Index");
	}

	protected void doPost(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		request = _request;
		response = _response;
		session = request.getSession();
		
		CommentDAO commentDao = createCommentDAO();
		Comment comment = createComment();
		commentDao.add(comment);
		
		MyLogger.logMessage("Comment created, ID: " + comment.getId());

		response.sendRedirect(request.getContextPath()+"/Story?id=" + Long.parseLong(request.getParameter("newsID")));
	}
	
	private CommentDAO createCommentDAO() {
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		
		return commentDao;
	}
	
	private Comment createComment() {
		
		String text = request.getParameter("nuevoComent");
		User user = (User) session.getAttribute("registredUser");
		Long newsID = Long.parseLong(request.getParameter("newsID"));
		
		Comment comment = new Comment();
		comment.setText(text);
		comment.setNews(newsID);
		comment.setOwner(user.getId());
		
		return comment;
	}
}
