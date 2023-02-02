/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
/*
 * Revised By   : Kyi Saw Win
 * Revised Date : 2017-09-12
 * Explanation  : Added DailyReport.findActiveYear.
 */
package com.dat.whm.dailyreport.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.common.entity.BaseEntity;
import com.dat.whm.common.entity.CheckDiv;
import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.common.entity.LeaveStatus;
import com.dat.whm.user.entity.User;

@Table(name = "DAILY_REPORT",uniqueConstraints={
	    @UniqueConstraint(columnNames = {"STAFF_ID", "ACTIVITY_DATE","DEL_DIV"})
	})
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "DailyReport.findAll", query = "SELECT d FROM DailyReport d WHERE d.delDiv != com.dat.whm.common.entity.DeleteDiv.DELETE"),
		@NamedQuery(name = "DailyReport.findByStaffID", query = "SELECT d FROM DailyReport d WHERE d.staffID = :staff_id"),
		@NamedQuery(name = "DailyReport.findByMonthYear", query = "SELECT d FROM DailyReport d WHERE FUNCTION('MONTH',d.activityDate) = :month AND FUNCTION('YEAR', d.activityDate) = :year AND d.delDiv != com.dat.whm.common.entity.DeleteDiv.DELETE"),
		@NamedQuery(name = "DailyReport.deleteByDailyReportID", query = "UPDATE DailyReport d SET d.delDiv = com.dat.whm.common.entity.DeleteDiv.DELETE WHERE"
				+ " d.id = :dailyReport_id"),
		@NamedQuery(name = "DailyReport.approveByDailyReportID", query = "UPDATE DailyReport d SET d.checkDiv = com.dat.whm.common.entity.CheckDiv.CHECKED WHERE"
				+ " d.id = :dailyReport_id"),
		@NamedQuery(name = "DailyReport.disapproveByDailyReportID", query = "UPDATE DailyReport d SET d.checkDiv = com.dat.whm.common.entity.CheckDiv.UNCHECKED WHERE"
				+ " d.id = :dailyReport_id"),
		@NamedQuery(name = "DailyReport.searchAllByAdmin", query = "SELECT d FROM DailyReport d WHERE FUNCTION('YEAR', d.activityDate) > :year "
				+ "AND d.delDiv != com.dat.whm.common.entity.DeleteDiv.DELETE ORDER BY d.staffID DESC"),
		@NamedQuery(name = "DailyReport.findReportIDforExcel", query = "SELECT d FROM DailyReport d WHERE "
				+ "d.staffID.username = :username "
				+ "AND d.activityDate BETWEEN :fromDate AND :toDate ORDER BY d.activityDate"),
		@NamedQuery(name = "DailyReport.findActiveYear", query = "SELECT DISTINCT FUNC('YEAR', d.activityDate) FROM DailyReport d ORDER BY d.activityDate ASC")})
public class DailyReport extends BaseEntity implements Serializable {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STAFF_ID")
	@NotNull
	private User staffID;
	@Column(name = "ACTIVITY_DATE")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date activityDate;
	@Column(name = "LEAVE_STATUS")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private LeaveStatus leaveStatus;
	@JoinColumn(name = "CREATED_BY")
	@NotNull
	private User createdBy;
	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date createdDate;
	@JoinColumn(name = "LAST_UPD_BY")
	private User lastUpdBy;
	@Column(name = "LAST_UPD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdDate;
	@Column(name = "DEL_DIV")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private DeleteDiv delDiv;
	@Column(name = "CHECK_DIV")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private CheckDiv checkDiv;

	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Add total_hours column.
	 */
	@Column(name = "TOTAL_HOURS")
	@NotNull
	private Double totalhours;
	
	@Version
	private int version;

	public DailyReport() {
	}

	public User getStaffID() {
		return staffID;
	}

	public void setStaffID(User staffID) {
		this.staffID = staffID;
	}


	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public LeaveStatus getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(LeaveStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(User lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public DeleteDiv getDelDiv() {
		return delDiv;
	}

	public void setDelDiv(DeleteDiv delDiv) {
		this.delDiv = delDiv;
	}

	public CheckDiv getCheckDiv() {
		return checkDiv;
	}

	public void setCheckDiv(CheckDiv checkDiv) {
		this.checkDiv = checkDiv;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	/**
	 * Created By   : Htet Wai Yum
	 * Created Date : 2018/08/24
	 * Explanation  : Add Getter/Setter for total_hours.
	 */

	public Double getTotalhours() {
		return totalhours;
	}

	public void setTotalhours(Double totalhours) {
		this.totalhours = totalhours;
	}


	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}