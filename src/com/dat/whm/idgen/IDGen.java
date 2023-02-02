package com.dat.whm.idgen;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "CUSTOM_ID_GEN")
@NamedQueries(value = {
		@NamedQuery(name = "IDGen.updateIDGen", query = "SELECT r FROM IDGen r WHERE r.generateItem = :generateItem"),
		@NamedQuery(name = "IDGen.findByName", query = "SELECT r FROM IDGen r WHERE r.generateItem = :generateItem") })
public class IDGen implements Serializable {
	@Id
	private String id;
	private String generateItem;
	@Column(name = "MAX_VALUE")
	private int maxValue;
	private String prefix;
	private int length;
	@Version
	private int version;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGenerateItem() {
		return generateItem;
	}

	public void setGenerateItem(String generateItem) {
		this.generateItem = generateItem;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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