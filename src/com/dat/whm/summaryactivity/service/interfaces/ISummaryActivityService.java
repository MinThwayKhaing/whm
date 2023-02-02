/***************************************************************************************
 * @author
 * @Date 
 * @Version 
 * @Purpose 
 *
 ***************************************************************************************/
package com.dat.whm.summaryactivity.service.interfaces;

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

public interface ISummaryActivityService {
	
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: insert daily report information into summary activity
	    */ 
	public SummaryActivity insertSummaryActivity(SummaryActivity summaryActivity) throws SystemException;
	
		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: update daily report information into summary activity
	    */ 
	public SummaryActivity updateSummaryActivity(SummaryActivity summaryActivity) throws SystemException;
	
	/**
	 * Revised By : Htet Wai Yum 
	 * Revised Date : 2018/09/10 
	 * Explanation: Update activity date into summary activities table for daily registration page
	 */
	public SummaryActivity updateActivityDate(SummaryActivity summaryActivity) throws SystemException;
	
	/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: delete daily report information into summary activity
	    */ 
	public SummaryActivity deleteSummaryActivity(SummaryActivity summaryActivity) throws SystemException;
	

		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: find daily report information into summary activity
	    */ 
	public List<SummaryActivity> findAll() throws SystemException;
	
	public double findByProjectId(SummaryActivity summaryActivity) throws SystemException;
	

		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: find by projectid/staffid/year/month for daily report information into summary activity
	    */
	public List<SummaryActivity> findByProjectidStaffidYearMonth(SummaryActivity summaryActivity) throws SystemException;

	public List<Object[]> findByProjectID(String projectID) throws SystemException;
	
	public List<SummaryActivity> findByTeam(Map<String, Object> searchData) throws SystemException;
	
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
	 * Explanation  : Get distinct of month by StaffID.
	 */
	public List<String> findYearsByUser(User user);
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/10/10
	 * Explanation  : Get distinct of month by StaffID.
	 */
	public List<String> findMonthsByUser(User user) throws SystemException;
	
	public String findStartMonthByUser(User user) throws SystemException;
	
	public String findEndMonthByUser(User user) throws SystemException;
	
	public List<Object[]> findByStaff(User user,String startDate,String endDate) throws SystemException;
	
	public  List<String> findProjectIDByStaff(User user,String startDate,String endDate) throws SystemException;
	
	public List<Object[]> findTotalByStaff(User user,String startDate,String endDate) throws SystemException;
	
public List<Object[]> findByProjectID(SummaryActivity sa, String startDate, String endDate) throws SystemException;
	
	public Date findMinDateByProjectID(SummaryActivity summaryActivity) throws SystemException;
	
	public Date findMinDateByProjectIDFull(SummaryActivity summaryActivity) throws SystemException;
	
	public Date findMaxDateByProjectID(SummaryActivity summaryActivity) throws SystemException;
	
	public Date findMaxDateByProjectIDFull(SummaryActivity summaryActivity) throws SystemException;
	
	public Object[] findMinDateByCustomer(String customer) throws SystemException;
	
	public Object[] findMaxDateByCustomer(String customer) throws SystemException;
	
	public Date findMinDate() throws SystemException;
	
	public Date findMaxDate() throws SystemException;
	
	public Date findMinDateByNullPrjID() throws SystemException;
	
	public Date findMaxDateByNullPrjID() throws SystemException;
	
	public List<SummaryActivity> findTeamByProjectID(SummaryActivity summaryActivity) throws SystemException;
	
	public List<Object[]> findByClientOrganization(String customerName, String startDate, String endDate)throws SystemException;
	
	public List<String> findProjectIDByProjectID(SummaryActivity summaryActivity, String startDate, String endDate) throws SystemException;
	
	public List<ProjectSummaryInfo> findProjectIDByFull(String projectid, String startDate, String endDate,String customer) throws SystemException;
	
	public List<ProjectSummaryInfo> findByAllProject(String startDate,String endDate,String customer) throws SystemException;
	
	public List<ProjectSummaryInfo> findProjectIDByNull(String projectid, String startDate, String endDate,String customer) throws SystemException;
	
	public List<ProjectSummaryInfo> findByCustomer(String startDate, String endDate,String customer) throws SystemException;
	
	public List<String> findProjectIDByCustomer(String customer, String startDate, String endDate) throws SystemException;
	
	public List<Object[]> findByAllProject(String startDate, String endDate)throws SystemException;
	
	public List<Object[]> findByNullProject(String startDate, String endDate)throws SystemException;
	
	public List<String> findALLProjectID(String startDate, String endDate)throws SystemException;

	public List<ProjectSummaryInfo> findByPartialProject(String projectid,String startDate,String endDate,String customer) throws SystemException;
	
	public List<Object[]> findDetailByPartialResult(String projectId,String startDate,String endDate,String year,String month,String username) throws SystemException;
	
	public double findNullProjectActualManMonth() throws SystemException;
	public double findFullProjectActualManMonth(SummaryActivity sa) throws SystemException;
	
	public List<Object[]> findDetailByProjectID(String projectId, String startDate, String endDate) throws SystemException;
	
	public List<Object[]> findDetailByProjectIDResult(String projectId, String startDate, String endDate,String year,String month,String username,String teamname) throws SystemException;
	
	public List<Object[]> findUserCountDetailByProjectIDResult(String projectId, String startDate, String endDate)throws SystemException;
	
	public List<Object[]> findDetailByPartial(String projectId, String startDate, String endDate)throws SystemException;
	
	public List<Object[]> findUserCountDetailByPartialResult(String projectId, String startDate, String endDate)throws SystemException;
	
	public List<Object[]> findUserCountEachProject(String projectId, String startDate, String endDate)throws SystemException;
	
	public List<Object[]> findTeamCountDetailByPartialResult(String projectId,String startDate,String endDate)throws SystemException;
	
	public List<Object[]> findTotalUserCountEachProject(String projectId, String startDate, String endDate)throws SystemException;
	
	public List<TotalManMonthInfo> findTotalResultByPartial(String projectId, String startdate, String enddate)throws SystemException;
	
	public List<Object[]> findDetailProjectByNull(String year, String month)throws SystemException;
	
	public List<Object[]> findUserCountByNull(String startDate,String endDate)throws SystemException;
	
	public List<Object[]> findDetailByNullIDResult(String startDate,String endDate,String year,String month,String staffid,Double working_hour,String teamname)throws SystemException;
	
	public List<Double> findWorkingHour(String date) throws SystemException;
	
	public List<Object[]> findTotalResultByNull(String year,String month,Double workingHour) throws SystemException;
	
	public List<Object[]> findMemberCountEachTeamByNull(String year,String month) throws SystemException;
	
	public List<Object[]> findDetailProjectByAll(String startDate,String endDate) throws SystemException;
	
	public List<Object[]> findDetailUserByAll(String startDate,String endDate) throws SystemException;
	
	public List<Object[]> findDetailProjectResultByAll(String projectId,String year,String month,String username,Double workingHour,String teamname) throws SystemException;
	
	public List<Object[]> findDetailRecordCountByAll(String startDate,String endDate) throws SystemException;
	
	public List<Object[]> findMemberCountEachTeamByAll(String startDate,String endDate) throws SystemException;
	
	public List<Object[]> findteamListByAll(String projectId,String startDate,String endDate) throws SystemException;
	
	public List<Object[]> findteamCountByAll(String projectId,String startDate,String endDate) throws SystemException;
	
	public List<Object[]> findTotalResultByAll(String projectId,String year,String month,Double workingHour) throws SystemException;
	
	public List<Object[]> findDetailProjectByCustomer(String customer,String startDate,String endDate) throws SystemException;
	
	public List<Object[]> findDetailUserByCustomer(String customer,String startDate,String endDate) throws SystemException;
	
	public List<Object[]> findDetailProjectResultByCustomer(String projectId,String customer,String year,String month,String username,Double workingHour,String teamname) throws SystemException;
	
	public List<ProjectSummaryInfo> findDetailProjectByPartial(String projectId,String startDate,String endDate,String customer) throws SystemException;
	
	public List<ProjectSummaryInfo> findDetailProjectByFull(String projectId,String startDate,String endDate,String customer) throws SystemException;
	
	public List<TeamInfo> findDetailTeamByPartial(String projectId,String startDate,String endDate) throws SystemException;
	
	public List<StaffInfo> findDetailUserByPartial(String projectId,String teamname,String startDate, String endDate) throws SystemException;
	
	public List<ManMonthInfo> findDetailManmonthByPartial(String projectId,String teamname,String username,String startdate, String enddate) throws SystemException;
	
	public List<ManMonthInfo> findManmonthByPartial(String projectId,String teamname,String startdate, String enddate) throws SystemException;
	
	
	public List<ProjectSummaryInfo> findDetailNullProject(String startDate, String endDate) throws SystemException;
	
	public void findHolidayCount() throws SystemException;
	
	public List<HolidayCount> getHolidayCount()  throws SystemException;
}