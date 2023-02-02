/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.approval.persistence.interfaces;

import java.util.List;

import com.dat.whm.exception.DAOException;
import com.dat.whm.approval.entity.Approval;
import com.dat.whm.dailyreport.entity.DailyReport;

public interface IApprovalDAO {
	public Approval insert(Approval Approval) throws DAOException;

	public Approval update(Approval Approval) throws DAOException;

	public void delete(Approval Approval) throws DAOException;
	
	public List<Approval> findApprovalByReportID(DailyReport dailyReport) throws DAOException;
}