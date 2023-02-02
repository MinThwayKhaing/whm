/***************************************************************************************
 * @author Aye Chan Thar Soe
 * @Date 
 * @Version 
 * @Purpose <<>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.web.dailyreport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.service.interfaces.IDailyReportService;
import com.dat.whm.holidays.entity.Holidays;
import com.dat.whm.holidays.service.interfaces.IHolidaysService;
import com.dat.whm.user.entity.User;
import com.dat.whm.user.service.interfaces.IUserService;
import com.dat.whm.web.Constants;
import com.dat.whm.web.common.BaseBean;
import com.dat.whm.web.common.DateHeader;
import com.dat.whm.web.common.WhmUtilities;
import com.dat.whm.workinghour.service.interfaces.IWorkingHourService;

@ManagedBean(name = "DailyReportRegistrationStatusAdminBean")
@RequestScoped
public class DailyReportRegistrationStatusAdminBean extends BaseBean {

	@ManagedProperty(value = "#{DailyReportService}")
	private IDailyReportService dailyReportService;
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	@ManagedProperty(value = "#{HolidaysService}")
	private IHolidaysService holidaysService;
	
	@ManagedProperty(value = "#{WorkingHourService}")
	private IWorkingHourService workingHourService;

	private DailyReport dailyReport;
	private DailyActivity dailyActivity;
	private List<DailyReport> dailyReportList = new ArrayList<DailyReport>();
	private List<DailyActivity> dailyActivityList = new ArrayList<DailyActivity>();

	private String year;
	private List<String> years;
	private String month;
	private String currentmonth;
	private TreeMap<Integer, String> months = new TreeMap<Integer, String>();

	List<DateHeader> headerList = new ArrayList<DateHeader>();

	private List<Integer> publicHolidaysList = new ArrayList<Integer>();

	List<RegistrationStatusData> resultList = new ArrayList<>();
	List<Object[]> regUserList = new ArrayList<>();
	List<Object[]> allUserList = new ArrayList<>();
	List<Object[]> allTeamList = new ArrayList<>();
	List<UserTeamInfo> UserTeamList = new ArrayList<>();
	User loginUser;
	private User user = new User();

	private double workingHourPerDay=0.0;

	private boolean showSelect = false;
	private boolean showBtn = false;

	@PostConstruct
	public void init() {
		
		load();
		
	}

	public void load() {
		showBtn = false;
		getActiveYear();
		prepareMonths();
		getCurrentYearMonth();
		prepareSearchData();
		loginUser = (User) getParam(Constants.LOGIN_USER);
		workingHourPerDay =7.5;
		
	}
	
	/* Get current year from year list from daily_report table. */
	public void getCurrentYearMonth() {
		 Calendar now = Calendar.getInstance();
		 year = new String();
		 month = new String();
		 year= Integer.toString(now.get(Calendar.YEAR));
		 month=Integer.toString(now.get(Calendar.MONTH));
		
	}
	

	/* Get year list from daily_report table. */
	public void getActiveYear() {
		years = new ArrayList<String>();
		years = dailyReportService.findActiveYears();
	}

	/* Show Data according to search result */
	public void show() {

		showBtn = true;
		if (!isValidSearch()) {
			return;
		}

		int tmpMonths =  Integer.parseInt(month) + 1;
		String tmpMonth = null;
		
		if(tmpMonths <= 9){
			tmpMonth = "0"+tmpMonths;
		}else
			tmpMonth = String.valueOf(tmpMonths);
		
		String searchDate = year.concat("-").concat(tmpMonth).concat("-").concat("01");
//		System.out.println(searchDate);
		
		
		//Get working hours per year
		workingHourPerDay = workingHourService.findByDate(searchDate);
		
//		System.out.println(workingHourPerDay);
		
		if(workingHourPerDay == 0.0){
			String errorMessage = "There is no working hour define for search period.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							errorMessage));
			return;
		}
	
		headerList = prepareDateHeaderList(Integer.parseInt(year),
				Integer.parseInt(getMonth()));

		// Public holiday header column color
		for (int s = 0; s < headerList.size(); s++) {
			loop5: for (int p = 0; p < publicHolidaysList.size(); p++) {
				if (publicHolidaysList.get(p).equals(
						headerList.get(s).getDayNames())) {
					headerList.get(s).setHolidays_flag(true);
					break loop5;
				} else
					headerList.get(s).setHolidays_flag(false);
			}
		}
		regReportResult();

	}

	public void regReportResult() {
		setUser(loginUser);

		regUserList = dailyReportService
				.findDailyReportRegistrationStatusByMonthYear(
						Integer.parseInt(year),
						Integer.parseInt(getMonth()) + 1, user);
			
		if(regUserList.size() > 0){
			allUserList = userService.findUserInfoListByUserId(
					Integer.parseInt(year), Integer.parseInt(getMonth()) + 1, user);
			allTeamList = userService.findTeamInfoList(user);
			UserTeamList = new ArrayList<>();
			
				a: for (int j = 0; j < allUserList.size(); j++) {
					UserTeamInfo ut = new UserTeamInfo();
					for (int i = 0; i < allTeamList.size(); i++) {
					if(allTeamList.get(i)[0].equals(allUserList.get(j)[2])){
						
						ut.setUsername(allUserList.get(j)[0].toString());
						ut.setFullname(allUserList.get(j)[1].toString());
						ut.setTeanname(allTeamList.get(i)[2].toString());
						UserTeamList.add(ut);
						continue a;
					}
					
				}
					
					ut.setUsername(allUserList.get(j)[0].toString());
					ut.setFullname(allUserList.get(j)[1].toString());
					ut.setTeanname("-");
					UserTeamList.add(ut);
			}
			RegistrationStatusData regData;
			List<String> dateList;

			for (int i = 0; i < UserTeamList.size(); i++) {

				regData = new RegistrationStatusData();

				regData.setUsername(UserTeamList.get(i).getUsername());
				regData.setFullname(UserTeamList.get(i).getFullname());
				regData.setTeam(UserTeamList.get(i).getTeanname());
				regData.setNotCompleteShow(false);
				dateList = prepareRegList(headerList.size());

				loop1: for (int k = 0; k < regUserList.size(); k++) {

					if (!regUserList.get(k)[0].equals(UserTeamList.get(i).getUsername()))
						continue loop1;

					if (regUserList.get(k)[0].equals(UserTeamList.get(i).getUsername())) {

						loop2: for (int j = 0; j < headerList.size(); j++) {

							java.sql.Date dat = java.sql.Date.valueOf(regUserList
									.get(k)[4].toString());
							Calendar cal = Calendar.getInstance();
							cal.setTime(dat);

							if (cal.get(Calendar.DAY_OF_MONTH) != headerList.get(j)
									.getDayNames())
								continue loop2;

							if (cal.get(Calendar.DAY_OF_MONTH) == headerList.get(j)
									.getDayNames()) {
								
								
								int leave_status = (Integer.parseInt(regUserList.get(k)[5].toString()));
								if (leave_status == 0 ) {
								if (Double.parseDouble(regUserList.get(k)[3]
										.toString()) >= workingHourPerDay ) {
									dateList.set(j, " ");
								}
							}else if (leave_status == 1) {
									dateList.set(j, " ");
							}else if (leave_status == 2) {
								if (Double.parseDouble(regUserList.get(k)[3]
										.toString()) >= (workingHourPerDay - 3.5) ) {
									dateList.set(j, " ");
								}
							}

								loop3: for (int m = 0; m < publicHolidaysList
										.size(); m++) {
									if (publicHolidaysList.get(m).equals(
											headerList.get(j).getDayNames())) {
										dateList.set(j, "H");
										break loop3;
									}
								}

							}

						}

					}
				}

				regData.setDateList(dateList);

				if (isShowSelect() == true) {
					loop4: for (String tmp : dateList) {
						if (tmp == "*") {
							regData.setNotCompleteShow(true);
							break loop4;
						}
					}
					if (regData.isNotCompleteShow() == true) {
						resultList.add(regData);
					}
				} else {
					resultList.add(regData);
				}

			}
			
		}else{
			String errorMessage = "There is no record for search period";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "",
							errorMessage));
			return;
		}
		
	}

	/* Check for valid search */
	private boolean isValidSearch() {

		if (this.year == null || this.year.equals("")) {
			String errorMessage = "Please select Year.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							errorMessage));
			return false;
		}
		if (this.month == null || this.month.equals("")) {
			String errorMessage = "Please select Month.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							errorMessage));
			return false;
		}

		return true;
	}

	/* Prepare registration report list. */
	public List<String> prepareRegList(int size) {
		List<String> dateList = new ArrayList<String>(size);

		for (int i = 0; i < size; i++) {
			dateList.add(i, "*");
		}

		for (int i = 0; i < headerList.size(); i++) {
			loop2: for (int m = 0; m < publicHolidaysList.size(); m++) {
				if (publicHolidaysList.get(m) == headerList.get(i)
						.getDayNames()) {
					dateList.set(i, "/");
					break loop2;
				}
			}
		}

		return dateList;
	}

	/* Prepare month list. */
	public void prepareMonths() {

		months.put(0, "January");
		months.put(1, "February");
		months.put(2, "March");
		months.put(3, "April");
		months.put(4, "May");
		months.put(5, "June");
		months.put(6, "July");
		months.put(7, "August");
		months.put(8, "September");
		months.put(9, "October");
		months.put(10, "November");
		months.put(11, "December");

	}

	/* Get public holiday list. */
	public List<Integer> getPublicHolidays(int yyyy, int mm) {
		List<Holidays> holidaysList = new ArrayList<Holidays>();

		holidaysList = holidaysService.findByYearMonth(yyyy, mm);

		List<Integer> pHDays = new ArrayList<Integer>();

		for (Holidays hdayTemp : holidaysList) {

			Date date = hdayTemp.getDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			pHDays.add(cal.get(Calendar.DAY_OF_MONTH));

		}

		return pHDays;
	}

	/* Get public date header list. */

	/* Prepare header list. */
	public List<DateHeader> prepareDateHeaderList(int yyyy, int mm) {
		int dayNums = 0;

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yyyy);
		cal.set(Calendar.MONTH, mm);
		cal.set(yyyy, mm, 1);

		dayNums = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);

		publicHolidaysList = getPublicHolidays(Integer.parseInt(year),
				Integer.parseInt(getMonth()) + 1);

		List<DateHeader> temp = new ArrayList<DateHeader>();
		// List<Integer> weekendTemp = new ArrayList<Integer>();

		for (int i = 1; i <= dayNums; i++) {
			if (weekDay > 7)
				weekDay = 1;

			DateHeader temp2 = new DateHeader();
			switch (weekDay) {
			case 1:
				temp2.setDayNames(i);
				temp2.setWeekDays("Sun");
				publicHolidaysList.add(i);
				// weekendTemp.add(i);
				weekDay++;
				break;
			case 2:
				temp2.setDayNames(i);
				temp2.setWeekDays("Mon");
				temp2.setHolidays_flag(false);
				weekDay++;
				break;
			case 3:
				temp2.setDayNames(i);
				temp2.setWeekDays("Tue");
				temp2.setHolidays_flag(false);
				weekDay++;
				break;
			case 4:
				temp2.setDayNames(i);
				temp2.setWeekDays("Wed");
				temp2.setHolidays_flag(false);
				weekDay++;
				break;
			case 5:
				temp2.setDayNames(i);
				temp2.setWeekDays("Thu");
				temp2.setHolidays_flag(false);
				weekDay++;
				break;
			case 6:
				temp2.setDayNames(i);
				temp2.setWeekDays("Fri");
				temp2.setHolidays_flag(false);
				weekDay++;
				break;
			case 7:
				temp2.setDayNames(i);
				temp2.setWeekDays("Sat");
				publicHolidaysList.add(i);
				// weekendTemp.add(i);
				weekDay++;
				break;
			}
			temp.add(temp2);
		}

		// for (Integer s : weekendTemp) {
		// if (!publicHolidaysList.contains(s))
		// publicHolidaysList.add(s);
		// }

		return temp;
	}

	public void prepareSearchData() {
		dailyReport = new DailyReport();
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

	public List<DailyActivity> getDailyActivityList() {
		return dailyActivityList;
	}

	public void setDailyActivityList(List<DailyActivity> dailyActivityList) {
		this.dailyActivityList = dailyActivityList;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public TreeMap<Integer, String> getMonths() {
		return months;
	}

	public void setMonths(TreeMap<Integer, String> months) {
		this.months = months;
	}

	public List<DateHeader> getHeaderList() {
		return headerList;
	}

	public void setHeaderList(List<DateHeader> headerList) {
		this.headerList = headerList;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public List<RegistrationStatusData> getResultList() {
		return resultList;
	}

	public void setResultList(List<RegistrationStatusData> resultList) {
		this.resultList = resultList;
	}

	public List<Object[]> getRegUserList() {
		return regUserList;
	}

	public void setRegUserList(List<Object[]> regUserList) {
		this.regUserList = regUserList;
	}

	public List<Object[]> getAllUserList() {
		return allUserList;
	}

	public void setAllUserList(List<Object[]> allUserList) {
		this.allUserList = allUserList;
	}

	public List<Integer> getPublicHolidaysList() {
		return publicHolidaysList;
	}

	public void setPublicHolidaysList(List<Integer> publicHolidaysList) {
		this.publicHolidaysList = publicHolidaysList;
	}

	public IHolidaysService getHolidaysService() {
		return holidaysService;
	}

	public void setHolidaysService(IHolidaysService holidaysService) {
		this.holidaysService = holidaysService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isShowSelect() {
		return showSelect;
	}

	public void setShowSelect(boolean showSelect) {
		this.showSelect = showSelect;
	}

	public double getWorkingHourPerDay() {
		return workingHourPerDay;
	}

	public void setWorkingHourPerDay(double workingHourPerDay) {
		this.workingHourPerDay = workingHourPerDay;
	}

	public IWorkingHourService getWorkingHourService() {
		return workingHourService;
	}

	public void setWorkingHourService(IWorkingHourService workingHourService) {
		this.workingHourService = workingHourService;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public String getCurrentmonth() {
		return currentmonth;
	}

	public void setCurrentmonth(String currentmonth) {
		this.currentmonth = currentmonth;
	}

	public boolean isShowBtn() {
		return showBtn;
	}

	public void setShowBtn(boolean showBtn) {
		this.showBtn = showBtn;
	}
	

}
