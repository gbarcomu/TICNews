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

import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import model.News;
import model.User;

@WebServlet("/CreateNewsServlet")
public class CreateNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	public CreateNewsServlet() {

		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("registredUser");

		if (user != null) {

			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/enviarNoticia.jsp");
			view.forward(request, response);
		}

		else {

			response.sendRedirect("LoginServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		String title = request.getParameter("titulo");
		String url = request.getParameter("url");
		String text = request.getParameter("cuerpoNoticia");
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("registredUser");
		
		News news = new News();
		news.setTitle(title);
		news.setUrl(url);
		news.setText(text);
		news.setLikes(0);
		news.setOwner(user.getId());
		
		newsDao.add(news);
		
		response.sendRedirect("ListNewsServlet");
	}
}
