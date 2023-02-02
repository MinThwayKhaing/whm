/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Entity class for project deliverable.
 *
 ***************************************************************************************/
package com.dat.whm.projectdeliverable.entity;

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

import com.dat.whm.project.entity.Project;

@Table(name = "PROJECT_DELIVERABLES")
@Entity
public class ProjectDeliverable implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project projectId;
	
	@Column(name = "PHASE")
	private String prjDelPhase;
	
	@Column(name = "DESIGN_TYPE")
	private String prjDelDesignType;
	
	@Column(name = "PERSON_IN_CHARGE")
	private String prjDelIncharge;
	
	@Column(name = "LANGUAGE_USED")
	private String prjDelLanguage;
	
	@Column(name = "REMARK")
	private String prjDelRemark;

	@Column(name = "ORDER_NO")
	private int orderNo;
	
	public ProjectDeliverable() {
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

	public String getPrjDelPhase() {
		return prjDelPhase;
	}

	public void setPrjDelPhase(String prjDelPhase) {
		this.prjDelPhase = prjDelPhase;
	}

	public String getPrjDelDesignType() {
		return prjDelDesignType;
	}

	public void setPrjDelDesignType(String prjDelDesignType) {
		this.prjDelDesignType = prjDelDesignType;
	}

	public String getPrjDelIncharge() {
		return prjDelIncharge;
	}

	public void setPrjDelIncharge(String prjDelIncharge) {
		this.prjDelIncharge = prjDelIncharge;
	}

	public String getPrjDelLanguage() {
		return prjDelLanguage;
	}

	public void setPrjDelLanguage(String prjDelLanguage) {
		this.prjDelLanguage = prjDelLanguage;
	}

	public String getPrjDelRemark() {
		return prjDelRemark;
	}

	public void setPrjDelRemark(String prjDelRemark) {
		this.prjDelRemark = prjDelRemark;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
}
