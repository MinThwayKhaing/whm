/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose DAO class for risk factors.
 *
 ***************************************************************************************/
package com.dat.whm.riskfactors.persistence;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.riskfactors.entity.RiskFactors;
import com.dat.whm.riskfactors.persistence.interfaces.IRiskFactorsDAO;

@Repository("RiskFactorsDAO")
public class RiskFactorsDAO extends BasicDAO implements IRiskFactorsDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Transactional(propagation = Propagation.REQUIRED)
	public RiskFactors insert(RiskFactors riskFactors) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(riskFactors);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Project(ProjectName = "+ riskFactors.getId() + ")", pe);
		}
		return riskFactors;
	}

}
