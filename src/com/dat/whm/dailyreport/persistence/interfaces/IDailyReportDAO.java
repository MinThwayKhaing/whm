/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.dailyreport.persistence.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.exception.DAOException;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.common.SearchDate;

public interface IDailyReportDAO {
	public DailyReport insert(DailyReport dailyReport) throws DAOException;

	public DailyReport update(DailyReport dailyReport) throws DAOException;

	// public void delete(DailyReport dailyReport) throws DAOException;

	public List<DailyReport> findByDate(Date date, User staff) throws DAOException;

	public List<DailyReport> findByDateforDuplicate(Date date, User staff) throws DAOException;

	public List<DailyReport> findByMonthYear(int month, int year) throws DAOException;

	public List<Object[]> findByMonthYearProj(int month, int year,String project) throws DAOException;

	public List<Object[]> findByYearProj(int year,String project) throws DAOException;

	public List<DailyReport> findAll() throws DAOException;

	/**
	 * Revised By   : Sai Kyaw Ye Myint
	 * Revised Date : 2017/09/08
	 * Explanation  : Added argument for login user info.
	 */
	public List<DailyReport> search(DailyReport dailyReport, SearchDate searchDate, User loginUser) throws DAOException;

	public boolean delete(List<DailyReport> dailyReportIDList) throws DAOException;

	public boolean approve(List<DailyReport> dailyReportIDList) throws DAOException;

	public boolean disapprove(List<DailyReport> dailyReportIDList) throws DAOException;

	public List<DailyReport> searchAllByAdmin(DailyReport dailyReport);

	public List<DailyReport> findReportIDListforExcel(DailyReport dailyReport, SearchDate searchDate) throws DAOException;
	
	/**
	 * Created By   : Kyi Saw Win
	 * Created Date : 2017/09/12
	 * Explanation  : Get available years from daily_report table.
	 */
	public List<String> findActiveYears() throws DAOException;
	
	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/12
	 * Explanation  : Get daily report data form daily_report table.
	 */
	public List<DailyReport> findAllReportByCriteria(DailyReport dailyReport, String year, String month, String day,SearchDate searchDate) throws DAOException;
	
	/**
	 * Created By	: Aye Chan Thar Soe
	 * Created Date	: 2018/09/04
	 * Explanation	: Get daily report register user list from daily_report table.
	 * */
	public List<Object[]> findDailyReportRegistrationStatusByMonthYear(int year,int month, User user);
	
	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Get daily report data by year/month from daily_report table.
	 */
	public List<DailyReport> searchReportByCriteria(DailyReport dailyReport, String year, String month, User loginUser) throws DAOException;
}