/***************************************************************************************
 * @author Kyi Saw Win,Ye Thu Naing
 * @Date 2017-09-25
 * @Version 1.0
 * @Purpose Created for Phase III.
 *
 ***************************************************************************************/
package com.dat.whm.web.workinghourmanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.chart.LineChartModel;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.project.entity.Project;
import com.dat.whm.task.entity.Task;
import com.dat.whm.task.service.interfaces.ITaskService;
import com.dat.whm.web.actualprojectworkinghour.ReportData;

@ManagedBean(name = "WorkingHourManageDetailBean")
@ViewScoped
public class WorkingHourManagementDetailBean {

	@ManagedProperty(value = "#{TaskService}")
	private ITaskService taskService;

	private List<DataTableColumn> dataTableColumns = new ArrayList<DataTableColumn>();

	private DataTable myDataTable;
	private HashMap<String, ArrayList<ReportData>> monthlyRptDataMap;
	private ArrayList<WorkingHourManagementDetailData> detailDatalist;
	private ArrayList<Task> taskDatalist;
	private ArrayList<WorkingHourManagementDetailData> detailDatalistbinder;
	private WorkingHourManagementDetailData tempdetailDatalist;
	private ArrayList<WorkingHourManagementViewData> viewDataList;
	private ArrayList<DailyActivity> dailyactivityList;
	private ArrayList<String> columnList;
	private ArrayList<Double> totalList;
	private List<String> tasklist;
	private List<Double> testlist;
	private List<Task> tasks;
	private List<DailyActivity> dailyactivityLister;
	Iterator<WorkingHourManagementDetailData> itr;

	private double prjmgmtTotal;
	List<Task> tasktemplist;
	boolean backInquiryFlag;
	String screenId;
	private String startYear;
	private String startMonth;
	private String endYear;
	private String endMonth;
	private List<Integer> years;
	private List<Integer> months;
	private List<Project>  projects;
	private DailyActivity dailyactivity;
	private ArrayList<CalculateData> calculateDataList;
	LineChartModel categoryLineChart = new LineChartModel();
	LineChartModel taskLineChart = new LineChartModel();

	/* Initialization method. */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		if (!FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.isEmpty()) {
			monthlyRptDataMap = (HashMap<String, ArrayList<ReportData>>) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("monthlyRptDataMap");
			startYear = (String) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("startYear");
			startMonth = (String) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("startMonth");
			endYear = (String) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("endYear");
			endMonth = (String) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("endMonth");
			years = (List<Integer>) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("Years");
			months = (List<Integer>) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("Months");
			dailyactivityLister = (List<DailyActivity>) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("dailyActivityList");
			calculateDataList = (ArrayList<CalculateData>) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("calculateDataList");
			projects = (List<Project>) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("Projects");
			dailyactivity = (DailyActivity) FacesContext
					.getCurrentInstance().getExternalContext().getFlash()
					.get("DailyActivity");
			backInquiryFlag = (boolean) FacesContext.getCurrentInstance()
					.getExternalContext().getFlash().get("backInquiryFlag");
			screenId = (String) FacesContext.getCurrentInstance()
					.getExternalContext().getFlash().get("screenId");
			categoryLineChart = (LineChartModel) FacesContext.getCurrentInstance()
					.getExternalContext().getFlash().get("categoryLineChart");
			taskLineChart = (LineChartModel) FacesContext.getCurrentInstance()
					.getExternalContext().getFlash().get("taskLineChart");
		}
		tasks = taskService.findAllTask();
		detail();
	}

	/* Calculation method of WorkingHourManagementDetailData. */
	public void detail() {
		List<Task> taskList = new ArrayList<Task>();
		taskList = taskService.findAllTask();

		Map<Integer, String> mainCategoryMap = new TreeMap<Integer, String>();

		if (taskList.size() > 0) {

			for (Task t : taskList) {
				mainCategoryMap.put(t.getOrderNo(), t.getDescription());
			}
		}

		detailDatalist = new ArrayList<WorkingHourManagementDetailData>();

		ArrayList<Double> prjMgtList = new ArrayList<Double>();
		ArrayList<Double> trainingForPrjList = new ArrayList<Double>();
		ArrayList<Double> reqDefList = new ArrayList<Double>();
		ArrayList<Double> bdList = new ArrayList<Double>();
		ArrayList<Double> ddList = new ArrayList<Double>();
		ArrayList<Double> cdList = new ArrayList<Double>();
		ArrayList<Double> sdList = new ArrayList<Double>();
		ArrayList<Double> utSpecList = new ArrayList<Double>();
		ArrayList<Double> utExeList = new ArrayList<Double>();
		ArrayList<Double> utEviList = new ArrayList<Double>();
		ArrayList<Double> itSpecList = new ArrayList<Double>();
		ArrayList<Double> itExeList = new ArrayList<Double>();
		ArrayList<Double> itEviList = new ArrayList<Double>();
		ArrayList<Double> stSpecList = new ArrayList<Double>();
		ArrayList<Double> stExeList = new ArrayList<Double>();
		ArrayList<Double> stEviList = new ArrayList<Double>();
		ArrayList<Double> ptSpecList = new ArrayList<Double>();
		ArrayList<Double> ptExeList = new ArrayList<Double>();
		ArrayList<Double> ptEviList = new ArrayList<Double>();
		ArrayList<Double> uatSpecList = new ArrayList<Double>();
		ArrayList<Double> uatExeList = new ArrayList<Double>();
		ArrayList<Double> userTrainingList = new ArrayList<Double>();
		ArrayList<Double> cusSupportingList = new ArrayList<Double>();
		ArrayList<Double> optNmtnList = new ArrayList<Double>();
		ArrayList<Double> servLvlMtnList = new ArrayList<Double>();
		ArrayList<Double> incidentMgtList = new ArrayList<Double>();
		ArrayList<Double> envTuningList = new ArrayList<Double>();
		ArrayList<Double> internalWorkList = new ArrayList<Double>();
		ArrayList<Double> impTrainingList = new ArrayList<Double>();
		ArrayList<Double> leaveList = new ArrayList<Double>();
		ArrayList<Double> idilingWorkForPrjList = new ArrayList<Double>();
		ArrayList<Double> idilingWorkList = new ArrayList<Double>();
		totalList = new ArrayList<Double>();

		for (Map.Entry<String, ArrayList<ReportData>> e : monthlyRptDataMap
				.entrySet()) {
			double tmpProjectMgt = 0.0;
			double tmpTrainingPrj = 0.0;
			double tmpReqDefinition = 0.0;
			double tmpBasicDesign = 0.0;
			double tmpDetailDesign = 0.0;
			double tmpCodingDev = 0.0;
			double tmpSpecDev = 0.0;
			double tmpUnitTestSpec = 0.0;
			double tmpUnitTestExe = 0.0;
			double tmpUnitTextEvi = 0.0;
			double tmpITSpec = 0.0;
			double tmpITExe = 0.0;
			double tmpITEvi = 0.0;
			double tmpSystemSpec = 0.0;
			double tmpSystemExe = 0.0;
			double tmpSystemEvi = 0.0;
			double tmpPTSpec = 0.0;
			double tmpPTExe = 0.0;
			double tmpPTEvi = 0.0;
			double tmpAcceptanceTSpec = 0.0;
			double tmpAcceptanceTExe = 0.0;
			double tmpUserTraining = 0.0;
			double tmpCustomerSupport = 0.0;
			double tmpOperation = 0.0;
			double tmpService = 0.0;
			double tmpIncidentMgmt = 0.0;
			double tmpET = 0.0;
			double tmpDATInternal = 0.0;
			double tmpIT = 0.0;
			double tmpLeave = 0.0;
			double tmpIdlingPrj = 0.0;
			double tmpIdlingWork = 0.0;
			for (int j = 0; j <= e.getValue().size() - 1; j++) {

				switch (mainCategoryMap.get(e.getValue().get(j).getCategory()
						.getOrderNo())) {
				case "Project Management":

					tmpProjectMgt += e.getValue().get(j).getHour();
					break;

				case "Training For Project":

					tmpTrainingPrj += e.getValue().get(j).getHour();
					break;

				case "Requirement Definition":

					tmpReqDefinition += e.getValue().get(j).getHour();
					break;

				case "Basic Design":

					tmpBasicDesign += e.getValue().get(j).getHour();
					break;

				case "Detail Design":

					tmpDetailDesign += e.getValue().get(j).getHour();
					break;

				case "Coding Development":

					tmpCodingDev += e.getValue().get(j).getHour();
					break;

				case "Spec Development":

					tmpSpecDev += e.getValue().get(j).getHour();
					break;

				case "Unit Test Specification":

					tmpUnitTestSpec += e.getValue().get(j).getHour();
					break;

				case "Unit Test Execution":

					tmpUnitTestExe += e.getValue().get(j).getHour();
					break;

				case "Unit Test Evidence":

					tmpUnitTextEvi += e.getValue().get(j).getHour();
					break;

				case "Integrated Test Specification":

					tmpITSpec += e.getValue().get(j).getHour();
					break;
				case "Integrated Test Execution":

					tmpITExe += e.getValue().get(j).getHour();
					break;
				case "Integrated Test Evidence":

					tmpITEvi += e.getValue().get(j).getHour();
					break;
				case "System Test Specification":

					tmpSystemSpec += e.getValue().get(j).getHour();
					break;
				case "System Test Execution":

					tmpSystemExe += e.getValue().get(j).getHour();
					break;
				case "System Test Evidence":

					tmpSystemEvi += e.getValue().get(j).getHour();
					break;
				case "Performance Test Specification":

					tmpPTSpec += e.getValue().get(j).getHour();
					break;
				case "Performance Test Execution":

					tmpPTExe += e.getValue().get(j).getHour();
					break;
				case "Performance Test Evidence":

					tmpPTEvi += e.getValue().get(j).getHour();
					break;
				case "User Acceptance Test Specification":

					tmpAcceptanceTSpec += e.getValue().get(j).getHour();
					break;
				case "User Acceptance Test Execution":

					tmpAcceptanceTExe += e.getValue().get(j).getHour();
					break;
				case "User Training":

					tmpUserTraining += e.getValue().get(j).getHour();
					break;
				case "Customer Supporting":

					tmpCustomerSupport += e.getValue().get(j).getHour();
					break;
				case "Operation and Maintenance":

					tmpOperation += e.getValue().get(j).getHour();
					break;
				case "Service Level Maintenance":

					tmpService += e.getValue().get(j).getHour();
					break;
				case "Incident Management":

					tmpIncidentMgmt += e.getValue().get(j).getHour();
					break;
				case "Environmental Tuning":

					tmpET += e.getValue().get(j).getHour();
					break;
				case "DAT Internal Work":

					tmpDATInternal += e.getValue().get(j).getHour();
					break;
				case "Improvement Training":

					tmpIT += e.getValue().get(j).getHour();
					break;
				case "Leave":

					tmpLeave += e.getValue().get(j).getHour();
					break;
				case "Idling Work For Project":

					tmpIdlingPrj += e.getValue().get(j).getHour();
					break;
				case "Idling Work":

					tmpIdlingWork += e.getValue().get(j).getHour();
					break;
				}
			}
			prjMgtList.add(tmpProjectMgt);
			trainingForPrjList.add(tmpTrainingPrj);
			reqDefList.add(tmpReqDefinition);
			bdList.add(tmpBasicDesign);
			ddList.add(tmpDetailDesign);
			cdList.add(tmpCodingDev);
			sdList.add(tmpSpecDev);
			utSpecList.add(tmpUnitTestSpec);
			utExeList.add(tmpUnitTestExe);
			utEviList.add(tmpUnitTextEvi);
			itSpecList.add(tmpITSpec);
			itExeList.add(tmpITExe);
			itEviList.add(tmpITEvi);
			stSpecList.add(tmpSystemSpec);
			stExeList.add(tmpSystemExe);
			stEviList.add(tmpSystemEvi);
			ptSpecList.add(tmpPTSpec);
			ptExeList.add(tmpPTExe);
			ptEviList.add(tmpPTEvi);
			uatSpecList.add(tmpAcceptanceTSpec);
			uatExeList.add(tmpAcceptanceTExe);
			userTrainingList.add(tmpUserTraining);
			cusSupportingList.add(tmpCustomerSupport);
			optNmtnList.add(tmpOperation);
			servLvlMtnList.add(tmpService);
			incidentMgtList.add(tmpIncidentMgmt);
			envTuningList.add(tmpET);
			internalWorkList.add(tmpDATInternal);
			impTrainingList.add(tmpIT);
			leaveList.add(tmpLeave);
			idilingWorkForPrjList.add(tmpIdlingPrj);
			idilingWorkList.add(tmpIdlingWork);
			totalList.add(tmpProjectMgt + tmpTrainingPrj + tmpReqDefinition
					+ tmpBasicDesign + tmpDetailDesign + tmpCodingDev
					+ tmpSpecDev + tmpUnitTestSpec + tmpUnitTestExe
					+ tmpUnitTextEvi + tmpITSpec + tmpITExe + tmpITEvi
					+ tmpSystemSpec + tmpSystemExe + tmpSystemEvi + tmpPTSpec
					+ tmpPTExe + tmpPTEvi + tmpAcceptanceTSpec
					+ tmpAcceptanceTExe + tmpUserTraining + tmpCustomerSupport
					+ tmpOperation + tmpService + tmpIncidentMgmt + tmpET
					+ tmpDATInternal + tmpIT + tmpLeave + tmpIdlingPrj
					+ tmpIdlingWork);
		}

		columnList = new ArrayList<String>();
		columnList.add("Summary");
		columnList.add("Main Category");
		columnList.add("Category");
		for (Map.Entry<String, ArrayList<ReportData>> e : monthlyRptDataMap
				.entrySet()) {

			columnList.add(e.getKey().toString());
		}

		viewDataList = new ArrayList<WorkingHourManagementViewData>();
		for (Task t : tasks) {
			WorkingHourManagementViewData viewData = new WorkingHourManagementViewData();
			viewData.setSummary(t.getSummary());
			viewData.setMainCategory(t.getMainCategory());
			viewData.setCategory(t.getDescription());
			viewDataList.add(viewData);
		}
		
		viewDataList.get(0).setDataList(prjMgtList);
		viewDataList.get(1).setDataList(trainingForPrjList);
		viewDataList.get(2).setDataList(reqDefList);
		viewDataList.get(3).setDataList(bdList);
		viewDataList.get(4).setDataList(ddList);
		viewDataList.get(5).setDataList(cdList);
		viewDataList.get(6).setDataList(sdList);
		viewDataList.get(7).setDataList(utSpecList);
		viewDataList.get(8).setDataList(utExeList);
		viewDataList.get(9).setDataList(utEviList);
		viewDataList.get(10).setDataList(itSpecList);
		viewDataList.get(11).setDataList(itExeList);
		viewDataList.get(12).setDataList(itEviList);
		viewDataList.get(13).setDataList(stSpecList);
		viewDataList.get(14).setDataList(stExeList);
		viewDataList.get(15).setDataList(stEviList);
		viewDataList.get(16).setDataList(ptSpecList);
		viewDataList.get(17).setDataList(ptExeList);
		viewDataList.get(18).setDataList(ptEviList);
		viewDataList.get(19).setDataList(uatSpecList);
		viewDataList.get(20).setDataList(uatExeList);
		viewDataList.get(21).setDataList(userTrainingList);
		viewDataList.get(22).setDataList(cusSupportingList);
		viewDataList.get(23).setDataList(optNmtnList);
		viewDataList.get(24).setDataList(servLvlMtnList);
		viewDataList.get(25).setDataList(incidentMgtList);
		viewDataList.get(26).setDataList(envTuningList);
		viewDataList.get(27).setDataList(internalWorkList);
		viewDataList.get(28).setDataList(impTrainingList);
		viewDataList.get(29).setDataList(leaveList);
		viewDataList.get(30).setDataList(idilingWorkForPrjList);
		viewDataList.get(31).setDataList(idilingWorkList);
	}

	/* Passing parameters to WorkingHourManagementBean Form. */
	public void back() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("monthlyRptDataMap", monthlyRptDataMap);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("startYear", startYear);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("startMonth", startMonth);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("endYear", endYear);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("endMonth", endMonth);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("years", years);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("months", months);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("dailyactivityLister", dailyactivityLister);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("calculateDataList", calculateDataList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("Projects", projects);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("DailyActivity", dailyactivity);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("categoryLineChart", categoryLineChart);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("taskLineChart", taskLineChart);
		try {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect(context.getRequestContextPath()
					+ "/faces/view/mgtreports/MNR002.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Getters and Setters. */
	public DataTable getMyDataTable() {
		return myDataTable;
	}

	public void setMyDataTable(DataTable myDataTable) {
		this.myDataTable = myDataTable;
	}

	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public boolean isBackInquiryFlag() {
		return backInquiryFlag;
	}

	public void setBackInquiryFlag(boolean backInquiryFlag) {
		this.backInquiryFlag = backInquiryFlag;
	}

	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public List<String> getTasklist() {
		return tasklist;
	}

	public void setTasklist(List<String> tasklist) {
		this.tasklist = tasklist;
	}

	public List<Task> getTasktemplist() {
		return tasktemplist;
	}

	public void setTasktemplist(List<Task> tasktemplist) {
		this.tasktemplist = tasktemplist;
	}

	public List<DataTableColumn> getDataTableColumns() {
		return dataTableColumns;
	}

	public void setDataTableColumns(List<DataTableColumn> dataTableColumns) {
		this.dataTableColumns = dataTableColumns;
	}

	public double getPrjmgmtTotal() {
		return prjmgmtTotal;
	}

	public void setPrjmgmtTotal(double prjmgmtTotal) {
		this.prjmgmtTotal = prjmgmtTotal;
	}

	public HashMap<String, ArrayList<ReportData>> getMonthlyRptDataMap() {
		return monthlyRptDataMap;
	}

	public void setMonthlyRptDataMap(
			HashMap<String, ArrayList<ReportData>> monthlyRptDataMap) {
		this.monthlyRptDataMap = monthlyRptDataMap;
	}

	public List<WorkingHourManagementDetailData> getDetailDatalist() {
		return detailDatalist;
	}

	public void setDetailDatalist(
			ArrayList<WorkingHourManagementDetailData> detailDatalist) {
		this.detailDatalist = detailDatalist;
	}

	public Iterator<WorkingHourManagementDetailData> getItr() {
		return itr;
	}

	public void setItr(Iterator<WorkingHourManagementDetailData> itr) {
		this.itr = itr;
	}

	public WorkingHourManagementDetailData getTempdetailDatalist() {
		return tempdetailDatalist;
	}

	public void setTempdetailDatalist(
			WorkingHourManagementDetailData tempdetailDatalist) {
		this.tempdetailDatalist = tempdetailDatalist;
	}

	public ArrayList<WorkingHourManagementDetailData> getDetailDatalistbinder() {
		return detailDatalistbinder;
	}

	public void setDetailDatalistbinder(
			ArrayList<WorkingHourManagementDetailData> detailDatalistbinder) {
		this.detailDatalistbinder = detailDatalistbinder;
	}

	public List<Double> getTestlist() {
		return testlist;
	}

	public void setTestlist(List<Double> testlist) {
		this.testlist = testlist;
	}

	public ArrayList<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(ArrayList<String> columnList) {
		this.columnList = columnList;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public ArrayList<Task> getTaskDatalist() {
		return taskDatalist;
	}

	public void setTaskDatalist(ArrayList<Task> taskDatalist) {
		this.taskDatalist = taskDatalist;
	}

	public ArrayList<WorkingHourManagementViewData> getViewDataList() {
		return viewDataList;
	}

	public void setViewDataList(
			ArrayList<WorkingHourManagementViewData> viewDataList) {
		this.viewDataList = viewDataList;
	}

	public ArrayList<Double> getTotalList() {
		return totalList;
	}

	public void setTotalList(ArrayList<Double> totalList) {
		this.totalList = totalList;
	}

	public ArrayList<DailyActivity> getDailyactivityList() {
		return dailyactivityList;
	}

	public void setDailyactivityList(ArrayList<DailyActivity> dailyactivityList) {
		this.dailyactivityList = dailyactivityList;
	}

	public List<DailyActivity> getDailyactivityLister() {
		return dailyactivityLister;
	}

	public void setDailyactivityLister(List<DailyActivity> dailyactivityLister) {
		this.dailyactivityLister = dailyactivityLister;
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

	public List<Integer> getMonths() {
		return months;
	}

	public void setMonths(List<Integer> months) {
		this.months = months;
	}

	public ArrayList<CalculateData> getCalculateDataList() {
		return calculateDataList;
	}

	public void setCalculateDataList(ArrayList<CalculateData> calculateDataList) {
		this.calculateDataList = calculateDataList;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public DailyActivity getDailyactivity() {
		return dailyactivity;
	}

	public void setDailyactivity(DailyActivity dailyactivity) {
		this.dailyactivity = dailyactivity;
	}
	/* Getters and Setters. */
}
