/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.approval.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.common.entity.BaseEntity;
import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.dailyreport.entity.DailyReport;
import com.dat.whm.user.entity.User;

@Table(name = "APPROVAL")
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Approval.findApprovalByReportID", query = "SELECT ap FROM Approval ap WHERE ap.dailyReport.id = :dailyReport_id")})
public class Approval extends BaseEntity implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DAILY_REPORT_ID")
	@NotNull
	private DailyReport dailyReport;
	@JoinColumn(name = "APPROVER_ID")
	@NotNull
	private User approver;
	@Column(name = "LAST_UPD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdDate;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "DEL_DIV")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private DeleteDiv delDiv;
	@Version
	private int version;

	public Approval() {
	}

	public DailyReport getDailyReport() {
		return dailyReport;
	}

	public void setDailyReport(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DeleteDiv getDelDiv() {
		return delDiv;
	}

	public void setDelDiv(DeleteDiv delDiv) {
		this.delDiv = delDiv;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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