/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.dailyreport.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.dailyreport.persistence.interfaces.IDailyReportDAO;
import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.Role;
import com.dat.whm.user.entity.User;
import com.dat.whm.web.common.SearchDate;

@Repository("DailyReportDAO")
public class DailyReportDAO extends BasicDAO implements IDailyReportDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public DailyReport insert(DailyReport dailyReport) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(dailyReport);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert DailyReport(Id = "
					+ dailyReport.getId() + ")", pe);
		}
		return dailyReport;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public DailyReport update(DailyReport dailyReport) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			dailyReport = em.merge(dailyReport);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update DailyReport(Id = "
					+ dailyReport.getId() + ")", pe);
		}
		return dailyReport;
	}

	/*@Transactional(propagation = Propagation.REQUIRED)
	public void delete(DailyReport dailyReport) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			dailyReport = em.merge(dailyReport);
			em.remove(dailyReport);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete DailyReport(Id = "
					+ dailyReport.getId() + ")", pe);
		}
	}*/

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(List<DailyReport> dailyReportIDList) throws DAOException {

		try {
			logger.debug("delete() method has been started.");
			for (DailyReport dd : dailyReportIDList) {

				Query q = em.createNamedQuery("DailyReport.deleteByDailyReportID");
				q.setParameter("dailyReport_id", dd.getId());
				q.executeUpdate();
				em.flush();
			}

			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean approve(List<DailyReport> dailyReportIDList) throws DAOException {

		try {
			logger.debug("approve() method has been started.");
			for (DailyReport dd : dailyReportIDList) {

				Query q = em.createNamedQuery("DailyReport.approveByDailyReportID");
				q.setParameter("dailyReport_id", dd.getId());
				q.executeUpdate();
				em.flush();
			}

			logger.debug("approve() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("approve() method has been failed.", pe);
			throw translate("Failed to approve all of Report.", pe);
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean disapprove(List<DailyReport> dailyReportIDList) throws DAOException {

		try {
			logger.debug("disapprove() method has been started.");
			for (DailyReport dd : dailyReportIDList) {

				Query q = em.createNamedQuery("DailyReport.disapproveByDailyReportID");
				q.setParameter("dailyReport_id", dd.getId());
				q.executeUpdate();
				em.flush();
			}

			logger.debug("disapprove() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("disapprove() method has been failed.", pe);
			throw translate("Failed to disapprove all of Report.", pe);
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findAll() throws DAOException {
		List<DailyReport> result = null;
		try {
			logger.debug("findAll() method has been started.");
			Query q = em.createNamedQuery("DailyReport.findAll");
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

	/**
	 * Revised By   : Sai Kyaw Ye Myint
	 * Revised Date : 2017/09/08
	 * Explanation  : Added argument for login user info.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> search(DailyReport dailyReport, SearchDate searchDate, User loginUser) throws DAOException {
		List<DailyReport> result = null;

		try {
			logger.debug("search() method has been started.");

			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DailyReport> cquery = builder.createQuery(DailyReport.class);
			Root<DailyReport> root = cquery.from(DailyReport.class);

			Join<DailyReport, User> bJoin = root.join("staffID");
			cquery.select(root);


			List<Predicate> predicateList = new ArrayList<Predicate>();
			Predicate teams,userName, fullName, leaveStatus,approveStatus, date, delFlag;

			// get related teams for PM
			if(dailyReport.getStaffID().getRole()!= Role.ADMIN){
				if(dailyReport.getStaffID().getAuthorities().size() != 0){
					Join<User,Team> dJoin = bJoin.join("teams");

					List<String> teamList = new ArrayList<String> ();
					for (Team team : dailyReport.getStaffID().getAuthorities()) {
						teamList.add(team.getTeamName());
						//System.out.println("Team Name >>>>>>>>>>>>>>>>>>>> "+ team.getTeamName());
					}

					Expression<String> literalTeam = dJoin.get("teamName");
					/**
					 * Revised By   : Sai Kyaw Ye Myint
					 * Revised Date : 2017/09/08
					 * Explanation  : Modify for own data search for user, who have no authorize for own team.
					 */
					//teams = literalTeam.in(teamList);
					teams = builder.or(literalTeam.in(teamList), builder.equal(root.<List<DailyReport>> get("staffID").get("username"), loginUser.getUsername()));
					predicateList.add(teams);
				}
			}


			cquery.orderBy(builder.desc(root.get("activityDate")));

			// for all search by admin
			if (dailyReport.getStaffID().getId() == "" && dailyReport.getStaffID().getUsername() == ""
					&& (dailyReport.getLeaveStatus() == null) && (dailyReport.getCheckDiv() == null) && searchDate.getFromDate() == null
					&& searchDate.getToDate() == null) {
			//	System.out.println("Go to all search list");


				// get last year
				Path<Date> dateEntryPath = root.get("activityDate");
				int year = Calendar.getInstance().get(Calendar.YEAR) - 1;
				Expression<Integer> exp_year = builder.function("year", Integer.class, dateEntryPath);
				// Expression<Integer> diff1 = builder.diff(exp_year, 1);
				date = builder.greaterThanOrEqualTo(exp_year, year);
				predicateList.add(date);

			} else {
				/*if ((dailyReport.getStaffID().getId() != "")) {

					Expression<String> literal = builder.literal("%" + dailyReport.getStaffID().getId() + "%");
					staffID = builder.like(bJoin.get("id").as(String.class), literal);
					predicateList.add(staffID);

				}*/

				if ((dailyReport.getStaffID().getUsername() != "")) {

					Expression<String> literal2 = builder.literal("%" + dailyReport.getStaffID().getUsername() + "%");
					userName = builder.like(bJoin.get("username").as(String.class), literal2);
					predicateList.add(userName);

				}

				if ((dailyReport.getStaffID().getFullName() != "")) {

					Expression<String> literal2 = builder.literal("%" + dailyReport.getStaffID().getFullName() + "%");
					fullName = builder.like(bJoin.get("fullName").as(String.class), literal2);
					predicateList.add(fullName);

				}


				if ((dailyReport.getLeaveStatus() != null) && (!dailyReport.getLeaveStatus().equals(null))) {
					leaveStatus = builder.equal(root.<List<DailyReport>> get("leaveStatus"),
							dailyReport.getLeaveStatus());
					predicateList.add(leaveStatus);
				}

				//for Approve
				if ((dailyReport.getCheckDiv() != null) && (!dailyReport.getCheckDiv().equals(null))) {
					approveStatus = builder.equal(root.<List<DailyReport>> get("checkDiv"),
							dailyReport.getCheckDiv());
					predicateList.add(approveStatus);
				}

				if ((searchDate.getFromDate() != null) || (searchDate.getToDate() != null)) {
					Path<Date> dateEntryPath = root.get("activityDate");
					// do between query
					if ((searchDate.getFromDate() != null) && (searchDate.getToDate() != null)) {
						date = builder.between(dateEntryPath, searchDate.getFromDate(), searchDate.getToDate());

					} else if (searchDate.getFromDate() != null) {
						// for fromDate
						date = builder.greaterThanOrEqualTo(dateEntryPath, searchDate.getFromDate());
					} else {
						// for toDate
						date = builder.lessThanOrEqualTo(dateEntryPath, searchDate.getToDate());
					}
					predicateList.add(date);
				} else {// for limit 1 year

					Path<Date> dateEntryPath = root.get("activityDate");
					int year = Calendar.getInstance().get(Calendar.YEAR) - 1;
					Expression<Integer> exp_year = builder.function("year", Integer.class, dateEntryPath);

					date = builder.greaterThanOrEqualTo(exp_year, year);
					predicateList.add(date);
				}
			}

			// for delete_flag
			delFlag = builder.notEqual(root.get("delDiv"), DeleteDiv.DELETE);
			predicateList.add(delFlag);

			Predicate[] predicateArray = new Predicate[predicateList.size()];
			predicateList.toArray(predicateArray);

			cquery.where(predicateArray).distinct(true);

			/*result = em.createQuery(cquery).setParameter("staffID", "%" + dailyReport.getStaffID().getId() + "%")
					.getResultList();*/

			result = em.createQuery(cquery).getResultList();
			//System.out.println("Getting All list : >>>>>>>>> " + result.size());
			em.flush();
			logger.debug("search() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("search() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> searchAllByAdmin(DailyReport dailyReport) {
		List<DailyReport> result = null;
		try {
			logger.debug("searchAllByAdmin() method has been started.");

			Query q = em.createNamedQuery("DailyReport.searchAllByAdmin");

			int year = Calendar.getInstance().get(Calendar.YEAR);
			q.setParameter("year", year - 1);

			result = q.getResultList();

			em.flush();
			logger.debug("searchAllByAdmin() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAll() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findByDate(Date date, User staff) throws DAOException {
		List<DailyReport> result = null;
		try {
			logger.debug("findByDate() method has been started.");
			/*
			 * dailyReport = em.merge(dailyReport); em.remove(dailyReport);
			 * em.flush();
			 */
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DailyReport> cquery = builder.createQuery(DailyReport.class);
			Root<DailyReport> root = cquery.from(DailyReport.class);
			Join<DailyReport, User> bJoin = root.join("staffID", JoinType.LEFT);
			cquery.select(root);
			// for predicate
			List<Predicate> predicateList = new ArrayList<Predicate>();
			Predicate staffID, activityDate, delFlag;

			// EntityType<DailyReport> type =
			// em.getMetamodel().entity(DailyReport.class);
			if ((staff.getId() != "")) {
				Expression<String> literal = builder.literal(staff.getId());
				staffID = builder.like(bJoin.get("id").as(String.class), literal);
				predicateList.add(staffID);
			}

			if (date != null) {
				Path<Date> dateEntryPath = root.get("activityDate");
				activityDate = builder.equal(dateEntryPath, date);
				predicateList.add(activityDate);
			}

			delFlag = builder.notEqual(root.get("delDiv"), DeleteDiv.DELETE);
			predicateList.add(delFlag);

			Predicate[] predicateArray = new Predicate[predicateList.size()];
			predicateList.toArray(predicateArray);
			cquery.where(predicateArray);
			result = em.createQuery(cquery).setParameter("staffID", staff.getId()).getResultList();
			em.flush();
			logger.debug("findByDate() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByDate() method has been failed.", pe);
			throw translate("Failed to search DailyReport by date(Date = " + date + ")", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findByDateforDuplicate(Date date, User staff) throws DAOException {
		List<DailyReport> result = null;
		try {
			logger.debug("findByDate() method has been started.");
			/*
			 * dailyReport = em.merge(dailyReport); em.remove(dailyReport);
			 * em.flush();
			 */
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DailyReport> cquery = builder.createQuery(DailyReport.class);
			Root<DailyReport> root = cquery.from(DailyReport.class);
			Join<DailyReport, User> bJoin = root.join("staffID", JoinType.LEFT);
			cquery.select(root);
			// for predicate
			List<Predicate> predicateList = new ArrayList<Predicate>();
			Predicate staffID, activityDate, delFlag;

			// EntityType<DailyReport> type =
			// em.getMetamodel().entity(DailyReport.class);
			if ((staff.getId() != "")) {
				Expression<String> literal = builder.literal(staff.getId());
				staffID = builder.like(bJoin.get("id").as(String.class), literal);
				predicateList.add(staffID);
			}

			if (date != null) {
				Path<Date> dateEntryPath = root.get("activityDate");
				activityDate = builder.equal(dateEntryPath, date);
				predicateList.add(activityDate);
			}

			delFlag = builder.equal(root.get("delDiv"), DeleteDiv.DELETE);
			predicateList.add(delFlag);

			Predicate[] predicateArray = new Predicate[predicateList.size()];
			predicateList.toArray(predicateArray);
			cquery.where(predicateArray);
			result = em.createQuery(cquery).setParameter("staffID", staff.getId()).getResultList();
			em.flush();
			logger.debug("findByDate() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByDate() method has been failed.", pe);
			throw translate("Failed to search DailyReport by date(Date = " + date + ")", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findByMonthYear(int month, int year) throws DAOException {
		List<DailyReport> result = null;
		try {
			logger.debug("findByMonthYear() method has been started.");
			Query q = em.createNamedQuery("DailyReport.findByMonthYear");
			q.setParameter("month", month);
			q.setParameter("year", year);
			result = q.getResultList();
			em.flush();
			logger.debug("findByMonthYear() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByMonthYear() method has been failed.", pe);
			throw translate("Failed to find all of Report by Month.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByMonthYearProj(int month, int year, String project) {
		List<Object[]> result = null;
		try {
			logger.debug("findByYearProj() method has been started.");
			// Query q = em.createNamedQuery("DailyReport.findByMonthYear");
			Query q = em.createNativeQuery(
					"SELECT MONTH(d.ACTIVITY_DATE) as months, SUM(a.activity_hours) as total FROM daily_report d,daily_activities a"
							+ " WHERE YEAR(d.activity_date) = ?1 AND MONTH(d.activity_date) = ?2 AND d.del_div != 1"
							+ " AND d.id = a.daily_report_id AND a.project_id = ?3"
							+ " GROUP BY a.project_id");
			q.setParameter(1, year);
			q.setParameter(2, month);
			q.setParameter(3, project);
			result = q.getResultList();
			em.flush();
			logger.debug("findByYearProj() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByYearProj() method has been failed.", pe);
			throw translate("Failed to find all of Report by Month.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findByYearProj(int year, String project) {
		List<Object[]> result = null;
		try {
			logger.debug("findByYearProj() method has been started.");
			// Query q = em.createNamedQuery("DailyReport.findByMonthYear");
			Query q = em.createNativeQuery(
					"SELECT MONTH(d.ACTIVITY_DATE) as months, SUM(a.activity_hours) as total FROM daily_report d,daily_activities a"
							+ " WHERE YEAR(d.activity_date) = ?1 AND d.del_div != 1"
							+ " AND d.id = a.daily_report_id AND a.project_id = ?2"
							+ " GROUP BY MONTH(d.ACTIVITY_DATE)");
			q.setParameter(1, year);
			q.setParameter(2, project);
			result = q.getResultList();
			em.flush();
			logger.debug("findByYearProj() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findByYearProj() method has been failed.", pe);
			throw translate("Failed to find all of Report by Month.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> findReportIDListforExcel(DailyReport dailyReport, SearchDate searchDate) throws DAOException {
		List<DailyReport> result = null;
		try {
			logger.debug("findReportIDListforExcel() method has been started.");
			Query q = em.createNamedQuery("DailyReport.findReportIDforExcel");
			q.setParameter("username", dailyReport.getStaffID().getUsername());
			q.setParameter("fromDate", searchDate.getFromDate());
			q.setParameter("toDate", searchDate.getToDate());
			result = q.getResultList();
			em.flush();
			logger.debug("findReportIDListforExcel() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findReportIDListforExcel() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}
	
	/**
	 * Created By   : Kyi Saw Win
	 * Created Date : 2017/09/12
	 * Explanation  : Get available years from daily_report table.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findActiveYears() throws DAOException{
		List<String> years = null;
		try{
			logger.debug("findActiveYears() method has been started.");
			Query q = em.createNamedQuery("DailyReport.findActiveYear");
			years = q.getResultList();
			em.flush();
			logger.debug("findActiveYears() method has been successfully finisehd.");
		}
		catch (PersistenceException pe) {
			logger.error("findActiveYears() method has been failed.", pe);
			throw translate("Faield to search active years.", pe);
		}
		return years;
	}
	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/09/12
	 * Explanation  : Get daily report data form daily_report table.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<DailyReport> findAllReportByCriteria(DailyReport dailyReport, String year, String month, String day,SearchDate searchDate) throws DAOException {
		List<DailyReport> result=null;
		try {
			logger.debug("findAllReportByCriteria() method has been started.");

			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DailyReport> cquery = builder.createQuery(DailyReport.class);
			Root<DailyReport> root = cquery.from(DailyReport.class);
			Join<DailyReport, User> bJoin = root.join("staffID");
			cquery.select(root);
			List<Predicate> predicateList = new ArrayList<Predicate>();
			Predicate staffid,staffName, y, m, d, approveStatus, date, leave, ymd;

			if(dailyReport.getStaffID() != null){
				if ((dailyReport.getStaffID().getUsername() != null)) {
					Expression<String> tmpStaffId = builder.literal("%" + dailyReport.getStaffID().getUsername() + "%");
					staffid = builder.like(bJoin.get("username").as(String.class), tmpStaffId);
					predicateList.add(staffid);
				}
				
				if ((dailyReport.getStaffID().getFullName() != null)) {
					Expression<String> tmpStaffName = builder.literal("%" + dailyReport.getStaffID().getFullName() + "%");
					staffName = builder.like(bJoin.get("fullName").as(String.class), tmpStaffName);
					predicateList.add(staffName);
				}
			}
			
			if(!year.equals("") && !year.equals(null)){
				
				Expression<String> tmpYear = builder.literal(year + "%");
				y = builder.like(root.get("activityDate").as(String.class), tmpYear);
				predicateList.add(y);
			}
			
			
			if (!month.equals("") && !month.equals(null)) {
				
				if (!day.equals("") && !day.equals(null)) {
					
					ymd = builder.equal(root.get("activityDate"), year + "-" + month + "-" + day);
					predicateList.add(ymd);
				}
				else {
					
					Expression<String> tmpMonth = builder.literal(year + "-" + month + "%");
					m = builder.like(root.get("activityDate").as(String.class),tmpMonth);
					predicateList.add(m);
				}
			}
			else{
				Expression<String> tmpDay = builder.literal(year + "%" + day);
				d = builder.like(root.get("activityDate").as(String.class),
						tmpDay);
				predicateList.add(d);
			}
			
			if ((dailyReport.getLeaveStatus() != null)) {
				leave = builder.equal(root.<List<DailyReport>>get("leaveStatus"), dailyReport.getLeaveStatus());
				predicateList.add(leave);
			}
			
			if ((dailyReport.getCheckDiv() != null)) {
				approveStatus = builder.equal(root.<List<DailyReport>>get("checkDiv"), dailyReport.getCheckDiv());
				predicateList.add(approveStatus);
			}
			
			if ((searchDate.getFromDate() != null) || (searchDate.getToDate() != null)) {
				Path<Date> dateEntryPath = root.get("activityDate");
				// do between query
				if ((searchDate.getFromDate() != null) && (searchDate.getToDate() != null)) {
					date = builder.between(dateEntryPath, searchDate.getFromDate(), searchDate.getToDate());

				} else if (searchDate.getFromDate() != null) {
					// for fromDate
					date = builder.greaterThanOrEqualTo(dateEntryPath, searchDate.getFromDate());
				} else {
					// for toDate
					date = builder.lessThanOrEqualTo(dateEntryPath, searchDate.getToDate());
				}
				predicateList.add(date);
			} else {// for limit 1 year

				Path<Date> dateEntryPath = root.get("activityDate");
				int oneyear = Calendar.getInstance().get(Calendar.YEAR) - 1;
				Expression<Integer> exp_year = builder.function("year", Integer.class, dateEntryPath);

				date = builder.greaterThanOrEqualTo(exp_year, oneyear);
				predicateList.add(date);
			}
			
			Predicate[] predicateArray = new Predicate[predicateList.size()];
			predicateList.toArray(predicateArray);
			
			cquery.where(predicateArray).orderBy(builder.asc(root.get("activityDate")));
			
			result = em.createQuery(cquery).getResultList();
			em.flush();
			logger.debug("findAllReportByCriteria() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAllReportByCriteria() method has been failed.", pe);
			throw translate("Failed to find all of Report by criteria.", pe);
		}
		return result;
	}	
	
	/**
	 * Created By	: Aye Chan Thar Soe, Htet Wai Yum
	 * Created Date	: 2018/09/04
	 * Explanation	: Get daily report register user list from daily_report table.
	 * */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findDailyReportRegistrationStatusByMonthYear(
			int year, int month, User user) {
		List<Object[]> reports = null;
		String query = null;
		Query q = null;
		try {
			logger.debug("findDailyReportRegistrationStatusByMonthYear() method has been started.");
			if (user.getRole() == Role.ADMIN) {
					query = "SELECT u.username,u.fullname,t.team_name,dr.total_hours,dr.activity_date,CAST(dr.leave_status AS CHAR) "
							+ " FROM users u, user_team_mainly_belong utm,team t,daily_report dr "
							+ " WHERE u.id = utm.user_id AND utm.team_id = t.team_id AND dr.del_div = ?3 "
							+ " AND dr.staff_id = u.id AND (YEAR(dr.activity_date) = ?1 AND MONTH(dr.activity_date) = ?2 ) "
							+ " ORDER BY u.username";
					q = em.createNativeQuery(query);
				} else if (user.getRole() == Role.MANAGEMENT) {
					query = "SELECT u.username,u.fullname,t.team_name,dr.total_hours,dr.activity_date,CAST(dr.leave_status AS CHAR)"
							+ " FROM users u, user_team_mainly_belong utm,team t,daily_report dr,user_team_authority uta "
							+ " WHERE u.id = utm.user_id AND utm.team_id = t.team_id AND dr.del_div = ?3 "
							+ " AND dr.staff_id = u.id  AND uta.team_id = t.team_id AND uta.user_id = ?4 "
							+ " AND (YEAR(dr.activity_date) = ?1 AND MONTH(dr.activity_date) = ?2 ) "
							+ " ORDER BY u.username ";
					q = em.createNativeQuery(query);
					q.setParameter(4, user.getId());
				}
		//	}

			q.setParameter(1, year);
			q.setParameter(2, month);
			q.setParameter(3, com.dat.whm.common.entity.DeleteDiv.ACTIVE);

			reports = q.getResultList();
			em.flush();
			logger.debug("findDailyReportRegistrationStatusByMonthYear() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error(
					"findDailyReportRegistrationStatusByMonthYear() method has been failed.",
					pe);
			throw translate(
					"Faield to search Daily Reports by Month and Year.", pe);
		}
		return reports;
	}
	 /**
	 * Explanation	: Get daily report register user list from daily_report table.
	 * End.
	 */
	
	
	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Get daily report data by year/month from daily_report table.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyReport> searchReportByCriteria(DailyReport dailyReport, String year , String month, User loginUser) throws DAOException {
		List<DailyReport> result = null;

		try {
			logger.debug("searchByCriteria() method has been started.");

			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DailyReport> cquery = builder.createQuery(DailyReport.class);
			Root<DailyReport> root = cquery.from(DailyReport.class);
			Join<DailyReport, User> bJoin = root.join("staffID");
			cquery.select(root);
			List<Predicate> predicateList = new ArrayList<Predicate>();
			Predicate teams,staffid,staffName, y, m;
			
			// get related teams for PM
						if(dailyReport.getStaffID().getRole()!= Role.ADMIN){
							if(dailyReport.getStaffID().getAuthorities().size() != 0){
								Join<User,Team> dJoin = bJoin.join("teams");

								List<String> teamList = new ArrayList<String> ();
								for (Team team : dailyReport.getStaffID().getAuthorities()) {
									teamList.add(team.getTeamName());
									//System.out.println("Team Name >>>>>>>>>>>>>>>>>>>> "+ team.getTeamName());
								}

								Expression<String> literalTeam = dJoin.get("teamName");
							
								//teams = literalTeam.in(teamList);
								teams = builder.or(literalTeam.in(teamList), builder.equal(root.<List<DailyReport>> get("staffID").get("username"), loginUser.getUsername()));
								predicateList.add(teams);
							}
						}


			if(dailyReport.getStaffID() != null){
				if ((dailyReport.getStaffID().getUsername() != null)) {
					Expression<String> tmpStaffId = builder.literal("%" + dailyReport.getStaffID().getUsername() + "%");
					staffid = builder.like(bJoin.get("username").as(String.class), tmpStaffId);
					predicateList.add(staffid);
				}
				
				if ((dailyReport.getStaffID().getFullName() != null)) {
					Expression<String> tmpStaffName = builder.literal("%" + dailyReport.getStaffID().getFullName() + "%");
					staffName = builder.like(bJoin.get("fullName").as(String.class), tmpStaffName);
					predicateList.add(staffName);
				}
			}
			
			if(!year.equals("") && !year.equals(null)){
				
				Expression<String> tmpYear = builder.literal(year + "%");
				y = builder.like(root.get("activityDate").as(String.class), tmpYear);
				predicateList.add(y);
			}
			
			
			if (!month.equals("") && !month.equals(null)) {
				
				
					
					Expression<String> tmpMonth = builder.literal(year + "-" + month + "%");
					m = builder.like(root.get("activityDate").as(String.class),tmpMonth);
					predicateList.add(m);
				
			}

			Predicate[] predicateArray = new Predicate[predicateList.size()];
			predicateList.toArray(predicateArray);

			cquery.where(predicateArray).distinct(true);

			result = em.createQuery(cquery).getResultList();
			em.flush();
			logger.debug("searchByCriteria() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("searchByCriteria() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}
	 /**
	 * Explanation  : Get daily report data by year/month from daily_report table.
	 * End.
	 */
	
}