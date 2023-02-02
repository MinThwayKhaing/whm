/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.dailyreport.service.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.exception.SystemException;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.common.SearchDate;
import com.dat.whm.web.weeklyreport.WeeklyData;

public interface IDailyReportService {

	public DailyReport addNewDailyReport(DailyReport dailyReport) throws SystemException;

	public DailyReport updateDailyReport(DailyReport dailyReport) throws SystemException;

	// public void deleteDailyReport(DailyReport dailyReport) throws
	// SystemException;

	public List<DailyReport> findByDate(Date date, User staff) throws SystemException;

	public List<DailyReport> findByDateforDuplicate(Date date, User staff) throws SystemException;

	public List<DailyReport> findByMonthYear(int month, int year) throws SystemException;


	public List<Object[]> findByMonthYearProj(int month, int year,String project) throws SystemException;
	public List<Object[]> findByYearProj(int year,String project) throws SystemException;

	public List<DailyReport> findAllReport() throws SystemException;

	/**
	 * Revised By   : Sai Kyaw Ye Myint
	 * Revised Date : 2017/09/08
	 * Explanation  : Added argument for login user info.
	 */
	//public List<DailyReport> searchReport(DailyReport dailyReport, SearchDate searchDate) throws SystemException;
	public List<DailyReport> searchReport(DailyReport dailyReport, SearchDate searchDate, User loginUser) throws SystemException;

	public void deleteDailyReportLists(List<DailyReport> dailyReportIDList) throws SystemException;

	public void approveDailyReportLists(List<DailyReport> dailyReportIDList) throws SystemException;

	public void disapproveDailyReportLists(List<DailyReport> dailyReportIDList) throws SystemException;

	public List<DailyReport> searchAllByAdmin(DailyReport dailyReport);

	public List<DailyReport> findReportIDListforExcel(DailyReport dailyReport, SearchDate searchDate) throws SystemException;
	
	/**
	 * Created By   : Kyi Saw Win
	 * Created Date : 2017/09/12
	 * Explanation  : Get available years from daily_report table.
	 */
	public List<String> findActiveYears() throws SystemException;
	
	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/12
	 * Explanation  : Get daily report data from daily_report table.
	 */
	public List<DailyReport> findAllReportByCriteria(DailyReport dailyReport, String year, String month, String day,SearchDate searchDate) throws SystemException;
	
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
	public List<DailyReport> searchReportByCriteria(DailyReport dailyReport, String year, String month, User loginUser) throws SystemException;
	
	public void deleteDailyReportData(List<DailyReport> checkboxlist,List<DailyActivity> dailyActivityList,List<DailyReport> dailyReportList) throws SystemException;
	
	public void deleteWeeklyReportData(List<WeeklyData> checkboxlist,List<DailyReport> dailyReportList) throws SystemException;
	

}