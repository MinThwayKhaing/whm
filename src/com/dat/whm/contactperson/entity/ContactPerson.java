/***************************************************************************************
 * 
 *@author Sai Kyaw Ye Myint
 * @Date 2017-09-08
 * @Version 1.0
 * @Purpose entity class for contact person.
 *
 ***************************************************************************************/
package com.dat.whm.contactperson.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.dat.whm.common.entity.OrganizationDev;
import com.dat.whm.project.entity.Project;

@Table(name = "CONTACT_PERSON")
@Entity
public class ContactPerson implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project projectId;
	
	@Column(name = "ORGANIZATION_DIV")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private OrganizationDev organization;
	
	@Column(name = "EMAIL_TYPE")
	@NotNull
	private String emailType;
	
	@Column(name = "NAME")
	@NotNull
	private String name;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONE_NO")
	private String phone;
	
	@Column(name = "FAX")
	private String fax;
	
	@Column(name = "ORDER_NO")
	private int orderNo;
	
	public ContactPerson() {
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

	public OrganizationDev getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDev organization) {
		this.organization = organization;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

}
