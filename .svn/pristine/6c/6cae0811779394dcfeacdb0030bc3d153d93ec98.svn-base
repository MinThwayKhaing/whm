/***************************************************************************************
 * @author Aye Chan Thar Soe
 * @Date 2018-09-25
 * @Version 
 * @Purpose 
 *
 ***************************************************************************************/
package com.dat.whm.web.workinghrmanagementproject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.hpsf.HPSFException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;

import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.service.interfaces.IProjectService;
import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.summaryactivity.service.interfaces.ISummaryActivityService;
import com.dat.whm.web.common.TableHeader;
import com.dat.whm.web.common.WhmUtilities;


@ManagedBean(name = "WorkingHourManagementProjectBean")
@ViewScoped
	
public class WorkingHourManagementProjectBean  {
	@ManagedProperty(value = "#{ProjectService}")
	private IProjectService projectService;

	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;

	@ManagedProperty(value = "#{SummaryActivityService}")
	private ISummaryActivityService summaryActivityService;

	private List<Project> projects;
	private List<String> clientOrganization;
	private String startYear;
	private String endYear;
	private String startMonth;
	private String endMonth;
	private String startDate;
	private String endDate;
	private String customer;
	private List<Integer> years;
	private List<String> months;
	private String projectID;
	private boolean btnShow;
	private boolean btnExport;
	private boolean fullPrjFlag = false;
	private boolean partialPrjFlag = false;
	private boolean allPrjFlag = false;
	private boolean nullPrjFlag = false;
	private boolean customerFlag = false;
	private boolean autoSelectFlag = false;
	
	private ProjectSummaryInfo summaryInfo;
	private List<ProjectSummaryInfo> summaryInfoList;
	
	private List<TableHeader> tableHeaderList;
	private List<SummaryActivity> teamList;
	private List<TotalPrjInfo> totalInfoList;
	private List<ProjectSummaryInfo> projectIDList;
	private List<ProjectSummaryInfo> fullResultList;
	private List<ProjectSummaryInfo> partialResultList;
	private List<ProjectSummaryInfo> nullResultList;
	private List<ProjectSummaryInfo> customerResultList;
	private List<ProjectSummaryInfo> allResultList;
	//Export excel
	private DefaultStreamedContent download;
	private String downloadFileName;
	private String exportFileName;
	private boolean partialPrjExportFlag = false;
	private List<String> holidayCount;
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		 summaryInfoList = new ArrayList<>();
		 
		 tableHeaderList = new ArrayList<>();
		 teamList = new ArrayList<>();
		 totalInfoList = new ArrayList<>();
		 projectIDList = new ArrayList<>(); 
		
		 fullResultList  = new ArrayList<>();
		 partialResultList  = new ArrayList<>();
		 nullResultList = new ArrayList<>();
		 customerResultList = new ArrayList<>();
		 allResultList = new ArrayList<>();
		 holidayCount = new ArrayList<>();
			setBtnShow(false);
			setBtnExport(false);
			setFullPrjFlag(false);
			setCustomerFlag(false);
			setAllPrjFlag(false);
			setNullPrjFlag(false);
			setPartialPrjFlag(false);
			autoSelectFlag = true;
		 
		
		if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag") != null) {
			Boolean backInquiryFlag = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag");
			if (backInquiryFlag) {
				HashMap<String, Object> detailData = (HashMap<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("detailData");
				
				this.tableHeaderList = (List<TableHeader>) detailData.get("tableHeaderList");
				this.teamList = (List<SummaryActivity>) detailData.get("teamList");
				this.summaryInfo = (ProjectSummaryInfo) detailData.get("summaryInfo");
			
				this.projectIDList = (List<ProjectSummaryInfo>) detailData.get("projectIDList");
				this.totalInfoList = (List<TotalPrjInfo>) detailData.get("totalInfoList");
				this.summaryInfoList = (List<ProjectSummaryInfo>) detailData.get("summaryInfoList");
				
				this.projectID = (String) detailData.get("projectID");
				this.startYear = (String) detailData.get("startYear");
				this.startMonth = (String) detailData.get("startMonth");
				this.endYear = (String) detailData.get("endYear");
				this.endMonth = (String) detailData.get("endMonth");
				this.years = (List<Integer>) detailData.get("years");
				this.months = (List<String>) detailData.get("months");
				this.customer = (String) detailData.get("customer");
				this.startDate = (String)detailData.get("startDate");
				this.endDate = (String)detailData.get("endDate");
				this.btnShow = (boolean)detailData.get("btnShow");
				this.btnExport = (boolean)detailData.get("btnShow");
				this.fullPrjFlag = (boolean)detailData.get("fullPrjFlag");
				this.allPrjFlag = (boolean)detailData.get("allPrjFlag");
				this.customerFlag = (boolean)detailData.get("customerFlag");
				this.nullPrjFlag = (boolean)detailData.get("nullPrjFlag");
				this.partialPrjFlag = (boolean)detailData.get("partialPrjFlag");
				this.clientOrganization = (List<String>)detailData.get("clientOrganization");
				this.fullResultList = (List<ProjectSummaryInfo>)detailData.get("fullResultList");
				this.partialResultList = (List<ProjectSummaryInfo>)detailData.get("partialResultList");
				this.allResultList = (List<ProjectSummaryInfo>)detailData.get("allResultList");
				this.customerResultList = (List<ProjectSummaryInfo>)detailData.get("customerResultList");
				this.nullResultList  = (List<ProjectSummaryInfo>)detailData.get("nullResultList");
			
				//this.searchResultCtl = true;
			}
		}else {
			load();
		}
		
	}
	
	
	public void load() {
		
		clientOrganization = projectService.findClientOrganization();
		summaryActivityService.findHolidayCount();
		 tableHeaderList = new ArrayList<>();
		 projectIDList = new ArrayList<>(); 
	 	setBtnShow(false);
	 	setBtnExport(false);
		setFullPrjFlag(false);
		setCustomerFlag(false);
		setAllPrjFlag(false);
		setNullPrjFlag(false);
		setPartialPrjFlag(false);
		autoSelectFlag = true;
			
		
	
		
	}

	public void show() {
		setBtnShow(false);
		setFullPrjFlag(false);
		setCustomerFlag(false);
		setAllPrjFlag(false);
		setNullPrjFlag(false);
		setPartialPrjFlag(false);
		tableHeaderList = new ArrayList<>();
		fullResultList = new ArrayList<>();
		partialResultList = new ArrayList<>();
		allResultList = new ArrayList<>();
		customerResultList = new ArrayList<>();
		nullResultList =  new ArrayList<>();
		
			
			searchPeriod(); 
			projectIDList = summaryActivityService.findProjectIDByFull(projectID,startDate,endDate,customer);
			Project prj =	projectService.findProject(projectID);
			
			// Search with ALL project
			if(projectID.equalsIgnoreCase("all")
					|| projectID.equals("all")){
				
				btnExport = true;
				btnShow = true;
				setAllPrjFlag(true);
				resultListByAll();
			}
			// Search with NULL project
			if(projectID.equals("-")){
			
			btnExport = true;
			btnShow = true;
			setNullPrjFlag(true);
			resultListByNull();
			
			}
			// Search with Customer name only
			if (projectID.equals("")) {
			
			btnExport = true;
			btnShow = true;
			setCustomerFlag(true);
			resultListByCustomer();
			}
			// Search with Full project
			if(prj != null){
			
				btnExport = true;
				btnShow = true;
				setFullPrjFlag(true);
				resultListByFull();
				
				
			}else{
				System.out.println("PROJECT ID LIST = " + projectIDList);
				if(!projectID.equals("-")
					&& !projectID.equalsIgnoreCase("all") && !projectID.equals("") ){
			
				System.out.println("// Search with Partial project");
				btnExport = true;
				btnShow = true;
				setPartialPrjFlag(true);
				resultListByPartial();
				
					}
			}
		
		
		

	
	}
	
	public void resultListByAll(){

		
		allResultList = new ArrayList<>();
		projectIDList  = new ArrayList<>();
		projectIDList = summaryActivityService
				.findByAllProject(startDate, endDate,customer);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				projectID, startDate,endDate);
		
		
		if (projectIDList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			tableHeaderList = new ArrayList<>();
			return;
		} else {
			for (ProjectSummaryInfo projectInfoList : projectIDList) {
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				if(projectInfoList.getProjectID().equals("9999")){
				projectInfo.setProjectID("-");
				}else{
				projectInfo.setProjectID(projectInfoList.getProjectID());
				}
				projectInfo.setProjectName(projectInfoList.getProjectName());
				if (projectInfoList.getCustomer() != null) {
					projectInfo.setCustomer(projectInfoList.getCustomer());
				} else {
					projectInfo.setCustomer("");
				}
				projectInfo.setTotalPlanMM(projectInfoList.getTotalPlanMM());
				projectInfo.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth(projectInfoList
						.getProjectID(),totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);
				allResultList.add(projectInfo);
			}
		}
		
	
	
		
	}
	public void resultListByCustomer(){
		

		customerResultList = new ArrayList<>();
		projectIDList  = new ArrayList<>();
		projectIDList = summaryActivityService
				.findByCustomer(startDate, endDate,customer);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				projectID, startDate,endDate);
		
		
		if (projectIDList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			tableHeaderList = new ArrayList<>();
			return;
		} else {
			for (ProjectSummaryInfo projectInfoList : projectIDList) {
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				if(projectInfoList.getProjectID().equals("9999")){
					projectInfo.setProjectID("-");	
				}else{
				projectInfo.setProjectID(projectInfoList.getProjectID());
				}
				projectInfo.setProjectName(projectInfoList.getProjectName());
				if (projectInfoList.getCustomer() != null) {
					projectInfo.setCustomer(projectInfoList.getCustomer());
				} else {
					projectInfo.setCustomer("");
				}
				projectInfo.setTotalPlanMM(projectInfoList.getTotalPlanMM());
				projectInfo.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth(projectInfoList
						.getProjectID(),totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);
				customerResultList.add(projectInfo);
			}
		}
	
	
	}
	
	public void resultListByNull(){


		nullResultList = new ArrayList<>();
		projectIDList  = new ArrayList<>();
		projectIDList = summaryActivityService
				.findProjectIDByNull(projectID, startDate, endDate,customer);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				"9999", startDate,endDate);
		
		
		if (projectIDList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			tableHeaderList = new ArrayList<>();
			return;
		} else {
			for (ProjectSummaryInfo projectInfoList : projectIDList) {
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				projectInfo.setProjectID(projectInfoList.getProjectID());
				projectInfo.setProjectName(projectInfoList.getProjectName());
				if (projectInfoList.getCustomer() != null) {
					projectInfo.setCustomer(projectInfoList.getCustomer());
				} else {
					projectInfo.setCustomer("");
				}
				projectInfo.setTotalPlanMM(projectInfoList.getTotalPlanMM());
				projectInfo.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth(projectInfoList
						.getProjectID(),totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);
				nullResultList.add(projectInfo);
			}
		}
	
	
	}

	public void resultListByPartial(){
		
		partialResultList = new ArrayList<>();
		projectIDList  = new ArrayList<>();
		projectIDList = summaryActivityService
				.findByPartialProject(projectID, startDate, endDate,customer);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				projectID, startDate,endDate);
	
		
		if (projectIDList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			tableHeaderList = new ArrayList<>();
			return;
		} else {
			for (ProjectSummaryInfo projectInfoList : projectIDList) {
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				projectInfo.setProjectID(projectInfoList.getProjectID());
				projectInfo.setProjectName(projectInfoList.getProjectName());
				if (projectInfoList.getCustomer() != null) {
					projectInfo.setCustomer(projectInfoList.getCustomer());
				} else {
					projectInfo.setCustomer("");
				}
				projectInfo.setTotalPlanMM(projectInfoList.getTotalPlanMM());
				projectInfo.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth(projectInfoList
						.getProjectID(),totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);
				partialResultList.add(projectInfo);
			}
		}
		
	
	}
	
	public void resultListByFull(){
		fullResultList = new ArrayList<>();
		projectIDList  = new ArrayList<>();
		projectIDList = summaryActivityService
				.findProjectIDByFull(projectID, startDate, endDate,customer);
		List<ManMonthInfo> manmonthdata = summaryActivityService
				.findManmonthByPartial(projectID, "",
						startDate,endDate);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				projectID, startDate,endDate);
		
		
		if (projectIDList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			tableHeaderList = new ArrayList<>();
			return;
		} else {
			for (ProjectSummaryInfo projectInfoList : projectIDList) {
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				List<TeamInfo> teamList = summaryActivityService
						.findDetailTeamByPartial(
								projectInfoList.getProjectID(), startDate,
								endDate);

				for (TeamInfo teamInfoList : teamList) {
					List<ManMonthInfo> manmonthList = new ArrayList<>();
					TeamInfo teaminfo = new TeamInfo();
					
					manmonthList = ManMonthData(
							projectInfoList.getProjectID(),
							teamInfoList.getTeanName(),
							manmonthdata);
					
					teaminfo.setTeanName(teamInfoList.getTeanName());
					teaminfo.setManmonthList(manmonthList);
				
					teaminfolist.add(teaminfo);
				}
				
				projectInfo.setProjectID(projectInfoList.getProjectID());
				projectInfo.setProjectName(projectInfoList.getProjectName());
				if (projectInfoList.getCustomer() != null) {
					projectInfo.setCustomer(projectInfoList.getCustomer());
				} else {
					projectInfo.setCustomer("");
				}
				projectInfo.setTotalPlanMM(projectInfoList.getTotalPlanMM());
				projectInfo.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth(projectInfoList
						.getProjectID(),totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);
				fullResultList.add(projectInfo);
			}
		}
	}

	public List<ManMonthInfo> ManMonthData(String projectId, String teanName,
			List<ManMonthInfo> findResultData) {
		List<ManMonthInfo> manmonthList = new ArrayList<>();
		List<ManMonthInfo> findResult = new ArrayList<>();
	
		for(ManMonthInfo mminfo: findResultData){
			if(mminfo.getProjectId().equals(projectId) && mminfo.getTeamname().equals(teanName)){
			ManMonthInfo mm = new ManMonthInfo();
			mm.setProjectId(mminfo.getProjectId());
			mm.setTeamname(mminfo.getTeamname());
			mm.setHour(mminfo.getHour());
			mm.setManmonth(mminfo.getManmonth());
			mm.setYear(mminfo.getYear());
			mm.setMonth(mminfo.getMonth());
			mm.setMembers(mminfo.getMembers());
			findResult.add(mm);
			}
			
		}
		
		temp: for (TableHeader th : tableHeaderList) {
			ManMonthInfo manmonthInfo = new ManMonthInfo();
			for (ManMonthInfo mminfo : findResult) {
				
				if(th.getYear().equals(mminfo.getYear()) && WhmUtilities.findMonthDigit(th.getMonth()).equals(mminfo.getMonth())){
					manmonthInfo.setMembers(mminfo.getMembers());
					manmonthInfo.setHour(mminfo.getHour());
					manmonthInfo.setManmonth(mminfo.getManmonth());
					manmonthList.add(manmonthInfo);
					continue temp;
				}
			}
			manmonthInfo.setMembers(0);
			manmonthInfo.setHour(0.0);
			manmonthInfo.setManmonth(0.0);
			manmonthList.add(manmonthInfo);
		}
		
		
	
	return manmonthList;
}
		
public List<TotalManMonthInfo> findTotalManMonth(String projectId,List<TotalManMonthInfo> totalData) {
		
		List<TotalManMonthInfo> totalInfoListByPartial = new ArrayList<>();
		List<TotalManMonthInfo> totalResultData = new ArrayList<>();
			for(TotalManMonthInfo total:totalData){
				if(projectId.equals(total.getProjectId())){
					TotalManMonthInfo totalinfo = new TotalManMonthInfo();
					totalinfo.setProjectId(total.getProjectId());
					totalinfo.setTotal_hour(total.getTotal_hour());
					totalinfo.setTotal_manmonth(total.getTotal_manmonth());
					totalinfo.setYear(total.getYear());
					totalinfo.setMonth(total.getMonth());
					totalinfo.setTotal_members(total.getTotal_members());
					totalResultData.add(totalinfo);
				}
			}
			
			temp: for (TableHeader th : tableHeaderList) {
				TotalManMonthInfo totalInfo = new TotalManMonthInfo();
				for (TotalManMonthInfo totalmminfo : totalResultData) {
					
					if(th.getYear().equals(totalmminfo.getYear()) && WhmUtilities.findMonthDigit(th.getMonth()).equals(totalmminfo.getMonth())){
						totalInfo.setTotal_hour(totalmminfo.getTotal_hour());
						totalInfo.setTotal_manmonth(totalmminfo.getTotal_manmonth());
						totalInfo.setTotal_members(totalmminfo.getTotal_members());
						totalInfoListByPartial.add(totalInfo);
						continue temp;
					}
				}
				totalInfo.setTotal_hour(0.0);
				totalInfo.setTotal_manmonth(0.0);
				totalInfo.setTotal_members(0);
				totalInfoListByPartial.add(totalInfo);

		}

		return totalInfoListByPartial;
	}
	
	
	public void selectProject() {
			btnExport = false;
		if(tableHeaderList.size()>0 || projectIDList.size() > 0){
			
			
			tableHeaderList = null;
			projectIDList = null;
			summaryInfoList = null;
			teamList = null;
			totalInfoList = null;
			
			
			tableHeaderList = new ArrayList<>();
			projectIDList = new ArrayList<>();
			summaryInfoList = new ArrayList<>();
			teamList = new ArrayList<>();
			totalInfoList = new ArrayList<>();
			fullPrjFlag = false;
			partialPrjFlag = false;
			allPrjFlag = false;
			nullPrjFlag = false;
			customerFlag = false;
			btnShow = false;
			autoSelectFlag = false;
			
		} 
		
	
		String currentProjectID;
		years = new ArrayList<>();
		months = new ArrayList<>();	
		
		String tmpStartYear;
		String tmpStartMonth;
		String tmpEndYear;
		String tmpEndMonth;
		Date minDate = null;
		Date maxDate =null;
		totalInfoList = new ArrayList<>();
		
		summaryInfo = new ProjectSummaryInfo();		

		SummaryActivity sa = new SummaryActivity();
		
		if((projectID == null || projectID.equals("")) && (customer == null || customer.equals("") )){
			minDate = new Date();
			maxDate = new Date();
			
			fullPrjFlag = false;
			partialPrjFlag = false;
			allPrjFlag = false;
			nullPrjFlag = false;
			customerFlag = false;
			btnShow = false;
			String errorMessage = "Please fill ProjectID or Customer!";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "",
							errorMessage));
			return;
		}
		
	
		if (projectID != null && !projectID.equals(null) && projectID !="") {
			minDate = new Date();
			maxDate = new Date();
			
			if(projectID == "-" || projectID.equals("-")){
				minDate = summaryActivityService.findMinDateByNullPrjID();
				maxDate = summaryActivityService.findMaxDateByNullPrjID();
				
			}else if(projectID == "all" || projectID.equalsIgnoreCase("all")){
				
				if(customer != null && !customer.equals(null) && customer != "" ){
					Object[] tmpMin,tmpMax;
					tmpMin = summaryActivityService.findMinDateByCustomer(customer);
					tmpMax = summaryActivityService.findMaxDateByCustomer(customer);
					
					minDate = (Date) tmpMin[0];
					maxDate = (Date) tmpMax[0];
					
				}else{
					minDate = summaryActivityService.findMinDate();
					maxDate = summaryActivityService.findMaxDate();
					
				}
				
			}else{
				Project prj =	projectService.findProject(projectID);
				// For choosing ProjectID list with Period
				if(prj != null){
				currentProjectID = projectID.trim();
				Project projectID = new Project();
				projectID.setId(currentProjectID);
			
				sa.setProject_id(projectID.getId());
				
				minDate = summaryActivityService.findMinDateByProjectIDFull(sa);
				maxDate = summaryActivityService.findMaxDateByProjectIDFull(sa);
				}else{
					currentProjectID = projectID.trim();
					Project projectID = new Project();
					projectID.setId(currentProjectID);
				
					sa.setProject_id(projectID.getId());
					
					minDate = summaryActivityService.findMinDateByProjectID(sa);
					maxDate = summaryActivityService.findMaxDateByProjectID(sa);	
				}
				
			}
						
			if (minDate == null || maxDate == null || minDate.equals(null) || maxDate.equals(null)) {
				String errorMessage = "There is no search result for the given ProjectID.";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "",
								errorMessage));
				return;
			}
					
			}else if(customer != null){
				 minDate = new Date();
				 maxDate = new Date();	
				
			Object[] tmpMin,tmpMax;
			tmpMin = summaryActivityService.findMinDateByCustomer(customer);
			tmpMax = summaryActivityService.findMaxDateByCustomer(customer);
			
			minDate = (Date) tmpMin[0];
			maxDate = (Date) tmpMax[0];
		
		}
	
		if (minDate == null || maxDate == null || minDate.equals(null) || maxDate.equals(null)) {
			String errorMessage = "There is no search result for the given ProjectID.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "",
							errorMessage));
			return;
		}
		System.out.println("MIN DATE = " + minDate);
		System.out.println("MAX DATE = " + maxDate);
		
		if (minDate != null && maxDate != null && !minDate.equals(null) && !maxDate.equals(null)) {
						
			setBtnShow(true);
			tmpStartYear = WhmUtilities.getYearFromDate(minDate);
			tmpStartMonth = WhmUtilities.getMonthFromDate(minDate);
			tmpEndYear = WhmUtilities.getYearFromDate(maxDate);
			tmpEndMonth = WhmUtilities.getMonthFromDate(maxDate);


			for (int i = Integer.parseInt(tmpStartYear); i <= Integer
					.parseInt(tmpEndYear); i++) {
				years.add(i);
			}

			if (Integer.parseInt(tmpEndYear) - Integer.parseInt(tmpStartYear) > 0) {
				months = WhmUtilities.getAllMonths();
			} else {

				List<Integer> tmpMonths = new ArrayList<Integer>();
				for (int i = Integer.parseInt(tmpStartMonth); i <= Integer
						.parseInt(tmpEndMonth); i++) {
					tmpMonths.add(i);
				}
				if (!months.equals("")) {
					months = WhmUtilities.convertDigitToMonth(tmpMonths);
				}
			}

			startYear = tmpStartYear;
			endYear = tmpEndYear;
			startMonth = WhmUtilities.findMonthByDigit(tmpStartMonth);
			endMonth = WhmUtilities.findMonthByDigit(tmpEndMonth);
			if(!autoSelectFlag){
			
			}

		}
		
	}
	
	public void searchPeriod() {
		
		// to check validation Start Year/Month < End Year/Month
		int noMonth;
		
		// Date convert and Get last day of EndMonth
		String tmpStartMonth =WhmUtilities.findMonthDigit(startMonth);
		String tmpEndMonth =WhmUtilities.findMonthDigit(endMonth);
		String tmpStartYear = startYear;
		String tmpEndYear = endYear;
		
		String tmpendDate = endYear.concat("-").concat(tmpEndMonth).concat("-").concat("01");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate = null;
		Calendar calendar = Calendar.getInstance();
		try {
			convertedDate =  (Date) dateFormat.parse(tmpendDate);
			
			calendar.setTime(convertedDate);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		startDate = tmpStartYear.concat("-").concat(tmpStartMonth).concat("-").concat("01");
		endDate = tmpEndYear.concat("-").concat(tmpEndMonth).concat("-").concat(String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)));
			
		// for table header
		if (Integer.parseInt(tmpStartYear) == Integer.parseInt(tmpEndYear)) {
			noMonth = Integer.parseInt(tmpEndMonth);
		} else
			noMonth = 12;
		
		for (int j = Integer.parseInt(tmpStartYear); j <= Integer
				.parseInt(tmpEndYear); j++) {

			for (int k = Integer.parseInt(tmpStartMonth); k <= noMonth; k++) {
				TableHeader tempDetailInfo = new TableHeader();

				tempDetailInfo.setYear(String.valueOf(j));
				tempDetailInfo.setMonth(WhmUtilities.findMonthByDigit(String
						.valueOf(k)));
				
				tableHeaderList.add(tempDetailInfo);
				
			}

			tmpStartMonth = "1";
			if (Integer.parseInt(endYear) == j + 1) {
				noMonth = Integer.parseInt(tmpEndMonth);
			}
			
		}
		//System.out.println("table header list size(working hour managemnt project bean : " + tableHeaderList.size());
		
	}
	
	public String detail(){
		HashMap<String, Object> detailData = new HashMap<>();
		detailData.put("tableHeaderList", this.tableHeaderList);
		detailData.put("projectIDList", this.projectIDList);
		detailData.put("projectID", this.projectID);
		detailData.put("startYear", this.startYear);
		detailData.put("startMonth", this.startMonth);
		detailData.put("endYear", this.endYear);
		detailData.put("endMonth", this.endMonth);
		detailData.put("customer", this.customer);
		detailData.put("clientOrganization", this.clientOrganization);
		detailData.put("startDate", this.startDate);
		detailData.put("endDate", this.endDate);
		detailData.put("years", this.years);
		detailData.put("months", this.months);
		detailData.put("btnShow", this.btnShow);
		detailData.put("btnExport", this.btnExport);
		detailData.put("fullPrjFlag", this.fullPrjFlag);
		detailData.put("allPrjFlag", this.allPrjFlag);
		detailData.put("customerFlag", this.customerFlag);
		detailData.put("nullPrjFlag", this.nullPrjFlag);
		detailData.put("partialPrjFlag", this.partialPrjFlag);
		detailData.put("fullResultList", this.fullResultList);
		detailData.put("partialResultList", this.partialResultList);
		detailData.put("nullResultList", this.nullResultList);
		detailData.put("customerResultList", this.customerResultList);
		detailData.put("allResultList", this.allResultList);
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("detailData", detailData);
		return "/faces/view/mgtreports/MNR006.xhtml?faces-redirect=true";
	}
	
	public void export() throws Exception {
		
		if (exportProjectReport()) {

			File file = new File(getDownloadFileName());
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] bfile = new byte[(int) file.length()];

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			fileInputStream.read(bfile);
			fileInputStream.close();

			InputStream stream = new ByteArrayInputStream(bfile);
			setDownload(new DefaultStreamedContent(stream, externalContext.getMimeType(file.getName()), getExportFileName()));

		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data to export."));
		}
	}

	private boolean exportProjectReport() throws FileNotFoundException,
	IOException, HPSFException {

StringBuilder name = new StringBuilder();
StringBuilder filename = new StringBuilder();
;

	filename = name.append("(DAT)_Working Hour Management Project").append(
			".xlsx");


if (projectIDList.size() <= 0 && !projectID.equals("-")) {
	setDownloadFileName("");
	setExportFileName("");
	return false;
} else {
	exportDatatoExcel(filename);
	return true;
}
}
	
	private void exportDatatoExcel(StringBuilder filename) throws FileNotFoundException, HPSFException {
		int startRow = 0;
		int startColumn = 0;
		XSSFWorkbook workbook;
		
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		ServletContext context = (ServletContext) externalContext.getContext();
		
		try {
		// Template Excel File
		File file = new File(context.getRealPath("/template/WorkingHourManagementProjectTemplate.xlsx"));
		FileInputStream fIP = new FileInputStream(file);
		workbook = new XSSFWorkbook(fIP);

		File outputFile = new File(context.getRealPath("/upload/" + filename));
		FileOutputStream outputData = new FileOutputStream(outputFile);

		XSSFSheet sheetName = workbook.getSheet("Sheet1");
	
	    
	    CellStyle headerStyle = workbook.createCellStyle();
	  
	    headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    headerStyle.setVerticalAlignment(CellStyle.ALIGN_FILL);
	    headerStyle.setWrapText(true);
	    
	    
		Row row = sheetName.getRow(startRow);
		
		Row rowTwo = sheetName.getRow(startRow + 1);
		Cell headCell = row.getCell(0);
		headerStyle = headCell.getCellStyle();
		
		if(fullPrjFlag){
			sheetName.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
	    	startColumn = 1;
	    	Cell cell = row.createCell(5);
			cell.setCellValue("Team");
			cell.setCellStyle(headerStyle);
		
	    }
		
		for (int i = 0; i < tableHeaderList.size(); i++) {
			sheetName.addMergedRegion(new CellRangeAddress(0, 0, startColumn + 5, startColumn + 7));
			
			Cell yearMonthCell = row.createCell(startColumn+5);
			yearMonthCell.setCellValue(tableHeaderList.get(i).getYear()+"-"+tableHeaderList.get(i).getMonth());
			yearMonthCell.setCellStyle(headerStyle);
			
			Cell cell = rowTwo.createCell(startColumn + 5);
			cell.setCellValue("Members");
			cell.setCellStyle(headerStyle);
			
			cell = rowTwo.createCell(startColumn + 6);
			cell.setCellValue("Hour");
			cell.setCellStyle(headerStyle);
			
			cell = rowTwo.createCell(startColumn + 7);
			cell.setCellValue("M-M");
			cell.setCellStyle(headerStyle);
			
			startColumn = startColumn + 3;
		}
		
		CellStyle cellStyle = workbook.createCellStyle();
	    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cellStyle.setWrapText(true);
	    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    
	    if(fullPrjFlag){
	    exportExcelFull(rowTwo, sheetName, startColumn, cellStyle,workbook);
	    }else if (allPrjFlag){
	    exportExcelAll(rowTwo, sheetName, startColumn, cellStyle,workbook);	
	    }else if (nullPrjFlag){
	    exportExcelNullProject(rowTwo, sheetName, startColumn, cellStyle,workbook);		
	    }else if (customerFlag){
	    	exportExcelCustomer(rowTwo, sheetName, startColumn, cellStyle,workbook);	
	    }else if(partialPrjFlag){
	    	exportExcelPartial(rowTwo, sheetName, startColumn, cellStyle,workbook);	
	    }
	    if(fullPrjFlag){
	    sheetName.createFreezePane(6, 2);	
	    }else{
	    sheetName.createFreezePane(5, 2);
	    }
	    workbook.write(outputData);
		outputData.flush();
		outputData.close();
		
		setDownloadFileName(context.getRealPath("/upload/" + filename));
		setExportFileName(filename.toString());
		
		}catch (IOException e) {
			throw new HPSFException(e.getMessage());
		}
	}
	
	public void exportExcelPartial(Row row, XSSFSheet sheetName, int startColumn,
			CellStyle cellStyle,XSSFWorkbook workbook) {


		int count = 0;
		

		for (ProjectSummaryInfo psi : partialResultList) {
			
					row = sheetName.createRow(count + 2);

					int start_column = 4;
				
					Cell cell = row.createCell(0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(psi.getProjectID());

					cell = row.createCell(1);
					cell.setCellValue(psi.getProjectName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(2);
					cell.setCellValue(psi.getCustomer());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(3);
					cell.setCellValue(psi.getTotalPlanMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

					cell = row.createCell(4);
					cell.setCellValue(psi.getTotalActualMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

					

				
					count++;
					for (TotalManMonthInfo manonth : psi.getTotalManMonthInfo()) {
						cell = row.createCell(start_column + 1);
						cell.setCellValue(manonth.getTotal_members());
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell(start_column + 2);
						cell.setCellValue(manonth.getTotal_hour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getTotal_manmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
						
						start_column = start_column + 3;
					}

}

		
	
		
	}
	
	public void exportExcelFull(Row row, XSSFSheet sheetName, int startColumn,
			CellStyle cellStyle,XSSFWorkbook workbook) {

		int count = 0;
		sheetName.addMergedRegion(new CellRangeAddress(2,
				fullResultList.get(0).getTeamInfo().size() + 2, 0, 0));
		sheetName.addMergedRegion(new CellRangeAddress(2,
				fullResultList.get(0).getTeamInfo().size() + 2, 1, 1));
		sheetName.addMergedRegion(new CellRangeAddress(2,
				fullResultList.get(0).getTeamInfo().size() + 2, 2, 2));
		sheetName.addMergedRegion(new CellRangeAddress(2,
				fullResultList.get(0).getTeamInfo().size() + 2, 3, 3));
		sheetName.addMergedRegion(new CellRangeAddress(2,
				fullResultList.get(0).getTeamInfo().size() + 2, 4, 4));
		for (ProjectSummaryInfo psi : fullResultList) {
			Row totalrow = sheetName.createRow(psi.getTeamInfo().size() + 2);
			int total_startcolumn = 5;
			
			for (TeamInfo teaminfo : psi.getTeamInfo()) {
			
					row = sheetName.createRow(count + 2);

					int start_column = 5;
					
					Cell cell = row.createCell(0);
					cell.setCellValue(psi.getProjectID());
					cell.setCellStyle(cellStyle);
					

					cell = row.createCell(1);
					cell.setCellValue(psi.getProjectName());
					cell.setCellStyle(cellStyle);
					

					cell = row.createCell(2);
					cell.setCellValue(psi.getCustomer());
					cell.setCellStyle(cellStyle);
					

					cell = row.createCell(3);
					cell.setCellValue(psi.getTotalPlanMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
					

					cell = row.createCell(4);
					cell.setCellValue(psi.getTotalActualMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));


					cell = row.createCell(5);
					cell.setCellValue(teaminfo.getTeanName());
					cell.setCellStyle(cellStyle);

				
					count++;
					for (ManMonthInfo manonth : teaminfo.getManmonthList()) {
						cell = row.createCell(start_column + 1);
						cell.setCellValue(manonth.getMembers());
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell(start_column + 2);
						cell.setCellValue(manonth.getHour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getManmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						start_column = start_column + 3;
					}

				
				
			}
			
			for (TotalManMonthInfo totalinfo : psi.getTotalManMonthInfo()) {

				Cell cell = totalrow.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(4);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(5);
				cell.setCellValue("Total");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(total_startcolumn + 1);
				cell.setCellValue(totalinfo.getTotal_members());
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(total_startcolumn + 2);
				cell.setCellValue(totalinfo.getTotal_hour());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				cell = totalrow.createCell(total_startcolumn + 3);
				cell.setCellValue(totalinfo.getTotal_manmonth());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				total_startcolumn = total_startcolumn + 3;
			}

		}

		
	}

	
	public void exportExcelAll(Row row, XSSFSheet sheetName, int startColumn,
			CellStyle cellStyle,XSSFWorkbook workbook) {



		int count = 0;
		
		for (ProjectSummaryInfo psi : allResultList) {
			
					row = sheetName.createRow(count + 2);

					int start_column = 4;
				
					Cell cell = row.createCell(0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(psi.getProjectID());

					cell = row.createCell(1);
					cell.setCellValue(psi.getProjectName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(2);
					cell.setCellValue(psi.getCustomer());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(3);
					cell.setCellValue(psi.getTotalPlanMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

					cell = row.createCell(4);
					cell.setCellValue(psi.getTotalActualMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

					count++;
					for (TotalManMonthInfo manonth : psi.getTotalManMonthInfo()) {
						cell = row.createCell(start_column + 1);
						cell.setCellValue(manonth.getTotal_members());
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell(start_column + 2);
						cell.setCellValue(manonth.getTotal_hour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getTotal_manmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						start_column = start_column + 3;
					}

}

		
	
		
	
	}
	
	public void exportExcelCustomer(Row row, XSSFSheet sheetName, int startColumn,
			CellStyle cellStyle,XSSFWorkbook workbook) {



		int count = 0;

		for (ProjectSummaryInfo psi : customerResultList) {
			
					row = sheetName.createRow(count + 2);

					int start_column = 4;
				
					Cell cell = row.createCell(0);
					cell.setCellValue(psi.getProjectID());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(1);
					cell.setCellValue(psi.getProjectName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(2);
					cell.setCellValue(psi.getCustomer());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(3);
					cell.setCellValue(psi.getTotalPlanMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

					cell = row.createCell(4);
					cell.setCellValue(psi.getTotalActualMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
					
					count++;
					for (TotalManMonthInfo manonth : psi.getTotalManMonthInfo()) {
						cell = row.createCell(start_column + 1);
						cell.setCellValue(manonth.getTotal_members());
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell(start_column + 2);
						cell.setCellValue(manonth.getTotal_hour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
						
						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getTotal_manmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						start_column = start_column + 3;
					}

}

		
	
		
	
	}
	
	public void exportExcelNullProject(Row row, XSSFSheet sheetName, int startColumn,
			CellStyle cellStyle,XSSFWorkbook workbook) {



		int count = 0;


		for (ProjectSummaryInfo psi : nullResultList) {
			
					row = sheetName.createRow(count + 2);

					int start_column = 4;
				
					Cell cell = row.createCell(0);
					cell.setCellValue("-");
					cell.setCellStyle(cellStyle);

					cell = row.createCell(1);
					cell.setCellValue(psi.getProjectName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(2);
					cell.setCellValue(psi.getCustomer());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(3);
					cell.setCellValue(psi.getTotalPlanMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

					cell = row.createCell(4);
					cell.setCellValue(psi.getTotalActualMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

					

				
					count++;
					for (TotalManMonthInfo manonth : psi.getTotalManMonthInfo()) {
						cell = row.createCell(start_column + 1);
						cell.setCellValue(manonth.getTotal_members());
						cell.setCellStyle(cellStyle);
						
						cell = row.createCell(start_column + 2);
						cell.setCellValue(manonth.getTotal_hour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getTotal_manmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						start_column = start_column + 3;
					}

}

		
	
		
	
	}
	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public IDailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public void setDailyActivityService(IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public ISummaryActivityService getSummaryActivityService() {
		return summaryActivityService;
	}

	public void setSummaryActivityService(
			ISummaryActivityService summaryActivityService) {
		this.summaryActivityService = summaryActivityService;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<String> getClientOrganization() {
		return clientOrganization;
	}

	public void setClientOrganization(List<String> clientOrganization) {
		this.clientOrganization = clientOrganization;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
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

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public boolean isBtnShow() {
		return btnShow;
	}

	public void setBtnShow(boolean btnShow) {
		this.btnShow = btnShow;
	}

	public boolean isFullPrjFlag() {
		return fullPrjFlag;
	}

	public void setFullPrjFlag(boolean fullPrjFlag) {
		this.fullPrjFlag = fullPrjFlag;
	}

	public boolean isAllPrjFlag() {
		return allPrjFlag;
	}

	public void setAllPrjFlag(boolean allPrjFlag) {
		this.allPrjFlag = allPrjFlag;
	}

	public boolean isNullPrjFlag() {
		return nullPrjFlag;
	}

	public void setNullPrjFlag(boolean nullPrjFlag) {
		this.nullPrjFlag = nullPrjFlag;
	}

	public boolean isCustomerFlag() {
		return customerFlag;
	}

	public void setCustomerFlag(boolean customerFlag) {
		this.customerFlag = customerFlag;
	}

	public ProjectSummaryInfo getSummaryInfo() {
		return summaryInfo;
	}

	public void setSummaryInfo(ProjectSummaryInfo summaryInfo) {
		this.summaryInfo = summaryInfo;
	}

	public List<ProjectSummaryInfo> getSummaryInfoList() {
		return summaryInfoList;
	}

	public void setSummaryInfoList(List<ProjectSummaryInfo> summaryInfoList) {
		this.summaryInfoList = summaryInfoList;
	}


	public List<TableHeader> getTableHeaderList() {
		return tableHeaderList;
	}

	public void setTableHeaderList(List<TableHeader> tableHeaderList) {
		this.tableHeaderList = tableHeaderList;
	}

	public List<SummaryActivity> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<SummaryActivity> teamList) {
		this.teamList = teamList;
	}

	public List<TotalPrjInfo> getTotalInfoList() {
		return totalInfoList;
	}

	public void setTotalInfoList(List<TotalPrjInfo> totalInfoList) {
		this.totalInfoList = totalInfoList;
	}

	public List<ProjectSummaryInfo> getProjectIDList() {
		return projectIDList;
	}

	public void setProjectIDList(List<ProjectSummaryInfo> projectIDList) {
		this.projectIDList = projectIDList;
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

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public boolean isAutoSelectFlag() {
		return autoSelectFlag;
	}

	public void setAutoSelectFlag(boolean autoSelectFlag) {
		this.autoSelectFlag = autoSelectFlag;
	}



	public boolean isPartialPrjFlag() {
		return partialPrjFlag;
	}

	public void setPartialPrjFlag(boolean partialPrjFlag) {
		this.partialPrjFlag = partialPrjFlag;
	}

	public boolean isPartialPrjExportFlag() {
		return partialPrjExportFlag;
	}

	public void setPartialPrjExportFlag(boolean partialPrjExportFlag) {
		this.partialPrjExportFlag = partialPrjExportFlag;
	}


	public List<ProjectSummaryInfo> getFullResultList() {
		return fullResultList;
	}


	public void setFullResultList(List<ProjectSummaryInfo> fullResultList) {
		this.fullResultList = fullResultList;
	}


	public List<ProjectSummaryInfo> getPartialResultList() {
		return partialResultList;
	}


	public void setPartialResultList(List<ProjectSummaryInfo> partialResultList) {
		this.partialResultList = partialResultList;
	}


	public List<ProjectSummaryInfo> getNullResultList() {
		return nullResultList;
	}


	public void setNullResultList(List<ProjectSummaryInfo> nullResultList) {
		this.nullResultList = nullResultList;
	}


	public List<ProjectSummaryInfo> getCustomerResultList() {
		return customerResultList;
	}


	public void setCustomerResultList(List<ProjectSummaryInfo> customerResultList) {
		this.customerResultList = customerResultList;
	}


	public List<ProjectSummaryInfo> getAllResultList() {
		return allResultList;
	}


	public void setAllResultList(List<ProjectSummaryInfo> allResultList) {
		this.allResultList = allResultList;
	}


	public boolean isBtnExport() {
		return btnExport;
	}


	public void setBtnExport(boolean btnExport) {
		this.btnExport = btnExport;
	}

	
	

}
