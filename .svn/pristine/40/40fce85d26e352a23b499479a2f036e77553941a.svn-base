/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-19
 * @Version 1.0
 * @Purpose Service class for project information.
 *
 ***************************************************************************************/
package com.dat.whm.projectinformation.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.project.entity.Project;
import com.dat.whm.projectinformation.persistence.interfaces.IProjectInformationDAO;
import com.dat.whm.projectinformation.service.interfaces.IProjectInformationService;

@Service("ProjectInformationService")
public class ProjectInformationService implements IProjectInformationService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "ProjectInformationDAO")
	private IProjectInformationDAO projectInformationDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProjectInformation(Project project) throws SystemException {
		
		try {
			logger.debug("addNewProjectInformation() method has been started.");
			projectInformationDAO.insert(project);
			logger.debug("addNewProjectInformation() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewProjectInformation() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Project", e);
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProjectInformation(Project project) throws SystemException {
		try {
			logger.debug("updateProjectInformation() method has been started.");
			projectInformationDAO.update(project);
			logger.debug("updateProjectInformation() method has been successfully finisehd.");
		} catch (SystemException e) {
			logger.error("updateProjectInformation() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Project", e);
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProjectInformation(Project project)throws SystemException {
		try {
			logger.debug("deleteProjectInformation() method has been started.");
			projectInformationDAO.delete(project);
			logger.debug("deleteProjectInformation() method has been successfully finisehd.");
		} catch (SystemException e) {
			logger.error("deleteProjectInformation() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Project", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void clearProjectInformation(Project project) throws SystemException {
		try {
			logger.debug("clearProjectInformation() method has been started.");
			projectInformationDAO.clear(project);
			logger.debug("clearProjectInformation() method has been successfully finisehd.");
		} catch (SystemException e) {
			logger.error("clearProjectInformation() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Project", e);
		}
	}

}
