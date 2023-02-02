package com.dat.whm.web.common;
public class DateHeader {

	private int dayNames;
	private String weekDays;
	private boolean holidays_flag;

	public int getDayNames() {
		return dayNames;
	}

	public void setDayNames(int dayNames) {
		this.dayNames = dayNames;
	}

	public String getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public boolean isHolidays_flag() {
		return holidays_flag;
	}

	public void setHolidays_flag(boolean holidays_flag) {
		this.holidays_flag = holidays_flag;
	}
	
}
