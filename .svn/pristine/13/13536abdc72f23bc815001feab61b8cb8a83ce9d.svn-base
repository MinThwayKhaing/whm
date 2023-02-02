/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose entity class for communication plan.
 *
 ***************************************************************************************/
package com.dat.whm.communicationplan.entity;

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

@Table(name = "COMMUNICATION_PLAN")
@Entity
public class CommunicationPlan implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project projectId;
	
	@Column(name = "COMM_GROUPS")
	private String commgroups;
	
	@Column(name = "COMM_TYPE")
	private String commType;
	
	@Column(name = "FREQUENCY")
	private String commFrequency;
	
	@Column(name = "COMM_METHOD")
	private String commMethod;
	
	@Column(name = "COMM_LANGUAGE")
	private String commLanguage;
	
	@Column(name = "COMM_PURPOSE")
	private String commPurpose;
	
	@Column(name = "ORDER_NO")
	private int orderNo;
	
	public CommunicationPlan() {
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

	public String getCommgroups() {
		return commgroups;
	}

	public void setCommgroups(String commgroups) {
		this.commgroups = commgroups;
	}

	public String getCommType() {
		return commType;
	}

	public void setCommType(String commType) {
		this.commType = commType;
	}

	public String getCommFrequency() {
		return commFrequency;
	}

	public void setCommFrequency(String commFrequency) {
		this.commFrequency = commFrequency;
	}

	public String getCommMethod() {
		return commMethod;
	}

	public void setCommMethod(String commMethod) {
		this.commMethod = commMethod;
	}

	public String getCommLanguage() {
		return commLanguage;
	}

	public void setCommLanguage(String commLanguage) {
		this.commLanguage = commLanguage;
	}

	public String getCommPurpose() {
		return commPurpose;
	}

	public void setCommPurpose(String commPurpose) {
		this.commPurpose = commPurpose;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

}
