/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.user.persistence.interfaces;

import java.util.List;

import com.dat.whm.exception.DAOException;
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;

public interface IUserDAO {
	public User insert(User user) throws DAOException;

	public void changePassword(String username, String newPassword)throws DAOException;

	public void resetPassword(String user, String defaultPassword)throws DAOException;

	public User update(User user) throws DAOException;

	public void delete(User user) throws DAOException;

	public User find(String username) throws DAOException;

	public List<User> findByRole(Role role) throws DAOException;

	public User findById(String id) throws DAOException;

	public List<User> findAll() throws DAOException;
	
	/**
	 * Created By	: Aye Chan Thar Soe
	 * Created Date	: 2018/09/04
	 * Explanation	: Find all user list by year and month.
	 * */
	public List<Object[]> findUserInfoListByUserId(int year,int month, User user) throws DAOException;
	
	public List<Object[]> findTeamInfoList(User user) throws DAOException;
}
