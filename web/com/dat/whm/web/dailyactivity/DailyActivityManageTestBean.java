package com.dat.whm.web.dailyactivity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.text.ParseException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.OptimisticLockException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import com.dat.whm.common.entity.CheckDiv;
import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.common.entity.LeaveStatus;
import com.dat.whm.common.entity.TaskStatus;
import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.exception.SystemException;
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.service.interfaces.IProjectService;
import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.summaryactivity.service.interfaces.ISummaryActivityService;
import com.dat.whm.task.entity.Task;
import com.dat.whm.task.service.interfaces.ITaskService;
import com.dat.whm.taskactivity.entity.TaskActivity;
import com.dat.whm.taskactivity.service.interfaces.ITaskActivityService;
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.userteammainlybelonghistory.entity.UserTeamMainlyBelongHistory;
import com.dat.whm.userteammainlybelonghistory.service.interfaces.IUserTeamMainlyBelongHistoryService;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.MessageId;
import com.dat.whm.web.common.SearchDate;

@ManagedBean(name = "DailyActivityManageTestBean")
@ViewScoped
public class DailyActivityManageTestBean extends BaseBean {

	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;
	@ManagedProperty(value = "#{ProjectService}")
	private IProjectService projectService;
	@ManagedProperty(value = "#{TaskService}")
	private ITaskService taskService;
	@ManagedProperty(value = "#{TaskActivityService}")
	private ITaskActivityService taskActivityService;
	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;
	@ManagedProperty(value = "#{UserTeamMainlyBelongHistoryService}")
	private IUserTeamMainlyBelongHistoryService mainlyBelongTeamHistoryService;
	@ManagedProperty(value = "#{SummaryActivityService}")
	private ISummaryActivityService summaryActivityService;
	
	private List<DailyReport> dailyReportList = new ArrayList<DailyReport>();	
	private List<DailyActivity> dailyActivityList = new ArrayList<DailyActivity>();
	public static List<DailyActivity> olddailyActivityList = new ArrayList<DailyActivity>();

	private List<DailyActivity> activitesListToCopy;
	private List<DailyActivity> selectedActivities;
	private DailyActivity dailyActivity;	
	private List<Project> projects;
	private List<Task> tasks;
	private List<TaskActivity> taskActivities;
	private Date reportDate = null;
	private Date oldDate = null;
	public Date duplicateDate;
	private int leaveStatus;
	private int saveBtnDisabled;
	private double planTotalHours = 0;
	private int nameDisabled;
	private boolean staffFlag;
	private boolean adminFlag;
	private String leaveDescription;
	private double totalHours = 0;
	private static Date today = new Date();
	private User user = new User();
	private DailyReport dailyReport;
	
	private String staffId;
	private String staffName;
	private static final String defaultLeaveDescription = "Activity Hour must be registered at least 7.5 hour.";
	private String suggestMessage;	
	private static final String fullLeaveDescription = "Activity Hour will be registered as 7.5 hour automatically.";

	private static final String halfLeaveDescription = "Activity Hour will be registered as 3.5 hour automatically.";
	static int tempId;
	DailyReport tempDailyReport;
	
	List<DailyActivity> tempDailyActivityList;
	double tempHours;
	User loginUser;
	User login;
	boolean fullLeave;
	boolean halfLeave;
	boolean editFlag;
	boolean planReport;
	private String projectName;

	private List<DailyActivity> dailyActivityListByReportID;

	private int backBtnDisabled;
	List<DailyReport> drlist;
	boolean backInquiryFlag;
	DailyReport inquiryReport;
	SearchDate searchDate;
	public String screenId;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		load();
		if (!FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.isEmpty()) {
			drlist = (List<DailyReport>) FacesContext.getCurrentInstance()
					.getExternalContext().getFlash().get("dailyReportList");
			backInquiryFlag = (boolean) FacesContext.getCurrentInstance()
					.getExternalContext().getFlash().get("backInquiryFlag");
			inquiryReport = (DailyReport) FacesContext.getCurrentInstance()
					.getExternalContext().getFlash().get("inquiryReport");
			searchDate = (SearchDate) FacesContext.getCurrentInstance()
					.getExternalContext().getFlash().get("searchDate");
			screenId = (String) FacesContext.getCurrentInstance()
					.getExternalContext().getFlash().get("screenId");
		dailyReportList= drlist;
			
		}
	}

	public void load() {
		loginUser = (User) getParam(Constants.LOGIN_USER);
		projects = projectService.findAllProject();
		tasks = taskService.findAllTask();
		tasks.remove(taskService.findTask(30));
		taskActivities = taskActivityService.findAllTaskActivity();
		today = new Date();
		reportDate = today;
		setLeaveDescription(defaultLeaveDescription);
	}
	public Double calculatePlanActualDifference(Double plan, Double real ){
		double diff = 0.0;
		Double planned = 0.0;
		Double actual = 0.0;

		if (plan != null) {
			planned = plan;

		}

		if(real != null){
			actual = real;

		}

		diff = planned - actual;

		return diff;
	}

	public void checkPlanReport() throws ParseException
	{
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date planTime= new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String todayDate = dateFormat.format(today);
	    String reportD = dateFormat.format(reportDate);
		Date todayD = dateFormat.parse(todayDate);
		Date rptD = dateFormat.parse(reportD);

		try {
			planTime = timeFormat.parse("00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		//Previous Date.
		if (!editFlag) {
			if(todayD.after(rptD)){


				planReport = false;
				if (dailyActivityList.size() >0) {
					for (DailyActivity element : dailyActivityList) {
						element.setPlanStartTime(planTime);
						element.setPlanEndTime(planTime);
						element.setPlanHours(Double.parseDouble("0.0"));
						element.setTempPlanHour("0.0");
						element.setPlanProgress(null);
						element.setTempPlanProgress("");
						element.setDisableLink(false);
						element.setDiffActivityHours(
								calculatePlanActualDifference(element.getPlanHours(), element.getActivityHours()));
						element.setDiffProgress(
								calculatePlanActualDifference(element.getPlanProgress(),element.getProgressPercentage()));
					}
					calculateHours();
				}
			}else{
					planReport = true;
					//Reset actual progress, actual time
					if (dailyActivityList.size() >0) {
						for (DailyActivity element : dailyActivityList) {
							element.setActivityHours(Double.parseDouble("0.0"));
							element.setTempHour("0.0");
							element.setProgressPercentage(null);
							element.setTempProgress("");
							element.setDisableLink(false);
							element.setDiffActivityHours(
									calculatePlanActualDifference(element.getPlanHours(), element.getActivityHours()));
							element.setDiffProgress(
									calculatePlanActualDifference(element.getPlanProgress(),element.getProgressPercentage()));
						}
						calculateHours();
					}
				}
			}
		/**
		 * Revised By   : Ye Yint Lin, Aye Myat Mon
		 * Revised Date : 2019/12/13
		 * Explanation 	: Modify to be able to edit plan hour and progress if planned date is greater than modification date.
		 */
		else{
			String lastUpdatedDate = dateFormat.format(tempDailyReport.getLastUpdDate());
		    Date lastUpdateD = dateFormat.parse(lastUpdatedDate);
			if((reportDate.equals(todayD) && lastUpdateD.equals(todayD)) || reportDate.before(todayD)){
				planReport = false;
			}else{
				planReport = true;
			}
		}

	}

	public void controlForm() {
		setLeaveStatus(0);
		setUser(loginUser);
		setStaffId(loginUser.getUsername());
		setStaffName(loginUser.getFullName());
		if ((user.getRole() == Role.ADMIN)
				|| (user.getRole() == Role.MANAGEMENT)) {
			setAdminFlag(true);
			setStaffFlag(false);
		} else if ((user.getRole() == Role.USER)) {
			setStaffFlag(true);
			setAdminFlag(false);
		}
		changeReport();
		try {
			checkPlanReport();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeReport() {
		if (DailyActivityMBUtil.isEditReportFlag()) {
			try {
				editFlag = true;
				tempDailyReport = DailyActivityMBUtil.getTempDailyReport();
				setUser(tempDailyReport.getStaffID());
				setStaffId(user.getUsername());
				setStaffName(user.getFullName());
				setOldDate(tempDailyReport.getActivityDate());
				this.dailyActivityList = dailyActivityService
						.findActivityByReportID(tempDailyReport);
				this.tempDailyActivityList = dailyActivityService
						.findActivityByReportID(tempDailyReport);
				
				for (int i = 0; i < dailyActivityList.size(); i++) {
					totalHours += dailyActivityList.get(i).getActivityHours();
					totalHours = round(totalHours, 2);
					dailyActivityList.get(i).setTempId(tempId);
					tempDailyActivityList.get(i).setTempId(tempId);

					if (dailyActivityList.get(i).getActivityHours() != null) {
						dailyActivityList.get(i).setTempHour(
								dailyActivityList.get(i).getActivityHours()
										.toString());
					}
					if (dailyActivityList.get(i).getProgressPercentage() != null) {
						dailyActivityList.get(i).setTempProgress(
								dailyActivityList.get(i)
										.getProgressPercentage().toString());
					}
					if (dailyActivityList.get(i).getTaskStatusCount() != null) {
						dailyActivityList.get(i).setTempTaskStatusCount(
								dailyActivityList.get(i).getTaskStatusCount()
										.toString());
					}

					tempId++;
					if (dailyActivityList.get(i).getTaskDescription()
							.equals("Full Leave")) {
						dailyActivityList.remove(i);
						i--;
					} else if (dailyActivityList.get(i).getTaskDescription()
							.equals("Half Leave")) {
						dailyActivityList.remove(i);
						i--;
					}
				}
				this.reportDate = tempDailyReport.getActivityDate();
				if (tempDailyReport.getLeaveStatus() == LeaveStatus.PRESENT) {
					this.leaveStatus = 0;
				} else if (tempDailyReport.getLeaveStatus() == LeaveStatus.FULL_LEAVE) {
					this.leaveStatus = 1;
					fullLeave = true;
					setLeaveDescription(fullLeaveDescription);
				} else if (tempDailyReport.getLeaveStatus() == LeaveStatus.HALF_LEAVE) {
					this.leaveStatus = 2;
					halfLeave = true;
					setLeaveDescription(halfLeaveDescription);
				}
				setNameDisabled(1);
				
				for (DailyActivity dailyActivity : dailyActivityList) {
					if (dailyActivity.getProject() != null) {
						for (Project project : projects) {
							if (project.getId().equals(dailyActivity.getProject().getId())) {
								dailyActivity.setProject(project);
								break;
							}
						}
					}
				}
			} catch (SystemException se) {
				handelSysException(se);
			}
		} else {
			setBackBtnDisabled(1);
		}
	}

	public void checkTotalHour(){
		RequestContext context = RequestContext.getCurrentInstance();
		if (dailyActivityList.size() > 0) {
			for (DailyActivity d : dailyActivityList) {
				if (!saveValidation(d)) {
					return;
				}
			}
		}
		switch (leaveStatus) {
		/*Present*/
		case 0:
			if(totalHours < 7.5){
				context.execute("PF('saveConfirmDialog').show();");
				setSuggestMessage("Activity Hour should be registered at least 7.5 hour. Would you like to save as it is?");
			}else {
				save();
			}
			break;
		/*Full Leave*/
		case 1:
			save();
			break;
		/*Half Leave*/
		case 2:
			if(totalHours < 7.5){
				context.execute("PF('saveConfirmDialog').show();");
				setSuggestMessage("Activity Hour must not be less than 4 hour. Would you like to save as it is?");
			}else {
				save();
			}
			break;
		}
	}

	public void save() {
		try {

			for (DailyActivity d : dailyActivityList) {
				System.out.println("this is activity hour"+d.getActivityHours());
				if (!saveValidation(d)) {
					return;
				}
			}
			List<DailyReport> drList = null;

			if (isAdminFlag()) {
				User u = userService.findUser(staffId);
				drList = dailyReportService.findByDate(reportDate, u);
			} else {
				drList = dailyReportService.findByDate(reportDate, user);
			}

			if ((!editFlag) && (!drList.isEmpty())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"Report already exists!!"));
				return;
			}

			List<DailyReport> result;
			if (isAdminFlag()) {
				User u = userService.findUser(staffId);
				result = dailyReportService.findByDateforDuplicate(reportDate,
						u);
			} else {
				result = dailyReportService.findByDateforDuplicate(reportDate,
						user);
			}

			if (!result.isEmpty()) {
				dailyReport = result.get(0);
			} else if (editFlag) {
				dailyReport = tempDailyReport;
			} else {
				dailyReport = new DailyReport();
			}

			dailyReport.setStaffID(user);

			if (isAdminFlag()) {
				User u = userService.findUser(staffId);
				dailyReport.setStaffID(u);
			}
			dailyReport.setActivityDate(reportDate);
			switch (leaveStatus) {
			case 0:
				dailyReport.setTotalhours(totalHours);
				dailyReport.setLeaveStatus(LeaveStatus.PRESENT);
				break;
			case 1:
				dailyReport.setTotalhours(0.0);
				dailyReport.setLeaveStatus(LeaveStatus.FULL_LEAVE);
				break;
			case 2:
				dailyReport.setTotalhours(totalHours - 3.5);
				dailyReport.setLeaveStatus(LeaveStatus.HALF_LEAVE);
				break;
			}

			dailyReport.setDelDiv(DeleteDiv.ACTIVE);
			dailyReport.setCheckDiv(CheckDiv.UNCHECKED);

			if (leaveStatus != 1) {

				if (dailyActivityList.isEmpty()) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
									"You haven't added any activity record !"));
					return;
				}
				
				System.out.println("LEAVE STATUS NOT EQUAL 1" + leaveStatus);
				saveDailyReport(dailyReport);
				
				if (leaveStatus == 2) {
					
					DailyActivity d = saveHalfLeaveRecord(dailyReport);
					dailyActivityService.addNewDailyActivity(d);
				}
				
			} else {
				System.out.println("LEAVE STATUS EQUAL 1");

				if (editFlag) {
					deleteActivities(tempDailyActivityList);
				}
				saveDailyReport(dailyReport);
				DailyActivity d = saveFullLeaveRecord(dailyReport);
				dailyActivityService.addNewDailyActivity(d);
				
			}
			addInfoMessage(null, MessageId.MS0001);

			if (DailyActivityMBUtil.isEditReportFlag()) {
				setEditReportFlagToFalse();
			}
			clear();
			load();
		} catch (SystemException se) {
			handelSysException(se);
		} catch (OptimisticLockException oe) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
									"Cannot be saved because it has changed or been deleted since it was last read"));
		}
	}
	
	
		
	
		
	
	public void saveDailyReport(DailyReport dailyReport) {
		Date now = new Date();
		List<UserTeamMainlyBelongHistory> teamHistoryList = new ArrayList<>();
		List<UserTeamMainlyBelongHistory> currentteamHistoryList = new ArrayList<>();
		teamHistoryList = mainlyBelongTeamHistoryService.findByPeriod(dailyReport.getStaffID().getId(), dailyReport.getActivityDate());
		currentteamHistoryList = mainlyBelongTeamHistoryService.findByIds(dailyReport.getStaffID().getId());
		SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");
		
		if (isAdminFlag()) {
			User u = userService.findUser(staffId);
			loginUser=u;
		}
		 
		try {
			if (editFlag) {
				System.out.println("EDIT FLAG ==> LEAVE STATUS ==>"+leaveStatus);
				if(teamHistoryList.size() > 0){
					for (UserTeamMainlyBelongHistory mainTeamList : teamHistoryList) {
						loginUser.setMainlyBelongTeam(mainTeamList.getTeam());
					
					}
					}else{
						for (UserTeamMainlyBelongHistory currentteam : currentteamHistoryList) {
							
						loginUser.setMainlyBelongTeam(currentteam.getTeam());
						
						}
					}
				dailyReport.setLastUpdBy(loginUser);
				dailyReport.setLastUpdDate(now);
				List<DailyReport> result = dailyReportService
						.findByDateforDuplicate(dailyReport.getActivityDate(),
								dailyReport.getStaffID());

			
				dailyActivityService.saveDailyReportData(dailyReport,dailyActivityList,tempDailyActivityList,editFlag,result,isAdminFlag(),staffId,loginUser,oldDate);
				List<DailyReport> dailyreportlist = dailyReportService.findByDate(oldDate, loginUser);
				if(dailyreportlist.size() > 0){
					for(DailyReport dr: dailyreportlist){
						 List<DailyActivity> activitylist = dailyActivityService.findActivityByReportID(dr);
						 if(activitylist.size() == 0 ){
							 if(!oldDate.toString().equals(dailyReport.getActivityDate().toString())){
								 dailyReportService.deleteDailyReportLists(dailyreportlist);
							 }
						 }
						 
					}
				}
				
				//delete old half_leave record
				for(DailyActivity d:dailyActivityList){
					if(!d.getTaskDescription().equals("Half Leave")){
						
				List<DailyReport> newdailyreportlist = dailyReportService.findByDate(dailyReport.getActivityDate(), loginUser);
				for(DailyReport dr: newdailyreportlist){
				List<DailyActivity> activitylist = dailyActivityService.findActivityByReportID(dr);
				 for (int i = 0; i < activitylist.size(); i++) {
					 if(activitylist.get(i).getTaskDescription().equals("Half Leave")){
						dailyActivityService.deleteDailyActivity(activitylist.get(i)); 
					 }
				 }
				}
				
					}
				}
				
			} else {
				if(teamHistoryList.size() > 0){
					for (UserTeamMainlyBelongHistory mainTeamList : teamHistoryList) {
						loginUser.setMainlyBelongTeam(mainTeamList.getTeam());
					
					}
					}else{
						for (UserTeamMainlyBelongHistory currentteam : currentteamHistoryList) {
							
						loginUser.setMainlyBelongTeam(currentteam.getTeam());
						
						}
					}
				dailyReport.setCreatedBy(loginUser);
				dailyReport.setCreatedDate(now);
				List<DailyReport> result = dailyReportService
						.findByDateforDuplicate(dailyReport.getActivityDate(),
								dailyReport.getStaffID());
		
				System.out.println("Save Daily Report#############");
				
				dailyActivityService.saveDailyReportData(dailyReport,dailyActivityList,tempDailyActivityList,editFlag,result,isAdminFlag(),staffId,loginUser,oldDate);
			
			}
		} catch (SystemException se) {
			handelSysException(se);
		}
	}

	public DailyActivity saveFullLeaveRecord(DailyReport dailyReport)
			throws SystemException {
		DailyActivity d = new DailyActivity();
		d.setDailyReport(dailyReport);
		d.setActivityHours(7.5);
		d.setTaskDescription("Full Leave");
		d.setTask(taskService.findTask(DailyActivityMBUtil.getLeavetaskid()));
		d.setTaskActivity(taskActivityService
				.findTaskActivity(DailyActivityMBUtil.getOthertaskactivityid()));
		return d;
	}

	public DailyActivity saveHalfLeaveRecord(DailyReport dailyReport)
			throws SystemException {
		DailyActivity d = new DailyActivity();
		d.setDailyReport(dailyReport);
		d.setActivityHours(3.5);
		d.setTaskDescription("Half Leave");
		d.setTask(taskService.findTask(DailyActivityMBUtil.getLeavetaskid()));
		d.setTaskActivity(taskActivityService
				.findTaskActivity(DailyActivityMBUtil.getOthertaskactivityid()));
		return d;
	}

	public void saveNewActivities(List<DailyActivity> newList,
			List<DailyActivity> tempList) {
		try {
			a: for (int i = 0; i < newList.size(); i++) {
				for (DailyActivity d : tempList) {
					if (newList.get(i).getTempId() == d.getTempId()) {
						dailyActivityService
								.updateDailyActivity(newList.get(i));
						System.out.println("Update.....");
						continue a;
					}
				}
				System.out.println("Adding......");
				dailyActivityService.addNewDailyActivity(newList.get(i));
				//dailyActivityService.saveDailyReportData(dailyReport,dailyActivityList,tempDailyActivityList,editFlag,null,newList.get(i));
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void deleteOldActivities(List<DailyActivity> newList,
			List<DailyActivity> tempList) {
		try {
			a: for (int i = 0; i < tempList.size(); i++) {
				for (DailyActivity d : newList) {
					if (tempList.get(i).getTempId() == d.getTempId()) {
						continue a;
					}
				}
				dailyActivityService.deleteDailyActivity(tempList.get(i));
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void deleteActivities(List<DailyActivity> tempList) {
		try {
			for (DailyActivity d : tempList) {
				dailyActivityService.deleteDailyActivity(d);
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void addActivity(SelectEvent event) {
		dailyActivity.setTempId(tempId);
		if (dailyActivity.getTaskStatus() != null) {
			if (dailyActivity.getTaskStatusCount() == null)
				dailyActivity.setTaskStatusCount(1.0);
		} else {
			if (dailyActivity.getTaskStatusCount() != null) {
				dailyActivity.setTaskStatusCount(null);
			}
		}
		dailyActivityList.add(dailyActivity);
		totalHours += dailyActivity.getActivityHours();
		totalHours = round(totalHours, 2);
		tempId++;
	}

	public void updateActivity(SelectEvent event) {
		DailyActivity dailyActivity = (DailyActivity) event.getObject();
		if (dailyActivity.getTaskStatus() != null) {
			if (dailyActivity.getTaskStatusCount() == null)
				dailyActivity.setTaskStatusCount(1.0);
		} else {
			if (dailyActivity.getTaskStatusCount() != null) {
				dailyActivity.setTaskStatusCount(null);
			}
		}
		totalHours -= tempHours;
		totalHours += dailyActivity.getActivityHours();
		totalHours = round(totalHours, 2);
	}

	/**
	 * Revised By : Ye Thu Naing Revised Date : 2017/06/30 Updated For : Phase
	 * II Explanation : Deleting the selected row and remove row from
	 * "Daily Progress List".
	 */
	public void deleteActivity(DailyActivity dailyActivity) {
		dailyActivityList.remove(dailyActivity);
		calculateHours();
	}

	public void openForm() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", false);
		options.put("modal", true);
		options.put("width", 470);
		RequestContext.getCurrentInstance().openDialog(
				"/view/dailyreport/DAR001_popUp", options, null);
	}

	public void editForm(DailyActivity dailyActivity) {
		DailyActivityMBUtil.setEditActivityFlag(true);
		DailyActivityMBUtil.setTempDailyActivity(dailyActivity);
		tempHours = dailyActivity.getActivityHours();
		openForm();
	}

	public void changeActivity() {
		if (DailyActivityMBUtil.isEditActivityFlag()) {
			this.dailyActivity = DailyActivityMBUtil.getTempDailyActivity();
			if (dailyActivity.getProject() == null) {
				setProjectName("--------");
			} else {
				setProjectName(dailyActivity.getProject().getProjectName());
			}
			DailyActivityMBUtil.setEditActivityFlag(false);
		}
	}

	public void closeForm(DailyActivity dailyActivity) {
		RequestContext.getCurrentInstance().closeDialog(dailyActivity);
	}

	public void checkLeaveStatus() {
		if (leaveStatus == 1) {
			dailyActivityList.removeAll(dailyActivityList);
			setLeaveDescription(fullLeaveDescription);
			totalHours = 7.5;
			fullLeave = true;
			halfLeave = false;
		} else if (leaveStatus == 2) {
			setLeaveDescription(halfLeaveDescription);
			if (fullLeave) {
				totalHours = 3.5;
			} else if (!halfLeave) {
				totalHours += 3.5;
				totalHours = round(totalHours, 2);
			}
			fullLeave = false;
			halfLeave = true;
		} else {
			/** Revised By Sai Kyaw Ye Myint on 2018/04/27. */
			setLeaveDescription(defaultLeaveDescription);
			/** Revised By Sai Kyaw Ye Myint on 2018/04/27. */
			if (fullLeave) {
				totalHours = 0;
			} else if (halfLeave) {
				totalHours -= 3.5;
				totalHours = round(totalHours, 2);
			}
			fullLeave = false;
			halfLeave = false;
		}
	}

	public void checkDate(Date reportDate) {
		/** Revised By Ye Thu Naing on 2017/07/11. */
		if (reportDate == null) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"Please select Report Date."));
			setSaveBtnDisabled(1);
		} else {
			/** Revised By Ye Thu Naing on 2017/07/11. */

			List<DailyReport> result = null;
			try {
				if (isAdminFlag()) {
					User u = userService.findUser(staffId);
					result = dailyReportService.findByDate(reportDate, u);
				} else {
					result = dailyReportService.findByDate(reportDate, user);
				}

				if (editFlag) {
					if (!result.isEmpty()
							&& !reportDate.equals(DailyActivityMBUtil
									.getTempDailyReport().getActivityDate())) {
						setSaveBtnDisabled(1);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"", "Report already exists!!"));
					} else {
						setSaveBtnDisabled(0);
					}
				} else {
					if (!result.isEmpty()) {
						setSaveBtnDisabled(1);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"", "Report already exists!!"));
					} else {
						setSaveBtnDisabled(0);
					}
				}
			} catch (SystemException e) {
				handelSysException(e);
			}

		}

	}

	public void changeUser() {
		if (staffId.trim().equals("")) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"Please enter User ID."));
			/** Revised By Ye Thu Naing on 2017/07/11. */
			setStaffId("");
			setStaffName("");
			setSaveBtnDisabled(1);
			/** Revised By Ye Thu Naing on 2017/07/11. */
		} else {
			try {
				User u = userService.findUser(staffId);
				if (u != null) {
					setStaffName(u.getFullName());
					setSaveBtnDisabled(0);
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
									"User doesn't exists!!"));

					/** Revised By Ye Thu Naing on 2017/07/11. */
					setStaffName("");
					setSaveBtnDisabled(1);
					/** Revised By Ye Thu Naing on 2017/07/11. */
				}
			} catch (SystemException e) {
				handelSysException(e);
			}
		}
		setReportDate(null);
		setLeaveStatus(0);
	}

	/**
	 * Revised By Ye Thu Naing on 2017/06/30. Project name chaning is controlled
	 * in view.
	 */
	/*
	 * public void changeProject() { if (dailyActivity.getProject() == null) {
	 * setProjectName("--------"); } else {
	 * setProjectName(dailyActivity.getProject().getProjectName()); //
	 * System.out.println("Current..Project:" + //
	 * dailyActivity.getProject().getId()); } }
	 */
	/** Revised By Ye Thu Naing on 2017/06/30. */

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public void clear() {
		setReportDate(null);
		setLeaveStatus(0);
		totalHours = 0;
		halfLeave = false;
		fullLeave = false;
		leaveDescription = "";
		dailyActivityList.removeAll(dailyActivityList);
	}

	public void setEditReportFlagToFalse() {
		DailyActivityMBUtil.setEditReportFlag(false);
	}

	/**
	 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/07/07 Explanation:
	 * Revised for dynamically redirect page base on the screenId.
	 */
	public void back() {

		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("dailyReportList", drlist);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("inquiryReport", inquiryReport);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("searchDate", searchDate);

		try {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect(context.getRequestContextPath()
					+ "/faces/view/dailyreport/" + screenId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return "dailyReportInquiry";
	}

	/**
	 * Revised By : Sai Kyaw Ye Myint Revised Date : 2017/07/07 Explanation:
	 * Added screenId argument for back button process.
	 */
	public String setEditReportPage(DailyReport dailyReport,
			List<DailyReport> dailyReportList, DailyReport inquiryReport,
			SearchDate searchDate, String screenId) {
		DailyActivityMBUtil.setEditReportFlag(true);
		DailyActivityMBUtil.setTempDailyReport(dailyReport);

		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("dailyReportList", dailyReportList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("inquiryReport", inquiryReport);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("searchDate", searchDate);
		/** Revised By Sai Kyaw Ye Myint on 2017/07/07. */
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("screenId", screenId);
		
		/** Revised By Sai Kyaw Ye Myint on 2017/07/07. */
		return "/faces/view/dailyreport/DAR001.xhtml?faces-redirect=true";
	}

	public void activityList(DailyReport dailyReport) {
		dailyActivityListByReportID = dailyActivityService
				.findActivityByReportID(dailyReport);
		totalHours = 0.0;
		for (int i = 0; i < dailyActivityListByReportID.size(); i++) {
			totalHours += dailyActivityListByReportID.get(i).getActivityHours();
			if (dailyActivityListByReportID.get(i).getTaskStatus() != null) {
				if (dailyActivityListByReportID.get(i).getTaskStatusCount() == null)
					dailyActivityListByReportID.get(i).setTaskStatusCount(1.0);
			}

		}
	}

	/**
	 * Revised By : Ye Thu Naing Revised Date : 2017/06/30 Updated For : Phase
	 * II Explanation : Performing total calculation method for "Total Hours".
	 */
	public void calculateHours() {
		double totalHr = 0;
		for (DailyActivity activity : dailyActivityList) {
			if (activity.getActivityHours() != null) {
				totalHr += activity.getActivityHours();
			}
		}
		if (halfLeave) {
			totalHours = 3.5 + totalHr;
			totalHours = round(totalHours, 2);
		} else {
			totalHours = totalHr;
			totalHours = round(totalHours, 2);
		}
		if (totalHours > 24) {
			addErrorMessage(null, MessageId.MS0003, "Total Hours", "0.1 and 24");
		}
	}

	public void progressValidation(String progress, int index, boolean isPlan) {
		try {
			if (progress.trim().equals(null) || progress.trim().isEmpty()
					|| progress.trim().equals(" ")) {
				dailyActivityList.get(index).setProgressFlag(true);
				if (isPlan) {
					dailyActivityList.get(index).setTempPlanProgress("");
					dailyActivityList.get(index).setPlanProgress(null);
				}else{
					dailyActivityList.get(index).setTempProgress("");
					dailyActivityList.get(index).setProgressPercentage(null);
				}
				dailyActivityList.get(index).setProgressFlag(true);
			} else if (Double.parseDouble(progress) > 100
					|| Double.parseDouble(progress) <= 0) {
				DecimalFormat df = new DecimalFormat("0.0##");
				if (isPlan) {
					dailyActivityList.get(index).setTempPlanProgress(
							df.format(Double.parseDouble(progress)));
				}else{
					dailyActivityList.get(index).setTempProgress(
							df.format(Double.parseDouble(progress)));
				}

				dailyActivityList.get(index).setProgressFlag(false);

				addErrorMessage(null, MessageId.MS0003, "Progress", "1 to 100");
			} else {
				DecimalFormat df = new DecimalFormat("0.0##");
				if (isPlan) {
					dailyActivityList.get(index).setTempPlanProgress(
							df.format(Double.parseDouble(progress)));
					dailyActivityList.get(index).setPlanProgress(
							Double.parseDouble(progress));
				}else{
					dailyActivityList.get(index).setTempProgress(
							df.format(Double.parseDouble(progress)));
					dailyActivityList.get(index).setProgressPercentage(
							Double.parseDouble(progress));
				}

				dailyActivityList.get(index).setProgressFlag(true);
			}

			Double diffProgress = 0.0;
			diffProgress =calculatePlanActualDifference(dailyActivityList.get(index).getPlanProgress(),
					dailyActivityList.get(index).getProgressPercentage());
			dailyActivityList.get(index).setDiffProgress(diffProgress);

		} catch (NumberFormatException e) {
			dailyActivityList.get(index).setProgressFlag(false);
			addErrorMessage(null, MessageId.MS0004, "Progress");
		}
	}
	public void addRow() {
		//Register
		dailyActivity = new DailyActivity();
//				if (planReport) {
//					dailyActivity.setActivityHours(null);
//					dailyActivity.setTempHour("0.0");
//					dailyActivity.setProgressPercentage(null);
//					dailyActivity.setTempProgress("");
//					dailyActivity.setDisableLink(false);
//					dailyActivity.setIsPlanActivity(true);
//
//				}else{//Edit Register or not plan.
//
//					SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
//					Date planTime= new Date();
//					try {
//						planTime = timeFormat.parse("00:00");
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					dailyActivity.setPlanStartTime(planTime);
//					dailyActivity.setPlanEndTime(planTime);
//					dailyActivity.setPlanHours(Double.parseDouble("0.0"));
//					dailyActivity.setTempPlanHour("0.0");
//					dailyActivity.setPlanProgress(null);
//					dailyActivity.setTempPlanProgress("");
//					dailyActivity.setDisableLink(false);
//					dailyActivity.setIsPlanActivity(false);
//
//				}

				dailyActivityList.add(dailyActivity);
	}
	public void descriptionValidation(String description, int index) {
		if (description.trim().equals(null) || description.trim().equals("")
				|| description.isEmpty()) {
			dailyActivityList.get(index).setTaskDescription(null);
			addErrorMessage(null, MessageId.REQUIRED, "Description");
			return;
		}
	}
	public void hourValidation(String hour, int index) {
		try {
			if (hour.trim().equals(null) || hour.trim().isEmpty()
					|| hour.trim().equals(" ")) {
				dailyActivityList.get(index).setHourFlag(false);
				dailyActivityList.get(index).setTempHour("");
				dailyActivityList.get(index).setActivityHours(null);
				addErrorMessage(null, MessageId.REQUIRED, "Hour");
				calculateHours();
				return;
			} else if (Double.parseDouble(hour) > 24
					|| Double.parseDouble(hour) < 0.1) {
				DecimalFormat df = new DecimalFormat("0.0##");
				dailyActivityList.get(index).setTempHour(
						df.format(Double.parseDouble(hour)));
				dailyActivityList.get(index).setHourFlag(false);
				dailyActivityList.get(index).setActivityHours(
						Double.parseDouble(hour));
				calculateHours();
				addErrorMessage(null, MessageId.MS0003, "Hour", "0.1 to 24");
				return;
			} else {
				DecimalFormat df = new DecimalFormat("0.0##");
				dailyActivityList.get(index).setTempHour(
						df.format(Double.parseDouble(hour)));
				dailyActivityList.get(index).setHourFlag(true);
				dailyActivityList.get(index).setActivityHours(
						Double.parseDouble(hour));
				calculateHours();
			}
		} catch (NumberFormatException e) {
			dailyActivityList.get(index).setHourFlag(false);
			addErrorMessage(null, MessageId.MS0004, "Hour");
			dailyActivityList.get(index).setActivityHours(0.0);
			calculateHours();
		}
	}

	/**
	 * Revised By : Ye Thu Naing Revised Date : 2017/06/30 Updated For : Phase
	 * II Explanation : Customized validation method for "Progress".
	 */
	public void progressValidation(String progress, int index) {
		try {
			if (progress.trim().equals(null) || progress.trim().isEmpty()
					|| progress.trim().equals(" ")) {
				dailyActivityList.get(index).setProgressFlag(true);
				dailyActivityList.get(index).setTempProgress("");
				dailyActivityList.get(index).setProgressPercentage(null);
				return;
			} else if (Double.parseDouble(progress) > 100
					|| Double.parseDouble(progress) <= 0) {
				DecimalFormat df = new DecimalFormat("0.0##");
				dailyActivityList.get(index).setTempProgress(
						df.format(Double.parseDouble(progress)));
				dailyActivityList.get(index).setProgressFlag(false);
				addErrorMessage(null, MessageId.MS0003, "Progress", "1 to 100");
				return;
			} else {
				DecimalFormat df = new DecimalFormat("0.0##");
				dailyActivityList.get(index).setTempProgress(
						df.format(Double.parseDouble(progress)));
				dailyActivityList.get(index).setProgressPercentage(
						Double.parseDouble(progress));
				dailyActivityList.get(index).setProgressFlag(true);
			}
		} catch (NumberFormatException e) {
			dailyActivityList.get(index).setProgressFlag(false);
			addErrorMessage(null, MessageId.MS0004, "Progress");
		}
	}

	/**
	 * Revised By : Ye Thu Naing Revised Date : 2017/06/30 Updated For : Phase
	 * II Explanation : Customized validation method for "Task Status Count".
	 */
	public void countValidation(String count, int index) {
		try {
			if (count.trim().equals(null) || count.trim().isEmpty()
					|| count.trim().equals(" ")) {
				dailyActivityList.get(index).setCountFlag(false);
				addErrorMessage(null, MessageId.REQUIRED, "Task Status Count");
				dailyActivityList.get(index).setTempTaskStatusCount("");
				dailyActivityList.get(index).setTaskStatusCount(null);
				return;
			} else if (Double.parseDouble(count) <= 0) {
				DecimalFormat df = new DecimalFormat("0.0##");
				dailyActivityList.get(index).setTempTaskStatusCount(
						df.format(Double.parseDouble(count)));
				dailyActivityList.get(index).setCountFlag(false);
				addErrorMessage(null, MessageId.MS0003, "Task Status Count",
						"greater than 0");
			} else if (Double.parseDouble(count) > 0.0) {
				DecimalFormat df = new DecimalFormat("0.0##");
				dailyActivityList.get(index).setTempTaskStatusCount(
						df.format(Double.parseDouble(count)));
				dailyActivityList.get(index).setCountFlag(true);
				dailyActivityList.get(index).setTaskStatusCount(
						Double.parseDouble(count));
			}
		} catch (NumberFormatException e) {
			dailyActivityList.get(index).setCountFlag(false);
			addErrorMessage(null, MessageId.MS0004, "Task Status Count");
		}
	}

	/**
	 * Revised By : Ye Thu Naing Revised Date : 2017/06/30 Updated For : Phase
	 * II Explanation : Copying the selected row and add row into
	 * "Daily Progress List".
	 */
	public void copyActivity(DailyActivity dailyActivity) {
		if (dailyActivity != null) {
			DailyActivity currentDailyActivity = new DailyActivity();
			currentDailyActivity.setProject(dailyActivity.getProject());
			currentDailyActivity.setWbs(dailyActivity.getWbs());
			currentDailyActivity.setWbsFunction(dailyActivity.getWbsFunction());
			currentDailyActivity.setTask(dailyActivity.getTask());
			currentDailyActivity.setTaskActivity(dailyActivity
					.getTaskActivity());
			currentDailyActivity.setTaskDescription(dailyActivity
					.getTaskDescription());
			currentDailyActivity.setDeliveryOutput(dailyActivity
					.getDeliveryOutput());
			currentDailyActivity.setBeginDate(dailyActivity.getBeginDate());
			currentDailyActivity.setEndDate(dailyActivity.getEndDate());

			currentDailyActivity.setTempProgress(dailyActivity
					.getTempProgress());
			try {
				currentDailyActivity.setTempHour(dailyActivity.getTempHour());
				if (dailyActivity.getTempHour().trim() != null
						&& !dailyActivity.getTempHour().trim().isEmpty()) {
					currentDailyActivity.setActivityHours(Double
							.parseDouble(dailyActivity.getTempHour()));
				}
				currentDailyActivity.setTaskStatus(dailyActivity
						.getTaskStatus());
				currentDailyActivity.setTempTaskStatusCount(dailyActivity
						.getTempTaskStatusCount());
				if (dailyActivity.getTempTaskStatusCount().trim() != null
						&& !dailyActivity.getTempTaskStatusCount().trim()
								.isEmpty()) {
					currentDailyActivity
							.setTaskStatusCount(Double
									.parseDouble(dailyActivity
											.getTempTaskStatusCount()));
				}
				if (dailyActivity.getTempProgress().trim() != null
						&& !dailyActivity.getTempProgress().trim().isEmpty()) {
					currentDailyActivity.setProgressPercentage(Double
							.parseDouble(dailyActivity.getTempProgress()));
				}

			} catch (NullPointerException e) {
				calculateHours();
			} catch (NumberFormatException e) {
				calculateHours();
			}
			currentDailyActivity.setReason(dailyActivity.getReason());
			currentDailyActivity.setTempId(dailyActivityList.get(
					dailyActivityList.size() - 1).getTempId() + 1);
			currentDailyActivity.setVersion(dailyActivity.getVersion());
			currentDailyActivity.setDailyReport(dailyActivity.getDailyReport());
			dailyActivityList.add(currentDailyActivity);
			calculateHours();
		} else {
			addErrorMessage(null, MessageId.MS0005);
		}
	}

	/**
	 * Revised By : Ye Thu Naing Revised Date : 2017/06/30 Updated For : Phase
	 * II Explanation : Customized Validation method before saving process is
	 * carried out.
	 */
	public boolean saveValidation(DailyActivity activity) {
		if (activity.getBeginDate() != null && activity.getEndDate() != null) {
			if (!isValidFromAndToDate(activity.getBeginDate(),
					activity.getEndDate())) {
				addErrorMessage(null, MessageId.MS0006);
				return false;
			}
		}
		if (activity.getTaskDescription() == null
				|| activity.getTaskDescription().trim().isEmpty()) {
			addErrorMessage(null, MessageId.REQUIRED, "Description");
			return false;
		}
		if (activity.getActivityHours() == null) {
			addErrorMessage(null, MessageId.REQUIRED, "Hour");
			return false;
		}
		if (!activity.getTempHour().trim().isEmpty()) {
			try {
				if (Double.parseDouble(activity.getTempHour()) < 0.1
						|| Double.parseDouble(activity.getTempHour()) > 24) {
					addErrorMessage(null, MessageId.MS0003, "Hour", "0.1 to 24");
					return false;
				}
			} catch (NumberFormatException e) {
				addErrorMessage(null, MessageId.MS0004, "Hour");
				return false;
			}
		}
		if (activity.getActivityHours() < 0.1
				|| activity.getActivityHours() > 24) {
			addErrorMessage(null, MessageId.MS0003, "Hour", "0.1 to 24");
			return false;
		}

		if (totalHours > 24 || totalHours < 0) {
			addErrorMessage(null, MessageId.MS0003, "Total Hours", "0 and 24");
			return false;
		}
		if (!activity.getTempProgress().trim().isEmpty()) {
			try {
				if (Double.parseDouble(activity.getTempProgress()) > 100
						|| Double.parseDouble(activity.getTempProgress()) <= 0) {
					addErrorMessage(null, MessageId.MS0003, "Progress",
							"1 to 100");
					return false;
				}
			} catch (NumberFormatException e) {
				addErrorMessage(null, MessageId.MS0004, "Progress");
				return false;
			}
		}
		if (activity.getTaskStatus() != null) {
			if (activity.getTempTaskStatusCount().trim().equals(null)
					|| activity.getTempTaskStatusCount().trim().isEmpty()) {
				addErrorMessage(null, MessageId.REQUIRED, "Task Status Count");
				return false;
			}
			try {
				if (Double.parseDouble(activity.getTempTaskStatusCount()) <= 0.0) {
					addErrorMessage(null, MessageId.MS0004, "Task Status Count");
					return false;
				}
			} catch (NumberFormatException e) {
				addErrorMessage(null, MessageId.MS0004, "Task Status Count");
				return false;
			}
		}
		return true;
	}

	/**
	 * Revised By : Ye Thu Naing Revised Date : 2017/07/10 Updated For : Phase
	 * II Explanation : Customized Validation method for BeginDate and EndDate.
	 */
	public boolean isValidFromAndToDate(Date beginDate, Date endDate) {
		if ((beginDate.equals(endDate) || beginDate.before(endDate))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Revised By : Ye Thu Naing Revised Date : 2017/07/10 Updated For : Phase
	 * II Explanation : Customized method for clearing status value.
	 */
	public void resetTaskStatusCount(String status, int index) {
		if (status.trim().isEmpty()) {
			dailyActivityList.get(index).setTempTaskStatusCount("");
			dailyActivityList.get(index).setTaskStatusCount(null);
		}
	}

	/**
	 * Revised By : Ye Thu Naing Revised Date : 2017/07/21 Updated For : Phase
	 * II Explanation : Copy method for daily activity records by date.
	 */
	public void duplicate(Date date) {
		if (date == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"There is no record to duplicate."));
		} else {
			List<DailyReport> result = null;
			User u = userService.findUser(staffId);
			result = dailyReportService.findByDate(date, u);
			if (!result.isEmpty()) {
				DailyReport dr = new DailyReport();
				dr = result.get(0);
				if (dr.getLeaveStatus() == LeaveStatus.FULL_LEAVE) {
					this.setLeaveStatus(1);
					checkLeaveStatus();
					return;
				}
				if (dr.getLeaveStatus() == LeaveStatus.HALF_LEAVE) {
					this.setLeaveStatus(2);
					checkLeaveStatus();
				}
				for (DailyActivity dailyActivity : dailyActivityService
						.findActivityByReportID(result.get(0))) {
					if (dailyActivity.getActivityHours() != null) {
						dailyActivity.setTempHour(dailyActivity
								.getActivityHours().toString());
					}
					if (dailyActivity.getProgressPercentage() != null) {
						dailyActivity.setTempProgress(dailyActivity
								.getProgressPercentage().toString());
					}
					if (dailyActivity.getTaskStatusCount() != null) {
						dailyActivity.setTempTaskStatusCount(dailyActivity
								.getTaskStatusCount().toString());
					}

					if (dr.getLeaveStatus() == LeaveStatus.HALF_LEAVE) {
						if (dailyActivity.getTask().getId() != DailyActivityMBUtil
								.getLeavetaskid()) {
							this.dailyActivityList.add(dailyActivity);
						}
					} else {
						/**
						 * Revised By Sai Kyaw Ye Myint on 2017/08/29.
						 * Explanation : Remove review for duplicated activity
						 */
						dailyActivity.setReview(null);
						
						if (dailyActivity.getProject() != null) {
							for (Project project : projects) {
								if (project.getId().equals(dailyActivity.getProject().getId())) {
									dailyActivity.setProject(project);
									break;
								}
							}
						}
						this.dailyActivityList.add(dailyActivity);
					}
				}
				calculateHours();
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"There is no record to duplicate."));
				calculateHours();
			}
		}
	}
	
	/**
	 * Revised By : Ye Phyo Ko
	 * Revised Date : 2018/01/18 
	 * Explanation: Modify copy activity function.
	 */
	public void searchActivitiesToCopy(Date date) {
		
		if (date == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"There is no record to duplicate."));
		} else {
			
			List<DailyReport> result = null;
			User u = userService.findUser(staffId);
			result = dailyReportService.findByDate(date, u);
			this.activitesListToCopy = new ArrayList<DailyActivity>();
			
			if (!result.isEmpty()) {
				
				for (DailyActivity dailyActivity : dailyActivityService.findActivityByReportID(result.get(0))) {
						this.activitesListToCopy.add(dailyActivity);
				}
				
				RequestContext.getCurrentInstance().execute("PF('dlg4').show();");
				
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"There is no record to duplicate."));
			}
		}
	}
	
	/**
	 * Revised By : Ye Phyo Ko
	 * Revised Date : 2018/01/18 
	 * Explanation: Modify copy activity function.
	 */
	public void copySelectActivities() {
		RequestContext context = RequestContext.getCurrentInstance();
			if (!selectedActivities.isEmpty()) {
				for (DailyActivity dailyActivity : selectedActivities) {
					
					if (dailyActivity.getActivityHours() != null) {
						dailyActivity.setTempHour(dailyActivity
								.getActivityHours().toString());
					}
					
					if (dailyActivity.getProgressPercentage() != null) {
						dailyActivity.setTempProgress(dailyActivity
								.getProgressPercentage().toString());
					}
					
					if (dailyActivity.getTaskStatusCount() != null) {
						dailyActivity.setTempTaskStatusCount(dailyActivity
								.getTaskStatusCount().toString());
					}
					
					if(dailyActivity.getTaskDescription().equals("Full Leave")){
						/*Get Confirm For Full Leave */
						context.execute("PF('copyConfirmDlg').show();");
					}else if (dailyActivity.getTaskDescription().equals("Half Leave")) {
						this.setLeaveStatus(2);
						checkLeaveStatus();
					} else {
						dailyActivity.setReview(null);
						if (dailyActivity.getProject() != null) {
							for (Project project : projects) {
								if (project.getId().equals(dailyActivity.getProject().getId())) {
									dailyActivity.setProject(project);
									break;
								}
							}
						}
						this.dailyActivityList.add(dailyActivity);
					}
				}
				this.selectedActivities = new ArrayList<DailyActivity>();
				calculateHours();
				
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"There is no record to Copy."));
				calculateHours();
			}
	}
	
	public void copyFullLeave(){
				this.setLeaveStatus(1);
				checkLeaveStatus();
				RequestContext.getCurrentInstance().execute("PF('copyConfirmDlg').hide();");
	}

	/** Revised By Ye Thu Naing on 2017/07/21 and 2017/07/24. */
	public Date getDuplicateDate() {
		return duplicateDate;
	}

	public void setDuplicateDate(Date duplicateDate) {
		this.duplicateDate = duplicateDate;
	}

	public Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/** Revised By Ye Thu Naing on 2017/07/21 and 2017/07/24. */

	/** Revised By Sai Kyaw Ye Myint on 2017/07/07. */
	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	/** Revised By Sai Kyaw Ye Myint on 2017/07/07. */

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}
	
	
	public List<DailyActivity> getActivitesListToCopy() {
		return activitesListToCopy;
	}

	public void setActivitesListToCopy(List<DailyActivity> activitesListToCopy) {
		this.activitesListToCopy = activitesListToCopy;
	}

	public List<DailyActivity> getSelectedActivities() {
		return selectedActivities;
	}

	public void setSelectedActivities(List<DailyActivity> selectedActivities) {
		this.selectedActivities = selectedActivities;
	}

	/** Revised By Sai Kyaw Ye Myint on 2018/04/27. */
	public String getSuggestMessage() {
		return suggestMessage;
	}

	public void setSuggestMessage(String suggestMessage) {
		this.suggestMessage = suggestMessage;
	}

	public Date getOldDate() {
		return oldDate;
	}

	public void setOldDate(Date oldDate) {
		this.oldDate = oldDate;
	}

	public IUserTeamMainlyBelongHistoryService getMainlyBelongTeamHistoryService() {
		return mainlyBelongTeamHistoryService;
	}

	public void setMainlyBelongTeamHistoryService(
			IUserTeamMainlyBelongHistoryService mainlyBelongTeamHistoryService) {
		this.mainlyBelongTeamHistoryService = mainlyBelongTeamHistoryService;
	}

	public ISummaryActivityService getSummaryActivityService() {
		return summaryActivityService;
	}

	public void setSummaryActivityService(
			ISummaryActivityService summaryActivityService) {
		this.summaryActivityService = summaryActivityService;
	}
	public IDailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public void setDailyActivityService(
			IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public ITaskActivityService getTaskActivityService() {
		return taskActivityService;
	}

	public void setTaskActivityService(ITaskActivityService taskActivityService) {
		this.taskActivityService = taskActivityService;
	}

	public IDailyReportService getDailyReportService() {
		return dailyReportService;
	}

	public void setDailyReportService(IDailyReportService dailyReportService) {
		this.dailyReportService = dailyReportService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public List<DailyActivity> getDailyActivityList() {
		return dailyActivityList;
	}

	public void setDailyActivityList(List<DailyActivity> dailyActivityList) {
		this.dailyActivityList = dailyActivityList;
	}

	public DailyActivity getDailyActivity() {
		return dailyActivity;
	}

	public void setDailyActivity(DailyActivity dailyActivity) {
		this.dailyActivity = dailyActivity;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<TaskActivity> getTaskActivities() {
		return taskActivities;
	}

	public void setTaskActivities(List<TaskActivity> taskActivities) {
		this.taskActivities = taskActivities;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public int getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(int leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public int getSaveBtnDisabled() {
		return saveBtnDisabled;
	}

	public void setSaveBtnDisabled(int saveBtnDisabled) {
		this.saveBtnDisabled = saveBtnDisabled;
	}
	public int getNameDisabled() {
		return nameDisabled;
	}

	public void setNameDisabled(int nameDisabled) {
		this.nameDisabled = nameDisabled;
	}

	public boolean isStaffFlag() {
		return staffFlag;
	}

	public void setStaffFlag(boolean staffFlag) {
		this.staffFlag = staffFlag;
	}

	public boolean isAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(boolean adminFlag) {
		this.adminFlag = adminFlag;
	}

	public String getLeaveDescription() {
		return leaveDescription;
	}

	public void setLeaveDescription(String leaveDescription) {
		this.leaveDescription = leaveDescription;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DailyReport getDailyReport() {
		return dailyReport;
	}

	public void setDailyReport(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
	}


	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getBackBtnDisabled() {
		return backBtnDisabled;
	}

	public void setBackBtnDisabled(int backBtnDisabled) {
		this.backBtnDisabled = backBtnDisabled;
	}

	public List<DailyActivity> getDailyActivityListByReportID() {
		return dailyActivityListByReportID;
	}

	public void setDailyActivityListByReportID(
			List<DailyActivity> dailyActivityListByReportID) {
		this.dailyActivityListByReportID = dailyActivityListByReportID;
	}

	public double getTotalHours() {
		return totalHours;
	}

	public TaskStatus[] getTaskStatuses() {
		return TaskStatus.values();
	}

	public Date getToday() {
		return today;
	}

	public List<DailyActivity> getAllDailyActivity() {
		return dailyActivityList;
	}

}