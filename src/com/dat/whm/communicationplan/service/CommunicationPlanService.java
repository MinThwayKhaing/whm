/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Service class for communication plan.
 *
 ***************************************************************************************/
package com.dat.whm.communicationplan.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dat.whm.communicationplan.entity.CommunicationPlan;
import com.dat.whm.communicationplan.persistence.interfaces.ICommunicationPlanDAO;
import com.dat.whm.communicationplan.service.interfaces.ICommunicationPlanService;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;

@Service("CommunicationPlanService")
public class CommunicationPlanService implements ICommunicationPlanService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "CommunicationPlanDAO")
	private ICommunicationPlanDAO communicationPlanDAO;
	
	public CommunicationPlan addNewCommunicationPlan(CommunicationPlan communicationPlan) throws SystemException {
		try {
			logger.debug("addNewCommunicationPlan() method has been started.");
			communicationPlan = communicationPlanDAO.insert(communicationPlan);
			logger.debug("addNewCommunicationPlan() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewCommunicationPlan() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Approval", e);
		}
		return communicationPlan;
	}

}
