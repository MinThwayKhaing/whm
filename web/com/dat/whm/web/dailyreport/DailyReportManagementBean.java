/***************************************************************************************
 * @author Aye Chan Thar Soe
 * @Date 2017-03-31
 * @Version 1.0
 * @Purpose <<Intend for DAR002.xhtml search page, Can Select,Update,Delete DailyReport and View Activities for each DailyReport>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.web.dailyreport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.dat.whm.approval.entity.Approval;
import com.dat.whm.approval.service.interfaces.IApprovalService;
import com.dat.whm.common.entity.CheckDiv;
import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.common.entity.LeaveStatus;
import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.service.DailyActivityService;
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
import com.dat.whm.web.common.WhmUtilities;
import com.dat.whm.web.workinghrmanagementproject.ProjectInfo;

@ManagedBean(name = "DailyReportManagementBean")
@ViewScoped
public class DailyReportManagementBean extends BaseBean {

	@ManagedProperty(value = "#{ApprovalService}")
	private IApprovalService approvalService;
	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;
	@ManagedProperty(value = "#{SummaryActivityService}")
	private ISummaryActivityService summaryactivityService;
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;
	private DailyReport dailyReport;
	private DailyActivity dailyActivity;
	public List<DailyReport> dailyReportList = new ArrayList<DailyReport>();
	List<DailyReport> checkboxlist = new ArrayList<DailyReport>();
	private List<DailyActivity> dailyActivityList = new ArrayList<DailyActivity>();
	private List<DailyActivity> checkdailyActivityList = new ArrayList<DailyActivity>();

	/**
	 * Revised By : Htet Wai Yum 
	 * Revised Date : 2018/09/10
	 * Explanation: Added daily report information list
	 * 
	 */
	List<DailyReport> uncheckboxlist = new ArrayList<DailyReport>();
	List<DailyActivity> uncheckboxData = new ArrayList<DailyActivity>();
	
	List<DailyActivity> uncheckdailyActivityList = new ArrayList<DailyActivity>();
	ArrayList<DailyActivity> updateddailyActivityList = new ArrayList<DailyActivity>();
	ArrayList<DailyActivity> checkupdateddailyActivityList = new ArrayList<DailyActivity>();
	ArrayList<DailyActivity> uncheckupdateddailyActivityList = new ArrayList<DailyActivity>();
	List<DailyActivity> arrList = new ArrayList<DailyActivity>();


	private List<DailyReport> dailyReportFilterList;

	List<SummaryActivity> summaryList = new ArrayList<SummaryActivity>();
	Map<Object, Boolean> status = new HashMap<Object, Boolean>();
	SearchDate searchDate;
	User staffData;
	User loginUser;
	LeaveStatus leaveStatus;
	CheckDiv checkDiv;
	int dailyReportListSize;
	boolean btnDel;
	boolean btndisApprove;
	boolean btnApprove;
	boolean btnApproveVisible;
	// boolean display;
	boolean editBtnDisable;
	boolean btnDelVisible;
	/**
	 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/09/08 Explanation:
	 * Added btndisApproveVisible for Remove Approval button visible flag.
	 */
	boolean btndisApproveVisible;

	/**
	 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/07/07 Explanation:
	 * Added SCEEN_ID and getter method for back button process of DAR001.xhtml.
	 */
	public final String SCREEN_ID = "DAR002.xhtml";

	/**
	 * Revised By : Aye Myat Mon Revised Date : 2018/08/20 Explanation : Added
	 * WhmUtilities for Date Filter.
	 */
	private WhmUtilities whmUtilities;

	
	
	@PostConstruct
	public void init() {

		prepareSearchData();
		
		
	}

	/**
	 * Revised By : Aye Thida Phyo Revised Date : 2017/06/28 Explanation: Modify
	 * visible for button and StaffData.
	 */

	public void prepareSearchData() {
		dailyReport = new DailyReport();
		searchDate = new SearchDate();
		staffData = new User();

		loginUser = new User();
		loginUser = (User) getParam(Constants.LOGIN_USER);
		/**
		 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/09/08 Explanation:
		 * Modify Delete,Approve and Remove Approval buttons visible policy.
		 */
		setBtnDelVisible(true);

		if ((loginUser.getRole() == Role.USER)
				&& (loginUser.getAuthorities().size() == 0)) {
			setBtnApproveVisible(false);
			// setBtnDelVisible(true);
			setBtndisApproveVisible(false);
			setStaffData(loginUser);
			dailyReport.setStaffID(staffData);
		} else if (loginUser.getRole() == Role.MANAGEMENT) {
			setBtnApproveVisible(true);
			// setBtnDelVisible(true);
			setBtndisApproveVisible(true);
			dailyReport.setStaffID(staffData);
		} else {
			setBtnApproveVisible(true);
			// setBtnDelVisible(false);
			setBtndisApproveVisible(true);
			dailyReport.setStaffID(staffData);
		}
		setBtnDel(true);
		setBtnApprove(true);
		setBtndisApprove(true);
		/**
		 * Revised By : Aye Myat Mon Revised Date : 2018/08/20 Explanation :
		 * Added WhmUtilities for Date Filter.
		 */
		whmUtilities = new WhmUtilities();
	}

	public void checkClicked() {

		if (checkboxlist.size() != 0 || checkboxlist.size() < 0) {
			// System.out.println("Clicked");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg2').show();");
		} else {
			// System.out.println("Not Clicked");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg3').show();");
		}
	}

	public void apBtnClicked() {

		if (checkboxlist.size() != 0 || checkboxlist.size() < 0) {
			// System.out.println("Clicked");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg4').show();");

		} else {
			// System.out.println("Not Clicked");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg3').show();");
		}
	}

	public void dapBtnClicked() {
		searchDailyReport();
		if (checkboxlist.size() != 0 || checkboxlist.size() < 0) {
			// System.out.println("Clicked");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg5').show();");

		} else {
			// System.out.println("Not Clicked");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg3').show();");
		}
	}
	
	

	
	
	public void deleteDailyReport() {
		
		dailyReportService.deleteDailyReportData(checkboxlist, checkdailyActivityList,dailyReportList);
		
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "",
						"Successfully Deleted!!"));
		if (dailyReportList.size() <= 0) {
			setBtnDel(true);
		} else {
			setBtnDel(false);
		}
		setDailyReportListSize(dailyReportList.size());
		status.clear();
		checkboxlist.clear();
	
	}

	public void approveDailyReport() {

		try {
			if (checkboxlist.size() != 0 || checkboxlist.size() < 0) {

				Date now = new Date();
				for (DailyReport r : checkboxlist) {
					Approval a = new Approval();
					a.setDailyReport(r);
					a.setLastUpdDate(now);
					a.setApprover(loginUser);
					a.setDelDiv(DeleteDiv.ACTIVE);
					a.setStatus("");
					approvalService.addNewApproval(a);
				}
				dailyReportService.approveDailyReportLists(checkboxlist);
			}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"Successfully Approved!!"));

			searchDailyReport();
			if (dailyReportList.size() <= 0) {
				setBtnApprove(true);
			} else {
				setBtnApprove(false);
			}
			setDailyReportListSize(dailyReportList.size());
			status.clear();
			checkboxlist.clear();
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void disapproveDailyReport() {

		try {
			if (checkboxlist.size() != 0 || checkboxlist.size() < 0) {

				Date now = new Date();
				for (DailyReport r : checkboxlist) {
					List<Approval> result = approvalService
							.findApprovalByReportID(r);
					for (Approval a : result) {
						approvalService.deleteApproval(a);
					}
				}
				dailyReportService.disapproveDailyReportLists(checkboxlist);
			}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"Successfully Disapproved!!"));

			searchDailyReport();
			if (dailyReportList.size() <= 0) {
				setBtndisApprove(true);
			} else {
				setBtndisApprove(false);
			}
			setDailyReportListSize(dailyReportList.size());
			status.clear();
			checkboxlist.clear();
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void searchDailyReport() {
		boolean flag = false;
		setBtnDel(false);
		setBtnApprove(false);
		setBtndisApprove(false);
		if (searchDate.getFromDate() != null && searchDate.getToDate() != null) {
			if (!isValidFromAndToDate(searchDate.getFromDate(),
					searchDate.getToDate())) {
				String errorMessage = "From Date must not greater than ToDate";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "",
								errorMessage));
				flag = true;

			}
		}
		if (!flag) {
			loginUser = (User) getParam(Constants.LOGIN_USER);
			if ((loginUser.getRole() != Role.ADMIN)
					&& (loginUser.getRole() != Role.MANAGEMENT)) {
				/**
				 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/09/08
				 * Explanation : Modify for user with authorities to search
				 * authorize team's report.
				 */
				if (loginUser.getAuthorities().size() == 0) {
					setStaffData(loginUser);
					dailyReport.setStaffID(loginUser);
					dailyReport.getStaffID().setId(loginUser.getId());
					dailyReport.getStaffID().setUsername(
							loginUser.getUsername());
					dailyReport.getStaffID().setFullName(
							loginUser.getFullName());
				}
			}
			/*
			 * if ((loginUser.getRole() == Role.MANAGEMENT)) {
			 * dailyReport.getStaffID().setRole(loginUser.getRole());
			 * dailyReport
			 * .getStaffID().setAuthorities(loginUser.getAuthorities()); }
			 */
			// dailyReport.getStaffID().setAuthorities(loginUser.getAuthorities());
			if ((loginUser.getRole() == Role.ADMIN)) {
				dailyReport.getStaffID().setRole(loginUser.getRole());
			}
			if (loginUser.getAuthorities().size() != 0) {
				dailyReport.getStaffID().setAuthorities(
						loginUser.getAuthorities());
				/**
				 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/09/08
				 * Explanation : Modify for management user to search all of his
				 * team's report.
				 */
				if (loginUser.getRole() == Role.MANAGEMENT) {
					for (Team team : loginUser.getTeams()) {
						if (!dailyReport.getStaffID().getAuthorities()
								.contains(team)) {
							dailyReport.getStaffID().getAuthorities().add(team);
						}
					}
				}
			}

			/**
			 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/09/08
			 * Explanation : Added argument for login user info.
			 */
			// dailyReportList = dailyReportService.searchReport(dailyReport,
			// searchDate);
			dailyReportList = dailyReportService.searchReport(dailyReport,
					searchDate, loginUser);
			// System.out.println("Search List size
			// >>>>>"+dailyReportList.size());
			if (dailyReportList.size() == 0) {
				setBtnDel(true);
				setBtnApprove(true);
				setBtndisApprove(true);
				String InfoMessage = "There is no search result";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								InfoMessage));
			}
			setDailyReportList(dailyReportList);
			setDailyReportListSize(dailyReportList.size());
			setDailyReportFilterList(null);
		}

	}

	@SuppressWarnings("unchecked")
	public void controlForm() {
		try {
			init();

			if (!FacesContext.getCurrentInstance().getExternalContext()
					.getFlash().isEmpty()) {
				boolean backInquiryFlag = (boolean) FacesContext
						.getCurrentInstance().getExternalContext().getFlash()
						.get("backInquiryFlag");

				if (backInquiryFlag) {
					setDailyReportList((List<DailyReport>) FacesContext
							.getCurrentInstance().getExternalContext()
							.getFlash().get("dailyReportList"));
					setDailyReportListSize(dailyReportList.size());
					setDailyReport((DailyReport) FacesContext
							.getCurrentInstance().getExternalContext()
							.getFlash().get("inquiryReport"));
					setSearchDate((SearchDate) FacesContext
							.getCurrentInstance().getExternalContext()
							.getFlash().get("searchDate"));
					setDailyReportFilterList(null);
					FacesContext.getCurrentInstance().getExternalContext()
							.getFlash().put("backInquiryFlag", false);
					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.getFlash()
							.put("dailyReportList",
									new ArrayList<DailyReport>());
					FacesContext.getCurrentInstance().getExternalContext()
							.getFlash().put("inquiryReport", new DailyReport());
					FacesContext.getCurrentInstance().getExternalContext()
							.getFlash().put("searchDate", new SearchDate());
					System.out.println("There");
					if (dailyReportList.size() > 0) {
						setBtnDel(false);
						setBtnApprove(false);
						setBtndisApprove(false);
					}
					searchDailyReport();
					
				}
			} else {
				dailyReportList = new ArrayList<DailyReport>();
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	/**
	 * Revised By : Aye Thida Phyo Revised Date : 2017/07/05 Explanation : Clear
	 * all input control and dailyReportFilterList.
	 */
	public void searchDataClear() {

		loginUser = (User) getParam(Constants.LOGIN_USER);
		if ((loginUser.getRole() != Role.ADMIN)
				&& (loginUser.getRole() != Role.MANAGEMENT)
				&& (loginUser.getAuthorities().size() == 0)) {
			dailyReport.setLeaveStatus(null);
			dailyReport.setCheckDiv(null);
			searchDate.setFromDate(null);
			searchDate.setToDate(null);
			dailyReportList.clear();
			if (dailyReportFilterList != null) {
				dailyReportFilterList.clear();
				setBtndisApprove(true);
				setDailyReportList(dailyReportFilterList);
				setDailyReportListSize(dailyReportFilterList.size());
				setDailyReportFilterList(null);
			}
		} else {
			dailyReport.getStaffID().setUsername(null);
			dailyReport.getStaffID().setFullName(null);
			dailyReport.setLeaveStatus(null);
			dailyReport.setCheckDiv(null);
			searchDate.setFromDate(null);
			searchDate.setToDate(null);
			dailyReportList.clear();
			if (dailyReportFilterList != null) {
				dailyReportFilterList.clear();
				setBtndisApprove(true);
				setDailyReportList(dailyReportFilterList);
				setDailyReportListSize(dailyReportFilterList.size());
				setDailyReportFilterList(null);
			}
		}
		setBtnDel(true);
		setBtnApprove(true);
		setBtndisApprove(true);
	}

	/**
	 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/09/11 Explanation :
	 * Added listener for select box event.
	 */
	public void btnManagement() {
		setBtnDel(false);
		setBtnApprove(false);
		setBtndisApprove(false);
		for (DailyReport dailyReport : checkboxlist) {
			if (dailyReport.getCheckDiv() == CheckDiv.CHECKED) {
				setBtnDel(true);
				setBtnApprove(true);
				setBtndisApprove(false);
				break;
			}
		}
	}

	public void onRowSelect(SelectEvent event) {
		btnManagement();
	}

	public void onRowUnselect(UnselectEvent event) {
		btnManagement();
	}

	public boolean isValidFromAndToDate(Date fromDate, Date toDate) {

		if ((fromDate.equals(toDate) || fromDate.before(toDate))) {
			return true;
		} else {
			return false;
		}

	}

	public Map<Object, Boolean> getStatus() {
		return status;
	}

	public void setStatus(Map<Object, Boolean> status) {
		this.status = status;
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

	public List<DailyReport> getDailyReportFilterList() {
		return dailyReportFilterList;
	}

	public void setDailyReportFilterList(List<DailyReport> dailyReportFilterList) {
		this.dailyReportFilterList = dailyReportFilterList;
	}

	public void setDailyReportListSize(int dailyReportListSize) {
		this.dailyReportListSize = dailyReportListSize;
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

	/**
	 * Revised By : Aye Thida Phyo Revised Date : 2017/06/28 Explanation :
	 * Modify visible for StaffData.
	 */
	/*
	 * public boolean isDisplay() { return display; }
	 * 
	 * public void setDisplay(boolean display) { this.display = display; }
	 */

	public List<DailyReport> getCheckboxlist() {
		return checkboxlist;
	}

	/**
	 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/07/07
	 */
	public String getSCREEN_ID() {
		return SCREEN_ID;
	}

	public void setCheckboxlist(List<DailyReport> checkboxlist) {
		this.checkboxlist = checkboxlist;
	}

	public boolean isEditBtnDisable() {
		return editBtnDisable;
	}

	public void setEditBtnDisable(boolean editBtnDisable) {
		this.editBtnDisable = editBtnDisable;
	}

	public IApprovalService getApprovalService() {
		return approvalService;
	}

	public void setApprovalService(IApprovalService approvalService) {
		this.approvalService = approvalService;
	}

	public boolean isBtnApproveVisible() {
		return btnApproveVisible;
	}

	public void setBtnApproveVisible(boolean btnApproveVisible) {
		this.btnApproveVisible = btnApproveVisible;
	}

	public boolean isBtndisApprove() {
		return btndisApprove;
	}

	public void setBtndisApprove(boolean btndisApprove) {
		this.btndisApprove = btndisApprove;
	}

	/**
	 * Revised By : Aye Thida Phyo Revised Date : 2017/06/28
	 */

	public boolean isBtnDelVisible() {
		return btnDelVisible;
	}

	public void setBtnDelVisible(boolean btnDelVisible) {
		this.btnDelVisible = btnDelVisible;
	}

	/**
	 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/09/08
	 */
	public boolean isBtndisApproveVisible() {
		return btndisApproveVisible;
	}

	public void setBtndisApproveVisible(boolean btndisApproveVisible) {
		this.btndisApproveVisible = btndisApproveVisible;
	}

	/**
	 * Revised By : Aye Myat Mon Revised Date : 2018/08/20 Explanation : Added
	 * getter and setter of WhmUtilities.
	 */
	public WhmUtilities getWhmUtilities() {
		return whmUtilities;
	}

	public void setWhmUtilities(WhmUtilities whmUtilities) {
		this.whmUtilities = whmUtilities;
	}

	/**
	 * Revised By : Htet Wai Yum Revised Date : 2018/09/10 Explanation: Added
	 * getter/setter for daily report information list
	 */
	/*public ArrayList<DailyActivity> getUpdateddailyActivityList() {
		return updateddailyActivityList;
	}

	public void setUpdateddailyActivityList(
			ArrayList<DailyActivity> updateddailyActivityList) {
		this.updateddailyActivityList = updateddailyActivityList;
	}*/

	public ISummaryActivityService getSummaryactivityService() {
		return summaryactivityService;
	}

	public void setSummaryactivityService(
			ISummaryActivityService summaryactivityService) {
		this.summaryactivityService = summaryactivityService;
	}

	/*public List<SummaryActivity> getSummaryList() {
		return summaryList;
	}

	public void setSummaryList(List<SummaryActivity> summaryList) {
		this.summaryList = summaryList;
	}*/

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}