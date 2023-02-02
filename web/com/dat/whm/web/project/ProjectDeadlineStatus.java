/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-29
 * @Version 1.0
 * @Purpose <<Deadline progress object for project information. >>
 *
 ***************************************************************************************/
package com.dat.whm.web.project;

public class ProjectDeadlineStatus {
	public boolean late;
	public int percentage;
	public String bgColor;
	public int actLabel;
	public int expLabel;
	
	public boolean isLate() {
		return late;
	}
	public void setLate(boolean late) {
		this.late = late;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	public int getActLabel() {
		return actLabel;
	}
	public void setActLabel(int actLabel) {
		this.actLabel = actLabel;
	}
	public int getExpLabel() {
		return expLabel;
	}
	public void setExpLabel(int expLabel) {
		this.expLabel = expLabel;
	}

}
