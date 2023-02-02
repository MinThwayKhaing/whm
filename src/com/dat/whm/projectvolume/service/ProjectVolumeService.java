/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Service class for project volume.
 *
 ***************************************************************************************/
package com.dat.whm.projectvolume.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.projectvolume.entity.ProjectVolume;
import com.dat.whm.projectvolume.persistence.interfaces.IProjectVolumeDAO;
import com.dat.whm.projectvolume.service.interfaces.IProjectVolumeService;

@Service("ProjectVolumnService")
public class ProjectVolumeService implements IProjectVolumeService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "ProjectVolumnDAO")
	private IProjectVolumeDAO projectVolumnDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public ProjectVolume addNewProjectVolumn(ProjectVolume projectVolumn)throws SystemException {
		try {
			logger.debug("addNewProjectVolumn() method has been started.");
			projectVolumn = projectVolumnDAO.insert(projectVolumn);
			logger.debug("addNewProjectVolumn() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewProjectVolumn() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a ProjectVolumn", e);
		}
		return projectVolumn;
	}

}
