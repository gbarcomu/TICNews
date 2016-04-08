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
import javax.servlet.http.HttpSession;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;

@WebServlet(urlPatterns = { "/RegisterServlet" })
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public RegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("registredUser");

		if (user == null) {

			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/registro.jsp");
			view.forward(request, response);
		}

		else {

			response.sendRedirect("ListNewsServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		

		logger.info("credentials: " + username + " - " + password);

		User user = new User();
		
		user.setName(username);
		user.setPassword(password);
		user.setEmail(email);
		
		userDao.add(user);

		HttpSession session = request.getSession();
		session.setAttribute("registredUser", user);
		response.sendRedirect("ListNewsServlet");
	}
}
