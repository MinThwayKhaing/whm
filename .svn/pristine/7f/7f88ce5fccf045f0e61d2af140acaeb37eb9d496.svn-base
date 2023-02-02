/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.web.authentication;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import mm.com.dat.presto.main.utils.ldapAuthentication.LDAPUtility;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.HttpSessionCollector;
import com.dat.whm.web.common.Properties;

@ManagedBean(name = "LoginBean")
@ViewScoped
public class LoginBean extends BaseBean {
	private Logger logger = Logger.getLogger(LoginBean.class);
	@ManagedProperty(value = "#{UserService}")
	protected IUserService userService;

	private String username;
	private String password;
	private boolean forExam;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isForExam() {
		return forExam;
	}

	public void setForExam(boolean forExam) {
		this.forExam = forExam;
	}

	public String authenticate() {
		logger.debug("Login, Username : " + username);
		/**
		 * Revised By : Sai Kyaw Ye Myint  
		 * Revised Date : 2017/11/24 
		 * Explanation: Revised for AD server login.
		 */
		boolean authenticate;
		boolean adLoginMode = Boolean.valueOf(Properties.getProperty(Constants.APP_SETTING, Constants.AD_LOGIN_MODE));
		if (adLoginMode && !username.equals("admin")) {
			logger.debug("Login with AD Server.");
			authenticate = LDAPUtility.isADUser(username, password);
		}else {
			logger.debug("Login with WHM DB.");
			authenticate = userService.authenticate(username, password);
		}
		
		if (authenticate) {
			logger.debug("Login is successfull (Username : " + username + ")");
			User user = userService.findUser(username);
			if(user != null){
				/**
				 * Revised By : Sai Kyaw Ye Myint  
				 * Revised Date : 2017/11/24 
				 * Explanation: Revised for duplicate login.
				 */
				FacesContext facesContext = getFacesContext();
				HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
				HttpSessionCollector.cheackLoginUser(user.getId(),session.getId());
				putParam(Constants.LOGIN_USER, user);
				HttpSessionCollector.addLoginUser(user.getId(), session.getId());
			}else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Failed to login.You are not register in WHM system."));
				logger.debug("Login is failed (Username : " + username + ") is not register in WHM system");
				RequestContext.getCurrentInstance().execute("PF('loginDialog').show();");
				RequestContext.getCurrentInstance().update("loginForm:login-dialog");
				return null;
			}
			return "home";
		} else {
			//addInfoMessage("Failed to login.Username or password is incorrect.");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Failed to login.Username or password is incorrect."));
			logger.debug("Login is failed (Username : " + username + ")");
		    RequestContext.getCurrentInstance().execute("PF('loginDialog').show();");
		    RequestContext.getCurrentInstance().update("loginForm:login-dialog");
			return null;
		}
	}

	public String logout() {
		FacesContext facesContext = getFacesContext();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		/**
		 * Revised By : Sai Kyaw Ye Myint  
		 * Revised Date : 2017/11/24 
		 * Explanation: Revised for duplicate login.
		 */
		if (getParam(Constants.LOGIN_USER)!= null) {
			HttpSessionCollector.userLogout((User) getParam(Constants.LOGIN_USER), session.getId());
		}
		session.invalidate();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		//return "login";
		return "/faces/login.xhtml?faces-redirect=true";
	}

	public void checkPermission(ComponentSystemEvent event) {
		FacesContext context = getFacesContext();
		ExternalContext extContext = context.getExternalContext();
		String messageId = (String) extContext.getSessionMap().remove(
				Constants.MESSAGE_ID);
		if (messageId != null) {
			addInfoMessage(null, messageId);
		}
	}
}