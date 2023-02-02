/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-09-20
 * @Version 1.0
 * @Purpose Entity class for project schedule.
 *
 ***************************************************************************************/
package com.dat.whm.projectschedule.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.dat.whm.project.entity.Project;
import com.dat.whm.schedulestartend.entity.ScheduleStartEnd;
import com.dat.whm.web.project.WorkDayForProjectSchedule;

@Table(name = "PROJECT_SCHEDULE")
@Entity
public class ProjectSchedule implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	private Project projectId;
	
	@Column(name = "PHASE")
	@NotNull
	private String phase;
	
	@OneToMany(cascade=CascadeType.ALL ,fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHEDULE_ID")
	private List<ScheduleStartEnd> scheduleStartEndList;
	
	@Column(name = "ORDER_NO")
	private int orderNo;
	
	@Transient
	private List<WorkDayForProjectSchedule> workingList;
	
	public ProjectSchedule() {
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

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public List<WorkDayForProjectSchedule> getWorkingList() {
		return workingList;
	}

	public void setWorkingList(List<WorkDayForProjectSchedule> workingList) {
		this.workingList = workingList;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public List<ScheduleStartEnd> getScheduleStartEndList() {
		return scheduleStartEndList;
	}

	public void setScheduleStartEndList(List<ScheduleStartEnd> scheduleStartEndList) {
		this.scheduleStartEndList = scheduleStartEndList;
	}
	
}
