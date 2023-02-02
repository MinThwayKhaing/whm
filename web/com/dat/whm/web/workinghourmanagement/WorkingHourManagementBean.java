/***************************************************************************************
 * @author Ye Thu Naing
 * @Date 2017-09-18
 * @Version 1.0
 * @Purpose Created for Phase III.
 *
 ***************************************************************************************/
package com.dat.whm.web.workinghourmanagement;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.exception.SystemException;
import com.dat.whm.holidays.service.interfaces.IHolidaysService;
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.service.interfaces.IProjectService;
import com.dat.whm.task.entity.Task;
import com.dat.whm.task.service.interfaces.ITaskService;
import com.dat.whm.web.Constants;
import com.dat.whm.web.actualprojectworkinghour.ReportData;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.Properties;
import com.dat.whm.web.common.SearchDate;
import com.dat.whm.web.common.WhmUtilities;

@ManagedBean(name = "WorkingHourManageBean")
@ViewScoped
public class WorkingHourManagementBean extends BaseBean {

	@ManagedProperty(value = "#{ProjectService}")
	private IProjectService projectService;
	@ManagedProperty(value = "#{TaskService}")
	private ITaskService taskService;
	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;
	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{HolidaysService}")
	private IHolidaysService holidaysService;

	private List<DailyReport> dailyReportList;
	private List<DailyActivity> dailyActivityList;
	private List<Project> projects;
	private ArrayList<CalculateData> calculateDataList;
	
	/**
	 * Revised By   : Aung Htay Oo
	 * Revised Date : 2017/11/01
	 * Explanation  : Modified for serially retrieve monthly report data.		 
	 */
	private LinkedHashMap<String, ArrayList<ReportData>> monthlyRptDataMap;
	LineChartModel categoryLineChart = new LineChartModel();
	LineChartModel taskLineChart = new LineChartModel();
	private ArrayList<ReportData> reportDataList;
	

	private String startYear;
	private String startMonth;
	private String endYear;
	private String endMonth;
	private List<Integer> years;
	private List<String> months;
	boolean detailLnkVisible;
	boolean backInquiryFlag;

	DailyReport dailyReport;
	DailyActivity dailyActivity;
	SearchDate searchDate;

	private boolean btnShow;
	
	/* Initialization method. */
	@PostConstruct
	public void init() {
		projects = projectService.findAllProject();
		dailyActivity = new DailyActivity();
		dailyActivity.setProject(new Project());

		dailyReport = new DailyReport();
		searchDate = new SearchDate();
		detailLnkVisible = false;
	}

	/* Binding data to startYear,endYear,startMonth and endMonth. */
	public void getYear() {
		String projectId = "";
		btnShow = false;
		try {
			projectId = dailyActivity.getProject().getId();
		} catch (NullPointerException e) {
			years = new ArrayList<>();
			months = new ArrayList<>();
			calculateDataList = new ArrayList<>();
			categoryLineChart = new LineChartModel();
			taskLineChart = new LineChartModel();
			detailLnkVisible = false;
			return;
		}
		
		try {
			/* Get project start date as start date from project table. */
			Date tmpStartDate = projectService.findStartDateByProjectId(projectId);
			Date tmpActStartDate = dailyActivityService.findMinDateByProjectID(projectId);
			if (tmpActStartDate.before(tmpStartDate)) {
				tmpStartDate = tmpActStartDate;
			}
			String tmpStartYear = WhmUtilities.getYearFromDate(tmpStartDate);
			String tmpStartMonth = WhmUtilities.getMonthFromDate(tmpStartDate);
			
			/* Get maximum reporting date as end date from daily activity table. */
			Date tmpEndDate = dailyActivityService.findActivityDateByProjectID(projectId);
			String tmpEndYear = WhmUtilities.getYearFromDate(tmpEndDate);
			String tmpEndMonth = WhmUtilities.getMonthFromDate(tmpEndDate);
			
			/* Prepare year list. */
			years = new ArrayList<Integer>();
			for (int i = Integer.parseInt(tmpStartYear); i <= Integer.parseInt(tmpEndYear); i++) {
				years.add(i);
			}
			
			/*If start year and end year is not same, bind 12 months.
			 * Else, calculate months. */
			months = new ArrayList<String>();
			if (Integer.parseInt(tmpEndYear) - Integer.parseInt(tmpStartYear) > 0) {
				months = WhmUtilities.getAllMonths();
			} else {
				/* Prepare month list. */
				List<Integer> tmpMonths = new ArrayList<Integer>();
				for (int i = Integer.parseInt(tmpStartMonth) ; i <= Integer.parseInt(tmpEndMonth); i++) {
					tmpMonths.add(i);
				}
				if (!months.equals("")) {
					months=WhmUtilities.convertDigitToMonth(tmpMonths);
				}
			}
			
			/* Prepare for selected value. */
			startYear = tmpStartYear;
			endYear = tmpEndYear;
			startMonth = WhmUtilities.findMonthByDigit(tmpStartMonth);
			endMonth = WhmUtilities.findMonthByDigit(tmpEndMonth);
			
		} catch (NullPointerException e) {
			btnShow = true;
			String InfoMessage = "No one did not report for " + projectId;
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							InfoMessage));
		}
	}
	
	/***************************************************************************************
	 * Revised By   : Aung Htay Oo
	 * Revised Date : 2017/11/09
	 * Explanation  : Modified for positioning the series below the chart
	 * 				  and rotate year label to 30 degree below X-axis of chart.
	 ***************************************************************************************/
	/* Search Report. */
	public void show() {
		if (dailyActivity.getProject() == null || dailyActivity.getProject().getId() == null) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "","Please select a Project ID."));
			return;
		}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("###.#");

		Date fromdate = null;
		try {
			fromdate = format.parse(startYear + "-" + WhmUtilities.findMonthDigit(startMonth) + "-" + "01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		searchDate.setFromDate(fromdate);
		
		Date todate = null;
		try {
			todate = format.parse(endYear + "-" + WhmUtilities.findMonthDigit(endMonth) + "-" + WhmUtilities.getLastDayOfMonth(endYear,WhmUtilities.findMonthDigit(endMonth)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		searchDate.setToDate(todate);
		
		dailyReportList = dailyReportService.findAllReportByCriteria(dailyReport, "", "", "", searchDate);
		if (dailyReportList.size() == 0 ) {
			String InfoMessage = "There is no search result.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							InfoMessage));
			return;
		}
		
		reportDataList = new ArrayList<ReportData>();
		for (DailyReport dr : dailyReportList) {

			dailyActivityList = dailyActivityService
					.findAllActivitiesByCriteria(dr, dailyActivity);

			if (dailyActivityList.size() > 0) {

				for (DailyActivity da : dailyActivityList) {
					
					ReportData rptData = new ReportData();
					
					rptData.setDate(dr.getActivityDate());
					rptData.setStaffID(dr.getStaffID().getUsername());
					rptData.setStaffName(dr.getStaffID().getFullName());
					if (da.getProject() != null) {
						rptData.setProjectID(da.getProject().getId());
						rptData.setProjectName(da.getProject().getProjectName());
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
					if (da.getProgressPercentage() != null) {
						rptData.setProgress(da.getProgressPercentage());
					}
					if (da.getActivityHours() != null) {
						rptData.setHour(da.getActivityHours());
					}
					if (da.getTaskStatus() != null) {
						rptData.setStatus(da.getTaskStatus());
					}
					if (da.getTaskStatusCount() != null) {
						rptData.setTaskStatusCount(da.getTaskStatusCount());
					}
					rptData.setReason(da.getReason());
					reportDataList.add(rptData);
				}
				
			}

		}
		
		if (reportDataList.size() == 0 ) {
			String InfoMessage = "There is no search result.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							InfoMessage));
			return;
		}
		
		monthlyRptDataMap = new LinkedHashMap<String, ArrayList<ReportData>>();
		if (reportDataList.size() > 0) {

			/* Get report data list by monthly. */
			String tmpMonth;
			
			for (ReportData rptData : reportDataList) {
				tmpMonth = WhmUtilities.getYearFromDate(rptData.getDate()) + "-" + WhmUtilities.getMonthFromDate(rptData.getDate(), "MMM");
				if (!monthlyRptDataMap.containsKey(tmpMonth)) {
					ArrayList<ReportData> monthlyRptDataList = new ArrayList<ReportData>();
					monthlyRptDataList.add(rptData);
					monthlyRptDataMap.put(tmpMonth, monthlyRptDataList);

				} else {
					monthlyRptDataMap.get(tmpMonth).add(rptData);
				}
			}

			/* Calculate daily activity hour by Task and Category. */
			
			/* Get all task data from task table. */
			List<Task> taskList = new ArrayList<Task>();
			taskList = taskService.findAllTask();

			Map<Integer, String> mainCategoryMap = new TreeMap<Integer, String>();
			Map<Integer, String> summaryMap = new TreeMap<Integer, String>();

			if (taskList.size() > 0) {

				for (Task t : taskList) {
					/*
					 * Get main category map. Key: id; Value: main_categoy from
					 * task table.
					 */
					mainCategoryMap.put(t.getOrderNo(), t.getMainCategory());

					/* Get task map. Key: id; Value: summary from task table. */
					summaryMap.put(t.getOrderNo(), t.getSummary());
				}

			}
			double tmpProjectMgt = 0.0;
			double tmpTaskMgt = 0.0;
			double tmpOthers = 0.0;
			double tmpIdling = 0.0;
			double tmpActualWork = 0.0;
			double tmpInternalWork = 0.0;
			double tmpWaitingTime = 0.0;
			double tmpIdlingTime = 0.0;
			int tmpMemberCount = 0;
			double tmpTotalHr = 0.0;

			calculateDataList = new ArrayList<CalculateData>();

			for (Map.Entry<String, ArrayList<ReportData>> e : monthlyRptDataMap
					.entrySet()) {

				Map<String, Integer> memberMap = new TreeMap<String, Integer>();

				CalculateData calData = new CalculateData();
				for (int j = 0; j <= e.getValue().size() - 1; j++) {

					/* By Category. */
					switch (mainCategoryMap.get(e.getValue().get(j)
							.getCategory().getOrderNo())) {
					case "Project Management":

						tmpProjectMgt += e.getValue().get(j).getHour();
						break;

					case "Task Management":
						tmpTaskMgt += e.getValue().get(j).getHour();
						break;

					case "Others":
						tmpOthers += e.getValue().get(j).getHour();
						break;

					case "Idling":
						tmpIdling += e.getValue().get(j).getHour();
						break;
					}

					/* By Tasks Summary. */
					switch (summaryMap.get(e.getValue().get(j).getCategory()
							.getOrderNo())) {
					case "Actual Work":
						tmpActualWork += e.getValue().get(j).getHour();
						break;

					case "DAT Internal Work":
						tmpInternalWork += e.getValue().get(j).getHour();
						break;

					case "Idling Work For Project":
						tmpWaitingTime += e.getValue().get(j).getHour();
						break;

					case "Idling Work":
						tmpIdlingTime += e.getValue().get(j).getHour();
						break;
					}

					if (!memberMap
							.containsKey(e.getValue().get(j).getStaffID())) {
						memberMap.put(e.getValue().get(j).getStaffID(),
								tmpMemberCount);
					}

				}

				calData.setMonth(e.getKey().toString());
				calData.setProjectID(dailyActivity.getProject().getId());
				calData.setProjectName(dailyActivity.getProject()
						.getProjectName());
				calData.setMembers(memberMap.size());
				tmpTotalHr = calculateTotalHours(memberMap.size(), e.getKey());
				calData.setTotalHours(tmpTotalHr);
				calData.setActualTotalHours(tmpProjectMgt + tmpTaskMgt
						+ tmpOthers + tmpIdling);
				calData.setProjectMgt(tmpProjectMgt);
				calData.setTaskMgt(tmpTaskMgt);
				calData.setOthers(tmpOthers);
				calData.setIdling(tmpIdling);
				calData.setActualwork(tmpActualWork);
				calData.setInternalwork(tmpInternalWork);
				calData.setWaitingTime(tmpWaitingTime);
				calData.setIdlingTime(tmpIdlingTime);
				if (tmpTotalHr > 0) {
					calData.setProjectMgtPercent(df
							.format((tmpProjectMgt / tmpTotalHr) * 100) + "%");
					calData.setTaskMgtPercent(df
							.format((tmpTaskMgt / tmpTotalHr) * 100) + "%");
					calData.setOthersPercent(df
							.format((tmpOthers / tmpTotalHr) * 100) + "%");
					calData.setIdlingPercent(df
							.format((tmpIdling / tmpTotalHr) * 100) + "%");
					calData.setActualWorkPercent(df
							.format((tmpActualWork / tmpTotalHr) * 100) + "%");
					calData.setInternalWorkPercent(df
							.format((tmpInternalWork / tmpTotalHr) * 100) + "%");
					calData.setWaitingTimePercent(df
							.format((tmpWaitingTime / tmpTotalHr) * 100) + "%");
					calData.setIdlingTImePercent(df
							.format((tmpIdlingTime / tmpTotalHr) * 100) + "%");
				} else {
					calData.setProjectMgtPercent("0%");
					calData.setTaskMgtPercent("0%");
					calData.setOthersPercent("0%");
					calData.setIdlingPercent("0%");
					calData.setActualWorkPercent("0%");
					calData.setInternalWorkPercent("0%");
					calData.setWaitingTimePercent("0%");
					calData.setIdlingTImePercent("0%");
				}

				calculateDataList.add(calData);

				tmpProjectMgt = 0.0;
				tmpTaskMgt = 0.0;
				tmpOthers = 0.0;
				tmpIdling = 0.0;
				tmpActualWork = 0.0;
				tmpInternalWork = 0.0;
				tmpWaitingTime = 0.0;
				tmpIdlingTime = 0.0;
				tmpMemberCount = 0;
				tmpTotalHr = 0.0;

			}

		}
		if (calculateDataList.size() > 0) {
			detailLnkVisible = true;
		}

		ChartSeries projectmgmt = new ChartSeries();
		ChartSeries taskmgmt = new ChartSeries();
		ChartSeries idling = new ChartSeries();
		ChartSeries others = new ChartSeries();
		ChartSeries actualworkmgmt = new ChartSeries();
		ChartSeries internalworkmgmt = new ChartSeries();
		ChartSeries waitingTime = new ChartSeries();
		ChartSeries idlingTime = new ChartSeries();

		projectmgmt.setLabel("ProjectManagement");
		taskmgmt.setLabel("TaskManagement");
		idling.setLabel("Idling");
		others.setLabel("Others");
		actualworkmgmt.setLabel("ActualWork");
		internalworkmgmt.setLabel("InternalWork");
		waitingTime.setLabel("WaitingTime");
		idlingTime.setLabel("Idling Time");

		double tmpPrjPercent = 0.0;
		double tmpTaskMgt = 0.0;
		double tmpOthers = 0.0;
		double tmpIdling = 0.0;
		double tmpActualWork = 0.0;
		double tmpInternalWork = 0.0;
		double tmpWaitingTime = 0.0;
		double tmpIdlingTime = 0.0;

		for (CalculateData cal : calculateDataList) {
			try {
				tmpPrjPercent = Double.parseDouble(cal.getProjectMgtPercent()
						.replace("%", ""));
				tmpTaskMgt = Double.parseDouble(cal.getTaskMgtPercent()
						.replace("%", ""));
				tmpIdling = Double.parseDouble(cal.getIdlingPercent().replace(
						"%", ""));
				tmpOthers = Double.parseDouble(cal.getOthersPercent().replace(
						"%", ""));
				tmpActualWork = Double.parseDouble(cal.getActualWorkPercent()
						.replace("%", ""));
				tmpInternalWork = Double.parseDouble(cal
						.getInternalWorkPercent().replace("%", ""));
				tmpWaitingTime = Double.parseDouble(cal.getWaitingTimePercent()
						.replace("%", ""));
				tmpIdlingTime = Double.parseDouble(cal.getIdlingTImePercent()
						.replace("%", ""));

				projectmgmt.set(cal.getMonth(),
						(Number) Math.round(tmpPrjPercent));
				taskmgmt.set(cal.getMonth(), (Number) Math.round(tmpTaskMgt));
				idling.set(cal.getMonth(), (Number) Math.round(tmpIdling));
				others.set(cal.getMonth(), (Number) Math.round(tmpOthers));

				actualworkmgmt.set(cal.getMonth(),
						(Number) Math.round(tmpActualWork));
				internalworkmgmt.set(cal.getMonth(),
						(Number) Math.round(tmpInternalWork));
				waitingTime.set(cal.getMonth(),
						(Number) Math.round(tmpWaitingTime));
				idlingTime.set(cal.getMonth(),
						(Number) Math.round(tmpIdlingTime));
			} catch (NumberFormatException e) {
				projectmgmt.set(cal.getMonth(), 0);
				taskmgmt.set(cal.getMonth(), 0);
				idling.set(cal.getMonth(), 0);
				others.set(cal.getMonth(), 0);
				actualworkmgmt.set(cal.getMonth(), 0);
				internalworkmgmt.set(cal.getMonth(), 0);
				waitingTime.set(cal.getMonth(), 0);
				idlingTime.set(cal.getMonth(), 0);
			}
		}
		categoryLineChart.clear();
		taskLineChart.clear();
		categoryLineChart.addSeries(projectmgmt);
		categoryLineChart.addSeries(taskmgmt);
		categoryLineChart.addSeries(idling);
		categoryLineChart.addSeries(others);
		categoryLineChart.setTitle("By Category");
		categoryLineChart.setLegendPosition("s");
		//categoryLineChart.setShowPointLabels(true);
		categoryLineChart.getAxes().put(AxisType.X, new CategoryAxis("Years"));
		categoryLineChart.setLegendRows(1);
		categoryLineChart.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		Axis yAxis1 = categoryLineChart.getAxis(AxisType.Y);
		yAxis1.setLabel("Percentage");

		taskLineChart.clear();
		taskLineChart.addSeries(actualworkmgmt);
		taskLineChart.addSeries(internalworkmgmt);
		taskLineChart.addSeries(waitingTime);
		taskLineChart.addSeries(idlingTime);
		taskLineChart.setTitle("By Task");
		taskLineChart.setLegendPosition("s");
		//taskLineChart.setShowPointLabels(true);
		taskLineChart.getAxes().put(AxisType.X, new CategoryAxis("Years"));
		taskLineChart.setLegendRows(1);
		taskLineChart.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		Axis yAxis2 = taskLineChart.getAxis(AxisType.Y);
		yAxis2.setLabel("Percentage");
		
		/**
		 * Rotate Year Labels to 30 degree below X-axis.
		 */
		Axis xAxis = taskLineChart.getAxis(AxisType.X);
		xAxis.setTickAngle(-30);
		Axis xAxis2 = categoryLineChart.getAxis(AxisType.X);
		xAxis2.setTickAngle(-30);
	}

	/* Calculating total hours according to members count. */
	public double calculateTotalHours(int memberCount,String currentYearMonth) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		double totalHr = 0.0;
		SearchDate currentSearchDate= new SearchDate();
		Date fromDate = null;
		Date toDate = null;
		double workingHrPerDay = Double.parseDouble(Properties.getProperty(
				Constants.APP_SETTING, Constants.WORKING_HOUR_PER_DAY));
		
		String[] yearMonth = currentYearMonth.split("-");
		GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Integer.parseInt(WhmUtilities.findMonthDigit(yearMonth[1]))-1, 1);
		try {
			fromDate= format.parse(yearMonth[0] + "-" + WhmUtilities.findMonthDigit(yearMonth[1]) + "-" + "01");
			toDate = format.parse(yearMonth[0] + "-" + WhmUtilities.findMonthDigit(yearMonth[1]) + "-" + gc.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		currentSearchDate.setFromDate(fromDate);
		currentSearchDate.setToDate(toDate);
		int holiday = holidaysService.countByYearMonth(currentSearchDate);
		
		double actualWorkingDay = WhmUtilities.getWorkingDaysBetweenTwoDates(fromDate, toDate, holiday);
		totalHr = workingHrPerDay * actualWorkingDay * memberCount;
		return totalHr;
	}

	/* Passing parameters to WorkingHourManagementDetailBean. */
	public String detail(
			HashMap<String, ArrayList<ReportData>> monthlyRptDataMap,
			String screenId) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("dailyActivityList", dailyActivityList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("startYear", startYear);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("startMonth", startMonth);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("endYear", endYear);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("endMonth", endMonth);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("Years", years);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("Months", months);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("monthlyRptDataMap", monthlyRptDataMap);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("calculateDataList", calculateDataList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("Projects", projects);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("DailyActivity", dailyActivity);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("screenId", screenId);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("categoryLineChart", categoryLineChart);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("taskLineChart", taskLineChart);
		return "/faces/view/mgtreports/MNR003.xhtml?faces-redirect=true";
	}

	/* Form components control. */
	@SuppressWarnings("unchecked")
	public void controlForm() {
		try {
			init();

			if (!FacesContext.getCurrentInstance().getExternalContext()
					.getFlash().isEmpty()) {
				boolean backInquiryFlag = (boolean) FacesContext
						.getCurrentInstance().getExternalContext().getFlash()
						.get("backInquiryFlag");

				if (backInquiryFlag) {
					monthlyRptDataMap = (LinkedHashMap<String, ArrayList<ReportData>>) FacesContext
							.getCurrentInstance().getExternalContext()
							.getFlash().get("monthlyRptDataMap");
					startYear = (String) FacesContext.getCurrentInstance()
							.getExternalContext().getFlash().get("startYear");
					startMonth = (String) FacesContext.getCurrentInstance()
							.getExternalContext().getFlash().get("startMonth");
					endYear = (String) FacesContext.getCurrentInstance()
							.getExternalContext().getFlash().get("endYear");
					endMonth = (String) FacesContext.getCurrentInstance()
							.getExternalContext().getFlash().get("endMonth");
					years = (List<Integer>) FacesContext.getCurrentInstance()
							.getExternalContext().getFlash().get("years");
					projects = (List<Project>) FacesContext
							.getCurrentInstance().getExternalContext()
							.getFlash().get("Projects");
					dailyActivity = (DailyActivity) FacesContext
							.getCurrentInstance().getExternalContext()
							.getFlash().get("DailyActivity");
					calculateDataList = (ArrayList<CalculateData>) FacesContext
							.getCurrentInstance().getExternalContext()
							.getFlash().get("calculateDataList");
					months = (List<String>) FacesContext.getCurrentInstance()
							.getExternalContext().getFlash().get("months");
					dailyActivityList = (List<DailyActivity>) FacesContext
							.getCurrentInstance().getExternalContext()
							.getFlash().get("dailyactivityLister");
					categoryLineChart = (LineChartModel) FacesContext.getCurrentInstance()
							.getExternalContext().getFlash().get("categoryLineChart");
					taskLineChart = (LineChartModel) FacesContext.getCurrentInstance()
							.getExternalContext().getFlash().get("taskLineChart");
					detailLnkVisible = true;
				}
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	/* Getters and Setters */
	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public IDailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public void setDailyActivityService(
			IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public IDailyReportService getDailyReportService() {
		return dailyReportService;
	}

	public void setDailyReportService(IDailyReportService dailyReportService) {
		this.dailyReportService = dailyReportService;
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

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public ArrayList<CalculateData> getCalculateDataList() {
		return calculateDataList;
	}

	public void setCalculateDataList(ArrayList<CalculateData> calculateDataList) {
		this.calculateDataList = calculateDataList;
	}

	public ArrayList<ReportData> getReportDataList() {
		return reportDataList;
	}

	public void setReportDataList(ArrayList<ReportData> reportDataList) {
		this.reportDataList = reportDataList;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<String> getMonths() {
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
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

	public SearchDate getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(SearchDate searchDate) {
		this.searchDate = searchDate;
	}

	public HashMap<String, ArrayList<ReportData>> getMonthlyRptDataMap() {
		return monthlyRptDataMap;
	}

	public void setMonthlyRptDataMap(
			LinkedHashMap<String, ArrayList<ReportData>> monthlyRptDataMap) {
		this.monthlyRptDataMap = monthlyRptDataMap;
	}

	public boolean isDetailLnkVisible() {
		return detailLnkVisible;
	}

	public void setDetailLnkVisible(boolean detailLnkVisible) {
		this.detailLnkVisible = detailLnkVisible;
	}

	public LineChartModel getCategoryLineChart() {
		return categoryLineChart;
	}

	public void setCategoryLineChart(LineChartModel categoryLineChart) {
		this.categoryLineChart = categoryLineChart;
	}

	public LineChartModel getTaskLineChart() {
		return taskLineChart;
	}

	public void setTaskLineChart(LineChartModel taskLineChart) {
		this.taskLineChart = taskLineChart;
	}

	public boolean isBackInquiryFlag() {
		return backInquiryFlag;
	}

	public void setBackInquiryFlag(boolean backInquiryFlag) {
		this.backInquiryFlag = backInquiryFlag;
	}

	public boolean isBtnShow() {
		return btnShow;
	}

	public void setBtnShow(boolean btnShow) {
		this.btnShow = btnShow;
	}

	public IHolidaysService getHolidaysService() {
		return holidaysService;
	}

	public void setHolidaysService(IHolidaysService holidaysService) {
		this.holidaysService = holidaysService;
	}
	
	/* Getters and Setters. */

}
