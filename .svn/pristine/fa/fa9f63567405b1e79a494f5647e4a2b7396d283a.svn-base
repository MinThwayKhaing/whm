package com.dat.whm.userteammainlybelonghistory.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.user.entity.User;
import com.dat.whm.userteammainlybelonghistory.entity.UserTeamMainlyBelongHistory;
import com.dat.whm.userteammainlybelonghistory.persistence.interfaces.IUserTeamMainlyBelongHistoryDAO;

@Repository("UserTeamMainlyBelongHistoryDAO")
public class UserTeamMainlyBelongHistoryDAO extends BasicDAO implements IUserTeamMainlyBelongHistoryDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertMainTeamHistory(User user){
		try{
			logger.debug("insertMainTeamHistory() method has been started.");
			UserTeamMainlyBelongHistory mainTeamHistory = new UserTeamMainlyBelongHistory();
			mainTeamHistory.setUser(user);
			mainTeamHistory.setTeam(user.getMainlyBelongTeam());
			mainTeamHistory.setStartDate(new Date());
			em.persist(mainTeamHistory);
			em.flush();
			logger.debug("insertMainTeamHistory() method has been successfully finished.");
		}catch (PersistenceException pe){
			logger.error("insertMainTeamHistory() method has been failed.",pe);
			throw translate("Fail to update", pe);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserTeamMainlyBelongHistory> findByIds(String userId) {
		List<UserTeamMainlyBelongHistory> result = null;
		try {
			logger.debug("findById() method has been started.");
			
			Query q = em.createNamedQuery("UserTeamMainlyBelongHistory.findByIds");
			q.setParameter("userID", userId);
			result = q.getResultList();
			em.flush();
			
			logger.debug("findById() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findById() method has been failed.", pe);
			throw translate("Failed to find",pe);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserTeamMainlyBelongHistory> findByPeriod(String userId,Date reportDate) {
		List<UserTeamMainlyBelongHistory> result = null;
		try {
			logger.debug("findByPeriod() method has been started.");
			
			Query q = em.createNamedQuery("UserTeamMainlyBelongHistory.findByPeriod");
			q.setParameter("userID", userId);
			q.setParameter("reportDate", reportDate);
			result = q.getResultList();
			em.flush();
			
			logger.debug("findByPeriod() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByPeriod() method has been failed.", pe);
			throw translate("Failed to find",pe);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEndDate(UserTeamMainlyBelongHistory mainTeamHistory){
		try{
			logger.debug("updateEndDate() method has been started.");
			Query q = em.createNamedQuery("UserTeamMainlyBelongHistory.updateEndDate");
			q.setParameter("endDate", mainTeamHistory.getEndDate());
			q.setParameter("userID", mainTeamHistory.getUser().getId());
			q.setParameter("teamID", mainTeamHistory.getTeam().getId());
			q.executeUpdate();
			logger.debug("updateEndDate() method has been successfully finished.");
		}catch (PersistenceException pe){
			logger.error("updateEndDate() method has been failed.",pe);
			throw translate("Fail to update", pe);
		}
	}
}
