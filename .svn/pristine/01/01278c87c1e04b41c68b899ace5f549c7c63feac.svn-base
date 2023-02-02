/***************************************************************************************
 * @author Aye Myat Mon
 * @Date 2018-02-05
 * @Version 1.0
 * @Purpose Created for HR Overhead Cost Management function.
 ***************************************************************************************/
package com.dat.whm.web.hrcost;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.exception.SystemException;
import com.dat.whm.hrcost.entity.HRCost;
import com.dat.whm.hrcost.service.interfaces.IHRCostService;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.MessageId;

@ManagedBean(name = "HRCostManagementBean")
@ViewScoped
public class HRCostManagementBean extends BaseBean {
	private boolean createNew;
	@ManagedProperty(value = "#{HRCostService}")
	private IHRCostService hrcostService;
	private HRCost hrcost;
	private List<HRCost> listHrcost;
	boolean btnAdd;
	boolean textYear;

	@PostConstruct
	public void init() {
		createNewHRCost();
		load();
	}

	private void load() {
		listHrcost = hrcostService.findAllHRCostS();

	}

	public void createNewHRCost() { 
		createNew = true;
		hrcost = new HRCost();
		setTextYear(false);

	}

	public void prepareUpdateHRCost(HRCost hrcost) {
		createNew = false;
		this.hrcost = hrcost;
		setTextYear(true);
	}

	public void updateHRCost() {
		try {

			hrcostService.updateHRCostS(hrcost);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, "HR Cost");
			createNewHRCost();
			load();

		} catch (SystemException se) {
			handelSysException(se);
		}
	}

	public void deleteHRCost(HRCost hrCost) {
		try {
			hrcostService.deleteHRCostS(hrCost);
			addInfoMessage(null,MessageId.DELETE_SUCCESS, "HR Cost");
			createNewHRCost();
			load();
		} catch (SystemException se) {
			handelSysException(se);
		}
	}

	public void addNewHRCost() {
		try {
			List<HRCost> result = null;
			result = hrcostService.findByYearS(hrcost.getYear(),DeleteDiv.DELETE);

			if (!result.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"Year already exists!!"));
			} else {
				hrcost.setDelDiv(DeleteDiv.ACTIVE);
				hrcostService.insertHRCostS(hrcost);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, "HR Cost");

			}
			createNewHRCost();
			load();
		} catch (SystemException se) {
			handelSysException(se);
		}

	}

	public void checkYear() {
		List<HRCost> result = null;
		result = hrcostService.findByYearS(hrcost.getYear(), DeleteDiv.ACTIVE);
		try {
			if (!result.isEmpty()) {
				setBtnAdd(true);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
								"Year has already existed"));
				;
			} else {
				setBtnAdd(false);
			}

		} catch (SystemException e) {
			handelSysException(e);
		}
	}

	public IHRCostService getHrcostService() {
		return hrcostService;
	}

	public void setHrcostService(IHRCostService hrcostService) {
		this.hrcostService = hrcostService;
	}

	public HRCost getHrcost() {
		return hrcost;
	}

	public void setHrcost(HRCost hrcost) {
		this.hrcost = hrcost;
	}

	public List<HRCost> getListHrcost() {
		return listHrcost;
	}

	public void setListHrcost(List<HRCost> listHrcost) {
		this.listHrcost = listHrcost;
	}

	public boolean iscreateNew() {
		return createNew;
	}

	public boolean isBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(boolean btnAdd) {
		this.btnAdd = btnAdd;
	}

	public boolean isTextYear() {
		return textYear;
	}

	public void setTextYear(boolean textYear) {
		this.textYear = textYear;
	}

}
