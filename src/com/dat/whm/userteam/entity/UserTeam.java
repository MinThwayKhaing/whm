/***************************************************************************************
 * @author Aye Thida Phyo
 * @Date 2017-09-14
 * @Version 1.0
 * @Purpose Created for PhaseIII : Team Management
 *
 ***************************************************************************************/
package com.dat.whm.userteam.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.User;

@Table(name = "USER_TEAM")
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "UserTeam.findByTeamID", query = "SELECT ut FROM UserTeam ut WHERE ut.team.id = :team_id")})
public class UserTeam implements Serializable {	
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@NotNull
	private User user;
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_ID")
	@NotNull
	private Team team;	
	
	public UserTeam() {

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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