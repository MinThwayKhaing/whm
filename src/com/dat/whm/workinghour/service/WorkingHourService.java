/***************************************************************************************
 * @author Soe Thiha Lwin
 * @Date 2018-09-24
 * @Version 1.0
 * @Purpose WorkingHour Service.
 *
 ***************************************************************************************/

package com.dat.whm.workinghour.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.workinghour.entity.WorkingHour;
import com.dat.whm.workinghour.persistence.interfaces.IWorkingHourDAO;
import com.dat.whm.workinghour.service.interfaces.IWorkingHourService;

@Service("WorkingHourService")
public class WorkingHourService implements IWorkingHourService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "WorkingHourDAO")
	private IWorkingHourDAO workinghourDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public WorkingHour addNewWorkingHour(WorkingHour workinghour) throws SystemException {
		try {
			logger.debug("addNewWorkingHour() method has been started.");
			workinghour = workinghourDAO.insert(workinghour);
			logger.debug("addNewWorkingHour() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("addNewWorkingHour() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Failed to add working hour", e);
		}
		return workinghour;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findAllWorkingHours() {
		logger.debug("findAllWorkingHours() method has been started.");
		List<WorkingHour> result = workinghourDAO.findAll();
		logger.debug("findAllWorkingHours() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findByStartDate(Date startDate) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findByStartDate() method has been started.");
			result = workinghourDAO.findByStartDate(startDate);
			logger.debug("findByStartDate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByStartDate() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search start date", e);
		}
		return result;
	}	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findByEndDate(Date endDate) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findByEndDate() method has been started.");
			result = workinghourDAO.findByEndDate(endDate);
			logger.debug("findByEndDate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByEndDate() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search end date", e);
		}
		return result;
	}	
		
	@Transactional(propagation = Propagation.REQUIRED)
	public WorkingHour updateWorkingHour(WorkingHour workinghour) {
		try {
			logger.debug("updateWorkingHour() method has been started.");
			workinghour = workinghourDAO.update(workinghour);
			logger.debug("updateWorkingHour() method has been successfully finished.");
		} catch (DAOException e) {
			logger.error("updateWorkingHour() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Failed to update Working Hours", e);
		}
		return workinghour;
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteWorkingHour(WorkingHour workinghour) {
		try {
			logger.debug("deleteWorkingHour() method has been started.");
			workinghourDAO.delete(workinghour);
			logger.debug("deleteWorkingHour() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteWorkingHour() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Failed to delete WorkingHours", e);
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findByDate(String date) throws SystemException {
		double result=0.0;
		try {
			logger.debug("findByDate() method has been started.");
			result = workinghourDAO.findByDate(date);
			logger.debug("findByDate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByDate() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Failed to findByDate", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findByStartDateUpdate(Date startDate, int id) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findByStartDateUpdate() method has been started.");
			result = workinghourDAO.findByStartDateUpdate(startDate, id);
			logger.debug("findByStartDateUpdate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByStartDateUpdate() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search start date for update", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findByEndDateUpdate(Date endDate, int id) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findByEndDateUpdate() method has been started.");
			result = workinghourDAO.findByEndDateUpdate(endDate, id);
			logger.debug("findByEndDateUpdate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByEndDateUpdate() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search End date for update", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findDurationPeriod(Date startDate, Date endDate) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findDurationPeriod() method has been started.");
			result = workinghourDAO.findDurationPeriod(startDate, endDate);
			logger.debug("findDurationPeriod() method has been finished.");
		} catch (DAOException e) {
			logger.error("findDurationPeriod() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DurationPeriod", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkingHour> findDurationPeriodUpdate(Date startDate,
			Date endDate, int id) {
		List<WorkingHour> result = null;
		try {
			logger.debug("findDurationPeriodUpdate() method has been started.");
			result = workinghourDAO.findDurationPeriodUpdate(startDate,
					endDate, id);
			logger.debug("findDurationPeriodUpdate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findDurationPeriodUpdate() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DurationPeriodUpdate", e);
		}
		return result;
	}
	
}
