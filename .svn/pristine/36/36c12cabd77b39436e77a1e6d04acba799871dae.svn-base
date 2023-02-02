/***************************************************************************************
 * @Date 2017-03-16
 * @author eimon
 * @Purpose <<For Monthly Report Features>>
 *
 *
 ***************************************************************************************/

package com.dat.whm.web.monthlyreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.exception.SystemException;
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.service.interfaces.IProjectService;

@ManagedBean(name = "MonthlyReportManageBean")
@ViewScoped
public class MonthlyReportManageBean {

	@ManagedProperty(value = "#{ProjectService}")
	private IProjectService projectService;
	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;

	private List<Project> projects;
	private List<String> months;
	private List<Integer> years;
	private Project project;
	private String selectedMonth;
	private int selectedYear;

	private List<MonthlyReportTableData> tableDataList;
	private String fileName;
	private boolean tableOne;
	private boolean tableTwo;

	static int monthInt = 0;
	static int startYear = 2016;
	Date today;
	SimpleDateFormat dateFormat;

	private String projectName;
	List<Object[]> result;

	@PostConstruct
	public void init() {
		// System.out.println("init...");
		load();
	}

	public void load() {
		projects = projectService.findAllProject();
		months = new ArrayList<String>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");

		today = new Date();
		dateFormat = new SimpleDateFormat("yyyyMdd-hhmmss");
		fileName = "MonthlyWorkingHours_" + dateFormat.format(today);

		years = new ArrayList<Integer>();
		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);
		// System.out.println("Current Year:"+year);
		// year += 10;
		for (int i = startYear; i <= currentYear; i++) {
			years.add(i);
		}
		// System.out.println("ArrayList:"+years.size());
		setTableOne(true);
		setProjectName("--------");
	}

	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public IDailyReportService getDailyReportService() {
		return dailyReportService;
	}

	public void setDailyReportService(IDailyReportService dailyReportService) {
		this.dailyReportService = dailyReportService;
	}

	public IDailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public void setDailyActivityService(IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<String> getMonths() {
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public int getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(int selectedYear) {
		this.selectedYear = selectedYear;
	}

	public List<MonthlyReportTableData> getTableDataList() {
		return tableDataList;
	}

	public void setTableDataList(List<MonthlyReportTableData> tableDataList) {
		this.tableDataList = tableDataList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isTableOne() {
		return tableOne;
	}

	public void setTableOne(boolean tableOne) {
		this.tableOne = tableOne;
	}

	public boolean isTableTwo() {
		return tableTwo;
	}

	public void setTableTwo(boolean tableTwo) {
		this.tableTwo = tableTwo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void show() {
		boolean projectBlank = false;
		boolean monthBlank = false;
		boolean yearBlank = false;

		if (project == null) {
			projectBlank = true;
		}
		if (selectedMonth.equals("")) {
			monthBlank = true;
		}
		if (selectedYear == 0) {
			yearBlank = true;
		}

		tableDataList = new ArrayList<MonthlyReportTableData>();

		setTableOne(true);
		setTableTwo(false);

		fileName = "MonthlyWorkingHours_" + dateFormat.format(today);

		if (projectBlank && monthBlank && yearBlank) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Please select at least one critiria for Project information reporting !"));
		} else if (yearBlank && !monthBlank) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"If you want to search by month, please select both month and year."));
		} else if (projectBlank && (!yearBlank && !monthBlank)) {
			// System.out.println("Selected Month:" + selectedMonth);
			// System.out.println("Selected Year:" + selectedYear);
			caseSix();
		} else if ((projectBlank && monthBlank) && (!yearBlank)) {
			// System.out.println("Selected Year:" + selectedYear);
			setTableOne(false);
			setTableTwo(true);
			// caseFiveV2();
			fileName = selectedYear + "_" + fileName;
		} else if (monthBlank && (!projectBlank && !yearBlank)) {
			// System.out.println("Selected Project:" + project);
			// System.out.println("Selected Year:" + selectedYear);
			caseFour();
		} else if (yearBlank && (!projectBlank && !monthBlank)) {
			// System.out.println("Selected Project:" + project);
			// System.out.println("Selected Month:" + selectedMonth);
			caseThree();
		} else if ((yearBlank && monthBlank) && (!projectBlank)) {
			// System.out.println("Selected Project:" + project);
			caseTwo();
		} else {
			// System.out.println("Selected Project:" + project);
			// System.out.println("Selected Month:" + selectedMonth);
			// System.out.println("Selected Year:" + selectedYear);
			caseOne();
		}
	}

	public void caseOne() throws SystemException {
		// Criteria: All fields
		// System.out.println("MNR case1..");
		selectMonth(selectedMonth);
		double hours = 0;
		MonthlyReportTableData tableData = new MonthlyReportTableData();
		tableData.setYear("" + selectedYear);
		tableData.setMonth(selectedMonth);
		tableData.setProject(project);
		List<Object[]> result = dailyReportService.findByMonthYearProj(monthInt, selectedYear,project.getId());

		if (!result.isEmpty()) {
			for (int i = 0; i < result.size(); i++)
			hours = (double) result.get(i)[1];
			hours = Math.round(hours*100.0)/100.0;
		}
		tableData.setHours(hours);
		tableDataList.add(tableData);
	}

	public void caseTwo() throws SystemException {
		// Critera:Only Project
		// System.out.println("MNR case2..");
		for (int y : years) {
			for (String m : months) {
				MonthlyReportTableData tableData = new MonthlyReportTableData();
				tableData.setYear("" + y);
				tableData.setMonth(m);
				double hours = 0;
				selectMonth(m);
				List<Object[]> result = dailyReportService.findByMonthYearProj(monthInt, y, project.getId());
				if (!result.isEmpty()) {
					for (int i = 0; i < result.size(); i++)
					hours = (double) result.get(i)[1];
					hours = Math.round(hours*100.0)/100.0;
				}
				tableData.setHours(hours);
				tableData.setProject(project);
				tableDataList.add(tableData);
			}
		}
	}

	public void caseThree() throws SystemException {
		// Critera:Project and Month
		// System.out.println("MNR case3..");
		for (int y : years) {
			MonthlyReportTableData tableData = new MonthlyReportTableData();
			tableData.setYear("" + y);
			tableData.setMonth(selectedMonth);
			int hours = 0;
			selectMonth(selectedMonth);
			List<DailyReport> dailyReportList = dailyReportService.findByMonthYear(monthInt, y);
			// System.out.println("Report Size:" + dailyReportList.size());
			for (DailyReport d : dailyReportList) {
				List<DailyActivity> dailyActivityList = dailyActivityService.findActivityByReportID(d);
				// System.out.println("Activity Size:" +
				// dailyActivityList.size());
				for (DailyActivity da : dailyActivityList) {
					if (da.getProject() != null) {
						if (da.getProject().getId().equals(project.getId())) {
							hours += da.getActivityHours();
						}
					}
				}
			}
			tableData.setHours(hours);
			tableData.setProject(project);
			tableDataList.add(tableData);

		}
	}

	public void caseFour() throws SystemException {
		// Critera:Project and Year
		// System.out.println("MNR case4..");
		for (String m : months) {
			MonthlyReportTableData tableData = new MonthlyReportTableData();
			tableData.setYear("" + selectedYear);
			tableData.setMonth(m);
			double hours = 0;
			selectMonth(m);
			List<Object[]> result = dailyReportService.findByMonthYearProj(monthInt, selectedYear, project.getId());
			if (!result.isEmpty()) {
				for (int i = 0; i < result.size(); i++)
				hours = (double) result.get(i)[1];
				hours = Math.round(hours*100.0)/100.0;
			}
			tableData.setHours(hours);
			tableData.setProject(project);
			tableDataList.add(tableData);
		}

	}

	public double caseFive(String m) throws SystemException {
		// Critera:Only Year
		// System.out.println("MNR case5..");
		selectMonth(m);
		double hours = 0;

		if (!result.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				if (monthInt == (int) result.get(i)[0]) {
					hours = (double) result.get(i)[1];
				}
			}
		}

		// System.out.println("Report Size:" + dailyReportList.size());
		/*
		 * for (DailyReport d : dailyReportList) { List<DailyActivity>
		 * dailyActivityList = dailyActivityService.findActivityByReportID(d);
		 * for (DailyActivity da : dailyActivityList) { if (da.getProject() !=
		 * null) { if (da.getProject().getId().equals(p.getId())) { hours +=
		 * da.getActivityHours(); } } } }
		 */
		hours = Math.round(hours*100.0)/100.0;
		return hours;
	}

	public String getProjectByYear(Project project) {
		result = dailyReportService.findByYearProj(selectedYear, project.getId());
		return project.getId();

	}

	public String getProjectName(Project project) {
		return project.getProjectName();
	}

	public void caseFiveV2() throws SystemException {
		// Critera:Only Year
		// System.out.println("MNR case5 Ver2..");
		for (String m : months) {
			selectMonth(m);
			List<DailyReport> dailyReportList = dailyReportService.findByMonthYear(monthInt, selectedYear);
			// System.out.println("Report Size:" + dailyReportList.size());
			for (Project p : projects) {
				MonthlyReportTableData tableData = new MonthlyReportTableData();
				tableData.setYear("" + selectedYear);
				tableData.setMonth(m);
				int hours = 0;
				for (DailyReport d : dailyReportList) {
					List<DailyActivity> dailyActivityList = dailyActivityService.findActivityByReportID(d);
					// System.out.println("Activity Size:" +
					// dailyActivityList.size());
					for (DailyActivity da : dailyActivityList) {
						if (da.getProject() != null) {
							if (da.getProject().getId().equals(p.getId())) {
								hours += da.getActivityHours();
							}
						}
					}
				}
				tableData.setProject(p);
				tableData.setHours(hours);
				tableDataList.add(tableData);
			}
		}
	}

	public void caseSix() throws SystemException {
		// Critera:Month and Year
		// System.out.println("MNR case6..");
		selectMonth(selectedMonth);

		for (Project p : projects) {
			MonthlyReportTableData tableData = new MonthlyReportTableData();
			tableData.setYear("" + selectedYear);
			tableData.setMonth(selectedMonth);
			double hours = 0;
			List<Object[]> result = dailyReportService.findByMonthYearProj(monthInt, selectedYear, p.getId());
			if (!result.isEmpty()) {
				for (int i = 0; i < result.size(); i++)
				hours = (double) result.get(i)[1];
				hours = Math.round(hours*100.0)/100.0;
			}
			tableData.setProject(p);
			tableData.setHours(hours);
			tableDataList.add(tableData);
		}
	}

	public void changeProject() {
		if (project == null) {
			setProjectName("--------");
		} else {
			setProjectName(project.getProjectName());
			// System.out.println("Current..Project:" + project.getId());
		}
	}

	public void selectMonth(String selectedMonth) {
		if (!selectedMonth.equals("")) {
			switch (selectedMonth) {
			case "January":
				monthInt = 1;
				break;
			case "February":
				monthInt = 2;
				break;
			case "March":
				monthInt = 3;
				break;
			case "April":
				monthInt = 4;
				break;
			case "May":
				monthInt = 5;
				break;
			case "June":
				monthInt = 6;
				break;
			case "July":
				monthInt = 7;
				break;
			case "August":
				monthInt = 8;
				break;
			case "September":
				monthInt = 9;
				break;
			case "October":
				monthInt = 10;
				break;
			case "November":
				monthInt = 11;
				break;
			case "December":
				monthInt = 12;
				break;
			}
		}
	}
}