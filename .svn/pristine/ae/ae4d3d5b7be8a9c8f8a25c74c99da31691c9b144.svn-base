/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-29
 * @Version 1.0
 * @Purpose  Created for Phase III.
 *
 ***************************************************************************************/
package com.dat.whm.web.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.common.entity.ProjectStatusDiv;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.holidays.service.interfaces.IHolidaysService;
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.service.interfaces.IProjectService;
import com.dat.whm.projectinformation.service.interfaces.IProjectInformationService;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.SearchDate;
import com.dat.whm.web.common.WhmUtilities;

@ManagedBean(name = "ProjectSummaryManageBean")
@ViewScoped
public class ProjectSummaryManageBean extends BaseBean  {
	
	@ManagedProperty(value = "#{ProjectService}")
	private IProjectService projectService;
	@ManagedProperty(value = "#{ProjectInformationService}")
	private IProjectInformationService projectInformationService;
	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;
	@ManagedProperty(value = "#{HolidaysService}")
	private IHolidaysService holidaysService;
	
	private List<Project> projectIDList;
	private Project projectSearchData;
	private List<Project> projectResultList;
	private String projcetId;
	private String projcetName;
	private String projcetManager;
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		projectIDList = projectService.findAllProject();
		projectSearchData = new Project();
		projectResultList = new ArrayList<>();
		if (!FacesContext.getCurrentInstance().getExternalContext().getFlash().isEmpty()) {
			boolean isBackFlag = (boolean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag");
			if (isBackFlag) {
				
				HashMap<String, String> projectSerachData = new HashMap<>();
				projectSerachData = (HashMap<String, String>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("projectSerachData");
				projcetId = projectSerachData.get("projcetId");
				projcetName = projectSerachData.get("projcetName");
				projcetManager = projectSerachData.get("projcetManager");
				
				searchProjects();
			}
		}
	}
	
	public void searchProjects(){
		/* Clear table data first. */
			projectResultList.clear();
			/*All Search*/
		if (projcetId == "" && projcetName == "" && projcetManager == "") {
			this.projectResultList.addAll(projectIDList);
		}else {
			/*Criteria Search*/
			for (Project project : projectIDList) {
				/**
				 * Revised By   : Sai Kyaw Ye Myint  
				 * Revised Date : 2018/01/18 
				 * Explanation  : Modified, to avoid null pointer exception.
				 */ 
				String tmpProjectId = project.getId() == null ? "" : project.getId();
				String tmpProjectName = project.getProjectName() == null ? "" : project.getProjectName();
				String tmpProjectManager = project.getProjectManager() == null ? "" : project.getProjectManager();
				
				if (projcetId == "") {
					if (tmpProjectName.contains(projcetName) && tmpProjectManager.contains(projcetManager))
						{
							projectResultList.add(project);
						}
				}else {
					if (tmpProjectId.equals(projcetId) && tmpProjectName.contains(projcetName) && tmpProjectManager.contains(projcetManager)) {
						projectResultList.add(project);
					}
				}
				
			}
		}
		if (projectResultList.size() !=0) {
			/*Calculate Progress for each project*/
			for (Project project : projectResultList) {
				if (project.getStartDate() != null && project.getEndDate() != null ) {
					if (isValidFromAndToDate(project.getStartDate(), project.getEndDate())) {
						calculateDeadline(project);
					}
				}
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "There is no search result."));
		}
		
	}
	
	public boolean isValidFromAndToDate(Date fromDate, Date toDate) {

		if ((fromDate.equals(toDate) || fromDate.before(toDate))) {
			return true;
		} else {
			return false;
		}

	}
	
	private void calculateDeadline(Project project){
		Date actualStartDate = dailyActivityService.findMinDateByProjectID(project.getId());
		Date actualEndDate = dailyActivityService.findMaxDateByProjectID(project.getId());
		if (actualEndDate == null || actualStartDate == null) {
			return;
		}
		
		SearchDate searchDate = new SearchDate();
		searchDate.setFromDate(project.getStartDate());
		searchDate.setToDate(project.getEndDate());
		int holidays = holidaysService.countByYearMonth(searchDate);
		int expectedWorkingDay = WhmUtilities.getWorkingDaysBetweenTwoDates(project.getStartDate(), project.getEndDate(), holidays);
		
		searchDate = new SearchDate();
		searchDate.setFromDate(actualStartDate);
		searchDate.setToDate(actualEndDate);
		holidays = holidaysService.countByYearMonth(searchDate);
		double actualWorkingDay =  WhmUtilities.getWorkingDaysBetweenTwoDates(actualStartDate, actualEndDate, holidays);
		
		double percentage = ( actualWorkingDay/expectedWorkingDay ) * 100;
		
		ProjectDeadlineStatus deadline= new ProjectDeadlineStatus();
		deadline.setPercentage((int) percentage);
		deadline.setActLabel((int) percentage);
		deadline.setExpLabel(100);
		if(percentage > 100){
			double diff = actualWorkingDay - expectedWorkingDay;
			while (diff > expectedWorkingDay) {
				diff = diff - expectedWorkingDay;
			}
			double exceedPercentage = (diff/expectedWorkingDay) * 100;
			deadline.setPercentage((int) exceedPercentage );
			
			int hundredRank = ( 100 - (int) exceedPercentage);
			deadline.setExpLabel(deadline.getActLabel() + hundredRank);
		}

		if(actualWorkingDay < expectedWorkingDay ){
			deadline.setLate(false);
			
			if (project.getProjectStatusDiv() == ProjectStatusDiv.END) {
				deadline.setBgColor("blue");
			}else {
				deadline.setBgColor("white");
			}
			
		}else if(actualWorkingDay > expectedWorkingDay){
			deadline.setLate(true);
			
			if (project.getProjectStatusDiv() == ProjectStatusDiv.END) {
				deadline.setBgColor("red");
			}else {
				deadline.setBgColor("orange");
			}
			
		}else if (actualWorkingDay == expectedWorkingDay) {
			deadline.setLate(false);
			deadline.setPercentage((int) 100);
		}
		
		project.setDeadline(deadline);
	}
	
	public void reset(){
		projectIDList = projectService.findAllProject();
		projectSearchData = new Project();
		projectResultList = new ArrayList<>();
		projcetId = "";
		projcetName = "";
		projcetManager = "";
	}

	public void controlForm() {
		init();
	}
	
	public String dashboard(Project project){
		HashMap<String, String> projectSerachData = new HashMap<>();
		projectSerachData.put("projcetId", projcetId);
		projectSerachData.put("projcetName", projcetName);
		projectSerachData.put("projcetManager", projcetManager);
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectSerachData", projectSerachData);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("project", project);
		return "/faces/view/project/PRJ003.xhtml?faces-redirect=true";
	}
	
	public String projectsummaryEdit(Project project){
		HashMap<String, String> projectSerachData = new HashMap<>();
		projectSerachData.put("projcetId", projcetId);
		projectSerachData.put("projcetName", projcetName);
		projectSerachData.put("projcetManager", projcetManager);
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("project", project);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectSerachData", projectSerachData);
		
		return "/faces/view/project/PRJ001.xhtml?faces-redirect=true";
	}
	
	public void projectEnd(Project project){
		User loginUser= (User) getParam(Constants.LOGIN_USER);
		
		project.setProjectStatusDiv(ProjectStatusDiv.END);
		Date now = new Date();
		project.setLastUpdBy(loginUser);
		project.setLastUpdDate(now);
		
		projectService.updateProject(project);
		
		projectIDList = projectService.findAllProject();
		searchProjects();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Project is successfully endded."));
	}
	
	/**
	 * Revised By   : Aung Htay Oo
	 * Revised Date : 2017/11/24
	 * Explanation  : For display error message.	 
	 */
	public void projectsummaryDelete(Project project){
		User loginUser= (User) getParam(Constants.LOGIN_USER);
		Date now = new Date();
		project.setLastUpdBy(loginUser);
		project.setLastUpdDate(now);
		project.setDelDiv(DeleteDiv.DELETE);
		projectService.updateProject(project);
		
		projectIDList = projectService.findAllProject();
		if (projectResultList.size() > 1) {
			searchProjects();
		}else {
			projectResultList.clear();
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Successfully Deleted!!"));
	}

	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public List<Project> getProjectIDList() {
		return projectIDList;
	}

	public void setProjectIDList(List<Project> projectIDList) {
		this.projectIDList = projectIDList;
	}

	public Project getProjectSearchData() {
		return projectSearchData;
	}

	public void setProjectSearchData(Project projectSearchData) {
		this.projectSearchData = projectSearchData;
	}

	public List<Project> getProjectResultList() {
		return projectResultList;
	}

	public void setProjectResultList(List<Project> projectResultList) {
		this.projectResultList = projectResultList;
	}

	public String getProjcetId() {
		return projcetId;
	}

	public void setProjcetId(String projcetId) {
		this.projcetId = projcetId;
	}

	public String getProjcetName() {
		return projcetName;
	}

	public void setProjcetName(String projcetName) {
		this.projcetName = projcetName;
	}

	public String getProjcetManager() {
		return projcetManager;
	}

	public void setProjcetManager(String projcetManager) {
		this.projcetManager = projcetManager;
	}

	public IProjectInformationService getProjectInformationService() {
		return projectInformationService;
	}

	public void setProjectInformationService(
			IProjectInformationService projectInformationService) {
		this.projectInformationService = projectInformationService;
	}

	public IDailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public void setDailyActivityService(IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public IHolidaysService getHolidaysService() {
		return holidaysService;
	}

	public void setHolidaysService(IHolidaysService holidaysService) {
		this.holidaysService = holidaysService;
	}
	
}
