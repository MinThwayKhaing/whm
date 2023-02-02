package com.dat.whm.userteammainlybelonghistory.persistence.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.exception.DAOException;
import com.dat.whm.user.entity.User;
import com.dat.whm.userteammainlybelonghistory.entity.UserTeamMainlyBelongHistory;

public interface IUserTeamMainlyBelongHistoryDAO {
	public List<UserTeamMainlyBelongHistory> findByIds(String userId) throws DAOException;
	
    public List<UserTeamMainlyBelongHistory> findByPeriod(String userId,Date reportDate) throws DAOException;
	public void updateEndDate(UserTeamMainlyBelongHistory mainTeamHistory) throws DAOException;
	
	public void insertMainTeamHistory(User user);
}
