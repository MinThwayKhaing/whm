/***************************************************************************************
 * 
 *@author Htet Wai Yum
 * @Date 2018-08-24
 * @Version 1.0
 * @Purpose <<Daily Report Individual .>>
 *
 ***************************************************************************************/
package com.dat.whm.web.dailyreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;




import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.exception.SystemException;
import com.dat.whm.holidays.entity.Holidays;
import com.dat.whm.holidays.service.interfaces.IHolidaysService;
import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.SearchDate;
import com.dat.whm.web.common.WhmUtilities;
import com.dat.whm.web.weeklyreport.DailyActivityData;
import com.dat.whm.web.weeklyreport.WeeklyData;
import com.dat.whm.workinghour.service.interfaces.IWorkingHourService;

@ManagedBean(name = "DailyReportRegistrationStatusIndividualBean")
@ViewScoped
public class DailyReportRegistrationStatusIndividualBean extends BaseBean {

	
	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;
	@ManagedProperty(value = "#{HolidaysService}")
	private IHolidaysService holidayService;
	@ManagedProperty(value = "#{WorkingHourService}")
	private IWorkingHourService workinghourService;

	public final String SCREEN_ID = "DAR006.xhtml";

	private DailyReport dailyReport;
	private DailyActivity dailyActivity;
	private List<DailyReport> dailyReportList = new ArrayList<DailyReport>();
	private List<DailyActivity> dailyActivityList = new ArrayList<DailyActivity>();
	private List<WeeklyData> weeklyReportData;
	private List<WeeklyData> weeklyReportDataResult;
	private List<WeeklyData> weeklyReportDataResultList;
	private List<WeeklyData> UserInfoList;

	List<WeeklyData> checkboxlist = new ArrayList<WeeklyData>();
	SearchDate searchDate;
	private String year;
	private String month;
	private List<String> years;
	private List<String> months;
	private boolean btnControl;
	private boolean searchResultCtl = false;
	List<Holidays> holidays;
	User staffData;
	User loginUser;
   
	@PostConstruct
	public void init() {
		
		prepareSearchData();
		getActiveYear();
		prepareMonths();
		getCurrentYearMonth();

	}
	
	
	/* Get current year from year list from daily_report table. */
	public void getCurrentYearMonth() {
		 Calendar now = Calendar.getInstance();
		 year = new String();
		 month = new String();
		 String current_month = new String();
		 year= Integer.toString(now.get(Calendar.YEAR));
		 current_month=WhmUtilities.findMonthByDigit(Integer.toString(now.get(Calendar.MONTH) + 1));
		 for (int i = 0; i <  months.size(); i++) {
			
		
			 if(months.get(i).contains(current_month)){
				 month=months.get(i);
			 }
		 }
		
	}
	

	/* Get year list from daily_report table. */
	public void getActiveYear() {
		years = new ArrayList<String>();
		years = dailyReportService.findActiveYears();
	}

	/* Prepare month list. */
	public void prepareMonths() {
		months = new ArrayList<String>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
	}

	
	public void prepareSearchData() {
		dailyReport = new DailyReport();
		staffData = new User();
		loginUser = new User();
		loginUser = (User) getParam(Constants.LOGIN_USER);
		weeklyReportData = new ArrayList<>();
		
		if (loginUser.getRole() == Role.ADMIN || loginUser.getRole() == Role.MANAGEMENT ){
				
			staffData.setUsername(loginUser.getUsername());
			staffData.setFullName(loginUser.getFullName());
			dailyReport.setStaffID(staffData);
		}
		
		
	else {
			setStaffData(loginUser);
			dailyReport.setStaffID(staffData);
		}

	}
	
	public ArrayList<DailyActivity> removeDuplicate(List<DailyActivity> dailyActivityList){
		
		ArrayList<DailyActivity> updateddailyActivityData = new ArrayList<DailyActivity>();
		List<DailyActivity> arr = dailyActivityList;
		int cnt= 0;
	   
         for(int i=0;i<arr.size();i++){
       for(int j=i+1;j<arr.size();j++){
    	   if(arr.get(i).getProject() != null && arr.get(j).getProject() !=null){
    		
          if(arr.get(i).getProject().getId().equals(arr.get(j).getProject().getId())){
        	 
            cnt+=1;
           
          }  
       }else if(arr.get(i).getProject() == null && arr.get(j).getProject() == null){
    	  
    	   
 	          cnt+=1;
 	         
 	             
       }
       }
       if(cnt<1){
    	   DailyActivity da = new DailyActivity();
    	  
    	   if(arr.get(i).getProject() != null){
    		  
    	   da.setProject(arr.get(i).getProject());
    	  
    	  
    	   updateddailyActivityData.add(da);
         }else{
        	 
        	 da.setProject(null);
        
         updateddailyActivityData.add(da); 
         }
    	   
       }
         cnt=0;
       
       }
		return updateddailyActivityData;
	}
	
	

	// * This method is a listener method for staff id change.

	public void staffIdSelect() {

		if (dailyReport.getStaffID().getUsername().equals("")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"Please enter User ID."));
			dailyReport.setStaffID(new User());
			btnController();
		} else {
			try {
				String staffId = new String();
						staffId = dailyReport.getStaffID().getUsername();
				
				User u = new User();
					u = userService.findUser(staffId);
				

				if (u != null) {

					dailyReport.setStaffID(u);
					btnController();
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
									"User doesn't exists!!"));
					dailyReport.setStaffID(new User());
					btnController();
				}
			} catch (SystemException e) {
				handelSysException(e);
			}
		}
	}

	/*
	 * This method is a listener method for from date change. Calculate to date
	 * by adding 6 days from fromDate.
	 */

	public void btnController() {
		if (dailyReport.getStaffID().getUsername() != null) {
			setBtnControl(false);
		} else {
			setBtnControl(true);
		}
	}

	private boolean isValidSearch() {
		if (dailyReport.getStaffID().getUsername() == null
				|| dailyReport.getStaffID().getUsername() == "") {
			String errorMessage = "Please input staff id.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							errorMessage));
			return false;
		}

		if (year.trim().equals("") || year.trim().equals(null)
				|| year.equals("-")) {
			String errorMessage = "Please select year.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							errorMessage));
			return false;
		}

		if (month.trim().equals("") || month.trim().equals(null)
				|| month.equals("-")) {
			String errorMessage = "Please select month.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							errorMessage));
			return false;
		}

		return true;
	}

	/*
	 * This method is use to search report data
	 */
	public void show() {

		if (!isValidSearch()) {
			this.searchResultCtl = false;
			return;
		}

		SimpleDateFormat approvalDateFormat = new SimpleDateFormat("yyyy-MM-dd (EEE)");
		SimpleDateFormat calDateFormat = new SimpleDateFormat("yyyy-MM-dd (EEE)");
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(WhmUtilities.findMonthDigit(month)) - 1;
		ArrayList<Object> cal_arr = new ArrayList<>();
		boolean flag = false;
		boolean holiday_flag = false;
		weeklyReportDataResult = new ArrayList<>();
		weeklyReportDataResultList = new ArrayList<>();
		UserInfoList =  new ArrayList<>();
		holidays=new ArrayList<Holidays>();
		// ArrayList<List<DailyReport>> db_arr = new ArrayList<>();

		calendar.set(y, m, 1);

		while (calendar.get(Calendar.MONTH) == m) {
			String formatted = calDateFormat.format(calendar.getTime());

			calendar.add(Calendar.DATE, 1);
			cal_arr.add(formatted);
		}

		loginUser = (User) getParam(Constants.LOGIN_USER);

		if (loginUser.getAuthorities().size() != 0) {
			dailyReport.getStaffID().setAuthorities(loginUser.getAuthorities());
			
			if (loginUser.getRole() == Role.MANAGEMENT) {
				for (Team team : loginUser.getTeams()) {
					if (!dailyReport.getStaffID().getAuthorities()
							.contains(team)) {
						dailyReport.getStaffID().getAuthorities().add(team);
					}
				}
			}
		}else{
			if (loginUser.getRole() == Role.MANAGEMENT) {
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"You haven't your related team authority.Please contact to administrator!"));
				return;
			}
		}

		
		if ((loginUser.getRole() == Role.ADMIN)) {
			dailyReport.getStaffID().setRole(loginUser.getRole());
		}

		// dailyReportList = dailyReportService.searchReport(dailyReport,
		// searchDate);
		dailyReportList = new ArrayList<DailyReport>();
		dailyReportList = dailyReportService.searchReportByCriteria(
				dailyReport, year, WhmUtilities.findMonthDigit(month),
				loginUser);

		WeeklyData weeklyData = new WeeklyData();
		List<WeeklyData> allDaysData = new ArrayList<WeeklyData>();
		List<WeeklyData> allDaysDataResult = new ArrayList<WeeklyData>();
		DailyActivityData oneDayData;
		List<DailyActivity> activityListByReportID = new ArrayList<DailyActivity>();
		double workingHour = 0.0;
		int count = 0;
		String username="";
		String fullname="";
		// ===============================

		if (dailyReportList.size() > 0) {
			for (DailyReport reportID : dailyReportList) {
				Double prj_hour = 0.0;
				Double nullprj_hour = 0.0;
				weeklyData = new WeeklyData(reportID);
				weeklyData.setActivityDate(approvalDateFormat.format(reportID
						.getActivityDate()));
				// get daily activity list for one day
				if(reportID.getDelDiv().equals(DeleteDiv.ACTIVE)){
				activityListByReportID = dailyActivityService
						.findActivityByReportID(reportID);
				System.out.println("Activity Date = "+reportID.getActivityDate());
				System.out.println("Report Delete Flag = "+reportID.getDelDiv());
				workingHour = workinghourService.findByDate(DateFormat.format(reportID
						.getActivityDate()));
				List<DailyActivity> updateddailyActivityData = new ArrayList<>();
				updateddailyActivityData = removeDuplicate(activityListByReportID);
				
			for (DailyActivity removeduplicate : updateddailyActivityData) {
				
				oneDayData = new DailyActivityData();
				for (DailyActivity activityOneDayData : activityListByReportID) {
					
					if(removeduplicate.getProject() != null && activityOneDayData.getProject() != null){
						if(removeduplicate.getProject().getId().equals(activityOneDayData.getProject().getId())){
							prj_hour += activityOneDayData.getActivityHours();
							oneDayData.setProjectID(activityOneDayData.getProject()
									.getId());
							oneDayData.setProjectName(activityOneDayData
									.getProject().getProjectName());
							oneDayData.setDescription(activityOneDayData.getTaskDescription());
							oneDayData.setHour(prj_hour);
							oneDayData.setUserName(activityOneDayData.getDailyReport().getStaffID().getUsername());
							oneDayData.setFullName(activityOneDayData.getDailyReport().getStaffID().getFullName());
							
						}
					}else if(removeduplicate.getProject() == null && activityOneDayData.getProject() == null){
						nullprj_hour += activityOneDayData.getActivityHours();
						oneDayData.setProjectID(null);
						oneDayData.setProjectName(null);
						oneDayData.setDescription(activityOneDayData.getTaskDescription());
						oneDayData.setHour(nullprj_hour);
						oneDayData.setUserName(activityOneDayData.getDailyReport().getStaffID().getUsername());
						oneDayData.setFullName(activityOneDayData.getDailyReport().getStaffID().getFullName());
					}
					
					
					weeklyData.setUsername(oneDayData.getUserName());
					weeklyData.setFullname(oneDayData.getFullName());
					
					
					weeklyData.setLeavestatus(oneDayData.getDescription());
					// total activity lists
					
					weeklyData.setWorkinghour(workingHour);
					weeklyData.setTotalHour(oneDayData.getHour());
					
				}
				weeklyData.getActivities().add(oneDayData);
				allDaysData.add(weeklyData);
				prj_hour = 0.0;
				nullprj_hour = 0.0;
				}
				

			}

	}
				//Add calendar days to activity date start
				for (int j = 0; j < cal_arr.size(); j++) {

					for (WeeklyData wd : allDaysData) {

						if (wd.getActivityDate().contains(
								cal_arr.get(j).toString())) {
							
							
							allDaysDataResult.add(wd);

							flag = true;
							break;
						} else {

							flag = false;

						}
					}
					if (!flag) {
						WeeklyData wd = new WeeklyData();
					
						username = weeklyData.getUsername();
						fullname = weeklyData.getFullname();
						wd.setActivities(null);
						wd.setDescription("");
						wd.setTotalHour(0.0);
						wd.setFullname(weeklyData.getFullname());
						wd.setUsername(weeklyData.getUsername());
						wd.setActivityDate(cal_arr.get(j).toString());
						wd.setWorkinghour(workingHour);
						
						allDaysDataResult.add(wd);

					}

					weeklyReportData = allDaysDataResult;

				}
				//Add calendar days to activity date end

				//set holiday to activity date start
				holidays = holidayService.findAllByDate(year,WhmUtilities.findMonthDigit(month));
									
				for(WeeklyData wd:weeklyReportData) {
					for(Holidays hday: holidays){
						
					if(wd.getActivityDate().contains(approvalDateFormat.format(hday.getDate()))){
						wd.setActivityDate(approvalDateFormat.format(hday.getDate())+"-"+hday.getDescription());
						weeklyReportDataResult.add(wd);
						holiday_flag=true;
						break;
					}
					else{
						holiday_flag = false;
					}
				}
				if(!holiday_flag)
				{   
					weeklyReportDataResult.add(wd);
				}
				
			}
				
				
				//set holiday to activity date end
				weeklyReportDataResultList = weeklyReportDataResult;
				for(WeeklyData wd:weeklyReportDataResult){
				
					if(wd.getActivities() != null){
					for(DailyActivityData da: wd.getActivities()){
					
						count++;
					}
					}else{
						count++;
					}
				}
				WeeklyData wdata = new WeeklyData();
				wdata.setUsername(username);
				wdata.setFullname(fullname);
				wdata.setDay_count(weeklyReportDataResult.size());
				wdata.setActivities_count(count);
				UserInfoList.add(wdata);
				
	}else{
		String InfoMessage = "There is no search result";
		weeklyReportData.clear();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						InfoMessage));
	}
		
	
	}

	

	/***************************************************************************************
	 * The following are getter and setter method of each class variable.
	 ***************************************************************************************/

	public String getSCREEN_ID() {
		return SCREEN_ID;
	}

	public List<WeeklyData> getWeeklyReportData() {
		return weeklyReportData;
	}

	public void setWeeklyReportData(List<WeeklyData> weeklyReportData) {
		this.weeklyReportData = weeklyReportData;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public DailyActivity getDailyActivity() {
		return dailyActivity;
	}

	public void setDailyActivity(DailyActivity dailyActivity) {
		this.dailyActivity = dailyActivity;
	}

	public IDailyReportService getDailyReportService() {
		return dailyReportService;
	}

	public void setDailyReportService(IDailyReportService dailyReportService) {
		this.dailyReportService = dailyReportService;
	}

	public DailyReport getDailyReport() {
		return dailyReport;
	}

	public IDailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public void setDailyActivityService(
			IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public void setDailyReport(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
	}

	public List<DailyReport> getDailyReportList() {
		return dailyReportList;
	}

	public void setDailyReportList(List<DailyReport> dailyReportList) {

		this.dailyReportList = dailyReportList;
	}

	public int getDailyReportListSize() {
		return this.dailyReportList.size();
	}

	public List<DailyActivity> getDailyActivityList() {
		return dailyActivityList;
	}

	public void setDailyActivityList(List<DailyActivity> dailyActivityList) {
		this.dailyActivityList = dailyActivityList;
	}


	public User getStaffData() {
		return staffData;
	}

	public void setStaffData(User staffData) {
		this.staffData = staffData;
	}



	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public List<String> getMonths() {
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
	}

	public boolean isBtnControl() {
		return btnControl;
	}

	public void setBtnControl(boolean btnControl) {
		this.btnControl = btnControl;
	}

	public boolean isSearchResultCtl() {
		return searchResultCtl;
	}

	public void setSearchResultCtl(boolean searchResultCtl) {
		this.searchResultCtl = searchResultCtl;
	}

	public List<WeeklyData> getWeeklyReportDataResult() {
		return weeklyReportDataResult;
	}

	public void setWeeklyReportDataResult(
			List<WeeklyData> weeklyReportDataResult) {
		this.weeklyReportDataResult = weeklyReportDataResult;
	}

	public List<WeeklyData> getWeeklyReportDataResultList() {
		return weeklyReportDataResultList;
	}

	public void setWeeklyReportDataResultList(
			List<WeeklyData> weeklyReportDataResultList) {
		this.weeklyReportDataResultList = weeklyReportDataResultList;
	}

	public List<Holidays> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<Holidays> holidays) {
		this.holidays = holidays;
	}

	public IHolidaysService getHolidayService() {
		return holidayService;
	}

	public void setHolidayService(IHolidaysService holidayService) {
		this.holidayService = holidayService;
	}

	public IWorkingHourService getWorkinghourService() {
		return workinghourService;
	}

	public void setWorkinghourService(IWorkingHourService workinghourService) {
		this.workinghourService = workinghourService;
	}


	public List<WeeklyData> getUserInfoList() {
		return UserInfoList;
	}


	public void setUserInfoList(List<WeeklyData> userInfoList) {
		UserInfoList = userInfoList;
	}
	
	

}
