/***************************************************************************************
 * @author Aye Myat Mon
 * @Date 2018-02-01
 * @Version 1.0
 * @Purpose Created for HR Overhead Cost Management function.
 ***************************************************************************************/
package com.dat.whm.hrcost.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.dat.whm.common.entity.DeleteDiv;

@Table(name = "HRCOST")
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "HRCost.findAll", query = "SELECT hr FROM HRCost hr WHERE hr.delDiv<> com.dat.whm.common.entity.DeleteDiv.DELETE ORDER BY hr.year "),
		@NamedQuery(name = "HRCost.findByYear", query = "SELECT hr FROM HRCost hr WHERE hr.year = :year AND hr.delDiv = :delDiv"),
		@NamedQuery(name = "HRCost.findHRCost", query="SELECT hr FROM HRCost hr WHERE hr.hr_cost = :hr_cost"),
		@NamedQuery(name = "HRCost.findById", query="SELECT hr FROM HRCost hr WHERE hr.id= :id"),
		@NamedQuery(name = "HRCost.updateByHRCost", query="UPDATE HRCost hr SET hr.delDiv = com.dat.whm.common.entity.DeleteDiv.ACTIVE , hr.hr_cost = :hr_cost WHERE hr.year = :year")})
public class HRCost implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "YEAR")
	@NotNull
	private String year;

	@Column(name = "HR_COST")
	@NotNull
	private double hr_cost;

	@Column(name ="DEL_DIV")
	@Enumerated(EnumType.ORDINAL)
	@NotNull 
	private DeleteDiv delDiv;
	
	
	public DeleteDiv getDelDiv() {
		return delDiv;
	}

	public void setDelDiv(DeleteDiv delDiv) {
		this.delDiv = delDiv;
	}

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

	public double getHr_cost() {
		return hr_cost;
	}

	public void setHr_cost(double hr_cost) {
		this.hr_cost = hr_cost;
	}

}
