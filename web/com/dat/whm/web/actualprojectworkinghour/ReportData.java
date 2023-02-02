/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/12
	 * Explanation  : Get daily report data form daily_report table.
	 */
package com.dat.whm.web.actualprojectworkinghour;

import java.util.Date;

import com.dat.whm.common.entity.TaskStatus;
import com.dat.whm.task.entity.Task;
import com.dat.whm.taskactivity.entity.TaskActivity;

public class ReportData {

	private Date date;
	private String projectID;
	private String projectName;
	private String staffID;
	private String staffName;
	private String wbsNo;
	private String functionID;
	private Task category;
	private TaskActivity activities;
	private String description;
	private String deliveryOutput;
	private Date beginDate;
	private Date endDate;
	private Double progress;
	private double hour;
	private TaskStatus status;
	private Double taskStatusCount;
	private String reason;
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the projectID
	 */
	public String getProjectID() {
		return projectID;
	}
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the staffID
	 */
	public String getStaffID() {
		return staffID;
	}
	/**
	 * @param staffID the staffID to set
	 */
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	/**
	 * @return the staffName
	 */
	public String getStaffName() {
		return staffName;
	}
	/**
	 * @param staffName the staffName to set
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	/**
	 * @return the wbsNo
	 */
	public String getWbsNo() {
		return wbsNo;
	}
	/**
	 * @param wbsNo the wbsNo to set
	 */
	public void setWbsNo(String wbsNo) {
		this.wbsNo = wbsNo;
	}
	/**
	 * @return the functionID
	 */
	public String getFunctionID() {
		return functionID;
	}
	/**
	 * @param functionID the functionID to set
	 */
	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}
	/**
	 * @return the category
	 */
	public Task getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Task category) {
		this.category = category;
	}
	
	/**
	 * @return the activities
	 */
	public TaskActivity getActivities() {
		return activities;
	}
	/**
	 * @param activities the activities to set
	 */
	public void setActivities(TaskActivity activities) {
		this.activities = activities;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the deliveryOutput
	 */
	public String getDeliveryOutput() {
		return deliveryOutput;
	}
	/**
	 * @param deliveryOutput the deliveryOutput to set
	 */
	public void setDeliveryOutput(String deliveryOutput) {
		this.deliveryOutput = deliveryOutput;
	}
	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the progress
	 */
	public Double getProgress() {
		return progress;
	}
	/**
	 * @param progress the progress to set
	 */
	public void setProgress(Double progress) {
		this.progress = progress;
	}
	
	/**
	 * @return the hour
	 */
	public double getHour() {
		return hour;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(double hour) {
		this.hour = hour;
	}
	/**
	 * @return the status
	 */
	public TaskStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	/**
	 * @return the taskStatusCount
	 */
	public Double getTaskStatusCount() {
		return taskStatusCount;
	}
	/**
	 * @param taskStatusCount the taskStatusCount to set
	 */
	public void setTaskStatusCount(Double taskStatusCount) {
		this.taskStatusCount = taskStatusCount;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
}
