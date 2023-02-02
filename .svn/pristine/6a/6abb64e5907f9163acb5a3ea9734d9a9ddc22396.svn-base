/***************************************************************************************
 * @author Htet Wai Yum
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.dat.whm.summaryactivity.service.interfaces.ISummaryActivityService;
import com.dat.whm.web.common.TableHeader;
import com.dat.whm.web.common.WhmUtilities;

@ManagedBean(name = "WorkingHourManagementProjectDetailBean")
@ViewScoped
public class WorkingHourManagementProjectDetailBean {

	@ManagedProperty(value = "#{ProjectService}")
	private IProjectService projectService;

	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;

	@ManagedProperty(value = "#{SummaryActivityService}")
	private ISummaryActivityService summaryActivityService;

	private List<TableHeader> tableHeaderList;

	private List<ProjectSummaryInfo> projectIDList;
	
	private List<ProjectSummaryInfo> fullResultList;
	private List<ProjectSummaryInfo> partialResultList;
	private List<ProjectSummaryInfo> nullResultList;
	private List<ProjectSummaryInfo> customerResultList;
	private List<ProjectSummaryInfo> allResultList;

	// full project information
	private List<ProjectSummaryInfo> fullPrjResultList;
	private boolean fullPrjFlag = false;
	private boolean fullPrjExportFlag = false;

	// partial project information
	private List<ProjectSummaryInfo> PartialPrjResultList;
	private boolean partialPrjFlag = false;
	private boolean allPrjExportFlag = false;

	// null project information
	private List<ProjectSummaryInfo> nullPrjResultList;
	private boolean nullPrjFlag = false;
	private boolean nullPrjExportFlag = false;

	// all project information
	private List<ProjectSummaryInfo> allPrjResultList;

	private boolean allPrjFlag = false;
	private boolean partialPrjExportFlag = false;

	// customer information
	private List<String> clientOrganization;
	private List<ProjectSummaryInfo> customerPrjResultList;
	private boolean customerFlag = false;
	private boolean customerPrjExportFlag = false;

	private HashMap<String, Object> detailData;

	private String startYear;
	private String endYear;
	private String startMonth;
	private String endMonth;
	private List<Integer> years;
	private List<String> months;
	private String customer;
	private String startDate;
	private String endDate;
	private String projectID;
	private boolean btnShow;
	private boolean btnExport;
	

	// Export excel
	private DefaultStreamedContent download;
	private String downloadFileName;
	private String exportFileName;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		System.out.println("INIT METHOD");
		tableHeaderList = new ArrayList<>();

		projectIDList = new ArrayList<>();

		fullPrjResultList = new ArrayList<>();
		PartialPrjResultList = new ArrayList<>();
		nullPrjResultList = new ArrayList<>();
		customerPrjResultList = new ArrayList<>();

		allPrjResultList = new ArrayList<>();

		startDate = new String();
		endDate = new String();
		projectID = new String();
		customer = new String();
		clientOrganization = new ArrayList<>();

		this.detailData = (HashMap<String, Object>) FacesContext
				.getCurrentInstance().getExternalContext().getFlash()
				.get("detailData");
		this.tableHeaderList = (List<TableHeader>) detailData
				.get("tableHeaderList");
		this.projectIDList = (List<ProjectSummaryInfo>) detailData.get("projectIDList");
		this.projectID = (String) detailData.get("projectID");
		this.customer = (String) detailData.get("customer");
		this.clientOrganization = (List<String>) detailData
				.get("clientOrganization");
		this.startDate = (String) detailData.get("startDate");
		this.endDate = (String) detailData.get("endDate");
		this.fullResultList = (List<ProjectSummaryInfo>) detailData.get("fullResultList");
		this.partialResultList = (List<ProjectSummaryInfo>) detailData.get("partialResultList");
		this.allResultList = (List<ProjectSummaryInfo>) detailData.get("allResultList");
		this.nullResultList = (List<ProjectSummaryInfo>) detailData.get("nullResultList");
		this.customerResultList = (List<ProjectSummaryInfo>) detailData.get("customerResultList");
		this.btnExport = (boolean) detailData.get("btnExport");
		
		load();

	}

	public void load() {
		System.out.println("LOAD METHOD");
		List<Object[]> dataList = new ArrayList<>();
		Project prj = projectService.findProject(projectID);
		// Search with customer name
		if (projectID.equals("")) {
			if (!customer.equals(null) || !customer.equals("")) {
				System.out
						.println("%%%%%%%%%%%%%%%Search by Only Customer%%%%%%%%%%%%%%%%");
				btnShow = true;
				customerFlag = true;
				dataList = new ArrayList<>();
				dataList = summaryActivityService.findDetailProjectByCustomer(
						customer.trim(), startDate, endDate);

				if (checkResultList(dataList)) {
					resultListByCustomer();

					setCustomerFlag(true);
					setCustomerPrjExportFlag(true);
				}

			}

		} else {
			// Search with full project
			if (projectIDList.size() == 1 && prj != null
					&& !projectID.equals("-") && !projectID.equals("")) {
				System.out
						.println("%%%%%%%%%%%%%%%Search by full%%%%%%%%%%%%%%%%");
				btnShow = true;
				fullPrjFlag = true;

				resultListByFull();

				setFullPrjFlag(true);
				setFullPrjExportFlag(true);
			}
			// Search with partial project
			else if (projectIDList.size() >= 1 && prj == null
					&& !projectID.equals("-")
					&& !projectID.equalsIgnoreCase("all")) {

				System.out
						.println("%%%%%%%%%%%%%%%Search by partial%%%%%%%%%%%%%%%");
				btnShow = true;
				partialPrjFlag = true;
				System.out.println("PARTIAL PROJECT SIZE = "+PartialPrjResultList.size());
				resultListByPartial();

				setPartialPrjFlag(true);
				setPartialPrjExportFlag(true);
			}
			// Search with null project
			else if (projectID.equals("-")) {

				System.out
						.println("%%%%%%%%%%%%%%%Search by Null%%%%%%%%%%%%%%%");
				btnShow = true;
				nullPrjFlag = true;

				resultListByNull();

				setNullPrjFlag(true);
				setNullPrjExportFlag(true);
			}
			// Search with all project
			else if (projectID.equalsIgnoreCase("all")
					|| projectID.equals("all")) {

				System.out
						.println("%%%%%%%%%%%%%%%Search by All%%%%%%%%%%%%%%%");
				btnShow = true;
				allPrjFlag = true;
				dataList = new ArrayList<>();
				dataList = summaryActivityService.findDetailProjectByAll(
						startDate, endDate);

				if (checkResultList(dataList)) {
					resultListByAll();

					setAllPrjFlag(true);
					setAllPrjExportFlag(true);
				}

			}
		}
	}

	public boolean checkResultList(List<Object[]> checkList) {

		if (checkList.size() <= 0) {
			String errorMessage = "There is no record";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							errorMessage));
			return false;
		} else {

			System.out.println("Result List Size = " + checkList.size());

			return true;
		}
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
					totalResultData.add(totalinfo);
				}
			}
			
			temp: for (TableHeader th : tableHeaderList) {
				TotalManMonthInfo totalInfo = new TotalManMonthInfo();
				for (TotalManMonthInfo totalmminfo : totalResultData) {
					
					if(th.getYear().equals(totalmminfo.getYear()) && WhmUtilities.findMonthDigit(th.getMonth()).equals(totalmminfo.getMonth())){
						totalInfo.setTotal_hour(totalmminfo.getTotal_hour());
						totalInfo.setTotal_manmonth(totalmminfo.getTotal_manmonth());
						totalInfoListByPartial.add(totalInfo);
						continue temp;
					}
				}
				totalInfo.setTotal_hour(0.0);
				totalInfo.setTotal_manmonth(0.0);
				totalInfoListByPartial.add(totalInfo);

		}

		return totalInfoListByPartial;
	}

	public List<ManMonthInfo> ManMonthData(String projectId, String teanName,
			String staffId,List<ManMonthInfo> findResultData) {
		List<ManMonthInfo> manmonthList = new ArrayList<>();
		List<ManMonthInfo> findResult = new ArrayList<>();
		System.out.println("**************MAN MONTH TOTAL SIZE "+ findResultData.size());
		for(ManMonthInfo mminfo: findResultData){
			if(mminfo.getProjectId().equals(projectId) && mminfo.getTeamname().equals(teanName) && mminfo.getUsername().equals(staffId)){
			ManMonthInfo mm = new ManMonthInfo();
			mm.setProjectId(mminfo.getProjectId());
			mm.setTeamname(mminfo.getTeamname());
			mm.setUsername(mminfo.getUsername());
			mm.setHour(mminfo.getHour());
			mm.setManmonth(mminfo.getManmonth());
			mm.setYear(mminfo.getYear());
			mm.setMonth(mminfo.getMonth());
			findResult.add(mm);
			}
			
		}
		
		temp: for (TableHeader th : tableHeaderList) {
			ManMonthInfo manmonthInfo = new ManMonthInfo();
			for (ManMonthInfo mminfo : findResult) {
				
				if(th.getYear().equals(mminfo.getYear()) && WhmUtilities.findMonthDigit(th.getMonth()).equals(mminfo.getMonth())){
					
					manmonthInfo.setHour(mminfo.getHour());
					manmonthInfo.setManmonth(mminfo.getManmonth());
					manmonthList.add(manmonthInfo);
					continue temp;
				}
			}
			manmonthInfo.setHour(0.0);
			manmonthInfo.setManmonth(0.0);
			manmonthList.add(manmonthInfo);
		}
		
		
	
	return manmonthList;
}

	public void resultListByAll() {
		List<ManMonthInfo> manmonthdata = summaryActivityService
				.findDetailManmonthByPartial(projectID, "", "",
						startDate,endDate);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				projectID, startDate,endDate);
		
		List<ProjectSummaryInfo> prjList = summaryActivityService
		.findDetailProjectByPartial(projectID, startDate, endDate,
				customer);
		
		
		
		System.out.println("************project list size = " + prjList.size());

		allPrjResultList = new ArrayList<>();

		if (prjList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			return;
		} else {
			for (ProjectSummaryInfo projectInfoList : prjList) {
				System.out.println("PROJECT ID = "+projectInfoList.getProjectID());
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				List<TeamInfo> teamList = summaryActivityService
						.findDetailTeamByPartial(
								projectInfoList.getProjectID(), startDate,
								endDate);

				for (TeamInfo teamInfoList : teamList) {
					List<StaffInfo> userinfolist = new ArrayList<>();
					List<ManMonthInfo> manmonthList = new ArrayList<>();
					List<StaffInfo> userList = summaryActivityService
							.findDetailUserByPartial(
									projectInfoList.getProjectID(),
									teamInfoList.getTeanName(), startDate,
									endDate);
					for (StaffInfo userInfoList : userList) {
						manmonthList = ManMonthData(
								projectInfoList.getProjectID(),
								teamInfoList.getTeanName(),
								userInfoList.getStaffId(),manmonthdata);
						StaffInfo userinfo = new StaffInfo();
						userinfo.setStaffId(userInfoList.getStaffId());
						userinfo.setStaffName(userInfoList.getStaffName());
						userinfo.setManmonth(manmonthList);
						userinfolist.add(userinfo);

					}

					TeamInfo teaminfo = new TeamInfo();
					teaminfo.setProjectID(teamInfoList.getProjectID());
					teaminfo.setTeanName(teamInfoList.getTeanName());
					teaminfo.setStaffInfo(userinfolist);
					teaminfolist.add(teaminfo);
				}
				if (projectInfoList.getProjectID().equals("9999")) {
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
				projectInfo
						.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setMembers(projectInfoList.getMembers());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth(projectInfoList
						.getProjectID(),totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);

				allPrjResultList.add(projectInfo);

			}

		}

	}

	public void resultListByPartial() {
		
		List<ManMonthInfo> manmonthdata = summaryActivityService
				.findDetailManmonthByPartial(projectID, "", "",
						startDate,endDate);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				projectID, startDate,endDate);
		List<ProjectSummaryInfo> prjList = summaryActivityService
				.findDetailProjectByPartial(projectID, startDate, endDate,
						customer);

		

		PartialPrjResultList = new ArrayList<>();

		if (prjList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			return;
		} else {
			for (ProjectSummaryInfo projectInfoList : prjList) {
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				List<TeamInfo> teamList = summaryActivityService
						.findDetailTeamByPartial(
								projectInfoList.getProjectID(), startDate,
								endDate);

				for (TeamInfo teamInfoList : teamList) {
					List<StaffInfo> userinfolist = new ArrayList<>();
					List<ManMonthInfo> manmonthList = new ArrayList<>();
					List<StaffInfo> userList = summaryActivityService
							.findDetailUserByPartial(
									projectInfoList.getProjectID(),
									teamInfoList.getTeanName(), startDate,
									endDate);
					for (StaffInfo userInfoList : userList) {
						manmonthList = ManMonthData(
								projectInfoList.getProjectID(),
								teamInfoList.getTeanName(),
								userInfoList.getStaffId(),manmonthdata);
						StaffInfo userinfo = new StaffInfo();
						userinfo.setStaffId(userInfoList.getStaffId());
						userinfo.setStaffName(userInfoList.getStaffName());
						userinfo.setManmonth(manmonthList);
						userinfolist.add(userinfo);

					}

					TeamInfo teaminfo = new TeamInfo();
					teaminfo.setProjectID(teamInfoList.getProjectID());
					teaminfo.setTeanName(teamInfoList.getTeanName());
					teaminfo.setStaffInfo(userinfolist);
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
				projectInfo
						.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setMembers(projectInfoList.getMembers());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth(projectInfoList.getProjectID().toString(),totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);

				PartialPrjResultList.add(projectInfo);

			}

		}

	}

	public void resultListByCustomer() {
		List<ManMonthInfo> manmonthdata = summaryActivityService
				.findDetailManmonthByPartial(projectID, "", "",
						startDate,endDate);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				projectID, startDate,endDate);
		List<ProjectSummaryInfo> prjList = summaryActivityService
				.findDetailProjectByPartial("", startDate, endDate,
						customer);
		System.out.println("************project list size = " + prjList.size());

		customerPrjResultList = new ArrayList<>();

		if (prjList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			return;
		} else {
			for (ProjectSummaryInfo projectInfoList : prjList) {
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				List<TeamInfo> teamList = summaryActivityService
						.findDetailTeamByPartial(
								projectInfoList.getProjectID(), startDate,
								endDate);

				for (TeamInfo teamInfoList : teamList) {
					List<StaffInfo> userinfolist = new ArrayList<>();
					List<ManMonthInfo> manmonthList = new ArrayList<>();
					List<StaffInfo> userList = summaryActivityService
							.findDetailUserByPartial(
									projectInfoList.getProjectID(),
									teamInfoList.getTeanName(), startDate,
									endDate);
					for (StaffInfo userInfoList : userList) {
						manmonthList = ManMonthData(
								projectInfoList.getProjectID(),
								teamInfoList.getTeanName(),
								userInfoList.getStaffId(),manmonthdata);
						StaffInfo userinfo = new StaffInfo();
						userinfo.setStaffId(userInfoList.getStaffId());
						userinfo.setStaffName(userInfoList.getStaffName());
						userinfo.setManmonth(manmonthList);
						userinfolist.add(userinfo);

					}

					TeamInfo teaminfo = new TeamInfo();
					teaminfo.setProjectID(teamInfoList.getProjectID());
					teaminfo.setTeanName(teamInfoList.getTeanName());
					teaminfo.setStaffInfo(userinfolist);
					teaminfolist.add(teaminfo);
				}
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
				projectInfo
						.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setMembers(projectInfoList.getMembers());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth(projectInfoList
						.getProjectID(),totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);

				customerPrjResultList.add(projectInfo);

			}

		}

	}

	public void resultListByFull() {
		List<ManMonthInfo> manmonthdata = summaryActivityService
				.findDetailManmonthByPartial(projectID, "", "",
						startDate,endDate);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				projectID, startDate,endDate);
		List<ProjectSummaryInfo> prjList = summaryActivityService
				.findDetailProjectByFull(projectID, startDate, endDate,customer);
		System.out.println("************project list size = " + prjList.size());
		for (ProjectSummaryInfo projectInfoList : prjList) {
			System.out.println("PROJECT ID " + projectInfoList.getProjectID());
		}
		fullPrjResultList = new ArrayList<>();
		if (prjList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			return;
		} else {
			for (ProjectSummaryInfo projectInfoList : prjList) {
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				List<TeamInfo> teamList = summaryActivityService
						.findDetailTeamByPartial(
								projectInfoList.getProjectID(), startDate,
								endDate);

				for (TeamInfo teamInfoList : teamList) {
					List<StaffInfo> userinfolist = new ArrayList<>();
					List<ManMonthInfo> manmonthList = new ArrayList<>();
					List<StaffInfo> userList = summaryActivityService
							.findDetailUserByPartial(
									projectInfoList.getProjectID(),
									teamInfoList.getTeanName(), startDate,
									endDate);
					for (StaffInfo userInfoList : userList) {
						manmonthList = ManMonthData(
								projectInfoList.getProjectID(),
								teamInfoList.getTeanName(),
								userInfoList.getStaffId(),manmonthdata);
						StaffInfo userinfo = new StaffInfo();
						userinfo.setStaffId(userInfoList.getStaffId());
						userinfo.setStaffName(userInfoList.getStaffName());
						userinfo.setManmonth(manmonthList);
						userinfolist.add(userinfo);

					}

					TeamInfo teaminfo = new TeamInfo();
					teaminfo.setProjectID(teamInfoList.getProjectID());
					teaminfo.setTeanName(teamInfoList.getTeanName());
					teaminfo.setStaffInfo(userinfolist);
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
				projectInfo
						.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setMembers(projectInfoList.getMembers());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth(projectInfoList
						.getProjectID(),totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);

				fullPrjResultList.add(projectInfo);

			}

		}

	}

	public void resultListByNull() {
		List<ManMonthInfo> manmonthdata = summaryActivityService
				.findDetailManmonthByPartial(projectID, "", "",
						startDate,endDate);
		List<TotalManMonthInfo> totalResultData = summaryActivityService.findTotalResultByPartial(
				"-", startDate,endDate);
		List<ProjectSummaryInfo> prjList = summaryActivityService
				.findDetailNullProject(startDate, endDate);
		nullPrjResultList = new ArrayList<>();
		if (prjList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							"There is no data for searched project."));
			return;
		} else {

			for (ProjectSummaryInfo projectInfoList : prjList) {
				ProjectSummaryInfo projectInfo = new ProjectSummaryInfo();
				List<TeamInfo> teaminfolist = new ArrayList<>();
				List<TotalManMonthInfo> totalInfoList = new ArrayList<>();
				List<TeamInfo> teamList = summaryActivityService
						.findDetailTeamByPartial("-", startDate, endDate);
				for (TeamInfo teamInfoList : teamList) {
					List<StaffInfo> userinfolist = new ArrayList<>();
					List<ManMonthInfo> manmonthList = new ArrayList<>();
					List<StaffInfo> userList = summaryActivityService
							.findDetailUserByPartial("-",
									teamInfoList.getTeanName(), startDate,
									endDate);
					for (StaffInfo userInfoList : userList) {
						manmonthList = ManMonthData("9999",
								teamInfoList.getTeanName(),
								userInfoList.getStaffId(),manmonthdata);
						StaffInfo userinfo = new StaffInfo();
						userinfo.setStaffId(userInfoList.getStaffId());
						userinfo.setStaffName(userInfoList.getStaffName());
						userinfo.setManmonth(manmonthList);
						userinfolist.add(userinfo);

					}

					TeamInfo teaminfo = new TeamInfo();
					teaminfo.setProjectID("-");
					teaminfo.setTeanName(teamInfoList.getTeanName());
					teaminfo.setStaffInfo(userinfolist);
					teaminfolist.add(teaminfo);
				}

				projectInfo.setProjectID("-");
				projectInfo.setProjectName("-");
				projectInfo.setCustomer("-");

				projectInfo.setTotalPlanMM(0.0);
				projectInfo
						.setTotalActualMM(projectInfoList.getTotalActualMM());
				projectInfo.setMembers(projectInfoList.getMembers());
				projectInfo.setTeamInfo(teaminfolist);
				totalInfoList = findTotalManMonth("9999",totalResultData);
				projectInfo.setTotalManMonthInfo(totalInfoList);
				nullPrjResultList.add(projectInfo);

			}

		}
	}

	/* Export excel file. */
	public void export() throws Exception {

		if (exportProjectDetailReport()) {

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
							"There is no data to export."));
		}
	}

	/* Set up excel file name and call exportdatatoexcel method. */
	private boolean exportProjectDetailReport() throws FileNotFoundException,
			IOException, HPSFException {

		StringBuilder name = new StringBuilder();
		StringBuilder filename = new StringBuilder();
		;

		filename = name.append("(DAT)_Working Hour Management Project(Detail)")
				.append(".xlsx");

		if (projectIDList.size() <= 0 && !projectID.equals("-")) {
			setDownloadFileName("");
			setExportFileName("");
			return false;
		} else {
			exportDatatoExcel(filename);
			return true;
		}
	}

	/* Export data to specified excel file */
	private void exportDatatoExcel(StringBuilder filename)
			throws FileNotFoundException, HPSFException {
		int startRow = 0;
		int startColumn = 1;
		XSSFWorkbook workbook;
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		ServletContext context = (ServletContext) externalContext.getContext();

		try {
			// Template Excel File
			File file = new File(
					context.getRealPath("/template/WorkingHourManagementProjectDetailTemplate.xlsx"));
			FileInputStream fIP = new FileInputStream(file);
			workbook = new XSSFWorkbook(fIP);

			File outputFile = new File(context.getRealPath("/upload/"
					+ filename));
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

			for (int i = 0; i < tableHeaderList.size(); i++) {
				sheetName.addMergedRegion(new CellRangeAddress(0, 0,
						startColumn + 7, startColumn + 8));

				Cell yearMonthCell = row.createCell(startColumn + 7);
				yearMonthCell.setCellValue(tableHeaderList.get(i).getYear()
						+ "-" + tableHeaderList.get(i).getMonth());
				yearMonthCell.setCellStyle(headerStyle);

				Cell cell = rowTwo.createCell(startColumn + 7);
				cell.setCellValue("Hour");
				cell.setCellStyle(headerStyle);

				cell = rowTwo.createCell(startColumn + 8);
				cell.setCellValue("M-M");
				cell.setCellStyle(headerStyle);

				startColumn = startColumn + 2;
			}

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			cellStyle.setWrapText(true);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

			if (fullPrjExportFlag) {
				exportExcelFull(rowTwo, sheetName, startColumn, cellStyle,workbook);
			} else if (partialPrjExportFlag) {
				exportExcelPartial(rowTwo, sheetName, startColumn, cellStyle,workbook);
			} else if (nullPrjExportFlag) {
				exportExcelNullProject(rowTwo, sheetName, startColumn,
						cellStyle,workbook);
			} else if (allPrjExportFlag) {
				exportExcelAll(rowTwo, sheetName, startColumn, cellStyle,workbook);
			} else if (customerPrjExportFlag) {
				exportExcelCustomer(rowTwo, sheetName, startColumn, cellStyle,workbook);
			}

			sheetName.createFreezePane(8, 2);
			workbook.write(outputData);
			outputData.flush();
			outputData.close();

			setDownloadFileName(context.getRealPath("/upload/" + filename));
			setExportFileName(filename.toString());

		} catch (IOException e) {
			throw new HPSFException(e.getMessage());
		}
	}

	public void exportExcelPartial(Row row, XSSFSheet sheetName,
			int startColumn, CellStyle cellStyle,XSSFWorkbook workbook) {

		int count = 0;
		int endrow = 0;
		int rowcount = 0;
		

		int startProjectidrow = 0;
		int endstartProjectidrow = 0;
		int Projectidcount = 0;
		
		for (ProjectSummaryInfo psi : PartialPrjResultList) {
			int prj_num = 1;
			Row totalrow;

			int total_startcolumn = 5;
			int startrow = 0;

			for (TeamInfo teaminfo : psi.getTeamInfo()) {
				int team_count=0;
				for (StaffInfo staffinfo : teaminfo.getStaffInfo()) {

					row = sheetName.createRow(count + 2);

					int start_column = 5;

					startProjectidrow = Projectidcount + 2;
					endstartProjectidrow = startProjectidrow + psi.getMembers()
							- 1;
					if(prj_num == psi.getMembers()){
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 0, 0));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 1, 1));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 2, 2));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 3, 3));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 4, 4));
					prj_num = 1;
					}

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

					cell = row.createCell(5);
					cell.setCellValue(teaminfo.getTeanName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(6);
					cell.setCellValue(staffinfo.getStaffId());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(7);
					cell.setCellValue(staffinfo.getStaffName());
					cell.setCellStyle(cellStyle);
					count = count + 1;
					for (ManMonthInfo manonth : staffinfo.getManmonth()) {
						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getHour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						cell = row.createCell(start_column + 4);
						cell.setCellValue(manonth.getManmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						start_column = start_column + 2;
					}
					prj_num = prj_num + 1;
					team_count=team_count + 1;
				}
				startrow = rowcount + 2;
				endrow = (startrow + teaminfo.staffInfo.size()) - 1;
				if(team_count == teaminfo.staffInfo.size()){
				sheetName.addMergedRegion(new CellRangeAddress(startrow,
						endrow, 5, 5));
				team_count = 0;	
				}
				rowcount = endrow - 1;

			}
			rowcount = endrow;

			totalrow = sheetName.createRow(endstartProjectidrow + 1);

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

				cell = totalrow.createCell(6);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(7);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(total_startcolumn + 3);
				cell.setCellValue(totalinfo.getTotal_hour());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				cell = totalrow.createCell(total_startcolumn + 4);
				cell.setCellValue(totalinfo.getTotal_manmonth());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				total_startcolumn = total_startcolumn + 2;
			}

			sheetName.addMergedRegion(new CellRangeAddress(
					endstartProjectidrow + 1, endstartProjectidrow + 1, 5, 7));
			Projectidcount = endstartProjectidrow;
			count = count + 1;
		}

	}

	public void exportExcelFull(Row row, XSSFSheet sheetName, int startColumn,
			CellStyle cellStyle,XSSFWorkbook workbook) {
		int count = 0;

		int endrow = 0;
		int rowcount = 0;
		

		for (ProjectSummaryInfo psi : fullPrjResultList) {
			Row totalrow = sheetName.createRow(psi.getMembers() + 2);
			int total_startcolumn = 5;
			int startrow = 0;
			int team_count=0;
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 0, 0));
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 1, 1));
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 2, 2));
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 3, 3));
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 4, 4));
			for (TeamInfo teaminfo : psi.getTeamInfo()) {
				
				for (StaffInfo staffinfo : teaminfo.getStaffInfo()) {
					row = sheetName.createRow(count + 2);

					int start_column = 5;
					
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

					cell = row.createCell(5);
					cell.setCellValue(teaminfo.getTeanName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(6);
					cell.setCellValue(staffinfo.getStaffId());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(7);
					cell.setCellValue(staffinfo.getStaffName());
					cell.setCellStyle(cellStyle);
					count++;
					for (ManMonthInfo manonth : staffinfo.getManmonth()) {
						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getHour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						cell = row.createCell(start_column + 4);
						cell.setCellValue(manonth.getManmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						start_column = start_column + 2;
					}
					team_count = team_count + 1;
				}
				startrow = rowcount + 2;
				endrow = startrow + teaminfo.staffInfo.size() - 1;
				
				
				if(team_count == teaminfo.staffInfo.size()){
				sheetName.addMergedRegion(new CellRangeAddress(startrow,
						endrow, 5, 5));
				team_count = 0;
				}
				
				rowcount = endrow - 1;
			}
			sheetName.addMergedRegion(new CellRangeAddress(
					psi.getMembers() + 2, psi.getMembers() + 2, 5, 7));
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

				cell = totalrow.createCell(6);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(7);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(total_startcolumn + 3);
				cell.setCellValue(totalinfo.getTotal_hour());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				cell = totalrow.createCell(total_startcolumn + 4);
				cell.setCellValue(totalinfo.getTotal_manmonth());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				total_startcolumn = total_startcolumn + 2;
			}

		}

		}

	public void exportExcelNullProject(Row row, XSSFSheet sheetName,
			int startColumn, CellStyle cellStyle,XSSFWorkbook workbook) {

		int count = 0;

		int endrow = 0;
		int rowcount = 0;
		

		for (ProjectSummaryInfo psi : nullPrjResultList) {
			
			Row totalrow = sheetName.createRow(psi.getMembers() + 2);
			int total_startcolumn = 5;
			int startrow = 0;
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 0, 0));
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 1, 1));
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 2, 2));
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 3, 3));
			sheetName.addMergedRegion(new CellRangeAddress(2,
					psi.getMembers() + 2, 4, 4));
			
			for (TeamInfo teaminfo : psi.getTeamInfo()) {
				int team_count=0;
				for (StaffInfo staffinfo : teaminfo.getStaffInfo()) {
					row = sheetName.createRow(count + 2);

					int start_column = 5;
					

					Cell cell = row.createCell(0);
					cell.setCellStyle(cellStyle);
					cell.setCellValue("-");

					cell = row.createCell(1);
					cell.setCellValue("-");
					cell.setCellStyle(cellStyle);

					cell = row.createCell(2);
					cell.setCellValue("-");
					cell.setCellStyle(cellStyle);

					cell = row.createCell(3);
					cell.setCellValue(0.0);
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

					cell = row.createCell(4);
					cell.setCellValue(psi.getTotalActualMM());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

					cell = row.createCell(5);
					cell.setCellValue(teaminfo.getTeanName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(6);
					cell.setCellValue(staffinfo.getStaffId());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(7);
					cell.setCellValue(staffinfo.getStaffName());
					cell.setCellStyle(cellStyle);
					count++;
					for (ManMonthInfo manonth : staffinfo.getManmonth()) {
						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getHour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						cell = row.createCell(start_column + 4);
						cell.setCellValue(manonth.getManmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						start_column = start_column + 2;
					}
					
					team_count=team_count + 1;
				}
				startrow = rowcount + 2;
				endrow = startrow + teaminfo.staffInfo.size() - 1;
				if(team_count == teaminfo.staffInfo.size()){
				sheetName.addMergedRegion(new CellRangeAddress(startrow,
						endrow, 5, 5));
				team_count = 0;
				}
				rowcount = endrow - 1;
			}

			sheetName.addMergedRegion(new CellRangeAddress(
					psi.getMembers() + 2, psi.getMembers() + 2, 5, 7));
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

				cell = totalrow.createCell(6);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(7);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(total_startcolumn + 3);
				cell.setCellValue(totalinfo.getTotal_hour());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				cell = totalrow.createCell(total_startcolumn + 4);
				cell.setCellValue(totalinfo.getTotal_manmonth());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				total_startcolumn = total_startcolumn + 2;
			}

		}

	}

	public void exportExcelAll(Row row, XSSFSheet sheetName, int startColumn,
			CellStyle cellStyle,XSSFWorkbook workbook) {

		int count = 0;
		int endrow = 0;
		int rowcount = 0;
		
		int startProjectidrow = 0;
		int endstartProjectidrow = 0;
		int Projectidcount = 0;

		for (ProjectSummaryInfo psi : allPrjResultList) {
			Row totalrow;
			int prj_num = 1;
			int total_startcolumn = 5;
			int startrow = 0;

			for (TeamInfo teaminfo : psi.getTeamInfo()) {
				int team_count=0;
				for (StaffInfo staffinfo : teaminfo.getStaffInfo()) {
					
					row = sheetName.createRow(count + 2);

					int start_column = 5;

					startProjectidrow = Projectidcount + 2;
					endstartProjectidrow = startProjectidrow + psi.getMembers()
							- 1;
					if(prj_num == psi.getMembers()){
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 0, 0));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 1, 1));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 2, 2));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 3, 3));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 4, 4));
					prj_num = 1;
					}
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

					cell = row.createCell(5);
					cell.setCellValue(teaminfo.getTeanName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(6);
					cell.setCellValue(staffinfo.getStaffId());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(7);
					cell.setCellValue(staffinfo.getStaffName());
					cell.setCellStyle(cellStyle);
					count = count + 1;
					for (ManMonthInfo manonth : staffinfo.getManmonth()) {
						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getHour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						cell = row.createCell(start_column + 4);
						cell.setCellValue(manonth.getManmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						start_column = start_column + 2;
					}
					prj_num = prj_num + 1;
					team_count=team_count + 1;
				}
				startrow = rowcount + 2;
				endrow = (startrow + teaminfo.staffInfo.size()) - 1;
				
				if(team_count == teaminfo.staffInfo.size()){
				sheetName.addMergedRegion(new CellRangeAddress(startrow,
						endrow, 5, 5));
				team_count = 0;
				}
				rowcount = endrow - 1;

			}
			rowcount = endrow;

			totalrow = sheetName.createRow(endstartProjectidrow + 1);

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

				cell = totalrow.createCell(6);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(7);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(total_startcolumn + 3);
				cell.setCellValue(totalinfo.getTotal_hour());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				cell = totalrow.createCell(total_startcolumn + 4);
				cell.setCellValue(totalinfo.getTotal_manmonth());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				total_startcolumn = total_startcolumn + 2;
			}

			sheetName.addMergedRegion(new CellRangeAddress(
					endstartProjectidrow + 1, endstartProjectidrow + 1, 5, 7));
			Projectidcount = endstartProjectidrow;
			count = count + 1;
		}

	}

	public void exportExcelCustomer(Row row, XSSFSheet sheetName,
			int startColumn, CellStyle cellStyle,XSSFWorkbook workbook) {

		int count = 0;
		int endrow = 0;
		int rowcount = 0;
	

		int startProjectidrow = 0;
		int endstartProjectidrow = 0;
		int Projectidcount = 0;

		for (ProjectSummaryInfo psi : customerPrjResultList) {
			Row totalrow;
			int prj_num = 1;
			int total_startcolumn = 5;
			int startrow = 0;

			for (TeamInfo teaminfo : psi.getTeamInfo()) {
				int team_count = 0;
				for (StaffInfo staffinfo : teaminfo.getStaffInfo()) {

					row = sheetName.createRow(count + 2);

					int start_column = 5;

					startProjectidrow = Projectidcount + 2;
					endstartProjectidrow = startProjectidrow + psi.getMembers()
							- 1;
					
					if(prj_num == psi.getMembers()){
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 0, 0));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 1, 1));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 2, 2));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 3, 3));
					sheetName.addMergedRegion(new CellRangeAddress(
							startProjectidrow, endstartProjectidrow + 1, 4, 4));
					prj_num = 1;
					}

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

					cell = row.createCell(5);
					cell.setCellValue(teaminfo.getTeanName());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(6);
					cell.setCellValue(staffinfo.getStaffId());
					cell.setCellStyle(cellStyle);

					cell = row.createCell(7);
					cell.setCellValue(staffinfo.getStaffName());
					cell.setCellStyle(cellStyle);
					count = count + 1;
					for (ManMonthInfo manonth : staffinfo.getManmonth()) {
						cell = row.createCell(start_column + 3);
						cell.setCellValue(manonth.getHour());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						cell = row.createCell(start_column + 4);
						cell.setCellValue(manonth.getManmonth());
						cell.setCellStyle(cellStyle);
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

						start_column = start_column + 2;
					}
					prj_num = prj_num + 1;
					team_count=team_count + 1;
				}
				startrow = rowcount + 2;
				endrow = (startrow + teaminfo.staffInfo.size()) - 1;
				if(team_count == teaminfo.staffInfo.size()){
				sheetName.addMergedRegion(new CellRangeAddress(startrow,
						endrow, 5, 5));
				team_count = 0;
				}
				rowcount = endrow - 1;

			}
			rowcount = endrow;

			totalrow = sheetName.createRow(endstartProjectidrow + 1);

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

				cell = totalrow.createCell(6);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(7);
				cell.setCellValue("");
				cell.setCellStyle(cellStyle);

				cell = totalrow.createCell(total_startcolumn + 3);
				cell.setCellValue(totalinfo.getTotal_hour());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				cell = totalrow.createCell(total_startcolumn + 4);
				cell.setCellValue(totalinfo.getTotal_manmonth());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));

				total_startcolumn = total_startcolumn + 2;
			}

			sheetName.addMergedRegion(new CellRangeAddress(
					endstartProjectidrow + 1, endstartProjectidrow + 1, 5, 7));
			Projectidcount = endstartProjectidrow;
			count = count + 1;
		}

	}

	public String back() {
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("detailData", this.detailData);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("projectID", this.projectID);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("startYear", this.startYear);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("startMonth", this.startMonth);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("endYear", this.endYear);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("endMonth", this.endMonth);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("customer", this.customer);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("clientOrganization", this.clientOrganization);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("years", this.years);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("months", this.months);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("btnShow", this.btnShow);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("fullPrjFlag", this.fullPrjFlag);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("allPrjFlag", this.allPrjFlag);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("customerFlag", this.customerFlag);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("partialPrjFlag", this.partialPrjFlag);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("nullPrjFlag", this.nullPrjFlag);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("projectIDList", this.projectIDList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("fullResultList", this.fullResultList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("partialResultList", this.partialResultList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("nullResultList", this.nullResultList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("allResultList", this.allResultList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("customerResultList", this.customerResultList);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
		.put("btnExport", this.btnExport);

		return "/faces/view/mgtreports/MNR005.xhtml?faces-redirect=true";
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

	public void setDailyActivityService(
			IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public ISummaryActivityService getSummaryActivityService() {
		return summaryActivityService;
	}

	public void setSummaryActivityService(
			ISummaryActivityService summaryActivityService) {
		this.summaryActivityService = summaryActivityService;
	}

	public List<TableHeader> getTableHeaderList() {
		return tableHeaderList;
	}

	public void setTableHeaderList(List<TableHeader> tableHeaderList) {
		this.tableHeaderList = tableHeaderList;
	}

	public List<ProjectSummaryInfo> getProjectIDList() {
		return projectIDList;
	}

	public void setProjectIDList(List<ProjectSummaryInfo> projectIDList) {
		this.projectIDList = projectIDList;
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

	public boolean isFullPrjFlag() {
		return fullPrjFlag;
	}

	public void setFullPrjFlag(boolean fullPrjFlag) {
		this.fullPrjFlag = fullPrjFlag;
	}

	public boolean isPartialPrjFlag() {
		return partialPrjFlag;
	}

	public void setPartialPrjFlag(boolean partialPrjFlag) {
		this.partialPrjFlag = partialPrjFlag;
	}

	public List<ProjectSummaryInfo> getFullPrjResultList() {
		return fullPrjResultList;
	}

	public void setFullPrjResultList(List<ProjectSummaryInfo> fullPrjResultList) {
		this.fullPrjResultList = fullPrjResultList;
	}

	public List<ProjectSummaryInfo> getPartialPrjResultList() {
		return PartialPrjResultList;
	}

	public void setPartialPrjResultList(
			List<ProjectSummaryInfo> partialPrjResultList) {
		PartialPrjResultList = partialPrjResultList;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
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

	public boolean isBtnShow() {
		return btnShow;
	}

	public void setBtnShow(boolean btnShow) {
		this.btnShow = btnShow;
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

	public List<ProjectSummaryInfo> getNullPrjResultList() {
		return nullPrjResultList;
	}

	public void setNullPrjResultList(List<ProjectSummaryInfo> nullPrjResultList) {
		this.nullPrjResultList = nullPrjResultList;
	}

	public List<ProjectSummaryInfo> getAllPrjResultList() {
		return allPrjResultList;
	}

	public void setAllPrjResultList(List<ProjectSummaryInfo> allPrjResultList) {
		this.allPrjResultList = allPrjResultList;
	}

	public List<String> getClientOrganization() {
		return clientOrganization;
	}

	public void setClientOrganization(List<String> clientOrganization) {
		this.clientOrganization = clientOrganization;
	}

	public List<ProjectSummaryInfo> getCustomerPrjResultList() {
		return customerPrjResultList;
	}

	public void setCustomerPrjResultList(
			List<ProjectSummaryInfo> customerPrjResultList) {
		this.customerPrjResultList = customerPrjResultList;
	}

	public boolean isAllPrjExportFlag() {
		return allPrjExportFlag;
	}

	public void setAllPrjExportFlag(boolean allPrjExportFlag) {
		this.allPrjExportFlag = allPrjExportFlag;
	}

	public boolean isFullPrjExportFlag() {
		return fullPrjExportFlag;
	}

	public void setFullPrjExportFlag(boolean fullPrjExportFlag) {
		this.fullPrjExportFlag = fullPrjExportFlag;
	}

	public boolean isNullPrjExportFlag() {
		return nullPrjExportFlag;
	}

	public void setNullPrjExportFlag(boolean nullPrjExportFlag) {
		this.nullPrjExportFlag = nullPrjExportFlag;
	}

	public boolean isCustomerPrjExportFlag() {
		return customerPrjExportFlag;
	}

	public void setCustomerPrjExportFlag(boolean customerPrjExportFlag) {
		this.customerPrjExportFlag = customerPrjExportFlag;
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
	
	

}
