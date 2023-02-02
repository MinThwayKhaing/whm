/***************************************************************************************
 * @author Kyi Saw Win,Ye Thu Naing
 * @Date 2014-09-08
 * @Version 1.0
 * @Purpose Created for Phase III.
 *
 ***************************************************************************************/
package com.dat.whm.web.actualprojectworkinghour;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.hpsf.HPSFException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;

import com.dat.whm.common.entity.CheckDiv;
import com.dat.whm.common.entity.LeaveStatus;
import com.dat.whm.common.entity.TaskStatus;
import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.persistence.interfaces.IDailyReportDAO;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.exception.SystemException;
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.service.interfaces.IProjectService;
import com.dat.whm.task.entity.Task;
import com.dat.whm.task.service.interfaces.ITaskService;
import com.dat.whm.taskactivity.entity.TaskActivity;
import com.dat.whm.taskactivity.service.interfaces.ITaskActivityService;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.MessageId;
import com.dat.whm.web.common.SearchDate;
import com.dat.whm.web.common.WhmUtilities;
import com.dat.whm.web.dailyactivity.DailyActivityMBUtil;

@ManagedBean(name = "ActualPrjWorkingHrRptManageBean")
@ViewScoped
public class ActualProjectWorkingHrReportManageBean extends BaseBean {

	@ManagedProperty(value = "#{ProjectService}")
	private IProjectService projectService;
	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{DailyReportDAOService}")
	private IDailyReportDAO dailyReportDAOService;
	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;
	@ManagedProperty(value = "#{TaskService}")
	private ITaskService taskService;
	@ManagedProperty(value = "#{TaskActivityService}")
	private ITaskActivityService taskActivityService;
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	private List<DailyReport> dailyReportList;
	private List<DailyActivity> dailyActivityList;
	private List<Project> projects;
	private List<Task> categories;
	private List<TaskActivity> taskActivities;
	private ArrayList<ReportData> reportDataList;
	private ArrayList<ReportData> monthlyRptDataList = new ArrayList<ReportData>();
	private ArrayList<TabViewData> tabViewDataList;
	private ArrayList<TabViewData> filteredDataList;
	
	/**
	 * Revised By   : Aung Htay Oo
	 * Revised Date : 2017/11/01
	 * Explanation  : Modified for serially sort month tabs in actual project working hour report.		 
	 */
	private LinkedHashMap<String, ArrayList<ReportData>> hashMap;
	private String year;
	private List<String> years;
	private String month;
	private List<String> months;
	private String day;
	private List<String> days;
	private DefaultStreamedContent download;
	private static Date today = new Date();
	SearchDate searchDate;
	Double totalHour;
	boolean btnExport;

	DailyReport dailyReport;
	DailyActivity dailyActivity;
	TaskStatus taskStatus;
	LeaveStatus leaveStatus;
	CheckDiv checkDiv;
	String downloadFileName;
	String exportFileName;
	/**
	 * Revised By	: Aye Myat Mon
	 * Revised Date	: 2018/08/20 
	 * Explanation	: Added WhmUtilities for Date Filter.
	 */
	private WhmUtilities whmUtilities;
	/* Initialization method. */
	@PostConstruct
	public void init() {
		today = new Date();
		projects = projectService.findAllProject();
		categories = taskService.findAllTask();
		taskActivities = taskActivityService.findAllTaskActivity();
		getActiveYear();
		prepareMonths();
		prepareDays();

		dailyActivity = new DailyActivity();
		dailyActivity.setProject(new Project());
		dailyActivity.setTask(new Task());
		dailyActivity.setTaskActivity(new TaskActivity());

		dailyReport = new DailyReport();
		dailyReport.setStaffID(new User());

		setTotalHour(0.0);
		searchDate = new SearchDate();
		setBtnExport(true);
		
		/**
		 * Revised By	: Aye Myat Mon
		 * Revised Date	: 2018/08/20 
		 * Explanation	: Added WhmUtilities for Date Filter.
		 */
		whmUtilities = new WhmUtilities();

	}

	/* Get year list from daily_report table. */
	public void getActiveYear() {
		years = new ArrayList<String>();
		years = dailyReportService.findActiveYears();
	}

	/* Prepare month list. */
	public void prepareMonths() {
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
	}

	/* Prepare day list. */
	public void prepareDays() {
		days = new ArrayList<String>();
		for (int d = 1; d <= 31; d++) {
			if (d < 10) {
				days.add("0" + Integer.toString(d));
			} else {
				days.add(Integer.toString(d));
			}

		}
	}

	/* Get staff name according to staff id. */
	public void changeUser() {
		if (!dailyReport.getStaffID().getUsername().equals("")) {
			try {
				String staffId = dailyReport.getStaffID().getUsername();
				User u = userService.findUser(staffId);
				if (u != null) {
					dailyReport.setStaffID(u);
				}else {
					dailyReport.getStaffID().setFullName("");
				}
			} catch (SystemException e) {
				handelSysException(e);
			}
		}
	}

	/**
	 * Revised By   : Aung Htay Oo
	 * Revised Date : 2017/11/09
	 * Explanation  : Modified for override object of 'ReportData'.
	 *****************************************************************************************
	 **/
		/* Search Report. */
	public void show() {
		double tmptotalHour = 0.0;
		if (checkReqItems()) {
			setBtnExport(false);
		} else {
			return;
		}

		reportDataList = new ArrayList<ReportData>();
		dailyActivityList = new ArrayList<DailyActivity>();
		hashMap = new LinkedHashMap<String, ArrayList<ReportData>>();
		tabViewDataList = new ArrayList<TabViewData>();

		/**
		 * Revised By	: Aung Htay Oo
		 * Revised Date	: 2017/11/21
		 * Explanation	: Add WhmUtilities.findMonthDigit() method for find months in digits.
		 **/
		
		dailyReportList = dailyReportService.findAllReportByCriteria(
				dailyReport, year, WhmUtilities.findMonthDigit(month), day, searchDate);
		if (dailyReportList.size() == 0) {
			String InfoMessage = "There is no search result";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							InfoMessage));
			/**
			 * Revised By	: Sai Kyaw Ye Myint
			 * Revised Date	: 2017/11/23
			 * Explanation	: Reset total hour to 0.0 when no result to show.
			 **/
			setTotalHour(0.0);
			return;
		}
		if (dailyReportList.size() > 0) {

			for (DailyReport dr : dailyReportList) {
				/**
				 * Revised By	: Sai Kyaw Ye Myint
				 * Revised Date	: 2017/11/23
				 * Explanation	: Revised for search half leave policy.
				 **/
				if (dailyReport.getLeaveStatus() == LeaveStatus.HALF_LEAVE) {
					dailyActivity.setTask(taskService.findTask(DailyActivityMBUtil.getLeavetaskid()));
				}
				dailyActivityList = dailyActivityService
						.findAllActivitiesByCriteria(dr, dailyActivity);
				if (dailyActivityList.size() > 0) {

					//ReportData rptData = new ReportData();

					for (DailyActivity da : dailyActivityList) {
	
						ReportData rptData = new ReportData();
						rptData.setDate(dr.getActivityDate());
						rptData.setStaffID(dr.getStaffID().getUsername());
						rptData.setStaffName(dr.getStaffID().getFullName());
						if (da.getProject() != null) {
							rptData.setProjectID(da.getProject().getId());
							rptData.setProjectName(da.getProject()
									.getProjectName());
						}
						rptData.setWbsNo(da.getWbs());
						rptData.setFunctionID(da.getWbsFunction());
						rptData.setCategory(da.getTask());
						rptData.setActivities(da.getTaskActivity());
						rptData.setDescription(da.getTaskDescription());
						rptData.setDeliveryOutput(da.getDeliveryOutput());
						if (da.getBeginDate() != null) {
							rptData.setBeginDate(da.getBeginDate());
						}
						if (da.getEndDate() != null) {
							rptData.setEndDate(da.getEndDate());
						}
						if (da.getProgressPercentage() == null) {
							rptData.setProgress(null);
						}
						if (da.getProgressPercentage() != null) {
							rptData.setProgress(da.getProgressPercentage());
						}
						if (da.getActivityHours() != null) {
							rptData.setHour(da.getActivityHours());
						}
						if (da.getTaskStatus() != null) {
							rptData.setStatus(da.getTaskStatus());
						}
						if (da.getTaskStatusCount() == null) {
							rptData.setTaskStatusCount(null);
						}
						if (da.getTaskStatusCount() != null) {
							rptData.setTaskStatusCount(da.getTaskStatusCount());
						}
						rptData.setReason(da.getReason());
						reportDataList.add(rptData);
					}
					//reportDataList.add(rptData);
				}
			}

			if (reportDataList.size() == 0) {
				String InfoMessage = "There is no search result";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								InfoMessage));
				/**
				 * Revised By	: Sai Kyaw Ye Myint
				 * Revised Date	: 2017/11/23
				 * Explanation	: Reset total hour to 0.0 when no result to show.
				 **/
				setTotalHour(0.0);
				return;
			}
			for (int i = 0; i < reportDataList.size(); i++) {
				tmptotalHour += reportDataList.get(i).getHour();
				tmptotalHour = WhmUtilities.round(tmptotalHour, 2);
			}
			Collections.sort(reportDataList, new Comparator<ReportData>() {
				@Override
				public int compare(ReportData tabDate1, ReportData tabDate2) {
					return tabDate1.getDate().compareTo(tabDate2.getDate());
				}
			});
			setTotalHour(tmptotalHour);
			String tmpTabName;
			Map<String, Object> map = new TreeMap<String, Object>();
			for (ReportData rptData : reportDataList) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(rptData.getDate());
				tmpTabName = cal.get(Calendar.YEAR) + "-"
						+ new SimpleDateFormat("MMM").format(cal.getTime());

				if (!hashMap.containsKey(tmpTabName)) {
					ArrayList<ReportData> tablist = new ArrayList<ReportData>();
					tablist.add(rptData);
					hashMap.put(tmpTabName, tablist);

				} else {

					hashMap.get(tmpTabName).add(rptData);
				}
			}

			for (Map.Entry<String, ArrayList<ReportData>> e : hashMap
					.entrySet()) {
				TabViewData tabViewData = new TabViewData();
				tabViewData.setTabName(e.getKey().toString());
				tabViewData.setRptDataList(e.getValue());

				tabViewDataList.add(tabViewData);
			}
		}

		/**
		 * Revised By   : Aung Htay Oo
		 * Revised Date : 2017/11/01
		 * Explanation  : Modified for serially sort month tabs by month in actual project working hour report.		 
		 */
		/*Collections.sort(tabViewDataList, new Comparator<TabViewData>() {
			@Override
			public int compare(TabViewData tabDate1, TabViewData tabDate2) {
				return tabDate1.getTabName().compareTo(tabDate2.getTabName());
			}
		});*/
	}

	/* Check mandatory value. */
	public boolean checkReqItems() {
		if (year.trim().equals("") || year.trim().equals(null)
				|| year.equals("-")) {
			addErrorMessage(null, MessageId.REQUIRED, "Year");
			return false;
		}
		return true;
	}

	/* Export excel file. */
	public void export() throws Exception {

		if (exportDailyReport()) {

			File file = new File(getDownloadFileName());
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] bfile = new byte[(int) file.length()];

			ExternalContext externalContext = FacesContext.getCurrentInstance()
					.getExternalContext();
			fileInputStream.read(bfile);
			fileInputStream.close();

			InputStream stream = new ByteArrayInputStream(bfile);
			setDownload(new DefaultStreamedContent(stream,
					externalContext.getMimeType(file.getName()),
					getExportFileName()));

		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data to export"));
		}
	}

	/* Set up excel file name and call exportdatatoexcel method. */
	public boolean exportDailyReport() throws FileNotFoundException,
			IOException, HPSFException {

		StringBuilder name = new StringBuilder();
		StringBuilder filename = new StringBuilder();
		;
		if (dailyActivity.getProject() != null) {
			filename = name.append("(DAT)_Actual Project Working Hour_")
					.append(dailyActivity.getProject().getId()).append(".xlsx");
		} else {
			filename = name.append("(DAT)_Actual Project Working Hour").append(
					".xlsx");
		}

		if (reportDataList.size() <= 0) {
			setDownloadFileName("");
			setExportFileName("");
			return false;
		} else {
			exportDatatoExcel(reportDataList, filename);
			return true;
		}
	}

	/* Export data to specified excel file */
	public void exportDatatoExcel(List<ReportData> reportdata,
			StringBuilder filename) throws FileNotFoundException, HPSFException {
		int startRow = 1;
		int startColumn = 0;
		XSSFWorkbook workbook;
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		ServletContext context = (ServletContext) externalContext.getContext();

		try {
			// Template Excel File
			File file = new File(
					context.getRealPath("/template/ActualProjectWorkingHourTemplate.xlsx"));
			FileInputStream fIP = new FileInputStream(file);
			workbook = new XSSFWorkbook(fIP);

			File outputFile = new File(context.getRealPath("/upload/"
					+ filename));
			FileOutputStream outputData = new FileOutputStream(outputFile);

			XSSFSheet sheetName = workbook.getSheet("Sheet1");
			XSSFCellStyle dateCellStyle = workbook.createCellStyle();
			short df = workbook.createDataFormat().getFormat("yyyy-mm-dd");

			for (int i = 0; i < reportdata.size(); i++) {

				Row row = sheetName.createRow(startRow + i);
				Cell datecell = row.createCell(startColumn);
				datecell.setCellValue(reportdata.get(i).getDate());
				dateCellStyle.setDataFormat(df);
				datecell.setCellStyle(dateCellStyle);

				Cell projectIDcell = row.createCell(startColumn + 1);
				projectIDcell.setCellValue(reportdata.get(i).getProjectID());

				Cell projectNamecell = row.createCell(startColumn + 2);
				projectNamecell
						.setCellValue(reportdata.get(i).getProjectName());

				Cell staffIDcell = row.createCell(startColumn + 3);
				staffIDcell.setCellValue(reportdata.get(i).getStaffID());

				Cell staffNamecell = row.createCell(startColumn + 4);
				staffNamecell.setCellValue(reportdata.get(i).getStaffName());

				Cell wbscell = row.createCell(startColumn + 5);
				wbscell.setCellValue(reportdata.get(i).getWbsNo());

				Cell functionIDcell = row.createCell(startColumn + 6);
				functionIDcell.setCellValue(reportdata.get(i).getFunctionID());

				Cell categorycell = row.createCell(startColumn + 7);
				categorycell.setCellValue(reportdata.get(i).getCategory()
						.getDescription());

				Cell activitycell = row.createCell(startColumn + 8);
				activitycell.setCellValue(reportdata.get(i).getActivities()
						.getDescription());

				Cell decriptioncell = row.createCell(startColumn + 9);
				decriptioncell.setCellValue(reportdata.get(i).getDescription());

				Cell deliverablesDoccell = row.createCell(startColumn + 10);
				deliverablesDoccell.setCellValue(reportdata.get(i)
						.getDeliveryOutput());

				Cell beginDatecell = row.createCell(startColumn + 11);
				if (reportdata.get(i).getBeginDate() != null) {
					beginDatecell
							.setCellValue(reportdata.get(i).getBeginDate());
					dateCellStyle.setDataFormat(df);
					beginDatecell.setCellStyle(dateCellStyle);
				}

				Cell endDatecell = row.createCell(startColumn + 12);
				if (reportdata.get(i).getEndDate() != null) {
					endDatecell.setCellValue(reportdata.get(i).getEndDate());
					dateCellStyle.setDataFormat(df);
					endDatecell.setCellStyle(dateCellStyle);
				}

				Cell progresscell = row.createCell(startColumn + 13);
				if (reportdata.get(i).getProgress() != null) {
					progresscell.setCellValue(reportdata.get(i).getProgress());
				}

				Cell hourscell = row.createCell(startColumn + 14);
				hourscell.setCellValue(reportdata.get(i).getHour());

				Cell statuscell = row.createCell(startColumn + 15);
				if (reportdata.get(i).getStatus() != null) {
					statuscell.setCellValue(reportdata.get(i).getStatus()
							.toString());
				}

				Cell statuscountcell = row.createCell(startColumn + 16);
				if (reportdata.get(i).getTaskStatusCount() != null) {
					statuscountcell.setCellValue(reportdata.get(i)
							.getTaskStatusCount());
				}

				Cell reasoncell = row.createCell(startColumn + 17);
				reasoncell.setCellValue(reportdata.get(i).getReason());
			}

			workbook.write(outputData);
			outputData.flush();
			outputData.close();

			setDownloadFileName(context.getRealPath("/upload/" + filename));
			setExportFileName(filename.toString());

		} catch (IOException e) {
			throw new HPSFException(e.getMessage());
		}

	}

	/* Calculate round value for total hours. */
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/* Getters and Setters. */
	public TaskStatus[] gettaskStatuses() {
		return TaskStatus.values();
	}

	public LeaveStatus[] getLeaveStatuses() {
		return LeaveStatus.values();
	}

	public CheckDiv[] getCheckDivs() {
		return CheckDiv.values();
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

	public IDailyReportDAO getDailyReportDAOService() {
		return dailyReportDAOService;
	}

	public void setDailyReportDAOService(IDailyReportDAO dailyReportDAOService) {
		this.dailyReportDAOService = dailyReportDAOService;
	}

	public IDailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public void setDailyActivityService(
			IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public DailyReport getDailyReport() {
		return dailyReport;
	}

	public void setDailyReport(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
	}

	public DailyActivity getDailyActivity() {
		return dailyActivity;
	}

	public void setDailyActivity(DailyActivity dailyActivity) {
		this.dailyActivity = dailyActivity;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<String> getMonths() {
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public List<String> getDays() {
		return days;
	}

	public void setDays(List<String> days) {
		this.days = days;
	}

	public SearchDate getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(SearchDate searchDate) {
		this.searchDate = searchDate;
	}

	public Date getToday() {
		return today;
	}

	public List<Task> getCategories() {
		return categories;
	}

	public void setCategories(List<Task> categories) {
		this.categories = categories;
	}

	public ITaskActivityService getTaskActivityService() {
		return taskActivityService;
	}

	public void setTaskActivityService(ITaskActivityService taskActivityService) {
		this.taskActivityService = taskActivityService;
	}

	public List<TaskActivity> getTaskActivities() {
		return taskActivities;
	}

	public void setTaskActivities(List<TaskActivity> taskActivities) {
		this.taskActivities = taskActivities;
	}

	public Double getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(Double totalHour) {
		this.totalHour = totalHour;
	}

	public boolean isBtnExport() {
		return btnExport;
	}

	public void setBtnExport(boolean btnExport) {
		this.btnExport = btnExport;
	}

	public List<DailyReport> getDailyReportList() {
		return dailyReportList;
	}

	public void setDailyReportList(List<DailyReport> dailyReportList) {
		this.dailyReportList = dailyReportList;
	}

	public List<DailyActivity> getDailyActivityList() {
		return dailyActivityList;
	}

	public void setDailyActivityList(List<DailyActivity> dailyActivityList) {
		this.dailyActivityList = dailyActivityList;
	}

	public ArrayList<ReportData> getReportDataList() {
		return reportDataList;
	}

	public void setReportDataList(ArrayList<ReportData> reportDataList) {
		this.reportDataList = reportDataList;
	}

	public ArrayList<ReportData> getMonthlyRptDataList() {
		return monthlyRptDataList;
	}

	public void setMonthlyRptDataList(ArrayList<ReportData> monthlyRptDataList) {
		this.monthlyRptDataList = monthlyRptDataList;
	}

	public HashMap<String, ArrayList<ReportData>> getHashMap() {
		return hashMap;
	}

	public void setHashMap(LinkedHashMap<String, ArrayList<ReportData>> hashMap) {
		this.hashMap = hashMap;
	}

	public ArrayList<TabViewData> getTabViewDataList() {
		return tabViewDataList;
	}

	public void setTabViewDataList(ArrayList<TabViewData> tabViewDataList) {
		this.tabViewDataList = tabViewDataList;
	}

	public DefaultStreamedContent getDownload() {
		return download;
	}

	public void setDownload(DefaultStreamedContent download) {
		this.download = download;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	public LeaveStatus getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(LeaveStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public CheckDiv getCheckDiv() {
		return checkDiv;
	}

	public void setCheckDiv(CheckDiv checkDiv) {
		this.checkDiv = checkDiv;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	/**
	 * Revised By   : Aung Htay Oo
	 * Revised Date : 2017-11-22
	 * Explanation  : Add filteredDataList member data with getters/setters methods with type ArrayList<TabViewData>.	 
	 */
	public ArrayList<TabViewData> getFilteredDataList() {
		return filteredDataList;
	}

	public void setFilteredDataList(ArrayList<TabViewData> filteredDataList) {
		this.filteredDataList = filteredDataList;
	}
	
	/**
	 * Revised By	: Aye Myat Mon
	 * Revised Date	: 2018/08/20 
	 * Explanation	: Added getter and setter of WhmUtilities.
	 */
	public WhmUtilities getWhmUtilities() {
		return whmUtilities;
	}

	public void setWhmUtilities(WhmUtilities whmUtilities) {
		this.whmUtilities = whmUtilities;
	}		

}
