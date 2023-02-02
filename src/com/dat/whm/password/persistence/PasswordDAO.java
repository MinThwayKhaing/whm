/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.password.persistence;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.password.persistence.interfaces.IPasswordDAO;
import com.dat.whm.password.entity.Password;
import com.dat.whm.persistence.BasicDAO;

@Repository("PasswordDAO")
public class PasswordDAO extends BasicDAO implements IPasswordDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public Password insert(Password password) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(password);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Password(Id = "
					+ password.getId() + ")", pe);
		}
		return password;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Password update(Password password) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			password = em.merge(password);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update Password(Id = "
					+ password.getId() + ")", pe);
		}
		return password;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Password password) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			password = em.merge(password);
			em.remove(password);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete Password(Id = "
					+ password.getId() + ")", pe);
		}
	}
}