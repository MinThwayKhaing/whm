/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.dailyactivity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.common.entity.BaseEntity;
import com.dat.whm.common.entity.TaskStatus;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.project.entity.Project;
import com.dat.whm.task.entity.Task;
import com.dat.whm.taskactivity.entity.TaskActivity;

/***************************************************************************************
 * Updated By   : Ye Thu Naing 
 * Updated Date : 2017/06/30
 * Updated For  : Phase II
 ***************************************************************************************/

@SuppressWarnings("serial")
@Table(name = "daily_activities")
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "DailyActivity.findActivityByReportID", query = "SELECT da FROM DailyActivity da WHERE da.dailyReport.id = :dailyReport_id"),
		@NamedQuery(name = "DailyActivity.deleteActivityByReportID", query = "DELETE FROM DailyActivity da WHERE da.dailyReport = :dailyReport_id"),
		@NamedQuery(name = "DailyActivity.deleteActivityByReportID", query = "DELETE FROM DailyActivity da WHERE da.dailyReport = :dailyReport_id"),
		@NamedQuery(name = "DailyActivity.findActivityDateByProjectID", query = "SELECT MAX(da.dailyReport.activityDate) FROM DailyActivity da WHERE da.project.id = :id"),
		@NamedQuery(name = "DailyActivity.findMinDateByProjectID", query = "SELECT MIN(da.dailyReport.activityDate) FROM DailyActivity da WHERE da.project.id = :id"),
		@NamedQuery(name = "DailyActivity.findMaxDateByProjectID", query = "SELECT MAX(da.dailyReport.activityDate) FROM DailyActivity da WHERE da.project.id = :id"),
		@NamedQuery(name = "DailyActivity.findSumOfActivityHrByProjectID", query = "SELECT SUM(da.activityHours) FROM DailyActivity da WHERE da.project.id = :id"),
		@NamedQuery(name = "DailyActivity.countActivityDateByProjectID", query = "SELECT COUNT(DISTINCT(da.dailyReport.activityDate)) FROM DailyActivity da WHERE da.project.id = :id")})
public class DailyActivity extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DAILY_REPORT_ID")
	@NotNull
	private DailyReport dailyReport;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project project;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASK_ID")
	@NotNull
	private Task task;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASK_ACTIVITY_ID")
	@NotNull
	private TaskActivity taskActivity;
	@Column(name = "WBS")
	private String wbs;
	@Column(name = "WBS_FUNCTION")
	private String wbsFunction;
	@Column(name = "TASK_DESCRIPTION")
	@NotNull
	private String taskDescription;
	@Column(name = "DELIVERY_OUTPUT")
	private String deliveryOutput;
	@Column(name = "ACTIVITY_HOURS")
	private Double activityHours;
	@Column(name = "PROGRESS_PERCENTAGE")
	private Double progressPercentage;

	@Column(name = "PLAN_PROGRESS_PERCENTAGE")
	private Double planProgress;
	@Column(name = "BEGIN_DATE")
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	@Column(name = "TASK_STATUS")
	@Enumerated(EnumType.ORDINAL)
	private TaskStatus taskStatus;
	@Column(name = "TASK_STATUS_COUNT")
	private Double taskStatusCount;
	@Column(name = "REASON")
	private String reason;
	@Transient
	private int tempId;
	@Version
	private int version;
	
	@Transient
	private Boolean isPlanActivity;
	@Transient
	private Double diffProgress;
	@Transient
	private Double diffActivityHours;
	@Transient
	private Boolean disableLink;
	@Transient
	private String tempPlanProgress;
	@Transient
	private String tempPlanHour;
	@Transient
	private String tempProgress;
	@Transient
	private String tempHour;
	@Transient
	private String tempTaskStatusCount;
	@Transient
	private boolean progressFlag=true;
	@Transient
	private boolean hourFlag=true;
	@Transient
	private boolean countFlag=true;
	@Column(name = "REVIEW")
	private String review;
	
	@Column(name = "PLAN_ACTIVITY_HOURS")
	private Double planHours;
	
	@Column(name = "PLAN_FROM_TIME")
	@Temporal(TemporalType.TIME)
	private Date planStartTime;
	
	@Column(name = "PLAN_TO_TIME")
	@Temporal(TemporalType.TIME)
	private Date planEndTime;
	
	
	public Date getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public Date getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}

	public String getTempProgress() {
		return tempProgress;
	}

	public void setTempProgress(String tempProgress) {
		this.tempProgress = tempProgress;
	}
	
	public String getTempHour() {
		return tempHour;
	}

	public void setTempHour(String tempHour) {
		this.tempHour = tempHour;
	}
	
	public String getTempTaskStatusCount() {
		return tempTaskStatusCount;
	}

	public void setTempTaskStatusCount(String tempTaskStatusCount) {
		this.tempTaskStatusCount = tempTaskStatusCount;
	}
	
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
	public boolean getProgressFlag() {
		return progressFlag;
	}

	public void setProgressFlag(boolean progressFlag) {
		this.progressFlag = progressFlag;
	}

	public boolean getHourFlag() {
		return hourFlag;
	}

	public void setHourFlag(boolean hourFlag) {
		this.hourFlag = hourFlag;
	}

	public boolean getCountFlag() {
		return countFlag;
	}

	public void setCountFlag(boolean countFlag) {
		this.countFlag = countFlag;
	}
	
	public DailyActivity() {
	}

	public DailyReport getDailyReport() {
		return dailyReport;
	}

	public void setDailyReport(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskActivity getTaskActivity() {
		return taskActivity;
	}

	public void setTaskActivity(TaskActivity taskActivity) {
		this.taskActivity = taskActivity;
	}

	public String getWbs() {
		return wbs;
	}

	public void setWbs(String wbs) {
		this.wbs = wbs;
	}

	public String getWbsFunction() {
		return wbsFunction;
	}

	public void setWbsFunction(String wbsFunction) {
		this.wbsFunction = wbsFunction;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getDeliveryOutput() {
		return deliveryOutput;
	}

	public void setDeliveryOutput(String deliveryOutput) {
		this.deliveryOutput = deliveryOutput;
	}

	public Double getActivityHours() {
		return activityHours;
	}

	public void setActivityHours(Double activityHours) {
		this.activityHours = activityHours;
	}

	public Double getProgressPercentage() {
		return progressPercentage;
	}

	public void setProgressPercentage(Double progressPercentage) {
		this.progressPercentage = progressPercentage;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Double getTaskStatusCount() {
		return taskStatusCount;
	}

	public void setTaskStatusCount(Double taskStatusCount) {
		this.taskStatusCount = taskStatusCount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getTempId() {
		return tempId;
	}

	public void setTempId(int tempId) {
		this.tempId = tempId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public Double getPlanHours() {
		return planHours;
	}

	public void setPlanHours(Double planHours) {
		this.planHours = planHours;
	}

	public Double getPlanProgress() {
		return planProgress;
	}

	public void setPlanProgress(Double planProgress) {
		this.planProgress = planProgress;
	}

	public Boolean getDisableLink() {
		return disableLink;
	}

	public void setDisableLink(Boolean disableLink) {
		this.disableLink = disableLink;
	}

	public String getTempPlanProgress() {
		return tempPlanProgress;
	}

	public void setTempPlanProgress(String tempPlanProgress) {
		this.tempPlanProgress = tempPlanProgress;
	}

	public String getTempPlanHour() {
		return tempPlanHour;
	}

	public void setTempPlanHour(String tempPlanHour) {
		this.tempPlanHour = tempPlanHour;
	}

	public Double getDiffActivityHours() {
		return diffActivityHours;
	}

	public void setDiffActivityHours(Double diffActivityHours) {
		this.diffActivityHours = diffActivityHours;
	}

	public Double getDiffProgress() {
		return diffProgress;
	}

	public void setDiffProgress(Double diffProgress) {
		this.diffProgress = diffProgress;
	}

	public Boolean getIsPlanActivity() {
		return isPlanActivity;
	}

	public void setIsPlanActivity(Boolean isPlanActivity) {
		this.isPlanActivity = isPlanActivity;
	}
	
	
}