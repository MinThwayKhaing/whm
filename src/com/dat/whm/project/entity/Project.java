/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.project.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.common.entity.ProjectStatusDiv;
import com.dat.whm.communicationplan.entity.CommunicationPlan;
import com.dat.whm.contactperson.entity.ContactPerson;
import com.dat.whm.projectcontrol.entity.ProjectControl;
import com.dat.whm.projectdeliverable.entity.ProjectDeliverable;
import com.dat.whm.projectschedule.entity.ProjectSchedule;
import com.dat.whm.projectvolume.entity.ProjectVolume;
import com.dat.whm.riskfactors.entity.RiskFactors;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.project.ProjectDeadlineStatus;

@Table(name = "PROJECT")
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p WHERE p.delDiv <> com.dat.whm.common.entity.DeleteDiv.DELETE"),
		@NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.projectName = :projectName"),
		@NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id"),
		
		/**
		 * Revised By   : Ye Thu Naing
		 * Revised Date : 2017/09/19
		 * Explanation  : Get year,month form project table.
		 */
		@NamedQuery(name = "Project.findStartDateByProjectId", query = "SELECT p.startDate FROM Project p WHERE p.id = :id"),
		
		@NamedQuery(name = "Project.deleteProjectById", query = "UPDATE Project p SET p.delDiv = com.dat.whm.common.entity.DeleteDiv.DELETE WHERE p.id = :project_id"),
		@NamedQuery(name = "Project.findClientOrganization", query = "SELECT DISTINCT p.clientOrganization FROM Project p WHERE p.clientOrganization IS NOT NULL ORDER BY p.clientOrganization ASC")})

public class Project implements Serializable {

	@Id
	@Column(name = "PROJECT_ID")
	private String id;
	
	@Column(name = "PROJECT_NAME")
	@NotNull
	private String projectName;

	/**
	 * Revised By : Sai Kyaw Ye Myint  
	 * Revised Date : 2017/09/13 
	 * Explanation: Added projectBackground, purpose, objectives,
	 * 				duration, startDate, endDate, budget, currency,
	 * 				projectSummary,  projectManager, clientOrganization,
	 * 				clientIncharge, clientDept, spOrganization, spIncharge,
	 *  			spDept, operatingSystem, appRuntime, webServer, appServer,
	 *  			appFramework, dbServer, clientAgent, pgmLanguage, libraries,
	 *  			others, prjPeroid, totalManMonth, createdDate, lastUpdBy, lastUpdDate,
	 *  			delDiv, contactPersonList, projectVolumeList, projectScheduleList, 
	 *  			communicationPlanList, projectControlList, projectDeliverablesList,
	 *  			riskFactorList, workSManMonth, reviewSManMonth, totalSManMonth, workSManDay,
	 *  			reviewSManDay, totalSManDay, totalRManMonth, totalRManDay, totalTManMonth, totalTManDay and deadline for project information.
	 */
	@Column(name = "PROJECT_BACKGROUND")
	private String projectBackground;
	
	@Column(name = "PURPOSE")
	private String purpose;
	
	@Column(name = "OBJECTIVES")
	private String objectives;
	
	@Column(name = "DURATION")
	private Double duration;
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Column(name = "BUDGET")
	private Double budget;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "PROJECT_SUMMARY")
	private String projectSummary;
	
	@Column(name = "PROJECT_MANAGER")
	private String projectManager;
	
	@Column(name = "CLIENT_ORGANIZATION")
	private String clientOrganization;
	
	@Column(name = "CLIENT_INCHARGE")
	private String clientIncharge;
	
	@Column(name = "CLIENT_DEPT")
	private String clientDept;
	
	@Column(name = "SP_ORGANIZATION")
	private String spOrganization;
	
	@Column(name = "SP_INCHARGE")
	private String spIncharge;
	
	@Column(name = "SP_DEPT")
	private String spDept;
	
	@Column(name = "OPERATING_SYSTEM")
	private String operatingSystem;
	
	@Column(name = "APP_RUN_TIME")
	private String appRuntime;
	
	@Column(name = "WEB_SERVER")
	private String webServer;
	
	@Column(name = "APP_SERVER")
	private String appServer;
	
	@Column(name = "APPLICATION_FRAMEWORK")
	private String appFramework;
	
	@Column(name = "DB_SERVER")
	private String dbServer;
	
	@Column(name = "CLIENT_AGENT")
	private String clientAgent;
	
	@Column(name = "PGM_LANGUAGE")
	private String pgmLanguage;
	
	@Column(name = "LIBRARIES")
	private String libraries;
	
	@Column(name = "OTHERS")
	private String others;
	
	@Column(name = "PROJECT_PERIOD")
	private double prjPeroid;
	
	@Column(name = "TOTAL_MAN_MONTH")
	private double totalManMonth;
	
	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date createdDate;
	
	@JoinColumn(name = "LAST_UPD_BY")
	private User lastUpdBy;
	
	@Column(name = "LAST_UPD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdDate;
	
	@Column(name = "DEL_DIV")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private DeleteDiv delDiv;
	
	@Version
	private int version;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@OrderBy("orderNo ASC")
	private List<ContactPerson> contactPersonList;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@OrderBy("orderNo ASC")
	private List<ProjectVolume> projectVolumeList;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@OrderBy("orderNo ASC")
	private List<ProjectSchedule> projectScheduleList;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@OrderBy("orderNo ASC")
	private List<CommunicationPlan> communicationPlanList;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@OrderBy("orderNo ASC")
	private List<ProjectControl> projectControlList;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@OrderBy("orderNo ASC")
	private List<ProjectDeliverable> projectDeliverablesList;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@OrderBy("orderNo ASC")
	private List<RiskFactors> riskFactorList;
	
	@Column(name = "PROJECT_STATUS")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private ProjectStatusDiv projectStatusDiv;
	
	@Transient
	private double workSManMonth;
	@Transient
	private double reviewSManMonth;
	@Transient
	private double totalSManMonth;
	@Transient
	private double workSManDay;
	@Transient
	private double reviewSManDay;
	@Transient
	private double totalSManDay;
	@Transient
	private double totalRManMonth;
	@Transient
	private double totalRManDay;
	@Transient
	private double totalTManMonth;
	@Transient
	private double totalTManDay;
	@Transient
	private ProjectDeadlineStatus deadline;
	
	//@Column(name = "PLAN_MAN_MONTH")
	//private Double planManMonth;
	//@Column(name = "SCHEDULE")
	//private Double schedule;

	public Project() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public DeleteDiv getDelDiv() {
		return delDiv;
	}

	public void setDelDiv(DeleteDiv delDiv) {
		this.delDiv = delDiv;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	/*public Double getPlanManMonth() {
		return planManMonth;
	}

	public void setPlanManMonth(Double planManMonth) {
		this.planManMonth = planManMonth;
	}

	public Double getSchedule() {
		return schedule;
	}

	public void setSchedule(Double schedule) {
		this.schedule = schedule;
	}*/

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	public String getProjectBackground() {
		return projectBackground;
	}

	public void setProjectBackground(String projectBackground) {
		this.projectBackground = projectBackground;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getObjectives() {
		return objectives;
	}

	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getProjectSummary() {
		return projectSummary;
	}

	public void setProjectSummary(String projectSummary) {
		this.projectSummary = projectSummary;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getClientOrganization() {
		return clientOrganization;
	}

	public void setClientOrganization(String clientOrganization) {
		this.clientOrganization = clientOrganization;
	}

	public String getClientIncharge() {
		return clientIncharge;
	}

	public void setClientIncharge(String clientIncharge) {
		this.clientIncharge = clientIncharge;
	}

	public String getClientDept() {
		return clientDept;
	}

	public void setClientDept(String clientDept) {
		this.clientDept = clientDept;
	}

	public String getSpOrganization() {
		return spOrganization;
	}

	public void setSpOrganization(String spOrganization) {
		this.spOrganization = spOrganization;
	}

	public String getSpIncharge() {
		return spIncharge;
	}

	public void setSpIncharge(String spIncharge) {
		this.spIncharge = spIncharge;
	}

	public String getSpDept() {
		return spDept;
	}

	public void setSpDept(String spDept) {
		this.spDept = spDept;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getAppRuntime() {
		return appRuntime;
	}

	public void setAppRuntime(String appRuntime) {
		this.appRuntime = appRuntime;
	}

	public String getWebServer() {
		return webServer;
	}

	public void setWebServer(String webServer) {
		this.webServer = webServer;
	}

	public String getAppServer() {
		return appServer;
	}

	public void setAppServer(String appServer) {
		this.appServer = appServer;
	}

	public String getAppFramework() {
		return appFramework;
	}

	public void setAppFramework(String appFramework) {
		this.appFramework = appFramework;
	}

	public String getDbServer() {
		return dbServer;
	}

	public void setDbServer(String dbServer) {
		this.dbServer = dbServer;
	}

	public String getClientAgent() {
		return clientAgent;
	}

	public void setClientAgent(String clientAgent) {
		this.clientAgent = clientAgent;
	}

	public String getPgmLanguage() {
		return pgmLanguage;
	}

	public void setPgmLanguage(String pgmLanguage) {
		this.pgmLanguage = pgmLanguage;
	}

	public String getLibraries() {
		return libraries;
	}

	public void setLibraries(String libraries) {
		this.libraries = libraries;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public double getPrjPeroid() {
		return prjPeroid;
	}

	public void setPrjPeroid(double prjPeroid) {
		this.prjPeroid = prjPeroid;
	}

	public double getTotalManMonth() {
		return totalManMonth;
	}

	public void setTotalManMonth(double totalManMonth) {
		this.totalManMonth = totalManMonth;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(User lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public double getWorkSManMonth() {
		return workSManMonth;
	}

	public void setWorkSManMonth(double workSManMonth) {
		this.workSManMonth = workSManMonth;
	}

	public double getReviewSManMonth() {
		return reviewSManMonth;
	}

	public void setReviewSManMonth(double reviewSManMonth) {
		this.reviewSManMonth = reviewSManMonth;
	}

	public double getTotalSManMonth() {
		return totalSManMonth;
	}

	public void setTotalSManMonth(double totalSManMonth) {
		this.totalSManMonth = totalSManMonth;
	}

	public double getWorkSManDay() {
		return workSManDay;
	}

	public void setWorkSManDay(double workSManDay) {
		this.workSManDay = workSManDay;
	}

	public double getReviewSManDay() {
		return reviewSManDay;
	}

	public void setReviewSManDay(double reviewSManDay) {
		this.reviewSManDay = reviewSManDay;
	}

	public double getTotalSManDay() {
		return totalSManDay;
	}

	public void setTotalSManDay(double totalSManDay) {
		this.totalSManDay = totalSManDay;
	}

	public double getTotalRManMonth() {
		return totalRManMonth;
	}

	public void setTotalRManMonth(double totalRManMonth) {
		this.totalRManMonth = totalRManMonth;
	}

	public double getTotalRManDay() {
		return totalRManDay;
	}

	public void setTotalRManDay(double totalRManDay) {
		this.totalRManDay = totalRManDay;
	}

	public double getTotalTManMonth() {
		return totalTManMonth;
	}

	public void setTotalTManMonth(double totalTManMonth) {
		this.totalTManMonth = totalTManMonth;
	}

	public double getTotalTManDay() {
		return totalTManDay;
	}

	public void setTotalTManDay(double totalTManDay) {
		this.totalTManDay = totalTManDay;
	}

	public List<ContactPerson> getContactPersonList() {
		return contactPersonList;
	}

	public void setContactPersonList(List<ContactPerson> contactPersonList) {
		this.contactPersonList = contactPersonList;
	}

	public List<ProjectVolume> getProjectVolumeList() {
		return projectVolumeList;
	}

	public void setProjectVolumeList(List<ProjectVolume> projectVolumeList) {
		this.projectVolumeList = projectVolumeList;
	}

	public List<ProjectSchedule> getProjectScheduleList() {
		return projectScheduleList;
	}

	public void setProjectScheduleList(List<ProjectSchedule> projectScheduleList) {
		this.projectScheduleList = projectScheduleList;
	}

	public List<CommunicationPlan> getCommunicationPlanList() {
		return communicationPlanList;
	}

	public void setCommunicationPlanList(
			List<CommunicationPlan> communicationPlanList) {
		this.communicationPlanList = communicationPlanList;
	}

	public List<ProjectControl> getProjectControlList() {
		return projectControlList;
	}

	public void setProjectControlList(List<ProjectControl> projectControlList) {
		this.projectControlList = projectControlList;
	}

	public List<ProjectDeliverable> getProjectDeliverablesList() {
		return projectDeliverablesList;
	}

	public void setProjectDeliverablesList(
			List<ProjectDeliverable> projectDeliverablesList) {
		this.projectDeliverablesList = projectDeliverablesList;
	}

	public List<RiskFactors> getRiskFactorList() {
		return riskFactorList;
	}

	public void setRiskFactorList(List<RiskFactors> riskFactorList) {
		this.riskFactorList = riskFactorList;
	}

	public ProjectDeadlineStatus getDeadline() {
		return deadline;
	}

	public void setDeadline(ProjectDeadlineStatus deadline) {
		this.deadline = deadline;
	}

	public ProjectStatusDiv getProjectStatusDiv() {
		return projectStatusDiv;
	}

	public void setProjectStatusDiv(ProjectStatusDiv projectStatusDiv) {
		this.projectStatusDiv = projectStatusDiv;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
	    return getId();
	}
}