/***************************************************************************************
 * @author Htet Wai Yum
 * @Date 2018-09-17
 * @Version 1.0
 * @Purpose Added summary activity entity for summary activities table
 *
 *
 ***************************************************************************************/
package com.dat.whm.summaryactivity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.dat.whm.common.entity.BaseEntity;
import com.dat.whm.project.entity.Project;
import com.dat.whm.team.entity.Team;
import com.dat.whm.user.entity.User;

@Table(name = "SUMMARY_ACTIVITIES")
@Entity
@NamedQueries(value = {
		@NamedQuery(name = "SummaryActivity.findAll", query = "SELECT sa FROM SummaryActivity sa"),
		@NamedQuery(name = "SummaryActivity.findBystaffid", query = "SELECT sa FROM SummaryActivity sa WHERE sa.staff_id= :staff_id AND sa.year = :year AND sa.month = :month AND sa.team_id = :team_id"),
		@NamedQuery(name = "SummaryActivity.findByprojectid", query = "SELECT sa.activityHours FROM SummaryActivity sa WHERE sa.staff_id= :staff_id AND sa.year = :year AND sa.month = :month and sa.project_id = :project_id"),
		@NamedQuery(name = "SummaryActivity.updateBystaffid", query = "UPDATE SummaryActivity sa SET sa.activityHours = :activity_hours,sa.activity_date = :activity_date WHERE sa.staff_id = :staff_id AND sa.project_id = :project_id AND sa.year = :year AND sa.month = :month AND sa.team_id = :team_id" ),
		@NamedQuery(name = "SummaryActivity.updateByprojectidnull", query = "UPDATE SummaryActivity sa SET sa.activityHours = :activity_hours,sa.activity_date = :activity_date WHERE sa.staff_id = :staff_id AND sa.project_id IS NULL AND sa.year = :year AND sa.month = :month AND sa.team_id = :team_id" ),
		@NamedQuery(name = "SummaryActivity.updateByActivityDate", query = "UPDATE SummaryActivity sa SET sa.activity_date = :activity_date WHERE sa.staff_id = :staff_id AND sa.project_id = :project_id AND sa.year = :year AND sa.month = :month AND sa.team_id = :team_id" ),
		@NamedQuery(name = "SummaryActivity.updateByActivityDateNull", query = "UPDATE SummaryActivity sa SET sa.activity_date = :activity_date WHERE sa.staff_id = :staff_id AND sa.project_id = '9999' AND sa.year = :year AND sa.month = :month AND sa.team_id = :team_id" ),
		@NamedQuery(name = "SummaryActivity.deleteBystaffid", query = "DELETE FROM SummaryActivity sa WHERE sa.staff_id = :staff_id AND sa.activityHours = 0 AND sa.year = :year AND sa.month = :month AND sa.team_id = :team_id" ),
		@NamedQuery(name = "SummaryActivity.findMinDateByProjectID", query = "SELECT DISTINCT MIN(sa.activity_date) FROM SummaryActivity sa where sa.project_id LIKE :project_id"),
		@NamedQuery(name = "SummaryActivity.findMaxDateByProjectID", query = "SELECT DISTINCT MAX(sa.activity_date) FROM SummaryActivity sa where sa.project_id LIKE :project_id"),
		@NamedQuery(name = "SummaryActivity.findMinDate", query = "SELECT DISTINCT MIN(sa.activity_date) FROM SummaryActivity sa"),
		@NamedQuery(name = "SummaryActivity.findMaxDate", query = "SELECT DISTINCT MAX(sa.activity_date) FROM SummaryActivity sa"),
		@NamedQuery(name = "SummaryActivity.findByTeam", query = "SELECT sa FROM SummaryActivity sa WHERE sa.team_id = :team_id AND CONCAT(sa.year ,'-', sa.month ,'-', '01') BETWEEN :startYearMonth AND :endYearMonth"),
		@NamedQuery(name = "SummaryActivity.findMinDateByNullPrjID", query = "SELECT DISTINCT MIN(sa.activity_date) FROM SummaryActivity sa WHERE sa.project_id='9999' "),
		@NamedQuery(name = "SummaryActivity.findMaxDateByNullPrjID", query = "SELECT DISTINCT MAX(sa.activity_date) FROM SummaryActivity sa WHERE sa.project_id='9999'"),
		@NamedQuery(name = "SummaryActivity.findTeamByProjectID", query = "SELECT DISTINCT sa FROM SummaryActivity sa WHERE sa.project_id = :project_id GROUP BY sa.team_id")})


public class SummaryActivity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "PROJECT_ID")
	@Null
	private String project_id;
	
	@Column(name = "PROJECT_NAME")
	@Null
	private String projectname;
	
	@Column(name = "CLIENT_ORGANIZATION")
	@Null
	private String clientorganization;
	
	
	@Column(name = "TOTAL_MAN_MONTH")
	@Null
	private Double totalmanmonth;


	@Column(name = "STAFF_ID")
	@Null
	private String staff_id;
	
	@Column(name = "USERNAME")
	@Null
	private String username;
	
	@Column(name = "FULLNAME")
	@Null
	private String fullname;



	@Column(name = "TEAM_ID")
	@Null
	private int team_id;
	
	@Column(name = "TEAM_NAME")
	@Null
	private String teamname;

	@Column(name = "ACTIVITY_HOURS")
	@Null
	private Double activityHours;

	@Column(name = "YEAR")
	private String year;

	@Column(name = "MONTH")
	private String month;
	
	@Column(name = "ACTIVITY_DATE")
	@Temporal(TemporalType.DATE)
	@Null
	private Date activity_date;
	

	
	public SummaryActivity() {

	}
	
	public SummaryActivity(String project_id, String projectname,
			String clientorganization, Double totalmanmonth, String staff_id,
			String username, String fullname, int team_id, String teamname,
			Double activityHours, String year, String month, Date activity_date
			) {
		super();
		this.project_id = project_id;
		this.projectname = projectname;
		this.clientorganization = clientorganization;
		this.totalmanmonth = totalmanmonth;
		this.staff_id = staff_id;
		this.username = username;
		this.fullname = fullname;
		this.team_id = team_id;
		this.teamname = teamname;
		this.activityHours = activityHours;
		this.year = year;
		this.month = month;
		this.activity_date = activity_date;
		
	}







	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getClientorganization() {
		return clientorganization;
	}

	public void setClientorganization(String clientorganization) {
		this.clientorganization = clientorganization;
	}

	public Double getTotalmanmonth() {
		return totalmanmonth;
	}

	public void setTotalmanmonth(Double totalmanmonth) {
		this.totalmanmonth = totalmanmonth;
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public Double getActivityHours() {
		return activityHours;
	}

	public void setActivityHours(Double activityHours) {
		this.activityHours = activityHours;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	

	public Date getActivity_date() {
		return activity_date;
	}

	public void setActivity_date(Date activity_date) {
		this.activity_date = activity_date;
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