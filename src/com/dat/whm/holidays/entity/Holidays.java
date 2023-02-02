/***************************************************************************************
 * @author Kyi Saw Win
 * @Date 2017-09-25
 * @Version 1.0
 * @Purpose Holiday Entity.
 *
 ***************************************************************************************/
package com.dat.whm.holidays.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Table(name = "HOLIDAYS")
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Holidays.countByYearMonth", query = "SELECT COUNT(h.date) FROM Holidays h WHERE h.date BETWEEN :fromDate AND :toDate"),
		@NamedQuery(name = "Holidays.findAll", query = "SELECT h FROM Holidays h  ORDER BY h.date"),
		@NamedQuery(name = "Holidays.findByDate", query = "SELECT h.date FROM Holidays h WHERE h.date = :date"),
		/**
		 * Created By	: Aye Chan Thar Soe
		 * Created Date	: 2018/09/04
		 * Explanation	: Find holidays by year and month.
		 */
		@NamedQuery(name = "Holidays.findByYearMonth", query = "SELECT h FROM Holidays h WHERE h.year = :year AND h.month = :month ORDER BY h.date"),
		/**
		 * Created By   : Htet Wai Yum
		 * Created Date : 2018/08/24
		 * Explanation  : Find all by date.
		 */
		@NamedQuery(name = "Holidays.findAllByDate", query = "SELECT h FROM Holidays h WHERE h.year = :year AND h.month = :month")})

public class Holidays implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "YEAR")
	@NotNull
	private String year;
	
	@Column(name = "MONTH")
	@NotNull
	private String month;
	
	@Column(name = "DATE")
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date date;
	
	@Column(name = "DESCRIPTION")
	@NotNull
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
