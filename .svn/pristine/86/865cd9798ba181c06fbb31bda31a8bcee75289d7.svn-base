/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.project.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.persistence.interfaces.IProjectDAO;
import com.dat.whm.project.service.interfaces.IProjectService;
import com.dat.whm.web.common.SearchDate;

@Service("ProjectService")
public class ProjectService implements IProjectService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "ProjectDAO")
	private IProjectDAO projectDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public Project addNewProject(Project project) throws SystemException {
		try {
			logger.debug("addProject() method has been started.");
			project = projectDAO.insert(project);
			logger.debug("addProject() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewProject() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Project", e);
		}
		return project;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Project updateProject(Project project) throws SystemException {
		try {
			logger.debug("updateProject() method has been started.");
			project = projectDAO.update(project);
			logger.debug("updateProject() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateProject() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to update Project", e);
		}
		return project;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProject(Project project) throws SystemException {
		try {
			logger.debug("deleteProject() method has been started.");
			projectDAO.delete(project);
			logger.debug("deleteProject() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteProject() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to delete Project", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Project> findAllProject() throws SystemException {
		logger.debug("findAllProject() method has been started.");
		List<Project> result = projectDAO.findAll();
		logger.debug("findAllProject() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Project findProject(String id) throws SystemException {
		logger.debug("findProject() method has been started.");
		Project project = projectDAO.findById(id);
		logger.debug("findProject() method has been finished.");
		return project;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Project findProjectByName(String name) throws SystemException {
		logger.debug("findProjectByName() method has been started.");
		Project project = projectDAO.findByName(name);
		logger.debug("findProjectByName() method has been finished.");
		return project;
	}

	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/19
	 * Explanation  : Get start date form project table.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findStartDateByProjectId(String projectId) throws DAOException {
		logger.debug("findStartDateByProjectId() method has been started.");
		Date startDate = projectDAO.findStartDateByProjectId(projectId);
		logger.debug("findStartDateByProjectId() method has been finished.");
		return startDate;
	}

	/**
	 * Created By   : Aye Myat Mon
	 * Created Date : 2018/04/30
	 * Explanation  : find client organization form project table.
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findClientOrganization() throws DAOException{
		logger.debug("findClientOrganization() method has been started.");
		List<String> result = projectDAO.findClientOrganization();
		logger.debug("findClientOrganization() method has been finished.");
		return result;
	}

	
}