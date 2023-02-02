/***************************************************************************************
 * @Date 2017-03-16
 * @author eimon
 * @Purpose <<For using entity for data table of MNR001>>
 *
 *
 ***************************************************************************************/

package com.dat.whm.web.monthlyreport;

import com.dat.whm.project.entity.Project;

public class MonthlyReportTableData {

	private String year;
	private String month;
	private Project project;
	private double hours;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}
}