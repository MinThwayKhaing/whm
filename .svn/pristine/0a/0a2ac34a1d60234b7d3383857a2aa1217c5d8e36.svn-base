/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.approval.service.interfaces;

import java.util.List;

import com.dat.whm.exception.SystemException;
import com.dat.whm.approval.entity.Approval;
import com.dat.whm.dailyreport.entity.DailyReport;

public interface IApprovalService {

	public Approval addNewApproval(Approval Approval) throws SystemException;

	public Approval updateApproval(Approval Approval) throws SystemException;

	public void deleteApproval(Approval Approval) throws SystemException;

	public List<Approval> findApprovalByReportID(DailyReport dailyReport) throws SystemException;
}