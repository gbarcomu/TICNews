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

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import helper.MyLogger;
import model.User;

@WebServlet(urlPatterns = { "/public/Register" })
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	public RegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		RequestDispatcher view = _request.getRequestDispatcher("/WEB-INF/register.jsp");
		view.forward(_request, _response);
	}

	protected void doPost(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		request = _request;
		response = _response;
		session = request.getSession();

		UserDAO userDao = createUserDAO();

		if (userDoesNotExist(userDao)) {

			User user = createUser(userDao);
			createUserSession(user);
			MyLogger.logMessage("New User created correctly");
			response.sendRedirect(request.getContextPath() + "/Index");
		}

		else {

			MyLogger.logMessage("Username already in use");
			request.setAttribute("messages", "El nombre de usuario ya esta en uso");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/register.jsp");
			view.forward(request, response);
		}
	}

	private UserDAO createUserDAO() {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		return userDao;
	}

	private boolean userDoesNotExist(UserDAO userDao) {

		String username = request.getParameter("username");
		MyLogger.logMessage("New User: " + username);
		User user = userDao.get(username);

		return user == null;
	}

	private User createUser(UserDAO userDao) {

		User user = new User();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		user.setName(username);
		user.setPassword(password);
		user.setEmail(email);

		userDao.add(user);

		return user;
	}

	private void createUserSession(User registredUser) {

		session.setAttribute("registredUser", registredUser);
	}
}
