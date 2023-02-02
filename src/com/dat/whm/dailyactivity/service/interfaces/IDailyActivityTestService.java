package com.dat.whm.dailyactivity.service.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.dailyactivity.entity.DailyActivityTest;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.exception.SystemException;
import com.dat.whm.user.entity.User;

public interface IDailyActivityTestService {


	public DailyActivityTest addNewDailyActivity(DailyActivityTest DailyActivityTest) throws SystemException;

	public DailyActivityTest updateDailyActivity(DailyActivityTest DailyActivityTest) throws SystemException;

	public void deleteDailyActivity(DailyActivityTest DailyActivityTest) throws SystemException;

	public List<DailyActivityTest> findActivityByReportID(DailyReport dailyReport) throws SystemException;

	public void deleteDailyActivityByReportID(DailyReport dailyReport) throws SystemException;
	

	public List<DailyActivityTest> findAllActivitiesByCriteria(DailyReport dailyReport,DailyActivityTest DailyActivityTest) throws SystemException;
	public Date findActivityDateByProjectID(String projectId) throws SystemException;
	

	public Date findMinDateByProjectID(String projectId) throws SystemException;
	public Date findMaxDateByProjectID(String projectId) throws SystemException;
	public double findSumOfActivityHrByProjectID(String projectId) throws SystemException;
	public int countActivityDateByProjectID(String projectId) throws SystemException;
	
	public List<Object[]> findReportByMonthly(String projectid,String staffid,String reportdate,int team_id) throws SystemException;
	
	public void saveDailyReportData(DailyReport dailyReport,List<DailyActivityTest> dailyActivityList,List<DailyActivityTest> tempDailyActivityList,boolean editFlag,List<DailyReport> result,boolean adminflag,String staff,User loginuser,Date oldDate) throws SystemException;

}
