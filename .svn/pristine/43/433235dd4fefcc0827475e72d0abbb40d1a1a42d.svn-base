package com.dat.whm.userteammainlybelonghistory.service.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.exception.SystemException;
import com.dat.whm.user.entity.User;
import com.dat.whm.userteammainlybelonghistory.entity.UserTeamMainlyBelongHistory;

public interface IUserTeamMainlyBelongHistoryService {
	public List<UserTeamMainlyBelongHistory> findByIds(String userId) throws SystemException;
	
	public List<UserTeamMainlyBelongHistory> findByPeriod(String userId,Date reportDate) throws SystemException;
	public void updateEndDate(UserTeamMainlyBelongHistory mainTeamHistory) throws SystemException;
	
	public void insertMainTeamHistory(User user) throws SystemException;
}
