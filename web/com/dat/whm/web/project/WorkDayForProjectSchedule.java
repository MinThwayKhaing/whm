/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-21
 * @Version 1.0
 * @Purpose  Created for Phase III.
 *
 ***************************************************************************************/
package com.dat.whm.web.project;

public class WorkDayForProjectSchedule {
	private int	daysPerWeek;
	private boolean working;
	
	public int getDaysPerWeek() {
		return daysPerWeek;
	}
	public void setDaysPerWeek(int daysPerWeek) {
		this.daysPerWeek = daysPerWeek;
	}
	public boolean isWorking() {
		return working;
	}
	public void setWorking(boolean working) {
		this.working = working;
	}
	
	
}
