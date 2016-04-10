package filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST },urlPatterns = { "/private/*" })
public class LoggedFilter implements Filter {
	private static final Logger logger = Logger.getLogger(Filter.class.getName());

    public LoggedFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;
		
	    HttpSession session = ((HttpServletRequest)request).getSession(true);
		logger.info("checking user in session");
		if(session.getAttribute("registredUser") == null) {
		  res.sendRedirect(req.getContextPath()+"/public/Login");
		}
		else
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

