package controller;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCNewsDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.NewsDAO;
import dao.UserDAO;
import model.News;
import model.User;

import javax.servlet.RequestDispatcher;

import java.sql.Connection;

@WebServlet("/ListNewsServlet")
public class ListNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
    
    public ListNewsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		logger.info("Atendiendo GET");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		
		List<News> newsList = newsDAO.getAll();
		
		Map<News, User> newsMap = new HashMap<News, User>();
		
		Iterator<News> it = newsList.iterator();
		
		while(it.hasNext()) {
			News news = (News) it.next();
			User user = userDAO.get(news.getOwner());
			newsMap.put(news, user);
			
		}
		
		request.setAttribute("newsMap",newsMap);
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/index.jsp");
		view.forward(request,response);
		
	
	}

	
}
