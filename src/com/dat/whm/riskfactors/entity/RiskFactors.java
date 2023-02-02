/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-13
 * @Version 1.0
 * @Purpose Entity class for risk factors.
 *
 ***************************************************************************************/
package com.dat.whm.riskfactors.entity;

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

@Table(name = "RISK_FACTORS")
@Entity
public class RiskFactors implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project projectId;

	@Column(name = "EXPECTED_RISK")
	private String riskExpected;
	
	@Column(name = "PRO_OCC")
	private String riskProbability;
	
	@Column(name = "IMPACT")
	private String riskimpact;
	
	@Column(name = "COUNTERMEASURE")
	private String riskCounterMeasure;

	@Column(name = "ORDER_NO")
	private int orderNo;
	
	public RiskFactors() {
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

	public String getRiskExpected() {
		return riskExpected;
	}

	public void setRiskExpected(String riskExpected) {
		this.riskExpected = riskExpected;
	}

	public String getRiskProbability() {
		return riskProbability;
	}

	public void setRiskProbability(String riskProbability) {
		this.riskProbability = riskProbability;
	}

	public String getRiskimpact() {
		return riskimpact;
	}

	public void setRiskimpact(String riskimpact) {
		this.riskimpact = riskimpact;
	}

	public String getRiskCounterMeasure() {
		return riskCounterMeasure;
	}

	public void setRiskCounterMeasure(String riskCounterMeasure) {
		this.riskCounterMeasure = riskCounterMeasure;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
}
