/***************************************************************************************
 * @Date 2017-03-13
 * @author eimon
 * @Purpose <<For Password Feature>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.web.password;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.dat.whm.exception.SystemException;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.MessageId;

@ManagedBean(name = "PasswordManagementBean")
@ViewScoped
public class PasswordManagementBean extends BaseBean {

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;
	private String currentPassword;
	private String newPassword;
	User user;

	@PostConstruct
	public void init() {
		User loginUser = (User) getParam(Constants.LOGIN_USER);
		user = loginUser;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void changePassword() {
		try {
			userService.changePassword(user.getUsername(), newPassword, currentPassword);
			addInfoMessage(null, MessageId.SUCCESS_CHANGE_PASSWORD, user.getFullName());
		} catch (SystemException e) {
			handelSysException(e);
		}
	}
}