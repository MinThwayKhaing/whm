/***************************************************************************************
 * @author Soe Thiha Lwin
 * @Date 2018-09-24
 * @Version 1.0
 * @Purpose Interface of WorkingHour DAO.
 *
 ***************************************************************************************/
package com.dat.whm.workinghour.persistence.interfaces;

import java.util.Date;
import java.util.List;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.workinghour.entity.WorkingHour;


public interface IWorkingHourDAO {
	
	public WorkingHour insert(WorkingHour workinghour);

	public List<WorkingHour> findAll();
	
	public List<WorkingHour> findByStartDate(Date startDate);
	
	public List<WorkingHour> findByEndDate(Date endDate);
	public List<WorkingHour> findByStartDateUpdate(Date startDate, int id);
	
	public List<WorkingHour> findByEndDateUpdate(Date endDate, int id);

	public WorkingHour update(WorkingHour workinghour);

	public void delete(WorkingHour workinghour);
	
	public double findByDate(String date) throws DAOException ;
	
	public List<WorkingHour> findDurationPeriod(Date startDate,Date endDate);
	
	public List<WorkingHour> findDurationPeriodUpdate(Date startDate,Date endDate,int id);

}
