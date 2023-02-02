/***************************************************************************************
 * @author Aye Thida Phyo
 * @Date 2017-09-14
 * @Version 1.0
 * @Purpose Created for PhaseIII : Team Management
 *
 ***************************************************************************************/
package com.dat.whm.userteam.service.interfaces;

import java.util.List;

import com.dat.whm.exception.SystemException;
import com.dat.whm.team.entity.Team;
import com.dat.whm.userteam.entity.UserTeam;

public interface IUserTeamService {
	
	public List<UserTeam> findByTeamID(Team team) throws SystemException;
}