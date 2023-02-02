/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Interface for risk factors Service.
 *
 ***************************************************************************************/
package com.dat.whm.riskfactors.service.interfaces;

import com.dat.whm.exception.SystemException;
import com.dat.whm.riskfactors.entity.RiskFactors;

public interface IRiskFactorsService {
	public RiskFactors addNewRiskFactors(RiskFactors riskFactors) throws SystemException;
}
