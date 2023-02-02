/***************************************************************************************
 * @author Soe Thiha Lwin
 * @Date 2018-09-24
 * @Version 1.0
 * @Purpose WorkingHour Entity.
 *
 ***************************************************************************************/
package com.dat.whm.workinghour.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity()
@Table(name = "WORKING_HOUR_INFO")
@NamedQueries(value = {
		@NamedQuery(name = "WorkingHour.findAll", query = "SELECT wh FROM WorkingHour wh ORDER BY wh.startDate"),
		@NamedQuery(name = "WorkingHour.findByStartDate", query = "SELECT wh FROM WorkingHour wh WHERE :startDate BETWEEN wh.startDate AND wh.endDate"),
		@NamedQuery(name = "WorkingHour.findByEndDate", query = "SELECT wh FROM WorkingHour wh WHERE :endDate BETWEEN wh.startDate AND wh.endDate"),
		@NamedQuery(name = "WorkingHour.findByStartDateUpdate", query = "SELECT wh FROM WorkingHour wh WHERE :startDate BETWEEN wh.startDate AND wh.endDate AND wh.id <> :id"),
		@NamedQuery(name = "WorkingHour.findByEndDateUpdate", query = "SELECT wh FROM WorkingHour wh WHERE :endDate BETWEEN wh.startDate AND wh.endDate AND wh.id <> :id"),
		@NamedQuery(name = "WorkingHour.findDurationPeriod", query = "SELECT wh FROM WorkingHour wh WHERE wh.startDate BETWEEN :startDate AND :endDate OR wh.endDate BETWEEN :startDate AND :endDate"),
		@NamedQuery(name = "WorkingHour.findDurationPeriodUpdate", query = "SELECT wh FROM WorkingHour wh WHERE wh.startDate BETWEEN :startDate AND :endDate AND wh.id <> :id OR wh.endDate BETWEEN :startDate AND :endDate AND wh.id <> :id"),
		@NamedQuery(name = "WorkingHour.findByDate", query = "SELECT wh.workingHour FROM WorkingHour wh WHERE wh.startDate <= :startDate AND :endDate <= wh.endDate")})



public class WorkingHour implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date endDate;

	@Column(name = "WORKING_HOUR")
	@NotNull
	private Double workingHour;

	public WorkingHour() {

	}

	public WorkingHour(int id, Date startDate, Date endDate, Double workingHour) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.workingHour = workingHour;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getstartDate() {
		return startDate;
	}

	public void setstartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getendDate() {
		return endDate;
	}

	public void setendDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getWorkingHours() {
		return workingHour;
	}

	public void setWorkingHours(Double workingHour) {
		this.workingHour = workingHour;
	}

}
