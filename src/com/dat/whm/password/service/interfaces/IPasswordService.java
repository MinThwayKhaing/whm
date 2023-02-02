/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.password.service.interfaces;

import com.dat.whm.exception.SystemException;
import com.dat.whm.password.entity.Password;

public interface IPasswordService {

	public Password addNewPassword(Password password) throws SystemException;

	public Password updatePassword(Password password) throws SystemException;

	public void deletePassword(Password password) throws SystemException;
}