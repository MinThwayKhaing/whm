/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.approval.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.approval.persistence.interfaces.IApprovalDAO;
import com.dat.whm.approval.entity.Approval;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.persistence.BasicDAO;

@Repository("ApprovalDAO")
public class ApprovalDAO extends BasicDAO implements IApprovalDAO {
	private Logger logger = Logger.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public Approval insert(Approval approval) throws DAOException {
		try {
			logger.debug("insert() method has been started.");
			em.persist(approval);
			em.flush();
			logger.debug("insert() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("insert() method has been failed.", pe);
			throw translate("Failed to insert Approval(Id = "
					+ approval.getId() + ")", pe);
		}
		return approval;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Approval update(Approval approval) throws DAOException {
		try {
			logger.debug("update() method has been started.");
			approval = em.merge(approval);
			em.flush();
			logger.debug("update() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("update() method has been failed.", pe);
			throw translate("Failed to update Approval(Id = "
					+ approval.getId() + ")", pe);
		}
		return approval;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Approval approval) throws DAOException {
		try {
			logger.debug("delete() method has been started.");
			approval = em.merge(approval);
			em.remove(approval);
			em.flush();
			logger.debug("delete() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("delete() method has been failed.", pe);
			throw translate("Failed to delete Approval(Id = "
					+ approval.getId() + ")", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Approval> findApprovalByReportID(DailyReport dailyReport) throws DAOException {
		List<Approval> result = null;
		try {
			logger.debug("findApprovalByReportID() method has been started.");
			Query q = em.createNamedQuery("Approval.findApprovalByReportID");
			q.setParameter("dailyReport_id", dailyReport.getId());
			result = q.getResultList();

			em.flush();
			logger.debug("findApprovalByReportID() method has been successfully finisehd.");
		} catch (PersistenceException pe) {
			logger.error("findApprovalByReportID() method has been failed.", pe);
			throw translate("Failed to find all of Approval.", pe);
		}
		return result;
	}
}