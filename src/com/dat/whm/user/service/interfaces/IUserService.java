/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.user.service.interfaces;

import java.util.List;

import com.dat.whm.exception.SystemException;
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;

public interface IUserService {
	public boolean authenticate(String username, String password) throws SystemException;

	public User addNewUser(User user) throws SystemException;

	public void changePassword(String username, String newPassword, String oldPassword) throws SystemException;

	public void resetPassword(String user) throws SystemException;

	public User updateUser(User user) throws SystemException;

	public void deleteUser(User user) throws SystemException;

	public User findUser(String username) throws SystemException;

	public List<User> findAllUser() throws SystemException;

	public List<User> findUserByRole(Role role) throws SystemException;

	public User findById(String id) throws SystemException;
	
	/**
	 * Created By	: Aye Chan Thar Soe
	 * Created Date	: 2018/09/04
	 * Explanation	: Find all user list by year and month.
	 * */
	public List<Object[]> findUserInfoListByUserId(int year,int month, User user) throws SystemException;
	
	public List<Object[]> findTeamInfoList(User user) throws SystemException;
}