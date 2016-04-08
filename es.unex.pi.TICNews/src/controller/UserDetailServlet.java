package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;

@WebServlet("/UserDetailServlet")
public class UserDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public UserDetailServlet() {
			
			super();
		}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Atendiendo GET");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		User user = userDAO.get(Long.parseLong(request.getParameter("id")));
		request.setAttribute("user", user);

		request.setAttribute("user", user);

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/usuario.jsp");
		view.forward(request, response);
	}
}
