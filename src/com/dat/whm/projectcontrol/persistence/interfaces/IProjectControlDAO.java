/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Interface for project control DAO.
 *
 ***************************************************************************************/
package com.dat.whm.projectcontrol.persistence.interfaces;

import com.dat.whm.exception.DAOException;
import com.dat.whm.projectcontrol.entity.ProjectControl;

public interface IProjectControlDAO {
	public ProjectControl insert(ProjectControl projectControl) throws DAOException;
}
