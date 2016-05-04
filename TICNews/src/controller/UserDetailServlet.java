package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import helper.MyLogger;
import model.User;

@WebServlet("/UserDetail")
public class UserDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public UserDetailServlet() {

		super();
	}

	protected void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws ServletException, IOException {

		request = _request;
		response = _response;

		UserDAO userDao = createUserDAO();
		
		if (userExists(userDao)) {
			
			showUser(userDao);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/user.jsp");
			view.forward(request, response);
		}
		
		else {
			
			MyLogger.logMessage("This User does not exist");
			response.sendRedirect(request.getContextPath()+"/Index");
		}
	}

	private void showUser(UserDAO userDao) {

		User user = userDao.get(Long.parseLong(request.getParameter("id")));
		request.setAttribute("user", user);
		MyLogger.logMessage("Showing user, ID: " + user.getId());
	}

	private boolean userExists(UserDAO userDao) {

		User user = userDao.get(Long.parseLong(request.getParameter("id")));
		return user != null;
	}

	private UserDAO createUserDAO() {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		return userDao;
	}
}
