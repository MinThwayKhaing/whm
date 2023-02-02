/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-14
 * @Version 1.0
 * @Purpose <<Projcet information Management (Create new project , edit project)>>
 *
 ***************************************************************************************/
package com.dat.whm.web.project;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import org.primefaces.model.DefaultStreamedContent;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.common.entity.OrganizationDev;
import com.dat.whm.common.entity.ProjectStatusDiv;
import com.dat.whm.communicationplan.entity.CommunicationPlan;
import com.dat.whm.contactperson.entity.ContactPerson;
import com.dat.whm.project.entity.Project;
import com.dat.whm.project.service.interfaces.IProjectService;
import com.dat.whm.projectcontrol.entity.ProjectControl;
import com.dat.whm.projectdeliverable.entity.ProjectDeliverable;
import com.dat.whm.projectinformation.service.interfaces.IProjectInformationService;
import com.dat.whm.projectschedule.entity.ProjectSchedule;
import com.dat.whm.projectvolume.entity.ProjectVolume;
import com.dat.whm.riskfactors.entity.RiskFactors;
import com.dat.whm.schedulestartend.entity.ScheduleStartEnd;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.MessageId;
import com.dat.whm.web.common.Properties;

@ManagedBean(name = "ProjectInformationManageBean")
@ViewScoped
public class ProjectInformationManageBean extends BaseBean  {
	
	
	@ManagedProperty(value = "#{ProjectService}")
	private IProjectService projectService;
	@ManagedProperty(value = "#{ProjectInformationService}")
	private IProjectInformationService projectInformationService;
	
	private Project project;
	
	private List<String> currencyList;
	private List<String> emailType;
	private List<String> commFrequency;
	private List<String> commMethod;
	private List<String> commLanguage;
	private List<String> riskProbabilityList;
	private List<String> scheduleYMList;
	private List<Integer> weekPerMonthList;
	
	private List<String> rowRepeat;
	
	private List<Integer> days= new ArrayList<>();
	/**
	 * Revised By : Sai Kyaw Ye Myint 
	 * Revised Date : 2018/07/18
	 * Explanation : Change the data type of workingHourPerDay from int to double.
	 */
	private double workingHourPerDay;
	private int	workingWeekPerMonth;
	private int	workingDayPerWeek;
	
	private List<ContactPerson> clientContactPersonList;
	private List<ContactPerson> serviceContactPersonList;
		
	private int selectedIndex;
	private int scheduleStartPoint;
	private int scheduleEndPoint;
	
	private boolean scheduleTableVisible;
	private boolean btnSubmit;
	private boolean btnBackVisible;
	
	private DefaultStreamedContent download;
	
	private HashMap<String, String> projectSerachData;
	
	private boolean editFlag;
	private boolean viewOnly;
	
	private Date tmpStartDate;
	private Date tmpEndDate;
	
	@PostConstruct
	public void init() {
		currencyList= Properties.getPropertyList(Constants.APP_SETTING, Constants.CURRENCY);
		emailType= Properties.getPropertyList(Constants.APP_SETTING, Constants.EMAIL_TYPE);
		commFrequency = Properties.getPropertyList(Constants.APP_SETTING, Constants.COMM_FREQUENCY);
		commMethod = Properties.getPropertyList(Constants.APP_SETTING, Constants.COMM_METHOD);
		commLanguage = Properties.getPropertyList(Constants.APP_SETTING, Constants.COMM_LANGUAGE);
		riskProbabilityList = Properties.getPropertyList(Constants.APP_SETTING, Constants.RISK_PROBABILITY);
		
		workingHourPerDay = Double.parseDouble(Properties.getProperty(Constants.APP_SETTING, Constants.WORKING_HOUR_PER_DAY));
		workingWeekPerMonth = Integer.parseInt(Properties.getProperty(Constants.APP_SETTING, Constants.WORKING_WEEK_PER_MONTH));
		workingDayPerWeek = Integer.parseInt(Properties.getProperty(Constants.APP_SETTING, Constants.WORKING_DAY_PER_WEEK));
		
		project = new Project();
		project.setContactPersonList(new ArrayList<ContactPerson>());
		project.setCommunicationPlanList(new ArrayList<CommunicationPlan>());
		project.setProjectControlList(new ArrayList<ProjectControl>());
		project.setProjectDeliverablesList(new ArrayList<ProjectDeliverable>());
		project.setRiskFactorList(new ArrayList<RiskFactors>());
		project.setProjectVolumeList(new ArrayList<ProjectVolume>());
		project.setProjectScheduleList(new ArrayList<ProjectSchedule>());
		project.setLastUpdBy(new User());
		
		clientContactPersonList = new ArrayList<ContactPerson>();
		serviceContactPersonList = new ArrayList<ContactPerson>();
		
		scheduleYMList = new ArrayList<>();
		rowRepeat= new ArrayList<>();
		rowRepeat.add("Year/Week");
		weekPerMonthList = new ArrayList<>();
		
		this.selectedIndex = 0;
		this.scheduleStartPoint = 0;
		this.scheduleEndPoint = 0;
		
		this.scheduleTableVisible = false;
		this.btnSubmit = true;
		this.editFlag = false;
	}
	
	@SuppressWarnings("unchecked")
	public void controlForm() {
		init();
		if (!FacesContext.getCurrentInstance().getExternalContext().getFlash().isEmpty()) {
			editFlag = (boolean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag");
			viewOnly = (boolean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag");
			
			if (editFlag) {
				this.btnBackVisible = true;
				project = (Project) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("project");
				projectSerachData = (HashMap<String, String>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("projectSerachData");
				
				clientContactPersonList = new ArrayList<ContactPerson>();
				serviceContactPersonList = new ArrayList<ContactPerson>();
				
				/* Separate Client and Service Provider Contact Person Information. */
				if(project.getContactPersonList() != null){
					for(ContactPerson cp: project.getContactPersonList()){
						if(cp.getOrganization() == OrganizationDev.CLIENT){
							clientContactPersonList.add(cp);
						}else {
							serviceContactPersonList.add(cp);
						}
					}
				}
				
				/* Calculate Project Volume. */
				project = calculateWork(project);
				project = calculateReview(project);
				
				/* Schedule. */
				scheduleYMList = new ArrayList<>();
				rowRepeat= new ArrayList<>();
				rowRepeat.add("Year/Week");
				weekPerMonthList = new ArrayList<>();
				
				selectedIndex = 0;
				scheduleStartPoint = 0;
				scheduleEndPoint = 0;
				
				prepareSchedule();
				findStartEndPoint();
				this.btnSubmit = false;
				
				this.tmpStartDate = project.getStartDate();
				this.tmpEndDate = project.getEndDate();
			}
		}
	}
	
	public void changeProjectID() {
		Project result = projectService.findProject(project.getId());
		this.btnSubmit = false;
		if (result != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Project ID already exist!!!"));
			this.btnSubmit = true;
		}
	}

/***************************************************************************************
 * Client Info add, delete and clear function
 ***************************************************************************************/
	public void addClientInfo() {
		ContactPerson client= new ContactPerson();
		client.setOrganization(OrganizationDev.CLIENT);
		clientContactPersonList.add(client);
	}

	public void deleteClientInfo(ContactPerson contactPerson) {
		clientContactPersonList.remove(contactPerson);
	}

	@SuppressWarnings("unused")
	public void clearClientInfo() {
		List<ContactPerson> tmplientContactPersonList = new ArrayList<>();
		for (ContactPerson contactPerson : clientContactPersonList) {
			ContactPerson client= new ContactPerson();
			client.setOrganization(OrganizationDev.CLIENT);
			tmplientContactPersonList.add(client);
		}
		clientContactPersonList = tmplientContactPersonList;
	}

/***************************************************************************************
 * Provider Info add, delete and clear function
 ***************************************************************************************/
	public void addServiceProviderInfo() {
		ContactPerson provider= new ContactPerson();
		provider.setOrganization(OrganizationDev.SERVICE_PROVIDER);
		serviceContactPersonList.add(provider);
	}

	public void deleteServiceProviderInfo(ContactPerson contactPerson) {
		serviceContactPersonList.remove(contactPerson);
	}

	@SuppressWarnings("unused")
	public void clearServiceProviderInfo() {
		List<ContactPerson> tmplientContactPersonList = new ArrayList<>();
		for (ContactPerson contactPerson : clientContactPersonList) {
			ContactPerson client= new ContactPerson();
			client.setOrganization(OrganizationDev.CLIENT);
			tmplientContactPersonList.add(client);
		}
		serviceContactPersonList = tmplientContactPersonList;
	}

/***************************************************************************************
 * Project Control add, delete and clear function
 ***************************************************************************************/
	public void addProjectControl() {
		project.getProjectControlList().add(new ProjectControl());
	}

	public void deleteProjectControl(ProjectControl projectControl) {
		project.getProjectControlList().remove(projectControl);
	}

	@SuppressWarnings("unused")
	public void clearProjectControl() {
		List<ProjectControl> tmpProjectControlList = new ArrayList<>();
		for (ProjectControl projectControl : project.getProjectControlList()) {
			tmpProjectControlList.add(new ProjectControl());
		}
		project.setProjectControlList(tmpProjectControlList);
	}

/***************************************************************************************
* Communication Plan add, delete and clear function
***************************************************************************************/
	public void addCommunicationPlan() {
		project.getCommunicationPlanList().add(new CommunicationPlan());
	}

	public void deleteCommunicationPlan(CommunicationPlan communicationPlan) {
		project.getCommunicationPlanList().remove(communicationPlan);
	}

	@SuppressWarnings("unused")
	public void clearCommunicationPlan() {
		List<CommunicationPlan> tmpCommunicationPlanList = new ArrayList<>();
		for (CommunicationPlan communicationPlan : project.getCommunicationPlanList()) {
			tmpCommunicationPlanList.add(new CommunicationPlan());
		}
		project.setCommunicationPlanList(tmpCommunicationPlanList);
	}

/***************************************************************************************
* Project Deliverables add, delete and clear function
***************************************************************************************/
	public void addProjectDeliverables() {
		project.getProjectDeliverablesList().add(new ProjectDeliverable());
	}

	public void deleteProjectDeliverables(ProjectDeliverable projectDeliverable) {
		project.getProjectDeliverablesList().remove(projectDeliverable);
	}

	@SuppressWarnings("unused")
	public void clearProjectDeliverables() {
		List<ProjectDeliverable> tmpProjectDeliverableList = new ArrayList<>();
		for (ProjectDeliverable projectDeliverable : project.getProjectDeliverablesList()) {
			tmpProjectDeliverableList.add(new ProjectDeliverable());
		}
		project.setProjectDeliverablesList(tmpProjectDeliverableList);
	}

/***************************************************************************************
* Risk Factors add, delete and clear function
***************************************************************************************/
	public void addRisk() {
		project.getRiskFactorList().add(new RiskFactors());
	}

	public void deleteRisk(RiskFactors riskFactors) {
		project.getRiskFactorList().remove(riskFactors);
	}

	@SuppressWarnings("unused")
	public void clearRisk() {
		List<RiskFactors> tmpRiskFactorsList = new ArrayList<>();
		for (RiskFactors riskFactors : project.getRiskFactorList()) {
			tmpRiskFactorsList.add(new RiskFactors());
		}
		project.setRiskFactorList(tmpRiskFactorsList);
	}
	
/***************************************************************************************
* Phase info add, delete and clear function
***************************************************************************************/
	public void addPhaseInfo() {
		project.getProjectVolumeList().add(new ProjectVolume());
	}
	
	/**
	 * Revised By : Sai Kyaw Ye Myint 
	 * Revised Date : 2018/07/16
	 * Explanation : If project volume's phase change, modify the phase of schedule.
	 */
	public void phaseChange(String phase, int rowIndex){
		if (project.getProjectScheduleList().size() >= rowIndex +1) {
			project.getProjectScheduleList().get(rowIndex).setPhase(phase);
		}
	}

	/**
	 * Revised By : Sai Kyaw Ye Myint 
	 * Revised Date : 2018/07/16
	 * Explanation : If project volume is deleted, delete the phase from schedule list.
	 */
	public void deletePhaseInfo(ProjectVolume projectVolumn,int rowIndex) {
		project.getProjectVolumeList().remove(projectVolumn);
		if (project.getProjectScheduleList().size() >= rowIndex +1) {
			project.getProjectScheduleList().remove(rowIndex);
			if (project.getProjectScheduleList().size() == 0 ) {
				this.scheduleTableVisible= false;
			}
		}
		project = calculateWork(project);
		project = calculateReview(project);
	}

	@SuppressWarnings("unused")
	public void clearPhaseInfo() {
		List<ProjectVolume> tmpProjectVolumeList = new ArrayList<>();
		for (ProjectVolume projectVolume : project.getProjectVolumeList()) {
			tmpProjectVolumeList.add(new ProjectVolume());
		}
		project.setProjectVolumeList(tmpProjectVolumeList);
		project = calculateWork(project);
		project = calculateReview(project);
	}

	public List<String> getPersonInchargeList(){
		List<String> personInchargeList= new ArrayList<>();
		if (project.getClientOrganization() != null){
				personInchargeList.add(project.getClientOrganization());
		}
		if (project.getSpOrganization() != null ) {
				personInchargeList.add(project.getSpOrganization());
		}
		return personInchargeList;
	}
	
	/*************************************************
	 * Calculate work day for project volume.
	 *************************************************/
	public Project calculateWork(Project currentPrj) {
		double tmpWorkSManMonth = 0;
		double tmpWorkSManDay = 0;
		NumberFormat formatter = new DecimalFormat("#0.000");
		
		for (ProjectVolume prjV : currentPrj.getProjectVolumeList()) {
			prjV.setWorkManMonth(Double.parseDouble(formatter.format(prjV.getWorkManDay()/(this.workingDayPerWeek*this.workingWeekPerMonth))));
			prjV.setTotalManMonth(Double.parseDouble(formatter.format(prjV.getWorkManMonth() + prjV.getReviewManMonth())));

			prjV.setTotalManDay(Double.parseDouble(formatter.format(prjV.getWorkManDay() + prjV.getReviewManDay())));

			tmpWorkSManMonth += prjV.getWorkManMonth();
			tmpWorkSManDay += prjV.getWorkManDay();
		}
		
		currentPrj.setWorkSManMonth(Double.parseDouble(formatter.format(tmpWorkSManMonth)));
		currentPrj.setWorkSManDay(Double.parseDouble(formatter.format(tmpWorkSManDay)));
		
		currentPrj.setTotalSManMonth(Double.parseDouble(formatter.format(currentPrj.getWorkSManMonth() + currentPrj.getReviewSManMonth())));
		currentPrj.setTotalSManDay(Double.parseDouble(formatter.format(currentPrj.getWorkSManDay() + currentPrj.getReviewSManDay())));

		currentPrj.setTotalRManMonth(Double.parseDouble(formatter.format(currentPrj.getTotalSManMonth()/10)));
		currentPrj.setTotalRManDay(Double.parseDouble(formatter.format(currentPrj.getTotalSManDay()/10)));
		
		currentPrj.setTotalTManMonth(Double.parseDouble(formatter.format(currentPrj.getTotalSManMonth() + currentPrj.getTotalRManMonth())));
		currentPrj.setTotalTManDay(Double.parseDouble(formatter.format(currentPrj.getTotalSManDay() + currentPrj.getTotalRManDay())));

		currentPrj.setPrjPeroid(Double.parseDouble(formatter.format(currentPrj.getTotalTManMonth())));
		currentPrj.setTotalManMonth(Double.parseDouble(formatter.format(currentPrj.getTotalSManMonth())));
		
		return currentPrj;
	}
	
	/*************************************************
	 * Calculate review day for project volume.
	 *************************************************/
	public Project calculateReview(Project currentPrj) {
		double tmpReviewSManMonth = 0;
		double tmpReviewSManDay = 0;
		NumberFormat formatter = new DecimalFormat("#0.000");
		
		for (ProjectVolume prjV : currentPrj.getProjectVolumeList()) {
			prjV.setReviewManMonth(Double.parseDouble(formatter.format(prjV.getReviewManDay() / (this.workingDayPerWeek*this.workingWeekPerMonth))));
			prjV.setTotalManMonth(Double.parseDouble(formatter.format(prjV.getWorkManMonth() + prjV.getReviewManMonth())));
			
			prjV.setTotalManDay(Double.parseDouble(formatter.format(prjV.getWorkManDay() + prjV.getReviewManDay())));
			
			tmpReviewSManMonth += prjV.getReviewManMonth();
			tmpReviewSManDay += prjV.getReviewManDay();
		}
		
		currentPrj.setReviewSManMonth(Double.parseDouble(formatter.format(tmpReviewSManMonth)));
		currentPrj.setReviewSManDay(Double.parseDouble(formatter.format(tmpReviewSManDay)));
		
		currentPrj.setTotalSManMonth(Double.parseDouble(formatter.format(currentPrj.getWorkSManMonth() + currentPrj.getReviewSManMonth())));
		currentPrj.setTotalSManDay(Double.parseDouble(formatter.format(currentPrj.getWorkSManDay() + currentPrj.getReviewSManDay())));
		
		currentPrj.setTotalRManMonth(Double.parseDouble(formatter.format(currentPrj.getTotalSManMonth()/10)));
		currentPrj.setTotalRManDay(Double.parseDouble(formatter.format(currentPrj.getTotalSManDay()/10)));
		
		currentPrj.setTotalTManMonth(Double.parseDouble(formatter.format(currentPrj.getTotalSManMonth() + currentPrj.getTotalRManMonth())));
		currentPrj.setTotalTManDay(Double.parseDouble(formatter.format(currentPrj.getTotalSManDay() + currentPrj.getTotalRManDay())));

		currentPrj.setPrjPeroid(Double.parseDouble(formatter.format(currentPrj.getTotalTManMonth())));
		currentPrj.setTotalManMonth(Double.parseDouble(formatter.format(currentPrj.getTotalSManMonth())));
		
		return currentPrj;
	}
	
	/*************************************************
	 * Prepare schedule view.
	 *************************************************/
	/**
	 * Revised By : Sai Kyaw Ye Myint 
	 * Revised Date : 2018/07/16
	 * Explanation : Modify for schedule not to clear. Only add new record from project volume list.
	 */
	public void addSchedule(){
		this.scheduleTableVisible= true;
		if (project.getStartDate() == null || project.getEndDate() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please define Start Date, End Date and Project Volume."));
			this.scheduleTableVisible= false;
			return;
		}else {
			if (!isValidFromAndToDate(project.getStartDate(), project.getEndDate())) {
				String errorMessage = "Start date must be before end date !!!";
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMessage));
				this.scheduleTableVisible= false;
				return;
			}else {
				this.scheduleYMList = calculateMonthCount(project.getStartDate(), project.getEndDate());
				this.weekPerMonthList = getTotalWeek();
			}
		}
		findStartEndPoint();
		prepareSchedule();
		if (project.getProjectVolumeList().size() != 0) {
			int index = 0;
			for (ProjectVolume projectVolumn : project.getProjectVolumeList()) {
				try {
					if (projectVolumn.getPhase().trim().equals("") || projectVolumn.getPhase() == null) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please define phase name in Project Volume."));
						if (project.getProjectScheduleList().size() == 0) {
							this.scheduleTableVisible = false;
						}
						return;
					}
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please define phase name in Project Volume."));
					if (project.getProjectScheduleList().size() == 0) {
						this.scheduleTableVisible = false;
					}
					return;
				}
				
				/* If project volume list's index is greater than schedule list size, add new schedule to schedule list. */
				if (index + 1 > project.getProjectScheduleList().size()) {
					ProjectSchedule prjSchedule= new ProjectSchedule();
					prjSchedule.setPhase(projectVolumn.getPhase());
					prjSchedule.setWorkingList(addWorkDay(0, 0));
					project.getProjectScheduleList().add(prjSchedule);
				}
				
				index++;
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please define Start Date, End Date and Project Volume."));
			this.scheduleTableVisible= false;
			return;
		}
	}
	
	/*************************************************
	 * Calculate total week from project start date and end date 
	 *************************************************/
	private List<Integer> getTotalWeek(){
		List<Integer> totalWeek= new ArrayList<>();
		for (int i = 1; i <= workingWeekPerMonth; i++) {
			totalWeek.add(i);
		}
		return totalWeek ;
	}
	
	/*************************************************
	 * Schedule management method
	 *************************************************/
	public void selectDay(int startDay, int selectedIndex) {
		this.scheduleStartPoint= startDay;
		this.scheduleEndPoint= startDay;
		this.selectedIndex= selectedIndex;
	}
	
	/*************************************************
	 * Schedule management method
	 *************************************************/
	public void submitSchedule(){
		int workingDayCount = project.getProjectScheduleList().get(selectedIndex).getWorkingList().size();
		if (scheduleStartPoint <= scheduleEndPoint) {
			for (int i = scheduleStartPoint; i <= scheduleEndPoint; i++) {
				if (workingDayCount >= scheduleStartPoint ) {
					if ( workingDayCount <= scheduleEndPoint) {
						scheduleEndPoint = workingDayCount;
					}
					project.getProjectScheduleList().get(selectedIndex).getWorkingList().get(i-1).setWorking(true);
				}
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Start point must be greater than end point !!!"));
		}
	}
	
	/**
	 * Revised By : Sai Kyaw Ye Myint 
	 * Revised Date : 2018/07/18
	 * Explanation : Added startDateChange listener for start date change.
	 */
	public void startDateChange(){
		if (this.tmpStartDate == null) {
			this.tmpStartDate = project.getStartDate();
		}
		if (project.getStartDate() == null || project.getEndDate() == null) {
			return;
		}
		if(!isValidFromAndToDate(project.getStartDate(), project.getEndDate())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Start date must be before end date !!!"));
			return;
		}
		findStartEndPoint();
		if (this.scheduleTableVisible && project.getProjectScheduleList().size() != 0) {
			/*Month increase*/
			int diffMonth = 0;
			int daydiff = 0;
			if (project.getStartDate().before(this.tmpStartDate)) {
				diffMonth = monthsBetween(project.getStartDate(), this.tmpStartDate);
				daydiff = (diffMonth * (workingWeekPerMonth * workingDayPerWeek));
				for (ProjectSchedule schedule : project.getProjectScheduleList()) {
					for (ScheduleStartEnd scheduleStarEnd : schedule.getScheduleStartEndList()) {
						int start = scheduleStarEnd.getStartPoint();
						int end = scheduleStarEnd.getEndPoint();
						
						start = start + daydiff;
						end = end + daydiff;
						
						scheduleStarEnd.setStartPoint(start);
						scheduleStarEnd.setEndPoint(end);
					}
				}
			}else {
				/*Month decrease*/
				diffMonth = monthsBetween(this.tmpStartDate, project.getStartDate());
				daydiff = (diffMonth * (workingWeekPerMonth * workingDayPerWeek));
				int dayCount = calculateMonthCount(project.getStartDate(), project.getEndDate()).size() * (workingWeekPerMonth * workingDayPerWeek) ;
				for (ProjectSchedule schedule : project.getProjectScheduleList()) {
					for (ScheduleStartEnd scheduleStarEnd : schedule.getScheduleStartEndList()) {
						int start = scheduleStarEnd.getStartPoint();
						int end = scheduleStarEnd.getEndPoint();
						if (start > daydiff) {
							start = start - daydiff;
							end = end - daydiff;
						}else if(end <= daydiff){
							start = dayCount +1;
							end = dayCount +1;
						}else {
							start = 1;
							end = end - daydiff;
						}
						scheduleStarEnd.setStartPoint(start);
						scheduleStarEnd.setEndPoint(end);
					}
				}
			}
			this.tmpStartDate = project.getStartDate();
			prepareSchedule();
		}
	}
	
	/**
	 * Revised By : Sai Kyaw Ye Myint 
	 * Revised Date : 2018/07/18
	 * Explanation : Added monthsBetween method to count months between two date.
	 */
	public static int monthsBetween(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int count = 0;

		while (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR) || cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH)) {
			cal1.add(Calendar.MONTH, 1);
			count++;
		}
	    return  count; 
	}
	
	/**
	 * Revised By : Sai Kyaw Ye Myint 
	 * Revised Date : 2018/07/18
	 * Explanation : Added endDateChange listener for end date change.
	 */
	public void endDateChange(){
		if (this.tmpEndDate == null) {
			this.tmpEndDate = project.getEndDate();
		}
		if (project.getStartDate() == null || project.getEndDate() == null) {
			return;
		}
		if(!isValidFromAndToDate(project.getStartDate(), project.getEndDate())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Start date must be before end date !!!"));
			return;
		}
		if (this.scheduleTableVisible && project.getProjectScheduleList().size() != 0) {
			if (project.getStartDate().after(this.tmpEndDate)) {
				for (ProjectSchedule projectSchedule : project.getProjectScheduleList()) {
					if (projectSchedule.getWorkingList() != null) {
						projectSchedule.getWorkingList().clear();
					}
				}
				findStartEndPoint();
				prepareSchedule();
			}else {
				findStartEndPoint();
				prepareSchedule();
			}
			this.tmpEndDate = project.getEndDate();
		}
	}
	
	/*************************************************
	 * Schedule management method
	 *************************************************/
	public void deleteSchedule(){
		if (scheduleStartPoint <= scheduleEndPoint) {
			for (int i = scheduleStartPoint; i <= scheduleEndPoint; i++) {
				project.getProjectScheduleList().get(selectedIndex).getWorkingList().get(i-1).setWorking(false);
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Start point must be greater than end point !!!"));
		}
	}
	
	/*************************************************
	 * Schedule management method
	 *************************************************/
	private void findStartEndPoint(){
		
		for (ProjectSchedule projectSchedule : project.getProjectScheduleList()) {
			boolean isStart= false;
			int start=0;
			int end=0;
			List<ScheduleStartEnd> scheduleStartEndList = new ArrayList<>();
			ScheduleStartEnd scheduleStartEnd;
			for (int i = 1; i <= projectSchedule.getWorkingList().size(); i++) {
				/*Start index found*/
				if (projectSchedule.getWorkingList().get(i-1).isWorking() && !isStart) {
					start=i;
					isStart = true;
				}
				
				if(projectSchedule.getWorkingList().get(i-1).isWorking() && isStart){
					end = i;
					if (i ==  projectSchedule.getWorkingList().size()) {
						scheduleStartEnd = new ScheduleStartEnd();
						scheduleStartEnd.setStartPoint(start);
						scheduleStartEnd.setEndPoint(end);
						scheduleStartEndList.add(scheduleStartEnd);
						isStart = false;
					}
				}else if(!projectSchedule.getWorkingList().get(i-1).isWorking() && isStart) {
					scheduleStartEnd = new ScheduleStartEnd();
					scheduleStartEnd.setStartPoint(start);
					scheduleStartEnd.setEndPoint(end);
					scheduleStartEndList.add(scheduleStartEnd);
					isStart = false;
				}
			}
			projectSchedule.setScheduleStartEndList(scheduleStartEndList);
		}
	}
	
	/*************************************************
	 * Schedule management method
	 *************************************************/
	private List<WorkDayForProjectSchedule> addWorkDay(int starWork,int finishWork){
		List<WorkDayForProjectSchedule> workDayList= new ArrayList<>();
		WorkDayForProjectSchedule day;
		int loopControl= (workingWeekPerMonth * workingDayPerWeek)*this.scheduleYMList.size();
		days.clear();
		for (int i = 1; i <= loopControl; i++) {
			day= new WorkDayForProjectSchedule();
			if (starWork > 0 && finishWork > 0) {
				if (i >= starWork && i <= finishWork) {
					day.setWorking(true);
				} else {
					day.setWorking(false);
				}
				day.setDaysPerWeek(i);
			} else {
				day.setWorking(false);
				day.setDaysPerWeek(i);
			}
			workDayList.add(day);
			days.add(i);
		}
		return workDayList;
	}
	
	/*************************************************
	 * Schedule management method
	 *************************************************/
	public void prepareSchedule(){
		if (project.getStartDate() != null || project.getEndDate() != null) {
			if (!isValidFromAndToDate(project.getStartDate(), project.getEndDate())) {
				String errorMessage = "Please define Start Date, End Date and Project Volume.";
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "", errorMessage));
				this.scheduleTableVisible= false;
			}else {
				scheduleYMList = calculateMonthCount(project.getStartDate(), project.getEndDate());
				weekPerMonthList = getTotalWeek();
			}
		}else {
			if (!viewOnly) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please enter start date and end date first!!!"));
			}
			this.scheduleTableVisible= false;
		}
		
		if (project.getProjectScheduleList().size() > 0) {
			scheduleTableVisible= true;
			int index= 0 ;
			for(ProjectSchedule prjSchedule : project.getProjectScheduleList()){
				prjSchedule.setWorkingList(addWorkDay(0, 0));
				for (ScheduleStartEnd scheduleStartEnd : prjSchedule.getScheduleStartEndList()) {
					this.scheduleStartPoint= scheduleStartEnd.getStartPoint();
					this.scheduleEndPoint= scheduleStartEnd.getEndPoint();
					this.selectedIndex= index;
					submitSchedule();
				}
				index++;
			}
			this.scheduleStartPoint= 0;
			this.scheduleEndPoint= 0;
			this.selectedIndex= 0;
		}
	}
	
	/*************************************************
	 * Calculate total month from project start date and end date 
	 *************************************************/
	private List<String> calculateMonthCount(Date startDate, Date endDate){
		List<String> monthCount= new ArrayList<>();
		Calendar cStart = Calendar.getInstance(); 
		cStart.setTime(startDate);
		Calendar cEnd = Calendar.getInstance(); 
		cEnd.setTime(endDate);
		
		cEnd.add(Calendar.DAY_OF_MONTH, 1);
		do {
			String str= cStart.get(Calendar.YEAR) + "-" + new SimpleDateFormat("MMM").format(cStart.getTime());
			if (!monthCount.contains(str)) {
				monthCount.add(str);
			}
		    cStart.add(Calendar.DAY_OF_MONTH, 1);
		}while (cStart.before(cEnd));
		
		return monthCount;
	}
	
	/*************************************************
	 * Validation method for start date and end date.  
	 *************************************************/
	public boolean isValidFromAndToDate(Date fromDate, Date toDate) {

		if ((fromDate.equals(toDate) || fromDate.before(toDate))) {
			return true;
		} else {
			return false;
		}

	}
	
	/*****************************************
	 * Save project information.
	 *****************************************/
	public void submitProjectInfo() {
		User loginUser= (User) getParam(Constants.LOGIN_USER);
		project.setDelDiv(DeleteDiv.ACTIVE);
		project.setLastUpdBy(loginUser);
		project.setLastUpdDate(new Date());
		ArrayList<ContactPerson> tmpContactPersonList = new ArrayList<ContactPerson>();
		
		findStartEndPoint();
		
		/**
		 * Revised By : Sai Kyaw Ye Myint 
		 * Revised Date : 2018/07/11
		 * Explanation : Removed unnecessary mandatory check.
		 */
		if (project.getStartDate() == null || project.getEndDate() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please define Start Date and End Date."));
		} else if (project.getProjectVolumeList().size() == 0) {
			addErrorMessage(null, MessageId.REQUIRED, "Project Volume");
		}else if(!isValidFromAndToDate(project.getStartDate(), project.getEndDate())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Start date must be before end date !!!"));
		}else{
			
			if (project.getProjectVolumeList().size() == project.getProjectScheduleList().size()) {
				for (int i = 0; i < project.getProjectVolumeList().size(); i++) {
					String prjVolumePhase = project.getProjectVolumeList().get(i).getPhase();
					String prjSchedulePhase = project.getProjectScheduleList().get(i).getPhase();
					if (!prjVolumePhase.equals(prjSchedulePhase)) {
						FacesContext.getCurrentInstance().addMessage(null,
														new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
														"Phase data is not same in Project Volume and Project Schedule. Please redefine schedule."));
						return;
					}
				}
			}
			if(project.getProjectVolumeList().size() < project.getProjectScheduleList().size()){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						"Phase data is not same in Project Volume and Project Schedule. Please redefine schedule."));
				return;
			}
			
			
				for(ContactPerson ccp: clientContactPersonList){
					if (!isEmbty(ccp.getName()) || !isEmbty(ccp.getEmail()) || !isEmbty(ccp.getPhone()) || !isEmbty(ccp.getFax())) {
						tmpContactPersonList.add(ccp);
					}
				}
				
				for(ContactPerson scp: serviceContactPersonList){
					if (!isEmbty(scp.getName()) || !isEmbty(scp.getEmail()) || !isEmbty(scp.getPhone()) || !isEmbty(scp.getFax())) {
						tmpContactPersonList.add(scp);
					}
				}
				
				List<CommunicationPlan> tmpCommunicationContainer = new ArrayList<>();
				for (CommunicationPlan communication : project.getCommunicationPlanList()) {
					if (!isEmbty(communication.getCommgroups()) || !isEmbty(communication.getCommType()) || !isEmbty(communication.getCommPurpose())) {
						tmpCommunicationContainer.add(communication);
					}
				}
				project.setCommunicationPlanList(tmpCommunicationContainer);
				
				List<ProjectControl> tmpProjectControlcContainer = new ArrayList<>();
				for (ProjectControl projectControl : project.getProjectControlList()) {
					if (!isEmbty(projectControl.getPrjCtlDeliverables()) || !isEmbty(projectControl.getPrjCtlRemark())) {
						tmpProjectControlcContainer.add(projectControl);
					}
				}
				project.setProjectControlList(tmpProjectControlcContainer);
				
				List<ProjectDeliverable> tmpProjectDeliverableContainer = new ArrayList<>();
				for (ProjectDeliverable projectDeliverable : project.getProjectDeliverablesList()) {
					if (!isEmbty(projectDeliverable.getPrjDelPhase()) || !isEmbty(projectDeliverable.getPrjDelDesignType()) || !isEmbty(projectDeliverable.getPrjDelRemark())) {
						tmpProjectDeliverableContainer.add(projectDeliverable);
					}
				}
				project.setProjectDeliverablesList(tmpProjectDeliverableContainer);
				
				List<RiskFactors> tmpRiskFactorsContainer = new ArrayList<>();
				for (RiskFactors riskFactors : project.getRiskFactorList()) {
					if (!isEmbty(riskFactors.getRiskExpected()) || !isEmbty(riskFactors.getRiskimpact()) || !isEmbty(riskFactors.getRiskCounterMeasure())) {
						tmpRiskFactorsContainer.add(riskFactors);
					}
				}
				project.setRiskFactorList(tmpRiskFactorsContainer);
				
				project.setContactPersonList(tmpContactPersonList);
				try {
					if (!editFlag) {
						project.setCreatedDate(new Date());
						project.setProjectStatusDiv(ProjectStatusDiv.NOT_END);
						projectInformationService.addNewProjectInformation(project);
					}else {
						/*Clear data first!!!*/
						Project oldProjectData = projectService.findProject(project.getId());
						projectInformationService.clearProjectInformation(oldProjectData);
						
						Date now = new Date();
						project.setLastUpdBy(loginUser);
						project.setLastUpdDate(now);
						projectInformationService.updateProjectInformation(project);
					}
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Project Information submit error"));
					return;
				}
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Project Information is successfully saved!!!"));
				tmpCommunicationContainer.clear();
				tmpProjectControlcContainer.clear();
				tmpProjectDeliverableContainer.clear();
				tmpRiskFactorsContainer.clear();
				controlForm();
		}
		
	}
	
	private boolean isEmbty(String str){
		return str.equals("") || str == null;
	}
	
	/*****************************************
	 * Reset Project Information.
	 *****************************************/
	public void resetProjectInfo() {
		String tmpProjectId = null;
		
		if (editFlag) {
			tmpProjectId = project.getId();
			project = projectService.findProject(tmpProjectId);
			
			clientContactPersonList = new ArrayList<ContactPerson>();
			serviceContactPersonList = new ArrayList<ContactPerson>();
			
			/* Separate Client and Service Provider Contact Person Information. */
			if(project.getContactPersonList() != null){
				for(ContactPerson cp: project.getContactPersonList()){
					if(cp.getOrganization() == OrganizationDev.CLIENT){
						clientContactPersonList.add(cp);
					}else {
						serviceContactPersonList.add(cp);
					}
				}
			}
			
			/* Calculate Project Volume. */
			project = calculateWork(project);
			project = calculateReview(project);
			
			/* Schedule. */
			scheduleYMList = new ArrayList<>();
			rowRepeat= new ArrayList<>();
			rowRepeat.add("Year/Week");
			weekPerMonthList = new ArrayList<>();
			
			selectedIndex = 0;
			scheduleStartPoint = 0;
			scheduleEndPoint = 0;
			
			this.tmpStartDate = project.getStartDate();
			
			prepareSchedule();
			findStartEndPoint();
		}else {
			controlForm();
		}

	}
	
	/**
	 * Created Date : 2017/09/21
	 * Created By   : Kyi Saw Win
	 * Explanation  : Created for project information detail.
	 */
	@SuppressWarnings("unchecked")
	public void detail(){
		
		/* Get selected project from PRJ003. */
		project = new Project();
		project = (Project) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("project");
		viewOnly = (boolean) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backInquiryFlag");
		projectSerachData = (HashMap<String, String>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("projectSerachData");
		
		clientContactPersonList = new ArrayList<ContactPerson>();
		serviceContactPersonList = new ArrayList<ContactPerson>();
		
		/* Separate Client and Service Provider Contact Person Information. */
		if(project.getContactPersonList() != null){
			for(ContactPerson cp: project.getContactPersonList()){
				if(cp.getOrganization() == OrganizationDev.CLIENT){
					clientContactPersonList.add(cp);
				}
				else{
					serviceContactPersonList.add(cp);
				}
				
			}
		}
		
		/* Calculate Project Volume. */
		project = calculateWork(project);
		project = calculateReview(project);
		
		/* Schedule. */
		scheduleYMList = new ArrayList<>();
		rowRepeat= new ArrayList<>();
		rowRepeat.add("Year/Week");
		weekPerMonthList = new ArrayList<>();
		
		selectedIndex = 0;
		scheduleStartPoint = 0;
		scheduleEndPoint = 0;
		
		prepareSchedule();
		
	}
	
	/*****************************************
	 * Export project information to pdf file.
	 *****************************************/
	public void exportPDF(){
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext context = (ServletContext) externalContext.getContext();
		String fileName = ProjectInformationPDFExportBean.exportProjectInformationPDF(project, scheduleYMList, weekPerMonthList);
		if (fileName != null) {
			try {
				File file = new File(context.getRealPath("/upload/" + fileName));
				FileInputStream fileInputStream = new FileInputStream(file);
				byte[] bfile = new byte[(int) file.length()];
				
				externalContext = FacesContext.getCurrentInstance().getExternalContext();
				fileInputStream.read(bfile);
				fileInputStream.close();
				
				InputStream stream = new ByteArrayInputStream(bfile);
				setDownload(new DefaultStreamedContent(stream, externalContext.getMimeType(file.getName()),fileName));

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "", "There is no data to export"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "", "There is no data to export"));
		}
	}
	
	/*******************************
	 * Back function for PRJ002
	 *******************************/
	public String back(){
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("project", project);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectSerachData", projectSerachData);
		return "/faces/view/project/PRJ003.xhtml?faces-redirect=true";
	}
	
	/*******************************
	 * Back function for PRJ001
	 *******************************/
	public String editPrjInfoBack(){
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backInquiryFlag", true);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("project", project);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectSerachData", projectSerachData);
		
		return "/faces/view/mgtreports/MNR004.xhtml?faces-redirect=true";
	}
	
/***************************************************************************************
 * The following are getter and setter method of each class variable.
 ***************************************************************************************/
	public IProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<String> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<String> currencyList) {
		this.currencyList = currencyList;
	}

	public List<ContactPerson> getClientContactPersonList() {
		return clientContactPersonList;
	}

	public void setClientContactPersonList(
			List<ContactPerson> clientContactPersonList) {
		this.clientContactPersonList = clientContactPersonList;
	}

	public List<ContactPerson> getServiceContactPersonList() {
		return serviceContactPersonList;
	}

	public void setServiceContactPersonList(
			List<ContactPerson> serviceContactPersonList) {
		this.serviceContactPersonList = serviceContactPersonList;
	}

	public List<String> getEmailType() {
		return emailType;
	}

	public void setEmailType(List<String> emailType) {
		this.emailType = emailType;
	}

	public List<String> getCommFrequency() {
		return commFrequency;
	}

	public void setCommFrequency(List<String> commFrequency) {
		this.commFrequency = commFrequency;
	}

	public List<String> getCommMethod() {
		return commMethod;
	}

	public void setCommMethod(List<String> commMethod) {
		this.commMethod = commMethod;
	}

	public List<String> getCommLanguage() {
		return commLanguage;
	}

	public void setCommLanguage(List<String> commLanguage) {
		this.commLanguage = commLanguage;
	}

	public List<String> getRiskProbabilityList() {
		return riskProbabilityList;
	}

	public void setRiskProbabilityList(List<String> riskProbabilityList) {
		this.riskProbabilityList = riskProbabilityList;
	}

	public double getWorkingHourPerDay() {
		return workingHourPerDay;
	}

	public void setWorkingHourPerDay(double workingHourPerDay) {
		this.workingHourPerDay = workingHourPerDay;
	}

	public int getWorkingWeekPerMonth() {
		return workingWeekPerMonth;
	}

	public void setWorkingWeekPerMonth(int workingWeekPerMonth) {
		this.workingWeekPerMonth = workingWeekPerMonth;
	}

	public int getWorkingDayPerWeek() {
		return workingDayPerWeek;
	}

	public void setWorkingDayPerWeek(int workingDayPerWeek) {
		this.workingDayPerWeek = workingDayPerWeek;
	}

	public IProjectInformationService getProjectInformationService() {
		return projectInformationService;
	}

	public void setProjectInformationService(
			IProjectInformationService projectInformationService) {
		this.projectInformationService = projectInformationService;
	}

	public List<String> getScheduleYMList() {
		return scheduleYMList;
	}

	public void setScheduleYMList(List<String> scheduleYMList) {
		this.scheduleYMList = scheduleYMList;
	}

	public List<Integer> getWeekPerMonthList() {
		return weekPerMonthList;
	}

	public void setWeekPerMonthList(List<Integer> weekPerMonthList) {
		this.weekPerMonthList = weekPerMonthList;
	}

	public boolean isScheduleTableVisible() {
		return scheduleTableVisible;
	}

	public void setScheduleTableVisible(boolean scheduleTableVisible) {
		this.scheduleTableVisible = scheduleTableVisible;
	}

	public List<Integer> getDays() {
		return days;
	}

	public void setDays(List<Integer> days) {
		this.days = days;
	}

	public List<String> getRowRepeat() {
		return rowRepeat;
	}

	public void setRowRepeat(List<String> rowRepeat) {
		this.rowRepeat = rowRepeat;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public int getScheduleStartPoint() {
		return scheduleStartPoint;
	}

	public void setScheduleStartPoint(int scheduleStartPoint) {
		this.scheduleStartPoint = scheduleStartPoint;
	}

	public int getScheduleEndPoint() {
		return scheduleEndPoint;
	}

	public void setScheduleEndPoint(int scheduleEndPoint) {
		this.scheduleEndPoint = scheduleEndPoint;
	}

	public boolean isBtnSubmit() {
		return btnSubmit;
	}

	public void setBtnSubmit(boolean btnSubmit) {
		this.btnSubmit = btnSubmit;
	}

	public DefaultStreamedContent getDownload() {
		return download;
	}

	public void setDownload(DefaultStreamedContent download) {
		this.download = download;
	}

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	public boolean isBtnBackVisible() {
		return btnBackVisible;
	}

	public void setBtnBackVisible(boolean btnBackVisible) {
		this.btnBackVisible = btnBackVisible;
	}

}
