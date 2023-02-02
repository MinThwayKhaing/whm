package com.dat.whm.holidays.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.holidays.entity.Holidays;
import com.dat.whm.holidays.persistence.interfaces.IHolidaysDAO;
import com.dat.whm.holidays.service.interfaces.IHolidaysService;
import com.dat.whm.web.common.SearchDate;

@Service("HolidaysService")
public class HolidaysService implements IHolidaysService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "HolidaysDAO")
	private IHolidaysDAO holidaysDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public int countByYearMonth(SearchDate searchDate){
		int result = 0;
		try{
			logger.debug("countByYearMonth() method has been started.");
			result = holidaysDAO.countByYearMonth(searchDate);
			logger.debug("countByYearMonth() method has been finished.");
		}catch (DAOException e) {
			logger.error("countByYearMonth() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to countByYearMonth.", e);
		}
		
		return result;
	}

	/**
	 * Created By   : Ye Phyo Ko
	 * Created Date : 2018/01/11
	 * Explanation  : Added addNewHoliday(), findByDate(), findAllHoliday(),
	 * 				  updateHoliday() and deleteHoliday() methods.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Holidays addNewHoliday(Holidays holiday) throws SystemException {
		try {
			logger.debug("addHoliday() method has been started.");
			holiday = holidaysDAO.insert(holiday);
			logger.debug("addHoliday() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewHoliday() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Holiday", e);
		}
		return holiday;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Holidays> findByDate(Date date) {
		List<Holidays> result = null;
		try {
			logger.debug("findByDate() method has been started.");
			result = holidaysDAO.findByDate(date);
			logger.debug("findByDate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByDate() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search Holidays by date", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Holidays> findAllHoliday() {
		logger.debug("findAllHoliday() method has been started.");
		List<Holidays> result = holidaysDAO.findAll();
		logger.debug("findAllHoliday() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Holidays updateHoliday(Holidays holiday) {
		try {
			logger.debug("updateHolidays() method has been started.");
			holiday = holidaysDAO.update(holiday);
			logger.debug("updateHolidays() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateHoloidays() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to update Holiday", e);
		}
		return holiday;
		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteHoliday(Holidays holiday) {
		try {
			logger.debug("deleteHoliday() method has been started.");
			holidaysDAO.delete(holiday);
			logger.debug("deleteHoliday() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteHoliday() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to delete Holiday", e);
		}
		
	}
	
	/**
	 * Created By	: Aye Chan Thar Soe
	 * Created Date	: 2018/09/04
	 * Explanation	: Find holidays by year and month.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Holidays> findByYearMonth(int year, int month) {

		List<Holidays> result = null;
		try {
			logger.debug("findByYearMonth() method has been started.");
			result = holidaysDAO.findByYearMonth(year,month);
			logger.debug("findByYearMonth() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByYearMonth() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search Holidays by year and month", e);
		}
		return result;
	
	}
	
	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Find all by date.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Holidays> findAllByDate(String year, String month) {
		List<Holidays> result = null;
		try {
			logger.debug("findAllByDate() method has been started.");
			result = holidaysDAO.findAllByDate(year, month);
			logger.debug("findAllByDate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findAllByDate() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search Holidays by date", e);
		}
		return result;
	}

}
