/***************************************************************************************
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-12
 * @Purpose Interface for UserTeamHistoryDAO.
 ***************************************************************************************/
package com.dat.whm.userteamhistory.persistence.interfaces;

import com.dat.whm.exception.SystemException;
import com.dat.whm.userteamhistory.entity.UserTeamHistory;

public interface IUserTeamHistoryDAO {
	public UserTeamHistory insert(UserTeamHistory userTeamHistory) throws SystemException;
}
