/***************************************************************************************
 * @author Soe Thiha Lwin
 * @Date 2018-09-25
 * @Version 1.0
 * @Purpose Created for working hour registration function.
 ***************************************************************************************/

package com.dat.whm.web.workinghourregistration;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.dat.whm.exception.SystemException;
import com.dat.whm.workinghour.entity.WorkingHour;
import com.dat.whm.workinghour.service.interfaces.IWorkingHourService;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.MessageId;

@ManagedBean(name = "WorkingHourRegistrationBean")
@ViewScoped
public class WorkingHourRegistrationBean extends BaseBean {
	private boolean createNew;

	@ManagedProperty(value = "#{WorkingHourService}")
	private IWorkingHourService workinghourService;
	private WorkingHour workinghour;
	private List<WorkingHour> workinghours;
	boolean btnAdd;

	@PostConstruct
	public void init() {
		createNewWorkingHour();
		load();
	}

	private void load() {

		workinghours = workinghourService.findAllWorkingHours();

	}

	public void createNewWorkingHour() {
		createNew = true;
		workinghour = new WorkingHour();
		btnAdd = false;
	}

	public void prepareUpdateWorkingHour(WorkingHour workinghour) {
		createNew = false;
		this.workinghour = workinghour;
		btnAdd = false;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public boolean isValidFromAndToDate(Date startDate, Date endDate) {

		if (startDate.before(endDate)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean checkStartDate() {
		btnAdd = false;

		if (workinghour.getstartDate() == null
				|| workinghour.getendDate() == null) {
			return true;
		}

		if (!isValidFromAndToDate(workinghour.getstartDate(),
				workinghour.getendDate())) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"",
									"Start Date and End Date must be different or Start Date must be before End Date!"));
			btnAdd = true;
			return false;
		}

		return true;
	}

	public boolean checkEndDate() {
		btnAdd = false;

		if (workinghour.getstartDate() == null
				|| workinghour.getendDate() == null) {
			return true;
		}
		if (!isValidFromAndToDate(workinghour.getstartDate(),
				workinghour.getendDate())) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"",
									"Start Date and End Date must be different or Start Date must be before End Date!"));
			btnAdd = true;
			return false;
		}

		return true;
	}

	public void addNewWorkingHour() {
		try {
			boolean flag = false;

			List<WorkingHour> startdate = workinghourService
					.findByStartDate(workinghour.getstartDate());
			List<WorkingHour> enddate = workinghourService
					.findByEndDate(workinghour.getendDate());

			if (workinghour.getstartDate() != null
					&& workinghour.getendDate() != null) {

				if (!startdate.isEmpty() || !enddate.isEmpty()) {

					String errorMessage = "Working Hour is already defined for this period!";
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "",
									errorMessage));
					flag = true;
				}

			}

			List<WorkingHour> periodList = workinghourService
					.findDurationPeriod(workinghour.getstartDate(),
							workinghour.getendDate());

			if (!flag) {
				if (periodList.isEmpty()) {
					workinghourService.addNewWorkingHour(workinghour);

					addInfoMessage(null, MessageId.INSERT_SUCCESS,
							"WorkingHour");
					createNewWorkingHour();
					load();
				} else {
					String errorMessage = "Working Hour is already defined for this period!";
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "",
									errorMessage));
					flag = true;
				}
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void updateWorkingHour() {
		try {

			boolean flag = false;

			List<WorkingHour> startdate = workinghourService
					.findByStartDateUpdate(workinghour.getstartDate(),
							workinghour.getId());
			List<WorkingHour> enddate = workinghourService.findByEndDateUpdate(
					workinghour.getendDate(), workinghour.getId());

			if (workinghour.getstartDate() != null
					&& workinghour.getendDate() != null) {

				if (!startdate.isEmpty() || !enddate.isEmpty()) {

					String errorMessage = "Working Hour is already defined for this period!";
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "",
									errorMessage));
					flag = true;
					load();
				}

			}

			List<WorkingHour> periodListUpdate = workinghourService
					.findDurationPeriodUpdate(workinghour.getstartDate(),
							workinghour.getendDate(), workinghour.getId());

			if (!flag) {
				if (periodListUpdate.isEmpty()) {
					workinghourService.updateWorkingHour(workinghour);
					addInfoMessage(null, MessageId.UPDATE_SUCCESS,
							"WorkingHour");
					createNewWorkingHour();
					load();

				} else {
					String errorMessage = "Working Hour is already defined for this period!";
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "",
									errorMessage));
					flag = true;
					load();
				}
			}
		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public void deleteWorkingHour(WorkingHour workinghour) {

		try {
			workinghourService.deleteWorkingHour(workinghour);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, "WorkingHour");
			createNewWorkingHour();
			load();
		} catch (SystemException e) {
			handelSysException(e);
		}

	}

	public IWorkingHourService getWorkingHourService() {
		return workinghourService;
	}

	public void setWorkingHourService(IWorkingHourService workinghourService) {
		this.workinghourService = workinghourService;
	}

	public WorkingHour getWorkinghour() {
		return workinghour;
	}

	public void setWorkinghour(WorkingHour workinghour) {
		this.workinghour = workinghour;
	}

	public List<WorkingHour> getWorkinghours() {
		return workinghours;
	}

	public void setWorkinghour(List<WorkingHour> workinghours) {
		this.workinghours = workinghours;
	}

	public boolean isBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(boolean btnAdd) {
		this.btnAdd = btnAdd;
	}

	public IWorkingHourService getWorkinghourService() {
		return workinghourService;
	}

	public void setWorkinghourService(IWorkingHourService workinghourService) {
		this.workinghourService = workinghourService;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	public void setWorkinghours(List<WorkingHour> workinghours) {
		this.workinghours = workinghours;
	}

}
