/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.dailyreport.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.persistence.interfaces.IDailyActivityDAO;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.persistence.interfaces.IDailyReportDAO;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.summaryactivity.persistence.interfaces.ISummaryActivityDAO;
import com.dat.whm.user.entity.User;
import com.dat.whm.userteammainlybelonghistory.entity.UserTeamMainlyBelongHistory;
import com.dat.whm.userteammainlybelonghistory.persistence.interfaces.IUserTeamMainlyBelongHistoryDAO;
import com.dat.whm.web.common.SearchDate;
import com.dat.whm.web.weeklyreport.WeeklyData;

@Service("DailyReportService")
public class DailyReportService implements IDailyReportService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "DailyReportDAO")
	private IDailyReportDAO dailyReportDAO;
	
	@Resource(name = "DailyActivityDAO")
	private IDailyActivityDAO dailyActivityDAO;
	
	@Resource(name = "SummaryActivityDAO")
	private ISummaryActivityDAO summaryActivityDAO;
	
	@Resource(name = "UserTeamMainlyBelongHistoryDAO")
	private IUserTeamMainlyBelongHistoryDAO mmainlybelonghistory;
	
	List<SummaryActivity> summaryList = new ArrayList<SummaryActivity>();
	
	
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
	
	/**
	 * Revised By : Htet Wai Yum Revised Date : 2018/09/10 Explanation: Delete
	 * daily report information into summary activities table for daily
	 * registration page
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDailyReportData(List<DailyReport> checkboxlist,List<DailyActivity> dailyActivityList,List<DailyReport> dailyReportList) {
	
		SimpleDateFormat yearmonth = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");
		Double total_activity_hours = 0.0;
		SummaryActivity sa = new SummaryActivity();
		SummaryActivity activity = new SummaryActivity();
		List<DailyActivity> dailyActivityListResult = new ArrayList<DailyActivity>();
		dailyActivityList = new ArrayList<DailyActivity>();
		summaryList = new ArrayList<SummaryActivity>();
		String null_value="9999";
		Double prj_hour = 0.0;
		Double nullprj_hour = 0.0;
	
		
		for (DailyReport dr : checkboxlist) {
			dailyActivityListResult = dailyActivityDAO.findActivityByReportID(dr);
			User mainlybelonguser = new User();
			mainlybelonguser = findMainlyBelongHistoryByPeroid(dr);
			activity = new SummaryActivity();
			activity.setStaff_id(dr.getStaffID().getId());
			activity.setTeam_id(mainlybelonguser.getMainlyBelongTeam().getId());
			activity.setYear(YearFormat.format(dr.getActivityDate()));
			activity.setMonth(MonthFormat.format(dr.getActivityDate()));
			activity.setActivity_date(dr.getActivityDate());
			for(DailyActivity da:dailyActivityListResult){
				if(da.getTaskDescription().equals("Half Leave") || da.getTaskDescription().equals("Full Leave")){
					DailyActivity daa = new DailyActivity();
					daa.setProject(da.getProject());
					daa.setActivityHours(0.0);
					daa.setDailyReport(da.getDailyReport());
					dailyActivityList.add(daa);	
				}else{
				DailyActivity daa = new DailyActivity();
				daa.setProject(da.getProject());
				daa.setActivityHours(da.getActivityHours());
				daa.setDailyReport(da.getDailyReport());
				dailyActivityList.add(daa);
				}
			}
			
			 List<SummaryActivity> summary = new ArrayList<>();
			summary = summaryActivityDAO
					.findByProjectidStaffidYearMonth(activity);
			for (SummaryActivity s : summary) {
				SummaryActivity sativity = new SummaryActivity();
				sativity.setStaff_id(s.getStaff_id());
				sativity.setTeam_id(s.getTeam_id());
				sativity.setActivityHours(s.getActivityHours());
				sativity.setProject_id(s.getProject_id());
				sativity.setActivity_date(s.getActivity_date());
				
				summaryList.add(sativity);
			}

		}
		

		if (checkboxlist.size() != 0 || checkboxlist.size() < 0) {
			dailyReportDAO.delete(checkboxlist);

			for (DailyReport deleteCandidate : checkboxlist) {

				dailyActivityDAO.deleteByReportID(deleteCandidate);
			   dailyReportList.remove(deleteCandidate);
				//setDailyReportList(this.dailyReportList);

			}
			ArrayList<DailyActivity> updateddailyActivityList = new ArrayList<DailyActivity>();
			ArrayList<DailyActivity> updateddailyActivityData = new ArrayList<DailyActivity>();
			ArrayList<DailyActivity> updateddailyActivity = new ArrayList<DailyActivity>();
			System.out.println("DAILY ACTIVITY LIST SIZE = "+dailyActivityList.size());
				for (DailyActivity da : dailyActivityList) {
					DailyReport report = new DailyReport();	
					User delete_user = new User();
					delete_user = findMainlyBelongHistoryByPeroid(da.getDailyReport());
					//User report_user = new User();
					List<Object[]> monthlyReportList = new ArrayList<>();
					if(da.getProject() !=null){
					monthlyReportList = dailyActivityDAO.findReportByMonthly(da.getProject().getId(), da.getDailyReport().getStaffID().getId(), yearmonth.format(da.getDailyReport().getActivityDate()),delete_user.getMainlyBelongTeam().getId());
					if(monthlyReportList.size() == 0){
						//report_user =findMainlyBelongHistoryByPeroid(da.getDailyReport());
						report.setId(da.getDailyReport().getId());
						report.setStaffID(delete_user);
						report.setActivityDate(da.getDailyReport().getActivityDate());		
					}else
					{
					for (int i = 0; i < monthlyReportList.size(); i++) {
						//report_user =findMainlyBelongHistoryByPeroid(da.getDailyReport());
						report.setId(da.getDailyReport().getId());
						report.setStaffID(delete_user);
						report.setActivityDate((Date)monthlyReportList.get(i)[2]);	
					}
					}
					}else{
					monthlyReportList = dailyActivityDAO.findReportByMonthly(null, da.getDailyReport().getStaffID().getId(), yearmonth.format(da.getDailyReport().getActivityDate()),delete_user.getMainlyBelongTeam().getId());
					if(monthlyReportList.size() == 0){
						//report_user =findMainlyBelongHistoryByPeroid(da.getDailyReport());
						report.setId(da.getDailyReport().getId());
						report.setStaffID(delete_user);
						report.setActivityDate(da.getDailyReport().getActivityDate());		
					}else{
					for (int i = 0; i < monthlyReportList.size(); i++) {
						//report_user =findMainlyBelongHistoryByPeroid(da.getDailyReport());
						report.setId(da.getDailyReport().getId());
						report.setStaffID(delete_user);
						report.setActivityDate((Date)monthlyReportList.get(i)[2]);		
						
						}
					}
					}	
					
					DailyActivity dd = new DailyActivity();
					//hour += -da.getActivityHours();
					dd.setDailyReport(report);
					dd.setProject(da.getProject());
					dd.setActivityHours(-da.getActivityHours());
					
					updateddailyActivityList.add(dd);
					
				}
				
				
				List<DailyActivity> arr = updateddailyActivityList;
				int cnt= 0;
			   
		         for(int i=0;i<arr.size();i++){
		       for(int j=i+1;j<arr.size();j++){
		    	   if(arr.get(i).getProject() != null && arr.get(j).getProject() !=null){
		          if(arr.get(i).getProject().getId().contains(arr.get(j).getProject().getId()) && 
		        	YearFormat.format(arr.get(i).getDailyReport().getActivityDate()).contains(YearFormat.format(arr.get(j).getDailyReport().getActivityDate())) && 
		        	MonthFormat.format(arr.get(i).getDailyReport().getActivityDate()).contains(MonthFormat.format(arr.get(j).getDailyReport().getActivityDate())) &&
		        	arr.get(i).getDailyReport().getStaffID().getMainlyBelongTeam().getId() == arr.get(j).getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
		        	 
		            cnt+=1;
		           
		          }  
		       }else if((arr.get(i).getProject() == null && arr.get(j).getProject() == null)&& 
			        	YearFormat.format(arr.get(i).getDailyReport().getActivityDate()).contains(YearFormat.format(arr.get(j).getDailyReport().getActivityDate())) && 
			        	MonthFormat.format(arr.get(i).getDailyReport().getActivityDate()).contains(MonthFormat.format(arr.get(j).getDailyReport().getActivityDate())) &&
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
			
		         System.out.println("###updateddailyActivityData = "+updateddailyActivityData.size());
			for(DailyActivity da1 : updateddailyActivityData){
				DailyActivity da = new DailyActivity();
				for(DailyActivity da2 : updateddailyActivityList){
					
					if(da1.getProject() != null && da2.getProject() != null && 
					YearFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(YearFormat.format(da2.getDailyReport().getActivityDate()).toString()) && 
					MonthFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(MonthFormat.format(da2.getDailyReport().getActivityDate()).toString()) &&
					da1.getDailyReport().getStaffID().getMainlyBelongTeam().getId() == da2.getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
						if(da1.getProject().getId().equals(da2.getProject().getId())){
							
							prj_hour += da2.getActivityHours();
							da.setProject(da2.getProject());
							da.setDailyReport(da2.getDailyReport());
							da.setActivityHours(prj_hour);
							
						}
					}else if(da1.getProject() == null && da2.getProject() == null && 
							YearFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(YearFormat.format(da2.getDailyReport().getActivityDate()).toString()) && 
							MonthFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(MonthFormat.format(da2.getDailyReport().getActivityDate()).toString()) &&
							da1.getDailyReport().getStaffID().getMainlyBelongTeam().getId() == da2.getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
						
						nullprj_hour += da2.getActivityHours();
						da.setProject(da2.getProject());
						da.setDailyReport(da2.getDailyReport());
						da.setActivityHours(nullprj_hour);
						
					}
				
					
				}
				updateddailyActivity.add(da);
				prj_hour = 0.0;
				nullprj_hour = 0.0;
				
				
			}
			 System.out.println("###UPDATEACTIVITYLISTSIZE = "+updateddailyActivity.size());
			// delete old daily record from delete page
			for (DailyActivity da : updateddailyActivity) {
				
				
				for (SummaryActivity s : summaryList) {
					total_activity_hours = s.getActivityHours();
				
					
					if (da.getProject() != null && !s.getProject_id().equals(null_value)) {
						if (s.getProject_id().toString()
								.contains(da.getProject().getId())
								&& (s.getStaff_id().toString().contains(da
										.getDailyReport().getStaffID().getId()
										.toString()))&& (s.getTeam_id() == da
												.getDailyReport().getStaffID().getMainlyBelongTeam()
												.getId())) {
							
							total_activity_hours += (da.getActivityHours());
							
							sa = new SummaryActivity();
							sa.setStaff_id(s.getStaff_id());
							sa.setTeam_id(s.getTeam_id());
							sa.setProject_id(da.getProject().getId());
							sa.setActivityHours(round(total_activity_hours, 3));

							sa.setYear(YearFormat.format(da.getDailyReport()
									.getActivityDate()));
							sa.setMonth(MonthFormat.format(da.getDailyReport()
									.getActivityDate()));
							sa.setActivity_date(da.getDailyReport()
									.getActivityDate());
							
							summaryActivityDAO.updateSummaryActivity(sa);
							
							
							break;
						}

					} else if (da.getProject() == null
							&& s.getProject_id().equals(null_value)
							&& (s.getStaff_id().toString().contains(da
									.getDailyReport().getStaffID().getId().toString()))&& (s.getTeam_id() == da
											.getDailyReport().getStaffID().getMainlyBelongTeam()
											.getId())) {
						
						total_activity_hours += (da.getActivityHours());
						sa = new SummaryActivity();
						sa.setStaff_id(s.getStaff_id());
						sa.setTeam_id(s.getTeam_id());
						sa.setProject_id(null_value);
						sa.setActivityHours(round(total_activity_hours, 3));
						sa.setYear(YearFormat.format(da.getDailyReport()
								.getActivityDate()));
						sa.setMonth(MonthFormat.format(da.getDailyReport()
								.getActivityDate()));
						sa.setActivity_date(da.getDailyReport()
								.getActivityDate());
						
						summaryActivityDAO.updateSummaryActivity(sa);
						
						
						break;
					}

				}
				
				for (DailyReport dr : checkboxlist) {
					User mainlybelonguser = new User();
					mainlybelonguser = findMainlyBelongHistoryByPeroid(dr);
					activity = new SummaryActivity();
					activity.setStaff_id(dr.getStaffID().getId());
					activity.setTeam_id(dr.getStaffID().getMainlyBelongTeam().getId());
					activity.setYear(YearFormat.format(dr.getActivityDate()));
					activity.setMonth(MonthFormat.format(dr.getActivityDate()));
					List<SummaryActivity> summary = summaryActivityDAO
							.findByProjectidStaffidYearMonth(activity);
					for (SummaryActivity s : summary) {
						SummaryActivity sativity = new SummaryActivity();
						sativity.setStaff_id(s.getStaff_id());
						sativity.setTeam_id(s.getTeam_id());
						sativity.setActivityHours(s.getActivityHours());
						sativity.setProject_id(s.getProject_id());
						summaryList.add(sativity);
					}
					SummaryActivity summaryactivity = new SummaryActivity();
					summaryactivity.setStaff_id(dr.getStaffID().getId());
					summaryactivity.setTeam_id(mainlybelonguser.getMainlyBelongTeam().getId());
					summaryactivity.setYear(YearFormat.format(dr.getActivityDate()));
					summaryactivity.setMonth(MonthFormat.format(dr.getActivityDate()));
					summaryActivityDAO.deleteSummaryActivity(summaryactivity);	 
				}
			}
			
			
		}
	
	}

	public void deleteWeeklyReportData(List<WeeklyData> checkboxlist,List<DailyReport> dailyReportList){

		SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearmonth = new SimpleDateFormat("yyyyMM");

		Double total_activity_hours = 0.0;
		SummaryActivity sa = new SummaryActivity();
		SummaryActivity activity = new SummaryActivity();
		summaryList = new ArrayList<SummaryActivity>();
		List<DailyActivity> dailyActivityListResult = new ArrayList<DailyActivity>();
		List<DailyActivity> dailyActivityList = new ArrayList<DailyActivity>();
		String null_value="9999";
		Double prj_hour = 0.0;
		Double nullprj_hour = 0.0;
	
		
		for (WeeklyData dr : checkboxlist) {
			User mainlybelonguser = new User();
			mainlybelonguser = findMainlyBelongHistoryByPeroid(dr.getDailyReport());
			activity = new SummaryActivity();
			dailyActivityListResult = dailyActivityDAO
					.findActivityByReportID(dr.getDailyReport());
			activity.setStaff_id(dr.getDailyReport().getStaffID().getId());
			activity.setTeam_id(mainlybelonguser.getMainlyBelongTeam().getId());
			activity.setYear(YearFormat.format(dr.getDailyReport().getActivityDate()));
			activity.setMonth(MonthFormat.format(dr.getDailyReport().getActivityDate()));
			activity.setActivity_date(dr.getDailyReport().getActivityDate());
			for(DailyActivity da:dailyActivityListResult){
				if(da.getTaskDescription().equals("Half Leave") || da.getTaskDescription().equals("Full Leave")){
					DailyActivity daa = new DailyActivity();
					daa.setProject(da.getProject());
					daa.setActivityHours(0.0);
					daa.setDailyReport(da.getDailyReport());
					dailyActivityList.add(daa);
				}else{
				DailyActivity daa = new DailyActivity();
				daa.setProject(da.getProject());
				daa.setActivityHours(da.getActivityHours());
				daa.setDailyReport(da.getDailyReport());
				dailyActivityList.add(daa);
				}
			}
			
			List<SummaryActivity> summary = summaryActivityDAO
					.findByProjectidStaffidYearMonth(activity);
			for (SummaryActivity s : summary) {
				SummaryActivity sativity = new SummaryActivity();
				sativity.setStaff_id(s.getStaff_id());
				sativity.setTeam_id(s.getTeam_id());
				sativity.setActivityHours(s.getActivityHours());
				sativity.setProject_id(s.getProject_id());
				sativity.setActivity_date(s.getActivity_date());
				
				summaryList.add(sativity);
			}
			

		}
		
		if (checkboxlist.size() > 0 ) {
			
					List<DailyReport> dailyReportIDList = new ArrayList<>();
					for (WeeklyData report : checkboxlist) {
						
							dailyReportIDList.add(report.getDailyReport());
					}
			dailyReportDAO.delete(dailyReportIDList);

			for (DailyReport deleteCandidate : dailyReportIDList) {

				dailyActivityDAO.deleteByReportID(deleteCandidate);
				dailyReportList.remove(deleteCandidate);
				//setDailyReportList(this.dailyReportList);
			}
			
			ArrayList<DailyActivity> updateddailyActivityList = new ArrayList<DailyActivity>();
			ArrayList<DailyActivity> updateddailyActivityData = new ArrayList<DailyActivity>();
			ArrayList<DailyActivity> updateddailyActivity = new ArrayList<DailyActivity>();
			System.out.println("Check box list size = "+checkboxlist.size());
			System.out.println("Daily activity list size = "+dailyActivityList.size());
		
				for (DailyActivity da : dailyActivityList) {
					WeeklyData wd = new WeeklyData();
					DailyReport dr = new DailyReport();	
					List<Object[]> monthlyReportList = new ArrayList<>();
					//User report_user = new User();
					User delete_user = new User();
					delete_user = findMainlyBelongHistoryByPeroid(da.getDailyReport());
					if(da.getProject() !=null){
						monthlyReportList = dailyActivityDAO.findReportByMonthly(da.getProject().getId(), da.getDailyReport().getStaffID().getId(), yearmonth.format(da.getDailyReport().getActivityDate()),delete_user.getMainlyBelongTeam().getId());
						if(monthlyReportList.size() == 0){
							//report_user =findMainlyBelongHistoryByPeroid(da.getDailyReport());
							dr.setStaffID(delete_user);
							dr.setActivityDate(da.getDailyReport().getActivityDate());		
							wd.setDailyReport(dr);
						}else{
						for (int i = 0; i < monthlyReportList.size(); i++) {
							//report_user =findMainlyBelongHistoryByPeroid(da.getDailyReport());
							dr.setStaffID(delete_user);
						dr.setActivityDate((Date)monthlyReportList.get(i)[2]);	
						wd.setDailyReport(dr);
						}
						
						}
						}else{
							
							monthlyReportList = dailyActivityDAO.findReportByMonthly(null, da.getDailyReport().getStaffID().getId(), yearmonth.format(da.getDailyReport().getActivityDate()),delete_user.getMainlyBelongTeam().getId());
							if(monthlyReportList.size() == 0){
								//report_user =findMainlyBelongHistoryByPeroid(da.getDailyReport());
								dr.setStaffID(delete_user);
								dr.setActivityDate(da.getDailyReport().getActivityDate());	
								wd.setDailyReport(dr);
							}else{
							for (int i = 0; i < monthlyReportList.size(); i++) {
								//report_user =findMainlyBelongHistoryByPeroid(da.getDailyReport());
								dr.setStaffID(delete_user);
							dr.setActivityDate((Date)monthlyReportList.get(i)[2]);		
							wd.setDailyReport(dr);
							}
						
							
						}
					}
					DailyActivity dd = new DailyActivity();
					
					dd.setDailyReport(wd.getDailyReport());
					dd.setProject(da.getProject());
					dd.setActivityHours(-da.getActivityHours());
					updateddailyActivityList.add(dd);
				}
				
				List<DailyActivity> arr = updateddailyActivityList;
				int cnt= 0;
			   
		         for(int i=0;i<arr.size();i++){
		       for(int j=i+1;j<arr.size();j++){
		    	   if(arr.get(i).getProject() != null && arr.get(j).getProject() !=null){
		          if(arr.get(i).getProject().getId().contains(arr.get(j).getProject().getId()) && 
		        	YearFormat.format(arr.get(i).getDailyReport().getActivityDate()).contains(YearFormat.format(arr.get(j).getDailyReport().getActivityDate())) && 
		        	MonthFormat.format(arr.get(i).getDailyReport().getActivityDate()).contains(MonthFormat.format(arr.get(j).getDailyReport().getActivityDate()))&&
		        	arr.get(i).getDailyReport().getStaffID().getMainlyBelongTeam().getId() == arr.get(j).getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
		            cnt+=1;
		           
		          }  
		       }else if((arr.get(i).getProject() == null && arr.get(j).getProject() == null)&& 
			        	YearFormat.format(arr.get(i).getDailyReport().getActivityDate()).contains(YearFormat.format(arr.get(j).getDailyReport().getActivityDate())) && 
			        	MonthFormat.format(arr.get(i).getDailyReport().getActivityDate()).contains(MonthFormat.format(arr.get(j).getDailyReport().getActivityDate()))&&
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
			
			
			for(DailyActivity da1 : updateddailyActivityData){
				DailyActivity da = new DailyActivity();
				for(DailyActivity da2 : updateddailyActivityList){
					
					if(da1.getProject() != null && da2.getProject() != null && 
					YearFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(YearFormat.format(da2.getDailyReport().getActivityDate()).toString()) && 
					MonthFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(MonthFormat.format(da2.getDailyReport().getActivityDate()).toString())&&
					da1.getDailyReport().getStaffID().getMainlyBelongTeam().getId() == da2.getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
						if(da1.getProject().getId().equals(da2.getProject().getId())){
							
							prj_hour += da2.getActivityHours();
							da.setProject(da2.getProject());
							da.setDailyReport(da2.getDailyReport());
							da.setActivityHours(prj_hour);
							
						}
					}else if(da1.getProject() == null && da2.getProject() == null && 
							YearFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(YearFormat.format(da2.getDailyReport().getActivityDate()).toString()) && 
							MonthFormat.format(da1.getDailyReport().getActivityDate()).toString().equals(MonthFormat.format(da2.getDailyReport().getActivityDate()).toString())&&
							da1.getDailyReport().getStaffID().getMainlyBelongTeam().getId() == da2.getDailyReport().getStaffID().getMainlyBelongTeam().getId()){
						
						nullprj_hour += da2.getActivityHours();
						da.setProject(da2.getProject());
						da.setDailyReport(da2.getDailyReport());
						da.setActivityHours(nullprj_hour);
						
					}
				
					
				}
				updateddailyActivity.add(da);
				prj_hour = 0.0;
				nullprj_hour = 0.0;
				
				
			}
			
			System.out.println("###UPDATEACTIVITYLISTSIZE = "+updateddailyActivity.size());
			
			// delete old daily record from delete page
						for (DailyActivity da : updateddailyActivity) {
							
							for (SummaryActivity s : summaryList) {
								total_activity_hours = s.getActivityHours();
								if (da.getProject() != null && !s.getProject_id().equals(null_value)) {
									if (s.getProject_id().toString()
											.contains(da.getProject().getId())
											&& (s.getStaff_id().toString().contains(da
													.getDailyReport().getStaffID().getId()
													.toString()))&& (s.getTeam_id() == da
															.getDailyReport().getStaffID().getMainlyBelongTeam()
															.getId())) {
										
										total_activity_hours += (da.getActivityHours());
										
										sa = new SummaryActivity();
										sa.setStaff_id(s.getStaff_id());
										sa.setTeam_id(s.getTeam_id());
										sa.setProject_id(da.getProject().getId());
										sa.setActivityHours(round(total_activity_hours, 3));

										sa.setYear(YearFormat.format(da.getDailyReport()
												.getActivityDate()));
										sa.setMonth(MonthFormat.format(da.getDailyReport()
												.getActivityDate()));
										sa.setActivity_date(da.getDailyReport()
												.getActivityDate());
										
										summaryActivityDAO.updateSummaryActivity(sa);

								
									
										
										

										break;
									}

								} else if (da.getProject() == null
										&& s.getProject_id().equals(null_value)
										&& (s.getStaff_id().toString().contains(da
												.getDailyReport().getStaffID().getId().toString()))&& (s.getTeam_id() == da
														.getDailyReport().getStaffID().getMainlyBelongTeam()
														.getId())) {
									total_activity_hours += (da.getActivityHours());
									sa = new SummaryActivity();
									sa.setStaff_id(s.getStaff_id());
									sa.setTeam_id(s.getTeam_id());
									sa.setProject_id(null_value);
									sa.setActivityHours(round(total_activity_hours, 3));
									sa.setYear(YearFormat.format(da.getDailyReport()
											.getActivityDate()));
									sa.setMonth(MonthFormat.format(da.getDailyReport()
											.getActivityDate()));
									sa.setActivity_date(da.getDailyReport()
											.getActivityDate());
									
									summaryActivityDAO.updateSummaryActivity(sa);
								
									
									

									break;
								}

							}
							
							for (WeeklyData dr : checkboxlist) {
								User mainlybelonguser = new User();
								mainlybelonguser = findMainlyBelongHistoryByPeroid(dr.getDailyReport());
								activity = new SummaryActivity();
								activity.setStaff_id(dr.getDailyReport().getStaffID().getId());
								activity.setTeam_id(dr.getDailyReport().getStaffID().getMainlyBelongTeam().getId());
								activity.setYear(YearFormat.format(dr.getDailyReport().getActivityDate()));
								activity.setMonth(MonthFormat.format(dr.getDailyReport().getActivityDate()));
								List<SummaryActivity> summary = summaryActivityDAO
										.findByProjectidStaffidYearMonth(activity);
								for (SummaryActivity s : summary) {
									SummaryActivity sativity = new SummaryActivity();
									sativity.setStaff_id(s.getStaff_id());
									sativity.setTeam_id(s.getTeam_id());
									sativity.setActivityHours(s.getActivityHours());
									sativity.setProject_id(s.getProject_id());
									summaryList.add(sativity);
								}
								SummaryActivity summaryactivity = new SummaryActivity();
								summaryactivity.setStaff_id(dr.getDailyReport().getStaffID().getId());
								summaryactivity.setTeam_id(mainlybelonguser.getMainlyBelongTeam().getId());
								summaryactivity.setYear(YearFormat.format(dr.getDailyReport().getActivityDate()));
								summaryactivity.setMonth(MonthFormat.format(dr.getDailyReport().getActivityDate()));
								summaryActivityDAO.deleteSummaryActivity(summaryactivity);	
								
							}
		}
		
		}

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public DailyReport addNewDailyReport(DailyReport dailyReport) throws SystemException {
		try {
			logger.debug("addDailyReport() method has been started.");
			dailyReport = dailyReportDAO.insert(dailyReport);
			logger.debug("addDailyReport() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewDailyReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a DailyReport", e);
		}
		return dailyReport;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public DailyReport updateDailyReport(DailyReport dailyReport) throws SystemException {
		try {
			logger.debug("updateDailyReport() method has been started.");
			dailyReport = dailyReportDAO.update(dailyReport);
			logger.debug("updateDailyReport() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateDailyReport() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to update DailyReport", e);
		}
		return dailyReport;
	}

	/*@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDailyReport(DailyReport dailyReport) throws SystemException {
		try {
			logger.debug("deleteDailyReport() method has been started.");
			dailyReportDAO.delete(dailyReport);
			logger.debug("deleteDailyReport() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteDailyReport() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to delete DailyReport", e);
		}
	}*/

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findAllReport() throws SystemException {
		logger.debug("findAllReport() method has been started.");
		List<DailyReport> result = dailyReportDAO.findAll();
		logger.debug("findAllReport() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteDailyReportLists(List<DailyReport> dailyReportIDList) throws SystemException {
		try {
			logger.debug("deleteDailyReport() method has been started.");
			dailyReportDAO.delete(dailyReportIDList);
			logger.debug("deleteDailyReport() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteDailyReport() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to delete DailyReport", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void approveDailyReportLists(List<DailyReport> dailyReportIDList) throws SystemException {
		try {
			logger.debug("approveDailyReportLists() method has been started.");
			dailyReportDAO.approve(dailyReportIDList);
			logger.debug("approveDailyReportLists() method has been finished.");
		} catch (DAOException e) {
			logger.error("approveDailyReportLists() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to approve DailyReport", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void disapproveDailyReportLists(List<DailyReport> dailyReportIDList) throws SystemException {
		try {
			logger.debug("disapproveDailyReportLists() method has been started.");
			dailyReportDAO.disapprove(dailyReportIDList);
			logger.debug("disapproveDailyReportLists() method has been finished.");
		} catch (DAOException e) {
			logger.error("disapproveDailyReportLists() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to disapprove DailyReport", e);
		}
	}

	/**
	 * Revised By   : Sai Kyaw Ye Myint
	 * Revised Date : 2017/09/08
	 * Explanation  : Added argument for login user info.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> searchReport(DailyReport dailyReport, SearchDate searchDate, User loginUser) throws SystemException {
		logger.debug("searchReport() method has been started.");
		//List<DailyReport> result = dailyReportDAO.search(dailyReport, searchDate);
		List<DailyReport> result = dailyReportDAO.search(dailyReport, searchDate,loginUser);
		logger.debug("searchReport() method has been finished.");
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> searchAllByAdmin(DailyReport dailyReport) {
		logger.debug("searchAll() method has been started.");
		List<DailyReport> result = dailyReportDAO.searchAllByAdmin(dailyReport);
		logger.debug("searchAll() method has been finished.");
		return result;
	}


	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findByDate(Date date, User staff) throws SystemException {
		List<DailyReport> result = null;
		try {
			logger.debug("findByDate() method has been started.");
			result = dailyReportDAO.findByDate(date, staff);
			logger.debug("findByDate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByDate() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DailyReport by date", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findByDateforDuplicate(Date date, User staff) throws SystemException {
		List<DailyReport> result = null;
		try {
			logger.debug("findByDateforDuplicate() method has been started.");
			result = dailyReportDAO.findByDateforDuplicate(date, staff);
			logger.debug("findByDateforDuplicate() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByDateforDuplicate() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DailyReport by date for Duplicate", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findByMonthYear(int month, int year) throws SystemException {
		List<DailyReport> result = null;
		try {
			logger.debug("findByMonthYear() method has been started.");
			result = dailyReportDAO.findByMonthYear(month, year);
			logger.debug("findByMonthYear() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByMonthYear() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DailyReport by month,year", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByMonthYearProj(int month,int year,String project) throws SystemException {
		List<Object[]> result = null;
		try {
			logger.debug("findByMonthYearProj() method has been started.");
			result = dailyReportDAO.findByMonthYearProj(month,year, project);
			logger.debug("findByMonthYearProj() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByMonthYearProj() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DailyReport by month,year,project", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByYearProj(int year,String project) throws SystemException {
		List<Object[]> result = null;
		try {
			logger.debug("findByYearProj() method has been started.");
			result = dailyReportDAO.findByYearProj(year, project);
			logger.debug("findByYearProj() method has been finished.");
		} catch (DAOException e) {
			logger.error("findByYearProj() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DailyReport by year,project", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findReportIDListforExcel(DailyReport dailyReport, SearchDate searchDate) throws SystemException {
		List<DailyReport> result = null;
		try {
			logger.debug("findReportIDListforExcel() method has been started.");
			result = dailyReportDAO.findReportIDListforExcel(dailyReport, searchDate);
			logger.debug("findReportIDListforExcel() method has been finished.");
		} catch (DAOException e) {
			logger.error("findReportIDListforExcel() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DailyReport by username and date", e);
		}
		return result;
	}
	
	/**
	 * Created By   : Kyi Saw Win
	 * Created Date : 2017/09/12
	 * Explanation  : Get available years from daily_report table.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findActiveYears() throws SystemException{
		List<String> years = null;
		try{
			logger.debug("findActiveYear() method has been started.");
			years = dailyReportDAO.findActiveYears();
		}catch (DAOException e) {
			logger.error("findActiveYear() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search active years.", e);
		}
		return years;
	}

	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/12
	 * Explanation  : Get daily report data form daily_report table.
	 */
	@Override
	public List<DailyReport> findAllReportByCriteria(DailyReport dailyReport, String year, String month, String day,SearchDate searchDate) throws SystemException {
		List<DailyReport> result = null;
		try {
			logger.debug("findAllReportByCriteria() method has been started.");
			result = dailyReportDAO.findAllReportByCriteria(dailyReport, year, month, day, searchDate);
			logger.debug("findAllReportByCriteria() method has been finished.");
		} catch (DAOException e) {
			logger.error("findAllReportByCriteria() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search DailyReport by defined criteria", e);
		}
		return result;
	}
	
	/**
	 * Created By	: Aye Chan Thar Soe
	 * Created Date	: 2018/09/04
	 * Explanation	: Get daily report register user list from daily_report table.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDailyReportRegistrationStatusByMonthYear(
			int year, int month,User user) {
		List<Object[]> reports = null;
		try {
			logger.debug("findDailyReportRegistrationStatusByMonthYear() method has been started.");
			reports = dailyReportDAO
					.findDailyReportRegistrationStatusByMonthYear(year, month,user);
			logger.debug("findDailyReportRegistrationStatusByMonthYear() method has been finished.");
		} catch (DAOException e) {
			logger.error("findDailyReportRegistrationStatusByMonthYear() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to search reports by defined Month and Year.", e);
		}
		return reports;
	}
	
	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Get daily report data by year/month from daily_report table.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> searchReportByCriteria(DailyReport dailyReport,
			String year, String month, User loginUser) throws SystemException {
		logger.debug("searchReportByCriteria() method has been started.");
		//List<DailyReport> result = dailyReportDAO.search(dailyReport, searchDate);
		List<DailyReport> result = dailyReportDAO.searchReportByCriteria(dailyReport, year, month ,loginUser);
		logger.debug("searchReportByCriteria() method has been finished.");
		return result;
	}
}