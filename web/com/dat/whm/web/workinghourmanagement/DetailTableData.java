package com.dat.whm.web.workinghourmanagement;

import java.util.List;

public class DetailTableData {
	private String colname;
	private List<String> summary;
	public DetailTableData(String colname,List<String> summary)
	{
		this.colname=colname;
		this.summary=summary;
	}
	public String getColname() {
		return colname;
	}
	public void setColname(String colname) {
		this.colname = colname;
	}
	public List<String> getTaskdata() {
		return summary;
	}
	public void setTaskdata(List<String> summary) {
		this.summary = summary;
	}
}
