/***************************************************************************************
 * @author Aye Chan Thar Soe
 * @Date 2017-03-31
 * @Version 1.0
 * @Purpose <<Intend for DAR003.xhtml search page, Can export Excel Document for Daily Report by searching result>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.web.dailyreport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.hpsf.HPSFException;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDataValidationHelper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.primefaces.model.DefaultStreamedContent;

import com.dat.whm.approval.entity.Approval;
import com.dat.whm.approval.service.interfaces.IApprovalService;
import com.dat.whm.common.entity.LeaveStatus;
import com.dat.whm.common.entity.TaskStatus;
import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.service.interfaces.IDailyActivityService;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.ExportExcelData;
import com.dat.whm.web.common.SearchDate;

@ManagedBean(name = "DailyReportExcelExportBean")
@RequestScoped
public class DailyReportExcelExportBean extends BaseBean {

	@ManagedProperty(value = "#{ApprovalService}")
	private IApprovalService approvalService;
	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{DailyActivityService}")
	private IDailyActivityService dailyActivityService;
	private DailyReport dailyReport;
	private DailyActivity dailyActivity;
	private List<DailyReport> dailyReportList = new ArrayList<DailyReport>();
	private List<DailyActivity> dailyActivityList = new ArrayList<DailyActivity>();

	SearchDate searchDate;
	User staffData;
	User loginUser;

	int dailyReportListSize;
	boolean display;
	private DefaultStreamedContent download;
	String downloadFileName;
	String exportFileName;

	private List<ExportExcelData> dataToShow;

	@PostConstruct
	public void init() {
		dataToShow = new ArrayList<ExportExcelData>();
		prepareSearchData();
	}

	public void prepareSearchData() {
		dailyReport = new DailyReport();
		searchDate = new SearchDate();
		staffData = new User();

		loginUser = new User();
		loginUser = (User) getParam(Constants.LOGIN_USER);

		if ((loginUser.getRole() != Role.ADMIN) && (loginUser.getRole() != Role.MANAGEMENT)) {
			setDisplay(false);
			setStaffData(loginUser);
			dailyReport.setStaffID(staffData);
		} else {
			setDisplay(true);
			dailyReport.setStaffID(staffData);
		}

	}

	public boolean exportDailyReport() throws FileNotFoundException, IOException, HPSFException {

		Date fromDate = searchDate.getFromDate();
		Date toDate = searchDate.getToDate();

		SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
		String fdate = format.format(fromDate);
		String edate = format.format(toDate);

		StringBuilder name = new StringBuilder();

		loginUser = (User) getParam(Constants.LOGIN_USER);
		if ((loginUser.getRole() != Role.ADMIN) && (loginUser.getRole() != Role.MANAGEMENT)) {

			setStaffData(loginUser);
			dailyReport.setStaffID(loginUser);
			dailyReport.getStaffID().setId(loginUser.getId());
			dailyReport.getStaffID().setUsername(loginUser.getUsername());
			dailyReport.getStaffID().setFullName(loginUser.getFullName());

		}

		// Get Report ID Lists order by activity_date
		dailyReportList = dailyReportService.findReportIDListforExcel(dailyReport, searchDate);

		StringBuilder reportFileName = name.append(dailyReport.getStaffID().getUsername()).append("-").append(fdate)
				.append("-").append(edate).append(".xls");

		List<ExportExcelData> allDaysData = new ArrayList<ExportExcelData>();
		ExportExcelData oneDayData = new ExportExcelData();
		List<DailyActivity> activityListByReportID = new ArrayList<DailyActivity>();
		SimpleDateFormat approvalDateFormat = new SimpleDateFormat("yyyy-M-dd");
		if (dailyReportList.size() > 0) {

			for (DailyReport reportID : dailyReportList) {

				// get daily activity list for one day
				activityListByReportID = dailyActivityService.findActivityByReportID(reportID);

				for (DailyActivity activityOneDayData : activityListByReportID) {
					oneDayData = new ExportExcelData();

					oneDayData.setActivity_date(reportID.getActivityDate());
					oneDayData.setReport_ID(reportID.getId());
					oneDayData.setFullName(activityOneDayData.getDailyReport().getStaffID().getFullName());

					if (activityOneDayData.getProject() != null) {
						oneDayData.setProjectID(activityOneDayData.getProject().getId());
					} else
						oneDayData.setProjectID(" - ");

					if (activityOneDayData.getWbs() != null)
						oneDayData.setWbsNo(activityOneDayData.getWbs());
					else
						oneDayData.setWbsNo("");

					if (activityOneDayData.getWbsFunction() != null)
						oneDayData.setFunctionID(activityOneDayData.getWbsFunction());
					else
						oneDayData.setFunctionID("");

					oneDayData.setCategory(activityOneDayData.getTask().getDescription());
					oneDayData.setActivities(activityOneDayData.getTaskActivity().getDescription());
					oneDayData.setDescription(activityOneDayData.getTaskDescription());

					if (activityOneDayData.getDeliveryOutput() != null)
						oneDayData.setDeliDoc(activityOneDayData.getDeliveryOutput());
					else
						oneDayData.setDeliDoc("");

					if (activityOneDayData.getReason() != null)
						oneDayData.setReason(activityOneDayData.getReason());
					else
						oneDayData.setReason("");

					if (activityOneDayData.getProgressPercentage() != null)
						oneDayData.setProgress(activityOneDayData.getProgressPercentage());
					else
						oneDayData.setProgress(0.0);

					oneDayData.setUserName(activityOneDayData.getDailyReport().getStaffID().getUsername());
					oneDayData.setHour(activityOneDayData.getActivityHours());

					if (activityOneDayData.getTaskStatus() != null) {
						oneDayData.setTaskStatusCount(1.0);
						if (activityOneDayData.getTaskStatus() == TaskStatus.FORWARD)
							oneDayData.setTask_status("FORWARD");
						else
							oneDayData.setTask_status("DELAY");
					} else {

						oneDayData.setTask_status("");
						oneDayData.setTaskStatusCount(0.0);
					}

					if (activityOneDayData.getTaskStatusCount() != null) {
						oneDayData.setTaskStatusCount(activityOneDayData.getTaskStatusCount());
					}

					oneDayData.setNullDate(" ");
					if (activityOneDayData.getBeginDate() != null) {
						oneDayData.setBeginDate(activityOneDayData.getBeginDate());
					}
					if (activityOneDayData.getEndDate() != null) {
						oneDayData.setEndDate(activityOneDayData.getEndDate());
					}
					//for approval
					oneDayData.setActivity_date(reportID.getActivityDate());

					List<Approval> result = null;
					result = approvalService.findApprovalByReportID(reportID);
					if (!result.isEmpty()) {
						Date approvalDate = result.get(0).getLastUpdDate();
						oneDayData.setApproval(result.get(0).getApprover().getFullName()+" "+approvalDateFormat.format(approvalDate));
					} else
						oneDayData.setApproval("");

					// total activity lists
					allDaysData.add(oneDayData);

				}
			}

		}
		if (allDaysData.size() <= 0) {
			setDownloadFileName("");
			setExportFileName("");
			return false;
		} else {
			exportDatatoExcel(allDaysData, searchDate, reportFileName);
			return true;
		}
	}

	public void exportDatatoExcel(List<ExportExcelData> allDaysData, SearchDate searchDate,
			StringBuilder reportFileName) throws FileNotFoundException, HPSFException {

		HSSFWorkbook workbook;
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext context = (ServletContext) externalContext.getContext();

		try {
			// Template Excel File
			File file = new File(context.getRealPath("/template/DailyReportTemplate.xls"));
			FileInputStream fIP = new FileInputStream(file);
			workbook = new HSSFWorkbook(fIP);

			File outputFile = new File(context.getRealPath("/upload/" + reportFileName));
			FileOutputStream outputData = new FileOutputStream(outputFile);

			HSSFSheet sheet = workbook.getSheet("Report");
			int approvalFirstRow = 5, approvalLastRow = 0;

			// Start header value
			Cell fromDateCell = sheet.getRow(1).getCell(7);
			fromDateCell.setCellValue(searchDate.getFromDate());

			Cell toDateCell = sheet.getRow(1).getCell(11);
			toDateCell.setCellValue(searchDate.getToDate());

			Cell reportDateCell = sheet.getRow(1).getCell(49);
			reportDateCell.setCellValue(searchDate.getToDate());

			Cell writerCell = sheet.getRow(1).getCell(54);
			writerCell.setCellValue(allDaysData.get(0).getFullName());

			int increaseRowCount = 5;
			Date tempDate = null;

			int from = 8;
			int to = 9;

			// To get the size of the week activities.
			int totalAllDaysDataSize = (allDaysData.size() * 2) - 4;
			int copyValidation = totalAllDaysDataSize + 9;

			if (allDaysData.size() > 2) {

				for (int j = 0; j < totalAllDaysDataSize; j++) {

					copyRow(sheet, from, to);
					from++;
					to++;
				}
				catagoryValidation(workbook, sheet, copyValidation);
				activitiesValidation(workbook, sheet, copyValidation);
			}

			Cell data = sheet.getRow(5).getCell(0);
			data.setCellValue(allDaysData.get(0).getActivity_date());

			SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
			Cell dayCell = sheet.getRow(6).getCell(0);
			dayCell.setCellValue(simpleDateformat.format(allDaysData.get(0).getActivity_date()));

			for (int i = 0; i < allDaysData.size(); i++) {

				if (i == 0) {

					tempDate = allDaysData.get(0).getActivity_date();
				}

				if (allDaysData.get(i).getActivity_date() != tempDate) {

					/// For the last Activities

					approvalLastRow = increaseRowCount - 1;
					sheet.addMergedRegion(new CellRangeAddress(approvalFirstRow, approvalLastRow, 53, 54));
					sheet.addMergedRegion(new CellRangeAddress(approvalFirstRow, approvalLastRow, 55, 56));
					sheet.addMergedRegion(new CellRangeAddress(approvalFirstRow, approvalLastRow, 57, 58));
					approvalFirstRow = increaseRowCount;

					tempDate = allDaysData.get(i).getActivity_date();

					// To set the solid line at the day by day
					HSSFRow newRow = sheet.getRow(increaseRowCount);
					HSSFRow sourceRow = sheet.getRow(5);

					for (int l = 0; l < sourceRow.getLastCellNum(); l++) {

						// Grab a copy of the old/new cell
						HSSFCell oldCell = sourceRow.getCell(l);
						HSSFCell newCell = newRow.createCell(l);

						// Copy style from old cell and apply to new cell
						HSSFCellStyle newCellStyle = workbook.createCellStyle();
						newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
						newCell.setCellStyle(newCellStyle);

					}

					// To show the date for each day by day
					Cell date = sheet.getRow(increaseRowCount).getCell(0);
					date.setCellValue(allDaysData.get(i).getActivity_date());
					Cell day = sheet.getRow(increaseRowCount + 1).getCell(0);
					day.setCellValue(simpleDateformat.format(allDaysData.get(i).getActivity_date()));
				}

				Cell prjID = sheet.getRow(increaseRowCount).getCell(2);
				prjID.setCellValue(allDaysData.get(i).getProjectID());

				Cell wbsNo = sheet.getRow(increaseRowCount).getCell(5);
				wbsNo.setCellValue(allDaysData.get(i).getWbsNo());

				Cell funID = sheet.getRow(increaseRowCount).getCell(8);
				funID.setCellValue(allDaysData.get(i).getFunctionID());

				Cell category = sheet.getRow(increaseRowCount).getCell(11);
				category.setCellValue(allDaysData.get(i).getCategory());

				Cell activities = sheet.getRow(increaseRowCount).getCell(17);
				activities.setCellValue(allDaysData.get(i).getActivities());

				Cell description = sheet.getRow(increaseRowCount).getCell(20);
				description.setCellValue(allDaysData.get(i).getDescription());

				Cell deliDoc = sheet.getRow(increaseRowCount).getCell(29);
				deliDoc.setCellValue(allDaysData.get(i).getDeliDoc());

				Cell beginDate = sheet.getRow(increaseRowCount).getCell(38);
				if(allDaysData.get(i).getBeginDate() != null){
					beginDate.setCellValue(allDaysData.get(i).getBeginDate());
				}else{
					beginDate.setCellValue(allDaysData.get(i).getNullDate());
				}

				Cell endDate = sheet.getRow(increaseRowCount).getCell(40);
				if(allDaysData.get(i).getEndDate() != null){
					endDate.setCellValue(allDaysData.get(i).getEndDate());
				}else{
					beginDate.setCellValue(allDaysData.get(i).getNullDate());
				}

				Cell progress = sheet.getRow(increaseRowCount).getCell(42);
				if (allDaysData.get(i).getProgress() != 0.0) {
					progress.setCellValue(allDaysData.get(i).getProgress() + " %");
				} else
					progress.setCellValue("");

				Cell hour = sheet.getRow(increaseRowCount).getCell(44);
				hour.setCellValue(allDaysData.get(i).getHour());


				CellStyle style1 = workbook.createCellStyle();
		        Font font1 = workbook.createFont();
		        font1.setColor(HSSFColor.BLUE.index);
		        font1.setFontHeightInPoints((short)8);
		        font1.setFontName("Tahoma");
		        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style1.setFont(font1);


		        CellStyle style2 = workbook.createCellStyle();
		        Font font2 = workbook.createFont();
		        font2.setColor(HSSFColor.RED.index);
		        font2.setFontHeightInPoints((short)8);
		        font2.setFontName("Tahoma");
		        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		        style2.setFont(font2);


		        Cell delayForward = sheet.getRow(increaseRowCount).getCell(45);

		        Cell firstCell=sheet.getRow(increaseRowCount).getCell(0);

		        if(firstCell.getCellType() == 0){
		        	style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		        	style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		        }
		        else{
		        	style1.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
					style2.setBorderTop(HSSFCellStyle.BORDER_DOTTED);
				}

				if(allDaysData.get(i).getTask_status()=="FORWARD"){
					delayForward.setCellValue(allDaysData.get(i).getTaskStatusCount());
					delayForward.setCellStyle(style1);

				}
				if(allDaysData.get(i).getTask_status()=="DELAY"){
					delayForward.setCellValue(allDaysData.get(i).getTaskStatusCount());
					delayForward.setCellStyle(style2);
				}

				Cell reason = sheet.getRow(increaseRowCount).getCell(47);
				reason.setCellValue(allDaysData.get(i).getReason());

				Cell approval = sheet.getRow(increaseRowCount).getCell(53);
				approval.setCellValue(allDaysData.get(i).getApproval());

				increaseRowCount += 2;

			}

			/// For the last Activities
			approvalLastRow = increaseRowCount - 1;
			sheet.addMergedRegion(new CellRangeAddress(approvalFirstRow, approvalLastRow, 53, 54));
			sheet.addMergedRegion(new CellRangeAddress(approvalFirstRow, approvalLastRow, 55, 56));
			sheet.addMergedRegion(new CellRangeAddress(approvalFirstRow, approvalLastRow, 57, 58));

			workbook.write(outputData);
			outputData.flush();
			outputData.close();

			setDownloadFileName(context.getRealPath("/upload/" + reportFileName));
			setExportFileName(reportFileName.toString());

		} catch (IOException e) {
			throw new HPSFException(e.getMessage());
		}

	}

	public boolean isValidExcelExportFromAndToDate(Date fromDate, Date toDate) {

		if (!(fromDate.equals(toDate) || fromDate.before(toDate))) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "From Date must not greater than ToDate"));
			return false;
		}

		long diff = toDate.getTime() - fromDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);

		if (diffDays > 6) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Date Difference must be within 7 days."));
			return false;
		}

		return true;
	}

	public void prepDownload() throws Exception {

		if (isValidExcelExportFromAndToDate(searchDate.getFromDate(), searchDate.getToDate())) {
			if (exportDailyReport()) {

				File file = new File(getDownloadFileName());
				FileInputStream fileInputStream = new FileInputStream(file);
				byte[] bfile = new byte[(int) file.length()];

				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				fileInputStream.read(bfile);
				fileInputStream.close();

				InputStream stream = new ByteArrayInputStream(bfile);
				setDownload(new DefaultStreamedContent(stream, externalContext.getMimeType(file.getName()),
						getExportFileName()));

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "There is no data to export"));
			}
		}
	}

	public void showData() throws Exception {

		if (isValidExcelExportFromAndToDate(searchDate.getFromDate(), searchDate.getToDate())) {
			//Date fromDate = searchDate.getFromDate();
			//Date toDate = searchDate.getToDate();
			SimpleDateFormat approvalDateFormat = new SimpleDateFormat("yyyy-M-dd");
			//SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
			//String fdate = format.format(fromDate);
			//String edate = format.format(toDate);

			//StringBuilder name = new StringBuilder();

			loginUser = (User) getParam(Constants.LOGIN_USER);
			if ((loginUser.getRole() != Role.ADMIN) && (loginUser.getRole() != Role.MANAGEMENT)) {

				setStaffData(loginUser);
				dailyReport.setStaffID(loginUser);
				dailyReport.getStaffID().setId(loginUser.getId());
				dailyReport.getStaffID().setUsername(loginUser.getUsername());
				dailyReport.getStaffID().setFullName(loginUser.getFullName());

			}

			// Get Report ID Lists order by activity_date
			dailyReportList = dailyReportService.findReportIDListforExcel(dailyReport, searchDate);

			List<ExportExcelData> allDaysData = new ArrayList<ExportExcelData>();
			ExportExcelData oneDayData = new ExportExcelData();
			List<DailyActivity> activityListByReportID = new ArrayList<DailyActivity>();

			if (dailyReportList.size() > 0) {

				for (DailyReport reportID : dailyReportList) {

					// get daily activity list for one day
					activityListByReportID = dailyActivityService.findActivityByReportID(reportID);
					boolean oneDay = true;

					for (DailyActivity activityOneDayData : activityListByReportID) {
						oneDayData = new ExportExcelData();

						if (oneDay) {
							oneDayData.setActivity_date(reportID.getActivityDate());

							List<Approval> result = null;
							result = approvalService.findApprovalByReportID(reportID);
							if (!result.isEmpty()) {
								Date approvalDate = result.get(0).getLastUpdDate();
								oneDayData.setApproval(result.get(0).getApprover().getFullName()+" "+approvalDateFormat.format(approvalDate));
								//oneDayData.setApproval(result.get(0).getLastUpdDate().toString());
							} else
								oneDayData.setApproval("");
							oneDayData.setRowCount(activityListByReportID.size());

							oneDay = false;
						} else {
							oneDayData.setActivity_date(null);
							oneDayData.setApproval("");
						}
						oneDayData.setReport_ID(reportID.getId());
						oneDayData.setFullName(activityOneDayData.getDailyReport().getStaffID().getFullName());

						if (activityOneDayData.getProject() != null) {
							oneDayData.setProjectID(activityOneDayData.getProject().getId());
						} else
							oneDayData.setProjectID(" - ");

						if (activityOneDayData.getWbs() != null) {
							if (activityOneDayData.getWbs().equals(""))
								oneDayData.setWbsNo(" - ");
							else
								oneDayData.setWbsNo(activityOneDayData.getWbs());
						} else {
							oneDayData.setWbsNo(" - ");
						}

						if (activityOneDayData.getWbsFunction() != null) {
							if (activityOneDayData.getWbsFunction().equals(""))
								oneDayData.setFunctionID(" - ");
							else
								oneDayData.setFunctionID(activityOneDayData.getWbsFunction());
						} else {
							oneDayData.setFunctionID(" - ");
						}

						oneDayData.setCategory(activityOneDayData.getTask().getDescription());
						oneDayData.setActivities(activityOneDayData.getTaskActivity().getDescription());
						oneDayData.setDescription(activityOneDayData.getTaskDescription());

						if (activityOneDayData.getDeliveryOutput() != null) {
							if (activityOneDayData.getDeliveryOutput().equals(""))
								oneDayData.setDeliDoc(" - ");
							else
								oneDayData.setDeliDoc(activityOneDayData.getDeliveryOutput());
						} else {
							oneDayData.setDeliDoc(" - ");
						}

						if (activityOneDayData.getReason() != null) {
							if (activityOneDayData.getReason().equals(""))
								oneDayData.setReason(" - ");
							else
								oneDayData.setReason(activityOneDayData.getReason());
						} else {
							oneDayData.setReason(" - ");
						}

						if (activityOneDayData.getProgressPercentage() != null)
							oneDayData.setProgress(activityOneDayData.getProgressPercentage());
						/*
						 * else oneDayData.setProgress(0.0);
						 */

						oneDayData.setUserName(activityOneDayData.getDailyReport().getStaffID().getUsername());
						oneDayData.setHour(activityOneDayData.getActivityHours());

						if (activityOneDayData.getTaskStatus() != null) {
							if (activityOneDayData.getTaskStatus() == TaskStatus.FORWARD) {
								oneDayData.setTask_status("FORWARD");
							} else {
								oneDayData.setTask_status("DELAY");
							}
							oneDayData.setTaskStatusCount(1.0);
						} else {

							oneDayData.setTask_status("");
						}

						if (activityOneDayData.getTaskStatusCount() != null) {
							oneDayData.setTaskStatusCount(activityOneDayData.getTaskStatusCount());
						}

						if (activityOneDayData.getBeginDate() != null) {
							oneDayData.setBeginDate(activityOneDayData.getBeginDate());
						}
						if (activityOneDayData.getEndDate() != null) {
							oneDayData.setEndDate(activityOneDayData.getEndDate());
						}

						// total activity lists
						allDaysData.add(oneDayData);
					}
				}
			}
			if (allDaysData.size() <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "There is no data to export"));
			} else {
				dataToShow = allDaysData;
			}
		}
	}

	public void clearTable() throws Exception {
		dataToShow = new ArrayList<ExportExcelData>();
	}

	public List<ExportExcelData> getDataToShow() {
		return dataToShow;
	}

	public void setDataToShow(List<ExportExcelData> dataToShow) {
		this.dataToShow = dataToShow;
	}

	public DailyActivity getDailyActivity() {
		return dailyActivity;
	}

	public void setDailyActivity(DailyActivity dailyActivity) {
		this.dailyActivity = dailyActivity;
	}

	public IDailyReportService getDailyReportService() {
		return dailyReportService;
	}

	public void setDailyReportService(IDailyReportService dailyReportService) {
		this.dailyReportService = dailyReportService;
	}

	public DailyReport getDailyReport() {
		return dailyReport;
	}

	public IDailyActivityService getDailyActivityService() {
		return dailyActivityService;
	}

	public void setDailyActivityService(IDailyActivityService dailyActivityService) {
		this.dailyActivityService = dailyActivityService;
	}

	public void setDailyReport(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
	}

	public List<DailyReport> getDailyReportList() {
		return dailyReportList;
	}

	public void setDailyReportList(List<DailyReport> dailyReportList) {

		this.dailyReportList = dailyReportList;
	}

	public int getDailyReportListSize() {
		return this.dailyReportList.size();
	}

	public void setDailyReportListSize(int dailyReportListSize) {
		this.dailyReportListSize = dailyReportListSize;
	}

	public List<DailyActivity> getDailyActivityList() {
		return dailyActivityList;
	}

	public void setDailyActivityList(List<DailyActivity> dailyActivityList) {
		this.dailyActivityList = dailyActivityList;
	}

	public LeaveStatus[] getLeaveStatuses() {
		return LeaveStatus.values();
	}

	public SearchDate getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(SearchDate searchDate) {
		this.searchDate = searchDate;
	}

	public User getStaffData() {
		return staffData;
	}

	public void setStaffData(User staffData) {
		this.staffData = staffData;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
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

	public void setDownload(DefaultStreamedContent download) {
		this.download = download;
	}

	public DefaultStreamedContent getDownload() throws Exception {
		return download;
	}

	public IApprovalService getApprovalService() {
		return approvalService;
	}

	public void setApprovalService(IApprovalService approvalService) {
		this.approvalService = approvalService;
	}

	// Adding Copy Row for Activities
	public static void copyRow(HSSFSheet worksheet, int from, int to) {

		HSSFRow sourceRow = worksheet.getRow(from);
		HSSFRow newRow = worksheet.getRow(to);

		if (newRow != null) {
			worksheet.shiftRows(to, worksheet.getLastRowNum(), 1);
		} else {
			newRow = worksheet.createRow(to);
		}

		for (int j = 0; j < sourceRow.getLastCellNum(); j++) {
			Cell oldCell = sourceRow.getCell(j);
			Cell newCell = newRow.createCell(j);
			if (oldCell != null) {
				copyCellStyle(oldCell, newCell);
				copyCellComment(oldCell, newCell);
				copyCellHyperlink(oldCell, newCell);
				copyCellDataTypeAndValue(oldCell, newCell);
			}
		}

		copyAnyMergedRegions(worksheet, sourceRow, newRow);
		to++;
	}

	private static void copyCellStyle(Cell oldCell, Cell newCell) {
		newCell.setCellStyle(oldCell.getCellStyle());
	}

	private static void copyCellComment(Cell oldCell, Cell newCell) {
		if (newCell.getCellComment() != null)
			newCell.setCellComment(oldCell.getCellComment());
	}

	private static void copyCellHyperlink(Cell oldCell, Cell newCell) {
		if (oldCell.getHyperlink() != null)
			newCell.setHyperlink(oldCell.getHyperlink());
	}

	private static void copyCellDataTypeAndValue(Cell oldCell, Cell newCell) {
		setCellDataType(oldCell, newCell);
		setCellDataValue(oldCell, newCell);
	}

	private static void setCellDataType(Cell oldCell, Cell newCell) {
		newCell.setCellType(oldCell.getCellType());
	}

	private static void setCellDataValue(Cell oldCell, Cell newCell) {
		switch (oldCell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			newCell.setCellValue(oldCell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			newCell.setCellValue(oldCell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			newCell.setCellErrorValue(oldCell.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			newCell.setCellFormula(oldCell.getCellFormula());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			newCell.setCellValue(oldCell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			newCell.setCellValue(oldCell.getRichStringCellValue());
			break;

		}
	}

	private static void copyAnyMergedRegions(HSSFSheet worksheet, Row sourceRow, Row newRow) {
		for (int i = 0; i < worksheet.getNumMergedRegions(); i++)
			copyMergeRegion(worksheet, sourceRow, newRow, worksheet.getMergedRegion(i));
	}

	private static void copyMergeRegion(Sheet worksheet, Row sourceRow, Row newRow, CellRangeAddress mergedRegion) {
		CellRangeAddress range = mergedRegion;
		if (range.getFirstRow() == sourceRow.getRowNum()) {
			int lastRow = newRow.getRowNum() + (range.getFirstRow() - range.getLastRow());
			CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(), lastRow,
					range.getFirstColumn(), range.getLastColumn());
			worksheet.addMergedRegion(newCellRangeAddress);
		}
	}

	public static void catagoryValidation(Workbook workbook, HSSFSheet sheet, int copyValidation) {

		CellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// HSSFRow row = null;
		// HSSFCell cell = null;

		Name namedCell = workbook.createName();
		namedCell.setRefersToFormula("Category!$A$2:$A$33");
		HSSFDataValidationHelper dvHelper = new HSSFDataValidationHelper(sheet);
		DVConstraint dvConstraint = (DVConstraint) dvHelper.createFormulaListConstraint("Category");
		CellRangeAddressList addressList = null;
		HSSFDataValidation validation = null;
		for (int i = 0; i <33; i++) {

			addressList = new CellRangeAddressList(5, copyValidation, 11, 11);
			validation = (HSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
			validation.setSuppressDropDownArrow(false);
			sheet.addValidationData(validation);
		}
	}

	public static void activitiesValidation(Workbook workbook, HSSFSheet sheet, int copyValidation) {

		CellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		Name namedCell = workbook.createName();
		namedCell.setRefersToFormula("Activity!$C$2:$C$13");
		HSSFDataValidationHelper dvHelper = new HSSFDataValidationHelper(sheet);
		DVConstraint dvConstraint = (DVConstraint) dvHelper.createFormulaListConstraint("Activity");
		CellRangeAddressList addressList = null;
		HSSFDataValidation validation = null;
		for (int i = 0; i < 13; i++) {
			addressList = new CellRangeAddressList(5, copyValidation, 17, 17);
			validation = (HSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
			validation.setSuppressDropDownArrow(false);
			sheet.addValidationData(validation);
		}
	}

}
