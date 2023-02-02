/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-07-07
 * @Version 1.0
 * @Purpose <<Weekly Activity Management ( Search, Approve, Remove Approval, Delete ) .>>
 *
 ***************************************************************************************/
package com.dat.whm.web.weeklyreport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.dat.whm.approval.entity.Approval;
import com.dat.whm.approval.service.interfaces.IApprovalService;
import com.dat.whm.common.entity.CheckDiv;
import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.common.entity.LeaveStatus;
import com.dat.whm.common.entity.TaskStatus;
import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.exception.SystemException;
import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.summaryactivity.service.interfaces.ISummaryActivityService;
import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.SearchDate;

@ManagedBean(name = "WeeklyReportManagementBean")
@ViewScoped
public class WeeklyReportManagementBean extends BaseBean {

	@ManagedProperty(value = "#{ApprovalService}")
	private IApprovalService approvalService;
	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public final String SCREEN_ID = "DAR004.xhtml";

	private DailyReport dailyReport;
	private DailyActivity dailyActivity;
	private List<DailyReport> dailyReportList = new ArrayList<DailyReport>();
	private List<DailyActivity> dailyActivityList = new ArrayList<DailyActivity>();
	private List<WeeklyData> weeklyReportData;
	/**
	 * Revised By : Htet Wai Yum 
	 * Revised Date : 2018/09/10
	 * Explanation: Added daily report information list
	 */

	List<DailyReport> uncheckboxlist = new ArrayList<DailyReport>();
	List<DailyActivity> uncheckboxData = new ArrayList<DailyActivity>();
	private List<DailyActivity> checkdailyActivityList = new ArrayList<DailyActivity>();
	List<DailyActivity> uncheckdailyActivityList = new ArrayList<DailyActivity>();
	ArrayList<DailyActivity> updateddailyActivityList = new ArrayList<DailyActivity>();
	ArrayList<DailyActivity> checkupdateddailyActivityList = new ArrayList<DailyActivity>();
	ArrayList<DailyActivity> uncheckupdateddailyActivityList = new ArrayList<DailyActivity>();
	List<DailyActivity> arrList = new ArrayList<DailyActivity>();

	@ManagedProperty(value = "#{SummaryActivityService}")
	private ISummaryActivityService summaryactivityService;
	List<SummaryActivity> summaryList = new ArrayList<SummaryActivity>();

	List<WeeklyData> checkboxlist = new ArrayList<WeeklyData>();
	SearchDate searchDate;
	User staffData;
	User loginUser;
	boolean btnControl;
	boolean btnDel;
	boolean btndisApprove;
	boolean btnApprove;

	@PostConstruct
	public void init() {
		prepareSearchData();
	}

	@SuppressWarnings("unchecked")
	public void controlForm() {
		try {
			init();

			if (!FacesContext.getCurrentInstance().getExternalContext().getFlash().isEmpty()) {
				boolean backInquiryFlag = (boolean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag");

				if (backInquiryFlag) {
					setDailyReportList((List<DailyReport>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("dailyReportList"));
					setDailyReport((DailyReport) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("inquiryReport"));
					setSearchDate((SearchDate) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("searchDate"));
					FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", false);
					FacesContext.getCurrentInstance().getExternalContext().getFlash().put("dailyReportList", new ArrayList<DailyReport>());
					FacesContext.getCurrentInstance().getExternalContext().getFlash().put("inquiryReport", new DailyReport());
					FacesContext.getCurrentInstance().getExternalContext().getFlash().put("searchDate", new SearchDate());
					if (dailyReportList.size() > 0) {
						setBtnDel(false);
						setBtnApprove(false);
						setBtndisApprove(false);
					}
					btnController();
					searchReport();
				}
			} else {
				dailyReportList = new ArrayList<DailyReport>();
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void prepareSearchData() {
		dailyReport = new DailyReport();
		searchDate = new SearchDate();
		staffData = new User();
		loginUser = new User();
		loginUser = (User) getParam(Constants.LOGIN_USER);
		weeklyReportData = new ArrayList<>();

		if ((loginUser.getRole() != Role.ADMIN) && (loginUser.getAuthorities().size() == 0)) {
			setStaffData(loginUser);
			dailyReport.setStaffID(staffData);
		} else {
			dailyReport.setStaffID(staffData);
		}
		setBtnControl(true);
		setBtnDel(true);
		setBtnApprove(true);
		setBtndisApprove(true);
	}

	/*
	 * This method is a listener method for staff id change.
	 */
	public void changeUser() {

		if (dailyReport.getStaffID().getUsername().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please enter User ID."));
			dailyReport.setStaffID(new User());
			btnController();
		} else {
			try {
				String staffId = dailyReport.getStaffID().getUsername();
				User u = userService.findUser(staffId);
				if (u != null) {
					dailyReport.setStaffID(u);
					btnController();
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "User doesn't exists!!"));
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
	public void changeDate() {
		if (searchDate.getFromDate() == null || searchDate.getFromDate().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please select from date."));
			searchDate = new SearchDate();
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(searchDate.getFromDate());
			c.add(Calendar.DATE, 6);
			searchDate.setToDate(c.getTime());
		}
		btnController();
	}

	public void btnController() {
		if (dailyReport.getStaffID().getUsername() != null && searchDate.getFromDate() != null) {
			setBtnControl(false);
		} else {
			setBtnControl(true);
		}
	}

	/*
	 * This method is use to search report data
	 */
	public void searchReport() {
		if (checkboxlist.size() != 0) {
			checkboxlist.clear();
		}

		SimpleDateFormat approvalDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		loginUser = (User) getParam(Constants.LOGIN_USER);

		if (loginUser.getAuthorities().size() != 0) {
			dailyReport.getStaffID().setAuthorities(loginUser.getAuthorities());
			/**
			 * Revised By   : Sai Kyaw Ye Myint
			 * Revised Date : 2017/09/08
			 * Explanation  : Modify for management user to search all of his team's report.
			 */
			if (loginUser.getRole() == Role.MANAGEMENT) {
				for (Team team : loginUser.getTeams()) {
					if (!dailyReport.getStaffID().getAuthorities().contains(team)) {
						dailyReport.getStaffID().getAuthorities().add(team);
					}
				}
			}
		}

		/**
		 * Revised By   : Sai Kyaw Ye Myint
		 * Revised Date : 2017/09/08
		 * Explanation  : Added argument for login user info.
		 */
		if ((loginUser.getRole() == Role.ADMIN)) {
			dailyReport.getStaffID().setRole(loginUser.getRole());
		}
		//dailyReportList = dailyReportService.searchReport(dailyReport, searchDate);
		dailyReportList = dailyReportService.searchReport(dailyReport, searchDate , loginUser);

		WeeklyData weeklyData;
		List<WeeklyData> allDaysData = new ArrayList<WeeklyData>();
		DailyActivityData oneDayData;
		List<DailyActivity> activityListByReportID = new ArrayList<DailyActivity>();

		if (dailyReportList.size() > 0) {

			for (DailyReport reportID : dailyReportList) {
				weeklyData = new WeeklyData(reportID);
				weeklyData.setActivityDate(approvalDateFormat.format(reportID.getActivityDate()));
				// get daily activity list for one day
				activityListByReportID = dailyActivityService.findActivityByReportID(reportID);

				for (DailyActivity activityOneDayData : activityListByReportID) {

					oneDayData = new DailyActivityData();

					oneDayData.setFullName(activityOneDayData.getDailyReport().getStaffID().getFullName());

					if (activityOneDayData.getProject() != null) {
						oneDayData.setProjectID(activityOneDayData.getProject().getId());
						oneDayData.setProjectName(activityOneDayData.getProject().getProjectName());
					}

					if (activityOneDayData.getWbs() != null) {
						oneDayData.setWbsNo(activityOneDayData.getWbs());
					}

					if (activityOneDayData.getWbsFunction() != null) {
						oneDayData.setFunctionID(activityOneDayData.getWbsFunction());
					}

					if (activityOneDayData.getDeliveryOutput() != null) {
						oneDayData.setCategory(activityOneDayData.getTask().getDescription());
					}

					oneDayData.setCategory(activityOneDayData.getTask().getDescription());
					oneDayData.setActivities(activityOneDayData.getTaskActivity().getDescription());
					oneDayData.setDescription(activityOneDayData.getTaskDescription());

					if (activityOneDayData.getDeliveryOutput() != null) {
						oneDayData.setDeliveryOutput(activityOneDayData.getDeliveryOutput());
					}

					if (activityOneDayData.getReason() != null) {
						oneDayData.setReason(activityOneDayData.getReason());
					}

					if (activityOneDayData.getReview() != null) {
						oneDayData.setReview(activityOneDayData.getReview());
					}

					oneDayData.setProgress(activityOneDayData.getProgressPercentage());
					oneDayData.setHour(activityOneDayData.getActivityHours());

					if (activityOneDayData.getTaskStatus() == TaskStatus.FORWARD) {
						oneDayData.setStatus("FORWARD");
					} else if (activityOneDayData.getTaskStatus() == TaskStatus.DELAY) {
						oneDayData.setStatus("DELAY");
					}

					if (activityOneDayData.getTaskStatusCount() != null) {
						oneDayData.setTaskStatusCount(activityOneDayData.getTaskStatusCount());
					}

					if (activityOneDayData.getBeginDate() != null) {
						oneDayData.setBeginDate(activityOneDayData.getBeginDate());
					}

					if (activityOneDayData.getEndDate() != null) {
						oneDayData.setEndDate(activityOneDayData.getEndDate());
					}

					// total activity lists
					weeklyData.getActivities().add(oneDayData);
					weeklyData.setTotalHour(oneDayData.getHour());
				}

				allDaysData.add(weeklyData);
			}
			if (allDaysData.size() <= 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "There is no search result"));
			} else {
				weeklyReportData = allDaysData;
			}
			Collections.sort(weeklyReportData, WeeklyData.Comparators.DATE);
		}

		if (dailyReportList.size() == 0) {
			setBtnDel(true);
			setBtnApprove(true);
			setBtndisApprove(true);
			String InfoMessage = "There is no search result";
			weeklyReportData.clear();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", InfoMessage));

		} else {
			setBtnDel(false);
			setBtnApprove(false);
			setBtndisApprove(false);
			setDailyReportList(dailyReportList);
		}
	}

	/*
	 * This method is use to clear report data
	 */
	public void searchDataClear() {
		dailyReport.getStaffID().setUsername(null);
		dailyReport.getStaffID().setFullName(null);
		dailyReport.setLeaveStatus(null);
		dailyReport.setCheckDiv(null);
		searchDate.setFromDate(null);
		searchDate.setToDate(null);
		weeklyReportData.clear();
		setBtnControl(true);
		setBtnDel(true);
		setBtnApprove(true);
		setBtndisApprove(true);
	}

	/**
     * Revised By : Sai Kyaw Ye Myint
     * Revised Date : 2017/09/11
     * Explanation : Added listener for select box event.
     */ 
	public void rollSelectListener(){
		setBtnDel(false);
		setBtnApprove(false);
		setBtndisApprove(false);
		
		searchSelectedReport();
		
		for (WeeklyData weelyReport : checkboxlist) {
			if (weelyReport.getDailyReport().getCheckDiv() == CheckDiv.CHECKED) {
				setBtnDel(true);
				setBtnApprove(true);
				setBtndisApprove(false);
				break;
			}
		}
	}
	
	/*
	 * This method is use to search selected report and added into checkboxlist.
	 */
	private void searchSelectedReport() {
		checkboxlist = new ArrayList<>();
		if (weeklyReportData.size() > 0) {
			for (WeeklyData oneDayData : weeklyReportData) {
				if (oneDayData.isSelectRecord()) {
					checkboxlist.add(oneDayData);
				}
			}
		}
	}

	/*
	 * This method is a listener method of delete button and open the relative
	 * dialog.
	 */
	public void delBtnClicked() {
		searchSelectedReport();
		if (checkboxlist.size() != 0 || checkboxlist.size() < 0) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg2').show();");
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg3').show();");
		}
	}

	/*
	 * This method is a listener method of approve button and open the relative
	 * dialog.
	 */
	public void apBtnClicked() {
		searchSelectedReport();
		if (checkboxlist.size() != 0 || checkboxlist.size() < 0) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg4').show();");
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg3').show();");
		}
	}

	/*
	 * This method is a listener method of disapprove button and open the
	 * relative dialog.
	 */
	public void disapproveDailyReport() {
		searchSelectedReport();
		if (checkboxlist.size() != 0 || checkboxlist.size() < 0) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg5').show();");
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg3').show();");
		}
	}
	
	/**
	 * Revised By : Htet Wai Yum 
	 * Revised Date : 2018/09/10 
	 * Explanation: Update daily report information into summary activities table for daily registration page
	 */
	public void UpdatesummaryActivity(SummaryActivity summaryActivity) {
		try {

			summaryactivityService.updateSummaryActivity(summaryActivity);
		} catch (SystemException se) {
			handelSysException(se);
		}
	}
	
	/**
	 * Revised By : Htet Wai Yum 
	 * Revised Date : 2018/09/10 
	 * Explanation: Update activity date into summary activities table for daily registration page
	 */
	public void UpdateActivityDate(SummaryActivity summaryActivity) {
		try {
			
			summaryactivityService.updateActivityDate(summaryActivity);
		} catch (SystemException se) {
			handelSysException(se);
		}
	}

	/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/10
	    * Explanation: Delete daily report information into summary activities table for daily registration page
	    */  
	public void DeletesummaryActivity(SummaryActivity summaryActivity) {
		try {

			summaryactivityService.deleteSummaryActivity(summaryActivity);
		} catch (SystemException se) {
			handelSysException(se);
		}
	}
	
	
		
		
		

	/*
	 * This method is use to delete the selected report.
	 */
	public void deleteDailyReport() throws ParseException {
		dailyReportService.deleteWeeklyReportData(checkboxlist, dailyReportList);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Successfully Deleted!!"));

		if (dailyReportList.size() <= 0) {
			setBtnDel(true);
		} else {
			setBtnDel(false);
		}
		checkboxlist.clear();
		weeklyReportData.clear();
		searchReport();
	}

	/*
	 * This method is use to approve the selected report.
	 */
	public void approveDailyReport() {
		try {
			if (checkboxlist.size() > 0) {
				List<DailyReport> dailyReportIDList = new ArrayList<>();
				for (WeeklyData report : checkboxlist) {
					dailyReportIDList.add(report.getDailyReport());
				}

				Date now = new Date();
				for (DailyReport r : dailyReportIDList) {
					Approval a = new Approval();
					a.setDailyReport(r);
					a.setLastUpdDate(now);
					a.setApprover(loginUser);
					a.setDelDiv(DeleteDiv.ACTIVE);
					a.setStatus("");
					approvalService.addNewApproval(a);
				}

				dailyReportService.approveDailyReportLists(dailyReportIDList);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Successfully Approved!!"));

			if (dailyReportList.size() <= 0) {
				setBtnApprove(true);
			} else {
				setBtnApprove(false);
			}
			checkboxlist.clear();
			weeklyReportData.clear();
			searchReport();
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	/* *
	 * This method is use to approve the selected report.
	 */
	public void removeApproval() {
		try {
			if (checkboxlist.size() > 0) {
				List<DailyReport> dailyReportIDList = new ArrayList<>();
				for (WeeklyData report : checkboxlist) {
					dailyReportIDList.add(report.getDailyReport());
				}

				for (DailyReport r : dailyReportIDList) {
					List<Approval> result = approvalService.findApprovalByReportID(r);
					for (Approval a : result) {
						approvalService.deleteApproval(a);
					}
				}
				dailyReportService.disapproveDailyReportLists(dailyReportIDList);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Successfully Disapproved!!"));

			if (dailyReportList.size() <= 0) {
				setBtndisApprove(true);
			} else {
				setBtndisApprove(false);
			}

			weeklyReportData.clear();
			checkboxlist.clear();
			searchReport();
		} catch (SystemException e) {
			handelSysException(e);
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

	public void setDailyActivityService(IDailyActivityService dailyActivityService) {
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

	public LeaveStatus[] getLeaveStatuses() {
		return LeaveStatus.values();
	}

	public CheckDiv[] getCheckDivs() {
		return CheckDiv.values();
	}

	public SearchDate getSearchDate() {
		if (searchDate.equals(null)) {
			searchDate = new SearchDate();
		}
		return searchDate;
	}

	public void setSearchDate(SearchDate searchDate) {
		this.searchDate = searchDate;
	}

	public User getStaffData() {
		return staffData;
	}

	public void setStaffData(User staffData) {
		this.staffData = staffData;
	}

	public boolean isBtnControl() {
		return btnControl;
	}

	public void setBtnControl(boolean btnControl) {
		this.btnControl = btnControl;
	}

	public boolean isBtnDel() {
		return btnDel;
	}

	public void setBtnDel(boolean btnDel) {
		this.btnDel = btnDel;
	}

	public boolean isBtnApprove() {
		return btnApprove;
	}

	public void setBtnApprove(boolean btnApprove) {
		this.btnApprove = btnApprove;
	}

	public List<WeeklyData> getCheckboxlist() {
		return checkboxlist;
	}

	public void setCheckboxlist(List<WeeklyData> checkboxlist) {
		this.checkboxlist = checkboxlist;
	}

	public IApprovalService getApprovalService() {
		return approvalService;
	}

	public void setApprovalService(IApprovalService approvalService) {
		this.approvalService = approvalService;
	}

	public boolean isBtndisApprove() {
		return btndisApprove;
	}

	public void setBtndisApprove(boolean btndisApprove) {
		this.btndisApprove = btndisApprove;
	}

	public ISummaryActivityService getSummaryactivityService() {
		return summaryactivityService;
	}

	public void setSummaryactivityService(
			ISummaryActivityService summaryactivityService) {
		this.summaryactivityService = summaryactivityService;
	}
	
	
}
