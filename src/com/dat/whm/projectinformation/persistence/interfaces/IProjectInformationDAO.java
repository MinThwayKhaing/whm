/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-19
 * @Version 1.0
 * @Purpose DAO interface for project information.
 *
 ***************************************************************************************/
package com.dat.whm.projectinformation.persistence.interfaces;

import com.dat.whm.exception.DAOException;
import com.dat.whm.project.entity.Project;

public interface IProjectInformationDAO {
	public void insert(Project project) throws DAOException;
	
	public void update(Project project) throws DAOException;
	
	public void delete(Project project) throws DAOException;
	
	public void clear(Project project) throws DAOException;
}
