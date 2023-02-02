/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.approval.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dat.whm.exception.DAOException;
import com.dat.whm.exception.SystemException;
import com.dat.whm.approval.persistence.interfaces.IApprovalDAO;
import com.dat.whm.approval.service.interfaces.IApprovalService;
import com.dat.whm.approval.entity.Approval;
import com.dat.whm.dailyreport.entity.DailyReport;

@Service("ApprovalService")
public class ApprovalService implements IApprovalService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "ApprovalDAO")
	private IApprovalDAO approvalDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public Approval addNewApproval(Approval approval) throws SystemException {
		try {
			logger.debug("addApproval() method has been started.");
			approval = approvalDAO.insert(approval);
			logger.debug("addApproval() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("addNewApproval() method has been failed.");
			throw new SystemException(e.getErrorCode(), "Faield to add a Approval", e);
		}
		return approval;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Approval updateApproval(Approval approval) throws SystemException {
		try {
			logger.debug("updateApproval() method has been started.");
			approval = approvalDAO.update(approval);
			logger.debug("updateApproval() method has been successfully finisehd.");
		} catch (DAOException e) {
			logger.error("updateApproval() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to update Approval", e);
		}
		return approval;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteApproval(Approval approval) throws SystemException {
		try {
			logger.debug("deleteApproval() method has been started.");
			approvalDAO.delete(approval);
			logger.debug("deleteApproval() method has been finished.");
		} catch (DAOException e) {
			logger.error("deleteApproval() method has been failed.");
			throw new SystemException(e.getErrorCode(),
					"Faield to delete Approval", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Approval> findApprovalByReportID(DailyReport dailyReport) throws SystemException {
		logger.debug("findApprovalByReportID() method has been started.");
		List<Approval> result = approvalDAO.findApprovalByReportID(dailyReport);
		logger.debug("findApprovalByReportID() method has been finished.");
		return result;
	}
}