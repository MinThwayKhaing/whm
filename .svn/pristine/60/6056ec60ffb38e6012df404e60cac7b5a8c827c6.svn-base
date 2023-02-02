/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.dailyactivity.persistence.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;

public interface IDailyActivityDAO {
	public DailyActivity insert(DailyActivity dailyActivity) throws DAOException;

	public DailyActivity update(DailyActivity dailyActivity) throws DAOException;

	public void delete(DailyActivity dailyActivity) throws DAOException;

	public List<DailyActivity> findActivityByReportID(DailyReport dailyReport) throws DAOException;

	public void deleteByReportID(DailyReport dailyReport) throws DAOException;
	
	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/12
	 * Explanation  : Get daily activity data form daily_activity table.
	 */
	public List<DailyActivity> findAllActivitiesByCriteria(DailyReport dailyReport, DailyActivity dailyActivity) throws DAOException; 
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
	
	public List<Object[]> findReportByMonthly(String projectid,String staffid,String reportdate,int team_id) throws DAOException;
}