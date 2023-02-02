/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Service class for project deliverable.
 *
 ***************************************************************************************/
package com.dat.whm.projectdeliverable.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.projectdeliverable.entity.ProjectDeliverable;
import com.dat.whm.projectdeliverable.persistence.interfaces.IProjectDeliverableDAO;
import com.dat.whm.projectdeliverable.service.interfaces.IProjectDeliverableService;

@Service("ProjectDeliverablesService")
public class ProjectDeliverablesService implements IProjectDeliverableService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "ProjectDeliverableDAO")
	private IProjectDeliverableDAO projectDeliverableDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public ProjectDeliverable addNewProjectDeliverable(ProjectDeliverable projectDeliverable) throws SystemException {
		try {
			logger.debug("addNewProjectControl() method has been started.");
			projectDeliverable = projectDeliverableDAO.insert(projectDeliverable);
			logger.debug("addProject() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewProjectControl() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Project", e);
		}
		return projectDeliverable;
	}

}
