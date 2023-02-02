/***************************************************************************************
 * @author Aye Myat Mon
 * @Date 2018-02-05
 * @Version 1.0
 * @Purpose Created for HR Overhead Cost Management function.
 ***************************************************************************************/
package com.dat.whm.hrcost.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.hrcost.entity.HRCost;
import com.dat.whm.hrcost.persistence.interfaces.IHRCostDAO;
import com.dat.whm.hrcost.service.interfaces.IHRCostService;

@Service("HRCostService")
public class HRCostService implements IHRCostService {
	private Logger logg = Logger.getLogger(this.getClass());

	@Resource(name = "HRCostDAO")
	private IHRCostDAO hrCostDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HRCost> findAllHRCostS() {
		List<HRCost> allHRCost = null;
		try {
			logg.debug("findAllHRCostS() method has stated");
			allHRCost = hrCostDAO.findAllHRCost();
			logg.debug("findAllHRCostS() method has finished");
		} catch (DAOException e) {
			logg.error("findAllHRCostS() has error");
			throw new SystemException(e.getErrorCode(),
					"Failed to find all record");
		}
		return allHRCost;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<HRCost> findByYearS(String year, DeleteDiv delDiv) {
		List<HRCost> result = null;
		try {
			result = hrCostDAO.findByYearD(year, delDiv);
			logg.debug("findByYearS() method has finished successfully");
		} catch (DAOException e) {
			logg.error("findByDateS() method has error");
			throw new SystemException(e.getErrorCode(),
					"Faield to search HRCost by date", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public HRCost findByHRCostS(double cost) {
		HRCost hrcost;
		try {
			hrcost = hrCostDAO.findByHRCost(cost);
			logg.debug("findByHRCostS() method has finished");
		} catch (DAOException e) {
			logg.error("findByHRCostS() method has error");
			throw new SystemException(e.getErrorCode(),
					"Failed to find HR Cost", e);
		}
		return hrcost;

	}

	/*@Transactional(propagation = Propagation.REQUIRED)
	public HRCost findByIdS(int id) {
		HRCost hrcost;
		try {
			hrcost = hrCostDAO.findByIdD(id);
			logg.debug("findByIdS() method has finished");
		} catch (DAOException e) {
			logg.error("findByIdS() method has error");
			throw new SystemException(e.getErrorCode(),
					"Failed to find HR Cost by ID", e);
		}
		return hrcost;

	}*/

	@Transactional(propagation = Propagation.REQUIRED)
	public HRCost updateHRCostS(HRCost hrcost) {
		try {
			hrcost = hrCostDAO.updateHRCostD(hrcost);
			logg.debug("updatHRCostS() method has finished");
		} catch (DAOException e) {
			logg.error("updateHRCostS() method has error");
			throw new SystemException(e.getErrorCode(),
					"Failed to update HR Cost", e);
		}
		return hrcost;
	}

	/*@Transactional(propagation = Propagation.REQUIRED)
	public HRCost updateByYearS(HRCost hrcost) {
		try {
			hrcost=hrCostDAO.updateByYearD(hrcost);
			logg.debug("updateByYearD() method has finished");
		} catch (DAOException e) {
			logg.error("updateByYearD() method has error");
			throw new SystemException(e.getErrorCode(),
					"Failed to update HR Cost by year ", e);
		}
		return hrcost;
	}*/

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteHRCostS(HRCost hrcost) {
		try {
			hrCostDAO.deleteHRCostD(hrcost);
			logg.debug("deleteHRCostS() method has finished successfully");
		} catch (DAOException e) {
			logg.error("deleteHRCostS() has error");
			throw new SystemException(e.getErrorCode(),
					"Failed to delete HR Cost");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public HRCost insertHRCostS(HRCost hrcost) {
		try {
			hrcost = hrCostDAO.insertHRCostD(hrcost);
			logg.debug("insertHRCostS() method has finished");
		} catch (DAOException e) {
			logg.error("insertHRCostS() method has error");
			throw new SystemException(e.getErrorCode(),
					"Failed to insert HR Cost", e);
		}
		return hrcost;
	}
}
