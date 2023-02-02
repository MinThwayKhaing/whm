/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-07-07
 * @Version 1.0
 * @Purpose <<Intend for DAR004.xhtml an pojo object to list one day report and activities.>>
 *
 ***************************************************************************************/
package com.dat.whm.web.weeklyreport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.dat.whm.dailyreport.entity.DailyReport;

public class WeeklyData {
	private DailyReport dailyReport;
	private List<DailyActivityData> activities;
	private double totalHour;
	private String activityDate;
	private boolean selectRecord = false;
	private String username;
	private String fullname;
	private String leavestatus;
	private String description;
	private double workinghour;
	private int day_count;
	private int activities_count;
		

	
	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Add no argument constructor.
	 */
	public WeeklyData() {
		
	}
	
	public WeeklyData(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
		activities= new ArrayList<>();
		totalHour= 0.0;
		activityDate= null;
	}
	
    public static class Comparators {

        public static Comparator<WeeklyData> DATE = new Comparator<WeeklyData>() {
			@Override
			public int compare(WeeklyData obj1, WeeklyData objData2) {
				return obj1.getActivityDate().compareTo(objData2.getActivityDate());
			}
        };
    }
	
	public double getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(double totalHour) {
		this.totalHour += totalHour;
	}

	public DailyReport getDailyReport() {
		return dailyReport;
	}
	public void setDailyReport(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
	}
	public List<DailyActivityData> getActivities() {
		return activities;
	}
	public void setActivities(List<DailyActivityData> activities) {
		this.activities = activities;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}
	public boolean isSelectRecord() {
		return selectRecord;
	}
	public void setSelectRecord(boolean selectRecord) {
		this.selectRecord = selectRecord;
	}
	
	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Getter and setter for userName and fullname.
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public double getWorkinghour() {
		return workinghour;
	}

	public void setWorkinghour(double workinghour) {
		this.workinghour = workinghour;
	}

	public int getDay_count() {
		return day_count;
	}

	public void setDay_count(int day_count) {
		this.day_count = day_count;
	}

	public int getActivities_count() {
		return activities_count;
	}

	public void setActivities_count(int activities_count) {
		this.activities_count = activities_count;
	}

	public String getLeavestatus() {
		return leavestatus;
	}

	public void setLeavestatus(String leavestatus) {
		this.leavestatus = leavestatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	
}
