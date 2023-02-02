/***************************************************************************************
 * @author Kyi Saw Win
 * @Date 2014-09-25
 * @Version 1.0
 * @Purpose Created for Phase III.
 *
 ***************************************************************************************/
package com.dat.whm.web.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.holidays.service.interfaces.IHolidaysService;
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.service.interfaces.IProjectService;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.Properties;
import com.dat.whm.web.common.SearchDate;
import com.dat.whm.web.common.WhmUtilities;

@ManagedBean(name = "ProjectDashboardManageBean")
@ViewScoped
public class ProjectDashboardManageBean extends BaseBean {
	
	@ManagedProperty(value = "#{ProjectService}")
	private IProjectService projectService;
	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;
	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{HolidaysService}")
	private IHolidaysService holidaysService;
	
	private Project project;
	private List<DailyReport> dailyReportList;
	private List<DailyActivity> dailyActivityList;
	
	private HashMap<String, String> projectSerachData;
	
	private DashboardModel projectDashboardModel;
	
	private Date expectedStartDate;
	private Date actualStartDate;
	private int diffStartDate;
	
	private Date expectedEndDate;
	private Date actualEndDate;
	private int diffEndDate;
	
	private double expectedManMonth;
	private double actualManMonth;
	private double diffManMonth;
	
	private double expectedHRCost;
	private double actualHRCost;
	private double diffHRCost;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		/* Dashboard Preparation. */
		projectDashboardModel = new DefaultDashboardModel();
		DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        
        column1.addWidget("projectSummary");
        column2.addWidget("projectReview");
        
        projectDashboardModel.addColumn(column1);
        projectDashboardModel.addColumn(column2);
        
        /* Project Summary Preparation. */
		project = new Project();
		project = (Project) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("project");
		projectSerachData = new HashMap<>();
		projectSerachData = (HashMap<String, String>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("projectSerachData");
		
		/**
		 * Revised By   : Sai Kyaw Ye Myint 
		 * Revised Date : 2017/10/10
		 * Explanation  : Revised for null pointer exception.
		 */
		/* Project Review Calculation. */
		if (project.getStartDate() != null && project.getEndDate() != null) {
			calculateStartDate();
			calculateEndDate();
			calculateManMonth();
			calculateHRCost();
		}
	}
	
	private void calculateStartDate(){
		
		SearchDate searchDate = new SearchDate();
		int holiday;
		
		expectedStartDate = project.getStartDate();
		actualStartDate = dailyActivityService.findMinDateByProjectID(project.getId());
		searchDate = new SearchDate();
		
		/**
		 * Revised By   : Sai Kyaw Ye Myint 
		 * Revised Date : 2017/10/10
		 * Explanation  : Revised for null pointer exception.
		 */
		if (actualStartDate != null) {
			if (expectedStartDate.equals(actualStartDate)) {
				diffStartDate = 0;
				return;
			}else if (expectedStartDate.before(actualStartDate)) {
				searchDate.setFromDate(expectedStartDate);
				searchDate.setToDate(actualStartDate);
			}else {
				searchDate.setFromDate(actualStartDate);
				searchDate.setToDate(expectedStartDate);
			}
			
			holiday = holidaysService.countByYearMonth(searchDate);
			
			diffStartDate = WhmUtilities.getWorkingDaysBetweenTwoDates(expectedStartDate, actualStartDate, holiday) -1;
			if (actualStartDate.after(expectedStartDate)) {
				diffStartDate = diffStartDate - (diffStartDate * 2);
			}
		}
	}
	
	private void calculateEndDate() {
		SearchDate searchDate = new SearchDate();
		int holiday;
		
		expectedEndDate = project.getEndDate();
		actualEndDate = dailyActivityService.findMaxDateByProjectID(project.getId());
		searchDate = new SearchDate();
		
		/**
		 * Revised By   : Sai Kyaw Ye Myint 
		 * Revised Date : 2017/10/10
		 * Explanation  : Revised for null pointer exception.
		 */
		if (actualEndDate != null) {
			if (expectedEndDate.equals(actualEndDate)) {
				diffEndDate = 0;
				return;
			}else if (expectedEndDate.before(actualEndDate)) {
				searchDate.setFromDate(expectedEndDate);
				searchDate.setToDate(actualEndDate);
			}else {
				searchDate.setFromDate(actualEndDate);
				searchDate.setToDate(expectedEndDate);
			}
			holiday = holidaysService.countByYearMonth(searchDate);
			
			diffEndDate = WhmUtilities.getWorkingDaysBetweenTwoDates(expectedEndDate, actualEndDate, holiday) -1;
			
			if (actualEndDate.after(expectedEndDate)) {
				diffEndDate = diffEndDate - (diffEndDate * 2);
			}
		}
	}
	
	private void calculateManMonth(){
		
		double totalHr = 0.0;
		int actualWorkingDay = 0;
		
		expectedManMonth = WhmUtilities.round(project.getPrjPeroid(), 2);
		
		totalHr = dailyActivityService.findSumOfActivityHrByProjectID(project.getId());
		actualWorkingDay = dailyActivityService.countActivityDateByProjectID(project.getId());
		actualManMonth = WhmUtilities.round(totalHr/actualWorkingDay, 2);
		
		diffManMonth =  WhmUtilities.round(expectedManMonth - actualManMonth, 2);
	}
	
	private void calculateHRCost(){
		double definedHRCost = Double.parseDouble(Properties.getProperty(Constants.APP_SETTING, Constants.HR_COST));
		expectedHRCost = expectedManMonth * definedHRCost;
		actualHRCost = actualManMonth * definedHRCost;
		diffHRCost = expectedHRCost - actualHRCost;
		
	}
	
	public String projectInformationDetail(){
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectSerachData", projectSerachData);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("project", project);
		return "/faces/view/project/PRJ002.xhtml?faces-redirect=true";
	}
	
	public String back(){
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("project", project);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectSerachData", projectSerachData);
		return "/faces/view/mgtreports/MNR004.xhtml?faces-redirect=true";
	}
	
	/***************************************************************************************
	 * The following are getter and setter method of each class variable.
	 ***************************************************************************************/
	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public DashboardModel getProjectDashboardModel() {
		return projectDashboardModel;
	}

	public void setProjectDashboardModel(DashboardModel projectDashboardModel) {
		this.projectDashboardModel = projectDashboardModel;
	}

	public IDailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public void setDailyActivityService(IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public IDailyReportService getDailyReportService() {
		return dailyReportService;
	}

	public void setDailyReportService(IDailyReportService dailyReportService) {
		this.dailyReportService = dailyReportService;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public int getDiffStartDate() {
		return diffStartDate;
	}

	public void setDiffStartDate(int diffStartDate) {
		this.diffStartDate = diffStartDate;
	}

	public List<DailyReport> getDailyReportList() {
		return dailyReportList;
	}

	public void setDailyReportList(List<DailyReport> dailyReportList) {
		this.dailyReportList = dailyReportList;
	}

	public List<DailyActivity> getDailyActivityList() {
		return dailyActivityList;
	}

	public void setDailyActivityList(List<DailyActivity> dailyActivityList) {
		this.dailyActivityList = dailyActivityList;
	}

	public IHolidaysService getHolidaysService() {
		return holidaysService;
	}

	public void setHolidaysService(IHolidaysService holidaysService) {
		this.holidaysService = holidaysService;
	}

	public Date getExpectedStartDate() {
		return expectedStartDate;
	}

	public void setExpectedStartDate(Date expectedStartDate) {
		this.expectedStartDate = expectedStartDate;
	}

	public Date getExpectedEndDate() {
		return expectedEndDate;
	}

	public void setExpectedEndDate(Date expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public int getDiffEndDate() {
		return diffEndDate;
	}

	public void setDiffEndDate(int diffEndDate) {
		this.diffEndDate = diffEndDate;
	}

	public double getExpectedManMonth() {
		return expectedManMonth;
	}

	public void setExpectedManMonth(double expectedManMonth) {
		this.expectedManMonth = expectedManMonth;
	}

	public double getActualManMonth() {
		return actualManMonth;
	}

	public void setActualManMonth(double actualManMonth) {
		this.actualManMonth = actualManMonth;
	}

	public double getDiffManMonth() {
		return diffManMonth;
	}

	public void setDiffManMonth(double diffManMonth) {
		this.diffManMonth = diffManMonth;
	}

	public double getExpectedHRCost() {
		return expectedHRCost;
	}

	public void setExpectedHRCost(double expectedHRCost) {
		this.expectedHRCost = expectedHRCost;
	}

	public double getActualHRCost() {
		return actualHRCost;
	}

	public void setActualHRCost(double actualHRCost) {
		this.actualHRCost = actualHRCost;
	}

	public double getDiffHRCost() {
		return diffHRCost;
	}

	public void setDiffHRCost(double diffHRCost) {
		this.diffHRCost = diffHRCost;
	}
	
}
