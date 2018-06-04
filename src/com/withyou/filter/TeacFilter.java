package com.withyou.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class StuFilter
 */
@WebFilter("/EngiFilter")
public class TeacFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TeacFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletResponse HttpResponse = (HttpServletResponse) response;
		HttpServletRequest HttpRequest = (HttpServletRequest) request;
		if (HttpRequest.getSession().getAttribute("user_teacher") == null) {
			System.out.println("用户未登录,返回至./login.html");
			HttpResponse.sendRedirect(HttpRequest.getContextPath() + "/login_teac.html");
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
