/***************************************************************************************
 * @author Kyi Saw Win,Ye Thu Naing
 * @Date 2017-09-25
 * @Version 1.0
 * @Purpose Created for Phase III.
 *
 ***************************************************************************************/
package com.dat.whm.web.workinghourmanagement;

import java.util.List;

public class WorkingHourManagementViewData {
	String summary;
	String mainCategory;
	String category;
	List<Double> dataList;
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getMainCategory() {
		return mainCategory;
	}
	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<Double> getDataList() {
		return dataList;
	}
	public void setDataList(List<Double> dataList) {
		this.dataList = dataList;
	}
	
}
