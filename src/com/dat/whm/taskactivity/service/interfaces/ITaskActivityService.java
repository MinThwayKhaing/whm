/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2014-08-06
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package com.dat.whm.taskactivity.service.interfaces;

import java.util.List;

import com.dat.whm.exception.SystemException;
import com.dat.whm.taskactivity.entity.TaskActivity;

public interface ITaskActivityService {
	public TaskActivity addNewTaskActivity(TaskActivity taskActivity) throws SystemException;

	public TaskActivity updateTaskActivity(TaskActivity taskActivity) throws SystemException;

	public void deleteTaskActivity(TaskActivity taskActivity) throws SystemException;
	
	public List<TaskActivity> findAllTaskActivity() throws SystemException;
	
	public TaskActivity findTaskActivity(int id) throws SystemException;
}