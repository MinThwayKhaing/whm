/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose DAO class for project control.
 *
 ***************************************************************************************/
package com.dat.whm.projectcontrol.persistence;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.projectcontrol.entity.ProjectControl;
import com.dat.whm.projectcontrol.persistence.interfaces.IProjectControlDAO;

@Repository("ProjectControlDAO")
public class ProjectControlDAO extends BasicDAO implements IProjectControlDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Transactional(propagation = Propagation.REQUIRED)
	public ProjectControl insert(ProjectControl projectControl)throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(projectControl);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Project(ProjectName = "
					+ projectControl.getId() + ")", pe);
		}
		return null;
	}

}
