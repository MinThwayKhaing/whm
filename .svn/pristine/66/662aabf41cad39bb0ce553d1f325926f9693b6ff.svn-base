/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2018-10-01
 * @Version 1.0
 * @Purpose <<Working Hour Management by Team Detail >>
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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

import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.team.entity.Team;
import com.dat.whm.web.common.WhmUtilities;

@ManagedBean(name = "WorkingHourManagementTeamDetailBean")
@ViewScoped
public class WorkingHourManagementTeamDetailBean {
	private HashMap<String, Object> detailData;
	
	/*View Data*/
	private List<WorkingHourManagementTeamView> viewData;
	private Team selectTeam;
	private List<String> rowRepeat;
	private List<String> yearMonth;
	private ProjectDivision totalRow;
	private Map<String,Double> yearMonthMap;
	
	/*Export File*/
	private DefaultStreamedContent download;
	private String downloadFileName;
	private String exportFileName;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		rowRepeat = new ArrayList<>(1);
		rowRepeat.add("");
		this.detailData = (HashMap<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("detailData");
		this.viewData = (List<WorkingHourManagementTeamView>) detailData.get("viewData");
		this.selectTeam = (Team) detailData.get("selectTeam");
		this.yearMonth = (List<String>) detailData.get("yearMonth");
		this.yearMonthMap = (Map<String, Double>) detailData.get("yearMonthMap");
		prepareViewData();
	}
	
	private void prepareViewData() {
		double hourPerMonth = 0.0;
		LinkedHashMap<String, YearMonthDivision> totalSumContainer = new LinkedHashMap<>();
		
		for (WorkingHourManagementTeamView workingHourManagementTeamView : this.viewData) {
			int totalRowCount = 0;
			for (ProjectDivision projectDivision : workingHourManagementTeamView.getProjectDivision()) {
				List<SummaryActivity> detailActivities = projectDivision.getDetailActivities();
				List<String> staffIDList = getStaffIDList(detailActivities);
				projectDivision.setStaffDivision(new ArrayList<StaffDivision>());
				for (String staffId : staffIDList) {
					StaffDivision staffDivision = new StaffDivision();
					staffDivision.setStaffId(staffId);
					staffDivision.setYearMonthDivision(new ArrayList<YearMonthDivision>());
					for (Map.Entry<String, Double>  yearMonth : this.yearMonthMap.entrySet()) {
						double hour = 0.0;

						for (SummaryActivity dailyActivity : detailActivities) {
							String reportDate = dailyActivity.getYear() + "-" +  WhmUtilities.findMonthByDigit(dailyActivity.getMonth()).substring(0, 3);

							if (yearMonth.getKey().equals(reportDate) && staffId.equals(dailyActivity.getUsername())) {
								staffDivision.setStaffName(dailyActivity.getFullname()); //Edit user's fullname column
								hour = hour + dailyActivity.getActivityHours();
							}
						}
						hourPerMonth = yearMonth.getValue();
						double manMonth=0.0;
				    	if (hourPerMonth > 0.0) {
				    		manMonth = hour/hourPerMonth;
						}
						staffDivision.getYearMonthDivision().add(new YearMonthDivision(0, hour, manMonth));
						
						
						if (!totalSumContainer.containsKey(yearMonth.getKey())) {
				    		totalSumContainer.put(yearMonth.getKey(), new YearMonthDivision(0, hour, manMonth));
						}else {
							totalSumContainer.get(yearMonth.getKey()).addMember(0);
							totalSumContainer.get(yearMonth.getKey()).addHour(hour);
							totalSumContainer.get(yearMonth.getKey()).addManMonth(manMonth);
						}
					}
					projectDivision.getStaffDivision().add(staffDivision);
				}
				totalRowCount = totalRowCount + (projectDivision.getStaffDivision().size() + 1);
			}
			workingHourManagementTeamView.setTotalRowCount(totalRowCount);
		}
		
		this.totalRow = new ProjectDivision();
		this.totalRow.setYearMonthDivision(new ArrayList<YearMonthDivision>());
		for (String yearMonth : totalSumContainer.keySet()) {
			this.totalRow.getYearMonthDivision().add(totalSumContainer.get(yearMonth));
		}
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
							"There is no data to export"));
		}
	}
	
	/* Set up excel file name and call export data to excel method. */
	private boolean exportTeamReport() throws FileNotFoundException, IOException, HPSFException {

		StringBuilder name = new StringBuilder();
		StringBuilder filename = new StringBuilder();
		
		if (selectTeam != null) {
			filename = name.append("(DAT)_Working Hour Management Team(Detail)_")
					.append(selectTeam.getTeamName()).append(".xlsx");
		} else {
			filename = name.append("(DAT)_Working Hour Management Team(Detail)").append(
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
			File file = new File(context.getRealPath("/template/WorkingHourManagementTeamDetailTemplate.xlsx"));
			FileInputStream fIP = new FileInputStream(file);
			workbook = new XSSFWorkbook(fIP);

			File outputFile = new File(context.getRealPath("/upload/" + filename));
			FileOutputStream outputData = new FileOutputStream(outputFile);

			XSSFSheet sheetName = workbook.getSheet("Sheet1");
			
			CellStyle headerStyle = workbook.createCellStyle();
	        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        headerStyle.setWrapText(true);
	        
			Row row = sheetName.getRow(startRow);
			Row rowTwo = sheetName.getRow(startRow + 1);
			Cell headCell = row.getCell(0);
			headerStyle = headCell.getCellStyle();
			
			/*Header Year Month*/
			for (int i = 0; i < yearMonth.size(); i++) {
				sheetName.addMergedRegion(new CellRangeAddress(0, 0, startColumn + 5, startColumn + 6));
				
				Cell yearMonthCell = row.createCell(startColumn+5);
				yearMonthCell.setCellValue(yearMonth.get(i));
				yearMonthCell.setCellStyle(headerStyle);
				
				Cell cell = rowTwo.createCell(startColumn + 5);
				cell.setCellValue("Hour");
				cell.setCellStyle(headerStyle);
				
				cell = rowTwo.createCell(startColumn + 6);
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
	        
	        for (WorkingHourManagementTeamView exportData : viewData) {
	        	row = sheetName.createRow(startRow + 2);
	        	Cell cell = row.createCell(0);
				
				List<ProjectDivision> projectDivision = exportData.getProjectDivision();
				for (int i = 0; i < projectDivision.size(); i++) {
					int startMerged = row.getRowNum();
					List<StaffDivision> staffDivision = projectDivision.get(i).getStaffDivision();
					startColumn = 4;
					
					for (int j = 0; j < staffDivision.size(); j++) {
						/*Team*/
						cell = row.createCell(0);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(exportData.getTeamId());
						
						/*Members*/
						cell = row.createCell(1);
						cell.setCellValue(exportData.getMembers());
						cell.setCellStyle(cellStyle);
						
						/*Project ID*/
						cell = row.createCell(2);
						cell.setCellValue(projectDivision.get(i).getProjectId());
						cell.setCellStyle(cellStyle);
						
						/*Project Name*/
						cell = row.createCell(3);
						cell.setCellValue(projectDivision.get(i).getProjectName());
						cell.setCellStyle(cellStyle);
						
						/*Staff ID*/
						cell = row.createCell(startColumn);
						cell.setCellValue(staffDivision.get(j).getStaffId());
						cell.setCellStyle(cellStyle);
						
						/*Staff Name*/
						cell = row.createCell(startColumn + 1);
						cell.setCellValue(staffDivision.get(j).getStaffName());
						cell.setCellStyle(cellStyle);
						
						List<YearMonthDivision> yearMonthDivision = staffDivision.get(j).getYearMonthDivision();
						int column = 6;
						for (int k = 0; k < yearMonthDivision.size(); k++) {
							/*Total Hour*/
							cell = row.createCell(column);
							cell.setCellValue(yearMonthDivision.get(k).getHour());
							cell.setCellStyle(cellStyle);
							cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
							
							/*Man-Month*/
							cell = row.createCell(column+1);
							cell.setCellValue(yearMonthDivision.get(k).getManMonth());
							cell.setCellStyle(cellStyle);
							cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
							
							column = column +2;
						}
						row = sheetName.createRow(row.getRowNum()+1);
					}
					sheetName.addMergedRegion(new CellRangeAddress(startMerged, row.getRowNum() -1, 2, 2));
					sheetName.addMergedRegion(new CellRangeAddress(startMerged, row.getRowNum() -1, 3, 3));
				}

				sheetName.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 5));
				/*Team Set Cell Style*/
				cell = row.createCell(0);
				cell.setCellStyle(cellStyle);
				
				/*Members Set Cell Style*/
				cell = row.createCell(1);
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(2);
				cell.setCellValue("Total");
				cell.setCellStyle(cellStyle);
				
				/*Set Cell Style*/
				cell = row.createCell(3);
				cell.setCellStyle(cellStyle);
				cell = row.createCell(4);
				cell.setCellStyle(cellStyle);
				cell = row.createCell(5);
				cell.setCellStyle(cellStyle);
				
				List<YearMonthDivision> yearMonthDivision = totalRow.getYearMonthDivision();
				int column = 6;
				for (int i = 0; i < yearMonthDivision.size(); i++) {
					
					cell = row.createCell(column);
					cell.setCellValue(yearMonthDivision.get(i).getHour());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
					
					cell = row.createCell(column+1);
					cell.setCellValue(yearMonthDivision.get(i).getManMonth());
					cell.setCellStyle(cellStyle);
					cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat("#,##0.000"));
					
					column = column + 2;
				}
				sheetName.addMergedRegion(new CellRangeAddress(2, row.getRowNum(), 0, 0));
				sheetName.addMergedRegion(new CellRangeAddress(2, row.getRowNum(), 1, 1));
				sheetName.createFreezePane(6, 2);
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
	
	private List<String> getStaffIDList(List<SummaryActivity> activities){
		List<String> staffIDList = new ArrayList<>();
		for (SummaryActivity activity : activities) {
			String staff= activity.getUsername(); //Edit user's fullname column
			if (!staffIDList.contains(staff)) {
				staffIDList.add(staff);
			}
		}
		return staffIDList;
	}
	
	/*Back to MNR007*/
	public String back(){
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("detailData", this.detailData);
		
		return "/faces/view/mgtreports/MNR007.xhtml?faces-redirect=true";
	}
	
	/* Getters and Setters. */
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

}
