/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose entity class for project control.
 *
 ***************************************************************************************/
package com.dat.whm.projectcontrol.entity;

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

@Table(name = "PROJECT_CONTROL")
@Entity
public class ProjectControl implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project projectId;
	
	@Column(name = "DELIVERABLES")
	private String prjCtlDeliverables;
	
	@Column(name = "PERSON_IN_CHARGE")
	private String prjCtlIncharge;
	
	@Column(name = "LANGUAGE_USED")
	private String prjCtlLanguage;
	
	@Column(name = "REMARK")
	private String prjCtlRemark;
	
	@Column(name = "ORDER_NO")
	private int orderNo;
	
	public ProjectControl() {
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

	public String getPrjCtlDeliverables() {
		return prjCtlDeliverables;
	}

	public void setPrjCtlDeliverables(String prjCtlDeliverables) {
		this.prjCtlDeliverables = prjCtlDeliverables;
	}

	public String getPrjCtlIncharge() {
		return prjCtlIncharge;
	}

	public void setPrjCtlIncharge(String prjCtlIncharge) {
		this.prjCtlIncharge = prjCtlIncharge;
	}

	public String getPrjCtlLanguage() {
		return prjCtlLanguage;
	}

	public void setPrjCtlLanguage(String prjCtlLanguage) {
		this.prjCtlLanguage = prjCtlLanguage;
	}

	public String getPrjCtlRemark() {
		return prjCtlRemark;
	}

	public void setPrjCtlRemark(String prjCtlRemark) {
		this.prjCtlRemark = prjCtlRemark;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
}
