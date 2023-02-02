/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose DAO class for project volume.
 *
 ***************************************************************************************/
package com.dat.whm.projectvolume.persistence;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.projectvolume.entity.ProjectVolume;
import com.dat.whm.projectvolume.persistence.interfaces.IProjectVolumeDAO;

@Repository("ProjectVolumnDAO")
public class ProjectVolumeDAO extends BasicDAO implements IProjectVolumeDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Transactional(propagation = Propagation.REQUIRED)
	public ProjectVolume insert(ProjectVolume projectVolumn)throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(projectVolumn);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Project(ProjectName = "+ projectVolumn.getId() + ")", pe);
		}
		return projectVolumn;
	}

}
