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

public class WorkingHourManagementTeamView {
	private String teamId;
	
	private int members;
	
	private List<ProjectDivision> projectDivision;
	
	private int totalRowCount;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public int getMembers() {
		return members;
	}

	public void setMembers(int members) {
		this.members = members;
	}

	public List<ProjectDivision> getProjectDivision() {
		return projectDivision;
	}

	public void setProjectDivision(List<ProjectDivision> projectDivision) {
		this.projectDivision = projectDivision;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}
	
}
