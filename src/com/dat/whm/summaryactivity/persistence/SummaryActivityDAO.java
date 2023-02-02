/***************************************************************************************
 * @author Htet Wai Yum
 * @Date 2018-09-17
 * @Version 1.0
 * @Purpose Added summary activity DAO for summary activities table
 *
 *
 ***************************************************************************************/
package com.dat.whm.summaryactivity.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.eclipse.persistence.sessions.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.summaryactivity.entity.SummaryActivity;
import com.dat.whm.summaryactivity.persistence.interfaces.ISummaryActivityDAO;
import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.workinghourmanagement.team.HolidayCount;
import com.dat.whm.web.workinghrmanagementproject.ManMonthInfo;
import com.dat.whm.web.workinghrmanagementproject.ProjectSummaryInfo;
import com.dat.whm.web.workinghrmanagementproject.StaffInfo;
import com.dat.whm.web.workinghrmanagementproject.TeamInfo;
import com.dat.whm.web.workinghrmanagementproject.TotalManMonthInfo;

@Repository("SummaryActivityDAO")
public class SummaryActivityDAO extends BasicDAO implements ISummaryActivityDAO {
	private Logger logger = Logger.getLogger(this.getClass());


	/**
    * Revised By : Htet Wai Yum 
    * Revised Date : 2018/09/17
    * Explanation: insert daily report information into summary activity
    */  
	@Transactional(propagation = Propagation.REQUIRED)
	public SummaryActivity insertSummaryActivity(SummaryActivity summaryActivity) {
		try {
			logger.debug("insert() method has been started.");
			em.persist(summaryActivity);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Summary Data(Staff_ID = "
					+ summaryActivity.getStaff_id() + ")", pe);
		}
		return summaryActivity;
	}


	/**
    * Revised By : Htet Wai Yum 
    * Revised Date : 2018/09/17
    * Explanation: update daily report information into summary activity
    */ 
		@Transactional(propagation = Propagation.REQUIRED)
	public SummaryActivity updateSummaryActivity(SummaryActivity summaryActivity)
	{
	try {
		logger.debug("updateSummaryActivity() method has been started.");
		if(summaryActivity.getProject_id() == null){
			Query q = em.createNamedQuery("SummaryActivity.updateByprojectidnull");
			q.setParameter("activity_hours", summaryActivity.getActivityHours());
			q.setParameter("staff_id", summaryActivity.getStaff_id());
			q.setParameter("team_id", summaryActivity.getTeam_id());
			q.setParameter("year", summaryActivity.getYear());
			q.setParameter("month", summaryActivity.getMonth());
			q.setParameter("activity_date", summaryActivity.getActivity_date());
			q.executeUpdate();	
		}else{
		Query q = em.createNamedQuery("SummaryActivity.updateBystaffid");
		q.setParameter("activity_hours", summaryActivity.getActivityHours());
		q.setParameter("staff_id", summaryActivity.getStaff_id());
		q.setParameter("team_id", summaryActivity.getTeam_id());
		q.setParameter("project_id", summaryActivity.getProject_id());
		q.setParameter("year", summaryActivity.getYear());
		q.setParameter("month", summaryActivity.getMonth());
		q.setParameter("activity_date", summaryActivity.getActivity_date());
		
		q.executeUpdate();
		}
		em.flush();
		logger.debug("updateSummaryActivity() method has been successfully finisehd.");
	} catch (PersistenceException pe) {
		logger.error("updateSummaryActivity() method has been failed.", pe);
		throw translate("Failed to update Summary Data(Staff_ID = "
				+ summaryActivity.getStaff_id() + ")", pe);
	}
	return summaryActivity;
	}
		
		/**
		 * Revised By : Htet Wai Yum 
		 * Revised Date : 2018/09/10 
		 * Explanation: Update activity date into summary activities table for daily registration page
		 */
		@Transactional(propagation = Propagation.REQUIRED)
		public SummaryActivity updateActivityDate(SummaryActivity summaryActivity)
		{
		try {
			logger.debug("updateActivityDate() method has been started.");
			if(summaryActivity.getProject_id() == null){
				Query q = em.createNamedQuery("SummaryActivity.updateByActivityDateNull");
				
				q.setParameter("staff_id", summaryActivity.getStaff_id());
				q.setParameter("team_id", summaryActivity.getTeam_id());
				q.setParameter("year", summaryActivity.getYear());
				q.setParameter("month", summaryActivity.getMonth());
				q.setParameter("activity_date", summaryActivity.getActivity_date());
				
				q.executeUpdate();	
			}else{
			Query q = em.createNamedQuery("SummaryActivity.updateByActivityDate");
		
			q.setParameter("staff_id", summaryActivity.getStaff_id());
			q.setParameter("team_id", summaryActivity.getTeam_id());
			q.setParameter("project_id", summaryActivity.getProject_id());
			q.setParameter("year", summaryActivity.getYear());
			q.setParameter("month", summaryActivity.getMonth());
			q.setParameter("activity_date", summaryActivity.getActivity_date());
			
			q.executeUpdate();
			}
			em.flush();
			logger.debug("updateActivityDate() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("updateActivityDate() method has been failed.", pe);
			throw translate("Failed to update Summary Data(Staff_ID = "
					+ summaryActivity.getStaff_id() + ")", pe);
		}
		return summaryActivity;
		}
		
		/**
		    * Revised By : Htet Wai Yum 
		    * Revised Date : 2018/09/17
		    * Explanation: delete daily report information into summary activity
		    */ 
			@Transactional(propagation = Propagation.REQUIRED)
			public SummaryActivity deleteSummaryActivity(SummaryActivity summaryActivity)
			{
			try {
				logger.debug("deleteSummaryActivity() method has been started.");
				
				Query q = em.createNamedQuery("SummaryActivity.deleteBystaffid");
			
				q.setParameter("staff_id", summaryActivity.getStaff_id());
				q.setParameter("team_id", summaryActivity.getTeam_id());
				q.setParameter("year", summaryActivity.getYear());
				q.setParameter("month", summaryActivity.getMonth());
				
				q.executeUpdate();
				
				em.flush();
				logger.debug("deleteSummaryActivity() method has been successfully finisehd.");
			} catch (PersistenceException pe) {
				logger.error("deleteSummaryActivity() method has been failed.", pe);
				throw translate("Failed to delete Summary Data(Staff_ID = "
						+ summaryActivity.getStaff_id() + ")", pe);
			}
			return summaryActivity;
			}

			/**
		    * Revised By : Htet Wai Yum 
		    * Revised Date : 2018/09/17
		    * Explanation: find daily report information into summary activity
		    */ 
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SummaryActivity> findAll() 
	{
		List<SummaryActivity> result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("SummaryActivity.findAll");
			// q.setParameter("staff_id", staffID);
			result = q.getResultList();
			em.flush();
			logger.debug("findAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public 	double findByProjectId(SummaryActivity sa) 
	{
		double result = 0.0;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("SummaryActivity.findByprojectid");
			q.setParameter("staff_id", sa.getStaff_id());
			q.setParameter("year", sa.getYear());
			q.setParameter("month", sa.getMonth());
			q.setParameter("project_id", sa.getProject_id());
			
			result = (double)q.getSingleResult();
			em.flush();
			logger.debug("findAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}

		/**
	    * Revised By : Htet Wai Yum 
	    * Revised Date : 2018/09/17
	    * Explanation: find by projectid/staffid/year/month for daily report information into summary activity
	    */ 
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SummaryActivity> findByProjectidStaffidYearMonth(SummaryActivity sa)
	{
		List<SummaryActivity> result = null;
		try {
			logger.debug("findByProjectid() method has been started.");
			Query q = em.createNamedQuery("SummaryActivity.findBystaffid");
			// Query q = em.createNamedQuery("DailyReport.findByMonthYear");
						//Query q = em.createNativeQuery(
								//"SELECT * FROM summary_activities sa WHERE sa.staff_id= ?1 AND sa.year=?2 AND sa.month=?3");
			 q.setParameter("staff_id", sa.getStaff_id());
			 q.setParameter("team_id", sa.getTeam_id());
			 q.setParameter("year", sa.getYear());
			 q.setParameter("month", sa.getMonth());
			
			 
			result = q.getResultList();
			em.flush();
			logger.debug("findByProjectid() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByProjectid() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByProjectID(String projectID) throws DAOException {

		List<Object[]> result = null;
		try {
			logger.debug("findByProjectID() method has been started.");

			Query q = em
					.createNativeQuery("SELECT sa.project_id,t.team_name,SUM(sa.activity_hours) AS hours, "
							+ "SUM(sa.activity_hours)/(20*w.working_hours) AS manmonth,p.client_organization as customer,p.total_man_month, "
							+ "COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month,sa.activity_date "
							+ "FROM summary_activities sa, team t,users u,project p, "
							+ "(SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2 "
							+ "WHERE sa2.activity_date >= wi.start_year AND sa2.activity_date <= wi.end_year AND sa2.project_id=?2) AS w "
							+ "WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id "
							+ "AND sa.project_id=?1 AND w.activity_date = sa.activity_date "
							+ "GROUP BY t.team_name,YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY sa.activity_date ");

			q.setParameter(1, projectID);
			q.setParameter(2, projectID);
			System.out.println(q.toString());
			result = q.getResultList();
			em.flush();
			
			
			logger.debug("findByProjectID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByProjectID() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SummaryActivity> findByTeam(Map<String, Object> searchData)throws DAOException {
		List<SummaryActivity> result = null;
		Team team = (Team) searchData.get("team");
		String startYear = (String) searchData.get("startYear");
		String endYear = (String) searchData.get("endYear");
		String startMonth = (String) searchData.get("startMonth");
		String endMonth = (String) searchData.get("endMonth");
		try {
			logger.debug("findByTeam() method has been started.");
			Query q = em.createNamedQuery("SummaryActivity.findByTeam");
			q.setParameter("team_id", team.getId());
			q.setParameter("startYearMonth",  startYear + '-' + startMonth + '-' + "01");
			q.setParameter("endYearMonth",  endYear + '-' + endMonth + '-' + "01");
			result = q.getResultList();
			em.flush();
			logger.debug("findByTeam() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByTeam() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/09/24
	 * Explanation  : Get distinct of year by TeamID.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findYearsByTeam(Team team) {

		List<String> years = null;
		try{
			logger.debug("findYearsByTeam() method has been started.");
			
			String queryString = "SELECT DISTINCT YEAR FROM summary_activities s WHERE s.team_id = ? ORDER BY s.year" ;
			Query q = em.createNativeQuery(queryString);
			q.setParameter(1, team.getId());
			years = q.getResultList();
			em.flush();
			logger.debug("findYearsByTeam() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findYearsByTeam() method has been failed.", pe);
			throw translate("Faield to search active years.", pe);
		}
		return years;
	
	} 

	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/09/24
	 * Explanation  : Get distinct of month by TeamID.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findMonthsByTeam(Team team) {

		List<String> months = null;
		try{
			logger.debug("findMonthsByTeam() method has been started.");
			
			String queryString = "SELECT DISTINCT MONTH FROM summary_activities s WHERE s.team_id = ? ORDER BY s.month" ;
			Query q = em.createNativeQuery(queryString);
			q.setParameter(1, team.getId());
			months = q.getResultList();
			em.flush();
			logger.debug("findMonthsByTeam() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findMonthsByTeam() method has been failed.", pe);
			throw translate("Faield to search active months.", pe);
		}
		return months;
	
	} 
	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/10/10
	 * Explanation  : Get distinct of year by StaffID.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findYearsByUser(User user) {

		List<String> years = null;
		try{
			logger.debug("findYearsByUser() method has been started.");
			
			String queryString = "SELECT DISTINCT YEAR FROM summary_activities s WHERE s.staff_id = ? ORDER BY s.year" ;
			Query q = em.createNativeQuery(queryString);
			q.setParameter(1, user.getId());
			years = q.getResultList();
			em.flush();
			logger.debug("findYearsByUser() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findYearsByUser() method has been failed.", pe);
			throw translate("Faield to search active years.", pe);
		}
		return years;
	
	} 
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByStaff(User user,String startDate,String endDate) throws DAOException {

		List<Object[]> resultList = null;
		try{
			logger.debug("findByStaff() method has been started.");
			
			String queryString = "SELECT sa.staff_id,sa.username,sa.fullname,sa.project_id,sa.project_name,sum(sa.activity_hours) AS hours,sa.year,sa.month,"
					+ " SUM(sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*w.working_hour))  AS manmonth,w.working_hour "
					+ " FROM (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
					+ " WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date) AS w,summary_activities sa "
					+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
					+ " WHERE sa.staff_id = ?1 AND sa.activity_date BETWEEN ?2 AND ?3"
					+ " AND w.activity_date = sa.activity_date GROUP BY sa.project_id, YEAR(sa.activity_date),MONTH(sa.activity_date) "
					+ " ORDER BY sa.project_id, YEAR(sa.activity_date),MONTH(sa.activity_date) ";
			
			Query q = em.createNativeQuery(queryString);
			q.setParameter(1, user.getId());
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			resultList = q.getResultList();
			em.flush();
			logger.debug("findByStaff() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findByStaff() method has been failed.", pe);
			throw translate("Faield to findByStaff", pe);
		}
		return resultList;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findProjectIDByStaff(User user,String startDate,String endDate) throws DAOException {

		List<String> projectIDList = null;
		try{
			logger.debug("findProjectIDByStaff() method has been started.");
			
			String queryString = " SELECT DISTINCT sa.project_id FROM summary_activities sa "
					+ " WHERE sa.staff_id = ?1 AND  sa.activity_date BETWEEN ?2 AND ?3" ;
			
			Query q = em.createNativeQuery(queryString);
			q.setParameter(1, user.getId());
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			projectIDList = q.getResultList();
			em.flush();
			logger.debug("findProjectIDByStaff() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findProjectIDByStaff() method has been failed.", pe);
			throw translate("Faield to findProjectIDByStaff", pe);
		}
		return projectIDList;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTotalByStaff(User user, String startDate,
			String endDate) throws DAOException {
		List<Object[]> resultList = null;
		try{
			logger.debug("findTotalByStaff() method has been started.");
			
			String queryString = "SELECT sa.staff_id,sa.username,sa.fullname,sum(sa.activity_hours) AS hours,"
					+ " SUM(sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*w.working_hour))  AS manmonth,w.working_hour,sa.year,sa.month "
					+ " FROM (SELECT DISTINCT wi.*,sa2.activity_date "
					+ " FROM working_hour_info wi,summary_activities sa2 "
					+ " WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date) AS w,"
					+ " summary_activities sa "
					+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
					+ " WHERE sa.staff_id = ?1 AND sa.activity_date BETWEEN ?2 AND ?3 "
					+ " AND w.activity_date = sa.activity_date GROUP BY YEAR(sa.activity_date),MONTH(sa.activity_date)"
					+ " ORDER BY YEAR(sa.activity_date),MONTH(sa.activity_date)";
			
			Query q = em.createNativeQuery(queryString);
			q.setParameter(1, user.getId());
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			resultList = q.getResultList();
			em.flush();
			logger.debug("findTotalByStaff() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findTotalByStaff() method has been failed.", pe);
			throw translate("Faield to findTotalByStaff", pe);
		}
		return resultList;
	} 

	
	/**
	 * Revised By   : Aye Thida Phyo
	 * Revised Date : 2018/10/10
	 * Explanation  : Get distinct of month by StaffID.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findMonthsByUser(User user) {

		List<String> months = null;
		try{
			logger.debug("findMonthsByUser() method has been started.");
			
			String queryString = "SELECT DISTINCT MONTH FROM summary_activities s WHERE s.staff_id = ?1 ORDER BY s.month" ;
			Query q = em.createNativeQuery(queryString);
			q.setParameter(1, user.getId());
			months = q.getResultList();
			em.flush();
			logger.debug("findMonthsByUser() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findMonthsByUser() method has been failed.", pe);
			throw translate("Faield to search active months.", pe);
		}
		return months;
	
	} 
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String findStartMonthByUser(User user) {

		String months = null;
		try{
			logger.debug("findMonthsByUser() method has been started.");
			
			String queryString = "SELECT MONTH(MIN(sa.activity_date)) as month FROM Summary_Activities sa where sa.staff_id = ?1" ;
			Query q = em.createNativeQuery(queryString);
			q.setParameter(1, user.getId());
			months = q.getSingleResult().toString();
			em.flush();
			logger.debug("findMonthsByUser() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findMonthsByUser() method has been failed.", pe);
			throw translate("Faield to search active months.", pe);
		}
		return months;
	
	} 
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String findEndMonthByUser(User user) {

		String months = null;
		try{
			logger.debug("findMonthsByUser() method has been started.");
			
			String queryString = "SELECT MONTH(MAX(sa.activity_date)) as month FROM Summary_Activities sa where sa.staff_id = ?1" ;
			Query q = em.createNativeQuery(queryString);
			q.setParameter(1, user.getId());
			months = q.getSingleResult().toString();
			em.flush();
			logger.debug("findMonthsByUser() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findMonthsByUser() method has been failed.", pe);
			throw translate("Faield to search active months.", pe);
		}
		return months;
	
	} 
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SummaryActivity> findByProjectidStaffidYearMonth(
			String staffid, String activity_date) {
		List<SummaryActivity> result = null;
		try {
			logger.debug("findByProjectid() method has been started.");
			Query q = em
					.createNativeQuery("SELECT * FROM summary_activities sa WHERE sa.staff_id= ?1 AND sa.activity_date like ?2");
			q.setParameter(1, staffid);
			q.setParameter(2, "%" + activity_date + "%");

			result = q.getResultList();
			em.flush();
			logger.debug("findByProjectid() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByProjectid() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDateByProjectID(SummaryActivity summaryActivity) {
		Date result = new Date();
		try {
			logger.debug("findMinYearMonthByProjectID() method has been started.");
			
			Query q = em
					.createNativeQuery("SELECT DISTINCT MIN(sa.activity_date) FROM summary_activities sa WHERE sa.project_id LIKE ?1");
			q.setParameter(1, "%" + summaryActivity.getProject_id() + "%");
			result =(Date) q.getSingleResult();
			
			em.flush();
			logger.debug("findMinYearMonthByProjectID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMinYearMonthByProjectID() method has been failed.", pe);
			throw translate("Failed to findMinYearMonthByProjectID of Report.", pe);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDateByProjectIDFull(SummaryActivity summaryActivity) {
		Date result = new Date();
		try {
			logger.debug("findMinDateByProjectIDPartial() method has been started.");
			
			Query q = em
					.createNativeQuery("SELECT DISTINCT MIN(sa.activity_date) FROM summary_activities sa WHERE sa.project_id = ?1");
			q.setParameter(1,  summaryActivity.getProject_id());
			result =(Date) q.getSingleResult();
			
			em.flush();
			logger.debug("findMinDateByProjectIDPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMinDateByProjectIDPartial() method has been failed.", pe);
			throw translate("Failed to findMinDateByProjectIDPartial of Report.", pe);
		}
		return result;
	}
	

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDateByProjectID(SummaryActivity summaryActivity) {

		Date result = new Date();
		try {
			logger.debug("findMaxYearMonthByProjectID() method has been started.");
			Query q = em
					.createNativeQuery("SELECT DISTINCT MAX(sa.activity_date) FROM summary_activities sa WHERE sa.project_id LIKE ?1");
			q.setParameter(1, "%" + summaryActivity.getProject_id() + "%");
			result =(Date) q.getSingleResult();
			
			em.flush();
			logger.debug("findMaxYearMonthByProjectID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMaxYearMonthByProjectID() method has been failed.", pe);
			throw translate("Failed to findMaxYearMonthByProjectID of Report.", pe);
		}
		return result;
	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDateByProjectIDFull(SummaryActivity summaryActivity) {

		Date result = new Date();
		try {
			logger.debug("findMaxYearMonthByProjectID() method has been started.");
			Query q = em
					.createNativeQuery("SELECT DISTINCT MAX(sa.activity_date) FROM summary_activities sa WHERE sa.project_id = ?1");
			q.setParameter(1, summaryActivity.getProject_id());
			result =(Date) q.getSingleResult();
			
			em.flush();
			logger.debug("findMaxYearMonthByProjectID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMaxYearMonthByProjectID() method has been failed.", pe);
			throw translate("Failed to findMaxYearMonthByProjectID of Report.", pe);
		}
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDate()
			throws DAOException {
		Date result = new Date();
		try {
			logger.debug("findMinDate() method has been started.");
			Query q = em.createNamedQuery("SummaryActivity.findMinDate");
			
			result =(Date) q.getSingleResult();
			em.flush();
			logger.debug("findMinDate() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMinDate() method has been failed.", pe);
			throw translate("Failed to findMinDate of Report.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDate()
			throws DAOException {

		Date result = new Date();
		try {
			logger.debug("findMaxDate() method has been started.");
			Query q = em.createNamedQuery("SummaryActivity.findMaxDate");
			
			result =  (Date) q.getSingleResult();
			em.flush();
			logger.debug("findMaxDate() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMaxDate() method has been failed.", pe);
			throw translate("Failed to findMaxDate of Report.", pe);
		}
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SummaryActivity> findTeamByProjectID(
			SummaryActivity summaryActivity) throws DAOException {
	List<SummaryActivity> result = null;
		try {
			logger.debug("findTeamByProjectID() method has been started.");
			Query q = em.createNamedQuery("SummaryActivity.findTeamByProjectID");
			q.setParameter("project_id", summaryActivity.getProject_id());
			
			result = q.getResultList();
				
			em.flush();
			logger.debug("findTeamByProjectID() method has been successfully finisehd.");
			
		} catch (PersistenceException pe) {
			logger.error("findTeamByProjectID() method has been failed.", pe);
			throw translate("Failed to findTeamByProjectID of Report.", pe);
		}
		return result;
	
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByProjectID(SummaryActivity sa,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		String customerQuery = "";
		
		Query q = null;
		
		if(sa.getClientorganization() != null && sa.getClientorganization() != " " && (!sa.getClientorganization().equals(null)) )
			customerQuery += " AND p.client_organization = ?3 ";
			
		try {
			logger.debug("findByProjectID() method has been started.");

			 q = em
					.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,p.total_man_month,t.team_name,t.team_id,SUM(sa.activity_hours) AS hours, "
							+ "SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth, "
							+ "COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month "
							+ "FROM summary_activities sa, team t,users u,project p, "
							+ "(SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2 "
							+ "WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id=?2) AS w "
							+ "WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id "
							+ "AND sa.project_id=?1 AND w.activity_date = sa.activity_date "  +customerQuery
							+ "AND sa.activity_date BETWEEN ?4 AND ?5 "
							+ "GROUP BY t.team_name,YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY sa.project_id,sa.activity_date ");

			q.setParameter(1, sa.getProject_id());
			q.setParameter(2, sa.getProject_id());
			
			if(sa.getClientorganization() != null && sa.getClientorganization() != " " && (!sa.getClientorganization().equals(null)) )
				q.setParameter(3, sa.getClientorganization());
			
			q.setParameter(4, startDate);
			q.setParameter(5, endDate);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findByProjectID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByProjectID() method has been failed.", pe);
			throw translate("Failed to find findByProjectID of Report.", pe);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findFullProjectActualManMonth(SummaryActivity sa) throws DAOException {
		double result = 0.0;
		String customerQuery = "";
		
		Query q = null;
		
		if(sa.getClientorganization() != null && sa.getClientorganization() != " " && (!sa.getClientorganization().equals(null)) )
			customerQuery += " AND p.client_organization = ?3 ";
		
		try {
			logger.debug("findFullProjectActualManMonth() method has been started.");
			
			 q = em.createNativeQuery("SELECT SUM(res.manmonth) FROM (SELECT sa.project_id,p.project_name,p.client_organization as customer,p.total_man_month,t.team_name,t.team_id,SUM(sa.activity_hours) AS hours, "
							+ "SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth, "
							+ "COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month "
							+ "FROM summary_activities sa, team t,users u,project p, "
							+ "(SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2 "
							+ "WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id=?2) AS w "
							+ "WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id "
							+ "AND sa.project_id=?1 AND w.activity_date = sa.activity_date "  +customerQuery
							+ "GROUP BY t.team_name,YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY sa.activity_date) AS res ");
			
			 q.setParameter(1, sa.getProject_id());
			 q.setParameter(2, sa.getProject_id());
				
			 if(sa.getClientorganization() != null && sa.getClientorganization() != " " && (!sa.getClientorganization().equals(null)) )
					q.setParameter(3, sa.getClientorganization());
			 
			List<Object> tmpList =  q.getResultList();
			
			if(tmpList.size() > 0)
				result = (double) tmpList.get(0);
			else
				result = 0.0;
			
			em.flush();
			logger.debug("findFullProjectActualManMonth() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findFullProjectActualManMonth() method has been failed.", pe);
			throw translate("Failed to findFullProjectActualManMonth of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByClientOrganization(String customerName,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		Query q = null;
		
		try {
			logger.debug("findByClientOrganization() method has been started.");

			q = em.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,p.total_man_month,"
					+ " SUM(sa.activity_hours) AS hours,COUNT(sa.staff_id) AS members,SUM(sa.activity_hours)/(20*w.working_hour)AS manmonth,"
					+ " sa.year,sa.month,w.working_hour FROM users u,"
					+ " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2 "
					+ " WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date) AS w,"
					+ " summary_activities sa left join  project p  on p.project_id = sa.project_id "
					+ " WHERE sa.staff_id = u.id AND sa.project_id=p.project_id "
					+ " AND w.activity_date = sa.activity_date AND p.client_organization = ?1 "
					+ " AND sa.activity_date BETWEEN ?2 AND ?3 "
					+ " GROUP BY sa.project_id,YEAR(sa.activity_date),MONTH(sa.activity_date)"
					+ " ORDER BY sa.project_id,sa.activity_date, YEAR(sa.activity_date),MONTH(sa.activity_date)");
			
			q.setParameter(1, customerName);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
		
			result = q.getResultList();
			em.flush();
			logger.debug("findByClientOrganization() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByClientOrganization() method has been failed.", pe);
			throw translate("Failed to find findByClientOrganization of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findProjectIDByProjectID(
		SummaryActivity sa,String startDate,String endDate) throws DAOException {
		List<String> result = null;
		
		String customerQuery = " ";
			
		if(sa.getClientorganization() != null && sa.getClientorganization() != " " && (!sa.getClientorganization().equals(null)) )
			customerQuery += " AND p.client_organization = ?3 ";
		
		
		try {
			logger.debug("findProjectIDByProjectID() method has been started.");
			Query q = em.createNativeQuery("SELECT DISTINCT sa.project_id FROM summary_activities sa,project p where sa.project_id = p.project_id "
					+ " AND sa.project_id LIKE ?1 " + customerQuery
					+ " AND sa.activity_date BETWEEN ?4 AND ?5 ORDER BY sa.project_id");
			
			q.setParameter(1, "%"+sa.getProject_id()+"%");
			q.setParameter(4, startDate);
			q.setParameter(5, endDate);
			
			if(sa.getClientorganization() != null && sa.getClientorganization() != " " && (!sa.getClientorganization().equals(null)) )
			q.setParameter(3, sa.getClientorganization());
			
			result = q.getResultList();
				
			em.flush();
			logger.debug("findProjectIDByProjectID() method has been successfully finisehd.");
			
		} catch (PersistenceException pe) {
			logger.error("findProjectIDByProjectID() method has been failed.", pe);
			throw translate("Failed to findProjectIDByProjectID of Report.", pe);
		}
		return result;
	
	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findProjectIDByNull(
		String project,String startDate,String endDate,String customer) throws DAOException {

		List<Object[]> result = null;
		List<ProjectSummaryInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailProjectByPartial() method has been started.");
			if (customer.equals(null) || customer.equals("")) {
				 q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi, summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id='9999' and activity_date "
										+ " between ?2 and ?3 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
					 	
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
			}else{
				 q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi, summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id='9999' and activity_date "
										+ " between ?2 and ?3 and client_organization=?4 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
					 	
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
						q.setParameter(4, customer);
			}
			
			
			result =q.getResultList();
			for (int i = 0; i < result.size(); i++) {
				ProjectSummaryInfo projectinfo = new ProjectSummaryInfo();
				projectinfo.setProjectID(result.get(i)[0].toString());
				projectinfo.setProjectName("-");
				projectinfo.setCustomer("-");	
				projectinfo.setTotalPlanMM(0.0);
				projectinfo.setTotalActualMM(Double.parseDouble(result.get(i)[4].toString()));
				projectinfo.setMembers(Integer.parseInt(result.get(i)[5].toString()));
				data.add(projectinfo);
			}
			
			em.flush();
			logger.debug("findDetailProjectByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailProjectByPartial() method has been failed.", pe);
			throw translate("Failed to find findDetailProjectByPartial of Report.", pe);
		}
		return data;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findProjectIDByFull(
		String project,String startDate,String endDate,String customer) throws DAOException {

		List<Object[]> result = null;
		List<ProjectSummaryInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findProjectIDByFull() method has been started.");
			
			if (customer.equals(null) || customer.equals("")) {
				System.out.println("without custemer");
				 q = em
						.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities  left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id =?1 and activity_date "
										+ " between ?2 and ?3 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 	q.setParameter(1, project);
				 	q.setParameter(2, startDate);
					q.setParameter(3, endDate);
				
			}else{
				System.out.println("with custemer");
				if(customer.equals("-")) {
				 q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id =?1 and activity_date "
										+ " between ?2 and ?3 and (client_organization is NULL or client_organization IN ('','-')) group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
					 	q.setParameter(1, project);
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
						
				}else{
					 q = em
								.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
											+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
											+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
											+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
											+ " project_id from summary_activities  where project_id =?1 and activity_date "
											+ " between ?2 and ?3 and client_organization=?4 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
						 	q.setParameter(1, project);
						 	q.setParameter(2, startDate);
							q.setParameter(3, endDate);
							q.setParameter(4, customer);
				}
			}
		
			
			
			result =q.getResultList();
			for (int i = 0; i < result.size(); i++) {
				ProjectSummaryInfo projectinfo = new ProjectSummaryInfo();
				projectinfo.setProjectID(result.get(i)[0].toString());
				projectinfo.setProjectName(result.get(i)[1].toString());
				if(result.get(i)[2] != null){
				projectinfo.setCustomer(result.get(i)[2].toString());
				}else{
				projectinfo.setCustomer("-");	
				}
				projectinfo.setTotalPlanMM(Double.parseDouble(result.get(i)[3].toString()));
				projectinfo.setTotalActualMM(Double.parseDouble(result.get(i)[4].toString()));
				projectinfo.setMembers(Integer.parseInt(result.get(i)[5].toString()));
				data.add(projectinfo);
			}
			
			em.flush();
			logger.debug("findProjectIDByFull() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findProjectIDByFull() method has been failed.", pe);
			throw translate("Failed to find findProjectIDByFull of Report.", pe);
		}
		return data;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findProjectIDByCustomer(String customer,String startDate,String endDate)
			throws DAOException {
		List<String> result = null;
		try {
			logger.debug("findProjectIDByProjectID() method has been started.");
			Query q = em.createNativeQuery("SELECT DISTINCT sa.project_id FROM summary_activities sa,project p WHERE sa.project_id=p.project_id AND p.client_organization = ?1 "
					+ " AND sa.activity_date BETWEEN ?2 AND ?3 ORDER BY sa.project_id");
			
			q.setParameter(1, customer);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			result = q.getResultList();
				
			em.flush();
			logger.debug("findProjectIDByProjectID() method has been successfully finisehd.");
			
		} catch (PersistenceException pe) {
			logger.error("findProjectIDByProjectID() method has been failed.", pe);
			throw translate("Failed to findProjectIDByProjectID of Report.", pe);
		}
		return result;
	
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Object[] findMinDateByCustomer(String customer) throws DAOException {

		Object[] result;
		try {
			logger.debug("findMinDateByCustomer() method has been started.");
			//Query q = em.createNativeQuery("SELECT MIN(sa.activity_date),sa.project_id FROM summary_activities sa,project p WHERE sa.project_id = p.project_id AND p.client_organization = ?1 group by sa.project_id limit 1");
			Query q =  em.createNativeQuery("SELECT MIN(sa.activity_date),sa.project_id FROM summary_activities sa  WHERE  sa.client_organization = ?1 order by sa.activity_date limit 1");
			q.setParameter(1, customer);
		
			result =  (Object[]) q.getSingleResult();
			em.flush();
			logger.debug("findMinDateByCustomer() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMinDateByCustomer() method has been failed.", pe);
			throw translate("Failed to findMinDateByCustomer", pe);
		}
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Object[] findMaxDateByCustomer(String customer) throws DAOException {

		Object[] result;
		try {
			logger.debug("findMaxDateByCustomer() method has been started.");
			//Query q = em.createNativeQuery("SELECT MAX(sa.activity_date),sa.project_id FROM summary_activities sa,project p WHERE sa.project_id = p.project_id AND p.client_organization = ?1 group by sa.project_id limit 1");
			Query q =  em.createNativeQuery("SELECT MAX(sa.activity_date),sa.project_id FROM summary_activities sa  WHERE  sa.client_organization = ?1 order by sa.activity_date limit 1");
			q.setParameter(1, customer);
				
			result = (Object[]) q.getSingleResult();
			em.flush();
			logger.debug("findMaxDateByCustomer() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMaxDateByCustomer() method has been failed.", pe);
			throw translate("Failed to findMaxDateByCustomer", pe);
		}
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByAllProject(String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		Query q = null;
		
		try {
			logger.debug("findByAllProject() method has been started.");
			
			
			q= em.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,p.total_man_month,"
					+ " SUM(sa.activity_hours) AS totalHour,COUNT(sa.staff_id) AS members, SUM(sa.activity_hours)/(20*w.working_hour)AS manmonth, "
					+ " sa.year,sa.month,w.working_hour FROM users u,"
					+ " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2 "
					+ " WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date) AS w,"
					+ " summary_activities sa LEFT JOIN  project p  on p.project_id = sa.project_id "
					+ " WHERE sa.staff_id = u.id AND w.activity_date = sa.activity_date "
					+ " AND sa.activity_date BETWEEN ?1 AND ?2"
					+ " GROUP BY sa.project_id,YEAR(sa.activity_date),MONTH(sa.activity_date)"
					+ " ORDER BY sa.project_id,sa.activity_date, YEAR(sa.activity_date),MONTH(sa.activity_date)");

			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
			result = q.getResultList();
			
			em.flush();
			logger.debug("findByAllProject() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByAllProject() method has been failed.", pe);
			throw translate("Failed to find findByAllProject of Report.", pe);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByNullProject(String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		Query q = null;
		
		try {
			logger.debug("findByNullProject() method has been started.");

		
			q = em.createNativeQuery("SELECT SUM(sa.activity_hours) AS hours,SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
					+ " COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month "
					+ " FROM summary_activities sa,"
					+ " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2 "
					+ " WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date) AS w "
					+ " WHERE w.activity_date = sa.activity_date AND sa.project_id IS NULL"
					+ " AND sa.activity_date BETWEEN ?1 AND ?2"
					+ " GROUP BY YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY YEAR(sa.activity_date),MONTH(sa.activity_date) ");

			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
			result = q.getResultList();
			
			em.flush();
			logger.debug("findByNullProject() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByNullProject() method has been failed.", pe);
			throw translate("Failed to find findByNullProject of Report.", pe);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findNullProjectActualManMonth() throws DAOException {
		double result = 0.0;

		try {
			logger.debug("findNullProjectActualManMonth() method has been started.");
			
			Query q = em.createNativeQuery("SELECT SUM(res.manmonth) FROM (SELECT SUM(sa.activity_hours) AS hours,SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
					+ " COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month "
					+ " FROM summary_activities sa,"
					+ " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2 "
					+ " WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date) AS w "
					+ " WHERE w.activity_date = sa.activity_date AND sa.project_id IS NULL"
					+ " GROUP BY YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY YEAR(sa.activity_date),MONTH(sa.activity_date)) AS res ");
			
			
			List<Object> tmpList =  q.getResultList();
			
			if(tmpList.size() > 0)
				result = (double) tmpList.get(0);
			else
				result = 0.0;
			
			em.flush();
			logger.debug("findNullProjectActualManMonth() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findNullProjectActualManMonth() method has been failed.", pe);
			throw translate("Failed to findNullProjectActualManMonth of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDateByNullPrjID() throws DAOException {

		Date result = new Date();
		try {
			logger.debug("findMinDateByNullPrjID() method has been started.");
			Query q = em.createNamedQuery("SummaryActivity.findMinDateByNullPrjID");
			
			result =  (Date) q.getSingleResult();
			em.flush();
			logger.debug("findMinDateByNullPrjID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMinDateByNullPrjID() method has been failed.", pe);
			throw translate("Failed to findMinDateByNullPrjID of Report.", pe);
		}
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDateByNullPrjID() throws DAOException {

		Date result = new Date();
		try {
			logger.debug("findMaxDateByNullPrjID() method has been started.");
			Query q = em.createNamedQuery("SummaryActivity.findMaxDateByNullPrjID");
			
			result =  (Date) q.getSingleResult();
			em.flush();
			logger.debug("findMaxDateByNullPrjID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMaxDateByNullPrjID() method has been failed.", pe);
			throw translate("Failed to findMaxDateByNullPrjID of Report.", pe);
		}
		return result;
	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findALLProjectID(String startDate,String endDate) throws DAOException {
		List<String> result = null;
		try {
			logger.debug("findALLProjectID() method has been started.");
			Query q = em.createNativeQuery("SELECT DISTINCT sa.project_id FROM summary_activities sa WHERE sa.activity_date BETWEEN ?1 AND ?2 ");
				
			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
			
			result = q.getResultList();
			
			em.flush();
			logger.debug("findALLProjectID() method has been successfully finisehd.");
			
		} catch (PersistenceException pe) {
			logger.error("findALLProjectID() method has been failed.", pe);
			throw translate("Failed to findALLProjectID of Report.", pe);
		}
		return result;
	
	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findByCustomer(String startDate,String endDate,String customer) throws DAOException {


		List<Object[]> result = null;
		List<ProjectSummaryInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findByCustomer() method has been started.");
			
		
				System.out.println("with custemer");
				if(customer.equals("-")){
				 q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where activity_date "
										+ " between ?2 and ?3 and (client_organization is NULL or client_organization IN ('','-')) group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 		
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
						
				}else{
					q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where activity_date "
										+ " between ?2 and ?3 and client_organization=?4 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 		
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
						q.setParameter(4, customer);
				}
			
			
			result =q.getResultList();
			for (int i = 0; i < result.size(); i++) {
				ProjectSummaryInfo projectinfo = new ProjectSummaryInfo();
				
				projectinfo.setProjectID(result.get(i)[0].toString());	
				
				if(result.get(i)[1] != null){
				projectinfo.setProjectName(result.get(i)[1].toString());
				}else{
				projectinfo.setProjectName("-");	
				}
				if(result.get(i)[2] != null){
				projectinfo.setCustomer(result.get(i)[2].toString());
				}else{
				projectinfo.setCustomer("");	
				}
				projectinfo.setTotalPlanMM(Double.parseDouble(result.get(i)[3].toString()));
				projectinfo.setTotalActualMM(Double.parseDouble(result.get(i)[4].toString()));
				projectinfo.setMembers(Integer.parseInt(result.get(i)[5].toString()));
				data.add(projectinfo);
			}
			
			em.flush();
			logger.debug("findByCustomer() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByCustomer() method has been failed.", pe);
			throw translate("Failed to find findByCustomer of Report.", pe);
		}
		return data;

	
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findByPartialProject(String project,String startDate,String endDate,String customer) throws DAOException {


		List<Object[]> result = null;
		List<ProjectSummaryInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailProjectByPartial() method has been started.");
			
			if (customer.equals(null) || customer.equals("")) {
				System.out.println("without custemer");
				 q = em
						.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id  like ?1 and activity_date "
										+ " between ?2 and ?3 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 	q.setParameter(1, "%"+project+"%");
				 	q.setParameter(2, startDate);
					q.setParameter(3, endDate);
				
			}else{
				System.out.println("with custemer");
				if (customer.equals("-")){
				
				 q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id like ?1 and activity_date "
										+ " between ?2 and ?3 and (client_organization is NULL or client_organization IN ('','-')) group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 		q.setParameter(1, "%"+project+"%");
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
					
						
						
				}else{
					 q = em
								.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
											+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
											+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
											+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
											+ " project_id from summary_activities  where project_id like ?1 and activity_date "
											+ " between ?2 and ?3 and client_organization=?4 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
					 		q.setParameter(1, "%"+project+"%");
						 	q.setParameter(2, startDate);
							q.setParameter(3, endDate);
							q.setParameter(4, customer);
				}
			}
			
			
			result =q.getResultList();
			for (int i = 0; i < result.size(); i++) {
				System.out.println("Project partial IDs with customer \n" + q);
				ProjectSummaryInfo projectinfo = new ProjectSummaryInfo();
				projectinfo.setProjectID(result.get(i)[0].toString());
				projectinfo.setProjectName(result.get(i)[1].toString());
				if(result.get(i)[2] != null){
				projectinfo.setCustomer(result.get(i)[2].toString());
				}else{
				projectinfo.setCustomer("");	
				}
				projectinfo.setTotalPlanMM(Double.parseDouble(result.get(i)[3].toString()));
				projectinfo.setTotalActualMM(Double.parseDouble(result.get(i)[4].toString()));
				projectinfo.setMembers(Integer.parseInt(result.get(i)[5].toString()));
				data.add(projectinfo);
			}
			
			em.flush();
			logger.debug("findDetailProjectByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailProjectByPartial() method has been failed.", pe);
			throw translate("Failed to find findDetailProjectByPartial of Report.", pe);
		}
		return data;

	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findByAllProject(String startDate,String endDate,String customer) throws DAOException {


		List<Object[]> result = null;
		List<ProjectSummaryInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findByAllProject() method has been started.");
			
			if (customer.equals(null) || customer.equals("")) {
				System.out.println("without custemer");
				 q = em
						.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where activity_date "
										+ " between ?2 and ?3 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 	
				 	q.setParameter(2, startDate);
					q.setParameter(3, endDate);
				
			}else{
				
				System.out.println("with custemer");
				if(customer.equals("-")){
				 q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where activity_date "
										+ " between ?2 and ?3 and (client_organization is NULL or client_organization IN ('','-')) group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 		
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
						
						
				}else{
					 q = em
								.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
											+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
											+ " FROM working_hour_info wi,summary_activities left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
											+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
											+ " project_id from summary_activities  where activity_date "
											+ " between ?2 and ?3 and client_organization=?4 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
						q.setParameter(4, customer);
				}
			}
			
			
			result =q.getResultList();
			for (int i = 0; i < result.size(); i++) {
				ProjectSummaryInfo projectinfo = new ProjectSummaryInfo();
				
				projectinfo.setProjectID(result.get(i)[0].toString());
				
				if(result.get(i)[1] != null){
					projectinfo.setProjectName(result.get(i)[1].toString());
					}else{
					projectinfo.setProjectName("-");	
					}
				
				if(result.get(i)[2] != null){
				projectinfo.setCustomer(result.get(i)[2].toString());
				}else{
				projectinfo.setCustomer("");	
				}
				if(result.get(i)[3] != null){
					projectinfo.setTotalPlanMM(Double.parseDouble(result.get(i)[3].toString()));
					}else{
					projectinfo.setTotalPlanMM(0.0);
					}
				
				projectinfo.setTotalActualMM(Double.parseDouble(result.get(i)[4].toString()));
				projectinfo.setMembers(Integer.parseInt(result.get(i)[5].toString()));
				data.add(projectinfo);
			}
			
			em.flush();
			logger.debug("findByAllProject() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByAllProject() method has been failed.", pe);
			throw translate("Failed to find findByAllProject of Report.", pe);
		}
		return data;

	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByProjectID(String projectId,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailByProjectID() method has been started.");

			 q = em
					.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,"
							+ " p.total_man_month,u.username,u.fullname,"
							+ " t.team_name,t.team_id,SUM(sa.activity_hours) AS hours,"
							+ " SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
							+ " COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
							+ " FROM summary_activities sa, team t,users u,project p, "
							+ " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
							+ " WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id=?1) AS w"
							+ " WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id"
							+ " AND sa.project_id=?1 AND w.activity_date = sa.activity_date"
							+ " AND sa.activity_date BETWEEN ?2 AND ?3"
							+ " GROUP BY t.team_id,u.id,YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY sa.activity_date ");

			q.setParameter(1, projectId);
			//q.setParameter(2, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailByProjectID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailByProjectID() method has been failed.", pe);
			throw translate("Failed to find findDetailByProjectID of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByProjectIDResult(String projectId,String startDate,String endDate,String year,String month,String username,String teamname)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailByProjectID() method has been started.");
			if (projectId == "-"){
				 q = em
							.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,"
									+ " p.total_man_month,u.username,u.fullname,"
									+ " t.team_name,t.team_id,SUM(sa.activity_hours) AS hours,"
									+ " SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
									+ " COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
									+ " FROM summary_activities sa, team t,users u,project p,"
									+ " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
									+ "	WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id IS NULL) AS w"
									+ " WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id"
									+ " AND sa.project_id IS NULL AND w.activity_date = sa.activity_date"
									+ " AND sa.activity_date BETWEEN ?1 AND ?2 AND sa.year=?3 AND sa.month=?4 AND u.username=?5 AND t.team_name=?6" 
									+ " GROUP BY t.team_id,u.id,YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY sa.activity_date");
				

					q.setParameter(1, startDate);
					q.setParameter(2, endDate);
					q.setParameter(3, year);
					q.setParameter(4, month);
					q.setParameter(5, username);
					q.setParameter(6, teamname);
			}else{
			/* q = em
					.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,"
							+ " p.total_man_month,u.username,u.fullname,"
							+ " t.team_name,t.team_id,SUM(sa.activity_hours) AS hours,"
							+ " SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
							+ " COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
							+ " FROM summary_activities sa, team t,users u,project p,"
							+ " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
							+ "	WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id=?1) AS w"
							+ " WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id"
							+ " AND sa.project_id=?1 AND w.activity_date = sa.activity_date"
							+ " AND sa.activity_date BETWEEN ?2 AND ?3 AND sa.year=?4 AND sa.month=?5 AND u.username=?6 AND t.team_name=?7"
							+ " GROUP BY t.team_id,u.id,YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY sa.activity_date");*/
				 q = em
							.createNativeQuery("SELECT sa.project_id,sa.project_name,sa.client_organization as customer,"
									+ " sa.total_man_month,sa.username,sa.fullname,"
									+ " sa.team_name,sa.team_id,SUM(sa.activity_hours) AS hours,"
									+ " SUM(sa.activity_hours)/(20*7) AS manmonth,"
									+ " COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
									+ " FROM summary_activities sa"
									+ " WHERE sa.project_id=?1 "
									+ " AND sa.activity_date BETWEEN ?2 AND ?3 AND sa.year=?4 AND sa.month=?5 AND sa.username=?6 AND sa.team_name=?7"
									+ " GROUP BY sa.team_id,sa.staff_id,YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY sa.activity_date");

			q.setParameter(1, projectId);
			//q.setParameter(2, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			q.setParameter(4, year);
			q.setParameter(5, month);
			q.setParameter(6, username);
			q.setParameter(7, teamname);
			
			}
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailByProjectID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailByProjectID() method has been failed.", pe);
			throw translate("Failed to find findDetailByProjectID of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByPartialResult(String projectId,String startDate,String endDate,String year,String month,String username)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailByPartialResult() method has been started.");
			if (projectId == "-"){
				 q = em
							.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,"
									+ " p.total_man_month,u.username,u.fullname,"
									+ " t.team_name,t.team_id,SUM(sa.activity_hours) AS hours,"
									+ " SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
									+ " COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
									+ " FROM summary_activities sa, team t,users u,project p,"
									+ " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
									+ "	WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id IS NULL) AS w"
									+ " WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id"
									+ " AND sa.project_id IS NULL AND w.activity_date = sa.activity_date"
									+ " AND sa.activity_date BETWEEN ?1 AND ?2 AND sa.year=?3 AND sa.month=?4 AND u.username=?5 AND t.team_name=?6" 
									+ " GROUP BY t.team_id,u.id,YEAR(sa.activity_date),MONTH(sa.activity_date) ORDER BY sa.activity_date");
				

					q.setParameter(1, startDate);
					q.setParameter(2, endDate);
					
			}else{
			q = em
							.createNativeQuery("select s.project_id, s.project_name,s.client_organization,s.total_man_month,"
									+ " sum(a.actmanmonth) actual_man_month,s.team_name,s.username,s.fullname,s.activity_hours,"
									+" s.activity_hours/(20*a.working_hour) man_month,s.year,s.month from summary_activities s, "
									+" (select sum(sa.activity_hours)/(20*wi.working_hour) actmanmonth,wi.working_hour"
									+" from working_hour_info wi, summary_activities sa where sa.activity_date between wi.start_date and wi.end_date"
									+" and sa.project_id=?1 group by wi.working_hour )a"
									+" where s.project_id=?1 and EXTRACT(YEAR_MONTH from s.activity_date) between ?2 and ?3 and s.year=?4 and s.month=?5 and s.username=?6"
									+" group by s.project_id,s.team_id,s.username,s.year,s.month ");
	


			q.setParameter(1, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			q.setParameter(4, year);
			q.setParameter(5, month);
			q.setParameter(6, username);
			
			}
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailByPartialResult() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailByPartialResult() method has been failed.", pe);
			throw translate("Failed to find findDetailByPartialResult of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserCountDetailByProjectIDResult(String projectId,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findUserCountDetailByProjectIDResult() method has been started.");

			 q = em
					.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,"
							+ " p.total_man_month,u.username,u.fullname, "
							+ " t.team_name,t.team_id,SUM(sa.activity_hours) AS hours,"
							+ " SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
							+ " COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
							+ " FROM summary_activities sa, team t,users u,project p,"
							+ " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
							+ " WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id=?1) AS w"
							+ " WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id"
							+ " AND sa.project_id=?1 AND w.activity_date = sa.activity_date"
							+ " AND sa.activity_date BETWEEN ?2 AND ?3"
							+ " GROUP BY t.team_id ORDER BY t.team_id");

			q.setParameter(1, projectId);
			//q.setParameter(2, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findUserCountDetailByProjectIDResult() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findUserCountDetailByProjectIDResult() method has been failed.", pe);
			throw translate("Failed to find findUserCountDetailByProjectIDResult of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserCountDetailByPartialResult(String projectId,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findUserCountDetailByPartialResult() method has been started.");

			/* q = em
					.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,"
										+ "	p.total_man_month,u.username,u.fullname,"
										+ "	t.team_name,t.team_id,SUM(sa.activity_hours) AS hours,"
										+ "	SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
										+ "	COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
										+ "	FROM summary_activities sa, team t,users u,project p,"
										+ "	(SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
										+ "	WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id like ?1) AS w"
										+ "	WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id"
										+ "	AND sa.project_id like ?1 AND w.activity_date = sa.activity_date"
										+ "	AND sa.activity_date BETWEEN ?2 AND ?3"
										+ "	GROUP BY t.team_id,sa.project_id ORDER BY sa.activity_date");*/
			/* q = em
						.createNativeQuery("SELECT sa.project_id,t.team_name,t.team_id,COUNT(DISTINCT sa.staff_id,sa.team_id)  AS members FROM summary_activities sa, team t,users u"
											+ "	WHERE sa.team_id=t.team_id AND sa.staff_id = u.id"
											+ "	AND sa.project_id like ?1"
											+ "	AND sa.activity_date BETWEEN ?2 AND ?3"
											+ "	GROUP BY t.team_id,sa.project_id"
											+ " ORDER BY sa.project_id,t.team_id");*/
			 q = em
						.createNativeQuery("SELECT sa.project_id,sa.team_name,sa.team_id,COUNT(DISTINCT sa.staff_id,sa.team_id)  AS members FROM summary_activities sa"
											+ "	WHERE sa.project_id like ?1"
											+ "	AND sa.activity_date BETWEEN ?2 AND ?3"
											+ "	GROUP BY sa.team_id,sa.project_id"
											+ " ORDER BY sa.project_id,sa.team_id");

			q.setParameter(1, "%"+projectId+"%");
			//q.setParameter(2, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findUserCountDetailByPartialResult() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findUserCountDetailByPartialResult() method has been failed.", pe);
			throw translate("Failed to find findUserCountDetailByPartialResult of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTeamCountDetailByPartialResult(String projectId,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findTeamCountDetailByPartialResult() method has been started.");

		
			/* q = em
						.createNativeQuery("SELECT sa.project_id,COUNT(DISTINCT sa.staff_id,sa.team_id) AS members,COUNT(DISTINCT sa.team_id) AS teamcount FROM summary_activities sa, team t,users u"
											+ "	WHERE sa.team_id=t.team_id AND sa.staff_id = u.id"
											+ "	AND sa.project_id like ?1"
											+ "	AND sa.activity_date BETWEEN ?2 AND ?3"
											+ "	GROUP BY sa.project_id"
											+ " ORDER BY sa.project_id");
*/
			 q = em
						.createNativeQuery("SELECT sa.project_id,COUNT(DISTINCT sa.staff_id,sa.team_id) AS members,COUNT(DISTINCT sa.team_id) AS teamcount FROM summary_activities sa"
											+ "	WHERE sa.project_id like ?1"
											+ "	AND sa.activity_date BETWEEN ?2 AND ?3"
											+ "	GROUP BY sa.project_id"
											+ " ORDER BY sa.project_id");

			q.setParameter(1, "%"+projectId+"%");
			//q.setParameter(2, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findUserCountDetailByPartialResult() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findUserCountDetailByPartialResult() method has been failed.", pe);
			throw translate("Failed to find findUserCountDetailByPartialResult of Report.", pe);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByPartial(String projectId,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailByPartial() method has been started.");

			 q = em
					.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,"
									  + " p.total_man_month,u.username,u.fullname,"
									  + " t.team_name,t.team_id,SUM(sa.activity_hours) AS hours,"
									  + " SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
									  + " COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
									  + " FROM summary_activities sa, team t,users u,project p,"
									  + " (SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
									  + " WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id like ?1) AS w"
									  + " WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id"
									  + " AND sa.project_id like ?1 AND w.activity_date = sa.activity_date"
									  + " AND sa.activity_date BETWEEN ?2 AND ?3"
									  + " GROUP BY t.team_id,u.id,YEAR(sa.activity_date),MONTH(sa.activity_date),sa.project_id ORDER BY sa.activity_date");

			q.setParameter(1, "%"+projectId+"%");
			//q.setParameter(2, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailByPartial() method has been failed.", pe);
			throw translate("Failed to find findDetailByPartial of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserCountEachProject(String projectId,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findUserCountEachProject() method has been started.");

			/* q = em
					.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,"
										+ "	p.total_man_month,u.username,u.fullname,"
										+ "	t.team_name,t.team_id,SUM(sa.activity_hours) AS hours,"
										+ "	SUM(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
										+ "	COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
										+ "	FROM summary_activities sa, team t,users u,project p,"
										+ "	(SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
										+ "	WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id like ?1) AS w"
										+ "	WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id"
										+ "	AND sa.project_id like ?1 AND w.activity_date = sa.activity_date"
										+ "	AND sa.activity_date BETWEEN ?2 AND ?3"
										+ "	GROUP BY u.id,sa.project_id,t.team_id ORDER BY sa.project_id,t.team_id");*/
			
			 q = em
						.createNativeQuery("SELECT sa.project_id,sa.project_name,sa.client_organization as customer,"
											+ "	sa.total_man_month,sa.username,sa.fullname,"
											+ "	sa.team_name,sa.team_id,SUM(sa.activity_hours) AS hours,"
											+ "	SUM(sa.activity_hours)/(20*7) AS manmonth,"
											+ "	COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
											+ "	FROM summary_activities sa"
											+ "	WHERE sa.project_id like ?1"
											+ "	AND sa.activity_date BETWEEN ?2 AND ?3"
											+ "	GROUP BY sa.staff_id,sa.project_id,sa.team_id ORDER BY sa.project_id,sa.team_id");

			q.setParameter(1, "%"+projectId+"%");
			//q.setParameter(2, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findUserCountEachProject() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findUserCountEachProject() method has been failed.", pe);
			throw translate("Failed to find findUserCountEachProject of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTotalUserCountEachProject(String projectId,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findTotalUserCountEachProject() method has been started.");

			/* q = em
					.createNativeQuery("SELECT sa.project_id,COUNT(DISTINCT sa.staff_id,sa.team_id)  AS members"
										+ "	FROM summary_activities sa, team t,users u,project p,"
										+ "	(SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
										+ "	WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id like ?1) AS w"
										+ "	WHERE sa.team_id=t.team_id AND sa.staff_id = u.id AND sa.project_id=p.project_id"
										+ "	AND sa.project_id like ?1 AND w.activity_date = sa.activity_date"
										+ "	AND sa.activity_date BETWEEN ?2 AND ?3"
										+ "	GROUP BY sa.project_id ORDER BY sa.project_id,t.team_id");*/
			 q = em
						.createNativeQuery("SELECT sa.project_id,COUNT(DISTINCT sa.staff_id,sa.team_id)  AS members"
											+ "	FROM summary_activities sa,"
											+ "	(SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
											+ "	WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id like ?1) AS w"
											+ "	WHERE sa.project_id like ?1 AND w.activity_date = sa.activity_date"
											+ "	AND sa.activity_date BETWEEN ?2 AND ?3"
											+ "	GROUP BY sa.project_id ORDER BY sa.project_id,sa.team_id");

			q.setParameter(1, "%"+projectId+"%");
			//q.setParameter(2, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findTotalUserCountEachProject() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findTotalUserCountEachProject() method has been failed.", pe);
			throw translate("Failed to find findTotalUserCountEachProject of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<TotalManMonthInfo> findTotalResultByPartial(String projectId,String startdate,String enddate)
			throws DAOException {

		List<Object[]> result = null;
		List<TotalManMonthInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findTotalResultByPartial() method has been started.");

			
			if(!projectId.equals("-")
					&& !projectId.equalsIgnoreCase("all")) {
			q = em
						.createNativeQuery("SELECT sa.project_id,SUM(sa.activity_hours) AS hours,"
										+ "	sum(sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) AS manmonth,"
										+ "	COUNT(DISTINCT sa.staff_id,sa.team_id) AS members,sa.year,sa.month"
										+ "	FROM working_hour_info wi,summary_activities sa left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
										+ "	WHERE sa.activity_date between wi.start_date and wi.end_date and sa.project_id like ?1"
										+ " AND sa.activity_date between ?2 and ?3"
										+ "	GROUP BY YEAR(sa.activity_date),MONTH(sa.activity_date),sa.project_id"
										+ " ORDER BY sa.activity_date,sa.team_id");

			q.setParameter(1, "%"+projectId+"%");
			//q.setParameter(2, projectId);
			q.setParameter(2, startdate);
			q.setParameter(3, enddate);
			}else  if(projectId.equalsIgnoreCase("all")
					|| projectId.equals("all")) {
				q = em
						.createNativeQuery("SELECT sa.project_id,SUM(sa.activity_hours) AS hours,"
										+ "	sum(sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  AS manmonth,"
										+ "	COUNT(DISTINCT sa.staff_id,sa.team_id) AS members,sa.year,sa.month"
										+ "	FROM working_hour_info wi,summary_activities sa left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
										+ "	WHERE sa.activity_date between wi.start_date and wi.end_date"
										+ " AND sa.activity_date between ?2 and ?3"
										+ "	GROUP BY YEAR(sa.activity_date),MONTH(sa.activity_date),sa.project_id"
										+ " ORDER BY sa.activity_date,sa.team_id");
				q.setParameter(2, startdate);
				q.setParameter(3, enddate);
			
			}else if(projectId.equals("-")){
				q = em
						.createNativeQuery("SELECT sa.project_id,SUM(sa.activity_hours) AS hours,"
										+ "	sum(sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) AS manmonth,"
										+ "	COUNT(DISTINCT sa.staff_id,sa.team_id) AS members,sa.year,sa.month"
										+ "	FROM working_hour_info wi,summary_activities sa left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
										+ "	WHERE sa.activity_date between wi.start_date and wi.end_date and sa.project_id='9999'"
										+ " AND sa.activity_date between ?2 and ?3"
										+ "	GROUP BY YEAR(sa.activity_date),MONTH(sa.activity_date),sa.project_id"
										+ " ORDER BY sa.activity_date,sa.team_id");

			
			//q.setParameter(2, projectId);
			q.setParameter(2, startdate);
			q.setParameter(3, enddate);	
			}
			
			result = q.getResultList();

for (int i = 0; i < result.size(); i++) {
	TotalManMonthInfo totalmanmonth = new TotalManMonthInfo();
	/*if(result.get(i)[0] != null){
		totalmanmonth.setProjectId(result.get(i)[0].toString());
		}else{
		totalmanmonth.setProjectId("-");	
		}*/
	totalmanmonth.setProjectId(result.get(i)[0].toString());
	totalmanmonth.setTotal_hour(Double.parseDouble(result.get(i)[1].toString()));
	totalmanmonth.setTotal_manmonth(Double.parseDouble(result.get(i)[2].toString()));
	totalmanmonth.setYear(result.get(i)[4].toString());
	totalmanmonth.setMonth(result.get(i)[5].toString());
	totalmanmonth.setTotal_members(Integer.parseInt(result.get(i)[3].toString()));
	data.add(totalmanmonth);
}
			em.flush();
			logger.debug("findTotalResultByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findTotalResultByPartial() method has been failed.", pe);
			throw translate("Failed to find findTotalResultByPartial of Report.", pe);
		}
		return data;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectByNull(String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailProjectByNull() method has been started.");

			 q = em
					.createNativeQuery("SELECT sa.project_id,p.project_name,p.client_organization as customer,"
										+ "	p.total_man_month,u.username,u.fullname,"
										+ "	t.team_name,t.team_id,(sa.activity_hours) AS hours,"
										+ "	(sa.activity_hours)/(20*w.working_hour) AS manmonth,"
										+ "	COUNT(DISTINCT sa.staff_id) AS members,sa.year,sa.month"
										+ "	FROM summary_activities sa, team t,users u,project p,"
										+ "	(SELECT DISTINCT wi.*,sa2.activity_date FROM working_hour_info wi,summary_activities sa2"
										+ "	WHERE sa2.activity_date >= wi.start_date AND sa2.activity_date <= wi.end_date AND sa2.project_id IS NULL) AS w"
										+ "	WHERE sa.team_id=t.team_id AND sa.staff_id = u.id"
										+ "	AND sa.project_id IS NULL AND w.activity_date = sa.activity_date"
										+ "	AND sa.activity_date BETWEEN ?1 AND ?2"
										+ "	GROUP BY t.team_id,u.id,YEAR(sa.activity_date),MONTH(sa.activity_date)"
										+ " ORDER BY sa.activity_date,t.team_id");

			
			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailProjectByNull() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailProjectByNull() method has been failed.", pe);
			throw translate("Failed to find findDetailByNull of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findUserCountByNull(String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findUserCountByNull() method has been started.");

			 q = em
					.createNativeQuery(" SELECT u.username,u.fullname,t.team_name,sa.year,sa.month from"
										+ "	summary_activities sa,users u,team t where project_ID is null AND sa.activity_date BETWEEN ?1 AND ?2"
										+ " AND u.id=sa.staff_id AND t.team_id=sa.team_id"
										+ "	GROUP BY sa.staff_ID,sa.team_id"
										+ "	ORDER BY sa.team_id");

		
			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findUserCountByNull() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findUserCountByNull() method has been failed.", pe);
			throw translate("Failed to find findUserCountByNull of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailByNullIDResult(String startDate,String endDate,String year,String month,String staffid,Double working_hour,String teamname)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailByNullIDResult() method has been started.");
			
			 q = em
					.createNativeQuery("SELECT u.username,u.fullname,"
									+ "	t.team_name,t.team_id,(sa.activity_hours) AS hours,"
									+ "	(sa.activity_hours)/(20*?6) AS manmonth,"
									+ "	sa.year,sa.month FROM summary_activities sa, team t,users u"
									+ "	WHERE sa.team_id=t.team_id AND sa.staff_id = u.id"
									+ "	AND sa.project_id IS NULL"
									+ "	AND sa.activity_date BETWEEN ?1 AND ?2 AND sa.year = ?3 AND sa.month = ?4 AND u.username = ?5 AND t.team_name = ?7"
									+ "	GROUP BY t.team_id,u.id,YEAR(sa.activity_date),MONTH(sa.activity_date)"
									+ "	ORDER BY t.team_id");

		
			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
			q.setParameter(3, year);
			q.setParameter(4, month);
			q.setParameter(5, staffid);
			q.setParameter(6, working_hour);
			q.setParameter(7, teamname);
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailByNullIDResult() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailByNullIDResult() method has been failed.", pe);
			throw translate("Failed to find findDetailByNullIDResult of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public 	List<Double> findWorkingHour(String date) 
	{
		List<Double> result = null;
		try {
			logger.debug("findWorkingHour() method has been started.");
			Query q = em.createNativeQuery("SELECT w.working_hour FROM working_hour_info w WHERE ?1 between DATE(w.start_date) and DATE(w.end_date);");
			q.setParameter(1, date);
		
			
			result = q.getResultList();
			em.flush();
			logger.debug("findWorkingHour() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findWorkingHour() method has been failed.", pe);
			throw translate("Failed to findWorkingHour.", pe);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTotalResultByNull(String year,String month,Double workingHour)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findTotalResultByNull() method has been started.");

			 q = em
					.createNativeQuery("SELECT sum(activity_hours) as hours,sum(activity_hours)/(20*?3) AS manmonth,year,month FROM summary_activities s where project_id is null and year=?1 and month=?2 group by year(s.activity_date),month(s.activity_date)");

			q.setParameter(1, year);
			q.setParameter(2, month);
			q.setParameter(3, workingHour);
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findTotalResultByNull() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findTotalResultByNull() method has been failed.", pe);
			throw translate("Failed to find findTotalResultByNull of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findMemberCountEachTeamByNull(String year,String month)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findMemberCountEachTeamByNull() method has been started.");

			 q = em
					.createNativeQuery("SELECT t.team_name,t.team_id,COUNT(DISTINCT sa.staff_id,sa.team_id) AS members FROM summary_activities sa, team t,users u"
										+ "	WHERE sa.team_id=t.team_id AND sa.staff_id = u.id"
										+ "	AND sa.project_id IS NULL"
										+ "	AND sa.activity_date BETWEEN ?1 AND ?2"
										+ "	GROUP BY t.team_id"
										+ " ORDER BY sa.team_id");

			q.setParameter(1, year);
			q.setParameter(2, month);
		
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findMemberCountEachTeamByNull() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMemberCountEachTeamByNull() method has been failed.", pe);
			throw translate("Failed to find findMemberCountEachTeamByNull of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectByAll(String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailProjectByAll() method has been started.");

			 q = em
					.createNativeQuery("SELECT sa.project_id,sa.year,sa.month from summary_activities sa WHERE sa.activity_date BETWEEN ?1 AND ?2 GROUP BY sa.project_id ORDER BY sa.activity_date");

			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
		
		
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailProjectByAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailProjectByAll() method has been failed.", pe);
			throw translate("Failed to find findDetailProjectByAll of Report.", pe);
		}
		return result;

	}


	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailUserByAll(String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailUserByAll() method has been started.");

			 q = em
					.createNativeQuery("SELECT sa.project_id,u.username,u.fullname,sa.year,sa.month,t.team_name from summary_activities sa,users u,team t where u.id=sa.staff_id AND t.team_id=sa.team_id AND sa.activity_date BETWEEN ?1 AND ?2 GROUP BY sa.staff_id,sa.project_id,sa.team_id ORDER BY sa.project_id,sa.team_id");

			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
			
		
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailUserByAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailUserByAll() method has been failed.", pe);
			throw translate("Failed to find findDetailUserByAll of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectResultByAll(String projectId,String year,String month,String username,Double workingHour,String teamname)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailProjectResultByAll() method has been started.");
			if(!projectId.equals("-") || projectId != "-"){
			 q = em
					.createNativeQuery("SELECT sa.project_id,u.username,u.fullname,t.team_name,sa.activity_hours,(sa.activity_hours/(20*?5)) as manmonth,sa.year,sa.month from summary_activities sa,users u,team t where u.id=sa.staff_id and t.team_id=sa.team_id and sa.year=?2 and sa.month=?3 and sa.project_id=?1 and u.username=?4 and t.team_name=?6");

			q.setParameter(1, projectId);
			q.setParameter(2, year);
			q.setParameter(3, month);
			q.setParameter(4, username);
			q.setParameter(5, workingHour);
			q.setParameter(6, teamname);
			}else{
				 q = em
						.createNativeQuery("SELECT sa.project_id,u.username,u.fullname,t.team_name,sa.activity_hours,(sa.activity_hours/(20*?5)) as manmonth,sa.year,sa.month from summary_activities sa,users u,team t where u.id=sa.staff_id and t.team_id=sa.team_id and sa.year=?2 and sa.month=?3 and sa.project_id IS NULL and u.username=?4 and t.team_name=?6");

					
					q.setParameter(2, year);
					q.setParameter(3, month);
					q.setParameter(4, username);
					q.setParameter(5, workingHour);
					q.setParameter(6, teamname);
			}
		
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailProjectResultByAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailProjectResultByAll() method has been failed.", pe);
			throw translate("Failed to find findDetailUserByAll of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailRecordCountByAll(String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailRecordCountByAll() method has been started.");

			 q = em
					.createNativeQuery("SELECT sa.project_id,u.username,u.fullname,sa.year,sa.month from summary_activities sa ,users u WHERE u.id=sa.staff_id and sa.activity_date BETWEEN ?1 AND ?2");

			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
			
			
		
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailRecordCountByAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailRecordCountByAll() method has been failed.", pe);
			throw translate("Failed to find findDetailRecordCountByAll of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findMemberCountEachTeamByAll(String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findMemberCountEachTeamByAll() method has been started.");

			 q = em
					.createNativeQuery("SELECT t.team_name,t.team_id,COUNT(DISTINCT sa.staff_id) AS members FROM summary_activities sa, team t,users u"
										+ "	WHERE sa.team_id=t.team_id AND sa.staff_id = u.id"
										+ "	AND sa.activity_date BETWEEN ?1 and ?2"
										+ "	GROUP BY t.team_id"
										+ "	ORDER BY sa.activity_date");

			q.setParameter(1, startDate);
			q.setParameter(2, endDate);
		
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findMemberCountEachTeamByAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findMemberCountEachTeamByAll() method has been failed.", pe);
			throw translate("Failed to find findMemberCountEachTeamByAll of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findteamListByAll(String projectId,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findteamListByAll() method has been started.");
			if(projectId == "-"){
				 q = em
						.createNativeQuery("SELECT s.project_id,t.team_name,count(distinct s.staff_id,s.team_id) as members,s.year,s.month FROM summary_activities s,team t where t.team_id=s.team_id and s.project_id IS NULL and s.activity_date between ?2 and ?3 group by t.team_id order by s.team_id");
				 	q.setParameter(2, startDate);
					q.setParameter(3, endDate);
			}else{
			 q = em
				   .createNativeQuery("SELECT s.project_id,t.team_name,count(distinct s.staff_id,s.team_id) as members,s.year,s.month FROM summary_activities s,team t where t.team_id=s.team_id and s.project_id=?1 and s.activity_date between ?2 and ?3 group by t.team_id order by s.team_id");
			 	q.setParameter(1, projectId);
				q.setParameter(2, startDate);
				q.setParameter(3, endDate);
			}
			
		
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findteamListByAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findteamListByAll() method has been failed.", pe);
			throw translate("Failed to find findteamListByAll of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findteamCountByAll(String projectId,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findteamCountByAll() method has been started.");
			if(projectId == "-"){
				 q = em
							.createNativeQuery("SELECT s.project_id,count(distinct t.team_id) as team_count,count(distinct s.staff_id,s.team_id) as members  FROM summary_activities s,team t where t.team_id=s.team_id and s.project_id IS NULL and s.activity_date between ?2 and ?3 group by s.project_id order by s.team_id");
					 
					
					q.setParameter(2, startDate);
					q.setParameter(3, endDate);
				
			}else{
			 q = em
					.createNativeQuery("SELECT s.project_id,count(distinct t.team_id) as team_count,count(distinct s.staff_id,s.team_id) as members  FROM summary_activities s,team t where t.team_id=s.team_id and s.project_id=?1 and s.activity_date between ?2 and ?3 group by s.project_id order by s.team_id");
			 
			q.setParameter(1, projectId);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
		}
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findteamCountByAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findteamCountByAll() method has been failed.", pe);
			throw translate("Failed to find findteamCountByAll of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findTotalResultByAll(String projectId,String year,String month,Double workingHour)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findTotalResultByAll() method has been started.");
			if(projectId == "-"){
				q = em
						.createNativeQuery("SELECT project_id,sum(activity_hours) as hours,sum(activity_hours)/(20*?4) AS manmonth,year,month FROM summary_activities s where s.project_id IS NULL and s.year=?2 and s.month=?3  group by year(s.activity_date),month(s.activity_date)");

				
				q.setParameter(2, year);
				q.setParameter(3, month);
				q.setParameter(4, workingHour);	
			}else{
			 q = em
					.createNativeQuery("SELECT project_id,sum(activity_hours) as hours,sum(activity_hours)/(20*?4) AS manmonth,year,month FROM summary_activities s where s.project_id =?1 and s.year=?2 and s.month=?3   group by year(s.activity_date),month(s.activity_date)");

			q.setParameter(1, projectId);
			//q.setParameter(2, projectId);
			q.setParameter(2, year);
			q.setParameter(3, month);
			q.setParameter(4, workingHour);
			}
			
			result = q.getResultList();
			em.flush();
			logger.debug("findTotalResultByAll() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findTotalResultByAll() method has been failed.", pe);
			throw translate("Failed to find findTotalResultByAll of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectByCustomer(String customer,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findTotalResultByAll() method has been started.");
			
			 q = em
					.createNativeQuery("SELECT sa.project_id,p.client_organization,sa.year,sa.month"
										+ " FROM summary_activities sa,project p where p.project_id=sa.project_id"
										+ " and p.client_organization=?1 and sa.activity_date between ?2 and ?3 group by sa.project_id;");

			q.setParameter(1, customer);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
		
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailProjectByCustomer() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailProjectByCustomer() method has been failed.", pe);
			throw translate("Failed to find findDetailProjectByCustomer of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailUserByCustomer(String customer,String startDate,String endDate)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailUserByCustomer() method has been started.");

			 q = em
					.createNativeQuery("SELECT sa.project_id,p.client_organization,u.username,u.fullname,t.team_name,sa.year,sa.month"
										+ " FROM summary_activities sa,users u,team t,project p where u.id=sa.staff_id and t.team_id=sa.team_id and p.project_id=sa.project_id"
										+ " and p.client_organization=?1 and sa.activity_date between ?2 and ?3 group by sa.staff_id,sa.project_id,sa.team_id ORDER BY sa.project_id,t.team_id");
			
			q.setParameter(1, customer);
			q.setParameter(2, startDate);
			q.setParameter(3, endDate);
			
		
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailUserByCustomer() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailUserByCustomer() method has been failed.", pe);
			throw translate("Failed to find findDetailUserByCustomer of Report.", pe);
		}
		return result;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDetailProjectResultByCustomer(String projectId,String customer,String year,String month,String username,Double workingHour,String teamname)
			throws DAOException {

		List<Object[]> result = null;
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailProjectResultByCustomer() method has been started.");
			
				 q = em
						.createNativeQuery("SELECT sa.project_id,p.client_organization,u.username,u.fullname,t.team_name,sa.activity_hours,sa.activity_hours/(20*?6) as manmonth,sa.year,sa.month"
								+ " FROM summary_activities sa,users u,team t,project p where u.id=sa.staff_id and t.team_id=sa.team_id and p.project_id=sa.project_id"
								+ " and sa.project_id=?1 and p.client_organization=?2 and sa.year=?3 and sa.month=?4 and u.username=?5 and team_name=?7  group by sa.staff_id,sa.project_id ORDER BY sa.project_id;");

				 	q.setParameter(1, projectId);
				 	q.setParameter(2, customer);
					q.setParameter(3, year);
					q.setParameter(4, month);
					q.setParameter(5, username);
					q.setParameter(6, workingHour);
					q.setParameter(7, teamname);
		
			
			
			result = q.getResultList();
			em.flush();
			logger.debug("findDetailProjectResultByCustomer() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailProjectResultByCustomer() method has been failed.", pe);
			throw translate("Failed to find findDetailProjectResultByCustomer of Report.", pe);
		}
		return result;

	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findDetailProjectByPartial(String projectId,String startDate, String endDate,String customer)
			throws DAOException {

		List<Object[]> result = null;
		List<ProjectSummaryInfo> data = new ArrayList<>();
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailProjectByPartial() method has been started.");
			if(!projectId.equals("") && (!projectId.equalsIgnoreCase("all"))){
			if (customer.equals(null) || customer.equals("")) {
				System.out.println("****PARTIAL QUERY WITHOUT CUSTOMER*********");
				 q = em
						.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities "
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id  like ?1 and activity_date "
										+ " between ?2 and ?3 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");

				 	q.setParameter(1, "%"+projectId+"%");
				 	q.setParameter(2, startDate);
					q.setParameter(3, endDate);
			}else{
				
				System.out.println("****PARTIAL QUERY WITH CUSTOMER*********");
				if(customer.equals("-")){
				 q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities"
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id  like ?1 and activity_date "
										+ " between ?2 and ?3 and (client_organization is NULL or client_organization IN ('','-')) group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");


					 	q.setParameter(1, "%"+projectId+"%");
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);	
						
				}else{
					 q = em
								.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
											+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  as TOTAL_ACTUAL_MAN_MONTH"
											+ " FROM working_hour_info wi,summary_activities "
											+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
											+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
											+ " project_id from summary_activities  where project_id  like ?1 and activity_date "
											+ " between ?2 and ?3 and client_organization = ?4 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");


						 	q.setParameter(1, "%"+projectId+"%");
						 	q.setParameter(2, startDate);
							q.setParameter(3, endDate);	
							q.setParameter(4, customer);	
				}
			}
			}else if (projectId.equalsIgnoreCase("all") || projectId.equals("all")) {
				if (customer.equals(null) || customer.equals("")) {
					System.out.println("****ALL QUERY WITHOUT CUSTOMER*********");
					 q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities "
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where activity_date "
										+ " between ?2 and ?3 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");

					 	
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
				}else{
					
					System.out.println("****ALL QUERY WITH CUSTOMER*********");
					if(customer.equals("-")){
					 q = em
								.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities "
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where activity_date "
										+ " between ?2 and ?3 and (client_organization is NULL or client_organization IN ('','-')) group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");

						 	
						 	q.setParameter(2, startDate);
							q.setParameter(3, endDate);	
							
					}else{
						 q = em
									.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
											+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
											+ " FROM working_hour_info wi,summary_activities "
											+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
											+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
											+ " project_id from summary_activities  where activity_date "
											+ " between ?2 and ?3 and client_organization = ?4 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");

							 	
							 	q.setParameter(2, startDate);
								q.setParameter(3, endDate);	
								q.setParameter(4, customer);	
					}
				}
				
			}else if (projectId.equals("")) {
				
				System.out.println("****CUSTOMER QUERY WITHOUT PROJECT*********");
				if(customer.equals("-")){
				 q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities "
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where activity_date "
										+ " between ?2 and ?3 and (client_organization is NULL or client_organization IN ('','-')) group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 
					 	
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);	
						
				}else{
					 q = em
								.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
											+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
											+ " FROM working_hour_info wi,summary_activities "
											+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
											+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
											+ " project_id from summary_activities  where activity_date "
											+ " between ?2 and ?3 and client_organization = ?4 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
					 
						 	
						 	q.setParameter(2, startDate);
							q.setParameter(3, endDate);	
							q.setParameter(4, customer);	
				}
			}
			
			
			result = q.getResultList();
			for (int i = 0; i < result.size(); i++) {
				ProjectSummaryInfo projectinfo = new ProjectSummaryInfo();
				projectinfo.setProjectID(result.get(i)[0].toString());
				
				if(result.get(i)[1] != null){
					projectinfo.setProjectName(result.get(i)[1].toString());	
				}else{
					projectinfo.setProjectName("-");	
				}
				
				if(result.get(i)[2] != null){
				projectinfo.setCustomer(result.get(i)[2].toString());
				}else{
				projectinfo.setCustomer("");	
				}
				if(result.get(i)[3] != null){
				projectinfo.setTotalPlanMM(Double.parseDouble(result.get(i)[3].toString()));
				}else{
				projectinfo.setTotalPlanMM(0.0);	
				}
				projectinfo.setTotalActualMM(Double.parseDouble(result.get(i)[4].toString()));
				projectinfo.setMembers(Integer.parseInt(result.get(i)[5].toString()));
				data.add(projectinfo);
			}
			em.flush();
			logger.debug("findDetailProjectByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailProjectByPartial() method has been failed.", pe);
			throw translate("Failed to find findDetailProjectByPartial of Report.", pe);
		}
		return data;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findDetailProjectByFull(String projectId,String startDate, String endDate,String customer)
			throws DAOException {

		List<Object[]> result = null;
		List<ProjectSummaryInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailProjectByPartial() method has been started.");
			if(customer.equals(null) || customer.equals("")){
				 q = em
						.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities "
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id  = ?1 and activity_date "
										+ " between ?2 and ?3 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 	q.setParameter(1, projectId);
				 	q.setParameter(2, startDate);
					q.setParameter(3, endDate);
				
			}else{
				if(customer.equals("-")){
					q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
											+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour))  as TOTAL_ACTUAL_MAN_MONTH"
											+ " FROM working_hour_info wi,summary_activities "
											+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
											+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
											+ " project_id from summary_activities  where project_id  = ?1 and activity_date "
											+ " between ?2 and ?3 and (client_organization is NULL or client_organization IN ('','-')) group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
					 	q.setParameter(1, projectId);
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
						
				}else{
					q = em
							.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
											+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
											+ " FROM working_hour_info wi,summary_activities "
											+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
											+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
											+ " project_id from summary_activities  where project_id  = ?1 and activity_date "
											+ " between ?2 and ?3 and client_organization = ?4 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
					 	q.setParameter(1, projectId);
					 	q.setParameter(2, startDate);
						q.setParameter(3, endDate);
						q.setParameter(4, customer);	
				}
			}
			
			
			result =q.getResultList();
			for (int i = 0; i < result.size(); i++) {
				ProjectSummaryInfo projectinfo = new ProjectSummaryInfo();
				projectinfo.setProjectID(result.get(i)[0].toString());
				projectinfo.setProjectName(result.get(i)[1].toString());
				if(result.get(i)[2] != null){
				projectinfo.setCustomer(result.get(i)[2].toString());
				}else{
				projectinfo.setCustomer("");	
				}
				projectinfo.setTotalPlanMM(Double.parseDouble(result.get(i)[3].toString()));
				projectinfo.setTotalActualMM(Double.parseDouble(result.get(i)[4].toString()));
				projectinfo.setMembers(Integer.parseInt(result.get(i)[5].toString()));
				data.add(projectinfo);
			}
			
			em.flush();
			logger.debug("findDetailProjectByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailProjectByPartial() method has been failed.", pe);
			throw translate("Failed to find findDetailProjectByPartial of Report.", pe);
		}
		return data;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<TeamInfo> findDetailTeamByPartial(String projectId,String startDate, String endDate)
			throws DAOException {

		List<Object[]> result = null;
		List<TeamInfo> data = new ArrayList<>();
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailTeamByPartial() method has been started.");
			if(projectId != "-" || !projectId.equals("-")){
				 q = em
						.createNativeQuery("SELECT s.project_id,s.team_name,count(distinct s.staff_id,s.team_name) as member from summary_activities s "
											+ " where s.project_id=?1 and s.activity_date between "
											+ " ?2 and ?3 group by s.project_id, s.team_id");

				 	q.setParameter(1, projectId);
				 	q.setParameter(2, startDate);
					q.setParameter(3, endDate);
			}else{
				 q = em
							.createNativeQuery("SELECT s.project_id,s.team_name,count(distinct s.staff_id,s.team_name) as member "
									+ " from summary_activities s where s.project_id='9999' and "
									+ " s.activity_date between ?1 and ?2 group by s.project_id, s.team_id;");

					 	
					 	q.setParameter(1, startDate);
						q.setParameter(2, endDate);
			}
		
			
			
			result = q.getResultList();
			for (int i = 0; i < result.size(); i++) {
				TeamInfo teaminfo = new TeamInfo();
				if(result.get(i)[0] != null){
				teaminfo.setProjectID(result.get(i)[0].toString());
				}else{
				teaminfo.setProjectID("-");	
				}
				teaminfo.setTeanName(result.get(i)[1].toString());
				
				data.add(teaminfo);
			}
			em.flush();
			logger.debug("findDetailTeamByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailTeamByPartial() method has been failed.", pe);
			throw translate("Failed to find findDetailTeamByPartial of Report.", pe);
		}
		return data;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<StaffInfo> findDetailUserByPartial(String projectId,String teamname,String startDate, String endDate)
			throws DAOException {

		
		List<Object[]> result = null;
		List<StaffInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailUserByPartial() method has been started.");
			if(projectId != "-" || !projectId.equals("-")){
				 q = em
						.createNativeQuery("SELECT s.project_id,s.team_name,s.username,s.fullname from summary_activities s "
									+ " where s.project_id=?1 and s.team_name=?2 and s.activity_date between "
									+ " ?3 and ?4 group by s.project_id, s.team_id,s.staff_id");

				 	q.setParameter(1, projectId);
				 	q.setParameter(2, teamname);
				 	q.setParameter(3, startDate);
					q.setParameter(4, endDate);
			}else{
				q = em
						.createNativeQuery("SELECT s.project_id,s.team_name,s.username,s.fullname from summary_activities s "
									 + " where s.project_id='9999' and s.team_name=?1 and s.activity_date between "
									 + " ?2 and ?3 group by s.project_id, s.team_id,s.staff_id");

				 	
				 	q.setParameter(1, teamname);
				 	q.setParameter(2, startDate);
					q.setParameter(3, endDate);
			}
		
			
			
			result = q.getResultList();

for (int i = 0; i < result.size(); i++) {
	StaffInfo staffinfo = new StaffInfo();
	staffinfo.setStaffId(result.get(i)[2].toString());
	staffinfo.setStaffName(result.get(i)[3].toString());
	data.add(staffinfo);
}
			em.flush();
			logger.debug("findDetailUserByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailUserByPartial() method has been failed.", pe);
			throw translate("Failed to find findDetailTeamByPartial of Report.", pe);
		}
		return data;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ManMonthInfo> findManmonthByPartial(String projectId,String teamname,String startdate, String enddate)
			throws DAOException {

		
		List<Object[]> result = null;
		List<ManMonthInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findManmonthByPartial() method has been started.");
			if(!projectId.equals("-")
					&& !projectId.equalsIgnoreCase("all")) {
				System.out.println("###################@@@@@@@@@@@@@@@@@@@@@@@FIND BY PROJECT ID############");
				q = em
						.createNativeQuery("select sa.project_id,sa.team_name,sa.username,sa.fullname,sum(sa.activity_hours),"
										+ " sum(sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) man_month,sa.year,sa.month,COUNT(distinct sa.staff_id,sa.team_id) AS members "
										+ " from working_hour_info wi, summary_activities sa left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
										+ " where sa.activity_date "
										+ " between wi.start_date and wi.end_date and sa.project_id like ?1 "
										+ " and sa.activity_date between ?4 and ?5"
										+ " group by sa.project_id,sa.team_id,sa.year,sa.month");

				 	q.setParameter(1, "%"+projectId+"%");
				 	/*q.setParameter(2, teamname);
				 	q.setParameter(3, username);*/
					q.setParameter(4, startdate);
					q.setParameter(5, enddate);
			}else  if(projectId.equalsIgnoreCase("all")
					|| projectId.equals("all")) {
				System.out.println("###################@@@@@@@@@@@@@@@@@@@@@@@FIND BY ALL############");
				q = em
						.createNativeQuery("select sa.project_id,sa.team_name,sa.username,sa.fullname,sum(sa.activity_hours),"
										+ " sum(sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) man_month,sa.year,sa.month,COUNT(distinct sa.staff_id,sa.team_id) AS members "
										+ " from working_hour_info wi, summary_activities sa left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
										+ " where sa.activity_date "
										+ " between wi.start_date and wi.end_date"
										+ " and sa.activity_date between ?4 and ?5"
										+ " group by sa.project_id,sa.team_id,sa.year,sa.month");

				 
				 	
					q.setParameter(4, startdate);
					q.setParameter(5, enddate);
			}else if(projectId.equals("-")){
				System.out.println("###################@@@@@@@@@@@@@@@@@@@@@@@FIND BY NULL############");
				q = em
						.createNativeQuery("select sa.project_id,sa.team_name,sa.username,sa.fullname,sum(sa.activity_hours),"
										+ " sum(sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) man_month,sa.year,sa.month,COUNT(distinct sa.staff_id,sa.team_id) AS members "
										+ " from working_hour_info wi, summary_activities sa left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
										+ " where sa.activity_date "
										+ " between wi.start_date and wi.end_date and sa.project_id is NULL "
										+ " and sa.activity_date between ?4 and ?5"
										+ " group by sa.project_id,sa.team_id,sa.year,sa.month");

				 
				 	
					q.setParameter(4, startdate);
					q.setParameter(5, enddate);
			}
			
			System.out.println("###################@@@@@@@@@@@@@@@@@@@@@@@DO NOT FIND ANY############");
			result = q.getResultList();
			System.out.println("***********RESULT LIST SIZE ="+result.size());
			for (int i = 0; i < result.size(); i++) {
			
				ManMonthInfo manmonth = new ManMonthInfo();
				if(result.get(i)[0] != null){
				manmonth.setProjectId(result.get(i)[0].toString());
				}else{
				manmonth.setProjectId("-");	
				}
				manmonth.setTeamname(result.get(i)[1].toString());
				manmonth.setUsername(result.get(i)[2].toString());
				manmonth.setHour(Double.parseDouble(result.get(i)[4].toString()));
				manmonth.setManmonth(Double.parseDouble(result.get(i)[5].toString()));
				manmonth.setYear(result.get(i)[6].toString());
				manmonth.setMonth(result.get(i)[7].toString());
				manmonth.setMembers(Integer.parseInt(result.get(i)[8].toString()));
				data.add(manmonth);
			}
			em.flush();
			logger.debug("findManmonthByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findManmonthByPartial() method has been failed.", pe);
			throw translate("Failed to find findManmonthByPartial of Report.", pe);
		}
		return data;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ManMonthInfo> findDetailManmonthByPartial(String projectId,String teamname,String username,String startdate, String enddate)
			throws DAOException {

		
		List<Object[]> result = null;
		List<ManMonthInfo> data = new ArrayList<>();
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailManmonthByPartial() method has been started.");
			if(!projectId.equals("-")
					&& !projectId.equalsIgnoreCase("all")) {
				System.out.println("###################@@@@@@@@@@@@@@@@@@@@@@@FIND BY PROJECT ID############");
				q = em
						.createNativeQuery("select sa.project_id,sa.team_name,sa.username,sa.fullname,sa.activity_hours,"
										+ " (sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) man_month,sa.year,sa.month "
										+ " from working_hour_info wi, summary_activities sa "
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
										+ " where sa.activity_date "
										+ " between wi.start_date and wi.end_date and sa.project_id like ?1 "
										+ " and sa.activity_date between ?4 and ?5"
										+ " group by sa.project_id,sa.team_id,sa.username,sa.year,sa.month");

				 	q.setParameter(1, "%"+projectId+"%");
				 	/*q.setParameter(2, teamname);
				 	q.setParameter(3, username);*/
					q.setParameter(4, startdate);
					q.setParameter(5, enddate);
			}else  if(projectId.equalsIgnoreCase("all")
					|| projectId.equals("all")) {
				System.out.println("###################@@@@@@@@@@@@@@@@@@@@@@@FIND BY ALL############");
				q = em
						.createNativeQuery("select sa.project_id,sa.team_name,sa.username,sa.fullname,sa.activity_hours,"
										+ " (sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) man_month,sa.year,sa.month "
										+ " from working_hour_info wi, summary_activities sa "
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
										+ " where sa.activity_date "
										+ " between wi.start_date and wi.end_date"
										+ " and sa.activity_date between ?4 and ?5"
										+ " group by sa.project_id,sa.team_id,sa.username,sa.year,sa.month");

				 
				 	
					q.setParameter(4, startdate);
					q.setParameter(5, enddate);
			}else if(projectId.equals("-")){
				System.out.println("###################@@@@@@@@@@@@@@@@@@@@@@@FIND BY NULL############");
				q = em
						.createNativeQuery("select sa.project_id,sa.team_name,sa.username,sa.fullname,sa.activity_hours,"
										+ " (sa.activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) man_month,sa.year,sa.month "
										+ " from working_hour_info wi, summary_activities sa "
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from sa.activity_date)"
										+ " where sa.activity_date "
										+ " between wi.start_date and wi.end_date and sa.project_id='9999' "
										+ " and sa.activity_date between ?4 and ?5"
										+ " group by sa.project_id,sa.team_id,sa.username,sa.year,sa.month");

				 
				 	
					q.setParameter(4, startdate);
					q.setParameter(5, enddate);
			}
			
			System.out.println("###################@@@@@@@@@@@@@@@@@@@@@@@DO NOT FIND ANY############");
			result = q.getResultList();
			System.out.println("***********RESULT LIST SIZE ="+result.size());
			for (int i = 0; i < result.size(); i++) {
			
				ManMonthInfo manmonth = new ManMonthInfo();
				if(result.get(i)[0] != null){
				manmonth.setProjectId(result.get(i)[0].toString());
				}else{
				manmonth.setProjectId("-");	
				}
				manmonth.setTeamname(result.get(i)[1].toString());
				manmonth.setUsername(result.get(i)[2].toString());
				manmonth.setHour(Double.parseDouble(result.get(i)[4].toString()));
				manmonth.setManmonth(Double.parseDouble(result.get(i)[5].toString()));
				manmonth.setYear(result.get(i)[6].toString());
				manmonth.setMonth(result.get(i)[7].toString());
				data.add(manmonth);
			}
			em.flush();
			logger.debug("findDetailManmonthByPartial() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailManmonthByPartial() method has been failed.", pe);
			throw translate("Failed to find findDetailManmonthByPartial of Report.", pe);
		}
		return data;

	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProjectSummaryInfo> findDetailNullProject(String startDate, String endDate)
			throws DAOException {

		List<Object[]> result = null;
		List<ProjectSummaryInfo> data = new ArrayList<>();
		
		
		Query q = null;
		
			
		try {
			logger.debug("findDetailNullProject() method has been started.");
			
				 q = em
						.createNativeQuery("SELECT * FROM (SELECT PROJECT_ID,PROJECT_NAME,CLIENT_ORGANIZATION,"
										+ " TOTAL_MAN_MONTH,sum(activity_hours/((20 - IFNULL(hc.noOfMonth,0))*wi.working_hour)) as TOTAL_ACTUAL_MAN_MONTH"
										+ " FROM working_hour_info wi,summary_activities "
										+ " left JOIN holidaycount hc ON CONCAT(hc.YEAR,hc.MONTH) = EXTRACT(YEAR_MONTH from activity_date)"
										+ " where activity_date between wi.start_date and wi.end_date group by project_id) as A join (select count(distinct staff_id,team_name) as member,"
										+ " project_id from summary_activities  where project_id='9999' and activity_date "
										+ " between ?2 and ?3 group by project_id) as B on A.project_id=B.project_id group by A.PROJECT_ID");
				 
				 q.setParameter(2, startDate);
				 q.setParameter(3, endDate);

				 
			result = q.getResultList();
			for (int i = 0; i < result.size(); i++) {
				ProjectSummaryInfo projectinfo = new ProjectSummaryInfo();
				
				projectinfo.setTotalActualMM(Double.parseDouble(result.get(i)[4].toString()));
				projectinfo.setMembers(Integer.parseInt(result.get(i)[5].toString()));
				data.add(projectinfo);
			}
			em.flush();
			logger.debug("findDetailNullProject() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findDetailNullProject() method has been failed.", pe);
			throw translate("Failed to find findDetailNullProject of Report.", pe);
		}
		return data;

	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void findHolidayCount()
			throws DAOException {

		List<Object[]> result = null;
		
		
		
		Query q = null;
		
			System.out.println("FIND HOLIDAY COUNT START....");
		try {
			logger.debug("findHolidayCount() method has been started.");
			em.createNativeQuery(" DROP TEMPORARY TABLE IF EXISTS holidaycount").executeUpdate();
			em.createNativeQuery(" CREATE TEMPORARY TABLE IF NOT EXISTS holidaycount(YEAR VARCHAR(255),MONTH VARCHAR(255),noOfMonth INT)"
					+ " SELECT YEAR, MONTH, COUNT(MONTH) noOfMonth FROM holidays GROUP BY YEAR,MONTH ORDER BY YEAR,MONTH ").executeUpdate();
				
			System.out.println("FIND HOLIDAY COUNT END....");		 
				

				 
			
			
			em.flush();
			logger.debug("findHolidayCount() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findHolidayCount() method has been failed.", pe);
			throw translate("Failed to find findHolidayCount of Report.", pe);
		}
		

	}


	@Transactional(propagation = Propagation.REQUIRED)
	public List<HolidayCount> getHolidayCount()
			throws DAOException {

		List<Object[]> result = null;
		List<HolidayCount> hclist = new ArrayList<>();
		
		
		Query q = null;
		
			System.out.println("FIND HOLIDAY COUNT START....");
		try {
			logger.debug("getHolidayCount() method has been started.");
			em.createNativeQuery(" DROP TEMPORARY TABLE IF EXISTS holidaycount").executeUpdate();
			em.createNativeQuery(" CREATE TEMPORARY TABLE IF NOT EXISTS holidaycount(YEAR VARCHAR(255),MONTH VARCHAR(255),noOfMonth INT)"
					+ " SELECT YEAR, MONTH, COUNT(MONTH) noOfMonth FROM holidays GROUP BY YEAR,MONTH ORDER BY YEAR,MONTH ").executeUpdate();
				 q = em
						.createNativeQuery(" SELECT * FROM holidaycount;");
				 result = q.getResultList();
				 for (int i = 0; i < result.size(); i++) {
					 HolidayCount hc = new HolidayCount();
					 hc.setYear(result.get(i)[0].toString());
					 hc.setMonth(result.get(i)[1].toString());
					 hc.setHolidayCount(Integer.parseInt(result.get(i)[2].toString()));
					 hclist.add(hc);
					
				}
			System.out.println("FIND HOLIDAY COUNT END....");		 
				

				 
			
			
			em.flush();
			logger.debug("getHolidayCount() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("getHolidayCount() method has been failed.", pe);
			throw translate("Failed to find getHolidayCount of Report.", pe);
		}
		
		return hclist;
	}


}
