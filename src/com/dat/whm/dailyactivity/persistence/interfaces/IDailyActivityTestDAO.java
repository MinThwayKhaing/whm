package com.dat.whm.dailyactivity.persistence.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.dailyactivity.entity.DailyActivityTest;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;

public interface IDailyActivityTestDAO {
	public DailyActivityTest insert(DailyActivityTest DailyActivityTest) throws DAOException;

	public DailyActivityTest update(DailyActivityTest DailyActivityTest) throws DAOException;

	public void delete(DailyActivityTest DailyActivityTest) throws DAOException;

	public List<DailyActivityTest> findActivityByReportID(DailyReport dailyReport) throws DAOException;

	public void deleteByReportID(DailyReport dailyReport) throws DAOException;

	public List<DailyActivityTest> findAllActivitiesByCriteria(DailyReport dailyReport, DailyActivityTest DailyActivityTest) throws DAOException; 
	public Date findActivityDateByProjectID(String projectId) throws SystemException;

	public Date findMinDateByProjectID(String projectId) throws SystemException;
	public Date findMaxDateByProjectID(String projectId) throws SystemException;
	public double findSumOfActivityHrByProjectID(String projectId) throws SystemException;
	public int countActivityDateByProjectID(String projectId) throws SystemException;
	public List<Object[]> findReportByMonthly(String projectid,String staffid,String reportdate,int team_id) throws DAOException;

}
