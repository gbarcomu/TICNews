package controllerTest;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import daoTest.DBConnection;

public class ListStoriesServletTest {

	static DBConnection dbConn;
	static NewsDAO newsDAO;
	static Connection conn;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConnection();
		conn = dbConn.create();
	    newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		dbConn.destroy(conn);
	}
}
