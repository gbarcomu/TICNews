package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import model.Comment;
import model.User;

@WebServlet("/CreateCommentServlet")
public class CreateCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public CreateCommentServlet() {

		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("ListNewsServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);

		String text = request.getParameter("nuevoComent");

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("registredUser");

		if (user != null) {

			Long newsID = Long.parseLong(request.getParameter("newsID"));

			Comment comment = new Comment();
			comment.setText(text);
			comment.setNews(newsID);
			comment.setOwner(user.getId());

			commentDao.add(comment);

			response.sendRedirect("ListCommentsServlet?id=" + newsID);
		}
		
		else {
			
			response.sendRedirect("LoginServlet");
		}
	}
}
