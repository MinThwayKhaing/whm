/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.project.persistence.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.exception.DAOException;
import com.dat.whm.project.entity.Project;
import com.dat.whm.web.common.SearchDate;

public interface IProjectDAO {
	public Project insert(Project project) throws DAOException;

	public Project update(Project project) throws DAOException;

	public void delete(Project project) throws DAOException;

	public List<Project> findAll() throws DAOException;

	public Project findById(String id) throws DAOException;

	public Project findByName(String name) throws DAOException;
	
	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/19
	 * Explanation  : Get year,month form project table.
	 */
	public Date findStartDateByProjectId(String projectId) throws DAOException;
	
	/**
	 * Created By   : Aye Myat Mon
	 * Created Date : 2018/04/30
	 * Explanation  : find client organization form project table.
	 */
	public List<String> findClientOrganization() throws DAOException;

}