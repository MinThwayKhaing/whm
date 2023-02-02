/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Service class for risk factors.
 *
 ***************************************************************************************/
package com.dat.whm.riskfactors.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.riskfactors.entity.RiskFactors;
import com.dat.whm.riskfactors.persistence.interfaces.IRiskFactorsDAO;
import com.dat.whm.riskfactors.service.interfaces.IRiskFactorsService;

@Service("RiskFactorsService")
public class RiskFactorsService implements IRiskFactorsService{
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "RiskFactorsDAO")
	private IRiskFactorsDAO riskFactorsDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public RiskFactors addNewRiskFactors(RiskFactors riskFactors)throws SystemException {
		try {
			logger.debug("addNewRiskFactors() method has been started.");
			riskFactors = riskFactorsDAO.insert(riskFactors);
			logger.debug("addNewRiskFactors() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewRiskFactors() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a ProjectVolumn", e);
		}
		return riskFactors;
	}

}
