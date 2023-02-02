/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose DAO class for communication plan.
 *
 ***************************************************************************************/
package com.dat.whm.communicationplan.persistence;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.communicationplan.entity.CommunicationPlan;
import com.dat.whm.communicationplan.persistence.interfaces.ICommunicationPlanDAO;
import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;

@Repository("CommunicationPlanDAO")
public class CommunicationPlanDAO extends BasicDAO implements ICommunicationPlanDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Transactional(propagation = Propagation.REQUIRED)
	public CommunicationPlan insert(CommunicationPlan communicationPlan) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(communicationPlan);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert CommunicationPlan(Id = "+ communicationPlan.getId() + ")", pe);
		}
		return communicationPlan;
	}

}
