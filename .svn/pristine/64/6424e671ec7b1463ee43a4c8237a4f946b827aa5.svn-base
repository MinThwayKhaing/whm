/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.password.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.common.entity.BaseEntity;
import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.user.entity.User;

@Table(name = "PASSWORD")
@Entity
public class Password extends BaseEntity implements Serializable {

	@Column(name = "HASH_CODE")
	@NotNull
	private String hashCode;
	@JoinColumn(name = "LAST_UPD_BY")
	private User lastUpdBy;
	@Column(name = "LAST_UPD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdDate;
	@Column(name = "DEL_DIV")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private DeleteDiv delDiv;

	public Password() {
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public User getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(User lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Calendar getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Calendar lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public DeleteDiv getDelDiv() {
		return delDiv;
	}

	public void setDelDiv(DeleteDiv delDiv) {
		this.delDiv = delDiv;
	}

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}