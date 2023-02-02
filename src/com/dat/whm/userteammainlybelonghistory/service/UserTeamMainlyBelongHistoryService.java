package com.dat.whm.userteammainlybelonghistory.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.user.entity.User;
import com.dat.whm.userteammainlybelonghistory.entity.UserTeamMainlyBelongHistory;
import com.dat.whm.userteammainlybelonghistory.persistence.interfaces.IUserTeamMainlyBelongHistoryDAO;
import com.dat.whm.userteammainlybelonghistory.service.interfaces.IUserTeamMainlyBelongHistoryService;

@Service("UserTeamMainlyBelongHistoryService")
public class UserTeamMainlyBelongHistoryService implements IUserTeamMainlyBelongHistoryService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "UserTeamMainlyBelongHistoryDAO")
	private IUserTeamMainlyBelongHistoryDAO mainTeamHistoryDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertMainTeamHistory(User user)
	{
		try {
			logger.debug("addProject() method has been started.");
			mainTeamHistoryDAO.insertMainTeamHistory(user);;
			logger.debug("addProject() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewProject() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Project", e);
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserTeamMainlyBelongHistory> findByIds(String userId)
	{
		List<UserTeamMainlyBelongHistory> teamHistory = null;
		try {
			logger.debug("findByIds() method has been started.");
			teamHistory = mainTeamHistoryDAO.findByIds(userId);
			logger.debug("findByIds() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("findByIds() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find user", e);
		}
		return teamHistory;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserTeamMainlyBelongHistory> findByPeriod(String userId,Date reportDate)
	{
		List<UserTeamMainlyBelongHistory> teamHistory = null;
		try {
			logger.debug("findByPeriod() method has been started.");
			teamHistory = mainTeamHistoryDAO.findByPeriod(userId,reportDate);
			logger.debug("findByPeriod() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("findByPeriod() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to find user", e);
		}
		return teamHistory;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEndDate(UserTeamMainlyBelongHistory mainTeamHistory)
	{
		
		try {
			logger.debug("updateEndDate() method has been started.");
			mainTeamHistoryDAO.updateEndDate(mainTeamHistory);
			logger.debug("updateEndDate() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateEndDate() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Project", e);
		}
		
	}
}
