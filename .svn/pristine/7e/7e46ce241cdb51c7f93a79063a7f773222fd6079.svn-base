/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.password.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.password.persistence.interfaces.IPasswordDAO;
import com.dat.whm.password.service.interfaces.IPasswordService;
import com.dat.whm.password.entity.Password;

@Service("PasswordService")
public class PasswordService implements IPasswordService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "PasswordDAO")
	private IPasswordDAO PasswordDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public Password addNewPassword(Password password) throws SystemException {
		try {
			logger.debug("addPassword() method has been started.");
			password = PasswordDAO.insert(password);
			logger.debug("addPassword() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewPassword() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Password", e);
		}
		return password;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Password updatePassword(Password password) throws SystemException {
		try {
			logger.debug("updatePassword() method has been started.");
			password = PasswordDAO.update(password);
			logger.debug("updatePassword() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updatePassword() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to update Password", e);
		}
		return password;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePassword(Password password) throws SystemException {
		try {
			logger.debug("deletePassword() method has been started.");
			PasswordDAO.delete(password);
			logger.debug("deletePassword() method has been finished.");
		} catch (DAOException e) {
			logger.error("deletePassword() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to delete Password", e);
		}
	}
}