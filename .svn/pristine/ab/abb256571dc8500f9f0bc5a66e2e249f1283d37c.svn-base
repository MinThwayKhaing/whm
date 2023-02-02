/***************************************************************************************
 * @author Ye Phyo Ko
 * @Date 2018-01-09
 * @Version 1.0
 * @Purpose Created for Holiday Info Management function.
 ***************************************************************************************/

package com.dat.whm.web.holiday;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.dat.whm.exception.SystemException;
import com.dat.whm.holidays.entity.Holidays;
import com.dat.whm.holidays.service.interfaces.IHolidaysService;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.MessageId;
import com.dat.whm.web.common.WhmUtilities;

@ManagedBean(name = "HolidaysInfoManagementBean")
@ViewScoped
public class HolidaysInfoManagementBean extends BaseBean{
	private boolean createNew;
	/**
	 * Revised By	: Aye Myat Mon
	 * Revised Date	: 2018/08/20 
	 * Explanation	: Added WhmUtilities for Date Filter.
	 */
	private WhmUtilities whmUtilities;
	
	@ManagedProperty(value = "#{HolidaysService}")
	private IHolidaysService holidayService;
	private Holidays holiday;
	private List<Holidays> holidays;
	boolean btnAdd;
	
	
	@PostConstruct
	public void init() {
		createNewHoliday();
		load();
	}
	
	private void load() {
		holidays = holidayService.findAllHoliday();
		/**
		 * Revised By	: Aye Myat Mon
		 * Revised Date	: 2018/08/20 
		 * Explanation	: Added WhmUtilities for Date Filter.
		 */
		whmUtilities = new WhmUtilities();
	}

	public void createNewHoliday() {
		createNew = true;
		holiday = new Holidays();
		btnAdd = false;
	}
	
	public void prepareUpdateHoliday(Holidays holiday) {
		createNew = false;
        this.holiday = holiday;
        btnAdd = false;
	}
	
	public boolean isCreateNew() {
		return createNew;
	}
	
	public boolean checkDate() {
		btnAdd = false;
		List<Holidays> result = holidayService .findByDate(holiday.getDate());
		if(!result.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							"Date already exists!!"));
			btnAdd = true;
			return false;
		}
		return true;
	}

	public void addNewHoliday(){
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTime(holiday.getDate());
			String year = Integer.toString(cal.get(Calendar.YEAR));
			String month = String.format("%02d", cal.get(Calendar.MONTH)+1);

				holiday.setMonth(month);
				holiday.setYear(year); 		
				holidayService.addNewHoliday(holiday);
				addInfoMessage(null, MessageId.INSERT_SUCCESS,
						"Holiday");
				createNewHoliday();
				load();
				
		}catch(SystemException e){
			handelSysException(e);
		}
	}
	
	public void updateHolidays(){
		try {
			/**
			 * Revised By	: Sai Kyaw Ye Myint 
			 * Revised Date	: 2018/03/15 
			 * Explanation	: Check date from database before update holiday.
			 */
			Calendar cal = Calendar.getInstance();
			cal.setTime(holiday.getDate());
			String year = Integer.toString(cal.get(Calendar.YEAR));
			String month = String.format("%02d", cal.get(Calendar.MONTH)+1);
			
				holiday.setMonth(month);
				holiday.setYear(year);
				holidayService.updateHoliday(holiday);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, "Holiday");
				createNewHoliday();
				load();

		} catch (SystemException e) {
			handelSysException(e);
		}
	}
	
	public void deleteHoliday(Holidays holiday) {

		try {
			holidayService.deleteHoliday(holiday);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, "Holiday");
			createNewHoliday();
			load();
		} catch (SystemException e) {
			handelSysException(e);
		}

	}

	public IHolidaysService getHolidayService() {
		return holidayService;
	}

	public void setHolidayService(IHolidaysService holidayService) {
		this.holidayService = holidayService;
	}

	public Holidays getHoliday() {
		return holiday;
	}

	public void setHoliday(Holidays holiday) {
		this.holiday = holiday;
	}

	public List<Holidays> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<Holidays> holidays) {
		this.holidays = holidays;
	}

	public boolean isBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(boolean btnAdd) {
		this.btnAdd = btnAdd;
	}
	
	/**
	 * Revised By	: Aye Myat Mon
	 * Revised Date	: 2018/08/20 
	 * Explanation	: Added getter and setter of WhmUtilities.
	 */
	public WhmUtilities getWhmUtilities() {
		return whmUtilities;
	}

	public void setWhmUtilities(WhmUtilities whmUtilities) {
		this.whmUtilities = whmUtilities;
	}
	
	

		
}
