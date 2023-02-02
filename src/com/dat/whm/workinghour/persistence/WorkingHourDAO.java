/***************************************************************************************
 * @author Soe Thiha Lwin
 * @Date 2018-09-24
 * @Version 1.0
 * @Purpose WorkingHour DAO.
 *
 ***************************************************************************************/
package com.dat.whm.workinghour.persistence;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.workinghour.entity.WorkingHour;
import com.dat.whm.workinghour.persistence.interfaces.IWorkingHourDAO;
import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;

@Repository("WorkingHourDAO")
public class WorkingHourDAO extends BasicDAO implements IWorkingHourDAO{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Transactional(propagation = Propagation.REQUIRED)
	public WorkingHour insert(WorkingHour workinghour) {
		try {			        
			logger.debug("insert() method has been started.");
			em.persist(workinghour);
			em.flush();
			logger.debug("insert() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Working Hour for (" + workinghour.getId() + ")", pe);
		}
		return workinghour;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findAll() {
		List<WorkingHour> result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("WorkingHour.findAll");
			result = q.getResultList();
			em.flush();
			logger.debug("findAll() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of Working Hour Info.", pe);
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findByStartDate(Date startDate) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findByStartDate() method has been started.");
			Query q = em.createNamedQuery("WorkingHour.findByStartDate");
			q.setParameter("startDate", startDate);
			result = q.getResultList();
			em.flush();
			logger.debug("findByStartDate() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findByStartDate() method has been failed.", pe);
			throw translate("Failed to search Start Date(Date = " + startDate + ")", pe);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findByEndDate(Date endDate) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findByEndDate() method has been started.");
			Query q = em.createNamedQuery("WorkingHour.findByEndDate");
			q.setParameter("endDate", endDate);
			result = q.getResultList();
			em.flush();
			logger.debug("findByEndDate() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findByEndDate() method has been failed.", pe);
			throw translate("Failed to search End Date(Date = " + endDate + ")", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public WorkingHour update(WorkingHour workinghour) {
		try {
			logger.debug("updateByDate() method has been started.");
			em.merge(workinghour);
			em.flush();
			logger.debug("updateByDate() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("updateByDate() method has been failed.", pe);
			throw translate("Failed to update Working Hour for (" + workinghour.getId() + ")", pe);
		}
		return workinghour;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(WorkingHour workinghour) {
		try {
			logger.debug("delete() method has been started.");
			workinghour = em.merge(workinghour);
			em.remove(workinghour);
			em.flush();
			logger.debug("delete() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete Working Hour for (" + workinghour.getId() + ")", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findByDate(String date) throws DAOException {
		double result=0.0;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 java.util.Date tmpDate = null;
		try {
			tmpDate = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 java.sql.Date sqlDate = new java.sql.Date(tmpDate.getTime());  
		// System.out.println(sqlDate);
		
		
		try {
			logger.debug("findByDate() method has been started.");
			
			Query q = em.createNamedQuery("WorkingHour.findByDate");
			q.setParameter("startDate", sqlDate);
			q.setParameter("endDate", sqlDate);

			List<Object> tmpList =  q.getResultList();
			
			if(tmpList.size() > 0)
				result = (double) tmpList.get(0);
			else
				result = 0.0;
			
		//	System.out.println(result +" >> working hour info");
			em.flush();
			logger.debug("findByDate() method has been successfully finisehd.");
			
		}catch (PersistenceException pe) {
			
			logger.error("findByDate() method has been failed.", pe);
			throw translate("Failed to findByDate", pe);
		}
		return result;
	}


	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findByStartDateUpdate(Date startDate, int id) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findByStartDateUpdate() method has been started.");
			Query q = em.createNamedQuery("WorkingHour.findByStartDateUpdate");
			q.setParameter("startDate", startDate);
			q.setParameter("id", id);
			result = q.getResultList();
			em.flush();
			logger.debug("findByStartDateUpdate() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findByStartDateUpdate() method has been failed.", pe);
			throw translate("Failed to search Start Date for Update (Date = "
					+ startDate + ")", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findByEndDateUpdate(Date endDate, int id) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findByEndDateUpdate() method has been started.");
			Query q = em.createNamedQuery("WorkingHour.findByEndDateUpdate");
			q.setParameter("endDate", endDate);
			q.setParameter("id", id);
			result = q.getResultList();
			em.flush();
			logger.debug("findByEndDateUpdate() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findByEndDateUpdate() method has been failed.", pe);
			throw translate("Failed to search End Date for Update (Date = "
					+ endDate + ")", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findDurationPeriod(Date startDate, Date endDate) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findDurationPeriod() method has been started.");
			Query q = em.createNamedQuery("WorkingHour.findDurationPeriod");
			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			result = q.getResultList();
			em.flush();
			logger.debug("findDurationPeriod() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findDurationPeriod method has been failed.", pe);
			throw translate("Failed to search DurationPeriod", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findDurationPeriodUpdate(Date startDate,
			Date endDate, int id) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findDurationPeriodUpdate() method has been started.");
			Query q = em
					.createNamedQuery("WorkingHour.findDurationPeriodUpdate");
			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			q.setParameter("id", id);
			result = q.getResultList();
			em.flush();
			logger.debug("findDurationPeriodUpdate() method has been successfully finished.");
		} catch (PersistenceException pe) {
			logger.error("findDurationPeriodUpdate method has been failed.", pe);
			throw translate("Failed to search DurationPeriodUpdate", pe);
		}
		return result;
	}
	

}
