/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Interface for risk factors DAO.
 *
 ***************************************************************************************/
package com.dat.whm.riskfactors.persistence.interfaces;

import com.dat.whm.exception.DAOException;
import com.dat.whm.riskfactors.entity.RiskFactors;

public interface IRiskFactorsDAO {
	public RiskFactors insert(RiskFactors riskFactors) throws DAOException;
}
