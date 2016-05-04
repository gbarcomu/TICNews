package resources;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.Comment;
import model.User;

@Path("/comments")
public class ListCommentsRest {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Comment, User> getStoriesJSON(@PathParam("id") long IDStory, @Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		return GenerateComments(commentDao, userDao, IDStory);
	}

	private Map<Comment, User> GenerateComments(CommentDAO commentDao, UserDAO userDao, long IDStory) {

		List<Comment> comments = commentDao.getAllByNews(IDStory);
		Map<Comment, User> commentsWithUser = new HashMap<Comment, User>();
		
		Iterator<Comment> it = comments.iterator();
		while (it.hasNext()) {
			Comment comment = (Comment) it.next();
			comment.setDateStamp(null);
			comment.setTimeStamp(null);
			User user = userDao.get(comment.getOwner());
			commentsWithUser.put(comment, user);
		}
		
		return commentsWithUser;
	}
}
