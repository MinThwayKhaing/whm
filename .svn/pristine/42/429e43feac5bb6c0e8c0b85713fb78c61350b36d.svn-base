/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-24
 * @Version 1.0
 * @Purpose <<Export project initiation data as pdf file.>>
 *
 ***************************************************************************************/
package com.dat.whm.web.project;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.dat.whm.common.entity.OrganizationDev;
import com.dat.whm.communicationplan.entity.CommunicationPlan;
import com.dat.whm.contactperson.entity.ContactPerson;
import com.dat.whm.project.entity.Project;
import com.dat.whm.projectcontrol.entity.ProjectControl;
import com.dat.whm.projectdeliverable.entity.ProjectDeliverable;
import com.dat.whm.projectschedule.entity.ProjectSchedule;
import com.dat.whm.projectvolume.entity.ProjectVolume;
import com.dat.whm.riskfactors.entity.RiskFactors;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.Properties;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class ProjectInformationPDFExportBean {
	
	public static final String FONT = "itextfont/SIMHEI.TTF";
	private static final String FILENAME = "(DAT)Project Initiation_";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static NumberFormat formatter = new DecimalFormat("#,##0.00");
	private static int	workingWeekPerMonth;
	private static int	workingDayPerWeek;
	
	public static String exportProjectInformationPDF(Project project,List<String> scheduleYMList,List<Integer> weekPerMonthList){
		String fileName= FILENAME.concat(project.getId()).concat(".pdf");
		workingWeekPerMonth = Integer.parseInt(Properties.getProperty(Constants.APP_SETTING, Constants.WORKING_WEEK_PER_MONTH));
		workingDayPerWeek = Integer.parseInt(Properties.getProperty(Constants.APP_SETTING, Constants.WORKING_DAY_PER_WEEK));
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext context = (ServletContext) externalContext.getContext();
		Document document = null;
		try {
			
			FileOutputStream fileOS= new FileOutputStream(context.getRealPath("/upload/"+fileName));
			FontFactory.register(FONT, "FONT");
			document= new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, fileOS);
			/** Document's page even*/
			HeaderFooterInfo pageEven= new HeaderFooterInfo(project.getStartDate());
			writer.setPageEvent(pageEven);
			
			document.open();
			
			addBunnerInformation(document);
			
			addProjectSummary(document, project);
			
			addClientInformation(document, project);
		
			addServiceProviderInformation(document, project);
			
			addCommunicationPlanInformation(document, project);
			
			addProjectControlInformation(document, project);
			
			addSystemOutlineInformation(document, project);
			
			addProjectDeliverables(document, project);
			
			addRiskFactorsInformation(document, project);
			
			addProjectVolumeInformation(document, project);
			
			addScheduleInformation(document, project, scheduleYMList, weekPerMonthList);

			document.close();
			
		} catch (FileNotFoundException ffe) {
			ffe.printStackTrace();
			fileName = null;
		}catch (DocumentException de) {
			de.printStackTrace();
			fileName = null;
		}catch (Exception e) {
			e.printStackTrace();
			fileName = null;
		}finally{
			document.close();
		}
		return fileName;
	}
	
	/** Write bunner information in document*/
	private static void addBunnerInformation(Document document) throws DocumentException, MalformedURLException, IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext context = (ServletContext) externalContext.getContext();
		PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        
        PdfPCell myCell = new PdfPCell();
   /*Header Infomation*/
        Image image1 = Image.getInstance(context.getRealPath("/images/logo/DAT logo.png"));
        image1.scaleAbsoluteWidth(100);
        image1.scaleAbsoluteHeight(100);
        
        myCell = new PdfPCell();
        myCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        myCell.addElement(image1);
        table.addCell(myCell);
        
        Paragraph preface = new Paragraph();
        preface.add(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H,8)));
        preface.add(new Paragraph("DIR-ACE TECHNOLOGY LTD", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8)));
        preface.add(new Paragraph("Buliding (17),3F, Myanmar Info Tech", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8)));
        preface.add(new Paragraph("Hlaing Township, Yangon Myanmar.", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8)));
        preface.add(new Paragraph("Tel : 951521127, 235139 Fax: 9512305139", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8)));
        preface.add(new Paragraph("E-mail : datadmin@diracetechnology.com", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8)));
        preface.add(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8)));
		preface.setAlignment(Element.ALIGN_LEFT);
		
		myCell = new PdfPCell(preface);
		myCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		myCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		myCell.setPaddingLeft(25);
		table.addCell(myCell);
		
		document.add(table);
		
		preface = new Paragraph();
		preface.add(new Paragraph("Project Initiation", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.BOLD)));
		preface.setAlignment(Element.ALIGN_CENTER);
		document.add(preface);
	/*Header Infomation*/
	}
	
	/** Write project summary data*/
	private static void addProjectSummary(Document document,Project project) throws DocumentException{
		/*Project Summary*/
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Project Summary",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		
		PdfPCell myCell = new PdfPCell(new Paragraph("Project ID",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getId(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Project Name", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getProjectName(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Project Background", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getProjectBackground(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Purpose", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getPurpose(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Objectives", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getObjectives(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Duration", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph( getString(project.getDuration()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
	
		myCell = new PdfPCell(new Paragraph("Start Date", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph( formatDate(project.getStartDate()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("End Date", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph( formatDate(project.getEndDate()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);

		myCell = new PdfPCell(new Paragraph("Budget", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		/**
		 * Revised By : Sai Kyaw Ye Myint 
		 * Revised Date : 2018/07/20
		 * Explanation : Modify for number format exception when budget is null.
		 */
		if (project.getBudget() != null) {
			myCell= new PdfPCell(new Paragraph(formatter.format(project.getBudget()) +""+ project.getCurrency(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		}else {
			myCell= new PdfPCell(new Paragraph(""));
		}
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Project Summary", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getProjectSummary(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Project Manager", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getProjectManager(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		document.add(table);
	/*Project Summary*/
	}
	/** Write client information data*/
	private static void addClientInformation(Document document,Project project) throws DocumentException{
	/*Client*/
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Client Information",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		PdfPTable table = new PdfPTable(4);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(100.0f);
		
		PdfPCell myCell = new PdfPCell(new Paragraph("Organization", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getClientOrganization(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Person In Charge", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getClientIncharge(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Department", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getClientDept(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(3);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		document.add(table);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		float[] columnWidths = {0.5f, 3, 5, 3, 3};
		table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100.0f);
		
		myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Name", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Email Address", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Phone No", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Fax", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		for (ContactPerson contactPerson : project.getContactPersonList()) {
			if (contactPerson.getOrganization() == OrganizationDev.CLIENT) {
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getEmailType(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getName(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getEmail(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getPhone(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getFax(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
			}
		}
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		document.add(table);
		
	/*Client*/
	}
	/** Write service provider data*/
	private static void addServiceProviderInformation(Document document,Project project) throws DocumentException{
	/*Service Provider*/
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Service Provider Information",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100.0f);
		
		PdfPCell myCell = new PdfPCell(new Paragraph("Organization", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getSpOrganization(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Person In Charge", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getSpIncharge(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Department", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getSpDept(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(3);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		document.add(table);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		float[] columnWidths = {0.5f, 3, 5, 3, 3};
		table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100.0f);
		
		myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Name", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Email Address", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Phone No", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Fax", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		for (ContactPerson contactPerson : project.getContactPersonList()) {
			if (contactPerson.getOrganization() == OrganizationDev.SERVICE_PROVIDER) {
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getEmailType(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getName(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getEmail(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getPhone(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
				myCell= new PdfPCell(new Paragraph(contactPerson.getFax(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				table.addCell(myCell);
				
			}
		}
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		document.add(table);
		
	/*Service Provider*/
	}
	/** Write communication plan data*/
	private static void addCommunicationPlanInformation(Document document,Project project) throws DocumentException{
	/*Communication Plan*/
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Communication Plan",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		float[] columnWidths = {4, 3, 2, 2.5f, 4, 4};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100.0f);
	
		PdfPCell myCell = new PdfPCell(new Paragraph("Groups/Individuals Involved", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Type of Communication", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Frequency", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Communication Method", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Communication Language", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Purpose", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		for (CommunicationPlan communicationPlan : project.getCommunicationPlanList()) {
			
			myCell= new PdfPCell(new Paragraph(communicationPlan.getCommgroups(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(communicationPlan.getCommType(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(communicationPlan.getCommFrequency(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(communicationPlan.getCommMethod(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(communicationPlan.getCommLanguage(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(communicationPlan.getCommPurpose(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
		}
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		document.add(table);
		
	/*Communication Plan*/
	}
	/** Write project control data*/
	private static void addProjectControlInformation(Document document,Project project) throws DocumentException{
	/*Project Control*/
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Project Control",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		float[] columnWidths = {5, 3, 3, 5};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100.0f);
		
		PdfPCell myCell = new PdfPCell(new Paragraph("Deliverables", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Person In Charge", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Language used in Document", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Remark", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		for (ProjectControl projectControl : project.getProjectControlList()) {
			myCell= new PdfPCell(new Paragraph(projectControl.getPrjCtlDeliverables(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(projectControl.getPrjCtlIncharge(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(projectControl.getPrjCtlLanguage(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(projectControl.getPrjCtlRemark(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
		}
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		document.add(table);
	
	/*Project Control*/
	}
	/** Write system outline data*/
	private static void addSystemOutlineInformation(Document document,Project project) throws DocumentException{
	/*System Outline (Draft)*/
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("System Outline (Draft)",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		
		PdfPCell myCell = new PdfPCell(new Paragraph("Operating System", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getOperatingSystem(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Application Run-time", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getAppRuntime(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Web Server", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getWebServer(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Application Server", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getAppServer(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Database Server", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getDbServer(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Application Framework", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getAppFramework(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Client Agent", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getClientAgent(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Programming Language(s)", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getPgmLanguage(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Libraries/Other Frameworks", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getLibraries(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Others", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(project.getOthers(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		document.add(table);
		
	/*System Outline (Draft)*/	
	}
	/** Write project deliverables data*/
	private static void addProjectDeliverables(Document document,Project project) throws DocumentException{
	/*Project Deliverables*/
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Project Deliverables",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		float[] columnWidths = {5, 3, 3, 4, 4};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100.0f);
		
		PdfPCell myCell = new PdfPCell(new Paragraph("Phase", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Design Type", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Person In Charge", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Language used in Document", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Remark", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		for (ProjectDeliverable projectDeliverable : project.getProjectDeliverablesList()) {
			myCell= new PdfPCell(new Paragraph(projectDeliverable.getPrjDelPhase(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(projectDeliverable.getPrjDelDesignType(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(projectDeliverable.getPrjDelIncharge(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(projectDeliverable.getPrjDelLanguage(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(projectDeliverable.getPrjDelRemark(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
		}
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		document.add(table);
	/*Project Deliverables*/
	}
	/** Write risk factors data*/
	private static void addRiskFactorsInformation(Document document,Project project) throws DocumentException{
	/*Risk Factors*/
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Risk Factors",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		float[] columnWidths = {5, 3, 3, 5};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(100.0f);
		
		PdfPCell myCell = new PdfPCell(new Paragraph("Expected Risk", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Probability of Occurence", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Impact", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Countermeasure", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(253,233,217));
		table.addCell(myCell);
		
		for (RiskFactors riskFactors : project.getRiskFactorList()) {
			myCell= new PdfPCell(new Paragraph(riskFactors.getRiskExpected(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(riskFactors.getRiskProbability(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(riskFactors.getRiskimpact(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
			
			myCell= new PdfPCell(new Paragraph(riskFactors.getRiskCounterMeasure(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			table.addCell(myCell);
		}
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		document.add(table);
	/*Risk Factors*/
	}
	/** Write project volume  data*/
	private static void addProjectVolumeInformation(Document document,Project project) throws DocumentException{
	/*Project Volume*/
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Project Volume",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		
		PdfPCell myCell = new PdfPCell(new Paragraph("Project Period", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(formatter.format(project.getPrjPeroid()) + " months", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Total Man-Month", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(146,205,220));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		myCell= new PdfPCell(new Paragraph(formatter.format(project.getTotalManMonth()) + " (without risk)", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
		myCell.setColspan(5);
		myCell.setBackgroundColor(new BaseColor(218,238,243));
		myCell.setBorderColor(BaseColor.WHITE);
		table.addCell(myCell);
		
		document.add(table);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		table = new PdfPTable(8);
		table.setWidthPercentage(100.0f);
		
		myCell = new PdfPCell(new Paragraph("", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL )));
		myCell.setBorder(Rectangle.NO_BORDER);
		myCell.setColspan(2);
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Man-Month", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL, BaseColor.WHITE )));
		myCell.setColspan(3);
		myCell.setBackgroundColor(new BaseColor(57,151,113));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Man-Month", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL, BaseColor.WHITE )));
		myCell.setColspan(3);
		myCell.setBackgroundColor(new BaseColor(79,98,40));
		table.addCell(myCell);
		
		myCell = new PdfPCell(new Paragraph("Phase", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL, BaseColor.WHITE)));
		myCell.setBackgroundColor(new BaseColor(57,151,113));
		myCell.setColspan(2);
		table.addCell(myCell);
		
		/*Man-Month*/
			myCell = new PdfPCell(new Paragraph("Work", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL, BaseColor.WHITE)));
			myCell.setBackgroundColor(new BaseColor(57,151,113));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph("Review", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL, BaseColor.WHITE)));
			myCell.setBackgroundColor(new BaseColor(57,151,113));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph("Total", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL, BaseColor.WHITE)));
			myCell.setBackgroundColor(new BaseColor(57,151,113));
			table.addCell(myCell);
		/*Man-Month*/
		
		/*Man-Day*/
			myCell = new PdfPCell(new Paragraph("Work", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL, BaseColor.WHITE)));
			myCell.setBackgroundColor(new BaseColor(79,98,40));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph("Review", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL, BaseColor.WHITE)));
			myCell.setBackgroundColor(new BaseColor(79,98,40));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph("Total", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL, BaseColor.WHITE)));
			myCell.setBackgroundColor(new BaseColor(79,98,40));
			table.addCell(myCell);
		/*Man-Day*/
		
		for (ProjectVolume projectVolume : project.getProjectVolumeList()) {
			
			myCell = new PdfPCell(new Paragraph(projectVolume.getPhase(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(238,236,255));
			myCell.setColspan(2);
			table.addCell(myCell);
			
			/*Man-Month*/
				myCell = new PdfPCell(new Paragraph(formatter.format(projectVolume.getWorkManMonth()) , FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				myCell.setBackgroundColor(new BaseColor(238,236,255));
				table.addCell(myCell);
				
				myCell = new PdfPCell(new Paragraph(formatter.format(projectVolume.getReviewManMonth()) , FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				myCell.setBackgroundColor(new BaseColor(238,236,255));
				table.addCell(myCell);
				
				myCell = new PdfPCell(new Paragraph(formatter.format(projectVolume.getTotalManMonth()) , FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				myCell.setBackgroundColor(new BaseColor(238,236,255));
				table.addCell(myCell);
			/*Man-Month*/
			
			/*Man-Day*/
				myCell = new PdfPCell(new Paragraph(formatter.format(projectVolume.getWorkManDay()) , FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				myCell.setBackgroundColor(new BaseColor(238,236,255));
				table.addCell(myCell);
				
				myCell = new PdfPCell(new Paragraph(formatter.format(projectVolume.getReviewManDay()) , FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				myCell.setBackgroundColor(new BaseColor(238,236,255));
				table.addCell(myCell);
				
				myCell = new PdfPCell(new Paragraph(formatter.format(projectVolume.getTotalManDay()) , FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
				myCell.setBackgroundColor(new BaseColor(238,236,255));
				table.addCell(myCell);
			/*Man-Day*/
	
		}
		
		myCell = new PdfPCell(new Paragraph("Sub Total(Man-Month)", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD)));
		myCell.setBackgroundColor(new BaseColor(166,222,199));
		myCell.setColspan(2);
		table.addCell(myCell);
		
		/*Man-Month*/
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getWorkSManMonth()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getReviewSManMonth()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getTotalSManMonth()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
		/*Man-Month*/
		
		/*Man-Day*/
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getWorkSManDay()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getReviewSManDay()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getTotalSManDay()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
		/*Man-Day*/
			
		myCell = new PdfPCell(new Paragraph("Risk(10%)", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD)));
		myCell.setBackgroundColor(new BaseColor(166,222,199));
		myCell.setColspan(2);
		table.addCell(myCell);
		
		/*Man-Month*/
			myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getTotalRManMonth()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
		/*Man-Month*/
		
		/*Man-Day*/
			myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getTotalRManDay()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
		/*Man-Day*/
			
		myCell = new PdfPCell(new Paragraph("Total(Man-Month)", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD)));
		myCell.setBackgroundColor(new BaseColor(166,222,199));
		myCell.setColspan(2);
		table.addCell(myCell);
			
		/*Man-Month*/
			myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getTotalTManMonth()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
		/*Man-Month*/
		
		/*Man-Day*/
			myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
			
			myCell = new PdfPCell(new Paragraph(formatter.format(project.getTotalTManDay()), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL)));
			myCell.setBackgroundColor(new BaseColor(166,222,199));
			table.addCell(myCell);
		/*Man-Day*/
		
		document.add(table);
	/*Project Volume*/
	}
	/** Write schedule data*/
	private static void addScheduleInformation(Document document,Project project, List<String> scheduleYMList,List<Integer>  weekPerMonthList ) throws DocumentException{
	/*Schedule*/
		if (project.getProjectScheduleList().size() == 0) {
			return ;
		}
		
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Schedule",FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 10,Font.UNDERLINE)));
		document.add(preface);
		
		preface = new Paragraph();
		preface.add(new Paragraph(""));
		document.add(preface);
		
		PdfPTable table = new PdfPTable( ((workingWeekPerMonth*workingDayPerWeek) * scheduleYMList.size()) + 10);
		table.setWidthPercentage(100.0f);
		
		PdfPCell myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBorder(Rectangle.NO_BORDER);
		myCell.setColspan(10);
		table.addCell(myCell);
		
		for (String yearMonth : scheduleYMList) {
			myCell = new PdfPCell(new Paragraph(yearMonth , FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD ,BaseColor.WHITE)));
			myCell.setColspan(workingWeekPerMonth*workingDayPerWeek);
			myCell.setBackgroundColor(new BaseColor(54,69,146));
			table.addCell(myCell);
		}
		
		myCell = new PdfPCell(new Paragraph("Week", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBorder(Rectangle.NO_BORDER);
		myCell.setColspan(10);
		table.addCell(myCell);
		
		for (String yearMonth : scheduleYMList) {
			for (Integer integer : weekPerMonthList) {
				myCell = new PdfPCell(new Paragraph(integer.toString() , FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD)));
				myCell.setColspan(workingDayPerWeek);
				myCell.setBackgroundColor(new BaseColor(166,222,199));
				table.addCell(myCell);
			}
		}
		
		myCell = new PdfPCell(new Paragraph("Phase", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD )));
		myCell.setBackgroundColor(new BaseColor(57,151,113));
		myCell.setColspan(10);
		table.addCell(myCell);
		
		for (String yearMonth : scheduleYMList) {
			for (Integer integer : weekPerMonthList) {
				myCell = new PdfPCell(new Paragraph(" " , FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.BOLD)));
				myCell.setColspan(workingDayPerWeek);
				table.addCell(myCell);
			}
		}
		
		for (ProjectSchedule projectSchedule : project.getProjectScheduleList()) {
			myCell = new PdfPCell(new Paragraph(projectSchedule.getPhase(), FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL )));
			myCell.setColspan(10);
			table.addCell(myCell);
			int index= 1;
			for (WorkDayForProjectSchedule workDay : projectSchedule.getWorkingList()) {
				myCell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("FONT", BaseFont.IDENTITY_H, 8,Font.NORMAL )));
				
				if (workDay.isWorking()) {
					myCell.setBackgroundColor(new BaseColor(54,69,146));
				}
				
				if (index == workingDayPerWeek) {
					myCell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
					index = 0;
				}else {
					myCell.setBorder(Rectangle.BOTTOM);
				}
				table.addCell(myCell);
				index++;
			}
		}
		
		document.add(table);
		
	/*Schedule*/
	}
	
	/** Get string value to avoid null pointer exception*/
	private static String getString(Object obj){
		if (obj != null) {
			return obj.toString();
		}else {
			return "";
		}
	}
	
	/** Format Date value to avoid null pointer exception*/
	private static String formatDate(Date date){
		if (date != null) {
			return dateFormat.format(date);
		}else {
			return "";
		}
	}
}

/** New page create even*/
class HeaderFooterInfo extends PdfPageEventHelper {
	private int year;
	
	public HeaderFooterInfo(Date startDate) {
		if (startDate != null) {
			Calendar cStart = Calendar.getInstance(); 
			cStart.setTime(startDate);
			this.year= cStart.get(Calendar.YEAR);
		}
	}

	public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, getFooterInfo(), 100, 10, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, getPageInfo(document.getPageNumber()), 570, 10, 0);
    }
	
	private Phrase getFooterInfo(){
		Phrase phrase = new Phrase("Copyright(c) "+ this.year +" DIR-ACE Technology Ltd. All rights reserved.", new Font(Font.FontFamily.TIMES_ROMAN, 7,Font.NORMAL,BaseColor.BLUE));
		return phrase;
	}
	
	private Phrase getPageInfo(int page){
		Phrase phrase = new Phrase("Page "+page,new Font(Font.FontFamily.TIMES_ROMAN, 7,Font.NORMAL,BaseColor.BLUE));
		return phrase;
	}
}