package filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.Filter;


@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }
,          urlPatterns = { "/rest/*" })
public class RestFilter implements Filter{

public RestFilter() {
}

public void destroy() {
}

public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	chain.doFilter(request, response);
}

public void init(FilterConfig fConfig) throws ServletException {
}
}
