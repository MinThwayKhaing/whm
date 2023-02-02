/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.password.persistence.interfaces;

import com.dat.whm.exception.DAOException;
import com.dat.whm.password.entity.Password;

public interface IPasswordDAO {
	public Password insert(Password password) throws DAOException;

	public Password update(Password password) throws DAOException;

	public void delete(Password password) throws DAOException;
}