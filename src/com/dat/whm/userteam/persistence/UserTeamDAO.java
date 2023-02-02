/***************************************************************************************
 * @author Aye Thida Phyo
 * @Date 2017-09-14
 * @Version 1.0
 * @Purpose Created for PhaseIII : Team Management
 *
 ***************************************************************************************/
package com.dat.whm.userteam.persistence;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.team.entity.Team;
import com.dat.whm.userteam.entity.UserTeam;
import com.dat.whm.userteam.persistence.interfaces.IUserTeamDAO;

@Repository("UserTeamDAO")
public class UserTeamDAO extends BasicDAO implements IUserTeamDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserTeam> findByTeamID(Team team) throws DAOException {
		List<UserTeam> result = null;
		try {
			logger.debug("findActivityByReportID() method has been started.");
			Query q = em.createNamedQuery("UserTeam.findByTeamID");
			q.setParameter("team_id", team.getId());
			result = q.getResultList();
			em.flush();
			logger.debug("findActivityByReportID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findActivityByReportID() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}
}