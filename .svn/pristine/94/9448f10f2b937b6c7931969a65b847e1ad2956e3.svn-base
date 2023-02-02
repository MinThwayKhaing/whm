/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Service class for project control.
 *
 ***************************************************************************************/
package com.dat.whm.projectcontrol.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.projectcontrol.entity.ProjectControl;
import com.dat.whm.projectcontrol.persistence.interfaces.IProjectControlDAO;
import com.dat.whm.projectcontrol.service.interfaces.IProjectControlService;

@Service("ProjectControlService")
public class ProjectControlService implements IProjectControlService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "ProjectControlDAO")
	private IProjectControlDAO projectControlDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public ProjectControl addNewProjectControl(ProjectControl projectControl)throws SystemException {
		try {
			logger.debug("addNewProjectControl() method has been started.");
			projectControl = projectControlDAO.insert(projectControl);
			logger.debug("addProject() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewProjectControl() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Project", e);
		}
		return projectControl;
	}

}
