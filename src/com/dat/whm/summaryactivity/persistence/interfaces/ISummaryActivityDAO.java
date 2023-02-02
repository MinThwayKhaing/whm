/***************************************************************************************
 * @author Htet Wai Yum
 * @Date 2018-09-17
 * @Version 1.0
 * @Purpose Added summary activity interface for summary activities table
 *
 *
 ***************************************************************************************/
package com.dat.whm.summaryactivity.persistence.interfaces;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.workinghourmanagement.team.HolidayCount;
import com.dat.whm.web.workinghrmanagementproject.ManMonthInfo;
import com.dat.whm.web.workinghrmanagementproject.ProjectSummaryInfo;
import com.dat.whm.web.workinghrmanagementproject.StaffInfo;
import com.dat.whm.web.workinghrmanagementproject.TeamInfo;
import com.dat.whm.web.workinghrmanagementproject.TotalManMonthInfo;


public interface ISummaryActivityDAO {
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: insert daily report information into summary activity
	    */ 
	public SummaryActivity insertSummaryActivity(SummaryActivity summaryActivity) throws DAOException;
	
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: update daily report information into summary activity
	    */ 
	public SummaryActivity updateSummaryActivity(SummaryActivity summaryActivity) throws DAOException;
	
	/**
	 * Revised By : Htet Wai Yum 
	 * Revised Date : 2018/09/10 
	 * Explanation: Update activity date into summary activities table for daily registration page
	 */ 
	public SummaryActivity updateActivityDate(SummaryActivity summaryActivity) throws DAOException;
	
	
	/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: delete daily report information into summary activity
	    */ 
	public SummaryActivity deleteSummaryActivity(SummaryActivity summaryActivity) throws DAOException;
	
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: find daily report information into summary activity
	    */ 
	public List<SummaryActivity> findAll() throws DAOException;
	
	public double findByProjectId(SummaryActivity summaryActivity) throws DAOException;
	
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: find by projectid/staffid/year/month for daily report information into summary activity
	    */
	public List<SummaryActivity> findByProjectidStaffidYearMonth(SummaryActivity summaryActivity) throws DAOException;

	public List<Object[]> findByProjectID(String projectID)throws DAOException;
	
	public List<SummaryActivity> findByTeam(Map<String, Object> searchData) throws DAOException;
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/09/24
	 * Explanation  : Get distinct of year by TeamID.
	 */
	public List<String> findYearsByTeam(Team team);
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/09/24
	 * Explanation  : Get distinct of month by TeamID.
	 */
	public List<String> findMonthsByTeam(Team team);
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/10/10
	 * Explanation  : Get distinct of year by StaffID.
	 */
	public List<String> findYearsByUser(User user);
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/10/10
	 * Explanation  : Get distinct of month by StaffID.
	 */
	public List<String> findMonthsByUser(User user)throws DAOException;
	
	public String findStartMonthByUser(User user) throws DAOException;
	
	public String findEndMonthByUser(User user) throws DAOException;
	
	public List<Object[]> findByStaff(User user,String startDate,String endDate) throws DAOException;
	
	public  List<String> findProjectIDByStaff(User user,String startDate,String endDate) throws DAOException;
	
	public  List<Object[]> findTotalByStaff(User user,String startDate,String endDate) throws DAOException;
	
	public List<Object[]> findByProjectID(SummaryActivity sa, String startDate, String endDate)throws DAOException;
	
	public Date findMinDateByProjectID(SummaryActivity summaryActivity) throws DAOException;
	
	public Date findMinDateByProjectIDFull(SummaryActivity summaryActivity) throws SystemException;
	
	public Date findMaxDateByProjectID(SummaryActivity summaryActivity) throws DAOException;
	
	public Date findMaxDateByProjectIDFull(SummaryActivity summaryActivity) throws DAOException;

	public Object[] findMinDateByCustomer(String customer) throws DAOException;
	
	public Object[] findMaxDateByCustomer(String customer) throws DAOException;
	
	public Date findMinDate() throws DAOException;
	
	public Date findMaxDate() throws DAOException;
	
	public Date findMinDateByNullPrjID() throws DAOException;
	
	public Date findMaxDateByNullPrjID() throws DAOException;
	
	public List<SummaryActivity> findTeamByProjectID(SummaryActivity summaryActivity) throws DAOException;
	
	public List<Object[]> findByClientOrganization(String customerName, String startDate, String endDate)throws DAOException;
	
	public List<String> findProjectIDByProjectID(SummaryActivity summaryActivity, String startDate, String endDate) throws DAOException;
	
	public List<ProjectSummaryInfo> findProjectIDByFull(String projectid, String startDate, String endDate,String customer) throws DAOException;
	
	public List<ProjectSummaryInfo> findProjectIDByNull(String projectid, String startDate, String endDate,String customer) throws DAOException;
	
	public List<ProjectSummaryInfo> findByCustomer(String startDate, String endDate,String customer) throws DAOException;
	
	public List<ProjectSummaryInfo> findByAllProject(String startDate,String endDate,String customer) throws DAOException;

	public List<String> findProjectIDByCustomer(String customer, String startDate, String endDate) throws DAOException;
	
	public List<Object[]> findByAllProject(String startDate, String endDate)throws DAOException;
	
	public List<Object[]> findByNullProject(String startDate, String endDate)throws DAOException;
	
	public List<String> findALLProjectID(String startDate, String endDate)throws DAOException;
	
	public List<ProjectSummaryInfo> findByPartialProject(String projectid, String startDate, String endDate,String customer) throws DAOException;
	
	public List<Object[]> findDetailByPartialResult(String projectId,String startDate,String endDate,String year,String month,String username) throws DAOException;
	
	public double findNullProjectActualManMonth() throws DAOException;
	
	public double findFullProjectActualManMonth(SummaryActivity sa) throws DAOException;
	
	public List<Object[]> findDetailByProjectID(String projectId, String startDate, String endDate)throws DAOException;
	
	public List<Object[]> findDetailByProjectIDResult(String projectId, String startDate, String endDate,String year,String month,String username,String teamname)throws DAOException;
	
	public List<Object[]> findUserCountDetailByProjectIDResult(String projectId, String startDate, String endDate)throws DAOException;
	
	public List<Object[]> findDetailByPartial(String projectId, String startDate, String endDate)throws DAOException;
	
	public List<Object[]> findUserCountDetailByPartialResult(String projectId, String startDate, String endDate)throws DAOException;
	
	public List<Object[]> findUserCountEachProject(String projectId, String startDate, String endDate)throws DAOException;
	
	public List<Object[]> findTeamCountDetailByPartialResult(String projectId,String startDate,String endDate)throws DAOException;
	
	public List<Object[]> findTotalUserCountEachProject(String projectId, String startDate, String endDate)throws DAOException;
	
	public List<TotalManMonthInfo> findTotalResultByPartial(String projectId, String startdate, String enddate)throws DAOException;
	
	public List<Object[]> findDetailProjectByNull(String year, String month)throws DAOException;
	
	public List<Object[]> findUserCountByNull(String startDate,String endDate)throws DAOException;
	
	public List<Object[]> findDetailByNullIDResult(String startDate,String endDate,String year,String month,String staffid,Double working_hour,String teamname)throws DAOException;
	
	public List<Double> findWorkingHour(String date) throws DAOException;
	
	public List<Object[]> findTotalResultByNull(String year,String month,Double workingHour) throws DAOException;
	
	public List<Object[]> findMemberCountEachTeamByNull(String year,String month) throws DAOException;
	
	public List<Object[]> findDetailProjectByAll(String startDate,String endDate) throws DAOException;
	
	public List<Object[]> findDetailUserByAll(String startDate,String endDate) throws DAOException;
	
	public List<Object[]> findDetailProjectResultByAll(String projectId,String year,String month,String username,Double workingHour,String teamname) throws DAOException;
	
	public List<Object[]> findDetailRecordCountByAll(String startDate,String endDate) throws DAOException;
	
	public List<Object[]> findMemberCountEachTeamByAll(String startDate,String endDate) throws DAOException;
	
	public List<Object[]> findteamListByAll(String projectId,String startDate,String endDate) throws DAOException;
	
	public List<Object[]> findteamCountByAll(String projectId,String startDate,String endDate) throws DAOException;
	
	public List<Object[]> findTotalResultByAll(String projectId,String year,String month,Double workingHour) throws DAOException;
	
	public List<Object[]> findDetailProjectByCustomer(String customer,String startDate,String endDate) throws DAOException;
	
	public List<Object[]> findDetailUserByCustomer(String customer,String startDate,String endDate) throws DAOException;
	
	public List<Object[]> findDetailProjectResultByCustomer(String projectId,String customer,String year,String month,String username,Double workingHour,String teamname) throws DAOException;
	
	public List<ProjectSummaryInfo> findDetailProjectByPartial(String projectId,String startDate,String endDate,String customer) throws DAOException;
	
	public List<ProjectSummaryInfo> findDetailProjectByFull(String projectId,String startDate,String endDate,String customer) throws DAOException;
	
	public List<TeamInfo> findDetailTeamByPartial(String projectId,String startDate,String endDate) throws DAOException;
	
	public List<StaffInfo> findDetailUserByPartial(String projectId,String teamname,String startDate, String endDate) throws DAOException;
	
	public List<ManMonthInfo> findDetailManmonthByPartial(String projectId,String teamname,String username,String startdate, String enddate) throws DAOException;
	
	public List<ManMonthInfo> findManmonthByPartial(String projectId,String teamname,String startdate, String enddate) throws DAOException;
	
	public List<ProjectSummaryInfo> findDetailNullProject(String startDate, String endDate) throws DAOException;
	
	public void findHolidayCount() throws DAOException;
	
	public List<HolidayCount> getHolidayCount()  throws DAOException;
	
	
}