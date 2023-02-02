/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.user.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.common.entity.DeleteDiv;
import com.dat.whm.team.entity.Team;

@Table(name = "USERS")
@Entity
@TableGenerator(name = "USERS_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "USERS_GEN", allocationSize = 10)
@NamedQueries(value = { 
		/**
		 * Revised By   : Sai Kyaw Ye Myint
		 * Revised Date : 2017/09/08
		 * Explanation  : Modify User.findAll query to find only active user.
		 */
		@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u WHERE u.id <> 'USR0001' AND u.delDiv = com.dat.whm.common.entity.DeleteDiv.ACTIVE"),
		@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
		@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
		@NamedQuery(name = "User.findByRole", query = "SELECT u FROM User u WHERE u.role = :role AND u.id <> 'USR0001'"),
		@NamedQuery(name = "User.changePassword", query = "UPDATE User u SET u.password = :newPassword WHERE u.username = :username"),
		@NamedQuery(name = "User.resetPassword", query = "UPDATE User u SET u.password = :defaultPassowrd WHERE u.username = :username") })
public class User implements Serializable {

	public static final String DEFAULT_PASSWORD = "password";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USERS_GEN")
	private String id;
	private String username;
	private String fullName;
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name = "USER_ROLE")
	private Role role;
	@Version
	private int version;
	/** Revised By Aye Thida Phyo on 2017/07/17. */ 
	@Column(name = "DEL_DIV")
	@Enumerated(EnumType.ORDINAL)
	private DeleteDiv delDiv;

	@OneToMany
	@JoinTable(name = "USER_TEAM", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "TEAM_ID", referencedColumnName = "TEAM_ID", unique = true) })
	private List<Team> teams;

	@OneToMany
	@JoinTable(name = "USER_TEAM_AUTHORITY", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "TEAM_ID", referencedColumnName = "TEAM_ID", unique = true) })
	private List<Team> authorities;
	
	/**
	 * Revised By   : Sai Kyaw Ye Myint
	 * Revised Date : 2018/04/26
	 * Explanation  : Added mainlyBelognTeam and getter setter method.
	 */
	@OneToOne
	@JoinTable(name = "USER_TEAM_MAINLY_BELONG", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "TEAM_ID", referencedColumnName = "TEAM_ID", unique = true) })
	private Team mainlyBelongTeam;

	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams2) {
		this.teams = teams2;
	}

	public List<Team> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Team> authorities) {
		this.authorities = authorities;
	}

	public DeleteDiv getDelDiv() {
		return delDiv;
	}

	public void setDelDiv(DeleteDiv delDiv) {
		this.delDiv = delDiv;
	}
	
	public Team getMainlyBelongTeam() {
		return mainlyBelongTeam;
	}

	public void setMainlyBelongTeam(Team mainlyBelongTeam) {
		this.mainlyBelongTeam = mainlyBelongTeam;
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