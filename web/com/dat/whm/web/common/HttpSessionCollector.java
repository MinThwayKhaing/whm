/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-11-24
 * @Version 1.0
 * @Purpose To manage all create or destroy session even.
 *
 ***************************************************************************************/
package com.dat.whm.web.common;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.dat.whm.user.entity.User;
import com.dat.whm.web.Constants;

public class HttpSessionCollector implements HttpSessionListener {
	private static Logger logger = Logger.getLogger(HttpSessionCollector.class);
	private static final Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();
	private static Map<String, String> loginUserMap= new HashMap<>();

	    @Override
	    public void sessionCreated(HttpSessionEvent event) {
	        HttpSession session = event.getSession();
	        session.setAttribute("isDuplicate", false);
	        logger.debug("New Session is created. Session id : "+session.getId());
	        sessions.put(session.getId(), session);
	    }

	    @Override
	    public void sessionDestroyed(HttpSessionEvent event) {
	    	HttpSession session = event.getSession();
	    	if (session.getAttribute(Constants.LOGIN_USER) != null) {
	    		userLogout((User) session.getAttribute(Constants.LOGIN_USER), session.getId());
			}
	    	logger.debug("Session id : "+session.getId()+". Session destoryed.");
	        sessions.remove(session.getId());
	    }

		public static void addLoginUser(String userId, String sessionId) {
			logger.debug("Collecting a new user login.");
			loginUserMap.put(userId, sessionId);
		}

		public static void cheackLoginUser(String userId, String currentSessionId) {
			logger.debug("Cheacking session duplication.");
			if (loginUserMap.containsKey(userId)) {
				String sessionId=loginUserMap.get(userId);
				HttpSession session=sessions.get(sessionId);
				if (session != null) {
					if (!session.getId().equals(currentSessionId)) {
						logger.debug("Session duplicated.");
						session.setAttribute("isDuplicate", true);
						sessions.remove(sessionId);
						loginUserMap.remove(userId);
					}
				}
			}
		}

		public static void userLogout(User user, String sessionId) {
			logger.debug("Processing user logout.");
			if(loginUserMap.containsKey(user.getId())){
				if (sessionId.equals(loginUserMap.get(user.getId()))) {
					loginUserMap.remove(user.getId());
				}
			}
		}
}
