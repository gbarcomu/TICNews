package helper;

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

public final class MyLogger {

	private static Logger logger = Logger.getLogger(HttpServlet.class.getName());	
	
	public static void logMessage(String message) {
		
		logger.info(message);
	}
}
