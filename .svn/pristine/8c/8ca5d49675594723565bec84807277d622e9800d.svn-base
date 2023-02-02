/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose DAO class for project deliverable.
 *
 ***************************************************************************************/
package com.dat.whm.projectdeliverable.persistence;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.projectdeliverable.entity.ProjectDeliverable;
import com.dat.whm.projectdeliverable.persistence.interfaces.IProjectDeliverableDAO;

@Repository("ProjectDeliverableDAO")
public class ProjectDeliverableDAO extends BasicDAO implements IProjectDeliverableDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public ProjectDeliverable insert(ProjectDeliverable projectDeliverables)throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(projectDeliverables);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Project(ProjectName = "+ projectDeliverables.getId() + ")", pe);
		}
		return projectDeliverables;
	}

}
