/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.dailyactivity.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.dat.whm.dailyactivity.persistence.interfaces.IDailyActivityDAO;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.persistence.interfaces.IDailyReportDAO;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.summaryactivity.persistence.interfaces.ISummaryActivityDAO;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.persistence.interfaces.IUserDAO;
import com.dat.whm.userteammainlybelonghistory.entity.UserTeamMainlyBelongHistory;
import com.dat.whm.userteammainlybelonghistory.persistence.interfaces.IUserTeamMainlyBelongHistoryDAO;
@Service("DailyActivityService")
public class DailyActivityService implements IDailyActivityService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "DailyReportDAO")
	private IDailyReportDAO dailyReportDAO;
	
	@Resource(name = "DailyActivityDAO")
	private IDailyActivityDAO dailyActivityDAO;
	
	@Resource(name = "SummaryActivityDAO")
	private ISummaryActivityDAO summaryActivityDAO;
	
	@Resource(name = "UserDAO")
	private IUserDAO userDAO;
	
	@Resource(name = "UserTeamMainlyBelongHistoryDAO")
	private IUserTeamMainlyBelongHistoryDAO mmainlybelonghistory;
	
	
	
	public User findMainlyBelongHistoryByPeroid(DailyReport dr){
		List<UserTeamMainlyBelongHistory> teamHistoryList = new ArrayList<>();
		List<UserTeamMainlyBelongHistory> currentteamHistoryList = new ArrayList<>();
		User loginUser = new User();
		teamHistoryList = mmainlybelonghistory.findByPeriod(dr.getStaffID().getId(), dr.getActivityDate());
		currentteamHistoryList = mmainlybelonghistory.findByIds(dr.getStaffID().getId());
		
				if(teamHistoryList.size() > 0){
					for (UserTeamMainlyBelongHistory mainTeamList : teamHistoryList) {
						loginUser.setId(mainTeamList.getUser().getId());
						loginUser.setMainlyBelongTeam(mainTeamList.getTeam());
					
					}
					}else{
						for (UserTeamMainlyBelongHistory currentteam : currentteamHistoryList) {
						loginUser.setId(currentteam.getUser().getId());	
						loginUser.setMainlyBelongTeam(currentteam.getTeam());
						
						}
					}
	return loginUser;
	}
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public ArrayList<DailyActivity> removeDuplicate(List<DailyActivity> dailyActivityList){
		SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");
		ArrayList<DailyActivity> updateddailyActivityData = new ArrayList<DailyActivity>();
		List<DailyActivity> arr = dailyActivityList;
		int cnt= 0;
	   
         for(int i=0;i<arr.size();i++){
       for(int j=i+1;j<arr.size();j++){
    	   if(arr.get(i).getProject() != null && arr.get(j).getProject() !=null){
    		
          if(arr.get(i).getProject().getId().equals(arr.get(j).getProject().getId()) && 
        	YearFormat.format(arr.get(i).getDailyReport().getActivityDate()).equals(YearFormat.format(arr.get(j).getDailyReport().getActivityDate())) && 
        	MonthFormat.format(arr.get(i).getDailyReport().getActivityDate()).equals(MonthFormat.format(arr.get(j).getDailyReport().getActivityDate())) &&
        	arr.get(i).getDailyReport().getStaffID().getMainlyBelongTeam().getId() == arr.get(j).getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
        	 
            cnt+=1;
           
          }  
       }else if((arr.get(i).getProject() == null && arr.get(j).getProject() == null)&& 
	        	YearFormat.format(arr.get(i).getDailyReport().getActivityDate()).equals(YearFormat.format(arr.get(j).getDailyReport().getActivityDate())) && 
	        	MonthFormat.format(arr.get(i).getDailyReport().getActivityDate()).equals(MonthFormat.format(arr.get(j).getDailyReport().getActivityDate())) &&
	        	arr.get(i).getDailyReport().getStaffID().getMainlyBelongTeam().getId() == arr.get(j).getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
    	  
    	   
 	          cnt+=1;
 	         
 	             
       }
       }
       if(cnt<1){
    	   DailyActivity da = new DailyActivity();
    	  
    	   if(arr.get(i).getProject() != null){
    		  
    	   da.setProject(arr.get(i).getProject());
    	   da.setDailyReport(arr.get(i).getDailyReport());
    	  
    	   updateddailyActivityData.add(da);
         }else{
        	 
         da.setDailyReport(arr.get(i).getDailyReport());
        
         updateddailyActivityData.add(da); 
         }
    	   
       }
         cnt=0;
       
       }
		return updateddailyActivityData;
	}
	
	public ArrayList<DailyActivity> calculateTotalHourBySameProject(List<DailyActivity> dailyActivityList,List<DailyActivity> updateddailyActivityData){
		ArrayList<DailyActivity> updateddailyActivityList = new ArrayList<DailyActivity>();
		Double prj_hour = 0.0;
		Double nullprj_hour = 0.0;
		SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");
		
		for(DailyActivity da1 : updateddailyActivityData){
			DailyActivity da = new DailyActivity();
			for(DailyActivity da2 : dailyActivityList){
				
				if(da1.getProject() != null && da2.getProject() != null && 
				YearFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(YearFormat.format(da2.getDailyReport().getActivityDate()).toString()) && 
				MonthFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(MonthFormat.format(da2.getDailyReport().getActivityDate()).toString()) &&
				da1.getDailyReport().getStaffID().getMainlyBelongTeam().getId() == da2.getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
					if(da1.getProject().getId().equals(da2.getProject().getId())){
						
						User u = new User();
						u = findMainlyBelongHistoryByPeroid(da2.getDailyReport());
						DailyReport dr = new DailyReport();
						dr.setStaffID(u);
						dr.setActivityDate(da2.getDailyReport().getActivityDate());
						prj_hour += da2.getActivityHours();
						da.setProject(da2.getProject());
						da.setDailyReport(dr);
						da.setActivityHours(prj_hour);
						
					}
				}else if(da1.getProject() == null && da2.getProject() == null && 
						YearFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(YearFormat.format(da2.getDailyReport().getActivityDate()).toString()) && 
						MonthFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(MonthFormat.format(da2.getDailyReport().getActivityDate()).toString()) &&
						da1.getDailyReport().getStaffID().getMainlyBelongTeam().getId() == da2.getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
					User u = new User();
					u = findMainlyBelongHistoryByPeroid(da2.getDailyReport());
					DailyReport dr = new DailyReport();
					dr.setStaffID(u);
					dr.setActivityDate(da2.getDailyReport().getActivityDate());
					nullprj_hour += da2.getActivityHours();
					da.setProject(da2.getProject());
					da.setDailyReport(dr);
					da.setActivityHours(nullprj_hour);
					
				}
			
				
			}
			updateddailyActivityList.add(da);
			prj_hour = 0.0;
			nullprj_hour = 0.0;
			
			
		}
		
		return updateddailyActivityList;
	}
	  
	@Transactional(propagation = Propagation.REQUIRED)
	  public void save(List<DailyActivity> dailyActivityList,boolean adminFlag,String staffId,User loginUser,DailyReport dailyReport){
		
			SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
			SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");
			SimpleDateFormat yearmonthFormat = new SimpleDateFormat("yyyy-MM");
			
			SummaryActivity sa = new SummaryActivity();
			Double total_activity_hours = 0.0;
			
			Date earliest_date = new Date(); 
			
			User report_user = new User();
			ArrayList<DailyActivity> updateddailyActivityData = new ArrayList<DailyActivity>();
			ArrayList<DailyActivity> updateddailyActivityList = new ArrayList<DailyActivity>();
			
			
			SummaryActivity activity = new SummaryActivity();
	try{
			report_user = loginUser;
			
			activity.setStaff_id(report_user.getId());
			activity.setTeam_id(report_user.getMainlyBelongTeam().getId());
			activity.setYear(YearFormat.format(dailyReport.getActivityDate()));
			activity.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
			
			updateddailyActivityData = removeDuplicate(dailyActivityList);
			updateddailyActivityList = calculateTotalHourBySameProject(dailyActivityList, updateddailyActivityData);
			activity = new SummaryActivity();
			activity.setStaff_id(loginUser.getId());
			activity.setTeam_id(loginUser.getMainlyBelongTeam().getId());
			activity.setYear(YearFormat.format(dailyReport.getActivityDate()));
			activity.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
			
			List<SummaryActivity> summaryList = summaryActivityDAO
					.findByProjectidStaffidYearMonth(activity);
	         
				
			int count = 0;
			
			//Add new record with new project id and update total hours on existing project id in insert page	
			 for (DailyActivity d : updateddailyActivityList) {
				  count = 0;
				 for (SummaryActivity s : summaryList) {
					
					
					if (d.getProject() != null && s.getProject_id() != null)
					{
					if (s.getProject_id().equals(d.getProject().getId())
							&& s.getStaff_id().equals(report_user.getId())
							&& yearmonthFormat.format(s.getActivity_date())
									.equals(
											yearmonthFormat.format(dailyReport
													.getActivityDate())) && s.getTeam_id() == d.getDailyReport().getStaffID().getMainlyBelongTeam().getId()) {
						SummaryActivity sumActivity = new SummaryActivity();
						sumActivity.setStaff_id(report_user.getId());
						sumActivity.setYear(YearFormat.format(dailyReport.getActivityDate()));
						sumActivity.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
						sumActivity.setProject_id(s.getProject_id());
						total_activity_hours=s.getActivityHours();
						total_activity_hours += d.getActivityHours();
						total_activity_hours = round(total_activity_hours, 3);
						if(dailyReport.getActivityDate().compareTo(s.getActivity_date()) < 0){
							earliest_date = dailyReport.getActivityDate();
						}else{
							earliest_date = s.getActivity_date();
						}
						
						
						
						sa = new SummaryActivity();
						sa.setProject_id(d.getProject().getId());
						sa.setProjectname(d.getProject().getProjectName());
						sa.setClientorganization(d.getProject().getClientOrganization());
						sa.setTotalmanmonth(d.getProject().getTotalManMonth());
						sa.setStaff_id(report_user.getId());
						sa.setUsername(report_user.getUsername());
						sa.setFullname(report_user.getFullName());
						sa.setTeam_id(report_user.getMainlyBelongTeam().getId());
						sa.setTeamname(report_user.getMainlyBelongTeam().getTeamName());
						sa.setActivityHours(total_activity_hours);
						sa.setYear(YearFormat.format(dailyReport.getActivityDate()));
						sa.setMonth(MonthFormat.format(dailyReport
								.getActivityDate()));
						sa.setActivity_date(earliest_date);
						
						
						summaryActivityDAO.updateSummaryActivity(sa);
						
						
						summaryList = summaryActivityDAO
								.findByProjectidStaffidYearMonth(activity);
						
						
						total_activity_hours = s.getActivityHours();
						
						count++;
						
	 				} 
					
				}else if (d.getProject() == null && s.getProject_id().equals("9999") && s.getStaff_id().equals(report_user.getId())
						&& yearmonthFormat.format(s.getActivity_date())
						.equals(
								yearmonthFormat.format(dailyReport
										.getActivityDate())) && s.getTeam_id() == d.getDailyReport().getStaffID().getMainlyBelongTeam().getId()) {
					
						total_activity_hours=s.getActivityHours();
						total_activity_hours += d.getActivityHours();
						total_activity_hours = round(total_activity_hours, 3);
						if(dailyReport.getActivityDate().compareTo(s.getActivity_date()) < 0){
							earliest_date = dailyReport.getActivityDate();
						}else{
							earliest_date = s.getActivity_date();
						}
					
						
						
						sa = new SummaryActivity();
						sa.setProject_id("9999");
						sa.setProjectname("-");
						sa.setClientorganization("-");
						sa.setTotalmanmonth(0.0);
						sa.setStaff_id(report_user.getId());
						sa.setUsername(report_user.getUsername());
						sa.setFullname(report_user.getFullName());
						sa.setTeam_id(report_user.getMainlyBelongTeam().getId());
						sa.setTeamname(report_user.getMainlyBelongTeam().getTeamName());
						sa.setActivityHours(total_activity_hours);
						sa.setYear(YearFormat.format(dailyReport.getActivityDate()));
						sa.setMonth(MonthFormat.format(dailyReport
								.getActivityDate()));
						sa.setActivity_date(earliest_date);
					
						
						
						summaryActivityDAO.updateSummaryActivity(sa);
						
						
						summaryList = summaryActivityDAO
								.findByProjectidStaffidYearMonth(activity);
						
						
						count++;
					}
				
			}	
				if (count == 0) {
					sa = new SummaryActivity();
					if(d.getProject() != null ){
					sa.setProject_id(d.getProject().getId());
					sa.setProjectname(d.getProject().getProjectName());
					sa.setClientorganization(d.getProject().getClientOrganization());
					sa.setTotalmanmonth(d.getProject().getTotalManMonth());
					}else{
					sa.setProject_id("9999");
					sa.setProjectname("-");
					sa.setClientorganization("-");
					sa.setTotalmanmonth(0.0);
					}
					
					sa.setStaff_id(report_user.getId());
					sa.setUsername(report_user.getUsername());
					sa.setFullname(report_user.getFullName());
					sa.setTeam_id(report_user.getMainlyBelongTeam().getId());
					sa.setTeamname(report_user.getMainlyBelongTeam().getTeamName());
					sa.setActivityHours(round(d.getActivityHours(), 3));
					sa.setYear(YearFormat.format(dailyReport.getActivityDate()));
					sa.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
					sa.setActivity_date(dailyReport.getActivityDate());
					
					
				
					summaryActivityDAO.insertSummaryActivity(sa);
					
					
					
					summaryList = summaryActivityDAO
							.findByProjectidStaffidYearMonth(activity);
					count = 0;

				}
				
			}
			}catch (SystemException se) {
					throw se;
				}
		
		
	  }
	  
	 
	@Transactional(propagation = Propagation.REQUIRED)
	  public void calculationUpdate(ArrayList<DailyActivity> updateddailyActivityList,Date updateDate,boolean del_flag,boolean adminFlag,String staffId,User loginUser,DailyReport dailyReport )
	  {
			SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
			SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");
			boolean update_flag = false;
			Double total_activity_hours = 0.0;
			SummaryActivity sa = new SummaryActivity();
			SummaryActivity activity = new SummaryActivity();
			User report_user = new User();
			Date earliest_date = new Date(); 
			Date updatedDate = new Date();
			updatedDate = updateDate;
			
			report_user = loginUser;
			ArrayList<DailyActivity> updateddailyActivityData = new ArrayList<DailyActivity>();
			ArrayList<DailyActivity> updateddailyActivity = new ArrayList<DailyActivity>();
			
			updateddailyActivityData = removeDuplicate(updateddailyActivityList);
			updateddailyActivity = calculateTotalHourBySameProject(updateddailyActivityList, updateddailyActivityData);
			activity = new SummaryActivity();
			activity.setStaff_id(loginUser.getId());
			activity.setTeam_id(loginUser.getMainlyBelongTeam().getId());
			activity.setYear(YearFormat.format(updatedDate));
			activity.setMonth(MonthFormat.format(updatedDate));
			
			List<SummaryActivity> summaryList = summaryActivityDAO
					.findByProjectidStaffidYearMonth(activity);
			
			for(DailyActivity da:updateddailyActivity){
				update_flag = false;
			
				for (SummaryActivity s : summaryList) {
					
					total_activity_hours = s.getActivityHours();
					if(da.getProject() != null && !s.getProject_id().equals("9999") )
					{
					if(s.getProject_id().toString().equals(da.getProject().getId().toString()) 
					&& YearFormat.format(s.getActivity_date()).equals(YearFormat.format(da.getDailyReport().getActivityDate()))
					&& MonthFormat.format(s.getActivity_date()).equals(MonthFormat.format(da.getDailyReport().getActivityDate()))){
						
						total_activity_hours +=(da.getActivityHours());
						total_activity_hours = round(total_activity_hours, 3);
						if(s.getActivity_date() != null){
						if(updatedDate.compareTo(s.getActivity_date()) < 0){
							earliest_date = updatedDate;
						}else{
							earliest_date = s.getActivity_date();
						}
						}else{
							earliest_date = updatedDate;	
						}
					
						sa = new SummaryActivity();
						sa.setStaff_id(report_user.getId());
						sa.setTeam_id(report_user.getMainlyBelongTeam().getId());
						sa.setProject_id(da.getProject().getId());
						sa.setActivityHours(total_activity_hours);
						sa.setYear(YearFormat.format(updatedDate));
						sa.setMonth(MonthFormat.format(updatedDate));
						if(del_flag){
							
						sa.setActivity_date(updatedDate);	
						}else{
						sa.setActivity_date(earliest_date);
						}
						System.out.println("[UPDATE]ACTIVITY DATE ===> "+updatedDate);
						summaryActivityDAO.updateSummaryActivity(sa);
						SummaryActivity summaryactivity = new SummaryActivity();
						summaryactivity.setStaff_id(report_user.getId());
						summaryactivity.setTeam_id(report_user.getMainlyBelongTeam().getId());
						summaryactivity.setYear(YearFormat.format(updatedDate));
						summaryactivity.setMonth(MonthFormat.format(updatedDate));
						summaryActivityDAO.deleteSummaryActivity(summaryactivity);	
						
						total_activity_hours = 0.0;
						update_flag=true;
						break;
					}
				}else if (da.getProject() == null &&  s.getProject_id().equals("9999")){
						if(YearFormat.format(s.getActivity_date()).equals(YearFormat.format(da.getDailyReport().getActivityDate()))
					&& MonthFormat.format(s.getActivity_date()).equals(MonthFormat.format(da.getDailyReport().getActivityDate()))){
						total_activity_hours +=(da.getActivityHours());
						total_activity_hours = round(total_activity_hours, 3);
						if(s.getActivity_date() != null){
						if(updatedDate.compareTo(s.getActivity_date()) < 0){
							earliest_date = updatedDate;
						}else{
							earliest_date = s.getActivity_date();
						}
						}else{
							earliest_date = updatedDate;
						}
					
						sa = new SummaryActivity();
						sa.setStaff_id(report_user.getId());
						sa.setTeam_id(report_user.getMainlyBelongTeam().getId());
						sa.setProject_id("9999");
						sa.setActivityHours(total_activity_hours);
						sa.setYear(YearFormat.format(updatedDate));
						sa.setMonth(MonthFormat.format(updatedDate));
						if(del_flag){
						sa.setActivity_date(updatedDate);	
						}else{
						sa.setActivity_date(earliest_date);
							}
						System.out.println("[UPDATE]ACTIVITY DATE ===> "+updatedDate);
						summaryActivityDAO.updateSummaryActivity(sa);
						SummaryActivity summaryactivity = new SummaryActivity();
						summaryactivity.setStaff_id(report_user.getId());
						summaryactivity.setTeam_id(report_user.getMainlyBelongTeam().getId());
						summaryactivity.setYear(YearFormat.format(updatedDate));
						summaryactivity.setMonth(MonthFormat.format(updatedDate));
						summaryActivityDAO.deleteSummaryActivity(summaryactivity);	
						
						
						total_activity_hours = 0.0;
						update_flag = true;
						
						break;	
				}	
					}
				
				}
				if(!update_flag)
				{	activity = new SummaryActivity();
					if(da.getProject() != null ){
						
						activity.setProject_id(da.getProject().getId());
						activity.setProjectname(da.getProject().getProjectName());
						activity.setClientorganization(da.getProject().getClientOrganization());
						activity.setTotalmanmonth(da.getProject().getTotalManMonth());
						}else{
						
						activity.setProject_id("9999");
						activity.setProjectname("-");
						activity.setClientorganization("-");
						activity.setTotalmanmonth(0.0);
						}
					System.out.println("[INSERT]ACTIVITY DATE ===> "+updatedDate);
					activity.setStaff_id(report_user.getId());
					activity.setUsername(report_user.getUsername());
					activity.setFullname(report_user.getFullName());
					activity.setTeam_id(report_user.getMainlyBelongTeam().getId());
					activity.setTeamname(report_user.getMainlyBelongTeam().getTeamName());
					activity.setActivityHours(round(da.getActivityHours(), 3));
					activity.setYear(YearFormat.format(updatedDate));
					activity.setMonth(MonthFormat.format(updatedDate));
					activity.setActivity_date(updatedDate);
					
					summaryActivityDAO.insertSummaryActivity(activity);
					SummaryActivity summaryactivity = new SummaryActivity();
					summaryactivity.setStaff_id(report_user.getId());
					summaryactivity.setTeam_id(report_user.getMainlyBelongTeam().getId());
					summaryactivity.setYear(YearFormat.format(updatedDate));
					summaryactivity.setMonth(MonthFormat.format(updatedDate));
					summaryActivityDAO.deleteSummaryActivity(summaryactivity);	
					
					summaryList = summaryActivityDAO
							.findByProjectidStaffidYearMonth(activity);

				}
			}
		}
	
	public Date getEarliestDate(DailyActivity old_d,DailyReport dailyReport,User old_user){
		Date update_date= new Date();
		SimpleDateFormat yearmonth = new SimpleDateFormat("yyyyMM");
		List<Object[]> monthlyReportList = new ArrayList<>();
		if(old_d.getProject() != null){
			monthlyReportList = dailyActivityDAO.findReportByMonthly(old_d.getProject().getId(), old_d.getDailyReport().getStaffID().getId(), yearmonth.format(old_d.getDailyReport().getActivityDate()),old_user.getMainlyBelongTeam().getId());
			}else{
			monthlyReportList = dailyActivityDAO.findReportByMonthly(null, old_d.getDailyReport().getStaffID().getId(), yearmonth.format(old_d.getDailyReport().getActivityDate()),old_user.getMainlyBelongTeam().getId());	
			}
			
			if(monthlyReportList.size() > 0){
				for (int j = 0; j < monthlyReportList.size(); j++) {
			
				update_date = (Date)monthlyReportList.get(j)[2];
				}
				}else{
				update_date	= dailyReport.getActivityDate();
				}
		
		return update_date;
	}
	  
	  @Transactional(propagation = Propagation.REQUIRED)
	  public void updateSummaryActivity(boolean adminFlag,String staffId,User loginUser,DailyReport dailyReport,List<DailyActivity> dailyActivityList,Date oldDate,List<DailyActivity> tempDailyActivityList) {
			SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
			SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");
			SimpleDateFormat yearmonth = new SimpleDateFormat("yyyyMM");
			Double total_activity_hours = 0.0;
			boolean del_flag = false;
			boolean change_period = false;
			SummaryActivity activity = new SummaryActivity();
			User report_user = new User();
			User old_user = new User();
			User new_user = new User();
			
			report_user = loginUser;
			
			
			List<DailyActivity> newdailyActivityList = new ArrayList<DailyActivity>();
			ArrayList<DailyActivity> updateddailyActivityList = new ArrayList<DailyActivity>();
			ArrayList<DailyActivity> inserteddailyActivityList = new ArrayList<DailyActivity>();
			
			newdailyActivityList = dailyActivityList;
			Date update_date = new Date();
			double update_hour = 0.0;
			double old_hour=0.0;
			double new_hour=0.0;
			boolean edit_flag = false;
			boolean change_project=false;
			List<UserTeamMainlyBelongHistory> oldteamHistoryList = new ArrayList<>();
			List<UserTeamMainlyBelongHistory> newteamHistoryList = new ArrayList<>();
			List<UserTeamMainlyBelongHistory> currentteamHistoryList = new ArrayList<>();
			oldteamHistoryList = mmainlybelonghistory.findByPeriod(dailyReport.getStaffID().getId(), oldDate);
			newteamHistoryList = mmainlybelonghistory.findByPeriod(dailyReport.getStaffID().getId(), dailyReport.getActivityDate());
			currentteamHistoryList = mmainlybelonghistory.findByIds(dailyReport.getStaffID().getId());
			if(oldteamHistoryList.size() > 0){
			for (UserTeamMainlyBelongHistory oldmainTeamList : oldteamHistoryList) {
				
				old_user.setMainlyBelongTeam(oldmainTeamList.getTeam());
				old_user.setId(oldmainTeamList.getUser().getId());
			
			}
			}else{
				for (UserTeamMainlyBelongHistory currentteam : currentteamHistoryList) {
					
					old_user.setMainlyBelongTeam(currentteam.getTeam());
					old_user.setId(currentteam.getUser().getId());
				}
			}
			
			if(newteamHistoryList.size() > 0){
				for (UserTeamMainlyBelongHistory newmainTeamList : newteamHistoryList) {
					new_user.setMainlyBelongTeam(newmainTeamList.getTeam());
					new_user.setId(newmainTeamList.getUser().getId());
				
				}
				}else{
					for (UserTeamMainlyBelongHistory currentteam : currentteamHistoryList) {
						
					new_user.setMainlyBelongTeam(currentteam.getTeam());
					new_user.setId(currentteam.getUser().getId());
					}
				}
			if(!oldDate.toString().equals(dailyReport.getActivityDate().toString())){
			if(newdailyActivityList.size() == 0 && tempDailyActivityList.size() > 0 || newdailyActivityList.size() > 0 && tempDailyActivityList.size() == 0){
				System.out.println("Change from present to leave case########");
				boolean update_change = false;
				
				for (DailyActivity old_d : tempDailyActivityList) {
					DailyActivity da = new DailyActivity();
					
					
					if(old_d.getTaskDescription().equals("Half Leave") || old_d.getTaskDescription().equals("Full Leave")){
					da.setProject(old_d.getProject());
					da.setActivityHours(0.0);
					}else{
					da.setProject(old_d.getProject());
					da.setActivityHours(-old_d.getActivityHours());	
					}
					DailyReport dr = new DailyReport();
					dr.setStaffID(old_user);
					dr.setActivityDate(old_d.getDailyReport().getActivityDate());
					da.setDailyReport(dr);
				
					updateddailyActivityList.add(da);
					
					
					update_date = getEarliestDate(old_d, dailyReport, old_user);
					update_change = true;
					del_flag = true;
				}
				
				if(update_change){
					report_user.setMainlyBelongTeam(old_user.getMainlyBelongTeam());
					SummaryActivity old_activity = new SummaryActivity();
					
					old_activity.setStaff_id(old_user.getId());
					old_activity.setTeam_id(old_user.getMainlyBelongTeam().getId());
					old_activity.setYear(YearFormat.format(oldDate));
					old_activity.setMonth(MonthFormat.format(oldDate));
					
					if(yearmonth.format(update_date).equals(yearmonth.format(oldDate))){
				calculationUpdate(updateddailyActivityList,update_date,del_flag,adminFlag,staffId,report_user,dailyReport);	
					}else{
				calculationUpdate(updateddailyActivityList,oldDate,del_flag,adminFlag,staffId,report_user,dailyReport);			
					}
				}
				
				
				
			}
	  }
			if(!oldDate.toString().equals(dailyReport.getActivityDate().toString())){
				boolean new_change = false;
				boolean update_change = false;
				
				if((newdailyActivityList.size() < tempDailyActivityList.size()) || (newdailyActivityList.size() > tempDailyActivityList.size()) || (newdailyActivityList.size() == tempDailyActivityList.size()) ||  (tempDailyActivityList.size() == 0)
						&& oldDate.toString().equals(dailyReport.getActivityDate().toString()) && !edit_flag) {
						
					
				temp: for (int i = 0; i < tempDailyActivityList.size(); i++) {
					
					DailyActivity da = new DailyActivity();
					List<Object[]> monthlyReportList = new ArrayList<>();
					DailyReport dr = new DailyReport();
					User delete_user = new User();
					
					
					for (DailyActivity d : newdailyActivityList) {
						if (tempDailyActivityList.get(i).getTempId() == d.getTempId() ) {
							System.out.println("*****1.1*****");
							continue temp;
						}
					}
					delete_user = findMainlyBelongHistoryByPeroid(tempDailyActivityList.get(i).getDailyReport());
					update_date = tempDailyActivityList.get(i).getDailyReport().getActivityDate();
					if(tempDailyActivityList.get(i).getProject() !=null){
						monthlyReportList = dailyActivityDAO.findReportByMonthly(tempDailyActivityList.get(i).getProject().getId(), tempDailyActivityList.get(i).getDailyReport().getStaffID().getId(), yearmonth.format(tempDailyActivityList.get(i).getDailyReport().getActivityDate()),delete_user.getMainlyBelongTeam().getId());
						if(monthlyReportList.size() > 0){
						for (int j = 0; j < monthlyReportList.size(); j++) {
						dr.setStaffID(tempDailyActivityList.get(i).getDailyReport().getStaffID());
						dr.setActivityDate((Date)monthlyReportList.get(j)[2]);	
						update_date = (Date)monthlyReportList.get(j)[2];
						}
						}else{
						dr.setStaffID(tempDailyActivityList.get(i).getDailyReport().getStaffID());
						dr.setActivityDate(tempDailyActivityList.get(i).getDailyReport().getActivityDate());	
						update_date = tempDailyActivityList.get(i).getDailyReport().getActivityDate();
						}
						}else{
						monthlyReportList = dailyActivityDAO.findReportByMonthly(null, tempDailyActivityList.get(i).getDailyReport().getStaffID().getId(), yearmonth.format(tempDailyActivityList.get(i).getDailyReport().getActivityDate()),delete_user.getMainlyBelongTeam().getId());
						if(monthlyReportList.size() > 0){
						for (int j = 0; j < monthlyReportList.size(); j++) {
						dr.setStaffID(tempDailyActivityList.get(i).getDailyReport().getStaffID());
						dr.setActivityDate((Date)monthlyReportList.get(j)[2]);	
						update_date = (Date)monthlyReportList.get(j)[2];
							
							}
						}else{
						dr.setStaffID(tempDailyActivityList.get(i).getDailyReport().getStaffID());
						dr.setActivityDate(tempDailyActivityList.get(i).getDailyReport().getActivityDate());
						update_date = tempDailyActivityList.get(i).getDailyReport().getActivityDate();
						}
						}
					System.out.println("TASK DESCRITPION ==> "+tempDailyActivityList.get(i).getTaskDescription());
					if(tempDailyActivityList.get(i).getTaskDescription().equals("Half Leave") || tempDailyActivityList.get(i).getTaskDescription().equals("Full Leave")){
						System.out.println("half leave or full leave");
						da.setProject(tempDailyActivityList.get(i).getProject());
						da.setActivityHours(0.0);
						dr = new DailyReport();
						dr.setStaffID(old_user);
						dr.setActivityDate(tempDailyActivityList.get(i).getDailyReport().getActivityDate());
						da.setDailyReport(dr);
					}else{
						System.out.println("present");
						da.setProject(tempDailyActivityList.get(i).getProject());
						da.setActivityHours(-tempDailyActivityList.get(i).getActivityHours());
						dr = new DailyReport();
						dr.setStaffID(old_user);
						dr.setActivityDate(tempDailyActivityList.get(i).getDailyReport().getActivityDate());
						da.setDailyReport(dr);
					}
					report_user.setMainlyBelongTeam(new_user.getMainlyBelongTeam());
					
					updateddailyActivityList.add(da);
					del_flag = true;
					
				}
					
				}
				
				for(DailyActivity new_d: newdailyActivityList){
					boolean found = false;
					
					
					DailyActivity da = new DailyActivity();
					DailyActivity da2 = new DailyActivity();
					DailyActivity da3 = new DailyActivity();
					for (DailyActivity old_d : tempDailyActivityList) {
						
						if(old_d.getId().toString().equals(new_d.getId().toString())){
							System.out.println("*****1*****");
							old_hour=old_d.getActivityHours();
							new_hour=new_d.getActivityHours() ;
							update_date = getEarliestDate(old_d, dailyReport, old_user);
							
							if(old_user.getMainlyBelongTeam().getId() == new_user.getMainlyBelongTeam().getId()){
										change_period = false;
									}else{
										
										change_period = true;
									}
								
						if(!change_period){
							System.out.println("NOT CHANDGE PERIOD");
							if(yearmonth.format(oldDate).equals(yearmonth.format(dailyReport.getActivityDate()))){
								
								if(new_hour != old_hour){
								new_hour = new_hour - old_hour;
							
							}else{
								new_hour=new_d.getActivityHours();
							}
								da = new DailyActivity();
								da.setProject(old_d.getProject());
								da.setActivityHours(-old_hour);
								DailyReport dr = new DailyReport();
								dr.setStaffID(old_user);
								dr.setActivityDate(old_d.getDailyReport().getActivityDate());
								da.setDailyReport(dr);
							
								updateddailyActivityList.add(da);	
							
							
							
							report_user.setMainlyBelongTeam(new_user.getMainlyBelongTeam());
							activity = new SummaryActivity();
							activity.setStaff_id(report_user.getId());
							activity.setTeam_id(new_user.getMainlyBelongTeam().getId());
							activity.setYear(YearFormat.format(dailyReport.getActivityDate()));
							activity.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
							
							da2 = new DailyActivity();
							dr = new DailyReport();
							dr.setStaffID(new_user);
							dr.setActivityDate(dailyReport.getActivityDate());
							da2.setProject(new_d.getProject());
							da2.setActivityHours(new_d.getActivityHours());
							da2.setDailyReport(dr);
							
							updateddailyActivityList.add(da2);
							
							//updateddailyActivityList.add(da2);
							update_change = true;
							System.out.println("YEAR/MONTH NOT CHANGE");
							
						}else{
							System.out.println("YEAR/MONTH CHANGE");
							da = new DailyActivity();
							da.setProject(old_d.getProject());
							da.setActivityHours(-old_hour);
							DailyReport dr = new DailyReport();
							dr.setStaffID(old_user);
							dr.setActivityDate(oldDate);
							da.setDailyReport(dr);
						
							updateddailyActivityList.add(da);
							update_change = true;
							del_flag = true;
							
						}
						System.out.println("%%%%%%%%%%%%Report changed...%%%%%%%%%%%%%%%");
							}else{
								System.out.println("CHANDGE PERIOD");
								if(yearmonth.format(oldDate).equals(yearmonth.format(dailyReport.getActivityDate()))){
									System.out.println("YEAR/MONTH NOT CHANGE");
									if(new_hour != old_hour){
									new_hour = new_hour - old_hour;
								
								}else{
									new_hour=new_d.getActivityHours();
								}
									da = new DailyActivity();
									da.setProject(old_d.getProject());
									da.setActivityHours(-old_d.getActivityHours());
									DailyReport dr = new DailyReport();
									dr.setStaffID(old_user);
									dr.setActivityDate(old_d.getDailyReport().getActivityDate());
									da.setDailyReport(dr);
								
									updateddailyActivityList.add(da);	
								
									da2 = new DailyActivity();
									dr = new DailyReport();
									dr.setStaffID(new_user);
									dr.setActivityDate(dailyReport.getActivityDate());
									da2.setProject(new_d.getProject());
									da2.setActivityHours(new_d.getActivityHours());
									da2.setDailyReport(dr);
									
									updateddailyActivityList.add(da2);
								
								report_user.setMainlyBelongTeam(new_user.getMainlyBelongTeam());
								activity = new SummaryActivity();
								activity.setStaff_id(report_user.getId());
								activity.setTeam_id(new_user.getMainlyBelongTeam().getId());
								activity.setYear(YearFormat.format(dailyReport.getActivityDate()));
								activity.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
								
								//updateddailyActivityList.add(da2);
								update_change = true;
							
								
							}else{
								System.out.println("YEAR/MONTH CHANGE");
								da2 = new DailyActivity();
								da2.setProject(old_d.getProject());
								da2.setActivityHours(-old_hour);
								DailyReport dr = new DailyReport();
								dr.setStaffID(old_user);
								dr.setActivityDate(oldDate);
								da2.setDailyReport(dr);
							
								updateddailyActivityList.add(da2);
								//report_user.setMainlyBelongTeam(old_user.getMainlyBelongTeam());
								update_change = true;
								del_flag = true;
								update_date = getEarliestDate(old_d, dailyReport, old_user);
								
							}	
							
							}
								
							}
						if(new_d.getId().toString().equals(old_d.getId().toString()) && new_user.getMainlyBelongTeam().getId() == old_user.getMainlyBelongTeam().getId() 
								&& yearmonth.format(new_d.getDailyReport().getActivityDate()).equals(yearmonth.format(old_d.getDailyReport().getActivityDate()))){
							System.out.println("*****2*****");
							found = true;
							break;
						}
						
						
					}
					
						if(!found)
						{
							System.out.println("*****3*****");
							System.out.println("Activity date ###"+new_d.getDailyReport().getActivityDate());
							da3 = new DailyActivity();
							da3.setProject(new_d.getProject());
							da3.setActivityHours(new_d.getActivityHours());
							DailyReport dr = new DailyReport();
							dr.setStaffID(new_user);
							dr.setActivityDate(new_d.getDailyReport().getActivityDate());
							
							da3.setDailyReport(dr);
							activity = new SummaryActivity();
							report_user.setMainlyBelongTeam(new_user.getMainlyBelongTeam());
							activity.setStaff_id(new_user.getId());
							activity.setTeam_id(new_user.getMainlyBelongTeam().getId());
							activity.setYear(YearFormat.format(dailyReport.getActivityDate()));
							activity.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
							//update_date = getEarliestDate(new_d, dailyReport, new_user);
							
							inserteddailyActivityList.add(da3);
							del_flag = true;
							new_change = true;
							
							
						}
				}
				
				if(update_change){
					System.out.println("UPDATE CHANGE");
					
					
				del_flag=true;
				report_user.setMainlyBelongTeam(old_user.getMainlyBelongTeam());
				if(yearmonth.format(update_date).equals(yearmonth.format(oldDate))){
				
				calculationUpdate(updateddailyActivityList,update_date,del_flag,adminFlag,staffId,report_user,dailyReport);
				}else{
				calculationUpdate(updateddailyActivityList,oldDate,del_flag,adminFlag,staffId,report_user,dailyReport);	
				}
				
				}
				
				if(new_change){
					System.out.println("NEW CHANGE");
					//del_flag=true;
					report_user.setMainlyBelongTeam(new_user.getMainlyBelongTeam());
					calculationUpdate(inserteddailyActivityList,dailyReport.getActivityDate(),false,adminFlag,staffId,report_user,dailyReport);	
				}
			}
			if(oldDate.toString().equals(dailyReport.getActivityDate().toString())){
				boolean delete_activity=false;
				activity.setStaff_id(report_user.getId());
				activity.setTeam_id(report_user.getMainlyBelongTeam().getId());
				activity.setYear(YearFormat.format(dailyReport.getActivityDate()));
				activity.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
				
			//Append or reduce total hours from edit page
			for(DailyActivity new_d: newdailyActivityList){
				DailyActivity da = new DailyActivity();
				DailyActivity da2 = new DailyActivity();
				boolean found = false;
				
				
				
				
					for (DailyActivity old_d : tempDailyActivityList) {
						update_date = getEarliestDate(old_d, dailyReport, old_user);	
					if(old_d.getId().toString().equals(new_d.getId().toString()) && (new_d.getActivityHours().equals(old_d.getActivityHours())) && oldDate.toString().equals(dailyReport.getActivityDate().toString()))
						{
								if(new_d.getProject() != null && old_d.getProject() != null){
									
									if(!old_d.getProject().getId().equals(new_d.getProject().getId())  ){
										System.out.println("*****4*****");
										old_hour=-old_d.getActivityHours();
										new_hour=new_d.getActivityHours() ;
										
										da.setProject(old_d.getProject());
										da.setActivityHours(old_hour);
										DailyReport dr = new DailyReport();
										dr.setStaffID(old_user);
										dr.setActivityDate(old_d.getDailyReport().getActivityDate());
										da.setDailyReport(dr);
										report_user.setMainlyBelongTeam(old_user.getMainlyBelongTeam());
										updateddailyActivityList.add(da);
										
										
										da2.setProject(new_d.getProject());
										da2.setActivityHours(new_hour);
										dr = new DailyReport();
										dr.setStaffID(new_user);
										dr.setActivityDate(new_d.getDailyReport().getActivityDate());
										da2.setDailyReport(dr);
										report_user.setMainlyBelongTeam(new_user.getMainlyBelongTeam());
										updateddailyActivityList.add(da2);
										
										change_project = true;
									}
						    } else if((new_d.getProject() != null && old_d.getProject() == null) || (new_d.getProject() == null && old_d.getProject() != null)){
						    	System.out.println("*****5*****");
									edit_flag = true;
									
									old_hour=-old_d.getActivityHours();
									if(new_d.getProject() == null){
										new_hour=new_d.getActivityHours();	
									}else{
										new_hour=new_d.getActivityHours();
									}
									
									da.setProject(old_d.getProject());
									da.setActivityHours(old_hour);
									DailyReport dr = new DailyReport();
									dr.setStaffID(old_user);
									dr.setActivityDate(old_d.getDailyReport().getActivityDate());
									da.setDailyReport(dr);
									updateddailyActivityList.add(da);
									
									
									da2.setProject(new_d.getProject());
									da2.setActivityHours(new_hour);
									dr = new DailyReport();
									dr.setStaffID(new_user);
									dr.setActivityDate(new_d.getDailyReport().getActivityDate());
									da2.setDailyReport(dr);
									report_user.setMainlyBelongTeam(new_user.getMainlyBelongTeam());
									
									updateddailyActivityList.add(da2);
									change_project = true;
									
									
							
					    } 
						
						   
						
						}
					
					if(old_d.getId().toString().equals(new_d.getId().toString()) && (new_d.getActivityHours() < old_d.getActivityHours() || new_d.getActivityHours() > old_d.getActivityHours() ) && oldDate.toString().equals(dailyReport.getActivityDate().toString()))
						{
					if(new_d.getProject() != null && old_d.getProject() != null){
					if(!old_d.getProject().getId().equals(new_d.getProject().getId())){
						change_project = true;
						System.out.println("*****6*****");
						
						da= new DailyActivity();
						da.setProject(old_d.getProject());
						da.setActivityHours(-old_d.getActivityHours());
						DailyReport dr = new DailyReport();
						dr.setStaffID(old_user);
						dr.setActivityDate(old_d.getDailyReport().getActivityDate());
						da.setDailyReport(dr);
						updateddailyActivityList.add(da);
						
						da2= new DailyActivity();
						da2.setProject(new_d.getProject());
						da2.setActivityHours(new_d.getActivityHours());
						DailyReport dr2 = new DailyReport();
						dr = new DailyReport();
						dr2.setStaffID(old_user);
						dr2.setActivityDate(old_d.getDailyReport().getActivityDate());
						da2.setDailyReport(dr2);
						
						updateddailyActivityList.add(da2);
						
					}
					
					if(old_d.getProject().getId().equals(new_d.getProject().getId())){
						edit_flag = true;
						System.out.println("*****7*****");
					if( new_d.getActivityHours() < (old_d.getActivityHours())){
						
						update_hour = new_d.getActivityHours() - old_d.getActivityHours();	
						total_activity_hours = update_hour;
					}else if( (new_d.getActivityHours()) > (old_d.getActivityHours())){
						update_hour = new_d.getActivityHours() - old_d.getActivityHours();
						total_activity_hours = update_hour;
					}else if((new_d.getActivityHours()) == (old_d.getActivityHours())){
						
						total_activity_hours = 0.0;
					}
					da = new DailyActivity();
					da.setProject(old_d.getProject());
					da.setActivityHours(total_activity_hours);
					DailyReport dr = new DailyReport();
					dr.setStaffID(old_user);
					dr.setActivityDate(old_d.getDailyReport().getActivityDate());
					da.setDailyReport(dr);
					
					updateddailyActivityList.add(da);
					
					}
				}
					else if(new_d.getProject() == null && old_d.getProject() == null){
					
						System.out.println("*****8*****");
						edit_flag = true;
					old_hour=-old_d.getActivityHours();
					new_hour=new_d.getActivityHours() ;
					da = new DailyActivity();
					da.setProject(old_d.getProject());
					da.setActivityHours(old_hour);
					DailyReport dr = new DailyReport();
					dr.setStaffID(old_user);
					dr.setActivityDate(old_d.getDailyReport().getActivityDate());
					da.setDailyReport(dr);
					updateddailyActivityList.add(da);
					
					
					da2.setProject(new_d.getProject());
					da2.setActivityHours(new_hour);
					dr = new DailyReport();
					dr.setStaffID(new_user);
					dr.setActivityDate(new_d.getDailyReport().getActivityDate());
					da2.setDailyReport(dr);
					
					updateddailyActivityList.add(da2);
					
					
					
						}else{
							System.out.println("*****9*****");
							edit_flag = true;
							old_hour=-old_d.getActivityHours();
							new_hour=new_d.getActivityHours();
							da2 = new DailyActivity();
							da.setProject(old_d.getProject());
							da.setActivityHours(old_hour);
							DailyReport dr = new DailyReport();
							dr.setStaffID(old_user);
							dr.setActivityDate(old_d.getDailyReport().getActivityDate());
							da.setDailyReport(dr);
							updateddailyActivityList.add(da);
							
							da2 = new DailyActivity();
							da2.setProject(new_d.getProject());
							da2.setActivityHours(new_hour);
							dr = new DailyReport();
							dr.setStaffID(new_user);
							dr.setActivityDate(new_d.getDailyReport().getActivityDate());
							da2.setDailyReport(dr);
							
							updateddailyActivityList.add(da2);
						}
					}
				
					if(new_d.getId().toString().equals(old_d.getId().toString()) && oldDate.toString().equals(dailyReport.getActivityDate().toString())){
						System.out.println("*****10*****");
						edit_flag = true;
						found = true;
						break;
						
					}
					
					
				}
					if(!found)
					{System.out.println("*****11*****");
						edit_flag = true;
						da.setProject(new_d.getProject());
						da.setActivityHours(new_d.getActivityHours());
						DailyReport dr = new DailyReport();
						dr.setStaffID(new_user);
						dr.setActivityDate(new_d.getDailyReport().getActivityDate());
						da.setDailyReport(dr);
						
						updateddailyActivityList.add(da);
						activity = new SummaryActivity();
						loginUser.setMainlyBelongTeam(new_user.getMainlyBelongTeam());
						activity.setStaff_id(new_user.getId());
						activity.setTeam_id(new_user.getMainlyBelongTeam().getId());
						activity.setYear(YearFormat.format(dailyReport.getActivityDate()));
						activity.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
						
						
					}
		
			}
			
		
			//delete activities record  from edit page
			if((newdailyActivityList.size() < tempDailyActivityList.size()) || (newdailyActivityList.size() > tempDailyActivityList.size()) || (newdailyActivityList.size() == tempDailyActivityList.size())  ||  (tempDailyActivityList.size() == 0)
					&& oldDate.toString().equals(dailyReport.getActivityDate().toString()) && !edit_flag) {
					
				
				
			temp: for (int i = 0; i < tempDailyActivityList.size(); i++) {
				
				DailyActivity da = new DailyActivity();
				List<Object[]> monthlyReportList = new ArrayList<>();
				DailyReport dr = new DailyReport();
				User delete_user = new User();
				
				
				for (DailyActivity d : newdailyActivityList) {
					if (tempDailyActivityList.get(i).getTempId() == d.getTempId() ) {
						System.out.println("*****12*****");
						
						continue temp;
					}
				}
				delete_user = findMainlyBelongHistoryByPeroid(tempDailyActivityList.get(i).getDailyReport());
				update_date = tempDailyActivityList.get(i).getDailyReport().getActivityDate();
				if(tempDailyActivityList.get(i).getProject() !=null){
					monthlyReportList = dailyActivityDAO.findReportByMonthly(tempDailyActivityList.get(i).getProject().getId(), tempDailyActivityList.get(i).getDailyReport().getStaffID().getId(), yearmonth.format(tempDailyActivityList.get(i).getDailyReport().getActivityDate()),delete_user.getMainlyBelongTeam().getId());
					if(monthlyReportList.size() > 0){
					for (int j = 0; j < monthlyReportList.size(); j++) {
					dr = new DailyReport();
					dr.setStaffID(tempDailyActivityList.get(i).getDailyReport().getStaffID());
					dr.setActivityDate((Date)monthlyReportList.get(j)[2]);	
					update_date = (Date)monthlyReportList.get(j)[2];
					}
					}else{
					dr = new DailyReport();
					dr.setStaffID(tempDailyActivityList.get(i).getDailyReport().getStaffID());
					dr.setActivityDate(tempDailyActivityList.get(i).getDailyReport().getActivityDate());	
					update_date = tempDailyActivityList.get(i).getDailyReport().getActivityDate();
					}
					}else{
					monthlyReportList = dailyActivityDAO.findReportByMonthly(null, tempDailyActivityList.get(i).getDailyReport().getStaffID().getId(), yearmonth.format(tempDailyActivityList.get(i).getDailyReport().getActivityDate()),delete_user.getMainlyBelongTeam().getId());
					if(monthlyReportList.size() > 0){
					for (int j = 0; j < monthlyReportList.size(); j++) {
					dr = new DailyReport();
					dr.setStaffID(tempDailyActivityList.get(i).getDailyReport().getStaffID());
					dr.setActivityDate((Date)monthlyReportList.get(j)[2]);	
					update_date = (Date)monthlyReportList.get(j)[2];
						
						}
					}else{
					dr = new DailyReport();
					dr.setStaffID(tempDailyActivityList.get(i).getDailyReport().getStaffID());
					dr.setActivityDate(tempDailyActivityList.get(i).getDailyReport().getActivityDate());
					update_date = tempDailyActivityList.get(i).getDailyReport().getActivityDate();
					}
					}
				
				
				if(tempDailyActivityList.get(i).getTaskDescription().equals("Half Leave") || tempDailyActivityList.get(i).getTaskDescription().equals("Full Leave")){
					
					da = new DailyActivity();
					da.setProject(tempDailyActivityList.get(i).getProject());
					da.setActivityHours(0.0);
					dr = new DailyReport();
					dr.setStaffID(old_user);
					dr.setActivityDate(tempDailyActivityList.get(i).getDailyReport().getActivityDate());
					da.setDailyReport(dr);
				}else{
					
					da = new DailyActivity();
					da.setProject(tempDailyActivityList.get(i).getProject());
					da.setActivityHours(-tempDailyActivityList.get(i).getActivityHours());
					dr = new DailyReport();
					dr.setStaffID(old_user);
					dr.setActivityDate(tempDailyActivityList.get(i).getDailyReport().getActivityDate());
					da.setDailyReport(dr);
				}
				report_user.setMainlyBelongTeam(old_user.getMainlyBelongTeam());
				
				updateddailyActivityList.add(da);
				del_flag = true;
				delete_activity = true;
			}
				
			}
			
			if(change_project || edit_flag || delete_activity){
				System.out.println("EDIT OR CHANGE FLAG");
				
			del_flag=true;	
			calculationUpdate(updateddailyActivityList,update_date,del_flag,adminFlag,staffId,report_user,dailyReport);
			}
			}
				
		}
	   
	@Transactional(propagation = Propagation.REQUIRED)
	public DailyActivity addNewDailyActivity(DailyActivity dailyActivity) throws SystemException {
		try {
			logger.debug("addDailyActivity() method has been started.");
			dailyActivity = dailyActivityDAO.insert(dailyActivity);
			logger.debug("addDailyActivity() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewDailyActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a DailyActivity", e);
		}
		return dailyActivity;
	}


	@Transactional(propagation = Propagation.REQUIRED)
	public void saveDailyReportData(DailyReport dailyReport,List<DailyActivity> dailyActivityList,List<DailyActivity> tempDailyActivityList,boolean editFlag,List<DailyReport> result,boolean adminflag,String staff,User loginuser,Date oldDate) throws SystemException {
		try {
			
			logger.debug("saveDailyReportData() method has been started.");
			SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
			SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");	 
			
			
			for (DailyActivity d : dailyActivityList) {
				d.setDailyReport(dailyReport);
			}	
			
			if (editFlag) {
				
				dailyReportDAO.update(dailyReport);				
				a: for (int i = 0; i < dailyActivityList.size(); i++) {
					
					for (DailyActivity d : tempDailyActivityList) {
						if(!d.getTaskDescription().equals("Half Leave")){
						if (dailyActivityList.get(i).getTempId() == d.getTempId()) {
							
							dailyActivityDAO.update(dailyActivityList.get(i));
							
							System.out.println("REPORT CHANGE BY UPDATE");
							
							continue a;	
							
						}
						
						}
						
						
						
					}
					System.out.println("REPORT CHANGE BY INSERT");
					
					dailyActivityDAO.insert(dailyActivityList.get(i));
					
					
				}
			
	
				updateSummaryActivity(adminflag, staff, loginuser, dailyReport, dailyActivityList, oldDate, tempDailyActivityList);
				SummaryActivity sa = new SummaryActivity();
				sa.setStaff_id(dailyReport.getStaffID().getId());
				sa.setTeam_id(dailyReport.getStaffID().getMainlyBelongTeam().getId());
				sa.setYear(YearFormat.format(dailyReport.getActivityDate()));
				sa.setMonth(MonthFormat.format(dailyReport.getActivityDate()));
				summaryActivityDAO.deleteSummaryActivity(sa);
				
				if(tempDailyActivityList.size() == 0 || dailyActivityList.size() == 0){
					
				List<DailyReport> dailyreportlist = dailyReportDAO.findByDate(oldDate, loginuser);
				if(dailyreportlist.size() > 0){
					for(DailyReport dr: dailyreportlist){
						 List<DailyActivity> activitylist = dailyActivityDAO.findActivityByReportID(dr);
						 if(activitylist.size() == 0 ){
							 if(!oldDate.toString().equals(dailyReport.getActivityDate().toString())){
						dailyReportDAO.delete(dailyreportlist);
							 }
						 }else{
							 if(!oldDate.toString().equals(dailyReport.getActivityDate().toString())){
							 for (int i = 0; i < activitylist.size(); i++) {
								dailyActivityDAO.delete(activitylist.get(i));
							}
							 dailyReportDAO.delete(dailyreportlist);	
						 }
						 }
						 
					}
				}
				}else{
					boolean delete_action = false;
					System.out.println("ROW DELETE");
					List<DailyReport> dailyreportlist = dailyReportDAO.findByDate(oldDate, loginuser);
					if(dailyreportlist.size() > 0){
					
						for(DailyReport dr: dailyreportlist){
							 List<DailyActivity> activitylist = dailyActivityDAO.findActivityByReportID(dr);
							 if(activitylist.size() == 0 ){
							dailyReportDAO.delete(dailyreportlist);
							 }else{
								 if(!oldDate.toString().equals(dailyReport.getActivityDate().toString())){
									 for (int i = 0; i < activitylist.size(); i++) {
										 if(activitylist.get(i).getTaskDescription().equals("Half Leave") || activitylist.get(i).getTaskDescription().equals("Full Leave")){
										dailyActivityDAO.delete(activitylist.get(i));
										dailyReportDAO.delete(dailyreportlist);
										delete_action = true;
										 }
									}
										
								 }
								 }
							 
						}
					}
					if(!delete_action){
				a: for (int i = 0; i < tempDailyActivityList.size(); i++) {
					
					for (DailyActivity d : dailyActivityList) {
						if (tempDailyActivityList.get(i).getTempId() == d.getTempId()) {
							
							continue a;
						}
					}
					dailyActivityDAO.delete(tempDailyActivityList.get(i));
				}
				}
				}
				
				System.out.println("edit flag##############");
				
				
			}else{
				if (!result.isEmpty()) {
					System.out.println("UPDATE##############");
					dailyReportDAO.update(dailyReport);
					
				} else {
					System.out.println("ADD##############");
					System.out.println("Daily Report = "+dailyReport);
					dailyReportDAO.insert(dailyReport);
					
				}
				for (DailyActivity d : dailyActivityList) {
					dailyActivityDAO.insert(d);
				}
				save(dailyActivityList,adminflag, staff, loginuser, dailyReport);
				
			}
			
			
			logger.debug("saveDailyReportData() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("saveDailyReportData() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to saveDailyReportData", e);
		}catch (OptimisticLockException ote) {
			throw ote;
		} catch (LockTimeoutException lte) {
			throw lte;
		} catch (DataAccessException de) {
			throw de;
		} catch (PersistenceException pe) {
			throw pe;
		}
		
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public DailyActivity updateDailyActivity(DailyActivity dailyActivity) throws SystemException {
		try {
			logger.debug("updateDailyActivity() method has been started.");
			dailyActivity = dailyActivityDAO.update(dailyActivity);
			logger.debug("updateDailyActivity() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateDailyActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to update DailyActivity", e);
		}
		return dailyActivity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDailyActivity(DailyActivity dailyActivity) throws SystemException {
		try {
			logger.debug("deleteDailyActivity() method has been started.");
			dailyActivityDAO.delete(dailyActivity);
			logger.debug("deleteDailyActivity() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteDailyActivity() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to delete DailyActivity", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyActivity> findActivityByReportID(DailyReport dailyReport) throws SystemException {
		logger.debug("findActivityByReportID() method has been started.");
		List<DailyActivity> result = dailyActivityDAO.findActivityByReportID(dailyReport);
		logger.debug("findActivityByReportID() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDailyActivityByReportID(DailyReport dailyReport) throws SystemException {

		try {
			logger.debug("deleteDailyActivityByReportID() method has been started.");
			dailyActivityDAO.deleteByReportID(dailyReport);
			logger.debug("deleteDailyActivityByReportID() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteDailyActivityByReportID() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to delete DailyActivity", e);
		}

	}
	
	@Override
	public List<DailyActivity> findAllActivitiesByCriteria(
			DailyReport dailyReport, DailyActivity dailyActivity)
			throws SystemException {
		List<DailyActivity> result = null;
		try {
			logger.debug("findDailyActivityListtforExcel() method has been started.");
			result = dailyActivityDAO.findAllActivitiesByCriteria(dailyReport, dailyActivity);
			logger.debug("findReportIDListforExcel() method has been finished.");
		} catch (DAOException e) {
			logger.error("findReportIDListforExcel() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DailyReport by username and date", e);
		}
		return result;
	}

	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/11/02
	 * Explanation  : Get maximum date according to selected project_id.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findActivityDateByProjectID(String projectID) throws SystemException {
		Date result;

		try {
			logger.debug("findActivityDateByProjectID() method has been started.");
			result = dailyActivityDAO.findActivityDateByProjectID(projectID);
			logger.debug("findActivityDateByProjectID() method has been finished.");
		} catch (DAOException e) {
			logger.error("findActivityDateByProjectID() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to findActivityDateByProjectID", e);
		}
		return result;
	}

	/**
	 * Created By   : Kyi Saw Win
	 * Created Date : 2017/09/25
	 * Explanation  : Get minimum date according to selected project_id.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDateByProjectID(String projectId) throws SystemException{
	Date result = new Date();

	try {
		logger.debug("findMinDateByProjectID() method has been started.");
		result = dailyActivityDAO.findMinDateByProjectID(projectId);
		logger.debug("findMinDateByProjectID() method has been finished.");
	} catch (DAOException e) {
		logger.error("findMinDateByProjectID() method has been failed.");
		throw new SystemException(e.getErrorCode(),
				"Faield to find MinDateByProjectID", e);
	}
	return result;
	}
	
	/**
	 * Created By   : Kyi Saw Win
	 * Created Date : 2017/09/25
	 * Explanation  : Get maximum date according to selected project_id.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDateByProjectID(String projectId) throws SystemException{
	Date result = new Date();

	try {
		logger.debug("findMaxDateByProjectID() method has been started.");
		result = dailyActivityDAO.findMaxDateByProjectID(projectId);
		logger.debug("findMaxDateByProjectID() method has been finished.");
	} catch (DAOException e) {
		logger.error("findMaxDateByProjectID() method has been failed.");
		throw new SystemException(e.getErrorCode(),
				"Faield to find MaxDateByProjectID", e);
	}
	return result;
	}
	
	/**
	 * Created By   : Kyi Saw Win
	 * Created Date : 2017/09/25
	 * Explanation  : Get summarization of activity_hours according to selected project_id.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public double findSumOfActivityHrByProjectID(String projectId) throws SystemException{
	double result = 0.0;

	try {
		logger.debug("findSumOfActivityHrByProjectID() method has been started.");
		result = dailyActivityDAO.findSumOfActivityHrByProjectID(projectId);
		logger.debug("findSumOfActivityHrByProjectID() method has been finished.");
	} catch (DAOException e) {
		logger.error("findSumOfActivityHrByProjectID() method has been failed.");
		throw new SystemException(e.getErrorCode(),
				"Faield to find SumOfActivityHrByProjectID", e);
	}
	return result;
	}
	
	/**
	 * Created By   : Kyi Saw Win
	 * Created Date : 2017/09/25
	 * Explanation  : Get count of activity_date according to selected project_id.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int countActivityDateByProjectID(String projectId) throws SystemException{
	int result = 0;

	try {
		logger.debug("countActivityDateByProjectID() method has been started.");
		result = dailyActivityDAO.countActivityDateByProjectID(projectId);
		logger.debug("countActivityDateByProjectID() method has been finished.");
	} catch (DAOException e) {
		logger.error("countActivityDateByProjectID() method has been failed.");
		throw new SystemException(e.getErrorCode(),
				"Faield to find countActivityDateByProjectID", e);
	}
	return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findReportByMonthly(String projectid,String staffid,String reportdate,int team_id) throws SystemException{
	List<Object[]> result = null;

	try {
		logger.debug("findReportByMonthly() method has been started.");
		result = dailyActivityDAO.findReportByMonthly(projectid,staffid,reportdate,team_id);
		logger.debug("findReportByMonthly() method has been finished.");
	} catch (DAOException e) {
		logger.error("findReportByMonthly() method has been failed.");
		throw new SystemException(e.getErrorCode(),
				"Faield to find findReportByMonthly", e);
	}
	return result;
	}


	
}