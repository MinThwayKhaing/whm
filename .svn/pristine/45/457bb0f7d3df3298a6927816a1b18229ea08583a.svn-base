/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.project.service.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.project.entity.Project;

public interface IProjectService {

	public Project addNewProject(Project project) throws SystemException;

	public Project updateProject(Project project) throws SystemException;

	public void deleteProject(Project project) throws SystemException;

	public List<Project> findAllProject() throws SystemException;

	public Project findProject(String id) throws SystemException;

	public Project findProjectByName(String name) throws SystemException;
	
	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/12
	 * Explanation  : Get start date form project table.
	 */
	public Date findStartDateByProjectId(String projectId) throws DAOException; 
	
	
	/**
	 * Created By   : Aye Myat Mon
	 * Created Date : 2018/04/30
	 * Explanation  : find client organization form project table.
	 */
	public List<String> findClientOrganization() throws DAOException;

}