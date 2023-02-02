/***************************************************************************************
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-12
 * @Purpose Service class for UserTeamHistory.
 ***************************************************************************************/
package com.dat.whm.userteamhistory.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.user.persistence.interfaces.IUserDAO;
import com.dat.whm.userteamhistory.entity.UserTeamHistory;
import com.dat.whm.userteamhistory.persistence.interfaces.IUserTeamHistoryDAO;
import com.dat.whm.userteamhistory.service.interfaces.IUserTeamHistoryService;

@Service("UserTeamHistoryService")
public class UserTeamHistoryService implements IUserTeamHistoryService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name = "UserTeamHistoryDAO")
	private IUserTeamHistoryDAO userTeamHistoryDAO;
	
	@Override
	public UserTeamHistory addRecord(UserTeamHistory userTeamHistory) throws SystemException {
		try {
			logger.debug("insert() method has been started.");
			userTeamHistory = userTeamHistoryDAO.insert(userTeamHistory);
			logger.debug("insert() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("insert() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to update user team history. ", e);
		}
		return userTeamHistory;
	}

}
