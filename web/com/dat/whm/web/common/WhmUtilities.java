/***************************************************************************************
 * @author Kyi Saw Win
 * @Date 2014-09-25
 * @Version 1.0
 * @Purpose WHM Utilities.
 *
 ***************************************************************************************/
package com.dat.whm.web.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WhmUtilities {
	
	public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate, int holiday) {
		
	    Calendar startCal = Calendar.getInstance();
	    startCal.setTime(startDate);        
	
	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(endDate);
	
	    int workDays = 0;
	
	    //Return 0 if start and end are the same
	    if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
	        return 0;
	    }
	
	    if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
	        startCal.setTime(endDate);
	        endCal.setTime(startDate);
	    }
	
	    /**
	     * Revised By   : Aung Htay Oo
		 * Revised Date : 2017/11/17
		 * Explanation  : Modified for getting actual total working day per month.
	     */
	    while(startCal.getTimeInMillis() <= endCal.getTimeInMillis()){
	    	
	    	if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	            workDays++;
	        }
	    	//excluding start date
	    	startCal.add(Calendar.DAY_OF_MONTH, 1);
	    }
	    
	    if(holiday > 0){
	    	workDays = workDays - holiday;
	    }
	
	    return workDays;
	}
	
	/* Will return yyyy as String. */
	public static String getYearFromDate(Date date) {
		String year = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		year = String.valueOf(cal.get(Calendar.YEAR)) ;
		return year;
	}
	
	/* Will return month as String. */
	public static String getMonthFromDate(Date date) {
		String month = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		month = String.valueOf(cal.get(Calendar.MONTH)+1) ;
		return month;
	}
	
	/* 
	 * Will return month as String according to format.
	 * MM => 01 ~ 12
	 * MMM => Jan ~ Dec
	 * MMMM => January ~ December */
	public static String getMonthFromDate(Date date, String format) {
		String month = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		month = String.valueOf(new SimpleDateFormat(format).format(cal.getTime())) ;
		return month;
	}
	
	/* Formatting date for each month. */
	public static String getMonthShortName(int monthNumber) {
		String monthName = "";

		if (monthNumber >= 0 && monthNumber < 12)
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.MONTH, monthNumber);

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
				simpleDateFormat.setCalendar(calendar);
				monthName = simpleDateFormat.format(calendar.getTime());
			} catch (Exception e) {
				if (e != null)
					e.printStackTrace();
			}
		return monthName;
	}
	
	/* Format the double value */
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public static ArrayList<String> getAllMonths(){
		ArrayList<String> months = new ArrayList<String>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		return months;
	}
	
	public static ArrayList<String> convertDigitToMonth(List<Integer> digitMonth){
		ArrayList<String> months = new ArrayList<String>();
		for (int i = 0; i <= digitMonth.size() - 1; i++) {
			switch (digitMonth.get(i).toString()) {
			case "1":
				months.add("January");
				break;
			case "2":
				months.add("February");
				break;
			case "3":
				months.add("March");
				break;
			case "4":
				months.add("April");
				break;
			case "5":
				months.add("May");
				break;
			case "6":
				months.add("June");
				break;
			case "7":
				months.add("July");
				break;
			case "8":
				months.add("August");
				break;
			case "9":
				months.add("September");
				break;
			case "10":
				months.add("October");
				break;
			case "11":
				months.add("November");
				break;
			case "12":
				months.add("December");
				break;
			}
		}
		return months;
	}
	
	public static ArrayList<String> convertDigitToMonthByString(List<String> digitMonth){
		ArrayList<String> months = new ArrayList<String>();
		for (int i = 0; i <= digitMonth.size() - 1; i++) {
			switch (digitMonth.get(i).toString()) {
			case "01":
				months.add("January");
				break;
			case "02":
				months.add("February");
				break;
			case "03":
				months.add("March");
				break;
			case "04":
				months.add("April");
				break;
			case "05":
				months.add("May");
				break;
			case "06":
				months.add("June");
				break;
			case "07":
				months.add("July");
				break;
			case "08":
				months.add("August");
				break;
			case "09":
				months.add("September");
				break;
			case "10":
				months.add("October");
				break;
			case "11":
				months.add("November");
				break;
			case "12":
				months.add("December");
				break;
			}
		}
		return months;
	}
	
	/* Finding digit for each month. */
	public static String findMonthDigit(String selectedMonth) {
		String month = "";
		if (!selectedMonth.equals("")) {
			switch (selectedMonth) {
			case "January":
			case "Jan":
				month = "01";
				break;
			case "February":
			case "Feb":
				month = "02";
				break;
			case "March":
			case "Mar":
				month = "03";
				break;
			case "April":
			case "Apr":
				month = "04";
				break;
			case "May":
				month = "05";
				break;
			case "June":
			case "Jun":
				month = "06";
				break;
			case "July":
			case "Jul":
				month = "07";
				break;
			case "August":
			case "Aug":
				month = "08";
				break;
			case "September":
			case "Sep":
				month = "09";
				break;
			case "October":
			case "Oct":
				month = "10";
				break;
			case "November":
			case "Nov":
				month = "11";
				break;
			case "December":
			case "Dec":
				month = "12";
				break;
			}
		}
		return month;
	}
	
	
	public static String findMonthByDigit(String monthDigit){
		String monthName = "";
		switch (monthDigit) {
		case "1":
		case "01":
			monthName = "January";
			break;
		case "2":
		case "02":
			monthName ="February";
			break;
		case "3":
		case "03":
			monthName="March";
			break;
		case "4":
		case "04":
			monthName="April";
			break;
		case "5":
		case "05":
			monthName="May";
			break;
		case "6":
		case "06":
			monthName="June";
			break;
		case "7":
		case "07":
			monthName="July";
			break;
		case "8":
		case "08":
			monthName="August";
			break;
		case "9":
		case "09":
			monthName="September";
			break;
		case "10":
			monthName="October";
			break;
		case "11":
			monthName="November";
			break;
		case "12":
			monthName="December";
			break;
		}
		return monthName;
	}
	
	public static String getLastDayOfMonth(String year, String month){
		Calendar calendar = Calendar.getInstance();

	    calendar.set(Calendar.DATE, 1);
	    calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
	    calendar.set(Calendar.YEAR, Integer.parseInt(year));
	    Integer lastDay= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    
	    return lastDay.toString();
	}
	
	/**
	 * Revised By	: Aye Myat Mon
	 * Revised Date	: 2018/08/20 
	 * Explanation	: Added datePattern and customDateFormat for date filter.
	 */
	public static String datePattern (){			
		return "yyyy-MM-dd";	
			
	}		

	public static String customDateFormat (Date date) {		
		if (date != null){	
			DateFormat format = new SimpleDateFormat(datePattern());
			return format.format(date);
		}else{	
			return "";
		}	
			
	}		
}
