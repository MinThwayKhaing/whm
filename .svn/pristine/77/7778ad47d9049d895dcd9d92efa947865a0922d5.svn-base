/***************************************************************************************
 * @author Aye Thida Phyo
 * @Date 2017-09-14
 * @Version 1.0
 * @Purpose Created for PhaseIII : Team Management
 *
 ***************************************************************************************/
package com.dat.whm.userteamauthority.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.team.entity.Team;
import com.dat.whm.userteamauthority.entity.UserTeamAuthority;
import com.dat.whm.userteamauthority.persistence.interfaces.IUserTeamAuthorityDAO;
import com.dat.whm.userteamauthority.service.interfaces.IUserTeamAuthorityService;

@Service("UserTeamAuthorityService")
public class UserTeamAuthorityService implements IUserTeamAuthorityService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "UserTeamAuthorityDAO")
	private IUserTeamAuthorityDAO userteamauthorityDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserTeamAuthority> findByTeamID(Team team) throws SystemException {
		List<UserTeamAuthority> result = null;
		try {
			logger.debug("findByTeamName() method has been started.");
			result = userteamauthorityDAO.findByTeamID(team);
			logger.debug("findByTeamName() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByTeamName() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search Team by teamName", e);
		}
		return result;
	}
}