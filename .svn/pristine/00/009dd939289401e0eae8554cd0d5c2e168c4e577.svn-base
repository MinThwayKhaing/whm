/***************************************************************************************
 * 
 * @author Sai Kyaw Ye Myint
 * @Date 2017-11-16
 * @Version 1.0
 * @Purpose Entity class for schedule start end.
 *
 ***************************************************************************************/
package com.dat.whm.schedulestartend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.dat.whm.projectschedule.entity.ProjectSchedule;

@Table(name = "SCHEDULE_START_END")
@Entity
public class ScheduleStartEnd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHEDULE_ID", referencedColumnName = "ID")
	private ProjectSchedule scheduleId;
	
	@Column(name = "START_POINT")
	@NotNull
	private int startPoint;
	
	@Column(name = "END_POINT")
	@NotNull
	private int endPoint;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProjectSchedule getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(ProjectSchedule scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
	}

	public int getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(int endPoint) {
		this.endPoint = endPoint;
	}

}
