package com.dat.whm.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
	public static Date resetStartDate(Date startDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date resetEndDate(Date endDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	private static SimpleDateFormat format1 = new SimpleDateFormat("MMM-yyyy");
	private static SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
	
	public static String getAvgDate(Date date) {
		return format1.format(date);
	}

	public static String getDateString(Date date) {
		return format2.format(date);
	}
}
