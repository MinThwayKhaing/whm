/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.dailyactivity.service.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.user.entity.User;

public interface IDailyActivityService {

	public DailyActivity addNewDailyActivity(DailyActivity dailyActivity) throws SystemException;

	public DailyActivity updateDailyActivity(DailyActivity dailyActivity) throws SystemException;

	public void deleteDailyActivity(DailyActivity dailyActivity) throws SystemException;

	public List<DailyActivity> findActivityByReportID(DailyReport dailyReport) throws SystemException;

	public void deleteDailyActivityByReportID(DailyReport dailyReport) throws SystemException;
	
	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/12
	 * Explanation  : Get daily activity data form daily_activity table.
	 */
	public List<DailyActivity> findAllActivitiesByCriteria(DailyReport dailyReport,DailyActivity dailyActivity) throws SystemException;
	public Date findActivityDateByProjectID(String projectId) throws SystemException;
	
	/**
	 * Created By   : Kyi Saw Win
	 * Created Date : 2017/09/25
	 * Explanation  : Created for project data calculation.
	 */
	public Date findMinDateByProjectID(String projectId) throws SystemException;
	public Date findMaxDateByProjectID(String projectId) throws SystemException;
	public double findSumOfActivityHrByProjectID(String projectId) throws SystemException;
	public int countActivityDateByProjectID(String projectId) throws SystemException;
	
	public List<Object[]> findReportByMonthly(String projectid,String staffid,String reportdate,int team_id) throws SystemException;
	
	public void saveDailyReportData(DailyReport dailyReport,List<DailyActivity> dailyActivityList,List<DailyActivity> tempDailyActivityList,boolean editFlag,List<DailyReport> result,boolean adminflag,String staff,User loginuser,Date oldDate) throws SystemException;
	
}