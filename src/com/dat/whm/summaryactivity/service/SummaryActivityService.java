/***************************************************************************************
 * @author Htet Wai Yum
 * @Date 2018-09-17
 * @Version 1.0
 * @Purpose Added summary activity service for summary activities table
 *
 *
 ***************************************************************************************/
package com.dat.whm.summaryactivity.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.LockTimeoutException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.summaryactivity.persistence.interfaces.ISummaryActivityDAO;
import com.dat.whm.summaryactivity.service.interfaces.ISummaryActivityService;
import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.workinghourmanagement.team.HolidayCount;
import com.dat.whm.web.workinghrmanagementproject.ManMonthInfo;
import com.dat.whm.web.workinghrmanagementproject.ProjectSummaryInfo;
import com.dat.whm.web.workinghrmanagementproject.StaffInfo;
import com.dat.whm.web.workinghrmanagementproject.TeamInfo;
import com.dat.whm.web.workinghrmanagementproject.TotalManMonthInfo;


@Service("SummaryActivityService")
public class SummaryActivityService implements ISummaryActivityService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "SummaryActivityDAO")
	private ISummaryActivityDAO summaryactivityDAO;
	
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: insert daily report information into summary activity
	    */ 
	@Transactional(propagation = Propagation.REQUIRED)
	public SummaryActivity insertSummaryActivity(SummaryActivity summaryactivity) throws SystemException {
		
		try {
			logger.debug("insertSummaryActivity() method has been started.");
			summaryactivity = summaryactivityDAO.insertSummaryActivity(summaryactivity);
			logger.debug("insertSummaryActivity() method has been finished.");
		} catch (DAOException e) {
			logger.error("insertSummaryActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search Team by teamName", e);
		}
		return summaryactivity;
	}
	
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: update daily report information into summary activity
	    */ 
	@Transactional(propagation = Propagation.REQUIRED)
	public SummaryActivity updateSummaryActivity(SummaryActivity summaryactivity) throws SystemException {
		
		try {
			logger.debug("updateSummaryActivity() method has been started.");
			summaryactivity = summaryactivityDAO.updateSummaryActivity(summaryactivity);
			logger.debug("updateSummaryActivity() method has been finished.");
		} catch (DAOException e) {
			logger.error("updateSummaryActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search Team by teamName", e);
		}
		return summaryactivity;
	}
	
	/**
	 * Revised By : Htet Wai Yum 
	 * Revised Date : 2018/09/10 
	 * Explanation: Update activity date into summary activities table for daily registration page
	 */
	    
	@Transactional(propagation = Propagation.REQUIRED)
	public SummaryActivity updateActivityDate(SummaryActivity summaryactivity) throws SystemException {
		
		try {
			logger.debug("updateSummaryActivity() method has been started.");
			summaryactivity = summaryactivityDAO.updateActivityDate(summaryactivity);
			logger.debug("updateSummaryActivity() method has been finished.");
		} catch (DAOException e) {
			logger.error("updateSummaryActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search Team by teamName", e);
		}
		return summaryactivity;
	}
	
	/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: delete daily report information into summary activity
	    */ 
	@Transactional(propagation = Propagation.REQUIRED)
	public SummaryActivity deleteSummaryActivity(SummaryActivity summaryactivity) throws SystemException {
		
		try {
			logger.debug("deleteSummaryActivity() method has been started.");
			summaryactivity = summaryactivityDAO.deleteSummaryActivity(summaryactivity);
			logger.debug("deleteSummaryActivity() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteSummaryActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search Team by teamName", e);
		}
		return summaryactivity;
	}
	
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: find daily report information into summary activity
	    */ 
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SummaryActivity> findAll() throws SystemException {
		logger.debug("findAllReport() method has been started.");
		List<SummaryActivity> result = summaryactivityDAO.findAll();
		logger.debug("findAllReport() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public double findByProjectId(SummaryActivity sa) throws SystemException {
		logger.debug("findAllReport() method has been started.");
		double result = summaryactivityDAO.findByProjectId(sa);
		logger.debug("findAllReport() method has been finished.");
		return result;
	}
	
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: find by projectid/staffid/year/month for daily report information into summary activity
	    */ 
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SummaryActivity> findByProjectidStaffidYearMonth(SummaryActivity sa) throws SystemException {
		logger.debug("findByProjectidStaffidYearMonth() method has been started.");
		List<SummaryActivity> result = summaryactivityDAO.findByProjectidStaffidYearMonth(sa);
		logger.debug("findByProjectidStaffidYearMonth() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByProjectID(String projectID)
			throws SystemException {
		logger.debug("findByProjectID() method has been started.");
		List<Object[]> result = summaryactivityDAO.findByProjectID(projectID);
		logger.debug("findByProjectID() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SummaryActivity> findByTeam(Map<String, Object> searchData)throws SystemException {
		logger.debug("findByTeam() method has been started.");
		List<SummaryActivity> result = summaryactivityDAO.findByTeam(searchData);
		logger.debug("findByTeam() method has been finished.");
		return result;
	}
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/09/24
	 * Explanation  : Get distinct of year by TeamID.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findYearsByTeam(Team team) {
		List<String> years = null;
		try {
			logger.debug("findYearsByTeam() method has been started.");
			years = summaryactivityDAO.findYearsByTeam(team);
			logger.debug("findYearsByTeam() method has been finished.");
		} catch (DAOException e) {
			logger.error("findYearsByTeam() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search year by defined team id.", e);
		}
		return years;
	}
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/09/24
	 * Explanation  : Get distinct of month by TeamID.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findMonthsByTeam(Team team) {
		List<String> months = null;
		try {
			logger.debug("findMonthsByTeam() method has been started.");
			months = summaryactivityDAO.findMonthsByTeam(team);
			logger.debug("findMonthsByTeam() method has been finished.");
		} catch (DAOException e) {
			logger.error("findMonthsByTeam() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search month by defined team id.", e);
		}
		return months;
	}
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/10/10
	 * Explanation  : Get distinct of year by StaffID.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findYearsByUser(User user) {
		List<String> years = null;
		try {
			logger.debug("findYearsByUser() method has been started.");
			years = summaryactivityDAO.findYearsByUser(user);
			logger.debug("findYearsByUser() method has been finished.");
		} catch (DAOException e) {
			logger.error("findYearsByUser() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search year by defined staff id.", e);
		}
		return years;
	}
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/10/10
	 * Explanation  : Get distinct of month by StaffID.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findMonthsByUser(User user) {
		List<String> months = null;
		try {
			logger.debug("findMonthsByUser() method has been started.");
			months = summaryactivityDAO.findMonthsByUser(user);
			logger.debug("findMonthsByUser() method has been finished.");
		} catch (DAOException e) {
			logger.error("findMonthsByUser() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search month by defined staff id.", e);
		}
		return months;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String findStartMonthByUser(User user) {
		String months = null;
		try {
			logger.debug("findMonthsByUser() method has been started.");
			months = summaryactivityDAO.findStartMonthByUser(user);
			logger.debug("findMonthsByUser() method has been finished.");
		} catch (DAOException e) {
			logger.error("findMonthsByUser() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search month by defined staff id.", e);
		}
		return months;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String findEndMonthByUser(User user) {
		String months = null;
		try {
			logger.debug("findMonthsByUser() method has been started.");
			months = summaryactivityDAO.findEndMonthByUser(user);
			logger.debug("findMonthsByUser() method has been finished.");
		} catch (DAOException e) {
			logger.error("findMonthsByUser() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search month by defined staff id.", e);
		}
		return months;
	}
	
	
	public List<Object[]> findByStaff(User user, String startDate,
			String endDate) throws SystemException {
		List<Object[]> resultList = null;
		
		try {
			logger.debug("findByStaff() method has been started.");
			resultList = summaryactivityDAO.findByStaff(user, startDate, endDate);
			logger.debug("findByStaff() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByStaff() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to findByStaff.", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findProjectIDByStaff(User user, String startDate,
			String endDate) throws SystemException {
		List<String> projectIDList = null;
		try {
			logger.debug("findProjectIDByStaff() method has been started.");
			projectIDList = summaryactivityDAO.findProjectIDByStaff(user, startDate, endDate);
			logger.debug("findProjectIDByStaff() method has been finished.");
		} catch (DAOException e) {
			logger.error("findProjectIDByStaff() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to findProjectIDByStaff.", e);
		}
		return projectIDList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTotalByStaff(User user, String startDate,
			String endDate) throws SystemException {
			List<Object[]> resultList = null;
		
		try {
			logger.debug("findTotalByStaff() method has been started.");
			resultList = summaryactivityDAO.findTotalByStaff(user, startDate, endDate);
			logger.debug("findTotalByStaff() method has been finished.");
		} catch (DAOException e) {
			logger.error("findTotalByStaff() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to findTotalByStaff.", e);
		}
		return resultList;
	}
	

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDateByProjectID(SummaryActivity summaryActivity)
			throws SystemException {
		logger.debug("findMinYearMonthByProjectID() method has been started.");
		Date result = summaryactivityDAO.findMinDateByProjectID(summaryActivity);
		logger.debug("findMinYearMonthByProjectID() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDateByProjectIDFull(SummaryActivity summaryActivity)
			throws SystemException {
		logger.debug("findMinDateByProjectIDFull() method has been started.");
		Date result = summaryactivityDAO.findMinDateByProjectIDFull(summaryActivity);
		logger.debug("findMinDateByProjectIDFull() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDateByProjectID(SummaryActivity summaryActivity)
			throws SystemException {
		logger.debug("findMaxYearMonthByProjectID() method has been started.");
		Date result = summaryactivityDAO.findMaxDateByProjectID(summaryActivity);
		logger.debug("findMaxYearMonthByProjectID() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDateByProjectIDFull(SummaryActivity summaryActivity)
			throws SystemException {
		logger.debug("findMaxDateByProjectIDFull() method has been started.");
		Date result = summaryactivityDAO.findMaxDateByProjectIDFull(summaryActivity);
		logger.debug("findMaxDateByProjectIDFull() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDate()
			throws SystemException {
		logger.debug("findMinDate() method has been started.");
		Date result = summaryactivityDAO.findMinDate();
		logger.debug("findMinDate() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDate()
			throws SystemException {
		logger.debug("findMaxDate() method has been started.");
		Date result = summaryactivityDAO.findMaxDate();
		logger.debug("findMaxDate() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SummaryActivity> findTeamByProjectID(
			SummaryActivity summaryActivity) throws SystemException {
		logger.debug("findTeamByProjectID() method has been started.");
		 List<SummaryActivity> result = summaryactivityDAO.findTeamByProjectID(summaryActivity);
		logger.debug("findTeamByProjectID() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByProjectID(SummaryActivity sa,String startDate,String endDate)
			throws SystemException {
		logger.debug("findByProjectID() method has been started.");
		List<Object[]> result = summaryactivityDAO.findByProjectID(sa,startDate,endDate);
		logger.debug("findByProjectID() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByClientOrganization(String customerName,String startDate,String endDate)
			throws SystemException {
		logger.debug("findByClientOrganization() method has been started.");
		List<Object[]> result = summaryactivityDAO.findByClientOrganization(customerName,startDate,endDate);
		logger.debug("findByClientOrganization() method has been started.");
		return result;
	}


	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findProjectIDByFull(
			String projectID,String startDate,String endDate,String customer) throws SystemException {
		logger.debug("findProjectIDByFull() method has been started.");
		 List<ProjectSummaryInfo> result = summaryactivityDAO.findProjectIDByFull(projectID,startDate,endDate,customer);
		logger.debug("findProjectIDByFull() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findProjectIDByNull(
			String projectID,String startDate,String endDate,String customer) throws SystemException {
		logger.debug("findProjectIDByNull() method has been started.");
		 List<ProjectSummaryInfo> result = summaryactivityDAO.findProjectIDByNull(projectID,startDate,endDate,customer);
		logger.debug("findProjectIDByNull() method has been started.");
		return result;
	}
	
	public List<ProjectSummaryInfo> findByAllProject(String startDate,String endDate,String customer) throws SystemException {
		logger.debug("findByAllProject() method has been started.");
		 List<ProjectSummaryInfo> result = summaryactivityDAO.findByAllProject(startDate,endDate,customer);
		logger.debug("findByAllProject() method has been started.");
		return result;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findProjectIDByProjectID(
			SummaryActivity summaryActivity,String startDate,String endDate) throws SystemException {
		logger.debug("findProjectIDByProjectID() method has been started.");
		 List<String> result = summaryactivityDAO.findProjectIDByProjectID(summaryActivity,startDate,endDate);
		logger.debug("findProjectIDByProjectID() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findProjectIDByCustomer(String customer,String startDate,String endDate)
			throws SystemException {

		logger.debug("findProjectIDByCustomer() method has been started.");
		List<String> result = summaryactivityDAO.findProjectIDByCustomer(customer,startDate,endDate);
		logger.debug("findProjectIDByCustomer() method has been started.");
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Object[] findMinDateByCustomer(String customer) throws SystemException {
		logger.debug("findMinDateByCustomer() method has been started.");
		Object[] result = summaryactivityDAO.findMinDateByCustomer(customer);
		logger.debug("findMinDateByCustomer() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Object[] findMaxDateByCustomer(String customer) throws SystemException {
		logger.debug("findMaxDateByCustomer() method has been started.");
		Object[] result = summaryactivityDAO.findMaxDateByCustomer(customer);
		logger.debug("findMaxDateByCustomer() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByAllProject(String startDate,String endDate)
			throws SystemException {
		logger.debug("findByAllProject() method has been started.");
		List<Object[]> result = summaryactivityDAO.findByAllProject(startDate,endDate);
		logger.debug("findByAllProject() method has been started.");
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByNullProject(String startDate,String endDate)
			throws SystemException {
		logger.debug("findByNullProject() method has been started.");
		List<Object[]> result = summaryactivityDAO.findByNullProject(startDate,endDate);
		logger.debug("findByNullProject() method has been started.");
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDateByNullPrjID() throws SystemException {
		logger.debug("findMinDateByNullPrjID() method has been started.");
		Date result = summaryactivityDAO.findMinDateByNullPrjID();
		logger.debug("findMinDateByNullPrjID() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDateByNullPrjID() throws SystemException {
		logger.debug("findMaxDateByNullPrjID() method has been started.");
		Date result = summaryactivityDAO.findMaxDateByNullPrjID();
		logger.debug("findMaxDateByNullPrjID() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findALLProjectID(String startDate,String endDate) throws SystemException {
		logger.debug("findByNullProject() method has been started.");
		List<String> result = summaryactivityDAO.findALLProjectID(startDate,endDate);
		logger.debug("findByNullProject() method has been started.");
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findByPartialProject(String projectid,String startDate,String endDate,String customer)
			throws SystemException {
		logger.debug("findByPartialProject() method has been started.");
		List<ProjectSummaryInfo> result = summaryactivityDAO.findByPartialProject(projectid,startDate,endDate,customer);
		logger.debug("findByPartialProject() method has been started.");
		return result;
	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findByCustomer(String startDate,String endDate,String customer)
			throws SystemException {
		logger.debug("findByCustomer() method has been started.");
		List<ProjectSummaryInfo> result = summaryactivityDAO.findByCustomer(startDate,endDate,customer);
		logger.debug("findByCustomer() method has been started.");
		return result;
	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByPartialResult(String projectId,String startDate,String endDate,String year,String month,String username)
			throws SystemException {
		logger.debug("findDetailByPartialResult() method has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailByPartialResult(projectId, startDate, endDate, year, month, username);
		logger.debug("findDetailByPartialResult() method has been started.");
		return result;
	
	}
	
	

	@Transactional(propagation = Propagation.REQUIRED)
	public double findNullProjectActualManMonth() throws SystemException {
		logger.debug("findNullProjectActualManMonth() method has been started.");
		double result = summaryactivityDAO.findNullProjectActualManMonth();
		logger.debug("findNullProjectActualManMonth() method has been started.");
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findFullProjectActualManMonth(SummaryActivity sa)
			throws SystemException {
		logger.debug("findFullProjectActualManMonth() method has been started.");
		double result = summaryactivityDAO.findFullProjectActualManMonth(sa);
		logger.debug("findFullProjectActualManMonth() method has been started.");
		return result;
	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByProjectID(String projectId,String startDate,String endDate)
			throws SystemException {
		logger.debug("findByProjectID() method has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailByProjectID(projectId,startDate,endDate);
		logger.debug("findByProjectID() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByProjectIDResult(String projectId, String startDate, String endDate,String year,String month,String username,String teamname)
			throws SystemException {
		logger.debug("findDetailByProjectIDResult() method has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailByProjectIDResult(projectId, startDate, endDate, year, month, username,teamname);
		logger.debug("findDetailByProjectIDResult() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserCountDetailByProjectIDResult(String projectId, String startDate, String endDate)
			throws SystemException {
		logger.debug("findUserCountDetailByProjectIDResult() method has been started.");
		List<Object[]> result = summaryactivityDAO.findUserCountDetailByProjectIDResult(projectId, startDate, endDate);
		logger.debug("findUserCountDetailByProjectIDResult() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByPartial(String projectId,String startDate,String endDate)
			throws SystemException {
		logger.debug("findDetailByPartial() method has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailByPartial(projectId,startDate,endDate);
		logger.debug("findDetailByPartial() method has been started.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserCountDetailByPartialResult(String projectId,String startDate,String endDate)
			throws SystemException {
		logger.debug("findUserCountDetailByPartialResult() method has been started.");
		List<Object[]> result = summaryactivityDAO.findUserCountDetailByPartialResult(projectId,startDate,endDate);
		logger.debug("findUserCountDetailByPartialResult() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserCountEachProject(String projectId,String startDate,String endDate)
			throws SystemException {
		logger.debug("findUserCountEachProject() method has been started.");
		List<Object[]> result = summaryactivityDAO.findUserCountEachProject(projectId,startDate,endDate);
		logger.debug("findUserCountEachProject() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTeamCountDetailByPartialResult(String projectId,String startDate,String endDate)
			throws SystemException {
		logger.debug("findTeamCountDetailByPartialResult() method has been started.");
		List<Object[]> result = summaryactivityDAO.findTeamCountDetailByPartialResult(projectId,startDate,endDate);
		logger.debug("findTeamCountDetailByPartialResult() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTotalUserCountEachProject(String projectId,String startDate,String endDate)
			throws SystemException {
		logger.debug("findTotalUserCountEachProject() method has been started.");
		List<Object[]> result = summaryactivityDAO.findTotalUserCountEachProject(projectId,startDate,endDate);
		logger.debug("findTotalUserCountEachProject() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<TotalManMonthInfo> findTotalResultByPartial(String projectId,String startdate, String enddate)
			throws SystemException {
		logger.debug("findTotalResultByPartial() method has been started.");
		List<TotalManMonthInfo> result = summaryactivityDAO.findTotalResultByPartial(projectId,startdate,enddate);
		logger.debug("findTotalResultByPartial() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectByNull(String year,String month)
			throws SystemException {
		logger.debug("findDetailProjectByNull() method has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailProjectByNull(year,month);
		logger.debug("findDetailProjectByNull() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserCountByNull(String startDate,String endDate)
			throws SystemException {
		logger.debug("findUserCountByNull() method has been started.");
		List<Object[]> result = summaryactivityDAO.findUserCountByNull(startDate,endDate);
		logger.debug("findUserCountByNull() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByNullIDResult(String startDate,String endDate,String year,String month,String staffid,Double working_hour,String teamname)
			throws SystemException {
		logger.debug("findDetailByNullIDResult() method has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailByNullIDResult(startDate, endDate, year, month, staffid,working_hour,teamname);
		logger.debug("findDetailByNullIDResult() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Double> findWorkingHour(String date) throws SystemException {
		logger.debug("findWorkingHour() date has been started.");
		List<Double> result = summaryactivityDAO.findWorkingHour(date);
		logger.debug("findWorkingHour() method has been finished.");
		return result;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTotalResultByNull(String year,String month,Double workingHour) throws SystemException {
		logger.debug("findTotalResultByNull() date has been started.");
		List<Object[]> result = summaryactivityDAO.findTotalResultByNull(year,month,workingHour);
		logger.debug("findTotalResultByNull() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findMemberCountEachTeamByNull(String year,String month) throws SystemException {
		logger.debug("findMemberCountEachTeamByNull() date has been started.");
		List<Object[]> result = summaryactivityDAO.findMemberCountEachTeamByNull(year,month);
		logger.debug("findMemberCountEachTeamByNull() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectByAll(String startDate,String endDate) throws SystemException {
		logger.debug("findDetailProjectByAll() date has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailProjectByAll(startDate,endDate);
		logger.debug("findDetailProjectByAll() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailUserByAll(String startDate,String endDate) throws SystemException {
		logger.debug("findDetailUserByAll() date has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailUserByAll(startDate,endDate);
		logger.debug("findDetailUserByAll() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectResultByAll(String projectId,String year,String month,String username,Double workingHour,String teamname) throws SystemException {
		logger.debug("findDetailProjectResultByAll() date has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailProjectResultByAll(projectId,year,month,username,workingHour,teamname);
		logger.debug("findDetailProjectResultByAll() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailRecordCountByAll(String startDate,String endDate)  throws SystemException {
		logger.debug("findDetailRecordCountByAll() date has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailRecordCountByAll(startDate,endDate);
		logger.debug("findDetailRecordCountByAll() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findMemberCountEachTeamByAll(String startDate,String endDate)  throws SystemException {
		logger.debug("findMemberCountEachTeamByAll() date has been started.");
		List<Object[]> result = summaryactivityDAO.findMemberCountEachTeamByAll(startDate,endDate);
		logger.debug("findMemberCountEachTeamByAll() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findteamListByAll(String projectId,String startDate,String endDate)  throws SystemException {
		logger.debug("findteamListByAll() date has been started.");
		List<Object[]> result = summaryactivityDAO.findteamListByAll(projectId,startDate,endDate);
		logger.debug("findteamListByAll() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findteamCountByAll(String projectId,String startDate,String endDate)  throws SystemException {
		logger.debug("findteamCountByAll() date has been started.");
		List<Object[]> result = summaryactivityDAO.findteamCountByAll(projectId,startDate,endDate);
		logger.debug("findteamCountByAll() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTotalResultByAll(String projectId,String year,String month,Double workingHour)  throws SystemException {
		logger.debug("findTotalResultByAll() date has been started.");
		List<Object[]> result = summaryactivityDAO.findTotalResultByAll(projectId,year,month,workingHour);
		logger.debug("findTotalResultByAll() method has been finished.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectByCustomer(String customer,String startDate,String endDate)
			throws SystemException {
		logger.debug("findDetailProjectByCustomer() method has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailProjectByCustomer(customer,startDate,endDate);
		logger.debug("findDetailProjectByCustomer() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailUserByCustomer(String customer,String startDate,String endDate)
			throws SystemException {
		logger.debug("findDetailUserByCustomer() method has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailUserByCustomer(customer,startDate,endDate);
		logger.debug("findDetailUserByCustomer() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectResultByCustomer(String projectId,String customer,String year,String month,String username,Double workingHour,String teamname)
			throws SystemException {
		logger.debug("findDetailProjectResultByCustomer() method has been started.");
		List<Object[]> result = summaryactivityDAO.findDetailProjectResultByCustomer(projectId,customer,year,month,username,workingHour,teamname);
		logger.debug("findDetailProjectResultByCustomer() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findDetailProjectByPartial(String projectId,String startDate,String endDate,String customer)
			throws SystemException {
		logger.debug("findDetailProjectByPartial() method has been started.");
		List<ProjectSummaryInfo> result = summaryactivityDAO.findDetailProjectByPartial(projectId,startDate,endDate,customer);
		logger.debug("findDetailProjectByPartial() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<TeamInfo> findDetailTeamByPartial(String projectId,String startDate,String endDate)
			throws SystemException {
		logger.debug("findDetailTeamByPartial() method has been started.");
		List<TeamInfo> result = summaryactivityDAO.findDetailTeamByPartial(projectId,startDate,endDate);
		logger.debug("findDetailTeamByPartial() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findDetailProjectByFull(String projectId,String startDate,String endDate,String customer)
			throws SystemException {
		logger.debug("findDetailProjectByFull() method has been started.");
		List<ProjectSummaryInfo> result = summaryactivityDAO.findDetailProjectByFull(projectId,startDate,endDate,customer);
		logger.debug("findDetailProjectByFull() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<StaffInfo> findDetailUserByPartial(String projectId,String teamname,String startDate, String endDate)
			throws SystemException {
		logger.debug("findDetailUserByPartial() method has been started.");
		List<StaffInfo> result = summaryactivityDAO.findDetailUserByPartial(projectId,teamname,startDate,endDate);
		logger.debug("findDetailUserByPartial() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ManMonthInfo> findDetailManmonthByPartial(String projectId,String teamname,String username,String startdate, String enddate)
			throws SystemException {
		logger.debug("findDetailManmonthByPartial() method has been started.");
		List<ManMonthInfo> result = summaryactivityDAO.findDetailManmonthByPartial(projectId,teamname,username,startdate,enddate);
		logger.debug("findDetailManmonthByPartial() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ManMonthInfo> findManmonthByPartial(String projectId,String teamname,String startdate, String enddate)
			throws SystemException {
		logger.debug("findManmonthByPartial() method has been started.");
		List<ManMonthInfo> result = summaryactivityDAO.findManmonthByPartial(projectId,teamname,startdate,enddate);
		logger.debug("findManmonthByPartial() method has been started.");
		return result;
	}
	
	
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findDetailNullProject(String startDate, String endDate)
			throws SystemException {
		logger.debug("findDetailNullProject() method has been started.");
		List<ProjectSummaryInfo> result = summaryactivityDAO.findDetailNullProject(startDate,endDate);
		logger.debug("findDetailNullProject() method has been started.");
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void findHolidayCount()
			throws SystemException {
		logger.debug("findHolidayCount() method has been started.");
		summaryactivityDAO.findHolidayCount();
		logger.debug("findHolidayCount() method has been started.");
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<HolidayCount> getHolidayCount()
			throws SystemException {
		logger.debug("getHolidayCount() method has been started.");
		List<HolidayCount> result=summaryactivityDAO.getHolidayCount();
		logger.debug("getHolidayCount() method has been started.");
		return result;
	}
	
}