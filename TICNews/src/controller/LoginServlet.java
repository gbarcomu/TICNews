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

@WebServlet(urlPatterns = { "/public/Login" })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		RequestDispatcher view = _request.getRequestDispatcher("/WEB-INF/login.jsp");
		view.forward(_request, _response);
	}

	protected void doPost(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		request = _request;
		response = _response;
		session = request.getSession();

		UserDAO userDao = createUserDAO();

		String username = request.getParameter("username");
		User registredUser = userDao.get(username);
		MyLogger.logMessage("User: " + username);

		if (checkUserAndPassword(registredUser)) {

			createUserSession(registredUser);
			MyLogger.logMessage("User log in correct");
			response.sendRedirect(request.getContextPath() + "/Index");

		} else {
			
			MyLogger.logMessage("User log in error");
			request.setAttribute("messages", "Usuario o contrasena incorrecta");
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/login.jsp");
			view.forward(request, response);
		}
	}

	private boolean checkUserAndPassword(User registredUser) {

		String password = request.getParameter("password");
		return (registredUser != null) && (registredUser.getPassword().equals(password));
	}

	private void createUserSession(User registredUser) {

		session.setAttribute("registredUser", registredUser);
	}

	private UserDAO createUserDAO() {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		return userDao;
	}

}
