/***************************************************************************************
 * @Date 2017-03-08
 * @author eimon
 * @Purpose <<For static variables and Flag to support DailyActivityManageBean class>>
 *
 *
 ***************************************************************************************/

package com.dat.whm.web.dailyactivity;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyreport.entity.DailyReport;

public class DailyActivityMBUtil {

	private static boolean editActivityFlag;
	private static DailyActivity tempDailyActivity;
	private static boolean editReportFlag;
	private static DailyReport tempDailyReport;

	private static final int leaveTaskId = 30; // Task = Leave
	private static final int otherTaskActivityId = 10; // Task Activity = Other

	public static boolean isEditActivityFlag() {
		return editActivityFlag;
	}

	public static void setEditActivityFlag(boolean editFlag) {
		DailyActivityMBUtil.editActivityFlag = editFlag;
	}

	public static DailyActivity getTempDailyActivity() {
		return tempDailyActivity;
	}

	public static void setTempDailyActivity(DailyActivity tempDailyActivity) {
		DailyActivityMBUtil.tempDailyActivity = tempDailyActivity;
	}

	public static boolean isEditReportFlag() {
		return editReportFlag;
	}

	public static void setEditReportFlag(boolean editReportFlag) {
		DailyActivityMBUtil.editReportFlag = editReportFlag;
	}

	public static DailyReport getTempDailyReport() {
		return tempDailyReport;
	}

	public static void setTempDailyReport(DailyReport tempDailyReport) {
		DailyActivityMBUtil.tempDailyReport = tempDailyReport;
	}

	public static int getLeavetaskid() {
		return leaveTaskId;
	}

	public static int getOthertaskactivityid() {
		return otherTaskActivityId;
	}
}