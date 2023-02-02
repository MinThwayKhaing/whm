/***************************************************************************************
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-12
 * @Purpose Interface for UserTeamHistoryService.
 ***************************************************************************************/
package com.dat.whm.userteamhistory.service.interfaces;

import com.dat.whm.exception.SystemException;
import com.dat.whm.userteamhistory.entity.UserTeamHistory;

public interface IUserTeamHistoryService {
	public UserTeamHistory addRecord(UserTeamHistory userTeamHistory) throws SystemException;
}
