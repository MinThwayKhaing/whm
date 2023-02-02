/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.web.authentication;

import java.io.IOException;

import javax.faces.bean.ManagedProperty;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.web.Constants;

public class AuthenticationFilter implements Filter {
	/** Revised By Sai Kyaw Ye Myint on 2017/08/01. */
	private final String ERROR_PAGE = "/faces/error/error.xhtml";
	private final String LOGIN_PAGE = "/faces/login.xhtml";
	private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"%s\"></redirect></partial-response>";
	private FilterConfig config;
	private ServletContext servletContext;
	@ManagedProperty(value = "#{UserService}")
	protected IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void init(FilterConfig filterConfig) {
		config = filterConfig;
		servletContext = config.getServletContext();
	}

	/**
	 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/08/01 Explanation:
	 * Revised for session time out.
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		HttpSession session = httpRequest.getSession();
		boolean ajaxRequest = "partial/ajax".equals(httpRequest
				.getHeader("Faces-Request"));
		String errorPage = httpRequest.getContextPath() + ERROR_PAGE;
		String loginPage = httpRequest.getContextPath() + LOGIN_PAGE;
		try {
			/**
			 * Revised By : Sai Kyaw Ye Myint  
			 * Revised Date : 2017/11/24 
			 * Explanation: Revised for duplicate login.
			 */
			if((boolean) session.getAttribute("isDuplicate")){
				session.setAttribute("message", "Dual login was detected.!!!");
				session.setAttribute(Constants.LOGIN_USER, null);
				if (ajaxRequest) {
					httpResponse.setContentType("text/xml");
					httpResponse.setCharacterEncoding("UTF-8");
					httpResponse.getWriter().printf(AJAX_REDIRECT_XML, errorPage);
				} else {
					httpResponse.sendRedirect(errorPage);
				}
			}else {

			User user = (User) session.getAttribute(Constants.LOGIN_USER);
			if(httpRequest.getRequestedSessionId() == null){
				httpResponse.sendRedirect(loginPage);
			}
			else{
				/* Session Time out.*/
				if(httpRequest.getRequestedSessionId() != null && !httpRequest.isRequestedSessionIdValid() ){
					session.setAttribute("message", "Session have been time out.!!!");
					if (ajaxRequest) {
						httpResponse.setContentType("text/xml");
						httpResponse.setCharacterEncoding("UTF-8");
						httpResponse.getWriter().printf(AJAX_REDIRECT_XML, errorPage);
					} else {
						httpResponse.sendRedirect(errorPage);
					}
				}
				else if(httpRequest.getRequestedSessionId() != null && httpRequest.isRequestedSessionIdValid()){
					
					/* Direct url access before login.*/
					if(user == null){
						httpResponse.sendRedirect(loginPage);
					}
					else{
						filterChain.doFilter(servletRequest, servletResponse);
					}
				}
				else {
					filterChain.doFilter(servletRequest, servletResponse);
				}
			}			
		}
		} catch (NullPointerException e) {
			httpResponse.sendRedirect(loginPage);
		}
		servletContext.log("Exiting the filter");
	}

	public void destroy() {
	}
}