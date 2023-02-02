/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.taskactivity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.common.entity.BaseEntity;
import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.user.entity.User;

@Table(name = "TASK_ACTIVITY")
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "TaskActivity.findAll", query = "SELECT ta FROM TaskActivity ta WHERE ta.delDiv <> com.dat.whm.common.entity.DeleteDiv.DELETE ORDER BY ta.orderNo"),
		@NamedQuery(name = "TaskActivity.findById", query = "SELECT ta FROM TaskActivity ta WHERE ta.id = :id")})
public class TaskActivity extends BaseEntity implements Serializable {

	@Column(name = "DESCRIPTION")
	@NotNull
	private String description;
	@JoinColumn(name = "LAST_UPD_BY")
	private User lastUpdBy;
	@Column(name = "LAST_UPD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdDate;
	@Column(name = "DEL_DIV")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private DeleteDiv delDiv;
	@Version
	private int version;
	@Column(name = "ORDER_NO")
	@NotNull
	private int orderNo;

	public TaskActivity() {
	}

	public TaskActivity(String description) {
		this.description = description;
		this.delDiv = DeleteDiv.ACTIVE;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(User lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public DeleteDiv getDelDiv() {
		return delDiv;
	}

	public void setDelDiv(DeleteDiv delDiv) {
		this.delDiv = delDiv;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
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