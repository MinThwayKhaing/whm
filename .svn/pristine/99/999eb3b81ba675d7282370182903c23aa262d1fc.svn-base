/***************************************************************************************
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-12
 * @Purpose DAO class for UserTeamHistory.
 ***************************************************************************************/
package com.dat.whm.userteamhistory.persistence;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.userteamhistory.entity.UserTeamHistory;
import com.dat.whm.userteamhistory.persistence.interfaces.IUserTeamHistoryDAO;

@Repository("UserTeamHistoryDAO")
public class UserTeamHistoryDAO extends BasicDAO implements IUserTeamHistoryDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public UserTeamHistory insert(UserTeamHistory userTeamHistory) throws DAOException{
		try {
			logger.debug("insert() method has been started.");
			em.persist(userTeamHistory);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to record for user team history. ", pe);
		}
		return userTeamHistory;
	}

}
