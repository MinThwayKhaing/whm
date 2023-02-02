/***************************************************************************************
 * @author Aye Thida Phyo
 * @Date 2017-09-14
 * @Version 1.0
 * @Purpose Created for PhaseIII : Team Management
 *
 ***************************************************************************************/
package com.dat.whm.userteamauthority.persistence;
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
import com.dat.whm.userteamauthority.entity.UserTeamAuthority;
import com.dat.whm.userteamauthority.persistence.interfaces.IUserTeamAuthorityDAO;

@Repository("UserTeamAuthorityDAO")
public class UserTeamAuthorityDAO extends BasicDAO implements IUserTeamAuthorityDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserTeamAuthority> findByTeamID(Team team) throws DAOException {
		List<UserTeamAuthority> result = null;
		try {
			logger.debug("findActivityByReportID() method has been started.");
			Query q = em.createNamedQuery("UserTeamAuthority.findByTeamID");
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