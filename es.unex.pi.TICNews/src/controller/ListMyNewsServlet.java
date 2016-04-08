package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import model.News;
import model.User;

@WebServlet("/ListMyNewsServlet")
public class ListMyNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public ListMyNewsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Atendiendo GET");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("registredUser");

		if (user != null) {

			List<News> newsList = newsDAO.getAllByOwner(user.getId());

			request.setAttribute("newsList", newsList);

			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/misNoticias.jsp");
			view.forward(request, response);
		}
		
		else {
			
			response.sendRedirect("LoginServlet");
		}

	}

}
