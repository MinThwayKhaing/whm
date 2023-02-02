/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2018-04-30
 * @Version 1.0
 * @Purpose <<Working Hour Management by Team (Search and view men-month by team)>>
 *
 ***************************************************************************************/
package com.dat.whm.web.workinghourmanagement.team;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;

import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.summaryactivity.service.interfaces.ISummaryActivityService;
import com.dat.whm.team.entity.Team;
import com.dat.whm.team.service.interfaces.ITeamService;
import com.dat.whm.web.common.WhmUtilities;
import com.dat.whm.workinghour.entity.WorkingHour;
import com.dat.whm.workinghour.service.interfaces.IWorkingHourService;

@ManagedBean(name = "WorkingHourManagementTeamBean")
@ViewScoped
public class WorkingHourManagementTeamBean {
	
	/*Services*/
	@ManagedProperty(value = "#{TeamService}")
	private ITeamService teamService;
	
	@ManagedProperty(value = "#{SummaryActivityService}")
	private ISummaryActivityService summaryActivityService;
	
	@ManagedProperty(value = "#{WorkingHourService}")
	private IWorkingHourService workingHourService;
	
	/*Data from DB*/
	private List<Team> teams;
	private List<String> years;
	private List<String> months;
	private List<WorkingHour> workHours;
	
	/*Input Data*/
	private Team selectTeam;
	private String startYear;
	private String endYear;
	private String startMonth;
	private String endMonth;
	
	/*View Data */
	private List<WorkingHourManagementTeamView> viewData;
	private List<String> yearMonth;
	private List<String> rowRepeat;
	private ProjectDivision totalRow;
	private Map<String,Double> yearMonthMap;
	private List<HolidayCount> holidayCount;
	
	/*View Controller*/
	private boolean exportBtnCtl = false;
	private boolean searchResultCtl = false;
	private boolean searchBtnCtl = false;
	
	/*Export File*/
	private DefaultStreamedContent download;
	private String downloadFileName;
	private String exportFileName;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		holidayCount = new ArrayList<>();
		viewData = new ArrayList<>();
		totalRow = new ProjectDivision();
		rowRepeat= new ArrayList<>();
		rowRepeat.add("");
		years = new ArrayList<>();
		selectTeam= new Team();
		
		if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag") != null) {
			Boolean backInquiryFlag = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag");
			if (backInquiryFlag) {
				HashMap<String, Object> detailData = (HashMap<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("detailData");
				this.teams = (List<Team>) detailData.get("teams");
				this.years = (List<String>) detailData.get("years");
				this.months = (List<String>) detailData.get("months");
				this.selectTeam = (Team) detailData.get("selectTeam");
				this.startYear = (String) detailData.get("startYear");
				this.endYear = (String) detailData.get("endYear");
				this.startMonth = (String) detailData.get("startMonth");
				this.endMonth = (String) detailData.get("endMonth");
				this.viewData = (List<WorkingHourManagementTeamView>) detailData.get("viewData");
				this.totalRow = (ProjectDivision) detailData.get("totalRow");
				this.yearMonth = (List<String>) detailData.get("yearMonth");
				this.yearMonthMap = (Map<String, Double>) detailData.get("yearMonthMap");
				this.searchResultCtl = true;
			}
		}else {
			load();
		}
	}

	public void load() {
		teams = teamService.findAllTeam();
		holidayCount=summaryActivityService.getHolidayCount();
		exportBtnCtl = true;
		searchBtnCtl = false;
	}
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/09/24
	 * Explanation  : Added teamSelect() method for TeamID changing.
	 */	
	public void teamSelect() {
		try{
			if(selectTeam != null){
				viewData = new ArrayList<>();
				totalRow = new ProjectDivision();
				years = new ArrayList<>();
				yearMonthMap = new LinkedHashMap<>();
				this.searchResultCtl = false;
				this.searchBtnCtl = false;
				this.exportBtnCtl = true;
				
				years = summaryActivityService.findYearsByTeam(selectTeam);	
				months = summaryActivityService.findMonthsByTeam(selectTeam);
				
				if (years.size() == 0 || months.size() == 0) {
					String infoMessage = "No one did not report for " + selectTeam.getTeamName();
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "",infoMessage));
					searchBtnCtl = true;
					exportBtnCtl = true;
					return;
				}
				
				List<String> yearsList = new ArrayList<String>();
				List<String> monthsList = new ArrayList<String>();
				
				for(String number:years){
					yearsList.add(number);
				}
				
				for(String number:months){
					monthsList.add(number);
				}
				
				if(years.size() != 0){
					if (years.size() > 1) {
						months = WhmUtilities.getAllMonths();
					}else{
						/* Prepare month list. */
						List<String> tmpMonths = new ArrayList<String>();
						for (String number:months) {
							tmpMonths.add(number);
						}
						
						if (!months.equals("")) {
							months=WhmUtilities.convertDigitToMonthByString(tmpMonths);
						}						
					}
					
					startYear = Collections.min(yearsList);
					endYear = Collections.max(yearsList);
					startMonth = WhmUtilities.findMonthByDigit(Collections.min(monthsList));
					endMonth = WhmUtilities.findMonthByDigit(Collections.max(monthsList));
				}
			}
		}catch (NullPointerException e) {
			String InfoMessage = "No one did not report for " + selectTeam.getTeamName();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							InfoMessage));
			searchBtnCtl=true;
			exportBtnCtl = true;
		}
	}

	public void show(){
				
		if (! isValidSearch()) {
			this.searchResultCtl = false;
			return;
		}
		
		this.viewData = new ArrayList<>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = null;

		Date toDate = null;
		try {
			fromDate = format.parse(startYear + "-"+ WhmUtilities.findMonthDigit(startMonth) + "-" + "01");
			toDate = format.parse(endYear+ "-"+ WhmUtilities.findMonthDigit(endMonth)+ "-"+ WhmUtilities.getLastDayOfMonth(endYear,WhmUtilities.findMonthDigit(endMonth)));

			if (toDate.before(fromDate)) {
				String InfoMessage = "Start Date is greater than End Date.";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "",
								InfoMessage));
				this.searchResultCtl = false;
				this.searchBtnCtl=true;
				this.exportBtnCtl = true;
				return ;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return ;
		}
		//
		workHours = new ArrayList<>();
		workHours = workingHourService.findAllWorkingHours();
		Map<String, Object> searchData = new HashMap<>();
		searchData.put("team", selectTeam);
		searchData.put("startYear", startYear);
		searchData.put("endYear", endYear);
		searchData.put("startMonth", WhmUtilities.findMonthDigit(startMonth));
		searchData.put("endMonth",  WhmUtilities.findMonthDigit(endMonth));
		
		List<SummaryActivity> summaryActivityResult = summaryActivityService.findByTeam(searchData);
		
		if (summaryActivityResult.isEmpty()) {
			String errorMessage = "There is no search result.";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", errorMessage));
			this.searchResultCtl = false;
			return;
		}
		this.yearMonth = getAllYearMonth();
		this.yearMonthMap = getYearMonthHrMap(); 
		TreeMap<String, List<SummaryActivity>> activitiesGroupByProjectID = groupByProjectID(summaryActivityResult);
		
		WorkingHourManagementTeamView workingHourManagementTeamView = new WorkingHourManagementTeamView();
		workingHourManagementTeamView.setTeamId(selectTeam.getTeamName());
		workingHourManagementTeamView = prepareViewData(activitiesGroupByProjectID, workingHourManagementTeamView);

		viewData.add(workingHourManagementTeamView);
		
		this.searchResultCtl = true;
		this.exportBtnCtl = false;
		
	}
	
	private TreeMap<String, List<SummaryActivity>> groupByProjectID(List<SummaryActivity> summaryActivityResult){
		TreeMap<String, List<SummaryActivity>> activitiesGroupByProjectID = new TreeMap<>();
		for (SummaryActivity summaryActivity : summaryActivityResult) {
			if (summaryActivity.getProject_id() != null) {
					
				if (!activitiesGroupByProjectID.containsKey(summaryActivity.getProject_id())) {  //Edit project id column
					List<SummaryActivity> newObj = new ArrayList<>();
					newObj.add(summaryActivity);
					activitiesGroupByProjectID.put(summaryActivity.getProject_id(), newObj);  //Edit project id column
				}else {
					activitiesGroupByProjectID.get(summaryActivity.getProject_id()).add(summaryActivity);  //Edit project id column
				}
				
			}else {
				if (!activitiesGroupByProjectID.containsKey("-")) {
					List<SummaryActivity> newObj = new ArrayList<>();
					newObj.add(summaryActivity);
					activitiesGroupByProjectID.put("-", newObj);
				}else {
					activitiesGroupByProjectID.get("-").add(summaryActivity);
				}
			}
		}
			return activitiesGroupByProjectID;
	}
	
	private WorkingHourManagementTeamView prepareViewData(TreeMap<String, List<SummaryActivity>> activitiesGroupByProjectID, WorkingHourManagementTeamView workingHourManagementTeamView){
		workingHourManagementTeamView.setProjectDivision(new ArrayList<ProjectDivision>());
		LinkedHashMap<String, YearMonthDivision> totalSumContainer = new LinkedHashMap<>();
		double hourPerMonth = 0.0;
		ArrayList<String> totalmemberCount = new ArrayList<>();
		for (String projectID : activitiesGroupByProjectID.keySet()) {
			ProjectDivision projectDivision= new ProjectDivision();
			
		    List<SummaryActivity> value = activitiesGroupByProjectID.get(projectID);
		    /**
			 * Revised By   : Htet Wai Yum
			 * Revised Date : 2019/02/27
			 * Explanation  : Check 'NULL' project as '9999' code
			 */	
		    if (value.get(0).getProject_id().equals("9999")) {
		    	 projectDivision.setProjectId("-");
				 projectDivision.setProjectName("-");
			}else {
				 projectDivision.setProjectId(value.get(0).getProject_id()); //Edit project id column
				 projectDivision.setProjectName(value.get(0).getProjectname()); //Edit project name column
			}
		    projectDivision.setYearMonthDivision(new ArrayList<YearMonthDivision>());
		    projectDivision.setDetailActivities(value);
		    
		    for (Map.Entry<String, Double>  yearMonth : this.yearMonthMap.entrySet()) {
		    	List<String> memberContainar= new ArrayList<>();
			    double hour=0.0;
			    hourPerMonth = yearMonth.getValue();
		    	for (SummaryActivity activity : value) {
		    		String reportDate = activity.getYear() + "-" +  WhmUtilities.findMonthByDigit(activity.getMonth()).substring(0, 3);
		    		if (yearMonth.getKey().equals(reportDate)) {
		    			String staffId = activity.getUsername();
				    	
				    	if (!memberContainar.contains(staffId)) {
				    		memberContainar.add(staffId);
				    		if (!totalmemberCount.contains(staffId)) {
				    			totalmemberCount.add(staffId);
							}
						}
				    	hour = hour + activity.getActivityHours();
					}
		    	}
		    	double manMonth=0.0;
		    	if (hourPerMonth > 0.0) {
		    		manMonth = hour/hourPerMonth;
				}
		    	
		    	projectDivision.getYearMonthDivision().add(new YearMonthDivision(memberContainar.size(), hour, manMonth));
		    	
		    	if (!totalSumContainer.containsKey(yearMonth.getKey())) {
		    		totalSumContainer.put(yearMonth.getKey(), new YearMonthDivision(memberContainar.size(), hour, manMonth));
				}else {
					totalSumContainer.get(yearMonth.getKey()).addMember(memberContainar.size());
					totalSumContainer.get(yearMonth.getKey()).addHour(hour);
					totalSumContainer.get(yearMonth.getKey()).addManMonth(manMonth);
				}
		    }
		    
		    workingHourManagementTeamView.getProjectDivision().add(projectDivision);
		    
		}
		
		workingHourManagementTeamView.setMembers(totalmemberCount.size());
		
		this.totalRow = new ProjectDivision();
		this.totalRow.setYearMonthDivision(new ArrayList<YearMonthDivision>());
		for (String yearMonth : totalSumContainer.keySet()) {
			this.totalRow.getYearMonthDivision().add(totalSumContainer.get(yearMonth));
		}

		return workingHourManagementTeamView;
	}
	
	private List<String> getAllYearMonth(){
		List<String> yearMonth= new ArrayList<>();
        String date1 = startYear +"-"+ startMonth;
        String date2 = endYear +"-"+ endMonth;

        DateFormat formater = new SimpleDateFormat("yyyy-MMM");

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        try {
        	beginCalendar.setTime(formater.parse(date1));
			finishCalendar.setTime(formater.parse(date2));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			}

        while (beginCalendar.before(finishCalendar) || beginCalendar.equals(finishCalendar)) {
            // add one month to date per loop
            String date =  formater.format(beginCalendar.getTime());
            yearMonth.add(date);
            beginCalendar.add(Calendar.MONTH, 1);
        }
        return yearMonth;
    }
	
	/**
	 * Revised By   : Aye Myat Mon.
	 * Revised Date : 2018/10/01
	 * Explanation  : Added getYearMonthHrMap to calculate working hour.
	 */	
	
	private Map<String, Double> getYearMonthHrMap(){
		Map<String, Double> yearMonthMap = new LinkedHashMap<>();
		Calendar date = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MMM");
		DateFormat yearformat = new SimpleDateFormat("yyyy");
		DateFormat monthformat = new SimpleDateFormat("MM");
		double hourPerMonth=0.0;
		double hccount=0.0;
		
		
		for(String ym: yearMonth){
			try{
				date.setTime(format.parse(ym));
			}catch(java.text.ParseException e){
				e.printStackTrace();
			}
			for (WorkingHour whr: workHours)
			{hccount=0.0;
				for(HolidayCount hc:holidayCount){
	    			
	    				if(yearformat.format(date.getTime()).equals(hc.getYear()) && monthformat.format(date.getTime()).equals(hc.getMonth())){
	    				hccount = hc.getHolidayCount();
	    				}
	    			
	    			
			}
				if ((date.getTime().after(whr.getstartDate()) || date.getTime().equals(whr.getstartDate()))
    					&& (date.getTime().before(whr.getendDate())|| date.getTime().equals(whr.getendDate()))){
    				
    				hourPerMonth = whr.getWorkingHours() *(20 -hccount);
    				yearMonthMap.put(ym, hourPerMonth);
    				break;
    			}else{
				hourPerMonth = 0.0;
				yearMonthMap.put(ym, hourPerMonth);
    			}
	    	}

		}
			
		return yearMonthMap;
	}
	
	private boolean isValidSearch() {
		if (this.selectTeam == null) {
			String errorMessage = "Please select a Team.";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMessage));
			return false;
		}
		if (this.startYear == null || this.startYear.equals("") ) {
			String errorMessage = "Please select start year.";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMessage));
			return false;
		}
		if (this.startMonth == null || this.startMonth.equals("")) {
			String errorMessage = "Please select start month.";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMessage));
			return false;
		}
		if (this.endYear == null || this.endYear.equals("")) {
			String errorMessage = "Please select end year.";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMessage));
			return false;
		}
		if (this.endMonth == null || this.endMonth.equals("")) {
			String errorMessage = "Please select end month.";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMessage));
			return false;
		}
		return true;
	}
	
	/* Export excel file. */
	public void export() throws Exception {
		
		if (exportTeamReport()) {

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
	
	/* Set up excel file name and call exportdatatoexcel method. */
	private boolean exportTeamReport() throws FileNotFoundException,
			IOException, HPSFException {

		StringBuilder name = new StringBuilder();
		StringBuilder filename = new StringBuilder();
		;
		if (selectTeam != null) {
			filename = name.append("(DAT)_Working Hour Management Team_")
					.append(selectTeam.getTeamName()).append(".xlsx");
		} else {
			filename = name.append("(DAT)_Working Hour Management Team").append(
					".xlsx");
		}

		if (viewData.size() <= 0) {
			setDownloadFileName("");
			setExportFileName("");
			return false;
		} else {
			exportDatatoExcel(filename);
			return true;
		}
	}
	
	/* Export data to specified excel file */
	private void exportDatatoExcel(StringBuilder filename) throws FileNotFoundException, HPSFException {
		int startRow = 0;
		int startColumn = 1;
		XSSFWorkbook workbook;
		ExternalContext externalContext = FacesContext.getCurrentInstance()
				.getExternalContext();
		ServletContext context = (ServletContext) externalContext.getContext();
		
		try {
		// Template Excel File
		File file = new File(context.getRealPath("/template/WorkingHourManagementTeamTemplate.xlsx"));
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
		
		for (int i = 0; i < yearMonth.size(); i++) {
			sheetName.addMergedRegion(new CellRangeAddress(0, 0, startColumn + 3, startColumn + 5));
			
			Cell yearMonthCell = row.createCell(startColumn+3);
			yearMonthCell.setCellValue(yearMonth.get(i));
			yearMonthCell.setCellStyle(headerStyle);
			
			Cell cell = rowTwo.createCell(startColumn+3);
			cell.setCellValue("Members");
			cell.setCellStyle(headerStyle);
			
			cell = rowTwo.createCell(startColumn + 4);
			cell.setCellValue("Hour");
			cell.setCellStyle(headerStyle);
			
			cell = rowTwo.createCell(startColumn + 5);
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
		
		for (WorkingHourManagementTeamView exportData : viewData) {
			row = sheetName.createRow(startRow + 2);
			Cell cell = row.createCell(0);
			cell.setCellValue(exportData.getTeamId());
			cell.setCellStyle(cellStyle);
			
			CellRangeAddress rangeForTeam = new CellRangeAddress(startRow + 2, exportData.getProjectDivision().size() + 2, 0, 0);
			sheetName.addMergedRegion(rangeForTeam);			
							
			cell = row.createCell(1);
			cell.setCellValue(exportData.getMembers());
			cell.setCellStyle(cellStyle);
			
			CellRangeAddress rangeForMember = new CellRangeAddress(startRow + 2, exportData.getProjectDivision().size() + 2, 1, 1);
			sheetName.addMergedRegion(rangeForMember);
			
			List<ProjectDivision> projectDivision = exportData.getProjectDivision();
			
			for (int i = 0; i < projectDivision.size(); i++) {
				
				/*Project ID*/
				cell = row.createCell(2);
				cell.setCellValue(projectDivision.get(i).getProjectId());
				cell.setCellStyle(cellStyle);
				
				/*Project Name*/
				cell = row.createCell(3);
				cell.setCellValue(projectDivision.get(i).getProjectName());
				cell.setCellStyle(cellStyle);
				
				List<YearMonthDivision> yearMonthDivision = projectDivision.get(i).getYearMonthDivision();
				
				startColumn = 1;
				for (int j = 0; j < yearMonthDivision.size(); j++) {
					
					/*Member Count*/
					cell = row.createCell(startColumn + 3);
					cell.setCellValue(yearMonthDivision.get(j).getMember());
					cell.setCellStyle(cellStyle);
					
					/*Total Hour*/
					cell = row.createCell(startColumn + 4);
					cell.setCellValue(yearMonthDivision.get(j).getHour());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
					
					/*Man-Month*/
					cell = row.createCell(startColumn + 5);
					cell.setCellValue(yearMonthDivision.get(j).getManMonth());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
					
					startColumn = startColumn + 3;
				}
				row = sheetName.createRow(startRow + 3 + i );
			}
			
			sheetName.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 3));
			cell = row.createCell(2);
			cell.setCellValue("Total");
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);
			cell.setCellStyle(cellStyle);
			
			List<YearMonthDivision> yearMonthDivision = totalRow.getYearMonthDivision();
			startColumn = 1;
			for (int i = 0; i < yearMonthDivision.size(); i++) {
				
				cell = row.createCell(startColumn + 3);
				cell.setCellValue(yearMonthDivision.get(i).getMember());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(startColumn + 4);
				cell.setCellValue(yearMonthDivision.get(i).getHour());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
				
				cell = row.createCell(startColumn + 5);
				cell.setCellValue(yearMonthDivision.get(i).getManMonth());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
				
				startColumn = startColumn + 3;
			}
			
			/**
			 * Revised By   : Aye Thida Phyo
			 * Revised Date : 2018/09/28
			 * Explanation  : Set border for merged cell.
			 */	
			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, rangeForTeam, sheetName, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, rangeForTeam, sheetName, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, rangeForTeam, sheetName, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, rangeForTeam, sheetName, workbook);			
		
			RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, rangeForMember, sheetName, workbook);
			RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, rangeForMember, sheetName, workbook);
			RegionUtil.setBorderRight(CellStyle.BORDER_THIN, rangeForMember, sheetName, workbook);
			RegionUtil.setBorderTop(CellStyle.BORDER_THIN, rangeForMember, sheetName, workbook);
			
			sheetName.createFreezePane(4, 2);
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
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/09/28
	 * Explanation  : Added detail() method.
	 */	
	public String detail(){
		HashMap<String, Object> detailData = new HashMap<>();
		detailData.put("teams", this.teams);
		detailData.put("years", this.years);
		detailData.put("months", this.months);
		detailData.put("selectTeam", this.selectTeam);
		detailData.put("startYear", this.startYear);
		detailData.put("endYear", this.endYear);
		detailData.put("startMonth", this.startMonth);
		detailData.put("endMonth", this.endMonth);
		detailData.put("viewData", this.viewData);
		detailData.put("totalRow", this.totalRow);
		detailData.put("yearMonth", this.yearMonth);
		detailData.put("yearMonthMap", this.yearMonthMap);
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("detailData", detailData);
		return "/faces/view/mgtreports/MNR008.xhtml?faces-redirect=true";
	}
	
	public ITeamService getTeamService() {
		return teamService;
	}

	public void setTeamService(ITeamService teamService) {
		this.teamService = teamService;
	}

	public ISummaryActivityService getSummaryActivityService() {
		return summaryActivityService;
	}

	public void setSummaryActivityService(
			ISummaryActivityService summaryActivityService) {
		this.summaryActivityService = summaryActivityService;
	}

	public Team getSelectTeam() {
		return selectTeam;
	}

	public void setSelectTeam(Team selectTeam) {
		this.selectTeam = selectTeam;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public List<String> getMonths() {
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
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

	public boolean isExportBtnCtl() {
		return exportBtnCtl;
	}

	public void setExportBtnCtl(boolean exportBtnCtl) {
		this.exportBtnCtl = exportBtnCtl;
	}
	
	public boolean isSearchResultCtl() {
		return searchResultCtl;
	}

	public void setSearchResultCtl(boolean searchResultCtl) {
		this.searchResultCtl = searchResultCtl;
	}

	public List<WorkingHourManagementTeamView> getViewData() {
		return viewData;
	}

	public void setViewData(List<WorkingHourManagementTeamView> viewData) {
		this.viewData = viewData;
	}

	public List<String> getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(List<String> yearMonth) {
		this.yearMonth = yearMonth;
	}

	public List<String> getRowRepeat() {
		return rowRepeat;
	}

	public void setRowRepeat(List<String> rowRepeat) {
		this.rowRepeat = rowRepeat;
	}

	public ProjectDivision getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(ProjectDivision totalRow) {
		this.totalRow = totalRow;
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

	public Map<String, Double> getYearMonthMap() {
		return yearMonthMap;
	}

	public void setYearMonthMap(Map<String, Double> yearMonthMap) {
		this.yearMonthMap = yearMonthMap;
	}

	public List<WorkingHour> getWorkHours() {
		return workHours;
	}

	public void setWorkHours(List<WorkingHour> workHours) {
		this.workHours = workHours;
	}

	public IWorkingHourService getWorkingHourService() {
		return workingHourService;
	}

	public void setWorkingHourService(IWorkingHourService workingHourService) {
		this.workingHourService = workingHourService;
	}

	public boolean isSearchBtnCtl() {
		return searchBtnCtl;
	}

	public void setSearchBtnCtl(boolean searchBtnCtl) {
		this.searchBtnCtl = searchBtnCtl;
	}

}
