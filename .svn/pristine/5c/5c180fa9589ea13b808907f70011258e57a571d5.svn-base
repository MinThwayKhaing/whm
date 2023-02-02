/***************************************************************************************
 * 
 *@author Aye Thida Phyo
 * @Date 2018-10-09
 * @Version 1.0
 * @Purpose <<Working Hour Management by Staff (Search and view men-month by staff)>>
 *
 ***************************************************************************************/
package com.dat.whm.web.workinghourmanagement.individual;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;

import com.dat.whm.summaryactivity.service.interfaces.ISummaryActivityService;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.web.common.TableHeader;
import com.dat.whm.web.common.WhmUtilities;
import com.dat.whm.web.workinghrmanagementproject.ProjectSummaryInfo;
import com.dat.whm.web.workinghrmanagementproject.TotalManMonthInfo;

@ManagedBean(name = "WorkingHourManagementIndividualBean")
@ViewScoped
public class WorkingHourManagementIndividualBean {
	
	/*Services*/
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;
	
	@ManagedProperty(value = "#{SummaryActivityService}")
	private ISummaryActivityService summaryActivityService;
	
	/*Data from DB*/
	private List<User> users;
	private List<String> years;
	private List<String> months;
	
	/*Input Data*/
	private User selectStaff;
	private String startYear;
	private String endYear;
	private String startMonth;
	private String endMonth;	
	
	private String startDate;
	private String endDate;
	/*View Controller*/
	private boolean exportBtnCtl = false;
	private boolean searchResultCtl = false;
	private boolean searchBtnCtl = false;
	private boolean showTable = false;
	private boolean exportflag = false;
	private boolean showbtn = false;
	
	
	private List<Object[]> resultList;
	private List<Object[]> totalResultList;
	private List<StaffTotalInfo> staffTotalInfoList;
	private List<String> projectIDList;
	private List<TableHeader> periodHeaderList;
	private List<StaffDetailInfo> staffDetailInfoList;
	private List<StaffSummaryInfo> staffSummaryInfoList;
	private double total_hour = 0.0;
	private double total_mm = 0.0;
	
	//Export excel
	private DefaultStreamedContent download;
	private String downloadFileName;
	private String exportFileName;
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		years = new ArrayList<>();
		selectStaff= new User();		
		load();
	}

	public void load() {
		summaryActivityService.findHolidayCount();
		resultList = new ArrayList<>();
		projectIDList = new ArrayList<>();
		periodHeaderList = new ArrayList<>();
		staffDetailInfoList = new ArrayList<>();
		staffSummaryInfoList = new ArrayList<>();
		staffTotalInfoList = new ArrayList<>();
		totalResultList = new ArrayList<>();
		users = userService.findAllUser();
		exportBtnCtl = true;
		searchBtnCtl = false;
		showTable = false;
		showbtn = false;
	}
	
	public void prepareTableHeader(){
		
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
					
						periodHeaderList.add(tempDetailInfo);
					}

					tmpStartMonth = "1";
					if (Integer.parseInt(endYear) == j + 1) {
						noMonth = Integer.parseInt(tmpEndMonth);
					}
					
				}
	}
	public void userSelect() {
		if(resultList.size() > 0 || projectIDList.size() > 0 || periodHeaderList.size() > 0 || staffDetailInfoList.size() > 0 || staffSummaryInfoList.size() > 0){
			showTable = false;
			resultList = null;
			projectIDList = null;
			periodHeaderList = null;
			staffDetailInfoList = null;
			staffSummaryInfoList = null;
			totalResultList = null;
			staffTotalInfoList = null;
			
			resultList = new ArrayList<>();
			projectIDList = new ArrayList<>();
			periodHeaderList = new ArrayList<>();
			staffDetailInfoList = new ArrayList<>();
			staffSummaryInfoList = new ArrayList<>();
			totalResultList = new ArrayList<>();
			staffTotalInfoList = new ArrayList<>();
		}
		try{
			if(selectStaff != null){
				years = new ArrayList<>();
				this.searchResultCtl = false;
				this.searchBtnCtl = false;
				this.exportBtnCtl = true;
				
				years = summaryActivityService.findYearsByUser(selectStaff);	
				months = summaryActivityService.findMonthsByUser(selectStaff);
				
				if (years.size() == 0 || months.size() == 0) {
					String infoMessage = "There is no any report for " + selectStaff.getUsername();
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "",infoMessage));
					searchBtnCtl = true;
					exportBtnCtl = true;
					showbtn = false;
					exportflag = false;
					showTable = false;
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
					startMonth = WhmUtilities.findMonthByDigit(summaryActivityService.findStartMonthByUser(selectStaff));
					endMonth = WhmUtilities.findMonthByDigit(summaryActivityService.findEndMonthByUser(selectStaff));
					System.out.println("START MONTH = "+startMonth);
					System.out.println("END MONTH = "+endMonth);
					showbtn = true;
					exportflag = false;
				}
			}
		}catch (NullPointerException e) {
			String InfoMessage = "There is no report any for " + selectStaff.getUsername();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "",
							InfoMessage));
			searchBtnCtl = true;
			exportBtnCtl = true;
		}
	}
	
	public void show(){	
		
		showTable = false;
		if(resultList.size() > 0 || projectIDList.size() > 0 || periodHeaderList.size() > 0 || staffDetailInfoList.size() > 0 || staffSummaryInfoList.size() > 0){
			resultList = null;
			projectIDList = null;
			periodHeaderList = null;
			staffDetailInfoList = null;
			staffSummaryInfoList = null;
			totalResultList = null;
			staffTotalInfoList = null;
			
			resultList = new ArrayList<>();
			projectIDList = new ArrayList<>();
			periodHeaderList = new ArrayList<>();
			staffDetailInfoList = new ArrayList<>();
			staffSummaryInfoList = new ArrayList<>();
			totalResultList = new ArrayList<>();
			staffTotalInfoList = new ArrayList<>();
		}
		
		prepareTableHeader();
		exportflag = true;	
		projectIDList = summaryActivityService.findProjectIDByStaff(selectStaff, startDate, endDate);
		resultList = summaryActivityService.findByStaff(selectStaff, startDate, endDate);
		totalResultList = summaryActivityService.findTotalByStaff(selectStaff, startDate, endDate);
		//System.out.println("ResultList Size >> " + resultList.size());		
		if(resultList.size() == 0 || resultList.isEmpty()){
			
			String infoMessage = "There is no search record for this period";
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "",infoMessage));
			
			return;
			
		} else {
			
			prepareView(resultList);
			prepareViewTotal(totalResultList);
			showTable = true;
		}

	}

	
private void prepareViewTotal(List<Object[]> dataList) {
	for (int i=0; i<periodHeaderList.size(); i++)
	{
		StaffTotalInfo tmpInfo = new StaffTotalInfo(); 
		
		for (int j=0; j<dataList.size(); j++)
	{
		if(dataList.get(j)[6].toString().equals(periodHeaderList.get(i).getYear()) && dataList.get(j)[7].toString().equals((WhmUtilities.findMonthDigit(periodHeaderList.get(i).getMonth())))){
			
			tmpInfo.setYear(dataList.get(j)[6].toString());
			tmpInfo.setMonth(dataList.get(j)[7].toString());
			tmpInfo.setTotalHours(Double.parseDouble(dataList.get(j)[3].toString()));
			tmpInfo.setTotalMM(Double.parseDouble(dataList.get(j)[4].toString()));
			break;
		}
	}
		staffTotalInfoList.add(tmpInfo);
	}
}

public void prepareView(List<Object[]> dataList){
	String null_value="9999";
	for (int i=0; i<projectIDList.size(); i++)
	{
		StaffSummaryInfo summaryInfo = new StaffSummaryInfo();
		summaryInfo.setStaffID(selectStaff.getUsername());
		summaryInfo.setStaffName(selectStaff.getFullName());
		
		if(!projectIDList.get(i).toString().equals(null_value))
			summaryInfo.setProjectID(projectIDList.get(i));
		else
			summaryInfo.setProjectID("-");
		
		for (int j=0; j<periodHeaderList.size(); j++)
		{
			StaffDetailInfo detailInfo = new StaffDetailInfo();
			detailInfo.setYear(periodHeaderList.get(j).getYear());
			detailInfo.setMonth(periodHeaderList.get(j).getMonth());
			
			if(!projectIDList.get(i).toString().equals(null_value))
				detailInfo.setProjectID(projectIDList.get(i));
			else
				detailInfo.setProjectID("-");	
			
			detailInfo.setProjectName("-");
			detailInfo.setTotal_hours(0.0);
			detailInfo.setTotal_mm(0.0);
			
			dataLoop :	for (int k=0; k<dataList.size(); k++){
			if(!projectIDList.get(i).toString().equals(null_value) && !dataList.get(k)[3].toString().equals(null_value))
			{
				
				if(projectIDList.get(i).equals(dataList.get(k)[3].toString()) && dataList.get(k)[6].toString().equals(periodHeaderList.get(j).getYear()) && dataList.get(k)[7].toString().equals((WhmUtilities.findMonthDigit(periodHeaderList.get(j).getMonth())))){
					
					detailInfo.setYear(dataList.get(k)[6].toString());
					detailInfo.setMonth(dataList.get(k)[6].toString());
					if(dataList.get(k)[3].toString().equals(null_value)){
						detailInfo.setProjectID("-");
					}else{
						detailInfo.setProjectID(dataList.get(k)[3].toString());	
					}
					
					
					if(dataList.get(k)[4]!=null)					
					detailInfo.setProjectName(dataList.get(k)[4].toString());
					
						
					detailInfo.setTotal_hours(Double.parseDouble(dataList.get(k)[5].toString()));
					detailInfo.setTotal_mm(Double.parseDouble(dataList.get(k)[8].toString()));
				
					if(dataList.get(k)[4]!=null)
					summaryInfo.setProjectName(dataList.get(k)[4].toString());
					
					break dataLoop;
				}
												
			}
			else{
				if(projectIDList.get(i).toString().equals(null_value) && dataList.get(k)[3].toString().equals(null_value)){
					
					if(dataList.get(k)[6].toString().equals(periodHeaderList.get(j).getYear()) && dataList.get(k)[7].toString().equals((WhmUtilities.findMonthDigit(periodHeaderList.get(j).getMonth())))){

						detailInfo.setProjectID("-");
						detailInfo.setProjectName("-");
						
						detailInfo.setTotal_hours(Double.parseDouble(dataList.get(k)[5].toString()));
						detailInfo.setTotal_mm(Double.parseDouble(dataList.get(k)[8].toString()));
						
						break dataLoop;
					}
				}
				
			}				
		}			
			staffDetailInfoList.add(detailInfo);
		}
		summaryInfo.setStaffDetailInfoList(staffDetailInfoList);
		staffSummaryInfoList.add(summaryInfo);		
	}
	
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

filename = name.append("(DAT)_Working Hour Management Individual").append(
		".xlsx");


if (staffSummaryInfoList.size() <= 0) {
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
	int startColumn = 1;
	XSSFWorkbook workbook;
	
	ExternalContext externalContext = FacesContext.getCurrentInstance()
			.getExternalContext();
	ServletContext context = (ServletContext) externalContext.getContext();
	
	try {
	// Template Excel File
	File file = new File(context.getRealPath("/template/WorkingHourManagementIndividualTemplate.xlsx"));
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
	
	for (int i = 0; i < periodHeaderList.size(); i++) {
		sheetName.addMergedRegion(new CellRangeAddress(0, 0, startColumn + 3, startColumn + 4));
		
		Cell yearMonthCell = row.createCell(startColumn+3);
		yearMonthCell.setCellValue(periodHeaderList.get(i).getYear()+"-"+periodHeaderList.get(i).getMonth());
		yearMonthCell.setCellStyle(headerStyle);
	
		Cell cell = rowTwo.createCell(startColumn + 3);
		cell.setCellValue("Hour");
		cell.setCellStyle(headerStyle);
		
		cell = rowTwo.createCell(startColumn + 4);
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
    
    exportExcelData(rowTwo, sheetName, startColumn, cellStyle,workbook);
    
    sheetName.createFreezePane(4, 2);	
    workbook.write(outputData);
	outputData.flush();
	outputData.close();
	
	setDownloadFileName(context.getRealPath("/upload/" + filename));
	setExportFileName(filename.toString());
	
	}catch (IOException e) {
		throw new HPSFException(e.getMessage());
	}
}

public void exportExcelData(Row row, XSSFSheet sheetName, int startColumn,
		CellStyle cellStyle,XSSFWorkbook workbook) {




	int count = 0;
	sheetName.addMergedRegion(new CellRangeAddress(
			2, projectIDList.size() +2 , 0, 0));
	sheetName.addMergedRegion(new CellRangeAddress(
			2, projectIDList.size() +2 , 1, 1));
	
	for (StaffSummaryInfo staffinfo : staffSummaryInfoList) {
		row = sheetName.createRow(count + 2);
		int start_column = 3;
		
		
		
		
		Cell cell = row.createCell(0);
		cell.setCellValue(selectStaff.getUsername());
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell(1);
		cell.setCellValue(selectStaff.getFullName());
		cell.setCellStyle(cellStyle);
		

		cell = row.createCell(2);
		cell.setCellValue(staffinfo.getProjectID());
		cell.setCellStyle(cellStyle);

		cell = row.createCell(3);
		cell.setCellValue(staffinfo.getProjectName());
		cell.setCellStyle(cellStyle);		
	
			
				
				for (StaffDetailInfo staffdetailinfo : staffinfo.getStaffDetailInfoList()) {
					if(staffinfo.getProjectID().equals(staffdetailinfo.getProjectID())){
				
						
				
				cell = row.createCell(start_column + 1);
				cell.setCellValue(staffdetailinfo.getTotal_hours());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
				
				cell = row.createCell(start_column + 2);
				cell.setCellValue(staffdetailinfo.getTotal_mm());
				cell.setCellStyle(cellStyle);
				cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
				start_column = start_column + 2;
				
				
}

				}
				
				count++;

	}
	
	Row totalrow = sheetName.createRow(count + 2);
	int total_start_column = 3;
	sheetName.addMergedRegion(new CellRangeAddress(
			count+2, count+2 , 2, 3));
	for (StaffTotalInfo stafftotalinfo : staffTotalInfoList) {
		
		Cell cell = totalrow.createCell(0);
		cell.setCellValue("");
		cell.setCellStyle(cellStyle);

		cell = totalrow.createCell(1);
		cell.setCellValue("");
		cell.setCellStyle(cellStyle);
		
		cell = totalrow.createCell(2);
		cell.setCellValue("Total");
		cell.setCellStyle(cellStyle);
		
		cell = totalrow.createCell(3);
		cell.setCellValue("");
		cell.setCellStyle(cellStyle);
		
		cell = totalrow.createCell(total_start_column + 1);
		cell.setCellValue(stafftotalinfo.getTotalHours());
		cell.setCellStyle(cellStyle);
		cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
		
		cell = totalrow.createCell(total_start_column + 2);
		cell.setCellValue(stafftotalinfo.getTotalMM());
		cell.setCellStyle(cellStyle);
		cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
		total_start_column = total_start_column + 2;
		
		
	}

}
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ISummaryActivityService getSummaryActivityService() {
		return summaryActivityService;
	}

	public void setSummaryActivityService(
			ISummaryActivityService summaryActivityService) {
		this.summaryActivityService = summaryActivityService;
	}

	public User getSelectStaff() {
		return selectStaff;
	}

	public void setSelectStaff(User selectStaff) {
		this.selectStaff = selectStaff;
	}

	public List<User> getusers() {
		return users;
	}

	public void setusers(List<User> users) {
		this.users = users;
	}

	public User getselectStaff() {
		return selectStaff;
	}

	public void setselectStaff(User selectStaff) {
		this.selectStaff = selectStaff;
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

	public boolean isSearchBtnCtl() {
		return searchBtnCtl;
	}

	public void setSearchBtnCtl(boolean searchBtnCtl) {
		this.searchBtnCtl = searchBtnCtl;
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
	
	public void setResultList(List<Object[]> resultList) {
		this.resultList = resultList;
	}
	
	public List<Object[]> getResultList() {
		return resultList;
	}
	public List<String> getProjectIDList() {
		return projectIDList;
	}
	public void setProjectIDList(List<String> projectIDList) {
		this.projectIDList = projectIDList;
	}

	public List<TableHeader> getPeriodHeaderList() {
		return periodHeaderList;
	}

	public void setPeriodHeaderList(List<TableHeader> periodHeaderList) {
		this.periodHeaderList = periodHeaderList;
	}

	public List<StaffDetailInfo> getStaffDetailInfoList() {
		return staffDetailInfoList;
	}

	public void setStaffDetailInfoList(List<StaffDetailInfo> staffDetailInfoList) {
		this.staffDetailInfoList = staffDetailInfoList;
	}

	public List<StaffSummaryInfo> getStaffSummaryInfoList() {
		return staffSummaryInfoList;
	}

	public void setStaffSummaryInfoList(List<StaffSummaryInfo> staffSummaryInfoList) {
		this.staffSummaryInfoList = staffSummaryInfoList;
	}

	public boolean isShowTable() {
		return showTable;
	}

	public void setShowTable(boolean showTable) {
		this.showTable = showTable;
	}

	public double getTotal_hour() {
		return total_hour;
	}

	public void setTotal_hour(double total_hour) {
		this.total_hour = total_hour;
	}

	public double getTotal_mm() {
		return total_mm;
	}

	public void setTotal_mm(double total_mm) {
		this.total_mm = total_mm;
	}

	public List<Object[]> getTotalResultList() {
		return totalResultList;
	}

	public void setTotalResultList(List<Object[]> totalResultList) {
		this.totalResultList = totalResultList;
	}

	public List<StaffTotalInfo> getStaffTotalInfoList() {
		return staffTotalInfoList;
	}

	public void setStaffTotalInfoList(List<StaffTotalInfo> staffTotalInfoList) {
		this.staffTotalInfoList = staffTotalInfoList;
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

	public boolean isExportflag() {
		return exportflag;
	}

	public void setExportflag(boolean exportflag) {
		this.exportflag = exportflag;
	}

	public boolean isShowbtn() {
		return showbtn;
	}

	public void setShowbtn(boolean showbtn) {
		this.showbtn = showbtn;
	}
	
	
	
}
