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
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.exception.DAOException;
import com.dat.whm.persistence.BasicDAO;
import com.dat.whm.dailyactivity.entity.DailyActivity;
import com.dat.whm.dailyactivity.entity.DailyActivityTest;
import com.dat.whm.dailyactivity.persistence.interfaces.IDailyActivityTestDAO;
@Repository("DailyActivityTestDAO")
public class DailyActivityTestDAO extends BasicDAO implements IDailyActivityTestDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public DailyActivityTest insert(DailyActivityTest dailyActivity) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(dailyActivity);
			em.flush();
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
	public DailyActivityTest update(DailyActivityTest DailyActivityTest) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			DailyActivityTest = em.merge(DailyActivityTest);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update DailyActivityTest(Description = "
					+ DailyActivityTest.getTaskDescription() + ")", pe);
		}
		return DailyActivityTest;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(DailyActivityTest DailyActivityTest) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			DailyActivityTest = em.merge(DailyActivityTest);
			em.remove(DailyActivityTest);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete DailyActivityTest(Description = "
					+ DailyActivityTest.getTaskDescription() + ")", pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyActivityTest> findActivityByReportID(DailyReport dailyReport) throws DAOException {
		List<DailyActivityTest> result = null;
		try {
			logger.debug("findActivityByReportID() method has been started.");
			Query q = em.createNamedQuery("DailyActivityTest.findActivityByReportID");
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
			Query q = em.createNamedQuery("DailyActivityTest.deleteActivityByReportID");
			q.setParameter("dailyReport_id", dailyReport);
			q.executeUpdate();
			em.flush();
			logger.debug("deleteByReportID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("deleteByReportID() method has been failed.", pe);
			throw translate("Failed to delete DailyActivityTest", pe);
		}

	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<DailyActivityTest> findAllActivitiesByCriteria(
			DailyReport dailyReport, DailyActivityTest DailyActivityTest)
			throws DAOException {
		List<DailyActivityTest> result = null;

		try {
			logger.debug("findAllActivitiesByCriteria() method has been started.");
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DailyActivityTest> cquery = builder
					.createQuery(DailyActivityTest.class);
			Root<DailyActivityTest> root = cquery.from(DailyActivityTest.class);
			cquery.select(root);
			List<Predicate> predicateList = new ArrayList<Predicate>();
			Predicate dailyRpt, project, projectID, task, taskActivity, wbsNo, functionId, tActivity, tDescription, deliveryOutput, beginDate, endDate, progressPercentage, activityHours, taskStatus, taskStatusCount, reason;

			if (dailyReport !=null && DailyActivityTest!=null) {
				dailyRpt = builder.equal(
						root.<List<DailyActivityTest>> get("dailyReport"),
						dailyReport);
				predicateList.add(dailyRpt);
			}

			if (DailyActivityTest.getProject()!=null) {
				project = builder.equal(
						root.<List<DailyActivityTest>> get("project"),
						DailyActivityTest.getProject());
				predicateList.add(project);
			}
			else{
				projectID = builder.isNull((root.<List<DailyActivityTest>> get("project").as(String.class)));
				predicateList.add(projectID);
			}
			
			if (DailyActivityTest.getTask()!=null) {
				task = builder.equal(root.<List<DailyActivityTest>> get("task"),
						DailyActivityTest.getTask());
				predicateList.add(task);
			}

			if (DailyActivityTest.getTaskActivity()!=null) {
				taskActivity = builder.equal(
						root.<List<DailyActivityTest>> get("taskActivity"),
						DailyActivityTest.getTaskActivity());
				predicateList.add(taskActivity);
			}
			

			if ((DailyActivityTest.getWbs() != null)) {
				if (!DailyActivityTest.getWbs().trim().equals("")
						&& !DailyActivityTest.getWbs().equals(null)) {

					Expression<String> tmpWbs = builder.literal("%"
							+ DailyActivityTest.getWbs() + "%");
					wbsNo = builder.like(root.get("wbs").as(String.class),
							tmpWbs);
					predicateList.add(wbsNo);

				}
			}
			
			if (DailyActivityTest.getWbsFunction() != null) {
				if (!DailyActivityTest.getWbsFunction().trim().equals("")
						&& !DailyActivityTest.getWbsFunction().equals(null)) {
					Expression<String> tmpWbsFunction = builder.literal("%"
							+ DailyActivityTest.getWbsFunction() + "%");
					functionId = builder.like(
							root.get("wbsFunction").as(String.class),
							tmpWbsFunction);
					predicateList.add(functionId);
				}}

			if (DailyActivityTest.getTaskDescription() != null) {
				if (!DailyActivityTest.getTaskDescription().trim().equals("")
						&& !DailyActivityTest.getTaskDescription().equals(null)) {
					Expression<String> tmpDescription = builder.literal("%"
							+ DailyActivityTest.getTaskDescription() + "%");
					tDescription = builder.like(
							root.get("taskDescription").as(String.class),
							tmpDescription);
					predicateList.add(tDescription);
				}}

			if (DailyActivityTest.getBeginDate() !=null) {
				beginDate = builder.equal(
						root.<List<DailyActivityTest>> get("beginDate"),
						DailyActivityTest.getBeginDate());
				predicateList.add(beginDate);
			}

			if (DailyActivityTest.getEndDate() !=null) {
				endDate = builder.equal(
						root.<List<DailyActivityTest>> get("endDate"),
						DailyActivityTest.getEndDate());
				predicateList.add(endDate);
			}

			if (DailyActivityTest.getTaskStatus() !=null) {
				taskStatus = builder.equal(
						root.<List<DailyActivityTest>> get("taskStatus"),
						DailyActivityTest.getTaskStatus());
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Date findActivityDateByProjectID(String projectId) throws DAOException {
		Date result = null;
		try {
			logger.debug("findActivityDateByProjectID() method has been started.");
			Query q = em.createNamedQuery("DailyActivityTest.findActivityDateByProjectID");
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
			Query q = em.createNamedQuery("DailyActivityTest.findMinDateByProjectID");
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
			Query q = em.createNamedQuery("DailyActivityTest.findMaxDateByProjectID");
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
			Query q = em.createNamedQuery("DailyActivityTest.findSumOfActivityHrByProjectID");
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
			
			Query q = em.createNamedQuery("DailyActivityTest.countActivityDateByProjectID");
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