/***************************************************************************************
 * @author Aye Myat Mon
 * @Date 2018-02-02
 * @Version 1.0
 * @Purpose Created for HR Overhead Cost Management function.
 ***************************************************************************************/
package com.dat.whm.hrcost.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.hrcost.entity.HRCost;
import com.dat.whm.hrcost.persistence.interfaces.IHRCostDAO;
import com.dat.whm.persistence.BasicDAO;

@Repository("HRCostDAO")
public class HRCostDAO extends BasicDAO implements IHRCostDAO {
	private Logger log = Logger.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<HRCost> findAllHRCost() {
		List<HRCost> allResult = null;
		try {
			log.debug("HR Cost findAllHRCostD() method has started");
			Query q = em.createNamedQuery("HRCost.findAll");
			allResult = q.getResultList();
			em.flush();
			log.debug("findAllHRCostD() method finished");
		} catch (PersistenceException pe) {
			log.error("findAllHRCostD() method has been failed.", pe);
			throw translate("Failed to search HR Cost ", pe);
		}

		return allResult;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<HRCost> findByYearD(String year, DeleteDiv delDiv) {
		List<HRCost> result = null;
		try {
			log.debug("HR Cost findByYearD() method has started");
			Query q = em.createNamedQuery("HRCost.findByYear");
			q.setParameter("year", year);
			q.setParameter("delDiv", delDiv);
			result = q.getResultList();
			em.flush();
			log.debug("HR Cost findByYearD() method has started");
		} catch (PersistenceException pe) {
			throw translate("Failed to search HR Cost By year", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public HRCost findByHRCost(double cost) {
		HRCost result = null;
		try {
			Query q = em.createNamedQuery("HRCost.findHRCost");
			q.setParameter("hr_cost", cost);
			result = (HRCost) q.getSingleResult();
			em.flush();
			log.debug("findByHRCostD() method has  finished");
		} catch (NoResultException ne) {
			result = null;
		} catch (PersistenceException pe) {
			log.error("findByHRCostD() method has been failed.", pe);
			throw translate("Failed to search HR Cost ", pe);
		}
		return result;
	}

	/*@Transactional(propagation = Propagation.REQUIRED)
	public HRCost findByIdD(int id) {
		HRCost result = null;
		try {
			Query q = em.createNamedQuery("HRCost.findById");
			q.setParameter("id", id);
			result = (HRCost) q.getSingleResult();
			em.flush();
			log.debug("findByIdD() has finished");
		} catch (PersistenceException pe) {
			log.error("findByIdD() mehtod has error", pe);
			throw translate("Failed to find by ID", pe);
		}
		return result;
	}*/

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteHRCostD(HRCost hrCost) {
		try {
			log.debug("deleteHRCostD() method has started");
			hrCost = em.merge(hrCost);
			em.remove(hrCost);
			em.flush();
			log.debug("deleteHRCostD() mehtod has finished");
		} catch (PersistenceException pe) {
			log.error("deleteHRCostD() method has been failed", pe);
			throw translate("Failed to delete HR Cost", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public HRCost insertHRCostD(HRCost hrcost) {
		try {
			log.debug("insertHRCostD() method has started");
			em.persist(hrcost);
			em.flush();
			log.debug("insertHRCostD() method has finished");
		} catch (PersistenceException pe) {
			log.error("insertHRCostD() method has error", pe);
			throw translate("Failed to insert HR Cost", pe);
		}

		return hrcost;
	}

	/*@Transactional(propagation = Propagation.REQUIRED)
	public HRCost updateByYearD(HRCost hrcost) {
		try {
			Query q = em.createNamedQuery("HRCost.updateByHRCost");
			q.setParameter("year", hrcost.getYear());
			q.setParameter(" hr_cost", hrcost.getHr_cost());
			q.executeUpdate();
			em.flush();
			log.debug("updateByYearD() has finished");
		} catch (PersistenceException pe) {
			log.error("updateByYearD() has error", pe);
			throw translate("Failed to update by year", pe);
		}
		return hrcost;
	}*/

	@Transactional(propagation = Propagation.REQUIRED)
	public HRCost updateHRCostD(HRCost hrCost) {
		try {
			log.debug("updateHRCostD() mehtod has started");
			hrCost = em.merge(hrCost);
			em.flush();
			log.debug("updateHRCostD() mehtod has finished");
		} catch (PersistenceException pe) {
			log.error("updateHRCostD() method has error", pe);
			throw translate("Failed to update HR Cost", pe);
		}
		return hrCost;
	}

}
