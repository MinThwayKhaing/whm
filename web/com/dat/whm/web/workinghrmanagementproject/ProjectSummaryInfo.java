package com.dat.whm.web.workinghrmanagementproject;

import java.util.List;



public class ProjectSummaryInfo {

	private String projectID;
	private String projectName;
	private String customer;
	private double totalPlanMM;
	private double totalActualMM;
	private List<TeamInfo> teamInfo;
	private List<TotalManMonthInfo> totalManMonthInfo;
	private int members;


	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public double getTotalPlanMM() {
		return totalPlanMM;
	}

	public void setTotalPlanMM(double totalPlanMM) {
		this.totalPlanMM = totalPlanMM;
	}

	public double getTotalActualMM() {
		return totalActualMM;
	}

	public void setTotalActualMM(double totalActualMM) {
		this.totalActualMM = totalActualMM;
	}

	public List<TeamInfo> getTeamInfo() {
		return teamInfo;
	}

	public void setTeamInfo(List<TeamInfo> teamInfo) {
		this.teamInfo = teamInfo;
	}

	public int getMembers() {
		return members;
	}

	public void setMembers(int members) {
		this.members = members;
	}

	public List<TotalManMonthInfo> getTotalManMonthInfo() {
		return totalManMonthInfo;
	}

	public void setTotalManMonthInfo(List<TotalManMonthInfo> totalManMonthInfo) {
		this.totalManMonthInfo = totalManMonthInfo;
	}

	
	
	

}
