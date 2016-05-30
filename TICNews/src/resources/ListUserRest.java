package resources;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;

@Path("/users")
public class ListUserRest {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserByIdJSON(@PathParam("id") long IDUser, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		User user = userDao.get(IDUser);
		user.setPassword(null);
		
		return user;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User checkSessionJSON(@Context HttpServletRequest request) {

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");


		
		if (user == null) {

			return new User();
		}
		
		user.setPassword(null);
		return user;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(User userCredentials, @Context HttpServletRequest request) throws Exception {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		User user = userDao.get(userCredentials.getName());

		if ((user != null) && (user.getPassword().equals(userCredentials.getPassword()))) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}

		return Response.status(201).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(User newUser, @Context HttpServletRequest request) throws Exception {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		long newId = userDao.add(newUser);
		
		User user = userDao.get(newId);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		System.out.println(user.getId());
		return Response.status(201).build();
	}

	@DELETE
	public Response delete(@Context HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		session.invalidate();
		return Response.status(201).build();
	}
}
