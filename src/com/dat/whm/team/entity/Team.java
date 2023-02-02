package com.dat.whm.team.entity;

import java.io.Serializable;

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
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.common.entity.DeleteDiv;

/***************************************************************************************
 * Updated By   : Aye Thida Phyo
 * Updated Date : 2017/09/12
 * Updated For  : Team Management for Phase III
 ***************************************************************************************/

@Table(name = "TEAM")
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t WHERE t.delDiv <> com.dat.whm.common.entity.DeleteDiv.DELETE ORDER BY t.teamName"),
		@NamedQuery(name = "Team.findByName", query = "SELECT t FROM Team t WHERE t.teamName = :teamName"),		
		@NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t WHERE t.id = :id"),
		@NamedQuery(name = "Team.findByTeamName", query = "SELECT t FROM Team t WHERE t.teamName = :teamName AND t.delDiv = :delDiv"),
		@NamedQuery(name = "Team.updateByTeamName", query = "UPDATE Team t SET t.delDiv = com.dat.whm.common.entity.DeleteDiv.ACTIVE , t.fullName = :fullName WHERE t.teamName = :teamName")})
public class Team implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEAM_ID")
	private int id;
	@Column(name = "TEAM_NAME")
	@NotNull
	private String teamName;
	@Column(name = "ORDER_NO")
	@NotNull
	private int orderNo;
	@Column(name = "DEL_DIV")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private DeleteDiv delDiv;
	/** Revised By Aye Thida Phyo on 2017/09/06. */ 
	/*@Version
	private int version;	*/
	@Column(name = "FULL_NAME")
	@NotNull
	private String fullName;

	public Team() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public DeleteDiv getDelDiv() {
		return delDiv;
	}

	public void setDelDiv(DeleteDiv delDiv) {
		this.delDiv = delDiv;
	}

	/*public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}*/

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return String.valueOf(getId());
	}
}