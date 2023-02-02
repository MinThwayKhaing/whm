/***************************************************************************************
 * @author Aye Myat Mon
 * @Date 2019-02-11
 * @Version 1.0
 * @Purpose MainlyBelongTeamHistory Entity.
 *
 ***************************************************************************************/
package com.dat.whm.userteammainlybelonghistory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.User;

@Table(name = "USER_TEAM_MAINLY_BELONG_HISTORY")
@Entity
@NamedQueries (value = {
		@NamedQuery (name = "UserTeamMainlyBelongHistory.findAll", query = "Select th From UserTeamMainlyBelongHistory th"),
		@NamedQuery (name = "UserTeamMainlyBelongHistory.findByIds", query = "Select th From UserTeamMainlyBelongHistory th where th.user.id = :userID and th.endDate is NULL"),
		@NamedQuery (name = "UserTeamMainlyBelongHistory.findByPeriod", query = "Select th From UserTeamMainlyBelongHistory th where th.user.id = :userID AND :reportDate between th.startDate AND th.endDate"),
		@NamedQuery (name = "UserTeamMainlyBelongHistory.updateEndDate", query = "Update UserTeamMainlyBelongHistory th Set th.endDate = :endDate where th.user.id = :userID and th.team.id = :teamID and th.endDate IS NULL")
})
public class UserTeamMainlyBelongHistory implements Serializable {

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
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

}
