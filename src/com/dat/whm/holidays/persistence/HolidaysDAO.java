/***************************************************************************************
 * @author Kyi Saw Win
 * @Date 2017-09-25
 * @Version 1.0
 * @Purpose Holidays DAO.
 *
 ***************************************************************************************/
package com.dat.whm.holidays.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.holidays.entity.Holidays;
import com.dat.whm.holidays.persistence.interfaces.IHolidaysDAO;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.web.common.SearchDate;

@Repository("HolidaysDAO")
public class HolidaysDAO extends BasicDAO implements IHolidaysDAO{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Transactional(propagation = Propagation.REQUIRED)
	public int countByYearMonth(SearchDate searchDate) throws DAOException{
		int result = 0;
		try{
			logger.debug("countByYearMonth() method has been started.");
			
			Query q = em.createNamedQuery("Holidays.countByYearMonth");
			q.setParameter("fromDate", searchDate.getFromDate());
			q.setParameter("toDate", searchDate.getToDate());
			result = (int) ((Number) q.getSingleResult()).intValue();
			em.flush();
			logger.debug("countByYearMonth() method has been finished.");
		}catch (PersistenceException pe) {
			logger.error("countByYearMonth() method has been fail.", pe);
			throw translate("Failed to countByYearMonth", pe);
		}
		
		return result;
		
	}

	/**
	 * Created By   : Ye Phyo Ko
	 * Created Date : 2018/01/11
	 * Explanation  : Added insert(), findByDate(), findAll(),
	 * 				  update() and delete() methods.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Holidays insert(Holidays holiday) {
		try {			        
			logger.debug("insert() method has been started.");
			em.persist(holiday);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Holidays(Date = " + holiday.getDate() + ")", pe);
		}
		return holiday;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Holidays> findByDate(Date date) {
		List<Holidays> result = null;
		try {
			logger.debug("findByDate() method has been started.");
			Query q = em.createNamedQuery("Holidays.findByDate");
			q.setParameter("date", date);
			result = q.getResultList();
			em.flush();
			logger.debug("findByDate() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findByDate() method has been failed.", pe);
			throw translate("Failed to search Holidays by Date(Date = " + date + ")", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Holidays> findAll() {
		List<Holidays> result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("Holidays.findAll");
			result = q.getResultList();
			em.flush();
			logger.debug("findAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of Team.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Holidays update(Holidays holiday) {
		try {
			logger.debug("updateByDate() method has been started.");
			em.merge(holiday);
			em.flush();
			logger.debug("updateByDate() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("updateByDate() method has been failed.", pe);
			throw translate("Failed to update Holidays(date = " + holiday.getDate() + ")", pe);
		}
		return holiday;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Holidays holiday) {
		try {
			logger.debug("delete() method has been started.");
			holiday = em.merge(holiday);
			em.remove(holiday);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete Holidays(Date = " + holiday.getDate() + ")", pe);
		}
	}
	
	/**
	 * Created By	: Aye Chan Thar Soe
	 * Created Date	: 2018/09/04
	 * Explanation	: Find holidays by year and month.
	 */	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Holidays> findByYearMonth(int year, int month) {
		List<Holidays> result = null;
		try {
			logger.debug("findByYearMonth() method has been started.");
			Query q = em.createNamedQuery("Holidays.findByYearMonth");
			
			if (month < 10) {
				q.setParameter("month", "0".concat(String.valueOf(month)));
			} else {
				q.setParameter("month", String.valueOf(month));
			}
			
			q.setParameter("year", String.valueOf(year));
			
			result = q.getResultList();
			em.flush();
			logger.debug("findByYearMonth() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByYearMonth() method has been failed.", pe);
			throw translate("Failed to find Holidays By year and month.", pe);
		}
		return result;
	}
	

	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Find all by date.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Holidays> findAllByDate(String year, String month) {
		List<Holidays> result = null;
		try {
			logger.debug("findAllByDate() method has been started.");
			Query q = em.createNamedQuery("Holidays.findAllByDate");
			q.setParameter("year", year);
			q.setParameter("month", month);
			result = q.getResultList();
			em.flush();
			logger.debug("findAllByDate() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findAllByDate() method has been failed.", pe);
			throw translate("Failed to search Holidays by Date(Year = " + year + " : Month = "+month+")", pe);
		}
		return result;
	}
}
