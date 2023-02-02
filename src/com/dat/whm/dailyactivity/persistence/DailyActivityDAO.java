/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.dailyactivity.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.persistence.interfaces.IDailyActivityDAO;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;

@Repository("DailyActivityDAO")
public class DailyActivityDAO extends BasicDAO implements IDailyActivityDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public DailyActivity insert(DailyActivity dailyActivity) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(dailyActivity);
			//em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert DailyActivity(Description = "
					+ dailyActivity.getTaskDescription() + ")", pe);
		}
		return dailyActivity;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public DailyActivity update(DailyActivity dailyActivity) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			dailyActivity = em.merge(dailyActivity);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update DailyActivity(Description = "
					+ dailyActivity.getTaskDescription() + ")", pe);
		}
		return dailyActivity;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(DailyActivity dailyActivity) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			dailyActivity = em.merge(dailyActivity);
			em.remove(dailyActivity);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete DailyActivity(Description = "
					+ dailyActivity.getTaskDescription() + ")", pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyActivity> findActivityByReportID(DailyReport dailyReport) throws DAOException {
		List<DailyActivity> result = null;
		try {
			logger.debug("findActivityByReportID() method has been started.");
			Query q = em.createNamedQuery("DailyActivity.findActivityByReportID");
			q.setParameter("dailyReport_id", dailyReport.getId());
			result = q.getResultList();

			em.flush();
			logger.debug("findActivityByReportID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findActivityByReportID() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteByReportID(DailyReport dailyReport) throws DAOException {
		try {
			logger.debug("deleteByReportID() method has been started.");
			Query q = em.createNamedQuery("DailyActivity.deleteActivityByReportID");
			q.setParameter("dailyReport_id", dailyReport);
			q.executeUpdate();
			em.flush();
			logger.debug("deleteByReportID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("deleteByReportID() method has been failed.", pe);
			throw translate("Failed to delete DailyActivity", pe);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyActivity> findAllActivitiesByCriteria(
			DailyReport dailyReport, DailyActivity dailyActivity)
			throws DAOException {
		List<DailyActivity> result = null;

		try {
			logger.debug("findAllActivitiesByCriteria() method has been started.");
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DailyActivity> cquery = builder
					.createQuery(DailyActivity.class);
			Root<DailyActivity> root = cquery.from(DailyActivity.class);
			cquery.select(root);
			List<Predicate> predicateList = new ArrayList<Predicate>();
			Predicate dailyRpt, project, projectID, task, taskActivity, wbsNo, functionId, tActivity, tDescription, deliveryOutput, beginDate, endDate, progressPercentage, activityHours, taskStatus, taskStatusCount, reason;

			if (dailyReport !=null && dailyActivity!=null) {
				dailyRpt = builder.equal(
						root.<List<DailyActivity>> get("dailyReport"),
						dailyReport);
				predicateList.add(dailyRpt);
			}

			if (dailyActivity.getProject()!=null) {
				project = builder.equal(
						root.<List<DailyActivity>> get("project"),
						dailyActivity.getProject());
				predicateList.add(project);
			}
			else{
				projectID = builder.isNull((root.<List<DailyActivity>> get("project").as(String.class)));
				predicateList.add(projectID);
			}
			
			if (dailyActivity.getTask()!=null) {
				task = builder.equal(root.<List<DailyActivity>> get("task"),
						dailyActivity.getTask());
				predicateList.add(task);
			}

			if (dailyActivity.getTaskActivity()!=null) {
				taskActivity = builder.equal(
						root.<List<DailyActivity>> get("taskActivity"),
						dailyActivity.getTaskActivity());
				predicateList.add(taskActivity);
			}
			

			if ((dailyActivity.getWbs() != null)) {
				if (!dailyActivity.getWbs().trim().equals("")
						&& !dailyActivity.getWbs().equals(null)) {

					Expression<String> tmpWbs = builder.literal("%"
							+ dailyActivity.getWbs() + "%");
					wbsNo = builder.like(root.get("wbs").as(String.class),
							tmpWbs);
					predicateList.add(wbsNo);

				}
			}
			
			if (dailyActivity.getWbsFunction() != null) {
				if (!dailyActivity.getWbsFunction().trim().equals("")
						&& !dailyActivity.getWbsFunction().equals(null)) {
					Expression<String> tmpWbsFunction = builder.literal("%"
							+ dailyActivity.getWbsFunction() + "%");
					functionId = builder.like(
							root.get("wbsFunction").as(String.class),
							tmpWbsFunction);
					predicateList.add(functionId);
				}}

			if (dailyActivity.getTaskDescription() != null) {
				if (!dailyActivity.getTaskDescription().trim().equals("")
						&& !dailyActivity.getTaskDescription().equals(null)) {
					Expression<String> tmpDescription = builder.literal("%"
							+ dailyActivity.getTaskDescription() + "%");
					tDescription = builder.like(
							root.get("taskDescription").as(String.class),
							tmpDescription);
					predicateList.add(tDescription);
				}}

			if (dailyActivity.getBeginDate() !=null) {
				beginDate = builder.equal(
						root.<List<DailyActivity>> get("beginDate"),
						dailyActivity.getBeginDate());
				predicateList.add(beginDate);
			}

			if (dailyActivity.getEndDate() !=null) {
				endDate = builder.equal(
						root.<List<DailyActivity>> get("endDate"),
						dailyActivity.getEndDate());
				predicateList.add(endDate);
			}

			if (dailyActivity.getTaskStatus() !=null) {
				taskStatus = builder.equal(
						root.<List<DailyActivity>> get("taskStatus"),
						dailyActivity.getTaskStatus());
				predicateList.add(taskStatus);
			}

			Predicate[] predicateArray = new Predicate[predicateList.size()];
			predicateList.toArray(predicateArray);

			cquery.where(predicateArray);
			result = em.createQuery(cquery).getResultList();
			em.flush();
			logger.debug("findAllActivitiesByCriteria() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findAllActivitiesByCriteria() method has been failed.", pe);
			throw translate("Failed to find all of Activities by criteria.", pe);
		}
		return result;
	}

	/**
	 * Created By   : Ye Thu Naing
	 * Created Date : 2017/11/02
	 * Explanation  : Get maximum date according to selected project_id.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findActivityDateByProjectID(String projectId) throws DAOException {
		Date result = null;
		try {
			logger.debug("findActivityDateByProjectID() method has been started.");
			Query q = em.createNamedQuery("DailyActivity.findActivityDateByProjectID");
			q.setParameter("id", projectId);
			result = (Date) q.getSingleResult();
			em.flush();
			logger.debug("findActivityDateByProjectID method has been successfully finished.");
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			logger.error("findActivityDateByProjectID method has been failed.", pe);
			throw translate("Failed to find ActivityDate By Project(projectId = " + projectId + ")",
					pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMinDateByProjectID(String projectId) throws DAOException {
		Date result = new Date();
		try {
			logger.debug("findMinDateByProjectID() method has been started.");
			Query q = em.createNamedQuery("DailyActivity.findMinDateByProjectID");
			q.setParameter("id", projectId);
			result = (Date) q.getSingleResult();
			em.flush();
			logger.debug("findMinDateByProjectID method has been successfully finished.");
		} catch (NoResultException e) {
			result = new Date();
		} catch (PersistenceException pe) {
			logger.error("findMinDateByProjectID method has been failed.", pe);
			throw translate("Failed to find min date for Project(projectId = " + projectId + ")",
					pe);
		}
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findMaxDateByProjectID(String projectId) throws DAOException {
		Date result = new Date();
		try {
			logger.debug("findMaxDateByProjectID() method has been started.");
			Query q = em.createNamedQuery("DailyActivity.findMaxDateByProjectID");
			q.setParameter("id", projectId);
			result = (Date) q.getSingleResult();
			em.flush();
			logger.debug("findMaxDateByProjectID method has been successfully finished.");
		} catch (NoResultException e) {
			result = new Date();
		} catch (PersistenceException pe) {
			logger.error("findMaxDateByProjectID method has been failed.", pe);
			throw translate("Failed to find max date for Project(projectId = " + projectId + ")",
					pe);
		}
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public double findSumOfActivityHrByProjectID(String projectId) throws DAOException {
		double result = 0.0;
		try {
			logger.debug("findSumOfActivityHrByProjectID() method has been started.");
			Query q = em.createNamedQuery("DailyActivity.findSumOfActivityHrByProjectID");
			q.setParameter("id", projectId);
			if (q.getSingleResult() != null) {
				result =  (Double) q.getSingleResult();
			}
			em.flush();
			logger.debug("findSumOfActivityHrByProjectID method has been successfully finished.");
		} catch (NoResultException e) {
			result = 0.0;
		} catch (PersistenceException pe) {
			logger.error("findSumOfActivityHrByProjectID method has been failed.", pe);
			throw translate("Failed to find SumOfActivityHrByProjectID for Project(projectId = " + projectId + ")",
					pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int countActivityDateByProjectID(String projectId) throws DAOException{
		int result = 0;
		try{
			logger.debug("countActivityDateByProjectID() method has been started.");
			
			Query q = em.createNamedQuery("DailyActivity.countActivityDateByProjectID");
			q.setParameter("id", projectId);
			result = (int) ((Number) q.getSingleResult()).intValue();
			em.flush();
			logger.debug("countActivityDateByProjectID() method has been finished.");
		}catch (PersistenceException pe) {
			logger.error("countActivityDateByProjectID() method has been fail.", pe);
			throw translate("Failed to countActivityDateByProjectID", pe);
		}
		
		return result;
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Object[]> findReportByMonthly(String projectid,String staffid,String reportdate,int team_id) throws DAOException {
		List<Object[]> result = null;
		

		try {
			logger.debug("findyReportByMonthly() method has been started.");
			if(projectid != null){
			Query q = em
					.createNativeQuery("SELECT da.project_id,dr.staff_id,MIN(dr.activity_date),utmbh.team_id FROM daily_activities da "
									 + " INNER JOIN daily_report dr ON da.DAILY_REPORT_ID = dr.id inner join users u on u.ID=dr.STAFF_ID "
									 + " INNER JOIN (SELECT user_id,team_id,start_date,IFNULL(end_date,CURDATE())end_date FROM user_team_mainly_belong_history "
									 + " WHERE user_id = ?2) utmbh ON utmbh.USER_ID = dr.staff_id AND dr.ACTIVITY_DATE BETWEEN utmbh.START_DATE AND utmbh.END_DATE "
									 + " INNER JOIN team t ON t.TEAM_ID = utmbh.team_id WHERE da.project_id = ?1 AND "
									 + " (dr.LEAVE_STATUS = 0 OR (dr.LEAVE_STATUS = 2 AND da.TASK_DESCRIPTION <> 'Half Leave')) "
									 + " and dr.del_div=0 AND EXTRACT(YEAR_MONTH FROM dr.activity_date) = ?3 AND t.TEAM_ID =?4 GROUP BY da.project_id, "
									 + " dr.STAFF_ID, utmbh.TEAM_ID, EXTRACT(YEAR_MONTH FROM dr.activity_date) ORDER BY dr.activity_date, da.project_id");

			q.setParameter(1, projectid);
			q.setParameter(2, staffid);
			q.setParameter(3, reportdate);
			q.setParameter(4, team_id);
			System.out.println(q.toString());
			result = q.getResultList();
			}else{
				Query q = em
						.createNativeQuery("SELECT da.project_id,dr.staff_id,MIN(dr.activity_date),utmbh.team_id FROM daily_activities da "
									 + " INNER JOIN daily_report dr ON da.DAILY_REPORT_ID = dr.id inner join users u on u.ID=dr.STAFF_ID "
									 + " INNER JOIN (SELECT user_id,team_id,start_date,IFNULL(end_date,CURDATE())end_date FROM user_team_mainly_belong_history "
									 + " WHERE user_id = ?2) utmbh ON utmbh.USER_ID = dr.staff_id AND dr.ACTIVITY_DATE BETWEEN utmbh.START_DATE AND utmbh.END_DATE "
									 + " INNER JOIN team t ON t.TEAM_ID = utmbh.team_id WHERE da.project_id is NULL AND "
									 + " (dr.LEAVE_STATUS = 0 OR (dr.LEAVE_STATUS = 2 AND da.TASK_DESCRIPTION <> 'Half Leave')) "
									 + " and dr.del_div=0 AND EXTRACT(YEAR_MONTH FROM dr.activity_date) = ?3 AND t.TEAM_ID =?4 GROUP BY da.project_id, "
									 + " dr.STAFF_ID, utmbh.TEAM_ID, EXTRACT(YEAR_MONTH FROM dr.activity_date) ORDER BY dr.activity_date, da.project_id");

			
				q.setParameter(2, staffid);
				q.setParameter(3, reportdate);
				q.setParameter(4, team_id);
				System.out.println(q.toString());
				result = q.getResultList();	
			}
			
			em.flush();
			
			logger.debug("findyReportByMonthly() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findyReportByMonthly() method has been failed.", pe);
			throw translate("Failed to find all of Report.", pe);
		}
		return result;
	}
}