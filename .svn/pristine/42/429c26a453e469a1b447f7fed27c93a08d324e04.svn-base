/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.PasswordCodecHandler;
import com.dat.whm.common.ErrorCode;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.persistence.interfaces.IUserDAO;
import com.dat.whm.user.service.interfaces.IUserService;

@Service("UserService")
public class UserService implements IUserService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "UserDAO")
	private IUserDAO userDAO;

	@Resource(name = "PasswordCodecHandler")
	private PasswordCodecHandler codecHandler;

	@Transactional(propagation = Propagation.REQUIRED)
	public User addNewUser(User user) throws SystemException {
		try {
			logger.debug("addNewUser() method has been started.");
			User existing = userDAO.find(user.getUsername());
			if(existing != null) {
				throw new SystemException(ErrorCode.NOT_AVAILABLE_USERNAME, "Username (" + user.getUsername() + ") is not available.");
			}
			user.setPassword(codecHandler.encode(user.getPassword()));
			user = userDAO.insert(user);
			logger.debug("addNewUser() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewUser() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a User", e);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User updateUser(User user) throws SystemException {
		try {
			logger.debug("updateUser() method has been started.");
			user = userDAO.update(user);
			logger.debug("updateUser() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateUser() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to update user", e);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(User user) throws SystemException {
		try {
			logger.debug("deleteUser() method has been started.");
			userDAO.delete(user);
			logger.debug("deleteUser() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteUser() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to delete user", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<User> findAllUser() {
		logger.debug("findAllUser() method has been started.");
		List<User> result = userDAO.findAll();
		logger.debug("findAllUser() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User findUser(String username) {
		logger.debug("findUser() method has been started.");
		User user = userDAO.find(username);
		logger.debug("findUser() method has been finished.");
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<User> findUserByRole(Role role) {
		logger.debug("findUserByRole() method has been started.");
		List<User> result = userDAO.findByRole(role);
		logger.debug("findUserByRole() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User findById(String id) {
		logger.debug("findById() method has been started.");
		User user = userDAO.findById(id);
		logger.debug("findById() method has been finished.");
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void changePassword(String username, String newPassword, String oldPassword) throws SystemException {
		try {
			logger.debug("changePassword() method has been started.");
			User user = userDAO.find(username);
			if (user != null && codecHandler.matches(oldPassword, user.getPassword())) {
				String encryptedPassword = codecHandler.encode(newPassword);
				userDAO.changePassword(username, encryptedPassword);
				logger.debug("changePassword() method has been finished.");
			} else {
				throw new SystemException(ErrorCode.OLD_PASSWORD_DOES_NOT_MATCH, "Old password does not match.");
			}
		} catch (DAOException e) {
			logger.error("changePassword() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to change passowrd", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void resetPassword(String username) throws SystemException {
		try {
			logger.debug("resetPassword() method has been started.");
			//String encryptedPassword = codecHandler.encode(User.DEFAULT_PASSWORD);
			String encryptedPassword = codecHandler.encode(username);
			userDAO.resetPassword(username, encryptedPassword);
			logger.debug("resetPassword() method has been finished.");
		} catch (DAOException e) {
			logger.error("resetPassword() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to reset passowrd", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean authenticate(String username, String password)
			throws SystemException {
		try {
			logger.debug("authenticate() method has been started.");
			User user = userDAO.find(username);
			if (user != null) {
				return codecHandler.matches(password, user.getPassword());
			}
			logger.debug("authenticate() method has been finished.");
		} catch (DAOException e) {
			logger.error("authenticate() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to change passowrd", e);
		}
		return false;
	}
	
	/**
	 * Created By	: Aye Chan Thar Soe
	 * Created Date	: 2018/09/04
	 * Explanation	: Find user information by user id.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserInfoListByUserId(int year, int month,User user)
			throws SystemException {
		logger.debug("findAllUserByYearAndMonth() method has been started.");
		 List<Object[]> result = userDAO.findUserInfoListByUserId(year,month,user);
		logger.debug("findAllUserByYearAndMonth() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTeamInfoList(User user)
			throws SystemException {
		logger.debug("findAllUserByYearAndMonth() method has been started.");
		 List<Object[]> result = userDAO.findTeamInfoList(user);
		logger.debug("findAllUserByYearAndMonth() method has been finished.");
		return result;
	}
}