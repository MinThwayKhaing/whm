/***************************************************************************************
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-12
 * @Purpose Entity object for "USER_TEAM_HISTORY" table.
 ***************************************************************************************/
package com.dat.whm.userteamhistory.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.User;

@Table(name = "USER_TEAM_HISTORY")
@Entity
public class UserTeamHistory implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@JoinColumn(name = "USER_ID")
	@NotNull
	private User userId;
	
	@JoinColumn(name = "TEAM_ID")
	@NotNull
	private Team teamId;
	
	@Column(name = "UPDATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date updateDate;
	
	@JoinColumn(name = "UPDATE_BY")
	@NotNull
	private User updateBy;
	
	public UserTeamHistory() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Team getTeamId() {
		return teamId;
	}

	public void setTeamId(Team teamId) {
		this.teamId = teamId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}
	

}
