package resources;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.JDBCNewsDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.NewsDAO;
import dao.UserDAO;
import model.News;
import model.User;

@Path("/stories")
public class ListStoriesRest {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<News, User> getStoriesJSON(@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		return createNewsUserMap(newsDao, userDao);
	}
	
	@GET
	@Path("/likes")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<News, User> getStoriesOrderedByLikesJSON(@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		return createNewsUserMapByLikes(newsDao, userDao);
	}
	
	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public News getStoryByIdJSON(@PathParam("id") long IDStory, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		News story = newsDao.get(IDStory);
		story.setTimeStamp(null);
		story.setDateStamp(null);
		
		return story;
	}

	private Map<News, User> createNewsUserMap(NewsDAO newsDAO, UserDAO userDAO) {

		List<News> newsList = newsDAO.getAll();
		Map<News, User> newsMap = new HashMap<News, User>();

		Iterator<News> it = newsList.iterator();

		while (it.hasNext()) {
			News news = (News) it.next();
			news.setDateStamp(null);
			news.setTimeStamp(null);
			User user = userDAO.get(news.getOwner());
			user.setPassword(null);
			newsMap.put(news, user);

		}

		return newsMap;
	}
	
	private Map<News, User> createNewsUserMapByLikes(NewsDAO newsDAO, UserDAO userDAO) {

		List<News> newsList = newsDAO.getAllOrdered();
		Map<News, User> newsMap = new LinkedHashMap<News, User>();

		Iterator<News> it = newsList.iterator();

		while (it.hasNext()) {
			News news = (News) it.next();
			news.setDateStamp(null);
			news.setTimeStamp(null);
			User user = userDAO.get(news.getOwner());
			user.setPassword(null);
			newsMap.put(news, user);

		}

		return newsMap;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(News story, @Context HttpServletRequest request) throws Exception {
		
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		newsDao.add(story);
		return Response.status(201).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(News story, @Context HttpServletRequest request) throws Exception {
		
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		newsDao.save(story);
		return Response.status(201).build();
	}
	
	@DELETE
	@Path("/{id: [0-9]+}")
	public Response delete(@PathParam("id") long IDStory, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		System.out.println(IDStory);
		newsDao.delete(IDStory);
		
		return Response.status(201).build();
	}
}
