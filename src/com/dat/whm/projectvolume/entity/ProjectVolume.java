/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Entity class for project volume.
 *
 ***************************************************************************************/
package com.dat.whm.projectvolume.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.dat.whm.project.entity.Project;

@Table(name = "PROJECT_VOLUME")
@Entity
public class ProjectVolume implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project projectId;
	
	@Column(name = "PHASE")
	@NotNull
	private String phase;
	
	@Column(name = "WORK")
	@NotNull
	private double workManDay;
	
	@Column(name = "REVIEW")
	@NotNull
	private double reviewManDay;
	
	@Transient
	private double totalManDay;
	@Transient
	private double workManMonth;
	@Transient
	private double reviewManMonth;
	@Transient
	private double totalManMonth;
	
	@Column(name = "ORDER_NO")
	private int orderNo;
	
	public ProjectVolume() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Project getProjectId() {
		return projectId;
	}


	public void setProjectId(Project projectId) {
		this.projectId = projectId;
	}


	public String getPhase() {
		return phase;
	}


	public void setPhase(String phase) {
		this.phase = phase;
	}


	public double getWorkManDay() {
		return workManDay;
	}


	public void setWorkManDay(double workManDay) {
		this.workManDay = workManDay;
	}


	public double getReviewManDay() {
		return reviewManDay;
	}


	public void setReviewManDay(double reviewManDay) {
		this.reviewManDay = reviewManDay;
	}


	public double getTotalManDay() {
		return totalManDay;
	}


	public void setTotalManDay(double totalManDay) {
		this.totalManDay = totalManDay;
	}


	public double getWorkManMonth() {
		return workManMonth;
	}


	public void setWorkManMonth(double workManMonth) {
		this.workManMonth = workManMonth;
	}


	public double getReviewManMonth() {
		return reviewManMonth;
	}


	public void setReviewManMonth(double reviewManMonth) {
		this.reviewManMonth = reviewManMonth;
	}


	public double getTotalManMonth() {
		return totalManMonth;
	}


	public void setTotalManMonth(double totalManMonth) {
		this.totalManMonth = totalManMonth;
	}


	public int getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

}
