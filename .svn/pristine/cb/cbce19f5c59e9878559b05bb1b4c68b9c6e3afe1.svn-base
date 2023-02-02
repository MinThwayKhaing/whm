/***************************************************************************************
 * @author Soe Thiha Lwin
 * @Date 2018-09-24
 * @Version 1.0
 * @Purpose Interface of WorkingHour Service.
 *
 ***************************************************************************************/
package com.dat.whm.workinghour.service.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.workinghour.entity.WorkingHour;


public interface IWorkingHourService {
	
	public WorkingHour addNewWorkingHour(WorkingHour workinghour) throws SystemException;
	public List<WorkingHour> findAllWorkingHours();
	public List<WorkingHour> findByStartDate(Date startdate);
	public List<WorkingHour> findByEndDate(Date enddate);
	public List<WorkingHour> findByStartDateUpdate(Date startDate, int id);

	public List<WorkingHour> findByEndDateUpdate(Date endDate, int id);	
	public WorkingHour updateWorkingHour(WorkingHour workinghour);
	public void deleteWorkingHour(WorkingHour workinghour);

	public double findByDate(String searchDate) throws SystemException ;
	public List<WorkingHour> findDurationPeriod(Date startDate, Date endDate);

	public List<WorkingHour> findDurationPeriodUpdate(Date startDate,
			Date endDate, int id);
}
