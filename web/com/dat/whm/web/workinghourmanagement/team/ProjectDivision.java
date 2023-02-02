/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2018-05-01
 * @Version 1.0
 * @Purpose <<Working Hour Management by Team (UI object for MNR007)>>
 *
 ***************************************************************************************/
package com.dat.whm.web.workinghourmanagement.team;

import java.util.List;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.summaryactivity.entity.SummaryActivity;

public class ProjectDivision {
	private String projectId;
	private String projectName;
	private List<YearMonthDivision> yearMonthDivision;
	private List<SummaryActivity> detailActivities;
	private List<StaffDivision> staffDivision;
	
	public String getProjectId() {
		return projectId;
	}
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public List<YearMonthDivision> getYearMonthDivision() {
		return yearMonthDivision;
	}
	
	public void setYearMonthDivision(List<YearMonthDivision> yearMonthDivision) {
		this.yearMonthDivision = yearMonthDivision;
	}

	public List<SummaryActivity> getDetailActivities() {
		return detailActivities;
	}

	public void setDetailActivities(List<SummaryActivity> detailActivities) {
		this.detailActivities = detailActivities;
	}

	public List<StaffDivision> getStaffDivision() {
		return staffDivision;
	}

	public void setStaffDivision(List<StaffDivision> staffDivision) {
		this.staffDivision = staffDivision;
	}
	
}
